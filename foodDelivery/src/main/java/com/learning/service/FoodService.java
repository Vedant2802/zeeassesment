package com.learning.service;

import com.learning.entity.FoodItems;
import com.learning.exception.AlreadyExistsException;
import com.learning.exception.FoodTypeNotFoundException;
import com.learning.exception.IdNotFoundException;

public interface FoodService {

	FoodItems addFoodItem(FoodItems foodItems) throws AlreadyExistsException;

	FoodItems updateFoodItem(FoodItems foodItems) throws IdNotFoundException;

	FoodItems getFoodItemById(int foodId) throws IdNotFoundException;

	String deleteFoodItemById(int foodId) throws IdNotFoundException;

	FoodItems[] getAllFoodItems();

	FoodItems[] getAllFoodsByFoodType(String foodType) throws FoodTypeNotFoundException;

}