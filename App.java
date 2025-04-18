package com.mycompany.moneycruncher;

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


public class App extends Application {
   private final List<Map<String, Double>> weeklyData = new ArrayList<>(); // Stores all weeks' spending data
    private Map<String, Double> weeklySpending; // Current week's spending
    private int currentWeekIndex = 0; // Tracks current week index

    private TextField inputField; // Input for daily spending
    private ComboBox<String> daySelector; // Dropdown to select day
    private GridPane dayGrid; // Layout for displaying daily spending
    private Label weekLabel; // Displays current week
    private Label comparisonLabel; // Displays comparison between weeks
    private SpendingChart spendingChart; // Chart utility class instance

    @Override
    public void start(Stage primaryStage) {
        // Initialize the first week's data
        weeklySpending = new LinkedHashMap<>();
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        for (String day : days) {
            weeklySpending.put(day, 0.0);
        }
        weeklyData.add(new LinkedHashMap<>(weeklySpending));

        // Set up input controls
        daySelector = new ComboBox<>();
        daySelector.getItems().addAll(days);
        daySelector.setValue("Monday");
        daySelector.setStyle("-fx-font-size: 18px;");

        inputField = new TextField();
        inputField.setPromptText("Enter spending amount");
        inputField.setFont(Font.font(18));

        TextField goalInput = new TextField();
        goalInput.setPromptText("Enter weekly goal");
        goalInput.setFont(Font.font(16));
        Button setGoalButton = new Button("Set Goal");
        setGoalButton.setStyle("-fx-font-size: 16px; -fx-padding: 6 12;");

        // Create control buttons
        Button addButton = new Button("Add Spending");
        Button compareButton = new Button("Compare Spending");
        Button prevWeekButton = new Button("Previous Week");
        Button nextWeekButton = new Button("Next Week");
        Button quitButton = new Button("Quit");
        List<Button> buttons = List.of(addButton, compareButton, prevWeekButton, nextWeekButton, quitButton);
        buttons.forEach(b -> b.setStyle("-fx-font-size: 16px; -fx-padding: 10 20;"));

        weekLabel = new Label();
        weekLabel.setFont(Font.font("Arial", 24));
        updateWeekLabel();

        comparisonLabel = new Label();
        comparisonLabel.setFont(Font.font("Arial", 16));
        comparisonLabel.setWrapText(true);

        // Initialize chart
        spendingChart = new SpendingChart();
        BarChart<String, Number> chart = spendingChart.createChart();
        spendingChart.updateChart(weeklyData);

        setGoalButton.setOnAction(e -> {
            try {
                double goal = Double.parseDouble(goalInput.getText());
                spendingChart.setGoal(goal);
                spendingChart.updateChart(weeklyData);
                goalInput.clear();
            } catch (NumberFormatException ex) {
                new Alert(Alert.AlertType.ERROR, "Enter a valid number for the goal.").showAndWait();
            }
        });

        //  button actions
        addButton.setOnAction(e -> addSpending());
        compareButton.setOnAction(e -> compareSpending());
        quitButton.setOnAction(e -> primaryStage.close());
        prevWeekButton.setOnAction(e -> switchWeek(-1));
        nextWeekButton.setOnAction(e -> switchWeek(1));

        // Layout for control 
        VBox controlBox = new VBox(15,
            new Label("Select Day:"), daySelector,
            new Label("Spending Amount:"), inputField,
            addButton, compareButton,
new Label("Spending Goal:"), goalInput, setGoalButton,
quitButton);
        controlBox.setPrefWidth(300);
        controlBox.setStyle("-fx-padding: 20; -fx-background-color: #f0f4f8;");
        controlBox.setAlignment(Pos.TOP_CENTER);

        // Layout for daily 
        dayGrid = new GridPane();
        dayGrid.setHgap(15);
        dayGrid.setVgap(15);
        dayGrid.setStyle("-fx-padding: 30; -fx-background-color: #ffffff;");
        dayGrid.setAlignment(Pos.CENTER);
        updateDayGrid();

        // Combine display elements
        HBox navButtons = new HBox(10, prevWeekButton, nextWeekButton);
navButtons.setAlignment(Pos.CENTER);
VBox displayColumn = new VBox(20, weekLabel, navButtons, comparisonLabel, dayGrid, chart);
        displayColumn.setAlignment(Pos.TOP_CENTER);
        displayColumn.setPrefWidth(900);

        // Set up main layout
        HBox rootLayout = new HBox(30, controlBox, displayColumn);
        rootLayout.setStyle("-fx-padding: 30; -fx-background-color: #dce3ea;");

        primaryStage.setTitle("Spending Tracker");
        primaryStage.setScene(new Scene(rootLayout, 1200, 700));
        primaryStage.show();
    }

