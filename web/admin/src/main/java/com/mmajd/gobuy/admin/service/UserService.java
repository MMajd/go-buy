package com.mmajd.gobuy.admin.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mmajd.gobuy.admin.exceptions.NotFoundException;
import com.mmajd.gobuy.admin.repository.UserRepository;
import com.mmajd.gobuy.common.entity.UserEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public List<UserEntity> listAll() {
        return (List<UserEntity>) repository.findAll();
    }

    public UserEntity save(UserEntity user) {
        encodeUserPassword(user);
        return repository.save(user);
    }

    public UserEntity update(UserEntity user) {
        UserEntity foundUser = repository.findById(user.getId()).orElseThrow(() -> {
            return new NotFoundException("No user found with given id");
        });

        if (user.getPassword().trim().equals("")) {
            user.setPassword(foundUser.getPassword());
            return repository.save(user);
        }

        encodeUserPassword(user);
        return repository.save(user);
    }

    private void encodeUserPassword (UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    public boolean uniqueEmail(Long id, String email) {
        UserEntity user = repository.findByEmail(email);

        if (id == null) {
            return user == null;
        }

        if (user == null) {
            return true;
        }

        return user.getId().equals(id);
    }

    public UserEntity get(Long id) {
            return repository.findById(id)
                    .orElseThrow(() -> {
                        return new NotFoundException("No user found with given id " + id);
                    });
    }

    public void delete(Long id) throws NotFoundException {
        if (repository.countById(id) == 0) {
            throw new NotFoundException("No used found with given id " + id);
        }

        repository.deleteById(id);
    }

    @Transactional
    public void updateEnabledStatus(Long id, Boolean enabled) {
        repository.updateUserEnable(id, enabled);
    }

}
