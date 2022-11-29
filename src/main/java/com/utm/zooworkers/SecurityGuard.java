package com.utm.zooworkers;

import com.utm.util.StaticUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SecurityGuard extends ZooWorker {
    private int hoursSlept;
    private boolean isSleeping;

    public SecurityGuard() {
        super();
        this.hoursSlept = 0;
        this.isSleeping = false;
    }

    public void updateSleeping() {
        this.setSleeping(StaticUtils.random.nextInt(100) % 2 == 0);
    }
}
