package com.learning.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.entity.FoodItems;
import com.learning.exception.IdNotFoundException;
import com.learning.repo.FoodItemsRepo;
import com.learning.service.FoodService;

@Service // using this we get the singleton object
public class FoodServiceImpl implements FoodService {

	@Autowired
	private FoodItemsRepo foodItemsRepo;

	@Override
	public FoodItems add(FoodItems foodItems) throws IdNotFoundException {
		boolean status = foodItemsRepo.existsByFoodId(foodItems.getFoodId());
		if (status) {
			throw new IdNotFoundException("Sorry food not found");
		}
		FoodItems food2 = foodItemsRepo.save(foodItems);
		return food2;
	}

//update
	@Override
	public FoodItems update(int id, FoodItems foodItems) throws IdNotFoundException {
		return foodItemsRepo.save(foodItems);
	}

//get food by id
	@Override
	public FoodItems getFoodById(int id) throws IdNotFoundException {
		Optional<FoodItems> optional = foodItemsRepo.findById(id);
		if (optional.isEmpty()) {
			throw new IdNotFoundException("sorry food not found");
		} else {
			return optional.get();
		}
	}

// get all foods
	@Override
	public FoodItems[] getAllFoods() {
		List<FoodItems> list = foodItemsRepo.findAll();
		FoodItems[] array = new FoodItems[list.size()];
		return list.toArray(array);
	}

//delete food by id
	@Override
	public String deleteFoodById(int id) throws IdNotFoundException {
		FoodItems optional;
		try {
			optional = this.getFoodById(id);
			if (optional != null) {
				throw new IdNotFoundException("food not found");
			} else {
				foodItemsRepo.deleteById(id);
				return "food deleted";
			}
		} catch (IdNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IdNotFoundException(e.getMessage());
		}
	}

//get all food details
	@Override
	public Optional<List<FoodItems>> getAllFoodDetails() {
		return Optional.ofNullable(foodItemsRepo.findAll());
	}

}