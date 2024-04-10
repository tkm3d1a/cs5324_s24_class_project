package com.cs5324.monitorbackend.controller;

import com.cs5324.monitorbackend.entity.Event;
import com.cs5324.monitorbackend.entity.Notification;
import com.cs5324.monitorbackend.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/events", produces = "application/json")
@RequiredArgsConstructor
@Slf4j
public class EventController {
    private final EventService eventService;

    // Returns all events owned by userId
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getOwnedEvents(@RequestParam(value="id",required=true) UUID userId){
        Set<Event> ownedEvents = eventService.getEvents(userId);
        return new ResponseEntity(ownedEvents, HttpStatus.OK);
    }

    // UC07: View Event -- returns event selected by owner
    @RequestMapping(value = "/selected", method = RequestMethod.GET)
    public ResponseEntity getSelectedEvent(@RequestParam(value="id",required=true) UUID eventId){
        Optional<Event> selectedEvent = eventService.getEvent(eventId);
        return new ResponseEntity(selectedEvent, HttpStatus.OK);
    }

    // UC05: Edit Event
    @RequestMapping(value = "/selected/edit", method = RequestMethod.PATCH)
    public ResponseEntity submitEditedEvent(@RequestBody Event event) throws BadRequestException {
        Notification editedNotif = eventService.submitEdits(event);
        return new ResponseEntity(editedNotif, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public Event createEvent(@RequestBody Event event) {
        return eventService.createEvent(event);
    }

    @DeleteMapping
    public void deleteEvent(Event event) {
        try {
            eventService.deleteEvent(event);
        } catch (Exception ex) {
            log.info("Cannot delete {}", event);
        }
    }
}
