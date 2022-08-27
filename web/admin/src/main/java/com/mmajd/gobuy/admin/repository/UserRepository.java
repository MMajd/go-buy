package com.mmajd.gobuy.admin.repository;

import com.mmajd.gobuy.common.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {

    @Query(value = "SELECT * FROM users WHERE email = :email", nativeQuery = true)
    UserEntity findByEmail(@Param("email") String mail);

    @Query(value = "SELECT COUNT(*) FROM users WHERE id >= :id", nativeQuery = true)
    long countById(@Param("id") Long id);
//    public Long countById(Long id);

    @Query(value = "SELECT * FROM users WHERE first_name LIKE %?1% or last_name LIKE %?1%", nativeQuery = true)
    Page<UserEntity> findAll(String keyword, Pageable pageable);

    @Modifying
    @Query(value = "UPDATE users SET enabled = :enabled WHERE id = :id", nativeQuery = true)
    void updateUserEnable(@Param("id") Long id, @Param("enabled") Boolean enabled);
}
