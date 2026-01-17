package com.example2.taskmanager.Repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example2.taskmanager.Model.Task;
import com.example2.taskmanager.Model.User;
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

	//List<Task> findByUsername(String username);
	List<Task> findByUser(User user);
	
}
