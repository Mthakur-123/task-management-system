package com.example2.taskmanager.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example2.taskmanager.Model.Task;
import com.example2.taskmanager.Model.User;
import com.example2.taskmanager.Repository.TaskRepository;
import com.example2.taskmanager.Repository.UserRepository;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repo;

    @Autowired
    private UserRepository userRepo;

    
   
    public List<Task> getTasks() {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepo.findByUsername(username).orElseThrow();
       
        return repo.findByUser(user);
    }

  
    public Task createTask(Task task ) {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepo.findByUsername(username).orElseThrow();

        task.setUser(user);

        return repo.save(task);
    }
    public Task updateTask(Long id, Task updatedTask) {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User loggedInUser = userRepo.findByUsername(username).orElseThrow();
        Task task = repo.findById(id).orElseThrow();

        // 🔐 Authorization check
        boolean isOwner = task.getUser().getId().equals(loggedInUser.getId());
        boolean isAdmin = loggedInUser.getRole().name().equals("ROLE_ADMIN");

        if (!isOwner && !isAdmin) {
            throw new RuntimeException("You are not allowed to update this task");
        }

        task.setTitle(updatedTask.getTitle());
        return repo.save(task);
    }
    public Task deleteTask(Long id) {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User loggedInUser = userRepo.findByUsername(username).orElseThrow();
        Task task = repo.findById(id).orElseThrow();

        boolean isOwner = task.getUser().getId().equals(loggedInUser.getId());
        boolean isAdmin = loggedInUser.getRole().name().equals("ROLE_ADMIN");

        if (!isOwner && !isAdmin) {
            throw new RuntimeException("You are not allowed to delete this task");
        }

        repo.delete(task);
        return task;
    }


}
