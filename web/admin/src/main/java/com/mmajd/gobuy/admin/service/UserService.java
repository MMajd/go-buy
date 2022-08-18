package com.mmajd.gobuy.admin.service;
import com.mmajd.gobuy.admin.repository.UserRepository;
import com.mmajd.gobuy.common.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public List<UserEntity> listAll() {
        return (List<UserEntity>) repository.findAll();
    }

    public UserEntity save(UserEntity user) {
        return repository.save(user);
    }
}
