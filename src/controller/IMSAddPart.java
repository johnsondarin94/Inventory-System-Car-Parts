package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.OutSourced;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

/**Controller Class for the AddPart Scene*/
public class IMSAddPart {

    public Button addPartCancelButton;
    public RadioButton addPartInHouseRB;
    public Label APChangingLabel;
    public RadioButton addPartOutsourcedRB;
    public Button saveButton;
    public TextField nameTextField;
    public TextField inventoryTextField;
    public TextField priceTextField;
    public TextField maxTextField;
    public TextField changingTextField;
    public TextField minTextField;

    /**Method returns user to IMSFirstPage
     *
     * @param actionEvent When user clicks cancel
     *
     * */
    public void toMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/IMSFirstPage.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1050, 500);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /**Method displays an Error Alert and takes String as input
     * Used to simplify code
     *
     * @param stringAlert Defines what the Alert Message says
     *
     * */
    public void displayError(String stringAlert) {
        Alert alert1 = new Alert(Alert.AlertType.ERROR, stringAlert);
        Optional<ButtonType> result = alert1.showAndWait();
    }
    /**Method sets the IMSAddPart Label to Machine ID
     *
     * @param actionEvent on click to the InHouse Radio Button
     *
     * */
    public void onAPInHouse(ActionEvent actionEvent) {
        APChangingLabel.setText("Machine ID");
    }

    /**Method sets the IMSAddPart Label to Company Name
     *
     * @param actionEvent on click to the OutSourced Radio Button
     *
     * */
    public void onAPOutsourced(ActionEvent actionEvent) {
        APChangingLabel.setText("Company Name");
    }

    /**Controller Method that creates a new Part if given conditions are met
     *
     * @param actionEvent When user clicks Save
     *
     * */
    public void onSave(ActionEvent actionEvent) {
        try {
            if (!Inventory.isValid(Integer.parseInt(inventoryTextField.getText()), Integer.parseInt(minTextField.getText()), Integer.parseInt(maxTextField.getText()))) {
                displayError("Please check Min and Max values with Inventory before continuing.");
            }

            if (addPartInHouseRB.isSelected() && Inventory.isValid(Integer.parseInt(inventoryTextField.getText()), Integer.parseInt(minTextField.getText()), Integer.parseInt(maxTextField.getText()))) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Create New Part?");
                Optional<ButtonType> result = alert.showAndWait();

                InHouse part = new InHouse(Inventory.generateUniqueID(), nameTextField.getText(), Double.parseDouble(priceTextField.getText()), Integer.parseInt(inventoryTextField.getText()),
                        Integer.parseInt(minTextField.getText()), Integer.parseInt(maxTextField.getText()), Integer.parseInt(changingTextField.getText()));
                Inventory.addPart(part);
                toMain(actionEvent);
            }

            if (addPartOutsourcedRB.isSelected() && Inventory.isValid(Integer.parseInt(inventoryTextField.getText()), Integer.parseInt(minTextField.getText()), Integer.parseInt(maxTextField.getText()))) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Create New Part?");
                Optional<ButtonType> result = alert.showAndWait();

                OutSourced part = new OutSourced(Inventory.generateUniqueID(), nameTextField.getText(), Double.parseDouble(priceTextField.getText()), Integer.parseInt(inventoryTextField.getText()),
                        Integer.parseInt(minTextField.getText()), Integer.parseInt(maxTextField.getText()), changingTextField.getText());
                Inventory.addPart(part);
                toMain(actionEvent);

            }
        }

        catch(NumberFormatException | IOException e)
        {
            displayError("Please enter in proper values for all fields.");
        }

    }
}
