package net.intelie.challenges;

import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.TestFramework;
import org.junit.Test;


/**
 * Asserts that multiple threads can insert and look at the same EventStore
 * in a safe-thread way
 */
public class MultithreadedTest extends MultithreadedTestCase {

    // **************
    // PRIVATE FIELDS
    // **************

    private final EventStoreTestHelper EVENT_STORE_HELPER = new EventStoreTestHelper();

    // ***********
    // INITIALIZER
    // ***********

    @Override
    public void initialize() {
        EVENT_STORE_HELPER.createEventStoreWithPredefinedValues();
    }

    // ******************
    // THREADS EXECUTIONS
    // ******************

    // Thread is adding values to EventStore
    public void thread1() {
        EVENT_STORE_HELPER.insertRandomValue();
    }

    // Thread is adding values to EventStore
    public void thread2() {
        EVENT_STORE_HELPER.insertRandomValue();
    }

    // Thread is going through the values not touched by other Threads
    public void thread3() {
        EVENT_STORE_HELPER.assertPreexistingEvent();
    }

    // Thread is going through the values not touched by other Threads
    // and moving through values touched by other Threads
    public void thread4() {
        EVENT_STORE_HELPER.assertPreexistingEvent();
        EVENT_STORE_HELPER.moveThroughThreadEvent();
    }

    // Thread is adding values to EventStore
    // and is going through the values not touched by other Threads
    // and moving through values touched by other Threads
    public void thread5() {
        EVENT_STORE_HELPER.insertRandomValue();
        EVENT_STORE_HELPER.assertPreexistingEvent();
        EVENT_STORE_HELPER.moveThroughThreadEvent();
    }

    // *********
    // FINALIZER
    // *********

    // At the end of each test suite, see if everything still works
    @Override
    public void finish() {
        EVENT_STORE_HELPER.assertPreexistingEvent();
        EVENT_STORE_HELPER.moveThroughThreadEvent();
        EVENT_STORE_HELPER.insertRandomValue();
        EVENT_STORE_HELPER.assertPreexinstingEventWithTimeStamp();
    }

    // *****
    // TESTS
    // *****

    @Test
    public void testEventStoreWithMultithreading() throws Throwable {
        // Running the entire suite 1000 times
        TestFramework.runManyTimes(new MultithreadedTest(), 1000);
    }
}