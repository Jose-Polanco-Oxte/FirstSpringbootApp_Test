package com.devtony.app.security;

import com.devtony.app.exception.ExceptionDetails;
import com.devtony.app.exception.UserAuthException;
import com.devtony.app.model.User;
import com.devtony.app.repository.IUserRepository;
import com.devtony.app.security.details.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String name) throws UserAuthException {
        User user = userRepository.findByEmail(name)
                .orElseThrow(() -> new UserAuthException("User not found",
                        new ExceptionDetails("Usuario no encontrado", "low")));
        return new CustomUserDetails(user);
    }

    public CustomUserDetails loadUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserAuthException("User not found",
                        new ExceptionDetails("Usuario no encontrado", "low")));
        return new CustomUserDetails(user);
    }
}
