package room;

import java.util.Objects;

/** The ComputerLab class. It extends the Room abstract class, and it has a osType attribute (os type of the computers in the lab), which is defined in the enum OSType. */
public class ComputerLab extends Room{
    private OSType osType;

    public ComputerLab() {
    }

    public ComputerLab(String name, int capacity, OSType osType) {
        super(name, capacity);
        this.osType = osType;
    }

    public OSType getOSType() {
        return osType;
    }

    public void setOSType(OSType osType) {
        this.osType = osType;
    }

    @Override
    public String toString() {
        return "ComputerLab{" +
                "name='" + name + '\'' +
                ", capacity=" + capacity +
                ", osType=" + osType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ComputerLab that = (ComputerLab) o;
        return osType == that.osType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), osType);
    }
}
