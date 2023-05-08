package com.jdbmAPIcore.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jdbmAPIcore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static jdk.nashorn.internal.objects.NativeString.toUpperCase;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<SimpleGrantedAuthority> roles = null;

        com.jdbmAPIcore.entity.User user = new UserService(userRepository).getUserByUsername(username);

        if(username.equals("admin")) {
            roles = new ArrayList<SimpleGrantedAuthority>();
            roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

            String sql = "update public.users set authorities = '" + roles.toString() + "' where id = " + user.getId();

            jdbcTemplate.execute(sql);

            return new User(username, user.getPassword(), roles);
        }

        roles = new ArrayList<SimpleGrantedAuthority>();
        roles.add(new SimpleGrantedAuthority("ROLE_USER"));
        //roles.add(new SimpleGrantedAuthority("ROLE_" + toUpperCase(username)));

        String sql = "update public.users set authorities = '" + roles.toString() + "' where id = " + user.getId();

        jdbcTemplate.execute(sql);

        return new User(username, user.getPassword(), roles);
    }

}
