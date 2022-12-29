package com.nhnacademy.gw1.parking;

import com.nhnacademy.gw1.parking.domain.Car;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ParkingFeeCalculator {
    private final long standardParkingTime = 1800;
    private final long standardParkingFee = 0;
    private final long oneHourParkingTime = 3600;
    private final long oneHourParkingFee = 1000;
    private final long additionalTime = 600;
    private final long additionalFee = 500;
    private final long maximumFeeTime = 20400;
    private final long maximumFee = 15000;
    private final long maximumParkingTime = 86400;
    private long parkingFee = 0;
    private long units = 0;

    public long pay(Car car, String enterTime, String outTime) {
        long parkingTime = timeDiffence(enterTime, outTime);
        long userAmount = car.getUser().getAmount();
        if (parkingTime < standardParkingTime) {
            parkingFee += standardParkingFee;
            car.getUser().setAmount(userAmount - parkingFee);
        } else if (parkingTime > standardParkingTime && parkingTime < oneHourParkingTime) {
            parkingFee += oneHourParkingFee;
            car.getUser().setAmount(userAmount - parkingFee);
        } else if (parkingTime > oneHourParkingTime && parkingTime < maximumFeeTime) {
            units = parkingTime / additionalTime;
            parkingFee += units * additionalFee;
            if (parkingTime % additionalTime != 0) {
                parkingFee += additionalFee;
            }
            car.getUser().setAmount(userAmount - parkingFee);
        } else if (parkingTime < maximumParkingTime) {
            car.getUser().setAmount(userAmount - maximumFee);
        }
        else {
            units = (parkingTime / maximumParkingTime) +1;
            car.getUser().setAmount(userAmount - (maximumFee * units));
        }
        if(parkingTime < maximumParkingTime && parkingTime > standardParkingTime && car.getCarsize().equals("Small")){
            return car.getUser().getAmount() + (parkingFee / 2);
        }
        else if(parkingTime > maximumParkingTime && car.getCarsize().equals("Small")){
            return car.getUser().getAmount() +(userAmount/2);
        }
        if(parkingTime < maximumParkingTime && parkingTime > standardParkingTime && car.getUser().getPayco().equals("Yes")){
            return car.getUser().getAmount() + (parkingFee / 10);
        }
        else if(parkingTime > maximumParkingTime && car.getUser().getPayco().equals("Yes")){
            return car.getUser().getAmount() +(userAmount/10);
        }
        return car.getUser().getAmount();
    }

    public long timeDiffence(String enterTime, String outTime) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            Date startDate = simpleDateFormat.parse(enterTime);
            Date endDate = simpleDateFormat.parse(outTime);
            long startTimeMil = startDate.getTime();
            long endTimeMil = endDate.getTime();
            long parkTimeMil = endTimeMil - startTimeMil;

            return parkTimeMil / 1000;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
