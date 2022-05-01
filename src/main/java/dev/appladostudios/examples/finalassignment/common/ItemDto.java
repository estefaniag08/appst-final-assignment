package dev.appladostudios.examples.finalassignment.common;

public class ItemDto {
    private final long code;
    private final String name;
    private final int units;
    private final Double unitPrice;

    public ItemDto(long code, String name, int units, Double unitPrice) {
        this.code = code;
        this.name = name;
        this.units = units;
        this.unitPrice = unitPrice;
    }

    public double getPrice(){
        return unitPrice;
    }
    public long getCode(){
        return code;
    }
    public String getName(){
        return name;
    }
    public int getUnits(){
        return units;
    }

}
