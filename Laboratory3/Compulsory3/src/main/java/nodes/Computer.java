package nodes;

import java.math.BigDecimal;
import java.util.Objects;

// A computer is a node that is identifiable and has storage
public class Computer extends Node implements Identifiable, Storage{
    private String ipAddress;
    private double storageCapacity;

    public Computer(String name, String macAddress, Location mapLocation, String ipAddress, double storageCapacity) {
        super(name, macAddress, mapLocation);
        this.ipAddress = ipAddress;
        this.storageCapacity = storageCapacity;
    }


    @Override
    public String getIpAddress() {
        return ipAddress;
    }

    @Override
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public double getStorageCapacity() {
        return storageCapacity;
    }

    @Override
    public void setStorageCapacity(double storageCapacity) {
        this.storageCapacity = storageCapacity;
    }

    @Override
    public String toString() {
        return "Computer{" +
                "name='" + name + '\'' +
                ", macAddress='" + macAddress + '\'' +
                ", mapLocation=" + mapLocation +
                ", ipAddress='" + ipAddress + '\'' +
                ", storageCapacity=" + storageCapacity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Computer computer = (Computer) o;
        return Double.compare(computer.storageCapacity, storageCapacity) == 0 && Objects.equals(ipAddress, computer.ipAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), ipAddress, storageCapacity);
    }

    // We override the default method 'getCapacityInUnit' from the interface Storage
    @Override
    public BigDecimal getCapacityInUnit(BytesUnits unit) {
        BigDecimal bigStorageCapacity = BigDecimal.valueOf(storageCapacity);
        switch (unit) {
            case B:
                bigStorageCapacity = bigStorageCapacity.multiply(BigDecimal.valueOf(1000000000));
                break;
            case KB:
                bigStorageCapacity = bigStorageCapacity.multiply(BigDecimal.valueOf(1000000));
                break;
            case MB:
                bigStorageCapacity = bigStorageCapacity.multiply(BigDecimal.valueOf(1000));
                break;
            case GB:
                break;
            case TB:
                bigStorageCapacity = bigStorageCapacity.divide(BigDecimal.valueOf(1000));
                break;
        }
        return bigStorageCapacity;
    }
}
