package com.learning.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.entity.Login;
import com.learning.entity.Reg;
import com.learning.exception.AlreadyExistsException;
import com.learning.exception.IdNotFoundException;
import com.learning.repo.LoginRepo;
import com.learning.repo.RegRepo;
import com.learning.service.LoginService;
import com.learning.service.RegService;

@Service // using this we get the singleton object
public class RegServiceImpl implements RegService {

	@Autowired
	private RegRepo regRepo;
	@Autowired
	private LoginRepo loginRepo;
	@Autowired
	private LoginService loginService;

	@Override
	@org.springframework.transaction.annotation.Transactional(rollbackFor = AlreadyExistsException.class)
	public Reg addReg(Reg reg) throws AlreadyExistsException {

		boolean status = regRepo.existsByEmail(reg.getEmail());
		if (status) {
			throw new AlreadyExistsException("EMAIL ALREADY EXISTS!");
		}

		Reg reg2 = regRepo.save(reg);
		if (reg2 != null) {
			Login login = new Login(reg.getEmail(), reg.getPassword(), reg2);
			if (loginRepo.existsByEmail(reg.getEmail())) {
				throw new AlreadyExistsException("USER ALREADY EXISTS IN LOGIN");
			}
			String result = loginService.addLogin(login);
			if (result == "success") {
				return reg2;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public Reg updateReg(Reg reg)
			throws IdNotFoundException, AlreadyExistsException {

		if (!regRepo.existsById(reg.getRegId())) {
			throw new IdNotFoundException("Sorry user with " + reg.getRegId() + " not found");
		}
		return regRepo.save(reg);
	}

	@Override
	public Reg getRegById(int regId) throws IdNotFoundException {
		Optional<Reg> optional = regRepo.findById(regId);
		if (optional.isEmpty()) {
			throw new IdNotFoundException("sorry " + regId + " not found");
		} else {
			return optional.get();
		}
	}

	@Override
	public Reg[] getAllReg() {
		List<Reg> list = regRepo.findAll();
		if (list.isEmpty()) {
			return null;
		}
		Reg[] array = new Reg[list.size()];
		return list.toArray(array);
	}

	@Override
	public String deleteRegById(int regId) throws IdNotFoundException {

		if (!regRepo.existsById(regId)) {
			throw new IdNotFoundException("sorry user with id " + regId + " not found");
		}
		regRepo.deleteById(regId);
		return "User Successfully deleted";
	}
}