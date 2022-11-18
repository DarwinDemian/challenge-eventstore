package net.intelie.challenges;

import net.intelie.challenges.event.Event;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EventTest {
    // Note from the interviewee:
    // I happen to (kinda) be one of those coverage freaks
    // or as Arnold Schwarzenegger would put it in 'The Running Man':
    // "It's showtime"

    @Test
    public void thisIsAWarning() throws Exception {
        Event event = new Event("some_type", 123L);

        //THIS IS A WARNING:
        //Some of us (not everyone) are coverage freaks.
        assertEquals(123L, event.getTimestamp());
        assertEquals("some_type", event.getType());
    }
}