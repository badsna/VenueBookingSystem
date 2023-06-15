package com.example.userservice.service;

import com.example.userservice.dto.UserCredentialsRequestDto;

public interface UserCredentialsService {
    public void addNewUser(UserCredentialsRequestDto userRequestDto) ;

    public void addNewAdmin(UserCredentialsRequestDto userRequestDto);
//    public void addNewVenueOwner(UserCredentialsRequestDto userRequestDto);
    public void validateToken(String token);
}
