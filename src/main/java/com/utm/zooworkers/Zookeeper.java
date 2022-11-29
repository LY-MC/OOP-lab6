package com.utm.zooworkers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Zookeeper extends ZooWorker {
    private boolean isFeeding;
    private boolean isCleaning;
    private int speed = 2;

    public Zookeeper() {
        super();
    }
}
