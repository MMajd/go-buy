package com.mmajd.gobuy.admin.repository;

import com.mmajd.gobuy.common.entity.RoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Long> {
}
