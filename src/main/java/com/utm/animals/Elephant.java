package com.utm.animals;

import java.util.Random;

public class Elephant extends Animal {
    public Elephant() {
        super();
        Random random = new Random();
        setMale(random.nextBoolean());
        setIll(random.nextInt(100) % 10 == 0);
        setHungry(random.nextBoolean());
        setAge(random.nextInt(32) + 1);
        if (getAge() < 4 && isMale()) {
            setWeight(random.nextInt(70) + 50);
            setHeight(random.nextInt(70) + 40);
        } else if (getAge() < 4 && !isMale()) {
            setWeight(random.nextInt(60) + 50);
            setHeight(random.nextInt(50) + 40);
        } else if (getAge() > 4 && isMale()) {
            setWeight(random.nextInt(4000) + 2500);
            setHeight(random.nextInt(200) + 200);
        } else if (getAge() > 4 && !isMale()) {
            setWeight(random.nextInt(3000) + 2500);
            setHeight(random.nextInt(100) + 200);
        }
    }


    @Override
    public void makeSound() {
        System.out.println("Elephant" + " : " + "ugh, ugh");
    }

    @Override
    public String toString() {
        return "Elephant{" +
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
