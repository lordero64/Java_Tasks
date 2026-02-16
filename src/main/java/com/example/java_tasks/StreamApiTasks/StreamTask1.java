package com.example.java_tasks.StreamApiTasks;

import com.example.java_tasks.StreamApiTasks.TaskClasses.Order;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamTask1 {

    public static void main(String[] args) {
        List<Order> orders = List.of(
                new Order("Laptop", 1200.0),
                new Order("Smartphone", 800.0),
                new Order("Laptop", 1500.0),
                new Order("Tablet", 500.0),
                new Order("Smartphone", 900.0)
        );


        orders.stream()
                .collect(Collectors.groupingBy(Order::getProduct, Collectors.summingDouble(Order::getCost)))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Double>comparingByKey().reversed())
                .limit(3)
                .forEach(s -> System.out.printf("%s : %.2f%n", s.getKey(), s.getValue()));
    }
}
