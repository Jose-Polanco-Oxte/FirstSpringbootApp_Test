package com.devtony.app.services;

import com.devtony.app.exception.ExceptionDetails;
import com.devtony.app.exception.UserException;
import com.devtony.app.dto.user.UserRequestDto;
import com.devtony.app.dto.user.UserResponseDto;
import com.devtony.app.model.User;
import com.devtony.app.repository.IUserRepository;
import com.devtony.app.repository.projections.PredictedUserProjection;
import com.devtony.app.services.interfaces.IUserService;
import com.devtony.app.services.utils.QRGenerator;
import com.devtony.app.services.validations.UserValidations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.image.BufferedImage;
import java.util.List;

@Service
public class UserService implements IUserService {

    private final PasswordEncoder passwordEncoder;
    private final IUserRepository repoUser;
    private final UserValidations validations;
    private final QRGenerator qrGenerator;
    private final AuxiliarAuthService authService;
    private final String serverUrl;

    public UserService(PasswordEncoder passwordEncoder, IUserRepository repoUser, UserValidations validations, AuxiliarAuthService authService, QRGenerator qrGenerator, @Value("${serverUrl}") String serverUrl) {
        this.passwordEncoder = passwordEncoder;
        this.repoUser = repoUser;
        this.validations = validations;
        this.authService = authService;
        this.qrGenerator = qrGenerator;
        this.serverUrl = serverUrl;
    }

    @Transactional
    @Override
    public void createUser(UserRequestDto userRequestDto) throws UserException{
        validations.validateSave(userRequestDto);
        User user = new User();
        qrGenerator.guardarQR(userRequestDto.getEmail().split(".com")[0]);
        String endPointQR = serverUrl + "/qr/get-qr?email=" + userRequestDto.getEmail().split(".com")[0];
        user.setName(userRequestDto.getName());
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        user.setEmail(userRequestDto.getEmail());
        user.setQrCode(endPointQR);
        user.getRoles().add("USER");
        repoUser.save(user);
    }

    @Override
    public UserResponseDto getUser() throws UserException {
        User user = repoUser.findById(authService.getId())
                .orElseThrow(() -> new UserException("User not found",
                        new ExceptionDetails("Usuario no encontrado", "low")));
        return new UserResponseDto(user.getId(), user.getName(), user.getEmail(), user.getQrCode());
    }

    @Transactional
    @Override
    public void updateUser(UserRequestDto userRequestDto) throws UserException{
        validations.validateSave(userRequestDto);
        User user = repoUser.findById(authService.getId())
                .orElseThrow(() -> new UserException("User not found",
                        new ExceptionDetails("El usuario no existe", "low")));
        qrGenerator.deleteQR(authService.getEmail().split(".com")[0]);
        qrGenerator.guardarQR(userRequestDto.getEmail().split(".com")[0]);
        String newQrCode = serverUrl + "/qr/get-qr?email=" + userRequestDto.getEmail().split(".com")[0];
        user.setName(userRequestDto.getName());
        user.setEmail(userRequestDto.getEmail());
        user.setQrCode(newQrCode);
        repoUser.save(user);
    }

    @Transactional
    @Override
    public void deleteUser() {
        validations.existEmail(authService.getEmail());
        qrGenerator.deleteQR(authService.getEmail().split(".com")[0]);
        repoUser.deleteById(authService.getId());
    }

    @Override
    public List<PredictedUserProjection> predictionUsers(String searchingText, int limit) {
        List<PredictedUserProjection> predictedUsers = repoUser.findAllUsersLikeByEmailOrName(searchingText, limit);
        validations.validateUsersReturn(predictedUsers, "Usuario no encontrado");
        return predictedUsers;
    }
}
