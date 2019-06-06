package com.gaborkallos.thefeedbacker.service;

import java.util.Random;

public class RandomGenerator {

    private Random RANDOM = new Random();

    public String passwordGenerator() {
        String abc = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "1234567890";
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            if (i == 5 || i == 6) {
                password.append(numbers.charAt(RANDOM.nextInt(numbers.length())));
            } else {
                if (RANDOM.nextBoolean()) {
                    password.append(abc.charAt(RANDOM.nextInt(abc.length())));
                } else {
                    password.append(Character.toUpperCase(abc.charAt(RANDOM.nextInt(abc.length()))));
                }
            }
        }
        return password.toString();
    }
}