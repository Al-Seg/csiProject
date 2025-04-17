# MoneyCruncher by Alex Segura 
This Project will be an expense tracker that can track what you spend every day and give back simple data such as spending for the week, or per day. It will also be able to track increases or decreases in spending through the weeks. Additionally, it will show trends with the use of a graph.
I want to build this as many people that I know and see struggle with overspending, so making a simple app that can track spending and tell you if you spend more or less daily or weekly visually will hopefully help users. I belive that money management is a good skill to have as money is somthing we could all use more of. 


Contributions
Alexandro Segura – Developed the JavaFX UI, created week and day navigation functionality, made comparison logic, integrated the goal input and chart.

User Manual
Launch the Application: Open the project in NetBeans or any Java IDE with JavaFX support and run the SpendingTracker class.

Track Spending:
Use the dropdown to select a day.
Enter your spending for that day and click “Add Spending.”
Repeat for other days as needed.

Set a Goal:
Enter a number in the “Spending Goal” field and click “Set Goal.”
A horizontal line will appear on the chart showing your weekly cap.

Navigate Weeks:
Use the “Next Week” and “Previous Week” buttons to move through your weekly history.
Your chart and daily data update accordingly.

Compare Spending:
Click “Compare Spending” to see how your current week stacks up against the previous one.

Implementation Manual
The program uses three main classes:

SpendingTracker (Main)
The JavaFX driver class
Sets up the GUI layout with HBox, VBox, GridPane
Manages week switching, input handling, and event flow

SpendingChart
Handles creation and updating of the bar chart
Displays weekly spending totals
Adds a second bar line when set

SpendingComparator
Compares spending between the current and previous week
Returns formatted output with percent increase or decrease

