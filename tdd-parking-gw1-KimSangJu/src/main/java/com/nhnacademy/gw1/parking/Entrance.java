package com.nhnacademy.gw1.parking;

import com.nhnacademy.gw1.parking.domain.Car;


public interface Entrance {
    Car scan(int carNumber);
    String enterTimeCheck(String time);

}
