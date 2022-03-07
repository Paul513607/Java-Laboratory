package problem;

import event.*;
import room.*;

import java.util.*;

/** The solution class represents a possible mapping of the events to the rooms of the Instance class, such that a minimum number of Rooms is used. */
public class Solution {
    int eventsSize = 0;
    Map<Room, ArrayList<Event>> roomsToListsOfEvents= new HashMap<>();
    Map<Event, Room> eventsToRooms = new HashMap<>();

    public Solution() {
    }

    public Map<Event, Room> getEventsToRooms() {
        return eventsToRooms;
    }

    public void setEventsToRooms(Map<Event, Room> eventsToRooms) {
        this.eventsToRooms = eventsToRooms;
    }

    public Map<Room, ArrayList<Event>> getRoomsToListsOfEvents() {
        return roomsToListsOfEvents;
    }

    public void setRoomsToListsOfEvents(Map<Room, ArrayList<Event>> roomsToListsOfEvents) {
        this.roomsToListsOfEvents = roomsToListsOfEvents;
    }

    @Override
    public String toString() {
        return "Solution{" +
                "eventsToRooms=" + eventsToRooms +
                '}';
    }

    static public boolean checkIfEventValid(Event currentEvent, ArrayList<Event> roomEvents) {
        for (Event event : roomEvents) {
            if (event.getEndTime().compareTo(currentEvent.getStartTime()) > 0)
                return false;
        }
        return true;
    }

    /** 'eventToRoomMapping()' is an algorithm that tries to map the events to rooms, such that the number of rooms is minimal */
    public void roomToListOfEventsMapping(Instance instanceOfProblem) {
        ArrayList<Event> events = instanceOfProblem.getEvents();
        eventsSize = events.size();

        ArrayList<Room> rooms = instanceOfProblem.getRooms();
        ArrayList<Boolean> eventsAssigned = new ArrayList<>(events.size());
        for (int i = 0; i < events.size(); ++i)
            eventsAssigned.add(false);

        /** We sort both of the arrays in descending order of size/capacity */
        Collections.sort(events, (o1, o2) -> (-1) * (((Integer) o1.getSize()).compareTo(o2.getSize())));
        Collections.sort(rooms, (o1, o2) -> (-1) * ((Integer) o1.getCapacity()).compareTo(o2.getCapacity()));

        /** For each room, we check if we can fit each event that hasn't been used before. We add events to a particulat room only if they don't intersect. */
        for (Room currentRoom : rooms) {
            for (Event event : events) {
                if (!roomsToListsOfEvents.containsKey(currentRoom))
                    roomsToListsOfEvents.put(currentRoom, new ArrayList<>());
                int currEventIndex = events.indexOf(event);
                if (event.getSize() <= currentRoom.getCapacity() && !eventsAssigned.get(currEventIndex) &&
                        checkIfEventValid(event, roomsToListsOfEvents.get(currentRoom))) {
                    roomsToListsOfEvents.get(currentRoom).add(event);
                    eventsAssigned.set(currEventIndex, true);
                }
            }
        }
    }

    /** We also convert the room-to-events format to an event-to-room format. */
    public void convertToEventsToRoomsMap() {
        for (Room room : roomsToListsOfEvents.keySet())
            for (Event event : roomsToListsOfEvents.get(room))
                eventsToRooms.put(event, room);
    }

    /** Finally, if we get less events in the solution than we got in the instance, we know we could not fit every event.
     *  Otherwise we print the solution. */
    public void printSolution() {
        this.convertToEventsToRoomsMap();
        if (eventsToRooms.keySet().size() < eventsSize)
            System.out.println("Could not fit every event.");
        else {
            eventsToRooms.entrySet().stream()
                    .forEach(pair -> System.out.println(pair.getKey().getName() + " -> " + pair.getValue().getName()));
        }
    }
}
