package com.laurentiuspilca.liveproject_spring_security_milestone1.controllers;

import com.laurentiuspilca.liveproject_spring_security_milestone1.entities.User;
import com.laurentiuspilca.liveproject_spring_security_milestone1.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  public void addUser(@RequestBody User user) {
    userService.addUser(user);
  }

  @GetMapping
  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }
}
