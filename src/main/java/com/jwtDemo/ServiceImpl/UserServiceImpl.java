package com.jwtDemo.ServiceImpl;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jwtDemo.Service.IUserService;
import com.jwtDemo.entities.User;
import com.jwtDemo.repository.UserRepository;

@Service
public class UserServiceImpl implements IUserService,UserDetailsService {

	@Autowired
	private UserRepository repo;
	
	// TODO :Encode password
	
	@Autowired
	private BCryptPasswordEncoder pwdEncoder;
	
	//Get save method
	@Override
	public Integer saveUser(User user) {
	 
		//Encode password
		user.setPassword(
				pwdEncoder.encode(user.getPassword())
				);
		
		Integer id = repo.save(user).getId();
		
		return id;
	}

	// get user by username
	@Override
	public Optional<User> findByUsername(String username) {
	    return repo.findByUsername(username);
	}

	//----------------------------------------------------------------------------------------------------------------------------------------------
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    
		//Get data from DB
		Optional<User> dbuser = findByUsername(username);
	   
	    
	    if(dbuser.isEmpty()) {
	    	throw new UsernameNotFoundException("User Does not exist");
	    }
	    //Read user From database
	    User user = dbuser.get();
	    
	    return new org.springframework.security.core.userdetails.User(
	    		username,
	    		user.getPassword(),
	    		user.getRoles().stream().map(role ->new SimpleGrantedAuthority(role)).collect(Collectors.toList()));
		
	}

}
