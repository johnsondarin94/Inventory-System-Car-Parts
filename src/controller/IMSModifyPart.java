package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.OutSourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**Controller Class for IMSModifyPart Scene*/
public class IMSModifyPart implements Initializable {

    public Button modifyPartCancelButton;
    public RadioButton modifyPartInHouseRB;
    public RadioButton modifyPartOutsourcedRB;
    public Label mpChangingLabel;
    public TextField partID;
    public TextField partName;
    public TextField partInventory;
    public TextField partPrice;
    public TextField partMax;
    public TextField partChanging;
    public TextField partMin;
    public Button mpSaveButton;
    private Part partToModify = null;

    /**Method that returns user to IMSFirstPage
     *
     * @param actionEvent When user clicks Cancel, or when user successfully modifies a part
     *
     * */
    public void toMain(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/IMSFirstPage.fxml"));
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

    /**Method that sets the last label to Machine ID when user clicks the onMPInHouse Radio Button
     *
     * @param actionEvent When user clicks on the MPInHouse Radio Button
     *
     * */
    public void onMPInHouse(ActionEvent actionEvent) {
        mpChangingLabel.setText("Machine ID");
    }

    /**Method that sets the last label to Company Name when user clicks the onMPOutSourced Radio Button
     *
     * @param actionEvent When user clicks on the MPOutSourced Radio Button
     *
     * */
    public void onMPOutsourced(ActionEvent actionEvent) {
        mpChangingLabel.setText("Company Name");
    }

    /**Method that saves changes made to a part by deleting old part and creating a new one
     *
     * @param actionEvent When user clicks Save
     *
     * */
    public void onSave(ActionEvent actionEvent) {
        try {
            if (!Inventory.isValid(Integer.parseInt(partInventory.getText()), Integer.parseInt(partMin.getText()), Integer.parseInt(partMax.getText()))) {
                displayError("Please check Min and Max values with Inventory before continuing.");
            }

            if (modifyPartInHouseRB.isSelected() && Inventory.isValid(Integer.parseInt(partInventory.getText()), Integer.parseInt(partMin.getText()), Integer.parseInt(partMax.getText()))) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Create New Part?");
                Optional<ButtonType> result = alert.showAndWait();

                Inventory.deletePart(partToModify);

                InHouse part = new InHouse(Inventory.generateUniqueID(), partName.getText(), Double.parseDouble(partPrice.getText()), Integer.parseInt(partInventory.getText()),
                        Integer.parseInt(partMin.getText()), Integer.parseInt(partMax.getText()), Integer.parseInt(partChanging.getText()));
                Inventory.addPart(part);
                toMain(actionEvent);
            }

            if (modifyPartOutsourcedRB.isSelected() && Inventory.isValid(Integer.parseInt(partInventory.getText()), Integer.parseInt(partMin.getText()), Integer.parseInt(partMax.getText()))) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Create New Part?");
                Optional<ButtonType> result = alert.showAndWait();

                Inventory.deletePart(partToModify);

                OutSourced part = new OutSourced(Inventory.generateUniqueID(), partName.getText(), Double.parseDouble(partPrice.getText()), Integer.parseInt(partInventory.getText()),
                        Integer.parseInt(partMin.getText()), Integer.parseInt(partMax.getText()), partChanging.getText());
                Inventory.addPart(part);
                toMain(actionEvent);

            }
        }

        catch(NumberFormatException | IOException e)
        {
            displayError("Please enter in proper values for all fields.");
        }

    }

    /**Method that Initializes controller and sets up desired tables and columns
     *
     * @param url
     * @param resourceBundle
     *
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partToModify = IMSFirstPage.getPartHandOff();
        partID.setText(String.valueOf(partToModify.getId()));
        partName.setText(partToModify.getName());
        partInventory.setText(String.valueOf(partToModify.getStock()));
        partPrice.setText(String.valueOf(partToModify.getPrice()));
        partMin.setText(String.valueOf(partToModify.getMin()));
        partMax.setText(String.valueOf(partToModify.getMax()));

        if(partToModify instanceof InHouse){
            modifyPartInHouseRB.setSelected(true);
            mpChangingLabel.setText("MachineID");
            partChanging.setText(String.valueOf(((InHouse) partToModify).getMachineId()));
        }

        if(partToModify instanceof OutSourced){
            modifyPartOutsourcedRB.setSelected(true);
            mpChangingLabel.setText("Company Name");
            partChanging.setText(((OutSourced) partToModify).getCompanyName());

        }


    }


}
