package congestion.calculator;

import congestion.calculator.util.DateUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Constants {

    private final static String VEHICLE = "vehicles.properties";
    private final static String CONGETSION_TAX = "congetsionTax.properties";

    public static Set<String> tollFreeVehicles;
    public static Map<String,String> congestionTax = new HashMap<>();

    static{
        Properties properties = new Properties();
        InputStream inputStream = DateUtils.class.getClassLoader().getResourceAsStream(VEHICLE);

        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String vehicles = properties.getProperty("tollFreeVehicles");
        String[] split = vehicles.split(",");

        tollFreeVehicles = new HashSet<>(Arrays.asList(split));

        properties.clear();
        inputStream = DateUtils.class.getClassLoader().getResourceAsStream(CONGETSION_TAX);
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Set<Object> objects = properties.keySet();

        for (Object object : objects) {
            String str = object.toString().replace(".","");
            congestionTax.put(str,properties.getProperty(object.toString()));
        }

    }


}
