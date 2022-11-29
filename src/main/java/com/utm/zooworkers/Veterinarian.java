package com.utm.zooworkers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Veterinarian extends ZooWorker {
    private boolean isTreating;

    public Veterinarian() {
        super();
    }
}
