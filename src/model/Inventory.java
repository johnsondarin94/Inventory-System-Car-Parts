
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**Container class for all Parts and Products*/
public class Inventory {
    private static ObservableList<Part> PartList = FXCollections.observableArrayList();
    private static ObservableList<Products> ProductsList = FXCollections.observableArrayList();

    private static int id = 1000;

        static {
            addTestData();
        }
    /**Method adds test data to sample. NOTE: Test Products do not contain associated parts at runtime!*/
        public static void addTestData(){

            Products mazda = new Products(generateUniqueID(), "Mazda", 200.00, 5, 1, 100);
            Inventory.addProduct(mazda);
            Products ford = new Products(generateUniqueID(), "Ford", 250.00, 2, 1, 100);
            Inventory.addProduct(ford);
            Products toyota = new Products(generateUniqueID(), "Toyota", 300.00, 6, 1, 100);
            Inventory.addProduct(toyota);
            Products chevrolet = new Products(generateUniqueID(), "Chevrolet", 350.00, 3, 1, 100);
            Inventory.addProduct(chevrolet);

            InHouse muffler = new InHouse(generateUniqueID(), "Muffler", 20.00, 4,1,10,10002);
            Inventory.addPart(muffler);
            InHouse brakePad = new InHouse(generateUniqueID(), "Brake Pad", 15.00, 10,1,10,10502);
            Inventory.addPart(brakePad);
            InHouse rim = new InHouse(generateUniqueID(), "Rim", 20.00, 4,1,10,12002);
            Inventory.addPart(rim);
            OutSourced steeringWheel = new OutSourced(generateUniqueID(), "Steering Wheel", 5.00, 2, 1, 10, "AutoZone");
            Inventory.addPart(steeringWheel);
            OutSourced radio = new OutSourced(generateUniqueID(), "Radio", 50.00, 6, 1, 10, "Napa");
            Inventory.addPart(radio);
            OutSourced hood = new OutSourced(generateUniqueID(), "Hood", 100.00, 1, 1, 10, "AutoZone");
            Inventory.addPart(hood);
            }

        /**Method takes in 3 integers and ensures inventory falls in between the other 2
         *
         * @param inventory Integer is checked with min and max
         * @param min minimum allowable Parts or Products
         * @param max maximum allowable Parts or Products
         *
         * @return Returns true if inventory falls in between the other 2 values and false if it does not
         *
         * */
        public static boolean isValid(int inventory, int min, int max) {
            if (inventory >= min && inventory <= max) {
                return true;
            } else {
                return false;
            }
        }

        /**Generates a unique ID for both Parts and Products
         *
         * @return Returns a Unique ID
         *
         * */
        public static int generateUniqueID(){
            return ++id * 10;
        }

        /**Method adds the given part to Inventory
         *
         * @param part Part to Add to Inventory
         *
         * */
        public static void addPart (Part part){
            PartList.add(part);
        }

        /**Method adds the given Product to Inventory
         *
         * @param product Product to add to Inventory
         *
         * */
        public static void addProduct(Products product){
            ProductsList.add(product);
        }

        /**Method searches Part table for Part based on Part ID
         *
         * @param partId ID used to search for desired Part
         * @return part If found returns Part
         *
         * */
        public static Part lookupPart(int partId){
            Part part = null;

            for (Part partListAtI : PartList){
                if (partListAtI.getId() == partId){
                    part = partListAtI;
                }
            }
            return part;
        }

        /**Method searches Product table for Product based on Product ID
         *
         * @param productId ID used to search for desired Product
         * @return Product If found returns Product
         *
         * */
        public static Products lookupProduct(int productId) {
            Products product = null;

            for (Products productListAtI : ProductsList) {
                if (productListAtI.getId() == productId) {
                    product = productListAtI;
                }
            }
            return product;
        }

        /**Method searches for Part based on a partial string
         *
         * @param name partial or complete name of Part desired
         * @return namedPart Returns a list of Parts with given string parameter
         *
         * */
        public static ObservableList<Part> lookupPart(String name){
            ObservableList<Part> namedPart = FXCollections.observableArrayList();
            for (Part partListAtI : PartList) {
                if (partListAtI.getName().contains(name)) {
                    namedPart.add(partListAtI);
                }
            }
            return namedPart;
        }

        /**Method searches for Products based on a partial string
         *
         * @param name partial or complete name of Product desired
         * @return namedProducts Returns a list of Products with given string parameter
         *
         * */
        public static ObservableList<Products> lookupProduct(String name){
            ObservableList<Products> namedProducts = FXCollections.observableArrayList();

            for (Products productListAtI : ProductsList) {
                if (productListAtI.getName().contains(name)) {
                    namedProducts.add(productListAtI);
                }
            }
            return namedProducts;
        }

        /**Method Updates given Part in the PartList
         *
         * @param selectedPart Part desired to be updated
         *
         * */
        public static void updatePart(int index, Part selectedPart){
            PartList.set(index, selectedPart);
        }

        /**Method Updates give Product in the ProductList
         *
         * @param selectedProduct Product desired to be updated
         *
         * */
        public static void updateProduct(int index, Products selectedProduct){
            ProductsList.set(index, selectedProduct);
        }

        /**Method Removes a Part from the Part List
         *
         * @param selectedPart Part desired to be removed
         *
         * */
        public static void deletePart(Part selectedPart){
            PartList.remove(selectedPart);
        }

        /**Method removes a Product from the Product List
         *
         * @param selectedProduct Product desired to be removed
         *
         * */
        public static void deleteProduct(Products selectedProduct){
            ProductsList.remove(selectedProduct);
        }

        /**Method returns all Parts in the list
         *
         * @return PartList
         *
         * */
        public static ObservableList<Part> getAllParts(){
            return PartList;
        }

        /**Method returns all Products in the list
         *
         * @return ProductsList
         *
         * */
        public static ObservableList<Products> getAllProducts(){
            return ProductsList;
        }

}
