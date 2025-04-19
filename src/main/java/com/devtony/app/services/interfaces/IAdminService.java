package com.devtony.app.services.interfaces;

import com.devtony.app.dto.admin.*;
import com.devtony.app.repository.projections.UserResponse;

import java.util.List;

public interface IAdminService {
    public UserAdminResponseDto changeUserProfile(UserAdminRequestDto userRequestDto);
    public List<UserAdminResponseDto> getUsers();
    public void deleteUser(Long id);
    public EventAdminResponseDto changeEventDetails(EventAdminRequestDto eventRequestDto);
    public List<EventAdminResponseDto> getEvents();
    public void deleteEvent(Long id);
}