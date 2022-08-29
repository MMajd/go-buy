package com.mmajd.gobuy.admin.websecurity;

import com.mmajd.gobuy.admin.repository.UserRepository;
import com.mmajd.gobuy.common.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = repository.findByEmail(email);

        if (user == null)
            throw new UsernameNotFoundException("Cannot find user with given email: " + email);

        return new UserDetailsImpl(user);
    }
}
