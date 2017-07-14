package com.testtask.task.model;
 
import java.io.Serializable;
import java.util.Date;
 
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
 
@Entity
@Table(name="persistent_login")
public class PersistentLogin implements Serializable{
 
    @Id
    private int id;
 
    @Column(name="username", unique=true, nullable=false)
    private String username;
     
    @Column(name="token", unique=true, nullable=false)
    private String token;
     
//    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="last_used", nullable=false)
    private Date last_used;
 
    public int getSeries() {
        return id;
    }
 
    public void setSeries(int series) {
        this.id = series;
    }
 
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getToken() {
        return token;
    }
 
    public void setToken(String token) {
        this.token = token;
    }
 
    public Date getLast_used() {
        return last_used;
    }
 
    public void setLast_used(Date last_used) {
        this.last_used = last_used;
    }
         
}