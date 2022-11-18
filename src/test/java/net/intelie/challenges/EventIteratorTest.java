package net.intelie.challenges;

import net.intelie.challenges.event.eventiterator.EventIterator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;

public class EventIteratorTest {

    // **************
    // PRIVATE FIELDS
    // **************

    private final EventStoreTestHelper EVENT_STORE_HELPER = new EventStoreTestHelper();

    // ***********
    // INITIALIZER
    // ***********

    @Before
    public void initialize() {
        EVENT_STORE_HELPER.createEventStoreWithPredefinedValues();
    }

    // *****
    // TESTS
    // *****

    @Test
    public void testIterator() {
        EVENT_STORE_HELPER.assertPreexistingEvent();
    }

    @Test
    public void testEmptyIterator() {
        assertThrows(NullPointerException.class,
                () -> EVENT_STORE_HELPER.getEventStore().query(
                        "Preexisting Event", 1999999999, 1999999999)
        );
    }

    @Test
    public void testRemove() {
        EventIterator iterator = EVENT_STORE_HELPER.getEventStore().query("Preexisting Event");
        iterator.remove();
        iterator.moveNext();
        iterator.remove();

        assertFalse(iterator.moveNext());
        assertThrows(IllegalStateException.class, iterator::remove);
    }

}