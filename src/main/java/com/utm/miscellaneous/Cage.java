package com.utm.miscellaneous;

import com.utm.animals.*;
import com.utm.enums.AnimalType;
import com.utm.root.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
public class Cage extends Entity {
    @Setter
    private int width;
    @Setter
    private int length;
    private final List<Animal> animalList = new ArrayList<>();

    public Cage() {
        super();
        Random random = new Random();
        this.width = random.nextInt(250) + 50;
        this.length = random.nextInt(250) + 50;
    }

    public void populateCage(int count, AnimalType animalType) {
        while (count-- > 0) {
            switch (animalType) {
                case MONKEY:
                    this.animalList.add(new Monkey());
                    break;
                case ELEPHANT:
                    this.animalList.add(new Elephant());
                    break;
                case HORSE:
                    this.animalList.add(new Horse());
                    break;
                case LION:
                    this.animalList.add(new Lion());
                    break;
                case DUCK:
                    this.animalList.add(new Duck());
                    break;
            }
        }
    }

    public int countHungryAnimals() {
        return (int) animalList.stream()
                .filter(Animal::isHungry)
                .count();
    }

    public int countIllAnimals() {
        return (int) animalList.stream()
                .filter(Animal::isIll)
                .count();
    }

    public void feedAnimals(int counter, int wrongFood) {
        int myCounter = 0;
        for (Animal animal : animalList) {
            if (animal.isHungry()) {
                animal.setHungry(false);
                if (wrongFood % 10 == 0) {
                    animal.setIll(true);
                }
                myCounter++;
                if (counter == myCounter) {
                    break;
                }
            }
        }
    }

    public void becomeHungry() {
        for (Animal animal : animalList) {
            if (!animal.isHungry()) {
                animal.setHoursFed(animal.getHoursFed() + 1);
            }

            if (animal.getHoursFed() > 4) {
                animal.setHungry(true);
                animal.setHoursFed(0);
            }
        }
    }

    public void treatAnimals() {
        for (Animal animal : animalList) {
            animal.setIll(false);
        }
    }

    public void treatAnimal() {
        for (Animal animal : animalList) {
            if (animal.isIll()) {
                animal.setIll(false);
                break;
            }
        }
    }

    public void printAnimalsInCage() {
        for (Animal animal : animalList) {
            System.out.println(animal.toString());
        }
    }
}
