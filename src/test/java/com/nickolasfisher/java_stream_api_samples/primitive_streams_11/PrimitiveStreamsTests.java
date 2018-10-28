package com.nickolasfisher.java_stream_api_samples.primitive_streams_11;

import com.nickolasfisher.java_stream_api_samples.SimplePair;
import com.nickolasfisher.java_stream_api_samples.TestUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.DoubleSummaryStatistics;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertEquals;

/**
 * Corresponding tutorial at https://nickolasfisher.com/blog/The-Java-Stream-API-Primitive-Streams
 */
public class PrimitiveStreamsTests {

    List<SimplePair> pairs;

    @Before
    public void setupSimplePairs() {
        pairs = TestUtils.generateSimplePairs(5);
    }

    @Test
    public void intStream_simpleEx() {
        IntStream intStream = IntStream.of(1,4,5,8,9,10);

        IntSummaryStatistics stats = intStream.summaryStatistics();

        assertEquals(10, stats.getMax());
    }

    @Test
    public void doubleStream_simpleEx() {
        DoubleStream doubleStream = DoubleStream.of(1.0, 2.5, 3.5, 6.5, 8.0);

        DoubleSummaryStatistics stats = doubleStream.summaryStatistics();

        assertEquals(5, stats.getCount());
        assertEquals(1.0, stats.getMin(), .01);
    }

    @Test
    public void longStream_simpleEx() {
        LongStream longStream = LongStream.of(100, 101, 102, 103);

        long[] longs = longStream.filter(l -> l >= 101).toArray();

        assertEquals(longs[0], 101);
    }

    @Test
    public void mapToIntStream() {
        IntStream nameLengths = pairs.stream().mapToInt(sp -> sp.getName().length());

        IntSummaryStatistics stats = nameLengths.summaryStatistics();

        assertEquals(5, stats.getMin());
    }

    @Test
    public void intStream_mapToObjectStream() {
        IntStream intStream = IntStream.of(1, 1, 3, 3, 4);
        Stream<Integer> boxed = intStream.boxed();

        Optional<Integer> mathResult = boxed.reduce((first, second) -> first + 2 * second);

        assertEquals(1 + 2 + 6 + 6 + 8, mathResult.orElseThrow(RuntimeException::new).intValue());
    }

}
