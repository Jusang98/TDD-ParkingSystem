package com.nhnacademy.gw1.parking;

import com.nhnacademy.gw1.parking.domain.Car;

public class ParkingSystem {
    private final Entrance entrance;
    private final Exit exit;
    private final ParkingSpace parkingSpace;
    private final ParkingFeeCalculator parkingFeeCalculator;



    public ParkingSystem(Entrance entrance, Exit exit, ParkingSpace parkingSpace, ParkingFeeCalculator parkingFeeCalculator) {
        this.entrance = entrance;
        this.exit = exit;
        this.parkingSpace = parkingSpace;
        this.parkingFeeCalculator = parkingFeeCalculator;
    }

    public void parkingCar(Car car, String enterTime) {
            entrance.scan(car.getCarNumber());
            entrance.enterTimeCheck(enterTime);
            parkingSpace.comeInParkingSpace(car,enterTime);
    }

    public void pullOutCar(Car car,String outTime){
            exit.outTimeCheck(outTime);
            parkingFeeCalculator.pay(car,parkingSpace.getParkinglist().get(car),outTime);
            parkingSpace.outParkingSpace(car);
    }
}
