package com.parkinglot;

import com.parkinglot.Exceptions.NoAvailablePositionException;
import com.parkinglot.Exceptions.UnrecognizedParkingTicketException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ParkingLot {
    private static int DEFAULT_CAPACITY = 10;
    private int currentCapacity;
    private final int totalCapacity;
    private List<Car> parkedCarList;

    public ParkingLot() {
        this.totalCapacity = DEFAULT_CAPACITY;
        this.currentCapacity = totalCapacity;
        this.parkedCarList = new ArrayList<>();
    }

    public ParkingLot(int capacity) {
        this.totalCapacity = capacity;
        this.currentCapacity = totalCapacity;
        this.parkedCarList = new ArrayList<>();
    }

    public Ticket park(Car car) {
        if (currentCapacity == 0) {
            throw new NoAvailablePositionException();
        }
        currentCapacity--;
        this.parkedCarList.add(car);
        return new Ticket(car.getId());
    }

    public Car fetch(Ticket ticket) {
        if (isCarInThisLot(ticket)) {
            this.parkedCarList = parkedCarList.stream().filter(
                    parkedCar -> parkedCar.getId() != ticket.getParkingLotId()
                    ).collect(Collectors.toList());
            currentCapacity++;
            return new Car(ticket.getParkingLotId());
        }
        throw new UnrecognizedParkingTicketException();
    }

    public boolean haveCapacity() {
        return currentCapacity != 0;
    }

    public boolean isCarInThisLot(Ticket ticket) {
        return parkedCarList.stream().filter(
                parkedCar -> parkedCar.getId() == ticket.getParkingLotId()
        ).findAny().isPresent();
    }
}
