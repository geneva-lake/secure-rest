package com.testtask.task.controller;
 
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.mockito.Matchers.anyString;
import com.testtask.task.dto.UserDto;
import com.testtask.task.model.PersistentLogin;
import com.testtask.task.model.User;
import com.testtask.task.service.PersistentLoginService;
import com.testtask.task.service.UserService;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;
 
public class MainControllerTest {
 
    @Mock
    UserService service;
    
    @Mock
    PersistentLoginService psService;
     
    @InjectMocks
    MainController mainController;
     
    @Spy
    UserDto dto;
    
    @Spy
    User user;
     
    @BeforeClass
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        user = new User();
        user.setPassword("password");
        user.setEmail("some@mail.com");
     }
     
    
    @Test
    public void registration(){
        Assert.assertEquals(mainController.register(dto), "registrationsuccess");
    }
    
    @Test
    public void login(){
    	MockHttpServletResponse mockResponse = new MockHttpServletResponse();
        Assert.assertEquals(mainController.login(dto, mockResponse), "not authorized");
        dto.setEmail("some@mail.com");
        dto.setPassword("password");
        when(service.findByEmail(anyString())).thenReturn(user);
        when(psService.getPersistenloginbyUser(user)).thenReturn(new PersistentLogin());
        doNothing().when(psService).save(any(PersistentLogin.class));
        Assert.assertEquals(mainController.login(dto, mockResponse), "success");
    }
    
    @Test
    public void user(){
    	MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        Assert.assertEquals(mainController.user(mockRequest), null);
        mockRequest.addHeader("Auth-Token", "token");
        when(psService.getPersistentLoginByToken(anyString())).thenReturn(new PersistentLogin());
        when(service.findByEmail(anyString())).thenReturn(user);
        Assert.assertEquals(mainController.user(mockRequest), user);
    }

 
}