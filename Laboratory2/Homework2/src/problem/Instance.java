package problem;

import event.Event;
import room.Room;

import java.util.ArrayList;

/** The Instance class. The instance of the problem contains a list of events, and a list of rooms.
 *  The Instance class also has methods useful when processing the data in the Solution class. */
public class Instance {
    ArrayList<Event> events = new ArrayList<>();
    ArrayList<Room> rooms = new ArrayList<>();

    public Instance() {
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public String toString() {
        return "Instance{" +
                "events=" + events +
                ", rooms=" + rooms +
                '}';
    }

    public void addEvent(Event e) throws IllegalArgumentException {
        if (!events.contains(e))
            events.add(e);
        else
            throw new IllegalArgumentException("Event " + e + "already exists as an instance of this problem!");
    }

    public void addRoom(Room r) throws IllegalArgumentException {
        if (!rooms.contains(r))
            rooms.add(r);
        else
            throw new IllegalArgumentException("Room " + r + "already exists as an instance of this problem!");
    }

    public void removeEvent(Event e) {
        if (events.contains(e))
            events.remove(e);
    }

    public void removeRoom(Room r) {
        if (rooms.contains(r))
            rooms.remove(r);
    }

    public Event getEventAtIndex(int index) {
        return events.get(index);
    }

    public int getIndexOfEvent(Event e) {
        return events.indexOf(e);
    }

    public Room getRoomAtIndex(int index) {
        return rooms.get(index);
    }

    public int getIndexOfRoom(Room r) {
        return rooms.indexOf(r);
    }
}
