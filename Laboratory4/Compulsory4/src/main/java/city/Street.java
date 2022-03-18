package city;

import java.util.Objects;

// A class which represents a street in the city as well as an edge in the city graph
public class Street {
    String name;
    Double length;
    String interId1, interId2;

    public Street(String name, Double length, String interId1, String interId2) {
        this.name = name;
        this.length = length;
        this.interId1 = interId1;
        this.interId2 = interId2;
    }

    public Street() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public String getInterId1() {
        return interId1;
    }

    public void setInterId1(String interId1) {
        this.interId1 = interId1;
    }

    public String getInterId2() {
        return interId2;
    }

    public void setInterId2(String interId2) {
        this.interId2 = interId2;
    }

    @Override
    public String toString() {
        return "Street{" +
                "name='" + name + '\'' +
                ", length=" + length +
                ", interId1='" + interId1 + '\'' +
                ", interId2='" + interId2 + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Street street = (Street) o;
        return Objects.equals(name, street.name) && Objects.equals(length, street.length) && Objects.equals(interId1, street.interId1) && Objects.equals(interId2, street.interId2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, length, interId1, interId2);
    }
}
