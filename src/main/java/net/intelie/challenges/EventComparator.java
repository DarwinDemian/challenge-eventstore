package net.intelie.challenges;

import java.util.Comparator;

/*
 * Compares Events by their timestamps in ascending order
 */
public class EventComparator implements Comparator<Event> {

    @Override
    public int compare(Event event1, Event event2) {
        return Long.compare(event1.getTimestamp(), event2.getTimestamp());
    }
}
