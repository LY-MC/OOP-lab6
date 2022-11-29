package com.utm;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import com.utm.animals.Horse;
import com.utm.clients.Adult;
import com.utm.clients.Child;
import com.utm.clients.Client;
import com.utm.enums.AnimalType;
import com.utm.miscellaneous.Cage;
import com.utm.miscellaneous.CageWithLake;
import com.utm.util.Printer;
import com.utm.util.StaticUtils;
import com.utm.zooworkers.Cashier;
import com.utm.zooworkers.SecurityGuard;
import com.utm.zooworkers.Veterinarian;
import com.utm.zooworkers.Zookeeper;

public class Simulation {
    private final Cage monkeyCage;
    private final Cage elephantCage;
    private final Cage horseCage;
    private final Cage lionCage;
    private final CageWithLake duckCage;
    private Client client;
    private final Cashier cashier;
    private final Veterinarian veterinarian;
    private final Zookeeper zookeeper;
    private final SecurityGuard securityGuard;

    private Properties props;

    int simulationHour = StaticUtils.OPENING_HOUR;

    boolean discountAvailable;

    boolean clientCanEnter;

    boolean clientWantsToEnter;

    int wrongFood;

    public Simulation(Cage monkeyCage, Cage elephantCage, Cage horseCage, Cage lionCage, CageWithLake duckCage,
                      Cashier cashier, Veterinarian veterinarian, Zookeeper zookeeper,
                      SecurityGuard securityGuard) {
        this.monkeyCage = monkeyCage;
        this.elephantCage = elephantCage;
        this.horseCage = horseCage;
        this.lionCage = lionCage;
        this.duckCage = duckCage;
        this.cashier = cashier;
        this.veterinarian = veterinarian;
        this.zookeeper = zookeeper;
        this.securityGuard = securityGuard;
    }

    void initProps() throws IOException {
        props = new Properties();
        FileInputStream ip = new FileInputStream(System.getProperty("user.dir")
                + "/src/main/resources/config.properties");
        props.load(ip);
    }

    void populateCages() {
        monkeyCage.populateCage(Integer.parseInt(props.getProperty("numberOfMonkeys")), AnimalType.MONKEY);
        elephantCage.populateCage(Integer.parseInt(props.getProperty("numberOfElephants")), AnimalType.ELEPHANT);
        horseCage.populateCage(Integer.parseInt(props.getProperty("numberOfHorses")), AnimalType.HORSE);
        lionCage.populateCage(Integer.parseInt(props.getProperty("numberOfLions")), AnimalType.LION);
        duckCage.populateCage(Integer.parseInt(props.getProperty("numberOfDucks")), AnimalType.DUCK);
    }

    void printCagesContents() throws InterruptedException {
        System.out.println("In cages we have:");
        elephantCage.printAnimalsInCage();
        horseCage.printAnimalsInCage();
        lionCage.printAnimalsInCage();
        monkeyCage.printAnimalsInCage();
        duckCage.printAnimalsInCage();
        Thread.sleep(5000);
        System.out.println();
    }

    void initAnimalsAge() {
        int noElephant = StaticUtils.random.nextInt(Integer.parseInt(props.getProperty("numberOfElephants")));
        int noHorse = StaticUtils.random.nextInt(Integer.parseInt(props.getProperty("numberOfHorses")));
        int noLion = StaticUtils.random.nextInt(Integer.parseInt(props.getProperty("numberOfLions")));
        int noMonkey = StaticUtils.random.nextInt(Integer.parseInt(props.getProperty("numberOfMonkeys")));
        int noDuck = StaticUtils.random.nextInt(Integer.parseInt(props.getProperty("numberOfDucks")));

        if (elephantCage.getAnimalList().get(noElephant).getAge()
                > Integer.parseInt(props.getProperty("elephantOldAge"))) {
            elephantCage.getAnimalList().get(noElephant).setIll(true);
        }
        if (horseCage.getAnimalList().get(noHorse).getAge() > Integer.parseInt(props.getProperty("horseOldAge"))) {
            horseCage.getAnimalList().get(noHorse).setIll(true);
        }
        if (lionCage.getAnimalList().get(noLion).getAge() > Integer.parseInt(props.getProperty("lionOldAge"))) {
            lionCage.getAnimalList().get(noLion).setIll(true);
        }
        if (monkeyCage.getAnimalList().get(noMonkey).getAge() > Integer.parseInt(props.getProperty("monkeyOldAge"))) {
            monkeyCage.getAnimalList().get(noMonkey).setIll(true);
        }
        if (duckCage.getAnimalList().get(noDuck).getAge() > Integer.parseInt(props.getProperty("duckOldAge"))) {
            duckCage.getAnimalList().get(noDuck).setIll(true);
        }
    }

