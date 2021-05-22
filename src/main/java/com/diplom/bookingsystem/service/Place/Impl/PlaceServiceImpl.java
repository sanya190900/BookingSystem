package com.diplom.bookingsystem.service.Place.Impl;

import com.diplom.bookingsystem.dto.Place.PlaceDto;
import com.diplom.bookingsystem.dto.Place.PlacesDto;
import com.diplom.bookingsystem.dto.Place.ScheduleDto;
import com.diplom.bookingsystem.exceptions.PlaceNotFoundException;
import com.diplom.bookingsystem.model.Place.*;
import com.diplom.bookingsystem.model.Address;
import com.diplom.bookingsystem.model.User.User;
import com.diplom.bookingsystem.repository.*;
import com.diplom.bookingsystem.service.Place.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.awt.print.Pageable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    GalleryRepository galleryRepository;

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    DayRepository dayRepository;

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
        place.setSchedule(makeSchedules(placeDto, place));

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
        scheduleRepository.deleteByPlace(placeNew);

        Set<Gallery> gallery = new HashSet<>();
        placeDto.getPathsToPhotos().forEach(path ->
                gallery.add(new Gallery(placeNew, path)));
        placeNew.setGallery(gallery);
        placeNew.setSchedule(makeSchedules(placeDto, placeNew));

        return new ResponseEntity<>(placeRepository.save(placeNew), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deletePlace(Long id) {
        placeRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getPlaces(Integer page, Integer pageSize, String name, String country,
                                       String city, String street, String creatorName, String creatorSurname) {
        Page<Place> placesPage = placeRepository.findAllByNameAndAddress_CountryAndAddress_CityAndAddress_StreetAndUser_NameAndUser_Surname(name, country, city, street, creatorName, creatorSurname, PageRequest.of(page, pageSize));
        System.out.println(placesPage);
        return new ResponseEntity<>(new PlacesDto(placesPage.getContent(), placesPage.getTotalElements()), HttpStatus.OK);
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

        place.setServices(makeServices(placeDto));

        return place;
    }

    private Set<Schedule> makeSchedules (PlaceDto placeDto, Place place) {
        Set<ScheduleDto> schedulesDto = placeDto.getSchedules();

        return schedulesDto
                .stream()
                .map(scheduleDto -> {
                    Schedule schedule = new Schedule(
                            scheduleDto.getStart(),
                            scheduleDto.getStop(),
                            scheduleDto.getPrice(),
                            place);
                    String strDay = scheduleDto.getDay();
                    switch (strDay) {
                        case "monday":
                            schedule.setDay(dayRepository.findByDay(EDay.MONDAY)
                                    .orElseThrow(() -> new RuntimeException("Day is not found.")));
                            break;
                        case "tuesday":
                            schedule.setDay(dayRepository.findByDay(EDay.TUESDAY)
                                    .orElseThrow(() -> new RuntimeException("Day is not found.")));
                            break;
                        case "wednesday":
                            schedule.setDay(dayRepository.findByDay(EDay.WEDNESDAY)
                                    .orElseThrow(() -> new RuntimeException("Day is not found.")));
                            break;
                        case "thursday":
                            schedule.setDay(dayRepository.findByDay(EDay.THURSDAY)
                                    .orElseThrow(() -> new RuntimeException("Day is not found.")));
                            break;
                        case "friday":
                            schedule.setDay(dayRepository.findByDay(EDay.FRIDAY)
                                    .orElseThrow(() -> new RuntimeException("Day is not found.")));
                            break;
                        case "saturday":
                            schedule.setDay(dayRepository.findByDay(EDay.SATURDAY)
                                    .orElseThrow(() -> new RuntimeException("Day is not found.")));
                            break;
                        case "sunday":
                            schedule.setDay(dayRepository.findByDay(EDay.SUNDAY)
                                    .orElseThrow(() -> new RuntimeException("Day is not found.")));
                            break;
                        default:
                            throw new RuntimeException("Day is not found.");
                    }
                    return schedule;
                }). collect(Collectors.toSet());
    }

    private Set<Service> makeServices (PlaceDto placeDto) {
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

        return services;
    }
}
