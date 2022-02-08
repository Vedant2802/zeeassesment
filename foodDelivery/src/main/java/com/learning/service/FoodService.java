//user service interface

package com.learning.service;

import java.util.List;
import java.util.Optional;

import com.learning.entity.FoodItems;
import com.learning.exception.IdNotFoundException;

public interface FoodService {

	public FoodItems add(FoodItems foodItems) throws IdNotFoundException;

	public FoodItems update(int id, FoodItems foodItems) throws IdNotFoundException;

	public FoodItems getFoodById(int id) throws IdNotFoundException;

	public FoodItems[] getAllFoods();

	public String deleteFoodById(int id) throws IdNotFoundException;

	public Optional<List<FoodItems>> getAllFoodDetails();

}