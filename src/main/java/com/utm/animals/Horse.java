package com.utm.animals;

import java.util.Random;

public class Horse extends Animal {
    Random random = new Random();

    public Horse() {
        super();

        setAge(random.nextInt(29) + 1);
        setMale(random.nextBoolean());
        setIll(random.nextInt(100) % 10 == 0);
        setHungry(random.nextBoolean());
        setWeight(random.nextInt(90) + 360);
        setHeight(random.nextInt(15) + 140);
    }

    public boolean isRideable() {
        if (isIll()) {
            System.out.println("Horse is ill and doesn't want to ride someone");
            return false;
        }

        if (random.nextInt(100) % 40 == 0) {
            System.out.println("It's unsafe to ride this horse");
            return false;
        }

        System.out.println("Child rides a horse");
        return true;
    }

    @Override
    public void makeSound() {
        System.out.println("Horse" + " : " + "Woo hoo hoo hoooo");
    }

    @Override
    public String toString() {
        return "Horse{" +
                "weight=" + getWeight() +
                ", height=" + getHeight() +
                ", age=" + getAge() +
                ", isMale=" + isMale() +
                ", isIll=" + isIll() +
                ", isHungry=" + isHungry() +
                ", ID=" + getID() +
                '}';
    }
}
