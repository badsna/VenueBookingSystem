package com.example.venueservice.service;

import com.example.venueservice.dto.VenueRequestDto;
import com.example.venueservice.dto.VenueResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface VenueService {
    void addNewVenue(VenueRequestDto venueRequestDto, List<MultipartFile> venueImage,String path) throws IOException;

  List<VenueResponseDto> getVenue(String area, Integer capacity, Double maximumPrice, String event, String venueName,Pageable pageable);

    public VenueResponseDto getVenueByVenueId(Long venueId);


  String getVenueByVenueOwnerEmail(String venueOwnerEmail);

    public void updateVenueInformation(Long venueId,VenueRequestDto venueRequestDto,List<MultipartFile> venueImages,String path,String username) throws IOException;
    void updateVenueStatus(Long venueId);

  public void updateVenueImage(Long imageId, MultipartFile venueImage, String path);

  }
