package com.learning.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.entity.User;
import com.learning.exception.AlreadyExistsException;
import com.learning.exception.IdNotFoundException;
import com.learning.repo.UserRepo;
import com.learning.service.UserService;

@Service // using this we get the singleton object
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Override
	public User addUser(User user) {

		return userRepo.save(user);

	}

	@Override
	public User updateUser(User user) throws IdNotFoundException, AlreadyExistsException {

		if (!userRepo.existsByUsername(user.getUsername())) {
			throw new IdNotFoundException("Sorry user with " + user.getUsername() + " not found");
		}
		return userRepo.save(user);
	}

	@Override
	public User getUserByUsername(String username) throws IdNotFoundException {
		Optional<User> optional = userRepo.findByUsername(username);
		if (optional.isEmpty()) {
			throw new IdNotFoundException("sorry " + username + " not found");
		} else {
			return optional.get();
		}
	}

	@Override
	public User[] getAllUsers() {
		List<User> list = userRepo.findAll();
		if (list.isEmpty()) {
			return null;
		}
		User[] array = new User[list.size()];
		return list.toArray(array);
	}

	@Override
	public String deleteUserByUsername(String username) throws IdNotFoundException {
		Optional<User> optional = userRepo.findByUsername(username);
		if (optional.isPresent()) {
			userRepo.deleteById(optional.get().getUserId());
			return "User Successfully deleted";
		}
		return "Not deleted";
	}

}