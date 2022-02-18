package ca.jrvs.apps.grep.practice;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LambdaStreamExcMain implements LambdaStreamExc {

    public static void main(String[] args) {
        LambdaStreamExcMain ls = new LambdaStreamExcMain();

        ls.createStrStream("a", "B", "c").forEach(System.out::println);

        // ls.toUpperCase("aa", "bb", "cc").forEach(System.out::println);

        // ls.filter(ls.createStrStream("ba", "B", "c"), "a").forEach(System.out::println);

        // int[] arr = {10, 20, 30, 40, 50};
        // ls.createIntStream(arr).forEach(System.out::println);

        //List<String> test = ls.createStrStream("a", "B", "c").collect(Collectors.toList());
        //for (String t: test) {
        //  System.out.println(t);
        //}

        //int[] arr = {10, 20, 30, 40, 50};
        //for (Integer i: ls.toList(Arrays.stream(arr))) {
        //  System.out.println(i);
        //}

        // ls.createIntStream(1, 5).forEach(System.out::println);

        // ls.squareRootIntStream(ls.createIntStream(1, 5)).forEach(System.out::println);

        // ls.getOdd(ls.createIntStream(1, 11)).forEach(System.out::println);

        //LambdaStreamExcMain lse = new LambdaStreamExcMain();
        //Consumer<String> printer = lse.getLambdaPrinter("start>", "<end"); printer.accept("Message body");

        //LambdaStreamExcMain lse = new LambdaStreamExcMain();
        //String[] messages = {"a","b", "c"};
        //lse.printMessages(messages, lse.getLambdaPrinter("msg:", "!") );

        //LambdaStreamExcMain lse = new LambdaStreamExcMain();
        //lse.printOdd(lse.createIntStream(0, 5), lse.getLambdaPrinter("odd number:", "!"));

    }

    /**
     * Create a String stream from array
     * <p>
     * note: arbitrary number of value will be stored in an array
     *
     * @param strings Input strings
     * @return Stream of String
     */
    @Override
    public Stream<String> createStrStream(String... strings) {
        return Stream.of(strings);
    }

    /**
     * Convert all strings to uppercase please use createStrStream
     *
     * @param strings Input strings
     * @return Stream of upper case strings
     */
    @Override
    public Stream<String> toUpperCase(String... strings) {
        return createStrStream(strings).map(String::toUpperCase);
    }

    /**
     * filter strings that contains the pattern e.g. filter(stringStream, "a") will return another
     * stream which no element contains a
     *
     * @param stringStream source of strings
     * @param pattern      filter pattern
     * @return result of filter
     */
    @Override
    public Stream<String> filter(Stream<String> stringStream, String pattern) {
        return stringStream.filter((s) -> !s.contains(pattern));
    }

    /**
     * Create a intStream from a arr[]
     *
     * @param arr integer array
     * @return stream of integers
     */
    @Override
    public IntStream createIntStream(int[] arr) {
        return Arrays.stream(arr);
    }

    /**
     * Convert a stream to list
     *
     * @param stream input stream
     * @return output list
     */
    @Override
    public <E> List<E> toList(Stream<E> stream) {
        return stream.collect(Collectors.toList());
    }

    /**
     * Convert a intStream to list
     *
     * @param intStream input stream
     * @return list of integers
     */
    @Override
    public List<Integer> toList(IntStream intStream) {
        return intStream.boxed().collect(Collectors.toList());
    }

    /**
     * Create a IntStream range from start to end inclusive
     *
     * @param start start
     * @param end   end
     * @return intstream
     */
    @Override
    public IntStream createIntStream(int start, int end) {
        return IntStream.rangeClosed(start, end);
    }

    /**
     * Convert a intStream to a doubleStream and compute square root of each element
     *
     * @param intStream int stream
     * @return double stream
     */
    @Override
    public DoubleStream squareRootIntStream(IntStream intStream) {
        return intStream.mapToDouble(Math::sqrt);
    }

    /**
     * filter all even number and return odd numbers from a intStream
     *
     * @param intStream integer stream
     * @return odd numbers
     */
    @Override
    public IntStream getOdd(IntStream intStream) {
        return intStream.filter(num -> num % 2 != 0);
    }

    /**
     * Return a lambda function that print a message with a prefix and suffix This lambda can be
     * useful to format logs
     * <p>
     * You will learn: - functional interface http://bit.ly/2pTXRwM & http://bit.ly/33onFig - lambda
     * syntax
     * <p>
     * e.g. LambdaStreamExc lse = new LambdaStreamImp(); Consumer<String> printer =
     * lse.getLambdaPrinter("start>", "<end"); printer.accept("Message body");
     * <p>
     * sout: start>Message body<end
     *
     * @param prefix prefix str
     * @param suffix suffix str
     * @return consumer string
     */
    @Override
    public Consumer<String> getLambdaPrinter(String prefix, String suffix) {
        return (message -> System.out.println(prefix + message + suffix));
    }

    /**
     * Print each message with a given printer Please use `getLambdaPrinter` method
     * <p>
     * e.g. String[] messages = {"a","b", "c"}; lse.printMessages(messages,
     * lse.getLambdaPrinter("msg:", "!") );
     * <p>
     * sout: msg:a! msg:b! msg:c!
     *
     * @param messages messages
     * @param printer  printer
     */
    @Override
    public void printMessages(String[] messages, Consumer<String> printer) {
        Arrays.stream(messages).forEach(printer);
    }

    /**
     * Print all odd number from a intStream. Please use `createIntStream` and `getLambdaPrinter`
     * methods
     * <p>
     * e.g. lse.printOdd(lse.createIntStream(0, 5), lse.getLambdaPrinter("odd number:", "!"));
     * <p>
     * sout: odd number:1! odd number:3! odd number:5!
     *
     * @param intStream integer stream
     * @param printer   printer
     */
    @Override
    public void printOdd(IntStream intStream, Consumer<String> printer) {
        getOdd(intStream).forEach(num -> printer.accept(String.valueOf(num)));
    }

    /**
     * Square each number from the input. Please write two solutions and compare difference - using
     * flatMap
     *
     * @param ints integers
     * @return squares
     */
    @Override
    public Stream<Integer> flatNestedInt(Stream<List<Integer>> ints) {
        return ints.flatMap(r -> r.stream().map(x -> x * x));
    }
}