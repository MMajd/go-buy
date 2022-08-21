package com.mmajd.gobuy.admin.controller.rest;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmajd.gobuy.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserRestController {
    private final UserService service;
    private final ObjectMapper objectMapper;

    @PostMapping("/check-email")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object checkEmail(@RequestParam("id") @Nullable Long userId,
                             @RequestParam("email") String email) {
        log.info("Given: user id: {}, and email: {} ", userId, email);
        return service.uniqueEmail(userId, email);
    }
}
