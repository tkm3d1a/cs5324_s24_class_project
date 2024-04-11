package com.cs5324.monitorbackend.services;

import com.cs5324.monitorbackend.entity.Event;
import com.cs5324.monitorbackend.entity.Notification;
import com.cs5324.monitorbackend.entity.enums.ItemStatus;
import com.cs5324.monitorbackend.exception.EventDoesNotExistException;
import com.cs5324.monitorbackend.repository.EventRepository;
import com.cs5324.monitorbackend.service.EventService;
import org.apache.coyote.BadRequestException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventServiceTests {
    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    @Test
    public void createEvent_Success() {
        Event e = new Event();
        e.setId(UUID.randomUUID());
        e.setDateOfEvent(LocalDate.of(2002, 1, 1));

        when(eventRepository.save(any(Event.class))).thenReturn(e);

        Event e2 = new Event();
        e2.setDateOfEvent(LocalDate.of(2002, 1, 1));

        Event event = eventService.createEvent(e);

        assertNotNull(event);
        assertNotNull(event.getId());
        assertEquals(LocalDate.of(2002, 1, 1), event.getDateOfEvent());
    }

    @Test
    public void viewEvent_Success(){
        // create initial event
        Event e = new Event();
        e.setId(UUID.randomUUID());
        e.setDateOfEvent(LocalDate.of(2002, 1, 1));

        when(eventRepository.save(any(Event.class))).thenReturn(e);
        Event event = eventService.createEvent(e);

        when(eventRepository.findById(event.getId())).thenReturn(Optional.of(event));
        Event viewedEvent = eventService.getEvent(event.getId()).get();

        assertNotNull(viewedEvent);
        assertNotNull(viewedEvent.getId());
        assertEquals(event,viewedEvent);
    }

    @Test
    public void editEvent_Success() throws BadRequestException, EventDoesNotExistException {
        // create initial event
        Event e = new Event();
        e.setId(UUID.randomUUID());
        e.setDateOfEvent(LocalDate.of(2002, 1, 1));

        when(eventRepository.save(any(Event.class))).thenReturn(e);
        Event event = eventService.createEvent(e);

        // create event edits
        Event edits = new Event();
        edits.setId(event.getId());
        edits.setDateOfEvent(LocalDate.of(2002, 1, 2));

        when(eventRepository.findById(edits.getId())).thenReturn(Optional.of(edits));
        Notification editNotif = eventService.submitEdits(edits);

        // check that event was successfully updated
        Event editedEvent = eventRepository.findById(event.getId()).get();
        assertEquals(editedEvent.getDateOfEvent(), edits.getDateOfEvent());
        assertEquals(editedEvent.getApprovalStatus(), ItemStatus.PENDING);
        assertNotNull(editedEvent.getNotification());
    }

    @Test
    public void deleteEvent_Success() throws BadRequestException {
        // create initial event
        Event e = new Event();
        e.setId(UUID.randomUUID());
        e.setDateOfEvent(LocalDate.of(2002, 1, 1));

        when(eventRepository.save(any(Event.class))).thenReturn(e);
        Event event = eventService.createEvent(e);

        // delete event
        eventService.deleteEvent(event);

        // check that event was successfully deleted
        Event deletedEvent = eventRepository.findById(event.getId()).orElse(null);
        assertNull(deletedEvent);
    }

    public void createEvent_NullDateOfEvent(){
        Event e = new Event();
        when(eventRepository.save(e)).thenThrow(ConstraintViolationException.class);

        assertThrows(ConstraintViolationException.class, () -> eventService.createEvent(e));
    }
}
