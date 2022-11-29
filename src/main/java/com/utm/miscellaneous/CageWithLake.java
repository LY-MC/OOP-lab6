package com.utm.miscellaneous;

import lombok.Getter;
import lombok.Setter;

import java.util.Random;

@Getter
@Setter
public class CageWithLake extends Cage {
    private int lakeWidth;
    private int lakeLength;

    public CageWithLake() {
        super();
        Random random = new Random();
        this.lakeWidth = this.getWidth() - random.nextInt(150) + 25;
        this.lakeLength = this.getLength() - random.nextInt(150) + 25;
    }
}
