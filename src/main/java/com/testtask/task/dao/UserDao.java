package com.testtask.task.dao;
 
import java.util.List;
 
import com.testtask.task.model.User;
 
 
public interface UserDao {
 
    User findById(int id);
    
    User findByEmail(String email);
     
    void save(User user);
     
    void delete(int id);
     
    List<User> findAllUsers();
 
}