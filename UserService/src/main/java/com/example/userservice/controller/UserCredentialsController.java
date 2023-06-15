package com.example.userservice.controller;

import com.example.userservice.dto.UserCredentialsRequestDto;
import com.example.userservice.service.UserCredentialsServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Validated
public class UserCredentialsController {
    private final UserCredentialsServiceImpl userService;

    @PostMapping("/add_new_user")
    public ResponseEntity<String> addNewUser( @RequestBody @Valid UserCredentialsRequestDto userRequestDto) {
        userService.addNewUser(userRequestDto);
        return ResponseEntity.ok("New User Added Successfully");
    }

    @PostMapping("/add_new_admin")
    public ResponseEntity<String> addNewAdmin(@RequestBody UserCredentialsRequestDto userRequestDto) {
        userService.addNewAdmin(userRequestDto);
        return ResponseEntity.ok("New Admin Added Successfully");
    }


}
