package com.utm.zooworkers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cashier extends ZooWorker {
    private int ticketPrice;
    private int tips;

    public Cashier() {
        super();
    }

    @Override
    public double getFullSalary() {
        return getSalary() + getSalary() * 0.04 * getExperience() + getTips() * 140;
    }

    public void sellTicket(boolean asked, int age) {
        if (asked && age < 18) {
            System.out.println("Cashier sells a ticket for " + ticketPrice / 2 + " dollars.");
        } else if (asked) {
            System.out.println("Cashier sells a ticket for " + ticketPrice + " dollars.");
        } else {
            System.out.println("Cashier: Sorry, I don't have tickets :( ");
        }
    }
}
