package com.learning.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.entity.FoodItems;
import com.learning.exception.AlreadyExistsException;
import com.learning.exception.FoodTypeNotFoundException;
import com.learning.exception.IdNotFoundException;
import com.learning.repo.FoodItemsRepo;
import com.learning.service.FoodService;

@Service
public class FoodServiceImpl implements FoodService {

	@Autowired
	private FoodItemsRepo foodItemsRepo;

	@Override
	public FoodItems addFoodItem(FoodItems foodItems) throws AlreadyExistsException {
		boolean status = foodItemsRepo.existsByFoodId(foodItems.getFoodId());
		if (status) {
			throw new AlreadyExistsException("Food Item Already Exist!");
		}
		FoodItems food2 = foodItemsRepo.save(foodItems);
		return food2;
	}

	@Override
	public FoodItems updateFoodItem(FoodItems foodItems) throws IdNotFoundException {
		if (!foodItemsRepo.existsById(foodItems.getFoodId())) {
			throw new IdNotFoundException("Sorry Food Not Found");
		}
		return foodItemsRepo.save(foodItems);
	}

	@Override
	public FoodItems getFoodItemById(int foodId) throws IdNotFoundException {
		Optional<FoodItems> optional = foodItemsRepo.findById(foodId);
		if (optional.isEmpty()) {
			throw new IdNotFoundException("Sorry Food Not Found");
		} else {
			return optional.get();
		}
	}

	@Override
	public FoodItems[] getAllFoodItems() {
		List<FoodItems> list = foodItemsRepo.findAll();
		FoodItems[] array = new FoodItems[list.size()];
		return list.toArray(array);
	}

	@SuppressWarnings("null")
	@Override
	public FoodItems[] getAllFoodsByFoodType(String foodType) throws FoodTypeNotFoundException {
		List<FoodItems> foodItems = foodItemsRepo.findAll();
		List<FoodItems> temp = new ArrayList<FoodItems>();
		for (FoodItems foodItems2 : foodItems) {
			if (foodItems2.getFoodType().toString().equals(foodType)) {
				temp.add(foodItems2);
			}
		}
		if (temp.isEmpty()) {
			throw new FoodTypeNotFoundException("Sorry Food Type Not Found");
		}
		FoodItems[] array = new FoodItems[temp.size()];
		return temp.toArray(array);
	}

	@Override
	public String deleteFoodItemById(int foodId) throws IdNotFoundException {

		if (!foodItemsRepo.existsById(foodId)) {
			throw new IdNotFoundException("sorry user with id " + foodId + " not found");
		}
		foodItemsRepo.deleteById(foodId);
		return "Food Successfully deleted";

	}
}