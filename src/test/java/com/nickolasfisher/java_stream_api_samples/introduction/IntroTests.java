package com.nickolasfisher.java_stream_api_samples.introduction;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class IntroTests {

    private List<String> names;

    @Before
    public void setupListOfNames() {
        names = new ArrayList<>();

        names.add("John");
        names.add("Jacob");
        names.add("Jerry");
        names.add("Josephine");
        names.add("Janine");
        names.add("Alan");
        names.add("Beverly");
    }

    @Test
    public void originalListUnchanged() {
        List<String> emptyList = names.stream().filter(name -> false).collect(Collectors.toList());

        assertTrue(emptyList.isEmpty());
        assertFalse(names.isEmpty());
        assertEquals("John", names.get(0));
    }

    @Test
    public void filterByFirstLetter() {
        Stream<String> streamFilteredByFirstLetter = names.stream()
                .filter(name -> name.startsWith("J"));

        List<String> listFilteredByFirstLetter = streamFilteredByFirstLetter.collect(Collectors.toList());

        assertEquals(5, listFilteredByFirstLetter.size());
        assertEquals("John", listFilteredByFirstLetter.get(0));
        assertEquals("Janine", listFilteredByFirstLetter.get(4));
    }

    @Test
    public void filterByFirstTwoLetters() {
        Stream<String> streamFilteredByFirstTwoLetters = names.stream()
                .filter(name -> name.startsWith("Jo"));

        List<String> listFiltered = streamFilteredByFirstTwoLetters.collect(Collectors.toList());

        assertEquals(2, listFiltered.size());
    }

    @Test
    public void countFilteredValues() {
        long countFilteredByFirstLetter = names.stream().filter(name -> name.startsWith("J")).count();

        assertEquals(5, countFilteredByFirstLetter);
    }

    @Test
    public void parallelCount() {
        long parallelCount = names.parallelStream().filter(name -> name.startsWith("J")).count();

        assertEquals(5, parallelCount);
    }

    @Test
    public void addLastName() {
        List<String> theSmiths = names.stream().map(name -> name + " Smith").collect(Collectors.toList());

        assertEquals("John Smith", theSmiths.get(0));
        assertEquals("Jacob Smith", theSmiths.get(1));

        for (String nameWithSmithAsLastName : theSmiths) {
            String lastName = nameWithSmithAsLastName.split(" ")[1];
            assertEquals("Smith", lastName);
        }
    }

    @Test
    public void getFirstLetter() {
        List<String> firstLetters = names.stream().map(name -> name.substring(0, 1)).collect(Collectors.toList());

        assertEquals("J",firstLetters.get(0));
        assertEquals("J",firstLetters.get(1));
        assertEquals("J",firstLetters.get(2));
        assertEquals("A",firstLetters.get(5));
    }

}
