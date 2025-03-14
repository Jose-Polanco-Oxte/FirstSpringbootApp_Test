package com.devtony.app.services;

import com.devtony.app.exception.ExceptionDetails;
import com.devtony.app.exception.UserException;
import com.devtony.app.dto.user.UserRequestDto;
import com.devtony.app.dto.user.UserResponseDto;
import com.devtony.app.model.User;
import com.devtony.app.repository.IUserRepository;
import com.devtony.app.repository.projections.UserProjection;
import com.devtony.app.services.interfaces.IUserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IUserRepository repoUser;

    @Override
    public List<UserProjection> getUsers() {
        if (repoUser.count() == 0) {
            throw new UserException("No users found",
                    new ExceptionDetails("No existen usuarios registrados actualmente", "low"));
        }
        return repoUser.findAllBy();
    }

    @Override
    @Transactional
    public void saveUser(UserRequestDto userRequestDto) throws UserException {
        if (repoUser.existsByEmail(userRequestDto.getEmail())) {
            throw new UserException("Email already exists in the database",
                    new ExceptionDetails("El correo ya ha sido registrado", "low"));
        }
        User user = new User();
        user.setName(userRequestDto.getName());
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        user.setEmail(userRequestDto.getEmail());
        user.setRoles("USER");
        repoUser.save(user);
    }

    @Override
    public UserResponseDto getUser(Long id) {
        if (!repoUser.existsById(id)) {
            throw new UserException("User not found",
                    new ExceptionDetails("El usuario no existe", "low"));
        }
        User user = repoUser.getReferenceById(id);
        return new UserResponseDto(user.getId(), user.getName(), user.getEmail());
    }

    @Override
    public void updateUser(UserRequestDto userRequestDto) {
        AuxiliarAuthService auxiliarAuthService = new AuxiliarAuthService();
        if (!repoUser.existsById(auxiliarAuthService.getId())) {
            throw new UserException("User not found",
                    new ExceptionDetails("El usuario no existe", "low"));
        }
        User user = repoUser.getReferenceById(auxiliarAuthService.getId());
        user.setName(userRequestDto.getName());
        user.setEmail(userRequestDto.getEmail());
        repoUser.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        if (!repoUser.existsById(id)) {
            throw new UserException("User not found",
                    new ExceptionDetails("El usuario no existe", "low"));
        }
        repoUser.deleteById(id);
    }
}
