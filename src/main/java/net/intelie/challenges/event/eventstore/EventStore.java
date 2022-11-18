package net.intelie.challenges.event.eventstore;

import net.intelie.challenges.Event;
import net.intelie.challenges.EventComparator;
import net.intelie.challenges.event.eventiterator.EventIterator;

import java.util.Collections;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class EventStore implements EventStoreInterface {

    // **************
    // PRIVATE FIELDS
    // **************

    // ConcurrentSkipListMap is thread safe
    private ConcurrentSkipListMap<String, SortedSet<Event>> eventStore = new ConcurrentSkipListMap<>();

    // **************
    // PUBLIC METHODS
    // **************

    // Event's added are sorted by their Timestamp.
    // Sorting is being used here to assert predictability on tests
    @Override
    public synchronized void insert(Event event) {
        String eventType = event.getType();

        // Put the Key and instantiate a new Set if it doesn't already exist
        eventStore.putIfAbsent(eventType,
                // SynchronizedSortedSet for thread-safety. TreeSet and EventComparator to sort it by timestamp
                Collections.synchronizedSortedSet(new TreeSet<>(new EventComparator())));

        // Now that the key is guaranteed to be in the map, add the event's value to it
        eventStore.get(eventType).add(event);
    }

    @Override
    public synchronized void removeAll(String type) {
        eventStore.remove(type);
    }

    @Override
    public EventIterator query(String eventType, long startTime, long endTime) throws NullPointerException {

        SortedSet<Event> eventsByType = eventStore.get(eventType)
                .stream().filter(e -> e.getTimestamp() >= startTime && e.getTimestamp() < endTime)
                .collect(Collectors.toCollection(() -> new TreeSet<>(new EventComparator())));

        // Exception is thrown if nothing was found with the provided timestamp
        if (eventsByType.isEmpty()) {
            throw new NullPointerException("Events not found with provided TimeStamp");
        }

        // Now EventsByType is guaranteed to always have at least one Event.
        return new EventIterator(eventsByType);
    }

    // if no timestamp were provided, return every Event with that type
    public EventIterator query(String eventType) {
        return new EventIterator(eventStore.get(eventType));
    }
}
