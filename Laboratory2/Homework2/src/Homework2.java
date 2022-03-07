import event.*;
import room.*;
import problem.*;

import java.time.LocalTime;

public class Homework2 {
    public static void main(String[] args) {
        /** We create a new instance of the problem, add items (events, rooms) to it, and print the respective lists.
         *  We will not allow duplicates in either of the events list or the rooms list of the instance. */
        Instance instanceOfProblem = new Instance();

        try {
            instanceOfProblem.addEvent(new Event("C1", EventType.COURSE, 100, LocalTime.of(8, 0), LocalTime.of(10, 0)));
            instanceOfProblem.addEvent(new Event("C2", EventType.COURSE, 100, LocalTime.of(10, 0), LocalTime.of(12, 0)));
            instanceOfProblem.addEvent(new Event("L1", EventType.LAB, 30, LocalTime.of(8, 0), LocalTime.of(10, 0)));
            instanceOfProblem.addEvent(new Event("L2", EventType.LAB, 30, LocalTime.of(8, 0), LocalTime.of(10, 0)));
            instanceOfProblem.addEvent(new Event("L3", EventType.SEMINAR, 30, LocalTime.of(10, 0), LocalTime.of(12, 0)));

            // instanceOfProblem.addRoom(new LectureHall("401", 30, true));
            instanceOfProblem.addRoom(new LectureHall("401", 30, true));
            instanceOfProblem.addRoom(new ComputerLab("403", 30, OSType.WIN32));
            instanceOfProblem.addRoom(new ComputerLab("405", 30, OSType.LINUX));
            instanceOfProblem.addRoom(new LectureHall("309", 100, false));
        }
        catch (IllegalArgumentException err) {
            err.printStackTrace();
        }

        System.out.println(instanceOfProblem.getEvents());
        System.out.println(instanceOfProblem.getRooms());
        System.out.print("\n\n\n");

        Solution solutionForProblem = new Solution();
        solutionForProblem.roomToListOfEventsMapping(instanceOfProblem);
        solutionForProblem.printSolution();
    }
}
