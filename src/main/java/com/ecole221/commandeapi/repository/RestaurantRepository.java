package com.ecole221.commandeapi.repository;

import com.ecole221.commandeapi.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {

}
