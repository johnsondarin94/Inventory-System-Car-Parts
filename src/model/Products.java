package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**Products class creates a model for a Product*/

public class Products {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**Constructor for the product
     *
     * @param id id of product
     * @param name name of product
     * @param price price of product
     * @param stock inventory of product
     * @param min minimum allowable amount of products
     * @param max maximum allowable amount of products
     *
     * */
    public Products(int id, String name, double price, int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**Getter for ID
    *
    * @return id
    *
    * */
    public int getId(){
        return id;
    }

    /**Setter for ID
    *
     * @param id sets ID for Product
    *
    * */
    public void setId(int id){
        this.id = id;

    }

    /**Getter for Name
    *
    * @return name
    *
    * */
    public String getName(){
        return name;
    }

    /**Setter for Name
    *
    * @param name Sets Name for Product
    *
    * */
    public void setName(String name){

        this.name = name;
    }

    /**Getter for Price
     *
     * @return price
     */
    public double getPrice(){
        return price;
    }

    /**Setter for Price
    *
    * @param price Sets Price for Product
    *
    * */
    public void setPrice(double price){

        this.price = price;
    }

    /**Getter for Inventory
    *
    * @return stock
    * */
    public int getStock(){

        return stock;
    }

    /**Setter for Stock
     *
     * @param stock Sets Inventory for Product
     *
     * */
    public void setStock(int stock){

        this.stock = stock;
    }

    /**Getter for Min
    *
    * @return min
    * */
    public int getMin(){

        return min;
    }

    /**Setter for Min
    *
    * @param min Sets Minimum allowable Products
    *
    * */
    public void setMin(int min){

        this.min = min;
    }

    /**Getter for Max
    *
    * @return max
    *
    * */
    public int getMax(){

        return max;
    }

    /**Setter for Max
    *
    * @param max Sets Maximum allowable Products
    *
    * */
    public void setMax(int max){

        this.max = max;
    }

    /**Method adds an associated part to a product with given Part
    *
    * @param part Takes a Part from the given list of Parts
    *
    *
    * */
    public void addAssociatedPart(Part part){

        associatedParts.add(part);
    }

    /**Method removes an Associated Part from the list of Parts in a given Product
    *
    * @param selectedAssociatedPart Associated Part to Delete
    * @return Boolean
    *
    * */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        if(associatedParts.equals(selectedAssociatedPart)){
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        else
            return false;
    }

    /**Method that returns the entire list of Associated Parts for a given Product
     *
     * @return Entire list of Associated Parts for a given Product
     *
     * */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }


}
