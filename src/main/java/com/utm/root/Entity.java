package com.utm.root;

import lombok.Getter;

import java.util.UUID;

public class Entity {
    @Getter
    private final UUID ID;

    public Entity() {
        this.ID = UUID.randomUUID();
    }
}
