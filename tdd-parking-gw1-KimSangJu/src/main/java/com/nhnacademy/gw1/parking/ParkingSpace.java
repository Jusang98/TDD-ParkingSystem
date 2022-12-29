package com.nhnacademy.gw1.parking;

import com.nhnacademy.gw1.parking.domain.Car;

import java.util.HashMap;
import java.util.Map;

public class ParkingSpace {
    private final Map<Car, String> parkinglist = new HashMap<>();

    public void comeInParkingSpace(Car car,String enterTime){
        if(parkinglist.size() >= 5){
            throw new NotEnoughParkingSpace();
        }
        if(car.getCarsize().equals("Large")){
            throw new NotAllowInLargeCar();
        }
        parkinglist.put(car,enterTime);
    }
    public void outParkingSpace(Car car){
        if(car.getUser().getAmount() < 0){
            throw new LackOfUserMoney();
        }
        parkinglist.remove(car);
    }

    public Map<Car, String> getParkinglist() {
        return parkinglist;
    }
}
