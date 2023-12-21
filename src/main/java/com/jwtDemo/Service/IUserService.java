package com.jwtDemo.Service;

import java.util.Optional;

import com.jwtDemo.entities.User;

public interface IUserService {
     Integer saveUser(User user);
 	 Optional<User>findByUsername(String username);

}
