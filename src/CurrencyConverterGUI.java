import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class CurrencyConverterGUI extends Application {

    private ComboBox<String> targetCurrencyComboBox;
    private TextField amountTextField;
    private Label resultLabel;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Currency Converter");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        Label defaultCurrencyLabel = new Label("Default currency base is USD");
        gridPane.add(defaultCurrencyLabel, 0, 0, 2, 1);

        targetCurrencyComboBox = new ComboBox<>();
        targetCurrencyComboBox.setPromptText("Select Target Currency");

        amountTextField = new TextField();
        amountTextField.setPromptText("Enter Amount");

        Button convertButton = new Button("Convert");
        convertButton.setOnAction(e -> convertCurrency());

        resultLabel = new Label();

        HBox buttonBox = new HBox(10, convertButton);

        gridPane.add(targetCurrencyComboBox, 0, 2);
        gridPane.add(amountTextField, 0, 3);
        gridPane.add(buttonBox, 1, 3);
        gridPane.add(resultLabel, 0, 4, 2, 1);

        Scene scene = new Scene(gridPane, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Populate currency options
        populateCurrencyOptions();
    }

    private void populateCurrencyOptions() {
        // For simplicity, providing a fixed list of currencies
        String[] currencies = {"USD", "EUR", "GBP", "JPY", "CAD", "BGN", "CHF", "CNY", "CZK", "DKK", "HKD", "HUF", "INR", "ISK", "KRW", "MXN", "MYR", "NOK", "NZD", "PHP", "PLN", "RON", "RUB", "SEK", "SGD", "THB", "TRY", "ZAR"};
        targetCurrencyComboBox.getItems().addAll(currencies);
    }

    private void convertCurrency() {
        String targetCurrency = targetCurrencyComboBox.getValue();
        double amountToConvert;
        try {
            amountToConvert = Double.parseDouble(amountTextField.getText());
        } catch (NumberFormatException e) {
            resultLabel.setText("Invalid amount");
            return;
        }

        double convertedAmount;
        if (targetCurrency.equals("USD")) {
            // If target currency is USD, convert back to USD
            convertedAmount = Convert.convertCurrency("USD", amountToConvert);
            resultLabel.setText(String.format("%.2f %s is approximately %.2f USD", amountToConvert, targetCurrency, convertedAmount));
        } else {
            // Otherwise, convert to the selected currency
            convertedAmount = Convert.convertCurrency(targetCurrency, amountToConvert);
            resultLabel.setText(String.format("%.2f USD is approximately %.2f %s", amountToConvert, convertedAmount, targetCurrency));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}