package com.devtony.app.security;

import com.devtony.app.exception.ExceptionDetails;
import com.devtony.app.exception.UserAuthException;
import com.devtony.app.model.User;
import com.devtony.app.repository.IUserRepository;
import com.devtony.app.security.details.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUserRepository userRepository;

    public UserDetailsServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String name) throws UserAuthException {
        User user = userRepository.findByEmail(name)
                .orElseThrow(() -> new UserAuthException("User not found",
                        new ExceptionDetails("Usuario no encontrado", "low")));
        return new CustomUserDetails(user);
    }

    public CustomUserDetails loadUserById(Long id) throws UserAuthException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserAuthException("User not found",
                        new ExceptionDetails("Usuario no encontrado", "low")));
        return new CustomUserDetails(user);
    }
}
