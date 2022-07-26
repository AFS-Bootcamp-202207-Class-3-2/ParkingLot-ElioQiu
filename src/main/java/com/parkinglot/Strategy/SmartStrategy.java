package com.parkinglot.Strategy;

import com.parkinglot.Car;
import com.parkinglot.Exceptions.NoAvailablePositionException;
import com.parkinglot.ParkingLot;
import com.parkinglot.Ticket;

import java.util.Comparator;
import java.util.List;

public class SmartStrategy implements ParkingStrategy{

    public SmartStrategy() {
    }

    @Override
    public Ticket park(Car car, List<ParkingLot> parkingLotList) {
        ParkingLot parkingLot = parkingLotList.stream()
                .filter(ParkingLot::haveCapacity)
                .max(Comparator.comparingInt(ParkingLot::getCurrentCapacity))
                .orElseThrow(NoAvailablePositionException::new);
        return parkingLot.park(car);
    }

}
