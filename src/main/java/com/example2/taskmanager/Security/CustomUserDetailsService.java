package com.example2.taskmanager.Security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.example2.taskmanager.Model.User;
import com.example2.taskmanager.Repository.UserRepository;

@Component
public class CustomUserDetailsService  implements UserDetailsService {

	  @Autowired
	    private UserRepository repo;
	   @Override
	    public UserDetails loadUserByUsername(String username) {

	        User user = repo.findByUsername(username)
	            .orElseThrow();

	        return new org.springframework.security.core.userdetails.User(
	            user.getUsername(),
	            user.getPassword(),
	            List.of(new SimpleGrantedAuthority(user.getRole().name()))
	        );
	    } 
}
