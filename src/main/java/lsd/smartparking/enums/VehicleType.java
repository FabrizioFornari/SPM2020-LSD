package lsd.smartparking.enums;

public enum VehicleType {
    
    BICYCLE("Bicycle"),
    CAR("Car"),
    CARAVAN("Caravan"),
    MOTORCYCLE("Motorcycle");

    private final String value;  
    
  
    VehicleType(String value) {  
        this.value = value;  
    }  
    
    public static VehicleType fromValue(String value) {  
        if (value != null) {  
            for (VehicleType type : values()) {  
                if (type.value.equals(value)) {  
                    return type;  
                }  
            }  
        }  
        throw new IllegalArgumentException("Invalid vehicle type: " + value);
    }  
    
    public String toValue() {  
        return value;  
    }  

}