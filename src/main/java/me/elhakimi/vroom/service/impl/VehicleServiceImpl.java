package me.elhakimi.vroom.service.impl;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import me.elhakimi.vroom.aws.service.StorageService;
import me.elhakimi.vroom.domain.AppUser;
import me.elhakimi.vroom.domain.Vehicle;
import me.elhakimi.vroom.domain.VehicleImages;
import me.elhakimi.vroom.domain.enums.VehicleStatus;
import me.elhakimi.vroom.repository.VehicleRepository;
import me.elhakimi.vroom.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

@Service
@AllArgsConstructor
public class VehicleServiceImpl {

    private final VehicleRepository vehicleRepository;
    private final UserService userService;
    private final VehicleImagesServiceImpl vehicleImagesServiceImpl;
    private final StorageService storageService;

    @Transactional
    public Vehicle save(Vehicle vehicle, MultipartFile[] images) {

        if (images == null || images.length == 0) {
            throw new IllegalArgumentException("Vehicle must have at least one image.");
        }

        AppUser appUser = getAuthenticatedUser();

        vehicle.setUser(appUser);
        vehicle.setCreatedAt(LocalDateTime.now());
        vehicle.setStatus(VehicleStatus.PENDING);

        if (vehicle.getVehicleImages() == null) {
            vehicle.setVehicleImages(new ArrayList<>());
        }

        vehicle = vehicleRepository.save(vehicle);

        for (MultipartFile image : images) {
            validateImage(image);
            isValidImageType(Objects.requireNonNull(image.getContentType()));

            String imageUrl = storageService.uploadFile(image, appUser.getUsername());
            if (imageUrl == null || imageUrl.isEmpty()) {
                throw new RuntimeException("Failed to upload image.");
            }

            VehicleImages vehicleImage = new VehicleImages();
            vehicleImage.setVehicle(vehicle);
            vehicleImage.setImage_url(imageUrl);
            vehicleImage.setCreated_at(LocalDateTime.now());

            vehicleImagesServiceImpl.save(vehicleImage);
            vehicle.getVehicleImages().add(vehicleImage);
        }

        return vehicle;
    }

    private AppUser getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("User is not authenticated.");
        }
        return (AppUser) authentication.getPrincipal();
    }

    private void validateImage(MultipartFile image) {
        if (image.getSize() > 5 * 1024 * 1024) {
            throw new IllegalArgumentException("Image size exceeds the maximum allowed size of 5MB.");
        }

        String contentType = image.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Only image files are allowed.");
        }
    }

    private boolean isValidImageType(String contentType) {
        return contentType.equals("image/jpeg") ||
                contentType.equals("image/png") ||
                contentType.equals("image/gif");
    }

    public Vehicle update(Vehicle vehicle){

        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(vehicle.getUser() != appUser) throw new RuntimeException("You are not allowed to update this vehicle");

        vehicle.setUpdatedAt(LocalDateTime.now());
        vehicle.setStatus(VehicleStatus.PENDING);

        return vehicleRepository.save(vehicle);

    }

    public void archive(Long id){
        Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(() -> new RuntimeException("Vehicle not found"));
        vehicle.setArchived(true);
        vehicleRepository.save(vehicle);
    }

    public void delete(Long id){
        Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(() -> new RuntimeException("Vehicle not found"));
        vehicleRepository.delete(vehicle);
    }

    public Vehicle approve(Long id){
        Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(() -> new RuntimeException("Vehicle not found"));
        vehicle.setStatus(VehicleStatus.PUBLISHED);
        return vehicleRepository.save(vehicle);
    }

    public Vehicle reject(Long id){
        Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(() -> new RuntimeException("Vehicle not found"));
        vehicle.setStatus(VehicleStatus.REJECTED);
        return vehicleRepository.save(vehicle);
    }

    public Vehicle findById(Long id){
        return vehicleRepository.findByIdWithImages(id).orElseThrow(() -> new RuntimeException("Vehicle not found"));
    }

    public Page<Vehicle> findAll(Pageable pageable) {

        return vehicleRepository.findAllWithImages(pageable);

    }

}
