package controller;

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
import model.Inventory;
import model.Part;
import model.Products;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static model.Inventory.lookupPart;
import static model.Inventory.lookupProduct;

/**Controller Class for the main scene*/
public class IMSFirstPage implements Initializable {
    public TableView productsTable;
    public TableColumn productsTableId;
    public TableColumn productsTableName;
    public TableColumn productsTableInventoryLevel;
    public TableColumn productsTablePrice;
    public Button addProductButton;
    public Button deleteProductButton;
    public Button ModifyProductButton;
    public Button addPartButton;
    public TableView partsTable;
    public TableColumn partsTableId;
    public TableColumn partsTableName;
    public TableColumn partsTableInventoryLevel;
    public TableColumn partsTablePrice;
    public Button modifyPartButton;
    public Button deletePartButton;
    public Button exitApplicationButton;
    public Button searchPartsButton;
    public TextField searchProductsField;
    public Button searchProductsButton;
    public TextField searchPartsField;

    private static Part partHandOff = null;
    private static Products productHandOff = null;
    //private ObservableList<Part> allPart = FXCollections.observableArrayList();
    //private ObservableList<Products> allProducts = FXCollections.observableArrayList();

    /**Method that sends user to the IMSAddPart Scene
     *
     * @param actionEvent When User Clicks Add from the Parts Table
     *
     * */
    public void toAddPart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/IMSAddPart.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 500);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    /**Method returns selected part data to the IMSModifyPart Scene
     * Sends to Initialize
     *
     * @return partHandOff returns selected part
     *
     * */
    public static Part getPartHandOff() {
        return partHandOff;
    }

    /**Method returns selected product data to the IMSModifyProduct Scene
     * Sends to Initialize
     *
     * @return productHandOff returns selected Product
     *
     * */
    public static Products getProductHandOff() {
        return productHandOff;
    }

    /**Method sends user to the IMSModifyPart Scene
     *
     * @param actionEvent on click Modify from the parts table
     *
     * */
    public void toModifyPart(ActionEvent actionEvent) throws IOException {
        try {
            partHandOff = (Part) partsTable.getSelectionModel().getSelectedItem();

            Parent root = FXMLLoader.load(getClass().getResource("/view/IMSModifyPart.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 600, 500);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            displayError("Please select a Part to Modify.");
        }
    }

    /**Method sends user to the IMSAddProduct Scene
     *
     * @param actionEvent on click Add from the products table
     *
     * */
    public void toAddProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/IMSAddProduct.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 700);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    /**Method sends user to the IMSModifyProduct Scene
     *
     *
     * @param actionEvent on click Modify from the products table
     *
     * */
    public void toModifyProduct(ActionEvent actionEvent) throws IOException {
        try {
            productHandOff = (Products) productsTable.getSelectionModel().getSelectedItem();

            Parent root = FXMLLoader.load(getClass().getResource("/view/IMSModifyProduct.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 900, 700);
            stage.setTitle("Modify Product");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            displayError("Please select a Product to Modify");
        }

    }

    /**Method closes the program*/
    public void onExit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Exit the Program?");
        Optional<ButtonType> result = alert.showAndWait();
        System.exit(0);
    }

    /**Method that searches for products from the products table and populates them
     *
     * @param actionEvent on Product Search click
     *
     * */
    public void getProductResults(ActionEvent actionEvent) {
        String searchResults = searchProductsField.getText();

        ObservableList<Products> products = lookupProduct(searchResults);

        if (products.size() == 0) {
            try {
                int productId = Integer.parseInt(searchResults);
                Products product = lookupProduct(productId);
                if (product != null) {
                    products.add(product);
                }
            } catch (NumberFormatException e) {
                //ignore
            }
        }


        productsTable.setItems(products);
    }

    /**Method that searches for parts from the parts table and populates them
     *
     * @param actionEvent on Product Search click
     *
     * */
    public void getPartResults(ActionEvent actionEvent) {
        String searchResults = searchPartsField.getText();

        ObservableList<Part> part = lookupPart(searchResults);

        if (part.size() == 0) {
            try {
                int partId = Integer.parseInt(searchResults);
                Part parts = lookupPart(partId);
                if (part != null) {
                    part.add(parts);
                }
            } catch (NumberFormatException e) {
                //ignore
            }
        }


        partsTable.setItems(part);
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

    /**Method removes product from table and from Inventory if parameters are met
     *
     * RUNTIME ERROR If user were to click on the delete product button without having selected a
     * product to delete, the application would through a runtime. This was solved by putting the
     * method in a try catch block and if an exception occurs would display an Error Alert as
     * opposed to crashing.
     *
     * @param actionEvent  when User clicks delete from the products table*/
    public void onProductsDelete(ActionEvent actionEvent) {
        try {
            Products selectedProduct = (Products) productsTable.getSelectionModel().getSelectedItem();
            ObservableList<Part> associatedParts = selectedProduct.getAllAssociatedParts();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete Selected Product?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

                if (associatedParts.size() >= 1) {
                    displayError("Product has Associated Parts, unable to delete");
                } else {
                    Inventory.deleteProduct(selectedProduct);
                    productsTable.getSelectionModel().clearSelection();
                }
            }
        } catch (Exception e) {
            displayError("Please Select a Product to delete");
        }
    }

    /**Method removes Part from table and from Inventory
     *
     * @param actionEvent  when User clicks delete from the parts table
     *
     * */
    public void onPartsDelete(ActionEvent actionEvent) {
        try {
            Part selectedPart = (Part) partsTable.getSelectionModel().getSelectedItem();
            if (selectedPart == null) {
                displayError("Please Select a Part to delete");

            } else {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete the selected Part?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {

                    Inventory.deletePart(selectedPart);
                    partsTable.getSelectionModel().clearSelection();
                }
            }
        }
        catch(Exception e){
            displayError("Please Select a Part to Delete");
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

        partsTable.setItems(Inventory.getAllParts());
        partsTableId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsTablePrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        partsTableInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));


        productsTable.setItems(Inventory.getAllProducts());
        productsTableId.setCellValueFactory(new PropertyValueFactory<>("id"));
        productsTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productsTablePrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        productsTableInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));

    }
}
