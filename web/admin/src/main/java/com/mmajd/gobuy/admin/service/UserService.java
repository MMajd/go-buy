package com.mmajd.gobuy.admin.service;
import com.mmajd.gobuy.admin.exceptions.NotFoundException;
import com.mmajd.gobuy.admin.repository.UserRepository;
import com.mmajd.gobuy.common.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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
        UserEntity foundUser = repository.findById(user.getId()).orElseGet(null);

        if (foundUser == null) return null;

        encodeUserPassword(user);
        foundUser.setEmail(user.getEmail());
        foundUser.setPassword(user.getPassword());
        foundUser.setFirstName(user.getFirstName());
        foundUser.setLastName(user.getLastName());
        foundUser.setPhotos(user.getPhotos());
        foundUser.setEnabled(user.getEnabled());

        repository.save(foundUser);

        return foundUser;
    }

    private void encodeUserPassword (UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    public boolean uniqueEmail(String email) {
        return repository.findByEmail(email) == null;
    }

    public UserEntity get(Long id) {
            return repository.findById(id)
                    .orElseThrow(() -> {
                        return new NotFoundException("No user found with given id " + id);
                    });
    }
}
