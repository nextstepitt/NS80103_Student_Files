package com.tc3.tc3service.services;

public interface ICalculator {

    double add(double x, double y);
    double subtract(double x, double y);
    double multiply(double x, double y);
    double divide(double x, double y);
    boolean isTrue(String word);
}
