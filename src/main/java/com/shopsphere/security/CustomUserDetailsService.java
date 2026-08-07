package com.shopsphere.security;

import org.springframework.stereotype.Service;
import com.shopsphere.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;

import com.shopsphere.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
        throws UsernameNotFoundException {

    User user = userRepository.findByEmail(username)
                .orElseThrow(()->
                            new UsernameNotFoundException("User not found"));
    
    return org.springframework.security.core.userdetails.User
            .withUsername(user.getEmail())
            .password(user.getPassword())
            .authorities(
                List.of(
                    new SimpleGrantedAuthority(user.getRole().name())
                )
            )
            .build();                        
}

}