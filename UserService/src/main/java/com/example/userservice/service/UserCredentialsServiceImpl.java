package com.example.userservice.service;

import com.example.userservice.config.JwtService;
import com.example.userservice.customerexception.PasswordNotMatched;
import com.example.userservice.customerexception.UserAlreadyExistsException;
import com.example.userservice.dto.UserCredentialsRequestDto;
import com.example.userservice.enums.Role;
import com.example.userservice.externalservice.EmailSenderService;
import com.example.userservice.externalservice.VenueService;
import com.example.userservice.model.EmailSender;
import com.example.userservice.model.UserCredentials;
import com.example.userservice.repo.UserCredentialsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserCredentialsServiceImpl implements UserCredentialsService {
    private final UserCredentialsRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final VenueService venueService;
    private final EmailSenderService emailSenderService;

    private UserCredentials userDetails(UserCredentialsRequestDto userRequestDto){
        Optional<UserCredentials> userCredentials=userRepo.findByEmail(userRequestDto.getEmail());
        if(userCredentials.isPresent()){
           throw new UserAlreadyExistsException("User with "+userRequestDto.getEmail()+" already exists");
        }

        UserCredentials user=new UserCredentials();
        user.setFirstName(userRequestDto.getFirstName());
        user.setMiddleName(userRequestDto.getMiddleName());
        user.setLastName(userRequestDto.getLastName());
        user.setAddress(userRequestDto.getAddress());
        user.setEmail(userRequestDto.getEmail());
        user.setPhone(userRequestDto.getPhone());
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        user.setRePassword(passwordEncoder.encode(userRequestDto.getRePassword()));

        if (!userRequestDto.getPassword().equals(userRequestDto.getRePassword())){
            throw new PasswordNotMatched("Password doesn't matched with rePassword");
        }

        return user;
    }

    public void sendMail(String userEmail){
        EmailSender emailSender=new EmailSender();
        emailSender.setToEmail(userEmail);
        emailSender.setBody("You can now access Venue Booking System");
        emailSender.setSubject("Welcome to Venue Booking System");
        emailSenderService.sendEmail(emailSender);
    }
    public void addNewUser(UserCredentialsRequestDto userRequestDto) {
        UserCredentials user=userDetails(userRequestDto);
        String venueOwnerEmail=venueService.getVenueByVenueOwnerEmail(userRequestDto.getEmail());
        if(venueOwnerEmail.equals(userRequestDto.getEmail())){
            user.setRole(Role.VENUE_OWNER);
        }
        else{
            user.setRole(Role.USER);
        }
        sendMail(userRequestDto.getEmail());
        userRepo.save(user);
    }

    public void addNewAdmin(UserCredentialsRequestDto userRequestDto) {

        UserCredentials user=userDetails(userRequestDto);
        user.setRole(Role.ADMIN);
        sendMail(userRequestDto.getEmail());
        userRepo.save(user);
    }

    public void validateToken(String token){
        jwtService.validateToken(token);
    }
}
