package com.mmajd.gobuy.admin.repository;

import com.mmajd.gobuy.common.entity.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    @Query(value = "SELECT * FROM users WHERE email = :email", nativeQuery = true)
    public UserEntity findByEmail(@Param("email") String mail);

    @Query(value = "SELECT COUNT(*) FROM users WHERE id >= :id", nativeQuery = true)
    public long countById(@Param("id") Long id);
//    public Long countById(Long id);

    @Modifying
    @Query(value = "UPDATE users SET enabled = :enabled WHERE id = :id", nativeQuery = true)
    public void updateUserEnable(@Param("id") Long id, @Param("enabled") Boolean enabled);
}
