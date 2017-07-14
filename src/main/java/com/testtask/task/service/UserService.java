package com.testtask.task.service;
 
import java.util.List;
 
import com.testtask.task.model.User;
 
 
public interface UserService {
     
    User findById(int id);
    
    User findByEmail(String email);
     
    void saveUser(User user);
     
    void updateUser(User user);
     
    void deleteUser(int id);
 
    List<User> findAllUsers(); 
 
}