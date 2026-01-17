package com.example2.taskmanager.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example2.taskmanager.DTO.AuthResponse;
import com.example2.taskmanager.DTO.LoginRequest;
import com.example2.taskmanager.DTO.RegisterRequest;
import com.example2.taskmanager.Model.Role;
import com.example2.taskmanager.Model.User;
import com.example2.taskmanager.Repository.UserRepository;
import com.example2.taskmanager.Security.JwtUtil;

@Service
public class UserService {
	
	    @Autowired
	    private AuthenticationManager authManager;
	    
	    @Autowired
	    private JwtUtil jwtUtil;
	    
	    @Autowired
	    private UserRepository repo;
         
	    @Autowired
	    private PasswordEncoder encoder;

	 public void register(RegisterRequest request) {
		 
	        User user = new User();
	    
	        user.setUsername(request.getUsername());
	        user.setPassword(encoder.encode(request.getPassword()));
	        user.setRole(Role.ROLE_USER);
	        repo.save(user);
	    }
	
	 public AuthResponse login(LoginRequest request) {

		   
		 authManager.authenticate(
		        new UsernamePasswordAuthenticationToken(
		            request.getUsername(),
		            request.getPassword()
		        )
		    );

		    User user = repo.findByUsername(request.getUsername())
		            .orElseThrow(() -> new RuntimeException("User not found"));

		    String token = jwtUtil.generateToken(user.getUsername(),
		    		user.getRole().name());

		    return new AuthResponse(
		            token,
		            user.getUsername(),
		            user.getRole().name()
		    );
		}

	 
}
	 
	    
