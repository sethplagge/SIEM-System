package com.siem.User;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Objects;

@RestController
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User CurrentUser(String userName) {
        return userRepository.findByUserName(userName).orElse(null);
    }

    //returns true if the username is available
    public Boolean UserNameIsAvailable(User newUser){
        return (userRepository.findByUserName(newUser.getUserName()).orElse(null)==null);
    }

    //returns true if the user exists in our database
    public Boolean UserExists(String userName) {
        return userRepository.findByUserName(userName).orElse(null)!=null;
    }

    //GET: shows all accounts
    @GetMapping("/users")
    public List<User> getLogins() {
        return userRepository.findAll();
    }

    //POST: add a new user
    @PostMapping("/signUp")
    public String signUpUser(@RequestBody User newUser) {
        if (UserNameIsAvailable(newUser)) {
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
            userRepository.save(newUser);
            return "New user " + newUser.getUserName() + " Saved with ID of " + newUser.getToken();
        }
        else {
            return "Sorry, the Username " + newUser.getUserName() + " is already in use.";
        }
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody User user) {
        if (UserExists(user.getUserName()) && passwordEncoder.matches(user.getPassword(), CurrentUser(user.getUserName()).getPassword())) {
            return "New login from " + user.getUserName();
        }
        else {
            return "Incorrect username or password";
        }
    }

    //GET: find a user by their username
    @GetMapping("/user/{userName}")
    public User getUser(@PathVariable String userName) {
        return CurrentUser(userName);
    }

    //GET: find a user by a piece of their username, ignoring case
    @GetMapping("/user/contains")
    public List<User> getUserByPieceOfName(@RequestParam("userName") String userName) {
        return userRepository.findByUserNameContainingIgnoreCase(userName);
    }

    //PUT: Allow someone to change their username or password
    @PutMapping("/user/{changeType}/{userName}")
    public String updateUser(@PathVariable String changeType, @PathVariable String userName, @RequestBody User user) {
        if (UserExists(userName)) {
            User currentUser = CurrentUser(userName);
            if (Objects.equals(changeType, "changeUsername") && UserNameIsAvailable(user)) {
                currentUser.setUserName(user.getUserName());
                userRepository.save(currentUser);
                return userName + "'s username changed to " + user.getUserName();
            }
            else if(Objects.equals(changeType, "changePassword")) {
                currentUser.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepository.save(currentUser);
                return userName + "'s password changed";
            }
            else if (!UserNameIsAvailable(user)){
                return "Sorry, the Username " + user.getUserName() + " is already in use.";
            }
            else {
                return "Error: this page does not exist";
            }
        }
        else {
            return "Sorry, that user does not exist";
        }
    }

    @PutMapping("/role/{userName}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateRole(@PathVariable String userName, @RequestBody User user) {
        if (UserExists(userName)) {
            User currentUser = CurrentUser(userName);
            currentUser.setRole(user.getRole());
            userRepository.save(currentUser);
            return userName + "'s role changed to " + user.getRole();
        }
        else {
            return "Sorry, that user does not exist";
        }
    }

    @PutMapping("/status/{userName}")
    public String updateStatus(@PathVariable String userName, @RequestBody User user) {
        if (UserExists(userName)) {
            User currentUser = CurrentUser(userName);
            currentUser.setStatus(user.getStatus());
            userRepository.save(currentUser);
            return userName + "'s status changed to " + user.getStatus();
        }
        else {
            return "Sorry, that user does not exist";
        }
    }

    //DELETE: Allow someone to delete a user by their username
    @DeleteMapping("/user/{userName}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteUsers(@PathVariable String userName) {
        if (UserExists(userName)) {
            userRepository.delete(CurrentUser(userName));
            return "User " + userName + " has been deleted";
        }
        return "User " + userName + " does not exist";
    }

    //DELETE: Allow someone to delete all users in the server
    // for testing purposed
    @DeleteMapping("/users")
    public void deleteUsers() {
        userRepository.deleteAll();
    }

}