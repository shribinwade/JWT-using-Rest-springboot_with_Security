package com.jwtDemo.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jwtDemo.Service.IUserService;
import com.jwtDemo.entities.User;
import com.jwtDemo.repository.UserRepository;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepository repo;
	
	@Override
	public Integer saveUser(User user) {
		// TODO Auto-generated method stub
		
		Integer id = repo.save(user).getId();
		
		return id;
	}

}
