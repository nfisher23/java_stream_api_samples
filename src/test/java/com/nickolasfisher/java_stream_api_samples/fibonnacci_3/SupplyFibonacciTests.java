package com.nickolasfisher.java_stream_api_samples.fibonnacci_3;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class SupplyFibonacciTests {

    @Test
    public void validate_fibonacci() {
        List<Integer> tenFibs = Stream.generate(new SupplyFibonacci()).limit(10).collect(Collectors.toList());

        assertEquals(0,tenFibs.get(0).intValue());
        assertEquals(1,tenFibs.get(1).intValue());
        assertEquals(1,tenFibs.get(2).intValue());
        assertEquals(2,tenFibs.get(3).intValue());
        assertEquals(3,tenFibs.get(4).intValue());
        assertEquals(5,tenFibs.get(5).intValue());
    }

    @Test
    public void fibonacciStream_printVals() {
        Stream.generate(new SupplyFibonacci()).peek(System.out::println).limit(25).collect(Collectors.toList());
    }
}
