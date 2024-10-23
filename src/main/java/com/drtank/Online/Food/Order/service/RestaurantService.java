package com.drtank.Online.Food.Order.service;

import com.drtank.Online.Food.Order.dto.RestaurantDto;
import com.drtank.Online.Food.Order.model.Address;
import com.drtank.Online.Food.Order.model.Restaurant;
import com.drtank.Online.Food.Order.model.User;
import com.drtank.Online.Food.Order.repository.AddressRepository;
import com.drtank.Online.Food.Order.repository.RestaurantRepository;
import com.drtank.Online.Food.Order.repository.UserRepository;
import com.drtank.Online.Food.Order.request.CreateRestaurantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService implements RestaurantServiceInterface{

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {

        Address address = addressRepository.save(req.getAddress());

        Restaurant restaurant = new Restaurant();

        restaurant.setName(req.getName());
        restaurant.setOwner(user);
        restaurant.setAddress(address);
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setDescription(req.getDescription());
        restaurant.setImages(req.getImages());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setRegistrationDate(LocalDateTime.now());

        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurantReq) throws Exception {

        Restaurant restaurant = findRestaurantById(restaurantId);

        if (updatedRestaurantReq.getName() != null) {
            restaurant.setName(updatedRestaurantReq.getName());
        }

        if (updatedRestaurantReq.getContactInformation() != null) {
            restaurant.setContactInformation(updatedRestaurantReq.getContactInformation());
        }

        if (updatedRestaurantReq.getCuisineType() != null) {
            restaurant.setCuisineType(updatedRestaurantReq.getCuisineType());
        }

        if (updatedRestaurantReq.getDescription() != null) {
            restaurant.setDescription(updatedRestaurantReq.getDescription());
        }

        if (updatedRestaurantReq.getImages() != null && !updatedRestaurantReq.getImages().isEmpty()) {
            restaurant.setImages(updatedRestaurantReq.getImages());
        }

        if (updatedRestaurantReq.getOpeningHours() != null) {
            restaurant.setOpeningHours(updatedRestaurantReq.getOpeningHours());
        }

        if (updatedRestaurantReq.getAddress() != null) {
            Address updatedAddress = addressRepository.save(updatedRestaurantReq.getAddress());
            restaurant.setAddress(updatedAddress);
        }

        return restaurantRepository.save(restaurant);
    }


    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);

        restaurantRepository.delete(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurants(String query) {
        return restaurantRepository.findBySearchQuery(query);
    }

    @Override
    public Restaurant findRestaurantById(Long restaurantId) throws Exception {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);

        if(restaurant.isEmpty()){
            throw new Exception("Restaurant is not found with id: " + restaurantId);
        }

        return restaurant.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {

        Restaurant restaurant = restaurantRepository.findByOwnerId(userId);

        if(restaurant == null){
            throw new Exception("Restaurant not found with Owner Id: " + userId);
        }
        return restaurant;
    }

    @Override
    public RestaurantDto addToFavourites(Long restaurantId, User user) throws Exception {

        Restaurant restaurant = findRestaurantById(restaurantId);

        RestaurantDto restaurantDto = new RestaurantDto();

        restaurantDto.setId(restaurantId);
        restaurantDto.setTitle(restaurant.getName());
        restaurantDto.setDescription(restaurant.getDescription());
        restaurantDto.setImages(restaurant.getImages());

        boolean isFavourite = false;
        List<RestaurantDto> favourites = user.getFavorites();

        for(RestaurantDto favourite : favourites){
            if(favourite.getId().equals(restaurantId)){
                isFavourite = true;
                break;
            }
        }

        if(isFavourite){
            favourites.removeIf(favourite -> favourite.getId().equals(restaurantId));
        }
        else{
            favourites.add(restaurantDto);
        }

        userRepository.save(user);

        return restaurantDto;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long restaurantId) throws Exception {

        Restaurant restaurant = findRestaurantById(restaurantId);

        restaurant.setOpen(!restaurant.isOpen());

        return restaurantRepository.save(restaurant);
    }
}
