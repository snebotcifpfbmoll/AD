package com.snebot.ad.workspace.helper;

import org.jeasy.random.EasyRandom;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DummyUtils {
    public <T extends Serializable> List<T> generateObjects(Class<T> classType, int size) {
        ArrayList<T> objects = new ArrayList<>();
        EasyRandom generator = new EasyRandom();
        for (int i = 0; i < size; i++) objects.add(generator.nextObject(classType));
        return objects;
    }
}
