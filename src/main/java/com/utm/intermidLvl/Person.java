package com.utm.intermidLvl;

import com.utm.root.Entity;
import com.utm.util.StaticUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Person extends Entity {
    private String name;
    private String surname;

    public Person() {
        this.name = StaticUtils.faker.name().firstName();
        this.surname = StaticUtils.faker.name().lastName();
    }
}
