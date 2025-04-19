package com.devtony.app.services.validations;

import com.devtony.app.dto.user.UserRequestDto;
import com.devtony.app.exception.ExceptionDetails;
import com.devtony.app.exception.UserException;
import com.devtony.app.repository.IUserRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserValidations {
    private final IUserRepository repoUser;

    public UserValidations(IUserRepository repoUser) {
        this.repoUser = repoUser;
    }

    public void existEmail(String email) throws UserException {
        if (repoUser.existsByEmail(email)) {
            throw new UserException("Email already exists in the database",
                    new ExceptionDetails("El correo ya ha sido registrado", "low"));
        }
    }

    public void validatePassword(String password) throws UserException{
        if (password.contains(" ")) {
            throw new UserException("Password contains spaces",
                    new ExceptionDetails("La contraseña contiene espacios", "low"));
        }
        if (password.matches(".*\\p{C}.*")) {
            throw new UserException("Password contains invalid characters",
                    new ExceptionDetails("La contraseña contiene caracteres inválidos", "low"));
        }
        if (password.length() < 8 || password.length() > 50) {
            throw new UserException("Password length must be between 8 and 50 characters",
                    new ExceptionDetails("La contraseña debe tener entre 8 y 50 caracteres", "low"));
        }
    }

    public void validateEmail(String email) throws UserException {
        if (email.contains(" ")) {
            throw new UserException("Email contains spaces",
                    new ExceptionDetails("El correo contiene espacios", "low"));
        }
        if (!email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new UserException("Invalid email",
                    new ExceptionDetails("Correo inválido", "low"));
        }
    }

    public void validateName(String name) throws UserException {
        System.out.println(name);

        if (name.matches(".*\\d.*")) {
            throw new UserException("Name contains numbers",
                    new ExceptionDetails("El nombre contiene números", "low"));
        }
        if (name.matches(".*\\p{Punct}.*")) {
            throw new UserException("Name contains special characters",
                    new ExceptionDetails("El nombre contiene caracteres especiales", "low"));
        }
        if (name.length() < 3 || name.length() > 70) {
            throw new UserException("Name length must be between 3 and 50 characters",
                    new ExceptionDetails("El nombre debe tener entre 3 y 50 caracteres", "low"));
        }
    }
    public void validateSave(UserRequestDto user) throws UserException {
        existEmail(user.getEmail());
        validateEmail(user.getEmail());
        validatePassword(user.getPassword());
        validateName(user.getName());
    }

    public void validateUsersReturn(List<?> users, String message) throws UserException{
        if (users.isEmpty()) {
            throw new UserException("No users found",
                    new ExceptionDetails(message, "low"));
        }
    }
}
