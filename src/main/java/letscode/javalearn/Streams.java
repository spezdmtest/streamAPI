package letscode.javalearn;

import javafx.geometry.Pos;
import letscode.javalearn.domain.Department;
import letscode.javalearn.domain.Employee;
import letscode.javalearn.domain.Event;
import letscode.javalearn.domain.Position;
import org.junit.Test;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.IntBinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Streams {
    private List<Employee> emps = List.of(
            new Employee("Michael", "Smith", 243, 43, Position.CHEF),
            new Employee("Jane", "Smith", 523, 40, Position.MANAGER),
            new Employee("Jury", "Gagarin", 6423, 26, Position.MANAGER),
            new Employee("Jack", "London", 5543, 53, Position.WORKER),
            new Employee("Eric", "Jackson", 2534, 22, Position.WORKER),
            new Employee("Andrew", "Bosh", 3456, 44, Position.WORKER),
            new Employee("Joe", "Smith", 723, 30, Position.MANAGER),
            new Employee("Jack", "Gagarin", 7423, 35, Position.MANAGER),
            new Employee("Jane", "London", 7543, 42, Position.WORKER),
            new Employee("Mike", "Jackson", 7534, 31, Position.WORKER),
            new Employee("Jack", "Bosh", 7456, 54, Position.WORKER),
            new Employee("Mark", "Smith", 123, 41, Position.MANAGER),
            new Employee("Jane", "Gagarin", 1423, 28, Position.MANAGER),
            new Employee("Sam", "London", 1543, 52, Position.WORKER),
            new Employee("Jack", "Jackson", 1534, 27, Position.WORKER),
            new Employee("Eric", "Bosh", 1456, 32, Position.WORKER)
    );

    private List<Department> deps = List.of(
            new Department(1, 0, "Head"),
            new Department(2, 1, "West"),
            new Department(3, 1, "East"),
            new Department(4, 2, "Germany"),
            new Department(5, 2, "France"),
            new Department(6, 3, "China"),
            new Department(7, 3, "Japan")
    );

    @Test
    public void creation() throws IOException {
        Stream<String> lines = Files.lines(Paths.get("some.txt"));
        Stream<Path> list = Files.list(Paths.get("./"));
        Files.walk(Paths.get("./"), 3);

        IntStream intStream1 = IntStream.of(1, 2, 3, 4);
        DoubleStream doubleStream = DoubleStream.of(1.2, 3.4);
        IntStream range = IntStream.range(10, 100); //10....99
        IntStream rangeClosed = IntStream.rangeClosed(10, 100);//10....100

        int[] ints = {1, 2, 3, 4};
        Arrays.stream(ints);

        Stream<String> stringStream = Stream.of("1", "2", "3");
        Stream<? extends Serializable> stream = Stream.of(1, "2", "3");

        Stream<String> build = Stream.<String>builder()
                .add("Mike")
                .add("Joe")
                .build();

        Stream<Employee> collection = emps.stream();
        Stream<Employee> employeeStream = emps.parallelStream();

        Stream<Event> generate = Stream.generate(() ->
                new Event(UUID.randomUUID(), LocalDateTime.now(), "")
        );

        Stream<Integer> iterate = Stream.iterate(1950, val -> val + 3);
        Stream<String> concat = Stream.concat(stringStream, build);
    }

    @Test
    public void terminate() {
        emps.stream().count();
        // emps.stream().forEach(employee -> System.out.println(employee.getAge()));
        // emps.forEach(employee -> System.out.println(employee.getAge()));
        // emps.stream().forEachOrdered(employee -> System.out.println(employee.getAge()));
        emps.stream().collect(Collectors.toList());
        emps.stream().toArray();
        Map<Integer, String> collect = emps.stream().collect(Collectors.toMap(
                Employee::getId,
                emp -> String.format("%s %s", emp.getLastName(), emp.getFirstName())
        ));

        IntStream intStream = IntStream.of(100, 200, 300, 400);
        intStream.reduce((left, right) -> left + right).orElse(0);

        System.out.println(deps.stream().reduce(this::reducer));

        System.out.println(IntStream.of(100,200,300,400).average());
        System.out.println(IntStream.of(100,200,300,400).max());
        System.out.println(IntStream.of(100,200,300,400).min());
        System.out.println(IntStream.of(100,200,300,400).sum());
        System.out.println(IntStream.of(100,200,300,400).summaryStatistics());
        System.out.println(emps.stream().max(Comparator.comparing(Employee::getAge)));
        emps.stream().findAny();
        emps.stream().findFirst();

        emps.stream().noneMatch(employee -> employee.getAge() > 60);
        emps.stream().anyMatch(employee -> employee.getPosition() == Position.CHEF);
    }

    public Department reducer(Department parent, Department child) {
        if (child.getParent() == parent.getId()) {
            parent.getChild().add(child);
        } else {
            parent.getChild().forEach(subParent -> reducer(subParent,child));
        }
        return parent;
}








}