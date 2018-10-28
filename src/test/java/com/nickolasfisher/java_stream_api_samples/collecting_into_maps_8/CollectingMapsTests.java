package com.nickolasfisher.java_stream_api_samples.collecting_into_maps_8;

import com.nickolasfisher.java_stream_api_samples.SimplePair;
import com.nickolasfisher.java_stream_api_samples.TestUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;

public class CollectingMapsTests {

    private List<SimplePair> simplePairs;

    @Before
    public void setupSimplePairs() {
        simplePairs = TestUtils.generateSimplePairs(5);
    }

    @Test
    public void collect_mapIdToName() {
        Map<Integer, String> mapIdToName = simplePairs.stream().collect(Collectors.toMap(SimplePair::getId, SimplePair::getName));

        assertEquals(mapIdToName.get(3), "pair3");
        assertEquals(mapIdToName.get(5), "pair5");
    }

    @Test
    public void collect_mapIdToPair() {
        Map<Integer, SimplePair> mapIdToObject = simplePairs.stream().collect(Collectors.toMap(SimplePair::getId, Function.identity()));

        SimplePair pair1 = mapIdToObject.get(1);
        SimplePair pair4 = mapIdToObject.get(4);

        assertEquals(pair1.toString(), "name-pair1,id-1");
        assertEquals(pair4.toString(), "name-pair4,id-4");
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
    public void collect_resolveConflictsOnMap() {
        addDuplicatePairs();

        Map<Integer, SimplePair> mapWithExistingValue = simplePairs.stream().collect(Collectors.toMap(
                SimplePair::getId,
                Function.identity(),
                (existingValue, newValue) -> existingValue // established is always better
        ));

        assertEquals("pair3", mapWithExistingValue.get(3).getName());

        Map<Integer, SimplePair> mapWithNewestValue = simplePairs.stream().collect(Collectors.toMap(
                SimplePair::getId,
                Function.identity(),
                (existingValue, newValue) -> newValue // newer is always better
        ));

        assertEquals("yet-another-pair3", mapWithNewestValue.get(3).getName());
    }

    @Test
    public void groupingBy_sortToMap() {
        addDuplicatePairs();

        Map<Integer, List<SimplePair>> mapIdsToPairs =
                simplePairs.stream().collect(Collectors.groupingBy(SimplePair::getId));

        assertEquals(3, mapIdsToPairs.get(3).size());
        assertEquals(3, mapIdsToPairs.get(3).get(0).getId());
        assertEquals(3, mapIdsToPairs.get(3).get(1).getId());
        assertEquals("another-pair3", mapIdsToPairs.get(3).get(1).getName());
    }

    @Test
    public void parititioningBy_sortByTrueAndFalse() {
        addDuplicatePairs();

        Map<Boolean, List<SimplePair>> partitionedByIdOf3 =
                simplePairs.stream().collect(
                        Collectors.partitioningBy(pair -> pair.getId() == 3)
                );

        assertEquals(3, partitionedByIdOf3.get(true).size());
        assertEquals(4, partitionedByIdOf3.get(false).size());
    }

}
