import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.text.Text;

public class App extends Application {
    private double convertedValue;

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Metric Converter");

        GridPane grid = converter_page();
        Scene scene = new Scene(grid, 600, 400);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private GridPane converter_page() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        // Add labels for better user guidance
        Label measurementLabel = new Label("Enter Measurement:");
        Label fromUnitLabel = new Label("From Unit:");
        Label toUnitLabel = new Label("To Unit:");

        TextField originalMeasurement = new TextField();

        ComboBox<String> origUnitDropDown = new ComboBox<>(FXCollections.observableArrayList(
                "km", "mm", "celsius", "kg"
        ));

        ComboBox<String> newUnitDropDown = new ComboBox<>(FXCollections.observableArrayList(
                "miles", "inches", "fahrenheit", "lb"
        ));

        Text convertedValueText = new Text();
        Button convertButton = new Button("Convert Units");

        convertButton.setOnAction(e -> {
            try {
                String fromUnit = origUnitDropDown.getValue();
                String toUnit = newUnitDropDown.getValue();
                double value = Double.parseDouble(originalMeasurement.getText());

                // Create an instance of the Conversion class
                Conversion conversion = new Conversion(value, fromUnit, toUnit);

                // Call the convert method
                convertedValue = conversion.convert();
                convertedValueText.setText(String.valueOf(convertedValue));

                // Clear the result field on each conversion
                // This helps in providing a clean slate for the next conversion
                originalMeasurement.clear();
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        });

        // Add components to the grid
        grid.add(measurementLabel, 0, 0);
        grid.add(originalMeasurement, 1, 0);
        grid.add(fromUnitLabel, 0, 1);
        grid.add(origUnitDropDown, 1, 1);
        grid.add(toUnitLabel, 0, 2);
        grid.add(newUnitDropDown, 1, 2);
        grid.add(convertButton, 0, 3);
        grid.add(convertedValueText, 1, 3);

        return grid;
    }

    private static class Conversion {
        private double value;
        private String fromUnit;
        private String toUnit;
        private double convertedValue;

        private Conversion(double value, String fromUnit, String toUnit) {
            this.value = value;
            this.fromUnit = fromUnit;
            this.toUnit = toUnit;
        }

        private double convert() {
            switch (fromUnit + " = " + toUnit) {
                case "km = miles":
                    convertedValue = value * 0.6213712;
                    break;
                case "mm = inches":
                    convertedValue = value / 25.4;
                    break;
                case "celsius = fahrenheit":
                    convertedValue = (value * 1.8) + 32;
                    break;
                case "kg = lb":
                    convertedValue = value * 2.2046;
                    break;
                default:
                    System.out.println("Unsupported unit conversion.");
            }
            return convertedValue;
        }
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
