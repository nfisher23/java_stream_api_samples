package com.nickolasfisher.java_stream_api_samples.concat_sort_flatmap_4;

import com.nickolasfisher.java_stream_api_samples.TestUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExamplesAndTests {

    List<String> names;

    @Before
    public void setupNames() {
        names = TestUtils.getListOfNames();
    }

    @Test
    public void concatenating() {
        Stream<String> startsWithJo = names.stream().filter(name -> name.startsWith("Jo"));
        Stream<String> startsWithJa = names.stream().filter(name -> name.startsWith("Ja"));

        List<String> combined = Stream.concat(startsWithJo, startsWithJa).collect(Collectors.toList());

        assertEquals("John", combined.get(0));
        assertEquals("Jacob", combined.get(2));
    }

    @Test
    public void distinct() {
        String jolene = "Jolene";
        names.add(jolene);
        names.add(jolene);
        names.add(jolene);

        List<String> jolenes = names.stream().filter(name -> name.equals(jolene)).collect(Collectors.toList());

        assertEquals(3, jolenes.size());

        List<String> distinctJolene = jolenes.stream().distinct().collect(Collectors.toList());

        assertEquals(1, distinctJolene.size());
    }

    @Test
    public void sorting() {
        Stream<String> sortedByFirstLetter = names.stream().sorted(new Comparator<String>() {
            @Override
            public int compare(String first, String second) {
                // by first letter
                if (first.charAt(0) > second.charAt(0)) {
                    return 1;
                } else if (first.charAt(0) < second.charAt(0)) {
                    return -1;
                }
                return 0;
            }
        });

        List<String> sortedAsList = sortedByFirstLetter.collect(Collectors.toList());

        assertEquals("Alan", sortedAsList.get(0));
        assertEquals("Beverly", sortedAsList.get(1));
    }

    private Stream<String> getCharactersAsStream(String word) {
        List<String> chars = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            chars.add(word.substring(i, i + 1));
        }
        return chars.stream();
    }

    @Test
    public void flatMapping() {
        Stream<Stream<String>> streamsOnStreams = names.stream().map(name -> getCharactersAsStream(name));

        List<Stream<String>> collected = streamsOnStreams.collect(Collectors.toList());
        String[] charsOfJohn = collected.get(0).collect(Collectors.toList()).toArray(new String[0]);
        String[] charsOfJacob = collected.get(1).collect(Collectors.toList()).toArray(new String[0]);

        assertTrue(Arrays.equals(new String[] {"J", "o","h","n"}, charsOfJohn));
        assertTrue(Arrays.equals(new String[] {"J", "a","c","o", "b"}, charsOfJacob));


        List<String> allCharactersFlatMapped = names.stream().flatMap(name -> getCharactersAsStream(name)).collect(Collectors.toList());

        assertEquals("J", allCharactersFlatMapped.get(0));
        assertEquals("o", allCharactersFlatMapped.get(1));
        assertEquals("h", allCharactersFlatMapped.get(2));
        assertEquals("n", allCharactersFlatMapped.get(3));
        assertEquals("J", allCharactersFlatMapped.get(4));
        assertEquals("a", allCharactersFlatMapped.get(5));
        assertEquals("c", allCharactersFlatMapped.get(6));
        assertEquals("o", allCharactersFlatMapped.get(7));
        assertEquals("b", allCharactersFlatMapped.get(8));
    }

}
