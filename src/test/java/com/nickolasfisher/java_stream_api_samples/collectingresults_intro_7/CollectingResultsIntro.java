package com.nickolasfisher.java_stream_api_samples.collectingresults_intro_7;

import com.nickolasfisher.java_stream_api_samples.SimplePair;
import com.nickolasfisher.java_stream_api_samples.TestUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Corresponding tutorial at https://nickolasfisher.com/blog/The-Java-Stream-API-An-Introduction-to-Collecting-Results
 */
public class CollectingResultsIntro {

    private List<String> names;

    @Before
    public void setupNames() {
        names = TestUtils.getListOfNames();
    }

    @Test
    public void collect_toSet() {
        Set<String> allJNames = names.stream().filter(name -> name.startsWith("J")).collect(Collectors.toSet());

        assertTrue(allJNames.contains("John"));
        assertTrue(allJNames.contains("Jacob"));
    }

    @Test
    public void collect_joining() {
        String allNamesJoined = names.stream().collect(Collectors.joining());

        assertTrue(allNamesJoined.startsWith("JohnJacobJerry"));
    }

    @Test
    public void collect_joinWithDelimiter() {
        String commaDelimitedNames = names.stream().collect(Collectors.joining(","));

        assertTrue(commaDelimitedNames.startsWith("John,Jacob,Jerry"));
    }

    private List<SimplePair> simplePairs;

    @Before
    public void setupSimplePairs() { simplePairs = TestUtils.generateSimplePairs(5); }

    @Test
    public void collect_mapToString() {
        List<SimplePair> twoPairs = TestUtils.generateSimplePairs(2);

        String semiColonDelimited = twoPairs.stream().map(Objects::toString).collect(Collectors.joining(";"));

        assertEquals("name-pair1,id-1;name-pair2,id-2", semiColonDelimited);
    }

    @Test
    public void collectStatistics() {
        IntSummaryStatistics statistics = simplePairs.stream().collect(Collectors.summarizingInt(SimplePair::getId));

        assertEquals(5, statistics.getCount());
        assertEquals(5, statistics.getMax());
        assertEquals(1, statistics.getMin());
        assertEquals(15, statistics.getSum());
    }

}
