package com.mmajd.gobuy.admin.websecurity;

import com.mmajd.gobuy.common.entity.RoleEntity;
import com.mmajd.gobuy.common.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private final UserEntity user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<RoleEntity> roles = user.getRoles();

        Set<SimpleGrantedAuthority> authorities = new HashSet<>(roles.size());

        roles.forEach(role ->
                authorities.add(new SimpleGrantedAuthority(role.getName()))
        );

        roles.forEach(r -> r.getOps().forEach(op -> authorities.add(new SimpleGrantedAuthority(op.getName()))));

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return isEnabled();
    }

    @Override
    public boolean isAccountNonLocked() {
        return isEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isEnabled();
    }

    @Override
    public boolean isEnabled() {
        return user.getEnabled();
    }

    public String getImagePath() {
        return user.imagePath();
    }

    public String getFullName() {
        return user.getFullName();
    }
}
