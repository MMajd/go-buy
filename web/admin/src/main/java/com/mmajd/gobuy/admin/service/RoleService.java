package com.mmajd.gobuy.admin.service;

import com.mmajd.gobuy.admin.repository.RoleRepository;
import com.mmajd.gobuy.common.entity.RoleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository repository;

    public List<RoleEntity> listAll() {
        return (List<RoleEntity>) repository.findAll();
    }
}
