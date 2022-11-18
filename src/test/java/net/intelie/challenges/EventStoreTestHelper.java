package net.intelie.challenges;

import net.intelie.challenges.event.Event;
import net.intelie.challenges.event.eventiterator.EventIterator;
import net.intelie.challenges.event.eventstore.EventStore;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


/*
 * Proof of Concept that separating the thread handling logic
 * is enough to keep everything Thread-Safe even when multiples threads are manipulating
 * the same EventStore. This class represent a use case of how one would fully utilize the EventStore.
 */
public class EventStoreTestHelper {

    // **************
    // PRIVATE FIELDS
    // **************

    private EventStore eventStore;

    public EventStore getEventStore() {
        return this.eventStore;
    }

    // ***************
    // PRIVATE METHODS
    // ***************

    private void assertContent(EventIterator iterator) {
        assertEquals("Preexisting Event", iterator.current().getType());
        assertEquals(1641081600, iterator.current().getTimestamp());
        iterator.moveNext();
        assertEquals(1641168000, iterator.current().getTimestamp());
        assertFalse(iterator.moveNext());
    }


    // **************
    // PUBLIC METHODS
    // **************

    public synchronized void insertRandomValue() {
        eventStore.insert(new Event("Thread Event", new Random().nextLong()));
    }

    public synchronized void createEventStoreWithPredefinedValues() {
        eventStore = new EventStore();

        long timeStamp = 1640995200; // 2022-01-01
        long secondTimeStamp = 1641081600; // 2022-01-02
        long thirdTimeStamp = 1641168000; // 2022-01-03

        eventStore.insert(new Event("Preexisting Event", secondTimeStamp));
        eventStore.insert(new Event("Preexisting Event", thirdTimeStamp));
        eventStore.insert(new Event("Thread Event", timeStamp));
    }

    public synchronized void assertPreexistingEvent() {
        EventIterator iterator = getEventStore().query("Preexisting Event");
        assertContent(iterator);
    }

    public synchronized void assertPreexinstingEventWithTimeStamp() {
        EventIterator iterator = getEventStore().query("Preexisting Event", 1640995200, 1999999999);
        assertContent(iterator);
    }

    public synchronized void moveThroughThreadEvent() {
        EventIterator iterator = getEventStore().query("Thread Event");
        iterator.current();
        iterator.moveNext();
        iterator.current();
        iterator.moveNext();
        iterator.current();
    }

}