package com.helmes.assignment.services;

import com.helmes.assignment.entity.models.MyUser;
import com.helmes.assignment.entity.repositories.MyUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final MyUserRepository myUserRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(MyUserRepository myUserRepository, PasswordEncoder passwordEncoder) {
        this.myUserRepository = myUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public MyUser loadUserById(Long id) {
        return myUserRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found!"));
    }

    public void createUser(MyUser myUser) {
        myUser.setPassword(passwordEncoder.encode(
            myUser.getPassword() != null ? myUser.getPassword() : "123456"));
        myUserRepository.save(myUser);
    }

    public void updateUser(MyUser myUser) {
        MyUser user = myUserRepository
            .findById(myUser.getId()).orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(
            myUser.getPassword() != null ? myUser.getPassword() : "123456"));
        user.setUsername(myUser.getUsername());
        user.setRole(myUser.getRole());
        myUserRepository.save(user);
    }

    public void deleteUserById(Long id) {
      if (!myUserRepository.existsById(id)) {
          throw new RuntimeException("User not found!");
      }
      myUserRepository.deleteById(id);
    }
}
