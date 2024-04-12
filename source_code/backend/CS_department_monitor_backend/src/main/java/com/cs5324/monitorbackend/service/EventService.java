package com.cs5324.monitorbackend.service;

import com.cs5324.monitorbackend.entity.Event;
import com.cs5324.monitorbackend.entity.Notification;
import com.cs5324.monitorbackend.entity.User;
import com.cs5324.monitorbackend.entity.enums.ItemStatus;
import com.cs5324.monitorbackend.exception.EventDoesNotExistException;
import com.cs5324.monitorbackend.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService{
    private final EventRepository eventRepo;
    private final UserService userService;

    public Set<Event> getEvents(UUID userId){

        Optional<User> user = userService.getUser(userId);
        Set<Event> allOwnedEvents = user.get().getEvents();

        Set<Event> ownedEvents = new HashSet<Event>();
        for(Event event : allOwnedEvents){
            if(event.getApprovalStatus() == ItemStatus.APPROVED){
                ownedEvents.add(event);
            }
        }

        return ownedEvents;
    }

    public Optional<Event> getEvent(UUID eventId){

        Optional<Event> event = eventRepo.findById(eventId);
        if(event.isEmpty()){
            log.warn("Event not found");
            throw new EventDoesNotExistException();
        }

        return event;
    }

    public Notification submitEdits(Event event) throws BadRequestException, EventDoesNotExistException{

        if (event.getId() == null){
            throw new BadRequestException();
        }

        Optional<Event> eventToEdit = eventRepo.findById(event.getId());
        if(eventToEdit.isPresent()) {
            Event editedEvent = eventToEdit.get();

            editedEvent.setApprovalStatus(ItemStatus.PENDING);
            if(event.getDateOfEvent() != null) editedEvent.setDateOfEvent(editedEvent.getDateOfEvent());
            if(event.getDateOfEvent() != null) editedEvent.setPage(editedEvent.getPage());

            Notification editedNotif = new Notification();
            editedEvent.setNotification(editedNotif);
            eventRepo.save(editedEvent);

            return editedNotif;

        } else {
            throw new EventDoesNotExistException();
        }

    }

    public Event createEvent(Event event) {
        Notification n = new Notification();
        n.setEvent(event);
        event.setNotification(n);
        return eventRepo.save(event);
    }

    public void deleteEvent(Event event) throws BadRequestException {
        if(event.getId() == null){
            throw new BadRequestException();
        }
        eventRepo.deleteById(event.getId());
    }

}
