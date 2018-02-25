package com.example.controller.rest;

import com.example.model.User;
import com.example.model.UserRole;
import com.example.model.Vote;
import com.example.repository.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserEndpoint {


    private RepositoryService repositoryService;

    @Autowired
    public void setRepositoryService(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAll() {
        return repositoryService.getAllUsersWihtoutPasswordAndEmail();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/{userId}")
    public ResponseEntity<User> getById(@PathVariable Long userId){
        User user = repositoryService.getUserWithoutPasswordAndEmail(userId);
        if(user != null){
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@RequestBody User user) {
        if(user.getUserId() == null) {
            User saved = repositoryService.addUserWithDefaultRoleAndDate(user);
            user.setPassword(null);
            user.setEmail(null);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{userId}")
                    .buildAndExpand(saved.getUserId())
                    .toUri();
            return ResponseEntity.created(location).body(user);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    //add response entity
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/{userId}/roles")
    public List<UserRole> getUserRole( @PathVariable Long userId) {
        List<UserRole> roles = repositoryService.getUserWithoutPasswordAndEmail(userId).getUserRoles();
        return  roles;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/{userId}/votes")
    public List<Vote> getVotes(@PathVariable Long userId){
        User user = repositoryService.getUserWithoutPasswordAndEmail(userId);
        return user.getVotes();
    }


}
