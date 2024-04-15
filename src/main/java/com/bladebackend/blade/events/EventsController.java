package com.bladebackend.blade.events;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class EventsController {

    @PostMapping("event")
    public ResponseEntity<?> createEvent(@RequestBody String event){
        return ResponseEntity.ok("Event created: "+event);
    }
}
