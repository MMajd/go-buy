package com.mmajd.gobuy.common.entity;

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
@Table(name = "roles")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoleEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", length = 40, nullable = false, unique = true)
    private String name;
    @Column(name = "description", nullable = false)
    private String description;

    @ManyToMany(targetEntity = Operation.class, fetch = FetchType.EAGER)
    @JoinTable(name = "roles_operations",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "op_id")
    )
    private Set<Operation> ops = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RoleEntity)) return false;
        if (o == this) return true;
        RoleEntity other = (RoleEntity) o;
        return getId().equals(other.getId()) && getName().equals(other.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription());
    }

    @Override
    public String toString() {
        return getName();
    }
}
