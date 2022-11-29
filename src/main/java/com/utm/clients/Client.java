package com.utm.clients;

import com.utm.intermidLvl.Person;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
abstract public class Client extends Person {
    private int age;
    private int happiness;

    public abstract void buyTicket();

    public abstract void enterZoo(boolean canEnter);
}
