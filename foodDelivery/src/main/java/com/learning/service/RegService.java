package com.learning.service;

import com.learning.entity.Reg;
import com.learning.exception.AlreadyExistsException;
import com.learning.exception.IdNotFoundException;

public interface RegService {

	Reg addReg(Reg reg) throws AlreadyExistsException;

	String deleteRegById(int regId) throws IdNotFoundException;

	Reg[] getAllReg();

	Reg getRegById(int regId) throws IdNotFoundException;

	Reg updateReg(Reg reg) throws IdNotFoundException, AlreadyExistsException;

}