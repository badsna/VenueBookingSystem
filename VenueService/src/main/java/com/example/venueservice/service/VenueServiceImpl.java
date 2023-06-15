package com.example.venueservice.service;


import com.example.venueservice.customexception.ResourceAlreadyExistsException;
import com.example.venueservice.customexception.ResourceNotFoundException;
import com.example.venueservice.dto.*;
import com.example.venueservice.enums.VenueStatus;
import com.example.venueservice.model.Address;
import com.example.venueservice.model.Contact;
import com.example.venueservice.model.Venue;
import com.example.venueservice.model.VenueImage;
import com.example.venueservice.repo.VenueImageRepo;
import com.example.venueservice.repo.VenueRepo;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VenueServiceImpl implements VenueService {
    private final VenueRepo venueRepo;
    private final VenueImageRepo venueImageRepo;

    public VenueResponseDto convertToVenueResponseDto(Venue venue) {
        VenueResponseDto venueResponseDto = new VenueResponseDto();
        venueResponseDto.setVenueName(venue.getVenueName());
        venueResponseDto.setCapacity(venue.getCapacity());
        venueResponseDto.setPrice(venue.getPrice());
        venueResponseDto.setAvailability(venue.getAvailability());
        venueResponseDto.setAmenities(venue.getAmenities());
        venueResponseDto.setEvents(venue.getEvents());
        venueResponseDto.setVenueOwnerEmail(venue.getVenueOwnerEmail());

        Address address = venue.getAddress();

        AddressDto addressDto = new AddressDto();
        addressDto.setProvince(address.getProvince());
        addressDto.setCity(address.getCity());
        addressDto.setArea(address.getArea());
        venueResponseDto.setAddress(addressDto);

        List<Contact> contactList = venue.getContacts();
        List<ContactDto> contactDtoList = new ArrayList<>();
        for (Contact contact : contactList) {
            ContactDto contactDto = new ContactDto();
            contactDto.setContactNumber(contact.getContactNumber());
            contactDto.setEmail(contact.getEmail());
            contactDtoList.add(contactDto);
        }

        venueResponseDto.setContacts(contactDtoList);

        List<VenueImage> venueImages = venue.getVenueImages();
        List<ImageDto> imageDtoList = new ArrayList<>();
        for (VenueImage venueImage : venueImages) {
            ImageDto imageDto = new ImageDto();

            int index = venueImage.getImagesUrl().indexOf("static\\");
            String finalPath = venueImage.getImagesUrl().substring(index + 7);
            String prefix = "http://localhost:8080/";
            String urlString = prefix + finalPath.replace("\\", "/");

            imageDto.setImageId(venueImage.getImageId());
            imageDto.setImagesUrl(urlString);

            imageDtoList.add(imageDto);
        }
        venueResponseDto.setVenueImages(imageDtoList);
        return venueResponseDto;
    }

    public List<VenueImage> saveVenueImage(List<MultipartFile> venueImages, String relativePath) throws IOException {

        //returns the current working directory of the user
        String absolutePath = System.getProperty("user.dir") + File.separator + relativePath;

        File fileFolder = new File(absolutePath);
        if (!fileFolder.exists()) {
            fileFolder.mkdirs();
        }

        List<VenueImage> venueImageList = new ArrayList<>();

        for (MultipartFile multipartFile : venueImages) {
            VenueImage venueImage = new VenueImage();

            java.nio.file.Path destinationPath = Paths.get(absolutePath, multipartFile.getOriginalFilename());
            Files.copy(multipartFile.getInputStream(), destinationPath);
            venueImage.setImagesUrl(relativePath + File.separator + multipartFile.getOriginalFilename());

            venueImageList.add(venueImage);

        }
        return venueImageList;

    }

    @Override
    public void addNewVenue(VenueRequestDto venueRequestDto, List<MultipartFile> venueImages, String path) throws IOException {
        for (int i = 0; i < venueRequestDto.getContacts().size(); i++) {
            Venue existingvenue = venueRepo.findByContacts_ContactNumber(venueRequestDto.getContacts().get(i).getContactNumber());
            if (existingvenue != null) {
                throw new ResourceAlreadyExistsException("Venue already exists " + venueRequestDto.getContacts().get(i).getContactNumber());
            }
        }
        Venue venue = new Venue();
        venue.setVenueName(venueRequestDto.getVenueName());
        venue.setCapacity(venueRequestDto.getCapacity());
        venue.setPrice(venueRequestDto.getPrice());
        venue.setAvailability(VenueStatus.AVAILABLE);
        venue.setAddress(venueRequestDto.getAddress());
        venue.setAmenities(venueRequestDto.getAmenities());
        venue.setEvents(venueRequestDto.getEvents());
        venue.setContacts(venueRequestDto.getContacts());
        venue.setCreatedBy(venueRequestDto.getCreatedBy());
        venue.setVenueOwnerEmail(venueRequestDto.getVenueOwnerEmail());
        String relativePath = path + "images\\" + venueRequestDto.getVenueName().replaceAll("\\s+", "");

        List<VenueImage> venueImage = saveVenueImage(venueImages,relativePath);
        venue.setVenueImages(venueImage);

        venueRepo.save(venue);
    }


    public List<VenueResponseDto> getVenue(String area, Integer minimumCapacity, Double maximumPrice, String events, String venueName, Pageable pageable) {

        List<Venue> venueList = venueRepo.findAll((Specification<Venue>) (root, query, criteriaBuilder) -> {

            Predicate predicate = criteriaBuilder.conjunction();
            // Navigate to the "area" field within the "address" entity
            Path<String> areaPath = root.get("address").get("area");

            if (!StringUtils.isEmpty(area)) {
                String lowercaseArea = area.toLowerCase();
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(criteriaBuilder.lower(areaPath), lowercaseArea));
            }

            if (!StringUtils.isEmpty(minimumCapacity)) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("capacity"), minimumCapacity));
            }
            if (!StringUtils.isEmpty(maximumPrice)) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("price"), maximumPrice));
            }

            if (!StringUtils.isEmpty(events)) {
                String lowercaseEvent = events.toLowerCase();
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.join("events")), "%" + lowercaseEvent + "%"));
            }
            if (!StringUtils.isEmpty(venueName)) {
                String lowercaseVenueName = venueName.toLowerCase();
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("venueName")), "%" + lowercaseVenueName + "%"));
            }


            query.orderBy(criteriaBuilder.desc(root.join("address").get("area")));
            return predicate;
        }, pageable).getContent();

        if (venueList.isEmpty()) {
            throw new ResourceNotFoundException("We dont have venue as per your request");
        }
        List<VenueResponseDto> venueResponseDtoList = new ArrayList<>();
        for (Venue venue : venueList) {
            VenueResponseDto venueResponseDto = convertToVenueResponseDto(venue);
            venueResponseDtoList.add(venueResponseDto);
        }
        return venueResponseDtoList;
    }

    public VenueResponseDto getVenueByVenueId(Long venueId) {
        Venue venue = venueRepo.findById(venueId).orElseThrow(()->new ResourceNotFoundException("Venue With "+ venueId+" Doesn't Exists"));
        return convertToVenueResponseDto(venue);

    }

    public String getVenueByVenueOwnerEmail(String venueOwnerEmail) {
        Optional<Venue> venue = venueRepo.findByVenueOwnerEmail(venueOwnerEmail);
        if (venue.isPresent()) {
            return venue.get().getVenueOwnerEmail();
        }
        return "venue";
    }

    public void updateVenueInformation(Long venueId, VenueRequestDto venueRequestDto, List<MultipartFile> venueImages, String path, String userName) throws IOException {
        Venue venue = venueRepo.findById(venueId).orElseThrow(() -> new ResourceNotFoundException("Venue With "+ venueId+" Doesn't Exists"));

        if (venue.getVenueOwnerEmail().equals(userName)) {
            String relativePath = path + "images\\" + venue.getVenueName().replaceAll("\\s+", "");
            List<VenueImage> venueImageList = saveVenueImage(venueImages, relativePath);

            List<VenueImage> existingvenueImageList = venue.getVenueImages();
            existingvenueImageList.addAll(venueImageList);

            venue.setVenueImages(existingvenueImageList);

            venue.setVenueName(venueRequestDto.getVenueName());
            venue.setCapacity(venueRequestDto.getCapacity());
            venue.setPrice(venueRequestDto.getPrice());

            Address address = venue.getAddress();
            address.setArea(venueRequestDto.getAddress().getArea());
            address.setCity(venueRequestDto.getAddress().getCity());
            address.setProvince(venueRequestDto.getAddress().getProvince());

            venue.setAddress(address);
            venue.setAmenities(venueRequestDto.getAmenities());
            venue.setEvents(venueRequestDto.getEvents());
            venue.setVenueOwnerEmail(venueRequestDto.getVenueOwnerEmail());

            List<Contact> contactList = venueRequestDto.getContacts();
            List<Contact> existingContactList = venue.getContacts();

            for (int i = 0; i < existingContactList.size(); i++) {
                for (int j = 0; j < contactList.size(); j++) {
                    if (i == j) {
                        existingContactList.get(i).setContactNumber(contactList.get(i).getContactNumber());
                        existingContactList.get(i).setEmail(contactList.get(i).getEmail());
                    }
                }
            }

            venue.setContacts(existingContactList);

            venueRepo.save(venue);
        } else {
            throw new AccessDeniedException("Have No Access");
        }

    }

    public void updateVenueStatus(Long venueId) {
        Venue venue = venueRepo.findById(venueId).orElseThrow(() -> new ResourceNotFoundException("Venue Not found"));
        venue.setAvailability(VenueStatus.BOOKED);
        venueRepo.save(venue);
    }

    public void updateVenueImage(Long imageId, MultipartFile venueImage, String path) {
        VenueImage newVenueImage = new VenueImage();
        try {
            VenueImage existingvenueImage = venueImageRepo.findById(imageId).get();
            Venue venue = venueRepo.findById(existingvenueImage.getVenueId()).get();

            if (!venueImage.isEmpty()) {
                //filework
                //rewrite file
                //delete old pic
                String relPath = path + "images\\" + venue.getVenueName().replaceAll("\\s+", "");
                String absPath = System.getProperty("user.dir") + File.separator + relPath;
                File file = new File(existingvenueImage.getImagesUrl());
                System.out.println("hi>>>>>>>>>>>>>>>>>>." + file);
                file.delete();


                //update new pic
                String relativePath = path + "images\\" + venue.getVenueName().replaceAll("\\s+", "");
                String absolutePath = System.getProperty("user.dir") + File.separator + relativePath;

                File fileFolder = new File(absolutePath);
                if (!fileFolder.exists()) {
                    fileFolder.mkdirs();
                }

                java.nio.file.Path destinationPath = Paths.get(absolutePath, venueImage.getOriginalFilename());
                Files.copy(venueImage.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("helo>>>>>>>>>>>>>>." + absolutePath + File.separator + venueImage.getOriginalFilename());
                newVenueImage.setImagesUrl(absolutePath + File.separator + venueImage.getOriginalFilename());

            } else {
                newVenueImage.setImagesUrl(existingvenueImage.getImagesUrl());
            }
            ImageDto imageDto=new ImageDto();
            imageDto.setImagesUrl(newVenueImage.getImagesUrl());
            //venue.setVenueImages(imageDto.getImagesUrl());

            venueRepo.save(venue);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
