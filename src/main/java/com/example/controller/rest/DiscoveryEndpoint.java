package com.example.controller.rest;

import com.example.model.Discovery;
import com.example.repository.DiscoveryRepository;
import com.example.utils.TimeUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/discoveries")
public class DiscoveryEndpoint {

    @Autowired
    private DiscoveryRepository discoveryRepository;
    @Autowired
    private TimeUtility timeUtility;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Discovery> getAll() {
        return discoveryRepository.findAll();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/{discoveryId}")
    public ResponseEntity<Discovery> getByDiscId(@PathVariable Long discoveryId){
        Discovery discovery = discoveryRepository.findOne(discoveryId);
        if(discovery != null){
            return ResponseEntity.ok(discovery);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@RequestBody Discovery discovery) {
        if(discovery.getDiscoveryId() == null) {
            discovery.setDownVote(0);
            discovery.setUpVote(0);
            discovery.setDate(timeUtility.getTimestamp());
            discovery.setShortDescription(shortDiscovery(discovery));
            Discovery saved = discoveryRepository.save(discovery);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{discoveryId}")
                    .buildAndExpand(saved.getDiscoveryId())
                    .toUri();
            return ResponseEntity.created(location).body(discovery);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    private String shortDiscovery(Discovery discovery) {
        String description =  discovery.getDescription();
        StringBuilder stringBuilder = new StringBuilder();
        if(description.length()>128){
            for(int i=0; i<128; i++){ stringBuilder.append(description.charAt(i)); }
            stringBuilder.append("...");
            return stringBuilder.toString();
        } else return description;

    }


}
