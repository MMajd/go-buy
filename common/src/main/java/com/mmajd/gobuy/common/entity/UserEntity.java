package com.mmajd.gobuy.common.entity;

import com.mmajd.gobuy.common.constant.ASSETS_CONSTANTS;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column()
    private String email;
    @Column()
    private String password;
    @Column()
    private String firstName;
    @Column()
    private String lastName;
    @Column(name = "photos")
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

    @Transient
    public String imagePath() {
        if (getId() == null || getPhoto() == null) {
            return ASSETS_CONSTANTS.ASSET_DIR.getDir() + "/default-user.png";
        }

        return ASSETS_CONSTANTS.USER_IMAGES_DIR.getDir() + "/" + getId() + "/" + getPhoto();
    }

    @Transient
    public String fullName() {
        return getFirstName() + " " + getLastName();
    }

    @Transient
    public String getFullName() {
        return getFirstName() + " " + getLastName();
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
