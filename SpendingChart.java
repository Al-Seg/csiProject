/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.spendingtracker;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.*;
/**
 *
 * @author ostri
 */
public class SpendingChart {
     private BarChart<String, Number> chart; // Bar chart reference
    private XYChart.Series<String, Number> series; // Data series for the chart
    private XYChart.Series<String, Number> goalSeries = new XYChart.Series<>(); // Goal line
    private double goalAmount = 0.0;

    // Initializes and returns the bar chart
    public BarChart<String, Number> createChart() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Week");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Total Spending");

        chart = new BarChart<>(xAxis, yAxis);
        chart.setTitle("Weekly Spending Overview");
        chart.setLegendVisible(true);

        series = new XYChart.Series<>();
        series.setName("Spending");
        goalSeries.setName("Goal");

        chart.getData().addAll(series, goalSeries);
        return chart;
    }

    // Updates the bar chart with new data
    public void updateChart(List<Map<String, Double>> weeklyData) {
        series.getData().clear();
        goalSeries.getData().clear();
        for (int i = 0; i < weeklyData.size(); i++) {
            double total = weeklyData.get(i).values().stream().mapToDouble(Double::doubleValue).sum();
            String week = "Week " + (i + 1);
            series.getData().add(new XYChart.Data<>(week, total));
            goalSeries.getData().add(new XYChart.Data<>(week, goalAmount));
        }
    }

    public void setGoal(double goal) {
        this.goalAmount = goal;
    }
}
