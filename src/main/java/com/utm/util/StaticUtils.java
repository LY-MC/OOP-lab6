package com.utm.util;

import com.github.javafaker.Faker;

import java.util.Random;

public class StaticUtils {
    public static Faker faker = new Faker();
    public static Random random = new Random();
    public static final int OPENING_HOUR = 8;
    public static final int CLOSING_HOUR = 23;
}
