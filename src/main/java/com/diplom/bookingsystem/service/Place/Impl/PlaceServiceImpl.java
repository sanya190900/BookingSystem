package com.diplom.bookingsystem.service.Place.Impl;

import com.diplom.bookingsystem.dto.Place.GalleryDto;
import com.diplom.bookingsystem.dto.Place.PlaceDto;
import com.diplom.bookingsystem.exceptions.PlaceNotFoundException;
import com.diplom.bookingsystem.model.Place.EService;
import com.diplom.bookingsystem.model.Place.Gallery;
import com.diplom.bookingsystem.model.Place.Place;
import com.diplom.bookingsystem.model.Address;
import com.diplom.bookingsystem.model.User.User;
import com.diplom.bookingsystem.repository.GalleryRepository;
import com.diplom.bookingsystem.repository.PlaceRepository;
import com.diplom.bookingsystem.repository.ServiceRepository;
import com.diplom.bookingsystem.repository.UserRepository;
import com.diplom.bookingsystem.service.Place.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.diplom.bookingsystem.model.Place.Service;

import java.util.HashSet;
import java.util.Set;

@org.springframework.stereotype.Service
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    GalleryRepository galleryRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseEntity<?> getPlace(Long id) {
        Place place = placeRepository
                .findById(id)
                .orElseThrow(() -> new PlaceNotFoundException("Place not found, id: " + id));

        return new ResponseEntity<>(place, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> createPlace(PlaceDto placeDto) {
        if (placeRepository.existsByName(placeDto.getName())) {
            return new ResponseEntity<>("Name is already exist.", HttpStatus.BAD_REQUEST);
        }

        Place place = placeRepository.save(makePlace(placeDto));

        Set<Gallery> gallery = new HashSet<>();
        placeDto.getPathsToPhotos().forEach(path ->
            gallery.add(new Gallery(place, path)));
        place.setGallery(gallery);

        return new ResponseEntity<>(placeRepository.save(place), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> updatePlace(PlaceDto placeDto) {
        Place place = placeRepository
                .findById(placeDto.getPlace_id())
                .orElseThrow(() -> new PlaceNotFoundException("Place not found, id: " + placeDto.getPlace_id()));

        if (placeRepository.existsByName(placeDto.getName()) && !placeDto.getName().equals(place.getName())) {
            return new ResponseEntity<>("Name is already exist.", HttpStatus.BAD_REQUEST);
        }

        Address address = placeDto.getAddress();
        address.setAddress_id(place.getAddress().getAddress_id());
        placeDto.setAddress(address);
        Place placeNew = placeRepository.save(makePlace(placeDto));

        galleryRepository.deleteByPlace(placeNew);

        Set<Gallery> gallery = new HashSet<>();
        placeDto.getPathsToPhotos().forEach(path ->
                gallery.add(new Gallery(placeNew, path)));
        placeNew.setGallery(gallery);

        return new ResponseEntity<>(placeRepository.save(placeNew), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deletePlace(Long id) {
        placeRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Place makePlace(PlaceDto placeDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found: " + auth.getName()));

        Place place = new Place(
                placeDto.getAddress(),
                placeDto.getName(),
                placeDto.getDescription()
        );

        if (placeDto.getPlace_id() != null)
            place.setPlace_id(placeDto.getPlace_id());
        else
            place.setUser(user);

        Set<String> strServices = placeDto.getService();
        Set<Service> services = new HashSet<>();

        strServices.forEach(service -> {
            switch (service) {
                case "wifi":
                    Service wifiService = serviceRepository.findByService(EService.SERVICE_WIFI)
                            .orElseThrow(() -> new RuntimeException("Service is not found."));
                    services.add(wifiService);
                    break;
                case "tv":
                    Service tvService = serviceRepository.findByService(EService.SERVICE_TV)
                            .orElseThrow(() -> new RuntimeException("Service is not found."));
                    services.add(tvService);
                    break;
                case "pool":
                    Service poolService = serviceRepository.findByService(EService.SERVICE_POOL)
                            .orElseThrow(() -> new RuntimeException("Service is not found."));
                    services.add(poolService);
                    break;
                case "bar":
                    Service barService = serviceRepository.findByService(EService.SERVICE_BAR)
                            .orElseThrow(() -> new RuntimeException("Service is not found."));
                    services.add(barService);
                    break;
                case "shower":
                    Service showerService = serviceRepository.findByService(EService.SERVICE_SHOWER)
                            .orElseThrow(() -> new RuntimeException("Service is not found."));
                    services.add(showerService);
                    break;
                case "transfer":
                    Service transferService = serviceRepository.findByService(EService.SERVICE_TRANSFER)
                            .orElseThrow(() -> new RuntimeException("Service is not found."));
                    services.add(transferService);
                    break;
                case "breakfast":
                    Service breakfastService = serviceRepository.findByService(EService.SERVICE_BREAKFAST)
                            .orElseThrow(() -> new RuntimeException("Service is not found."));
                    services.add(breakfastService);
                    break;
                case "fitness":
                    Service fitnessService = serviceRepository.findByService(EService.SERVICE_FITNESS)
                            .orElseThrow(() -> new RuntimeException("Service is not found."));
                    services.add(fitnessService);
                    break;
                case "parking":
                    Service parkingService = serviceRepository.findByService(EService.SERVICE_PARKING)
                            .orElseThrow(() -> new RuntimeException("Service is not found."));
                    services.add(parkingService);
                    break;
                case "animals":
                    Service animalsService = serviceRepository.findByService(EService.SERVICE_ANIMALS)
                            .orElseThrow(() -> new RuntimeException("Service is not found."));
                    services.add(animalsService);
                    break;
                case "excursion":
                    Service excursionService = serviceRepository.findByService(EService.SERVICE_EXCURSION)
                            .orElseThrow(() -> new RuntimeException("Service is not found."));
                    services.add(excursionService);
                    break;
                default:
                    throw new RuntimeException("Service is not found.");
            }
        });

        place.setServices(services);

        return place;
    }
}