    void initSimulation() throws IOException, InterruptedException {
        initProps();
        populateCages();
        printCagesContents();
        initAnimalsAge();
    }

    void incrementHour() {
        simulationHour++;

        if (simulationHour >= StaticUtils.CLOSING_HOUR) {
            simulationHour = StaticUtils.OPENING_HOUR;
        }

        Printer.printSimulationHour(simulationHour);
    }

    void checkForDiscount() {
        discountAvailable = simulationHour == Integer.parseInt(props.getProperty("timeForDiscount"));
        Printer.printTicketsWithDiscount(discountAvailable);
    }

    void handleClientBehavior() {
        int randomSeed = StaticUtils.random.nextInt(100);
        clientWantsToEnter = randomSeed % 2 == 0;
        clientCanEnter = randomSeed % 20 != 0;
        int newAge = StaticUtils.random.nextInt(80);
        final int AGE_OF_MAJORITY = Integer.parseInt(props.getProperty("ageOfMajority"));

        if (clientWantsToEnter) {
            if (newAge < AGE_OF_MAJORITY) {
                client = new Child();
            } else {
                client = new Adult();
            }

            client.setAge(newAge);
            client.buyTicket();

            if (simulationHour < Integer.parseInt(props.getProperty("timeForDiscount"))) {
                cashier.setTicketPrice(Integer.parseInt(props.getProperty("ticketPrice")));
            } else {
                cashier.setTicketPrice(Integer.parseInt(props.getProperty("ticketPriceWithDiscount")));
            }
            cashier.sellTicket(clientCanEnter, newAge);

            client.enterZoo(clientCanEnter);
            if (randomSeed % 18 == 0) {
                Printer.printCashierRude();
                client.setHappiness(75);
            } else {
                client.setHappiness(100);
            }

            if (clientCanEnter) {
                Printer.printAnimalSounds(Arrays.asList(duckCage, elephantCage, horseCage, lionCage, monkeyCage));
            }
        }
    }

    void handleSecurityGuardBehavior() {
        securityGuard.updateSleeping();
        if (securityGuard.isSleeping()) {
            securityGuard.setHoursSlept(securityGuard.getHoursSlept() + 1);
        }
        if (securityGuard.getHoursSlept() > 2) {
            securityGuard.setSleeping(false);
            securityGuard.setHoursSlept(0);
        }
        if (securityGuard.isSleeping() && !clientCanEnter) {
            System.out.println("Security guard is sleeping");
            System.out.println("Client sneaks into the zoo");
            System.out.println("Animals:");
            elephantCage.getAnimalList().get(0).makeSound();
            for (Cage cage : Arrays.asList(horseCage, lionCage, monkeyCage)) {
                cage.getAnimalList().get(0).makeSound();
            }
            duckCage.getAnimalList().get(0).makeSound();
        } else if (securityGuard.isSleeping()) {
            System.out.println("Security guard is sleeping");
        } else {
            System.out.println("Security guard is not sleeping");
        }
    }

    void handleCleaningAndFeeding() {
        int counterHungryDucks = duckCage.countHungryAnimals();
        int counterHungryMonkeys = monkeyCage.countHungryAnimals();
        int counterHungryElephants = elephantCage.countHungryAnimals();
        int counterHungryHorses = horseCage.countHungryAnimals();
        int counterHungryLions = lionCage.countHungryAnimals();
        zookeeper.setFeeding(true);
        wrongFood = StaticUtils.random.nextInt(100);
        if (simulationHour == 8 || simulationHour == 15 || simulationHour == 20) {
            System.out.println("Zookeeper is cleaning cages");
            zookeeper.setCleaning(true);
        } else {
            zookeeper.setCleaning(false);
            if (simulationHour == 9 || simulationHour == 14) {
                duckCage.feedAnimals(zookeeper.getSpeed(), wrongFood);
            } else if (simulationHour == 10 || simulationHour == 16) {
                monkeyCage.feedAnimals(zookeeper.getSpeed(), wrongFood);
            } else if (simulationHour == 11 || simulationHour == 17) {
                elephantCage.feedAnimals(zookeeper.getSpeed(), wrongFood);
            } else if (simulationHour == 12 || simulationHour == 18) {
                horseCage.feedAnimals(zookeeper.getSpeed(), wrongFood);
            } else if (simulationHour == 13 || simulationHour == 19) {
                lionCage.feedAnimals(zookeeper.getSpeed(), wrongFood);
            }
            System.out.println("Zookeeper needs to feed " + counterHungryDucks + " ducks, " +
                    counterHungryMonkeys + " monkeys, " + counterHungryElephants + " elephants, " +
                    counterHungryHorses + " horses, " + counterHungryLions + " lions");
        }

        elephantCage.becomeHungry();
        horseCage.becomeHungry();
        lionCage.becomeHungry();
        monkeyCage.becomeHungry();
        duckCage.becomeHungry();
    }

