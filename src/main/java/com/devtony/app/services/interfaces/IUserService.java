package com.devtony.app.services.interfaces;

import com.devtony.app.dto.user.UserRequestDto;
import com.devtony.app.dto.user.UserResponseDto;
import com.devtony.app.repository.projections.PredictedUserProjection;

import java.util.List;

public interface IUserService {
    //CRUD
    //Create
    public void createUser(UserRequestDto userRequestDto);

    //Read
    public UserResponseDto getUser();

    //Update
    public void updateUser(UserRequestDto userRequestDto);

    //Delete
    public void deleteUser();

    public List<PredictedUserProjection> predictionUsers(String searchingText, int limit);
}