package net.intelie.challenges.event;

/**
 * This is just an event stub, feel free to expand it if needed.
 */
public class Event {

    // **************
    // PRIVATE FIELDS
    // **************

    private final String type;
    private final long timestamp;

    // ***********
    // CONSTRUCTOR
    // ***********

    public Event(String type, long timestamp) {
        this.type = type;
        this.timestamp = timestamp;
    }

    // **************
    // PUBLIC METHODS
    // **************

    public String getType() {
        return type;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
