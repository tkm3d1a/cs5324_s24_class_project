package com.cs5324.monitorbackend.services;

import com.cs5324.monitorbackend.entity.Event;
import com.cs5324.monitorbackend.repository.EventRepository;
import com.cs5324.monitorbackend.service.EventService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
}
