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

/**Controller Class for the IMSModifyProduct Scene*/
public class IMSModifyProduct implements Initializable {
    public TableView allPartsTable;
    public TableColumn allPartID;
    public TableColumn allPartName;
    public TableColumn allPartInventory;
    public TableColumn allPartPrice;
    public TableView associatedPartsTable;
    public TableColumn associatedPartID;
    public TableColumn associatedPartName;
    public TableColumn associatedPartInventory;
    public TableColumn associatedPartPrice;
    public TextField productSearchTextField;
    public Button removeAssociatedPartButton;
    public Button modifyProductCancelButton;
    public Button saveButton;
    public Button addButton;
    public TextField productID;
    public TextField productName;
    public TextField productInventory;
    public TextField productPrice;
    public TextField productMax;
    public TextField productMin;
    public Button searchButton;
    private Products productToModify = null;

    private ObservableList<Part> associatedPart = FXCollections.observableArrayList();

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

    /**Method that removes an associated part from a given product
     *
     * @param actionEvent  On user click remove
     *
     * */
    public void onRemove(ActionEvent actionEvent) {
        Part selectedPart = (Part) associatedPartsTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            displayError("Please select a Part to Remove");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Remove the associated part from this product?");
            Optional<ButtonType> result = alert.showAndWait();


            if (result.isPresent() && result.get() == ButtonType.OK) {
                associatedPart.remove(selectedPart);
                associatedPartsTable.setItems(associatedPart);
            }
        }
    }

    /**Method that replaces desired product with another
     *
     * @param actionEvent When user clicks save
     *
     * */
    public void onSave(ActionEvent actionEvent) {
        try {
            if (!Inventory.isValid(Integer.parseInt(productInventory.getText()), Integer.parseInt(productMin.getText()), Integer.parseInt(productMax.getText()))) {
                displayError("Please check Min and Max values with Inventory before continuing.");
            }
            if (Inventory.isValid(Integer.parseInt(productInventory.getText()), Integer.parseInt(productMin.getText()), Integer.parseInt(productMax.getText()))) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Modify the selected Product?");
                Optional<ButtonType> result = alert.showAndWait();

                Inventory.deleteProduct(productToModify);
                Products product = new Products(Inventory.generateUniqueID(), productName.getText(), Double.parseDouble(productPrice.getText()), Integer.parseInt(productInventory.getText()),
                        Integer.parseInt(productMin.getText()), Integer.parseInt(productMax.getText()));
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

    /**Method that adds an associated Part to a given product
     *
     * @param actionEvent on click add
     *
     * */
    public void onAdd(ActionEvent actionEvent) {
        Part selectedPart = (Part) allPartsTable.getSelectionModel().getSelectedItem();
        if(selectedPart == null){
            displayError("Please select a Part to add");
        }
        else {
            associatedPart.add(selectedPart);
            associatedPartsTable.setItems(associatedPart);
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
        productToModify = IMSFirstPage.getProductHandOff();
        associatedPart = productToModify.getAllAssociatedParts();

        productID.setText(String.valueOf(productToModify.getId()));
        productName.setText(productToModify.getName());
        productInventory.setText(String.valueOf(productToModify.getStock()));
        productPrice.setText(String.valueOf(productToModify.getPrice()));
        productMax.setText(String.valueOf(productToModify.getMax()));
        productMin.setText(String.valueOf(productToModify.getMin()));

        allPartsTable.setItems(Inventory.getAllParts());
        allPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        associatedPartsTable.setItems(associatedPart);
    }

    /**Method that searches for a part from the IMSModifyProducts Part List Table
     *
     * @param actionEvent when the user clicks search*/
    public void onSearch(ActionEvent actionEvent) {
        String searchResults = productSearchTextField.getText();

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


        allPartsTable.setItems(part);
    }
}
