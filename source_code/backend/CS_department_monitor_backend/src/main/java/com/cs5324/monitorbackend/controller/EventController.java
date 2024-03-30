package com.cs5324.monitorbackend.controller;

import com.cs5324.monitorbackend.entity.Event;
import com.cs5324.monitorbackend.entity.Notification;
import com.cs5324.monitorbackend.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/events", produces = "application/json")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getOwnedEvents(UUID userId){
        Set<Event> ownedEvents = eventService.getEvents(userId);
        return new ResponseEntity(ownedEvents, HttpStatus.OK);
    }

    @RequestMapping(value = "/selected", method = RequestMethod.GET)
    public ResponseEntity getSelectedEvent(Set<Event> ownedEvents, UUID eventId){
        Event selectedEvent = eventService.getEvent(ownedEvents, eventId);
        return new ResponseEntity(selectedEvent, HttpStatus.OK);
    }

    @RequestMapping(value = "/selected/edit", method = RequestMethod.POST)
    public ResponseEntity submitEditedEvent(Event editedEvent){
        Notification editedNotif = eventService.submitEdits(editedEvent);
        return new ResponseEntity(editedNotif, HttpStatus.OK);
    }

}
