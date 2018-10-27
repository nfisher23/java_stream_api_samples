package com.nickolasfisher.java_stream_api_samples.optionals_5;

import com.nickolasfisher.java_stream_api_samples.TestUtils;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class OptionalsTests {

    List<String> names;

    @Before
    public void setupNames() {
        names = TestUtils.getListOfNames();
    }

    @Test
    public void optional_max() {
        Optional<String> maxName = names.stream().max(String::compareToIgnoreCase);

        assertEquals("Josephine", maxName.orElse(""));
    }

    @Test
    public void optional_orElse() {
        Optional<String> doesntExist = names.stream().filter(name -> name.startsWith("Z")).findAny();

        assertEquals("default", doesntExist.orElse("default"));
    }

    @Test
    public void optional_orElseGet() {
        Optional<String> doesntExist = names.stream().filter(name -> name.startsWith("Z")).findAny();

        String stringTime = doesntExist.orElseGet(() -> Instant.now().toString());

        System.out.println(stringTime);
    }

    @Test(expected = RuntimeException.class)
    public void optional_orElseThrow() {
        Optional<String> doesntExist = names.stream().filter(name -> name.startsWith("Z")).findAny();

        doesntExist.orElseThrow(() -> new RuntimeException("No names starting with 'Z' in the collection"));
    }

    private class SimpleConsumer implements Consumer<String> {
        String internalValue = null;

        @Override
        public void accept(String s) {
            internalValue = s;
        }
    }

    @Test
    public void optional_ifPresent_exists() {
        Optional<String> alan = names.stream().filter(name -> name.equals("Alan")).findFirst();

        SimpleConsumer shouldRun = new SimpleConsumer();
        alan.ifPresent(shouldRun);

        assertEquals("Alan", shouldRun.internalValue);
    }

    @Test
    public void optional_ifPresent_DNE() {
        Optional<String> notHere = names.stream().filter(name -> name.equals("Not a Real Name")).findFirst();

        notHere.ifPresent(name -> { throw new RuntimeException("this exception won't get thrown"); });
    }

    @Test
    public void optional_map() {
        Optional<String> alan = names.stream().filter(name -> name.equals("Alan")).findFirst();

        Optional<String> firstChar = alan.map(name -> name.substring(0, 1));

        assertEquals("A", firstChar.orElseThrow(RuntimeException::new));
    }

}
