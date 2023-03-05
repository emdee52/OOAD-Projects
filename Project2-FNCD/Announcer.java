/*
 * 
 * This file is used to help implement the Observer pattern
 * 
 * 
 */

import java.util.ArrayList;
import java.util.List;

public class Announcer {
    private List<Listener> listeners = new ArrayList<>();

    public void addListener(Listener toAdd) {
        listeners.add(toAdd);
    }

    public void publishEvent(String event, int day) {
        for (Listener listener : listeners) // Notify every listener/subscriber object.
            listener.notifyEvent(event, day);
    }
}