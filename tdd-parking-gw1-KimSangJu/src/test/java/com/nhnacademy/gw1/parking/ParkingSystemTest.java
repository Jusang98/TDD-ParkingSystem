package com.nhnacademy.gw1.parking;

import com.nhnacademy.gw1.parking.domain.Car;
import com.nhnacademy.gw1.parking.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ParkingSystemTest {
    /*
    * 차가 들어올떄 차 번호 v
    * 차가 들어올떄 시간 v
    * 차가 더 많이 들어올경우 예외처리 v
    * 차가 나갈떄 시간 v
    * 차 시간의 차이 구해지는지 v
    * 일일 최대 주차시간 금액전까지 계산 v
    * 일일 최대 주차시간 금액 계산 v
    * 24시간 지날시 주차비 계산 v
    * 주차비 계산할때 가진 돈이 더 적은 경우 예외 처리 v
    * 대형차일떈 입장 x로 예외처리 v
    * 경차일떄 요금 50% 감면 v
    * 페이코 회원일때 10% 할인 v
    * */
    User user;
    Entrance entrance;
    Exit exit;
    ParkingSystem parkingSystem;
    ParkingSpace parkingSpace = new ParkingSpace();

    ParkingFeeCalculator parkingFeeCalculator = new ParkingFeeCalculator();

    @BeforeEach
    void setUp() {
        user = mock(User.class);
        entrance = mock(Entrance.class);
        exit = mock(Exit.class);
        parkingSystem = mock(ParkingSystem.class);
    }

    @DisplayName("차가 들어올떄 차 번호")
    @Test
    void scanCollect_thenEnterCar() {
        int carNumber = 1234;
        String carsize = "Small";
        Car car = new Car(carNumber, carsize, user);

        when(entrance.scan(carNumber)).thenReturn(car);

        assertThat(car).isEqualTo(entrance.scan(carNumber));
    }

    @DisplayName("차가 들어올떄 시간")
    @Test
    void enterTime_thenEnterCar() {
        String enterTime = "12:30:00";

        when(entrance.enterTimeCheck(enterTime)).thenReturn(enterTime);

        assertThat(enterTime).isEqualTo(entrance.enterTimeCheck(enterTime));
    }

    @DisplayName("차가 나갈떄 시간")
    @Test
    void outTime_thenOutCar() {
        String outTime = "12:30:00";

        when(exit.outTimeCheck(outTime)).thenReturn(outTime);

        assertThat(outTime).isEqualTo(exit.outTimeCheck(outTime));
    }

    @DisplayName("차가 더 많이 들어올경우 예외처리")
    @Test
    void enterCar_thenLackofParkSpace() {
        int carNumber =0;
        String enterTime = "12:30:00";
        String carsize = "Small";

        for(int i = 0; i<5; i++) {
            carNumber = i;
            Car car = new Car(carNumber, carsize, user);

            parkingSpace.comeInParkingSpace(car, enterTime);
        }

        Car car = new Car(carNumber, carsize, user);

        Assertions.assertThatThrownBy(() -> parkingSpace.comeInParkingSpace(car, enterTime))
                .isInstanceOf(NotEnoughParkingSpace.class)
                .hasMessageContaining("Not Enough ParkingSpace");
    }

    @DisplayName("차 시간의 차이 구해지는지")
    @Test
    void parkingTime_thenOutCar() {
        String enterTime = "12:30:00";
        String outTime = "14:30:00";
        long parkTime = 7200;

        assertThat(parkTime).isEqualTo(parkingFeeCalculator.timeDiffence(enterTime,outTime));
    }

    @DisplayName("일일 최대 주차시간 금액전까지 계산")
    @Test
    void parkingFee_thenOutCar(){
        String userId = "sangju";
        String payco = "No";
        long amount = 20000;
        int carNumber = 1234;
        String carsize = "Middle";
        String enterTime = "12:30:00";
        String outTime = "13:20:00";
        long finalUserMoney = 19000;

        User user = new User(userId,amount, payco);
        Car car = new Car(carNumber, carsize, user);

        assertThat(finalUserMoney).isEqualTo(parkingFeeCalculator.pay(car,enterTime,outTime));
    }

    @DisplayName("일일 최대 주차시간 금액 계산")
    @Test
    void parkingFee_thenMaximumFee(){
        String userId = "sangju";
        String payco = "No";
        long amount = 20000;
        int carNumber = 1234;
        String carsize = "Middle";
        String enterTime = "12:00:00";
        String outTime = "17:40:00";
        long finalUserMoney = 5000;

        User user = new User(userId,amount, payco);
        Car car = new Car(carNumber, carsize, user);

        assertThat(finalUserMoney).isEqualTo(parkingFeeCalculator.pay(car,enterTime,outTime));
    }

    @DisplayName("24시간 지날시 주차비 계산")
    @Test
    void parkingFee_thenMaximumParkingTime() {
        String userId = "sangju";
        String payco = "No";
        long amount = 30000;
        int carNumber = 1234;
        String carsize = "Middle";
        String enterTime = "00:00:00";
        String outTime = "47:59:59";
        long finalUserMoney = 0;

        User user = new User(userId,amount, payco);
        Car car = new Car(carNumber, carsize, user);

        assertThat(finalUserMoney).isEqualTo(parkingFeeCalculator.pay(car,enterTime,outTime));
    }

    @DisplayName("주차비 계산할때 가진 돈이 더 적은 경우 예외 처리")
    @Test
    void parkingFee_thenLackOfUserMoney() {
        String userId = "sangju";
        String payco = "No";
        long amount = 20000;
        int carNumber = 1234;
        String carsize = "Middle";
        String enterTime = "00:00:00";
        String outTime = "48:00:01";

        User user = new User(userId,amount, payco);
        Car car = new Car(carNumber, carsize, user);

        parkingFeeCalculator.pay(car,enterTime,outTime);

        Assertions.assertThatThrownBy(() -> parkingSpace.outParkingSpace(car))
                .isInstanceOf(LackOfUserMoney.class)
                .hasMessageContaining("Lack of User Money");
    }

    @DisplayName("대형차일떈 입장불가 예외처리")
    @Test
    void NotAllow_thenLargeCar(){
        int carNumber = 1234;
        String carsize = "Large";
        String enterTime = "00:00:00";
        Car car = new Car(carNumber, carsize, user);

        Assertions.assertThatThrownBy(() -> parkingSpace.comeInParkingSpace(car,enterTime))
                .isInstanceOf(NotAllowInLargeCar.class)
                .hasMessageContaining("Not Allow In Large Car");
    }

    @DisplayName("경차일떄 요금 50% 감면")
    @Test
    void parkingFee_thenSmallCar(){
        String userId = "sangju";
        String payco = "No";
        long amount = 20000;
        int carNumber = 1234;
        String carsize = "Small";
        String enterTime = "12:30:00";
        String outTime = "13:20:00";
        long finalUserMoney = 19500;

        User user = new User(userId,amount, payco);
        Car car = new Car(carNumber, carsize, user);

        assertThat(finalUserMoney).isEqualTo(parkingFeeCalculator.pay(car,enterTime,outTime));
    }

    @DisplayName("페이코 회원일때 10% 할인")
    @Test
    void parkingFee_thenPaycoUser(){
        String userId = "sangju";
        String payco = "Yes";
        long amount = 30000;
        int carNumber = 1234;
        String carsize = "Middle";
        String enterTime = "00:00:00";
        String outTime = "45:00:00";
        long finalUserMoney = 3000;

        User user = new User(userId,amount, payco);
        Car car = new Car(carNumber, carsize, user);

        assertThat(finalUserMoney).isEqualTo(parkingFeeCalculator.pay(car,enterTime,outTime));
    }

}