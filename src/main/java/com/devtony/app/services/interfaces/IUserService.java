package com.devtony.app.services.interfaces;

import com.devtony.app.dto.user.UserRequestDto;
import com.devtony.app.dto.user.UserResponseDto;
import com.devtony.app.model.User;
import com.devtony.app.repository.projections.UserProjection;

import java.util.List;

public interface IUserService {
    //CRUD
    //Create
    public void saveUser(UserRequestDto userRequestDto);

    //Read
    public UserResponseDto getUser(Long id);

    //Update
    public void updateUser(UserRequestDto userRequestDto);

    //Delete
    public void deleteUser(Long id);

    //EXTRA
    //Read all
    public List<UserProjection> getUsers();

    //Test
}