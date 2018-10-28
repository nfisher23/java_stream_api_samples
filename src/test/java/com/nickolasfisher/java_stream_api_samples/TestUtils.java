package com.nickolasfisher.java_stream_api_samples;

import java.util.ArrayList;
import java.util.List;

public final class TestUtils {

    public static List<String> getListOfNames() {
        List<String> names = new ArrayList<>();

        names.add("John");
        names.add("Jacob");
        names.add("Jerry");
        names.add("Josephine");
        names.add("Janine");
        names.add("Alan");
        names.add("Beverly");

        return names;
    }

    public static List<SimplePair> generateSimplePairs(int numToGenerate) {
        List<SimplePair> pairs = new ArrayList<>();
        for (int i = 1; i <= numToGenerate; i++) {
            SimplePair pair = new SimplePair();

            pair.setId(i);
            pair.setName("pair" + i);

            pairs.add(pair);
        }
        return pairs;
    }
}
