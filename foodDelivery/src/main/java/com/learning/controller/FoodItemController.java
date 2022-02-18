package com.learning.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.entity.FoodItems;
import com.learning.exception.AlreadyExistsException;
import com.learning.exception.FoodTypeNotFoundException;
import com.learning.exception.IdNotFoundException;
import com.learning.service.FoodService;

@RestController
@RequestMapping("/api/food")
public class FoodItemController {

	@Autowired
	FoodService foodService;

	@PostMapping
	@PreAuthorize("hasRole('ADMIN') ")
	public ResponseEntity<?> addFoodItem(@Valid @RequestBody FoodItems foodItems) throws AlreadyExistsException {
		FoodItems result = foodService.addFoodItem(foodItems);
		System.out.println(result);
		return ResponseEntity.status(201).body(result);
	}

	@GetMapping("/{foodId}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER') ")
	public ResponseEntity<?> getFoodItemById(@PathVariable("foodId") int foodId) throws IdNotFoundException {
		FoodItems foodItems = foodService.getFoodItemById(foodId);
		return ResponseEntity.status(200).body(foodItems);
	}

	@PutMapping("/update/food")
	@PreAuthorize("hasRole('ADMIN') ")
	public ResponseEntity<?> updateFoodItemById(@RequestBody FoodItems foodItems) throws IdNotFoundException {
		FoodItems result = foodService.updateFoodItem(foodItems);
		return ResponseEntity.status(200).body(result);
	}

	@GetMapping("/foods")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER') ")
	public ResponseEntity<?> getAllFoodItems() {

		FoodItems[] foodItems = foodService.getAllFoodItems();
		return ResponseEntity.status(200).body(foodItems);
	}

	@GetMapping("/foodType/{foodType}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER') ")
	public ResponseEntity<?> getFoodByFoodType(@PathVariable("foodType") String foodType)
			throws FoodTypeNotFoundException {
		FoodItems[] foodItems = foodService.getAllFoodsByFoodType(foodType);
		return ResponseEntity.status(200).body(foodItems);
	}

	@DeleteMapping("/delete/{foodId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteUserById(@PathVariable("foodId") int foodId) throws IdNotFoundException {
		String result = foodService.deleteFoodItemById(foodId);
		Map<String, String> map = new HashMap<String, String>();
		map.put("message", result);
		return ResponseEntity.status(200).body(map);
	}

}
