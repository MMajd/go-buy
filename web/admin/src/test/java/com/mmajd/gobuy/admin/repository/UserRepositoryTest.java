package com.mmajd.gobuy.admin.repository;

import com.mmajd.gobuy.common.entity.UserEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
//    @Autowired UserRepository repository;

//    @Test
//    void shouldListUsers() {
//        List<UserEntity> users = (List<UserEntity>) (repository.findAll());
//
//        System.out.println(users);
//
//        assertThat(users.size()).isGreaterThan(0);
//    }
}