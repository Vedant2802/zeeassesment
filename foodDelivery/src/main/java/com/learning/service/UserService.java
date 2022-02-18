package com.learning.service;

import com.learning.entity.User;
import com.learning.exception.AlreadyExistsException;
import com.learning.exception.IdNotFoundException;

public interface UserService {

	User addUser(User user);

	User[] getAllUsers();

	User getUserByUsername(String username) throws IdNotFoundException;

	User updateUser(User user) throws IdNotFoundException, AlreadyExistsException;

	String deleteUserByUsername(String username) throws IdNotFoundException;

}