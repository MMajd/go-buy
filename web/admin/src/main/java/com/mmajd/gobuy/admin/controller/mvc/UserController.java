package com.mmajd.gobuy.admin.controller.mvc;

import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.mmajd.gobuy.admin.exceptions.NotFoundException;
import com.mmajd.gobuy.admin.service.RoleService;
import com.mmajd.gobuy.admin.service.UserService;
import com.mmajd.gobuy.admin.utils.FileUploadUtil;
import com.mmajd.gobuy.admin.utils.MediaExportUtil;
import com.mmajd.gobuy.common.PagesUtil;
import com.mmajd.gobuy.common.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.lowagie.text.FontFactory.HELVETICA_BOLD;
import static com.lowagie.text.FontFactory.getFont;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class UserController {
    private static final String[] headers = {"Id", "Name", "Email", "Roles", "Active"};

    private final UserService service;
    private final RoleService roleService;


    @GetMapping
    String listAllUsers(
            @RequestParam("page") @Nullable Integer pageNo,
            @RequestParam("sort-prop") @Nullable String sortProp,
            @RequestParam("sort-dir") @Nullable String sortDir,
            @RequestParam("keyword") @Nullable String keyword,
            Model model
    ) {
        if (pageNo == null || pageNo < 1) {
            return "redirect:/users?page=1";
        }

        Sort sort = Sort.by(Sort.Direction.fromString(sortDir == null ? "asc" : sortDir), sortProp == null ? "id" : sortProp);

        Page<UserEntity> usersPage = service.listByPage(pageNo - 1, sort, keyword);
        model.addAttribute("startCount", PagesUtil.getStartCount(pageNo, UserService.PAGE_SIZE));
        model.addAttribute("endCount", PagesUtil.getEndCount(pageNo, UserService.PAGE_SIZE, usersPage.getTotalElements()));
        model.addAttribute("totalRecords", usersPage.getTotalElements());
        model.addAttribute("currPage", pageNo);
        model.addAttribute("totalPages", usersPage.getTotalPages());
        model.addAttribute("users", usersPage.getContent());
        return "users/index";
    }

    @GetMapping("/new")
    String newUser(Model model) {
        UserEntity user = new UserEntity();
        user.setEnabled(true);
        model.addAttribute("pageTitle", "Create User");
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.listAll());
        return "users/form";
    }

    @PostMapping("/save")
    String saveUser(
            @RequestParam("image") MultipartFile imgFile,
            UserEntity user,
            RedirectAttributes attributes
    ) throws IOException {

        String filename = StringUtils.cleanPath(Objects.requireNonNull(imgFile.getOriginalFilename()));

        if (filename.length() > 0) {
            user.setPhoto(filename);
        }

        UserEntity savedUser = service.save(user);

        if (!imgFile.isEmpty()) {
            FileUploadUtil.saveUserImage(savedUser.getId().toString(), filename, imgFile);
        }

        attributes.addFlashAttribute("message", "Saved Successfully");
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    String editUser(@PathVariable Long id, Model model, RedirectAttributes attributes) throws IOException {
        try {
            UserEntity user = service.get(id);

            user.setPassword(null);
            model.addAttribute("pageTitle", "Edit User: " + user.getId());
            model.addAttribute("user", user);
            model.addAttribute("roles", roleService.listAll());

            return "users/form";
        } catch (NotFoundException ex) {
            attributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/users";
        }
    }

    @PostMapping("/update/{id}")
    String updateUser(
            @RequestParam("image") MultipartFile imgFile,
            @PathVariable("id") Long id,
            UserEntity user,
            Model model,
            RedirectAttributes attributes
    ) {

        log.info("Update values {}", user.toString());

        try {
            if (!imgFile.isEmpty()) {
                String filename = StringUtils.cleanPath(Objects.requireNonNull(imgFile.getOriginalFilename()));
                user.setPhoto(filename);
                FileUploadUtil.saveUserImage(user.getId().toString(), filename, imgFile);
            }

            service.update(user);
            attributes.addFlashAttribute("message", "Updated Successfully");
        } catch (NotFoundException | IOException ex) {
            attributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/users";
    }

    @PutMapping("/update-status/{id}")
    String updateUserEnableStatus(
            @PathVariable("id") Long id,
            Boolean enabled,
            Model model,
            RedirectAttributes attributes
    ) {
        log.debug("User status Id: {}, Enabled: {}", id, enabled);

        service.updateEnabledStatus(id, enabled);

        String message = "User with id: [" + id + "] account has been " + (enabled ? "activated" : "deactivated");
        attributes.addFlashAttribute("message", message);

        return "redirect:/users";
    }

    @DeleteMapping("/delete/{id}")
    String deleteUser(
            @PathVariable("id") Long id,
            Model model,
            RedirectAttributes attributes
    ) {

        try {
            service.delete(id);
            attributes.addFlashAttribute("message", "Deleted Successfully");
        } catch (NotFoundException ex) {
            attributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/users";
    }

    // TODO: change this to use async operations
    @GetMapping("/export/csv")
    String exportCSV(HttpServletResponse response) throws IOException {
        String[] mappings = new String[]{"id", "fullName", "email", "roles", "enabled"};

        List<UserEntity> users = service.listAll();

        MediaExportUtil.exportCSV(response, "users", writer -> {
            try {
                writer.writeHeader(headers);
                for (UserEntity u : users) writer.write(u, mappings);
            } catch (IOException e) {
                throw new RuntimeException("Could not export users data to csv");
            }
        });

        return "redirect:/users";
    }

    // TODO: change this to use async operations
    @GetMapping("/export/pdf")
    void exportPDF(HttpServletResponse response) throws IOException {

        List<UserEntity> users = service.listAll();

        MediaExportUtil.exportPDF(response, "users", doc -> {
            doc.addTitle("Generated Users list");
            doc.addSubject("Users list");

            doc.addHeader("Generated", "Users list");

            doc.add(new Paragraph("LIST OF SYSTEM USERS:", getFont(HELVETICA_BOLD, 16)));

            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100f);
            table.setSpacingBefore(10);

            table.setWidths(new float[]{1.5f, 3.5f, 3.5f, 3.5f, 1.5f});

            for (String h : headers) {
                MediaExportUtil.writeHeaderCellToPDF(table, h);
            }

            for (UserEntity u : users) {
                MediaExportUtil.writeDataCellToPDF(table, u.getId().toString());
                MediaExportUtil.writeDataCellToPDF(table, u.getFullName());
                MediaExportUtil.writeDataCellToPDF(table, u.getEmail());
                MediaExportUtil.writeDataCellToPDF(table, u.getRoles().toString());
                MediaExportUtil.writeDataCellToPDF(table, u.getEnabled().toString());
            }

            doc.add(table);
        });
    }
}
