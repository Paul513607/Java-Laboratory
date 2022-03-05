package Nodes;

import java.math.BigDecimal;

public interface Storage {
    enum BytesUnits {
        TB, GB, MB, KB, B
    }

    double getStorageCapacity();
    void setStorageCapacity(double storageCapacity);

    default BigDecimal getCapacityInUnit(BytesUnits unit) {
        System.out.println("Storage capacity not available.");
        return BigDecimal.valueOf(0);
    }
}
