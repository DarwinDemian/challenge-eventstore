package net.intelie.challenges.event.eventiterator;

import net.intelie.challenges.event.Event;

import java.util.Iterator;
import java.util.SortedSet;

/**
 * An iterator over an event collection.
 */
public class EventIterator implements EventIteratorInterface {

    // **************
    // PRIVATE FIELDS
    // **************

    private final Iterator ITERATOR;
    private Event currentEvent;

    // ***********
    // CONSTRUCTOR
    // ***********

    // EventIterator always start with the first Event already defined.
    public EventIterator(SortedSet<Event> eventsByType) {
        this.ITERATOR = eventsByType.iterator();
        this.currentEvent = (Event) ITERATOR.next();
    }

    // **************
    // PUBLIC METHODS
    // **************

    @Override
    public synchronized boolean moveNext() {
        if (ITERATOR.hasNext()) currentEvent = (Event) ITERATOR.next();

        return ITERATOR.hasNext();
    }

    @Override
    public synchronized Event current() {
        // No need to throw IllegalStateException since currentEvent will always be last Event set
        return currentEvent;
    }

    @Override
    public synchronized void remove() {
        // removes the last Event returned by iterator
        ITERATOR.remove();
    }

    @Override
    public synchronized void close() {}
}
