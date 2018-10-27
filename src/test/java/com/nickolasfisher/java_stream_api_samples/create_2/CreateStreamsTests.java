package com.nickolasfisher.java_stream_api_samples.create_2;

import com.nickolasfisher.java_stream_api_samples.TestUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class CreateStreamsTests {

    List<String> names;

    @Before
    public void setupNames() {
        names = TestUtils.getListOfNames();
    }

    @Test
    public void simpleStream() {
        Stream<String> emotions = Stream.of("happy", "sad", "ecstatic", "joyful", "exuberant", "jealous");

        List<String> listOfJs = emotions.filter(emotion -> emotion.startsWith("j")).collect(Collectors.toList());

        assertEquals(2, listOfJs.size());
    }

    @Test
    public void lazyStreams() {
        // nothing gets printed
        Stream<String> template = names.stream().peek(System.out::println).filter(n -> n.length() > 4);
    }

    @Test
    public void lazyStreams_withTerminalOperator() {
        Stream<String> template = names.stream().peek(System.out::println).filter(n -> n.length() > 4);

        // execute here
        template.collect(Collectors.toList());
    }

    private class SupplyInfinity implements Supplier<Integer> {
        private int counter = 0;

        @Override
        public Integer get() {
            return counter++;
        }

    }

    @Test
    public void infinteStreams_withCustomSupplier() {
        Stream<Integer> infinity = Stream.generate(new SupplyInfinity());

        List<Integer> collected = infinity.limit(100).collect(Collectors.toList());

        assertEquals(99, collected.get(99).intValue());
    }

    @Test
    public void verifyLazinessOfStream() {
        Stream.iterate(0.0, num -> num + (new Random()).nextInt(2) - .5)
                .peek(num -> System.out.println("getting " + num))
                .limit(5).collect(Collectors.toList());
    }

    @Test
    public void infiniteStreams_withIterate() {
        Stream<Integer> infinity = Stream.iterate(0, num -> num + 1);

        List<Integer> collected = infinity.limit(100).collect(Collectors.toList());

        assertEquals(99, collected.get(99).intValue());
    }

}
