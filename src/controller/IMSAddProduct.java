package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static model.Inventory.lookupPart;
/**Controller Class for IMSAddProduct Scene*/
public class IMSAddProduct implements Initializable {

    public Button addProductCancelButton;
    public TableView fullPartTable;
    public TableView associatedPartTable;
    public TextField searchPartTextField;
    public Button removeAssociatedPartButton;
    public Button saveButton;
    public Button addProductAddButton;
    public TextField idTextField;
    public TextField nameTextField;
    public TextField inventoryTextField;
    public TextField priceTextField;
    public TextField maxTextField;
    public TextField minTextField;
    public TableColumn allPartID;
    public TableColumn allPartName;
    public TableColumn allPartInventory;
    public TableColumn allPartPrice;
    public TableColumn associatedPartID;
    public TableColumn associatedPartName;
    public TableColumn associatedPartInventory;
    public TableColumn associatedPartPrice;
    public Button searchButton;

    private ObservableList<Part> associatedPart = FXCollections.observableArrayList();

    /**Method that returns to IMSFirstPage at the click of the cancel button
     *
     * @param actionEvent When user clicks cancel
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

    /**Method removes associated part from a given product
     *
     * @param actionEvent When user clicks remove in the IMSAddProduct Scene
     *
     * */
    public void onRemoveAssociatedPart(ActionEvent actionEvent) {
        Part selectedPart = (Part) associatedPartTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {

            displayError("Please select a Part to remove");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Remove the associated part from this product?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {

                associatedPart.remove(selectedPart);
                associatedPartTable.setItems(associatedPart);
            }
        }
    }

    /**Method that creates a new Product and adds any associated parts to it.
     *
     * @param actionEvent When User clicks Save on the IMSAddProductPage
     *
     * */
    public void onSave(ActionEvent actionEvent) {
        try {
            if (!Inventory.isValid(Integer.parseInt(inventoryTextField.getText()), Integer.parseInt(minTextField.getText()), Integer.parseInt(maxTextField.getText()))) {
                displayError("Please check Min and Max values with Inventory before continuing.");
            }
            if (Inventory.isValid(Integer.parseInt(inventoryTextField.getText()), Integer.parseInt(minTextField.getText()), Integer.parseInt(maxTextField.getText()))) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Create New Product?");
                Optional<ButtonType> result = alert.showAndWait();

                Products product = new Products(Inventory.generateUniqueID(), nameTextField.getText(), Double.parseDouble(priceTextField.getText()), Integer.parseInt(inventoryTextField.getText()),
                        Integer.parseInt(minTextField.getText()), Integer.parseInt(maxTextField.getText()));
                for (Part part : associatedPart) {
                    product.addAssociatedPart(part);
                }
                Inventory.addProduct(product);
                toMain(actionEvent);

            }
        }
        catch(NumberFormatException | IOException e)
        {
           displayError("Please enter in proper values for all fields.");
        }

    }

    /**Method that adds an associated part to a Product
     *
     * @param actionEvent When user clicks add
     *
     * */
    public void onAdd(ActionEvent actionEvent) {
       Part selectedPart = (Part) fullPartTable.getSelectionModel().getSelectedItem();
       if(selectedPart == null){
           displayError("Please select a Part to add");
       }
       else {
           associatedPart.add(selectedPart);
           associatedPartTable.setItems(associatedPart);
       }
    }

    /**Method that Initializes controller and sets up desired tables and columns
     *
     * @param url
     * @param resourceBundle
     *
     * */
    public void initialize(URL url, ResourceBundle resourceBundle){

        fullPartTable.setItems(Inventory.getAllParts());
        allPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**Method that searches for a given Part in the Part Table
     *
     * @param actionEvent On click Search
     *
     * */
    public void onSearch(ActionEvent actionEvent) {
        String searchResults = searchPartTextField.getText();

        ObservableList<Part> part = lookupPart(searchResults);

        if(part.size() == 0){
            try {
                int partId = Integer.parseInt(searchResults);
                Part parts = lookupPart(partId);
                if (part!= null) {
                    part.add(parts);
                }
            }
            catch (NumberFormatException e)
            {
                //ignore
            }
        }

        fullPartTable.setItems(part);
    }
}
