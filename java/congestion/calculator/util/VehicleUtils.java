package congestion.calculator.util;

import congestion.calculator.Constants;
import congestion.calculator.vo.Vehicle;

public class VehicleUtils {

    public static boolean isTollFreeVehicle(Vehicle vehicle) {
        if (vehicle == null) return false;
        String vehicleType = vehicle.getVehicleType();
        return Constants.tollFreeVehicles.contains(vehicleType);
    }
}
