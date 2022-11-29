package com.utm.util;

import com.utm.animals.Animal;
import com.utm.miscellaneous.Cage;

import java.util.Arrays;
import java.util.List;

public class Printer {
    public static void printSimulationHour(int simulationHour) {
        System.out.println("Time " + simulationHour + ":00");
    }

    public static void printTicketsWithDiscount(boolean isDiscountAvailable) {
        if (isDiscountAvailable) {
            System.out.println("DISCOUNT FOR TICKETS!");
        }
    }

    public static void printCashierRude() {
        System.out.println("Cashier was rude");
    }

    public static void printAnimalSounds(List<Cage> listOfCages) {
        System.out.println("Animals:");
        for (Cage cage : listOfCages) {
            cage.getAnimalList().get(0).makeSound();
        }
    }
}
