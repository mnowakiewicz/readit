package com.example.repository.service;

import com.example.model.User;
import com.example.model.UserRole;
import com.example.repository.UserRepository;
import com.example.repository.UserRoleRepository;
import com.example.security.CustomPasswordEncoder;
import com.example.utils.TimeUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class RepositoryService {

    private static final String DEFAULT_ROLE = "ROLE_USER";

    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private CustomPasswordEncoder passwordEncoder;

    private TimeUtility timestamp;


    public User addUserWithDefaultRoleAndDate(User user) {
        UserRole defaultRole = userRoleRepository.findByRole(DEFAULT_ROLE);
        user.setUserRoles(Arrays.asList(defaultRole));
        user.setRegistrationDate(timestamp.getTimestamp());
        String encodedPass = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPass);
        userRepository.save(user);
        return user;
    }

    public List<User> getAllUsersWihtoutPasswordAndEmail() {
        List<User> users = userRepository.findAll();
        users.forEach(user -> user.setPassword(null));
        users.forEach(user -> user.setEmail(null));
        return users;
    }

    public User getUserWithoutPasswordAndEmail(Long id) {
        User user = userRepository.findOne(id);
        user.setEmail(null);
        user.setPassword(null);
        return user;
    }

    @Autowired
    public void setTimestamp(TimeUtility timestamp) {
        this.timestamp = timestamp;
    }
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Autowired
    public void setUserRoleRepository(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }
    @Autowired
    public void setPasswordEncoder(CustomPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
