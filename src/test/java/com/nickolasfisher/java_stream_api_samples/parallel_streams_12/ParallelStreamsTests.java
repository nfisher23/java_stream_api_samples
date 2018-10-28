package com.nickolasfisher.java_stream_api_samples.parallel_streams_12;

import com.nickolasfisher.java_stream_api_samples.TestUtils;
import com.nickolasfisher.java_stream_api_samples.fibonnacci_3.SupplyFibonacci;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;

public class ParallelStreamsTests {

    List<String> names;

    @Before
    public void setupNames() {
        names = TestUtils.getListOfNames();
    }

    @Test
    public void countingInParallel() {
        long parallelCount = names.parallelStream().filter(name -> name.startsWith("J")).count();
        assertEquals(5, parallelCount);
    }

    @Test
    public void fibonacci_predictableWhenSynchronous() {
        Stream<Integer> fibonaccis_1 = Stream.generate(new SupplyFibonacci());

        Integer firstFibOver5K = fibonaccis_1.peek(System.out::println)
                .filter(num -> num >= 500000).findFirst().orElseThrow(RuntimeException::new);

        Stream<Integer> fibonaccis_2 = Stream.generate(new SupplyFibonacci());

        Integer stillFirstFibOver5K = fibonaccis_2
                .filter(num -> num.intValue() == firstFibOver5K)
                .findFirst().orElseThrow(RuntimeException::new);

        assertEquals(firstFibOver5K, stillFirstFibOver5K);
    }

    @Test
    public void badUseOfParallel_thisIsUnpredictable() {
        Stream<Integer> fibonaccis_1 = Stream.generate(new SupplyFibonacci());

        Integer firstFibOver5K = fibonaccis_1
                .parallel()
                .peek(System.out::println)
                .filter(num -> num >= 500000)
                .findAny()
                .orElseThrow(RuntimeException::new);

        Stream<Integer> fibonaccis_2 = Stream.generate(new SupplyFibonacci());

        Integer stillFirstFibOver5K = fibonaccis_2
                .parallel()
                .peek(System.out::println)
                .filter(num -> num >= 500000)
                .findAny()
                .orElseThrow(RuntimeException::new);

        assertEquals(firstFibOver5K, stillFirstFibOver5K);
    }

    public List<Integer> generateLargeList(int max) {
        List<Integer> ints = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            ints.add(i);
        }
        return ints;
    }

    @Test
    public void parallelStreams_orderedCollectionsRemainOrdered() {
        List<Integer> largeSequentialList = generateLargeList(100000);

        List<Integer> collectedInOrder = largeSequentialList.parallelStream()
                .filter(num -> num >= 1000)
                .peek(System.out::println)
                .collect(Collectors.toList());

        // verify order
        for (int i = 1; i < collectedInOrder.size(); i++) {
            assertTrue(collectedInOrder.get(i) > collectedInOrder.get(i - 1));
        }
    }

    @Test
    public void parallelStreams_concurrentMaps() {
        ConcurrentMap<Integer, List<String>> mapToNamesInParallel =
                names.parallelStream()
                        .collect(Collectors.groupingByConcurrent(String::length));

        assertTrue(mapToNamesInParallel.get(4).contains("Alan"));
        assertTrue(mapToNamesInParallel.get(4).contains("John"));

        ConcurrentMap<Integer, Long> mapOfCountsInParallel = names.parallelStream()
                .collect(Collectors.groupingByConcurrent(String::length, Collectors.counting()));

        assertEquals(2, mapOfCountsInParallel.get(4).intValue());
    }

}