    // Adds spending amount to selected day
    private void addSpending() {
        String selectedDay = daySelector.getValue();
        try {
            double amount = Double.parseDouble(inputField.getText());
            weeklySpending.put(selectedDay, weeklySpending.get(selectedDay) + amount);
            inputField.clear();
            weeklyData.set(currentWeekIndex, new LinkedHashMap<>(weeklySpending));
            updateDayGrid();
            spendingChart.updateChart(weeklyData);
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Enter a valid number.").showAndWait();
        }
    }

    // Updates the daily grid display
    private void updateDayGrid() {
        updateWeekLabel();
        dayGrid.getChildren().clear();
        int col = 0;
        int row = 0;
        for (Map.Entry<String, Double> entry : weeklySpending.entrySet()) {
            VBox dayBox = new VBox();
            dayBox.setStyle("-fx-border-color: #7a7a7a; -fx-background-color: #e9f5ff; -fx-padding: 20; -fx-border-radius: 8; -fx-background-radius: 8;");
            dayBox.setAlignment(Pos.CENTER);
            dayBox.setPrefSize(160, 120);

            Label dayLabel = new Label(entry.getKey());
            dayLabel.setFont(Font.font("Arial", 18));
            Label amountLabel = new Label(String.format("$%.2f", entry.getValue()));
            amountLabel.setFont(Font.font("Arial", 20));
            amountLabel.setTextFill(Color.DARKBLUE);

            dayBox.getChildren().addAll(dayLabel, amountLabel);
            dayGrid.add(dayBox, col, row);
            col++;
            if (col > 3) {
                col = 0;
                row++;
            }
        }
    }

    // Updates the label showing the current week
    private void updateWeekLabel() {
        if (weekLabel != null) {
            weekLabel.setText("Current Week: " + (currentWeekIndex + 1));
        }
    }

    // Compares this week's spending with the previous week's
    private void compareSpending() {
        if (currentWeekIndex == 0) {
            comparisonLabel.setText("No previous week data to compare.");
            comparisonLabel.setTextFill(Color.BLACK);
            return;
        }
        Map<String, Double> previousWeek = weeklyData.get(currentWeekIndex - 1);
        String result = SpendingComparator.compare(previousWeek, weeklySpending);
        comparisonLabel.setText(result);
        if (result.contains("Increase")) {
            comparisonLabel.setTextFill(Color.FIREBRICK);
        } else if (result.contains("Decrease")) {
            comparisonLabel.setTextFill(Color.FORESTGREEN);
        } else {
            comparisonLabel.setTextFill(Color.BLACK);
        }
    }

    // Switches between previous and next weeks
    private void switchWeek(int direction) {
        int newIndex = currentWeekIndex + direction;
        if (newIndex < 0) {
            new Alert(Alert.AlertType.WARNING, "No earlier week available.").showAndWait();
            return;
        }
        if (newIndex >= weeklyData.size()) {
            Map<String, Double> newWeek = new LinkedHashMap<>();
            for (String day : weeklySpending.keySet()) {
                newWeek.put(day, 0.0);
            }
            weeklyData.add(newWeek);
        }
        currentWeekIndex = newIndex;
        weeklySpending = weeklyData.get(currentWeekIndex);
        updateDayGrid();
        spendingChart.updateChart(weeklyData);
    }

    // Launches the JavaFX application
    public static void main(String[] args) {
        launch(args);
    }
}
