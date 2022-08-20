package com.mmajd.gobuy.admin.repository;

import com.mmajd.gobuy.common.entity.UserEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
    @Autowired UserRepository repository;
    UserEntity user;

    @BeforeEach
    void beforeEach() {
        user = new UserEntity(null,
                "dummy@test.com",
                "password",
                "firstname",
                "lastname",
                null,
                true,
                null);
        repository.save(user);
    }

    @Test
    void shouldFindByEmail() {
        UserEntity foundUser = repository.findByEmail("dummy@test.com");

        System.out.println(foundUser);

        assertThat(foundUser).isNotNull();
        assertThat(user.getId()).isEqualTo(foundUser.getId());
    }

    @Test
    void shouldNotFindByEmail () {
        UserEntity user = repository.findByEmail("no-existance-email@no-domail.void");

        assertThat(user).isNull();
    }
}