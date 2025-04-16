/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.spendingtracker;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.LinkedHashMap;
import java.util.Map;
/**
 *
 * @author ostri
 */
class SpendingComparator {
     public static String compare(Map<String, Double> previousWeek, Map<String, Double> currentWeek) {
        double previousTotal = previousWeek.values().stream().mapToDouble(Double::doubleValue).sum();
        double currentTotal = currentWeek.values().stream().mapToDouble(Double::doubleValue).sum();

        if (previousTotal == 0.0) {
            return "No previous week data to compare.";
        } else {
            double change = currentTotal - previousTotal;
            double percentChange = (change / previousTotal) * 100;
            String trend = change > 0 ? "Increase" : "Decrease";
            return String.format("This week: $%.2f\nLast week: $%.2f\n%s of %.2f%%",
                    currentTotal, previousTotal,
                    trend, Math.abs(percentChange));
        }
    }
}