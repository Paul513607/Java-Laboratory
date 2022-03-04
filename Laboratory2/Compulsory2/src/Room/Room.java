package Room;

public class Room {
    private String name;
    RoomType type;
    private int capacity;

    public Room() {
    }

    public Room(String name, RoomType type, int capacity) {
        this.name = name;
        this.type = type;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        String roomType;
        switch (type) {
            case LECTURE_HALL:
                roomType = "Lecture Hall";
                break;
            case COMPUTER_LAB:
                roomType = "Computer Lab";
                break;
            default:
                roomType = "Unknown";
        }
        return "Room{" +
                "name='" + name + '\'' +
                ", type='" + roomType + '\'' +
                ", capacity=" + capacity +
                '}';
    }
}
