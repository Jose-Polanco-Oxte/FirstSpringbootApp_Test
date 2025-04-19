package com.devtony.app.services;

import com.devtony.app.dto.admin.EventAdminRequestDto;
import com.devtony.app.dto.admin.EventAdminResponseDto;
import com.devtony.app.dto.admin.UserAdminRequestDto;
import com.devtony.app.dto.admin.UserAdminResponseDto;
import com.devtony.app.dto.user.UserResponseDto;
import com.devtony.app.exception.AdminException;
import com.devtony.app.exception.ExceptionDetails;
import com.devtony.app.exception.UserException;
import com.devtony.app.model.User;
import com.devtony.app.repository.IEventRepository;
import com.devtony.app.repository.IUserRepository;
import com.devtony.app.repository.projections.UserResponse;
import com.devtony.app.services.interfaces.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService implements IAdminService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public UserAdminResponseDto changeUserProfile(UserAdminRequestDto userRequestDto) {
        User user = userRepository.findByEmail(userRequestDto.getEmail())
                .orElseThrow(() -> new AdminException("User not found",
                        new ExceptionDetails("El usuario no existe", "low")));

        user.setName(userRequestDto.getName());
        user.setEmail(userRequestDto.getEmail());
        for (String role : userRequestDto.getRoles()) {
            user.getRoles().add(role);
        }
        userRepository.save(user);
        return new UserAdminResponseDto(user.getId(), user.getName(), user.getEmail(), user.getQrCode(), user.getRoles());
    }

    @Override
    public List<UserAdminResponseDto> getUsers() {
        if (userRepository.count() == 0) {
            throw new UserException("Users not found",
                    new ExceptionDetails("No se encontraron usuarios", "low"));
        }
        return userRepository.findAll().stream().map(user -> new UserAdminResponseDto(user.getId(), user.getName(), user.getEmail(), user.getQrCode(), user.getRoles())).toList();
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserException("User not found",
                    new ExceptionDetails("El usuario no existe", "low"));
        }
        userRepository.deleteById(id);
    }

    @Override
    public EventAdminResponseDto changeEventDetails(EventAdminRequestDto eventRequestDto) {
        return null;
    }

    @Override
    public List<EventAdminResponseDto> getEvents() {
        return List.of();
    }

    @Override
    public void deleteEvent(Long id) {
    }
}
