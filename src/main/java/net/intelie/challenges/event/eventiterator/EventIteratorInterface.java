package net.intelie.challenges.event.eventiterator;

import net.intelie.challenges.event.Event;

/**
 * An iterator over an event collection.
 */
public interface EventIteratorInterface extends AutoCloseable {

    /**
     * Move the iterator to the next event, if any.
     *
     * @return false if the iterator has reached the end, true otherwise.
     */
    boolean moveNext();

    /**
     * Gets the current event ref'd by this iterator.
     *
     * @return the event itself.
     * @throws IllegalStateException if {@link #moveNext} was never called
     *                               or its last result was {@code false}.
     */
    Event current();

    /**
     * Remove current event from its store.
     *
     * @throws IllegalStateException if {@link #moveNext} was never called
     *                               or its last result was {@code false}.
     */
    void remove();
}
