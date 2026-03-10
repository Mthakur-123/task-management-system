package com.example2.taskmanager.controller;

import java.time.LocalDateTime;
import java.util.List;

//import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example2.taskmanager.DTO.ApiResponse;
import com.example2.taskmanager.Model.Task;
import com.example2.taskmanager.Service.TaskService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;



@SecurityRequirement(name ="bearerAuth")
@PreAuthorize("hasAuthority('ROLE_USER')") 
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

	    @Autowired
	    private TaskService service;
	    
	    @GetMapping
	    public ResponseEntity<ApiResponse<List<Task>>> getTasks() {

	        List<Task> tasks = service.getTasks();
            System.out.println(tasks);
	        ApiResponse<List<Task>> response = new ApiResponse<>(
	                HttpStatus.OK.value(),
	                "Tasks fetched successfully",
	                LocalDateTime.now(),
	                tasks
	        );

	        return ResponseEntity.ok(response);
	    }

	    @PostMapping
	    public ResponseEntity<ApiResponse<Task>> createTask(@RequestBody Task task) {

	        Task savedTask = service.createTask(task);

	        ApiResponse<Task> response = new ApiResponse<>(
	                HttpStatus.CREATED.value(),
	                "Task created successfully",
	                LocalDateTime.now(),
	                savedTask
	        );

	        return new ResponseEntity<>(response, HttpStatus.CREATED);
	    }

	    
	    @PreAuthorize("hasAuthority('ROLE_USER')")
	    @PutMapping("/{id}")
	    public ResponseEntity<ApiResponse<Task>> updateTask(
	            @PathVariable Long id,
	            @RequestBody Task updatedTask
	    ) {
	        Task task = service.updateTask(id, updatedTask);

	        ApiResponse<Task> response = new ApiResponse<>(
	                HttpStatus.OK.value(),
	                "Task updated successfully",
	                LocalDateTime.now(),
	                task
	        );

	        return ResponseEntity.ok(response);
	    }
	    @DeleteMapping("/{id}")
	    public ResponseEntity<ApiResponse<Task>>deleteTask(@PathVariable Long id, HttpServletRequest request) {
	       Task task =  service.deleteTask(id);
	        
	        ApiResponse<Task> response = new ApiResponse<>(
	                HttpStatus.OK.value(),
	                "Task deleted successfully",
	                LocalDateTime.now(),
	                task
	                
	        );
	        return ResponseEntity.ok(response);
	    }
}
