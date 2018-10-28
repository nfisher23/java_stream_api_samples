package com.nickolasfisher.java_stream_api_samples.collecting_downstream_9;

import com.nickolasfisher.java_stream_api_samples.SimplePair;
import com.nickolasfisher.java_stream_api_samples.TestUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;

public class CollectingDownstreamTests {

    private List<SimplePair> simplePairs;

    @Before
    public void setupSimplePairs() {
        simplePairs = TestUtils.generateSimplePairs(5);
    }

    private void addDuplicatePairs() {
        simplePairs.add(new SimplePair() {{
            setId(3);
            setName("another-pair3");
        }});

        simplePairs.add(new SimplePair() {{
            setId(3);
            setName("yet-another-pair3");
        }});
    }

    @Test
    public void groupingBy_toList() {
        addDuplicatePairs();

        Map<Integer, List<SimplePair>> defaultsToList = simplePairs.stream().collect(
                Collectors.groupingBy(SimplePair::getId)
        );

        assertEquals(3, defaultsToList.get(3).size());
    }

    @Test
    public void groupingBy_toSet() {
        addDuplicatePairs();

        Map<Integer, Set<SimplePair>> idToSetOfPairs = simplePairs.stream().collect(
                Collectors.groupingBy(SimplePair::getId, Collectors.toSet())
        );

        assertEquals(3, idToSetOfPairs.get(3).size());
    }

    @Test
    public void groupingBy_count() {
        addDuplicatePairs();

        Map<Integer, Long> countIdOccurrence = simplePairs.stream().collect(
                Collectors.groupingBy(SimplePair::getId, Collectors.counting())
        );

        assertEquals(3, countIdOccurrence.get(3).intValue());
        assertEquals(1, countIdOccurrence.get(1).intValue());
    }

    @Test
    public void groupingBy_sumDownstreamElements() {
        addDuplicatePairs();

        Map<Integer, Integer> addUpIds = simplePairs.stream().collect(
                Collectors.groupingBy(SimplePair::getId, Collectors.summingInt(SimplePair::getId))
        );

        assertEquals(9, addUpIds.get(3).intValue());
        assertEquals(4, addUpIds.get(4).intValue());
    }

    @Test
    public void groupingBy_getMaxDownstreamElement() {
        addDuplicatePairs();

        Map<Integer, Optional<SimplePair>> sortByMaxDownstream = simplePairs.stream().collect(
                Collectors.groupingBy(SimplePair::getId, Collectors.maxBy(
                        Comparator.comparing(sp -> sp.getName().length())
                ))
        );

        String maxNameOfThree = sortByMaxDownstream.get(3).orElseThrow(RuntimeException::new).getName();

        assertEquals("yet-another-pair3", maxNameOfThree);
    }

    @Test
    public void groupingBy_getMinDownstreamElement() {
        addDuplicatePairs();

        Map<Integer, Optional<SimplePair>> sortByMinDownstream = simplePairs.stream().collect(
                Collectors.groupingBy(SimplePair::getId, Collectors.minBy(
                        Comparator.comparing(simplePair -> simplePair.getName().length())
                ))
        );

        String minNameOfThree = sortByMinDownstream.get(3).orElseThrow(RuntimeException::new).getName();

        assertEquals("pair3", minNameOfThree);
    }

    @Test
    public void groupingBy_mapsToMaps() {
        addDuplicatePairs();

        Map<Integer, Map<Integer, List<SimplePair>>> mapToSetOfLengths = simplePairs.stream().collect(Collectors.groupingBy(
                SimplePair::getId,
                Collectors.groupingBy(sp -> sp.getName().length(), Collectors.toList())
        ));

        Map<Integer, List<SimplePair>> lengthsToSimplePairsWithId1 = mapToSetOfLengths.get(1);
        Map<Integer, List<SimplePair>> lengthsToSimplePairsWithId3 = mapToSetOfLengths.get(3);

        assertEquals(1, lengthsToSimplePairsWithId1.size());
        assertEquals(3, lengthsToSimplePairsWithId3.size());
    }

    @Test
    public void groupingBy_mappingToMoreDownstreamElements() {
        addDuplicatePairs();

        Map<Integer, List<Integer>> mapIdsToSetOfLengths = simplePairs.stream().collect(Collectors.groupingBy(
                SimplePair::getId,
                Collectors.mapping(sp -> sp.getName().length(), Collectors.toList())
        ));

        assertEquals(5, mapIdsToSetOfLengths.get(3).get(0).intValue());
        assertEquals(13, mapIdsToSetOfLengths.get(3).get(1).intValue());
    }

}
