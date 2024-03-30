package com.cs5324.monitorbackend.service;

import com.cs5324.monitorbackend.entity.Event;
import com.cs5324.monitorbackend.entity.Notification;
import com.cs5324.monitorbackend.entity.Page;
import com.cs5324.monitorbackend.entity.User;
import com.cs5324.monitorbackend.entity.enums.ItemStatus;
import com.cs5324.monitorbackend.repository.EventRepository;
import com.cs5324.monitorbackend.repository.UserRepository;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService{
    @Resource
    private final EventRepository eventRepo;

    @Resource
    private final UserRepository userRepo;

    public Set<Event> getEvents(UUID userId){

        Optional<User> user = userRepo.findById(userId);
        Set<Event> ownedEvents = user.get().getEvents();

        return ownedEvents;
    }

    public Event getEvent(Set<Event> ownedEvents, UUID eventId){

        for(Event event : ownedEvents){
            if(event.getId() == eventId){
                return event;
            }
        }

        return null;
    }

    public Notification submitEdits(Event editedEvent){
        editedEvent.setApprovalStatus(ItemStatus.PENDING);
        editedEvent.setDateOfEvent(editedEvent.getDateOfEvent());
        editedEvent.setPage(editedEvent.getPage());

        Notification editedNotif = new Notification();
        editedEvent.setNotification(editedNotif);

        return editedNotif;
    }

}
