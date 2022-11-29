package com.utm.zooworkers;

import com.utm.intermidLvl.Person;
import com.utm.util.StaticUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ZooWorker extends Person {
    private int salary;
    private int experience;

    public ZooWorker() {
        super();
        this.salary = StaticUtils.random.nextInt(1500) + 1000;
        this.experience = StaticUtils.random.nextInt(6) + 1;
    }

    public double getFullSalary() {
        return salary + salary * 0.04 * experience;
    }
}
