package com.helmes.assignment.services;

import com.helmes.assignment.entity.models.MyUser;
import com.helmes.assignment.entity.models.Sector;
import com.helmes.assignment.entity.repositories.MyUserRepository;
import com.helmes.assignment.enums.TermsAndConditionsAgreement;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

    public MyUser loadUserByUsername(String username) {
        return myUserRepository.findMyUserByUsername(username);
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

    public void addSectors(String username, List<Sector> sectors, String userTermsAndConditions) {
        MyUser user = this.loadUserByUsername(username);
        user.setSectors(sectors);
        user.setUserTermsAndConditions(TermsAndConditionsAgreement.valueOf(
            Objects.equals(userTermsAndConditions, "true") ? "AGREE" : "DISAGREE"));
        myUserRepository.save(user);
    }

    public void deleteUserById(Long id) {
      if (!myUserRepository.existsById(id)) {
          throw new RuntimeException("User not found!");
      }
      myUserRepository.deleteById(id);
    }
}
