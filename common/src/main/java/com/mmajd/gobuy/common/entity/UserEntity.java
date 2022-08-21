package com.mmajd.gobuy.common.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(length = 128, unique = true, nullable = false)
    private String email;
    @Column(length=64, nullable = false)
    private String password;
    @Column(length = 40)
    private String firstName;
    @Column(length = 40)
    private String lastName;
    @Column(length=64, name = "photos")
    private String photo;
    @Column(name = "enabled")
    private Boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = RoleEntity.class)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roles = new HashSet<>();

    public boolean addRole(RoleEntity role) {
        return roles.add(role);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof UserEntity)) return false;
        if (this == o) return true;
        UserEntity other = (UserEntity) o;
        return getId().equals(other.getId()) && Objects.equals(getEmail(), other.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail());
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", photos='" + photo + '\'' +
                ", enabled=" + enabled +
                ", roles=" + roles +
                '}';
    }
}
