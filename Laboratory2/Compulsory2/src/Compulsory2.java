import Utilities.*;

import java.time.LocalTime;
import java.util.ArrayList;

public class Compulsory2 {
    public static void main(String[] args) {
        ArrayList<Event> events = new ArrayList<>();
        ArrayList<Room> rooms = new ArrayList<>();

        events.add(new Event(EventType.COURSE, 100, LocalTime.of(8, 0), LocalTime.of(10, 0)));        events.add(new Event(EventType.COURSE, 100, LocalTime.of(8, 0), LocalTime.of(10, 0)));
        events.add(new Event(EventType.COURSE, 100, LocalTime.of(10, 0), LocalTime.of(12, 0)));
        events.add(new Event(EventType.LAB, 30, LocalTime.of(8, 0), LocalTime.of(10, 0)));
        events.add(new Event(EventType.LAB, 30, LocalTime.of(8, 0), LocalTime.of(10, 0)));
        events.add(new Event(EventType.SEMINAR, 30, LocalTime.of(10, 0), LocalTime.of(12, 0)));

        rooms.add(new Room("401", RoomType.COMPUTER_LAB, 30));
        rooms.add(new Room("403", RoomType.COMPUTER_LAB, 30));
        rooms.add(new Room("405", RoomType.COMPUTER_LAB, 30));
        rooms.add(new Room("309", RoomType.LECTURE_HALL, 100));

        System.out.println(events);
        System.out.println(rooms);
    }
}
