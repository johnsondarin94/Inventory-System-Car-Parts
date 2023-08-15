package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

    /**
    *
    * This is an Inventory Management System
    * Allows the user to create, modify and delete parts and products.
    * Parts are separate whereas Products may have associated parts linked to them.
    * User may also search for parts and products given a partial string or exact ID
     *
     * FUTURE ENHANCEMENT A potential enhancement for this application could be to link
     * it to a database and have parts and products from the database populate the tables
     * as needed.
     *
    *
    * */
public class Main extends Application{

    /**The start method creates the stage and loads the scene with given size and title
     *
     * @param primaryStage First Stage to load
     *
     * */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/IMSFirstPage.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root, 1050, 500));
        primaryStage.show();
    }

    /**The main method launches the application
    *
    * @param args
    * */
    public static void main(String[] args) {
        launch(args);
    }

}
