package com.snebot.ad.workspace.poc;

import java.util.ArrayList;
import java.util.List;

public class Lambda {
    public String getData(int times) {
        StringBuilder stringBuilder = new StringBuilder();
        List<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        numbers.add(5);
        numbers.forEach(number -> {
            stringBuilder.append(String.format("test lambda: %s\n", number));
        });
        return stringBuilder.toString();
    }
}