    void handleAnimalTreating() {
        int counterIllAnimals = duckCage.countIllAnimals() + lionCage.countIllAnimals() +
                monkeyCage.countIllAnimals() + elephantCage.countIllAnimals() + horseCage.countIllAnimals();
        if (clientWantsToEnter && client.getAge() < Integer.parseInt(props.getProperty("ageOfMajority"))
                && clientCanEnter) {
            client.setHappiness(client.getHappiness()
                    - Integer.parseInt(props.getProperty("childHappinessCoefficient")) * counterIllAnimals);
        } else if (clientWantsToEnter && clientCanEnter) {
            client.setHappiness(client.getHappiness()
                    - Integer.parseInt(props.getProperty("adultHappinessCoefficient")) * counterIllAnimals);
        }

        if (counterIllAnimals != 0) {
            veterinarian.setTreating(true);
            if (wrongFood % 10 == 0 && !zookeeper.isCleaning()) {
                System.out.println("Veterinarian is treating animals because of wrong food.");
                if (simulationHour == 9 || simulationHour == 14) {
                    duckCage.treatAnimals();
                } else if (simulationHour == 10 || simulationHour == 16) {
                    monkeyCage.treatAnimals();
                } else if (simulationHour == 11 || simulationHour == 17) {
                    elephantCage.treatAnimals();
                } else if (simulationHour == 12 || simulationHour == 18) {
                    horseCage.treatAnimals();
                } else if (simulationHour == 13 || simulationHour == 19) {
                    lionCage.treatAnimals();
                }
            } else {
                int animalType = StaticUtils.random.nextInt(5) + 1;
                switch (animalType) {
                    case 1:
                        duckCage.treatAnimal();
                        System.out.println("Veterinarian is treating a duck");
                        break;
                    case 2:
                        monkeyCage.treatAnimal();
                        System.out.println("Veterinarian is treating a monkey");
                        break;
                    case 3:
                        elephantCage.treatAnimal();
                        System.out.println("Veterinarian is treating an elephant");
                        break;
                    case 4:
                        horseCage.treatAnimal();
                        System.out.println("Veterinarian is treating a horse");
                        break;
                    case 5:
                        lionCage.treatAnimal();
                        System.out.println("Veterinarian is treating a lion");
                        break;
                }
            }

        }
    }

    void handleHorseRiding() {
        int noHorse = StaticUtils.random.nextInt(Integer.parseInt(props.getProperty("numberOfHorses")));

        if (clientWantsToEnter && client instanceof Child && clientCanEnter) {
            ((Child) client).wantsRideHorse();
            if (client.getAge() >= Integer.parseInt(props.getProperty("acceptableAge"))) {
                if (((Horse) horseCage.getAnimalList().get(noHorse)).isRideable()) {
                    client.setHappiness(client.getHappiness()
                            + Integer.parseInt(props.getProperty("childHappinessCoefficient")));
                }
            }
        }
    }

    void handleTipping() {
        if (clientWantsToEnter && clientCanEnter
                && client.getHappiness() >= Integer.parseInt(props.getProperty("tipHappinessRate"))) {
            int tips = StaticUtils.random.nextInt(Integer.parseInt(props.getProperty("maxTipValue"))) + 1;
            cashier.setTips(tips);
            System.out.println("Cashier gets " + tips + " dollars tips because client was happy");
        } else if ((clientWantsToEnter && clientCanEnter
                && (client.getHappiness() < Integer.parseInt(props.getProperty("returnAdultHappinessRate")))
                || client.getHappiness() < Integer.parseInt(props.getProperty("returnChildHappinessRate")))) {
            System.out.println("Client gets " + cashier.getTicketPrice() / 2 + " dollars back because he was unhappy");
        }
    }


    void renderHour() throws InterruptedException {
        incrementHour();
        checkForDiscount();
        handleClientBehavior();
        handleSecurityGuardBehavior();
        handleCleaningAndFeeding();
        handleAnimalTreating();
        handleHorseRiding();
        handleTipping();

        System.out.println();
        Thread.sleep(3000);
    }
}
