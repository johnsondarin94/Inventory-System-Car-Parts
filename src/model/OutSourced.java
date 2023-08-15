package model;
    /**The OutSourced class creates a Part with Company Name*/
public class OutSourced extends Part{
    private String companyName;

    /**OutSourced method is a super constructor used to create an instance of an OutSourced Part
    *
    * @param id id for an OutSourced Part
    * @param name name for an OutSourced Part
    * @param price Price of an OutSourced Part
    * @param stock Inventory of an OutSourced Part
    * @param min Minimum allowed amount of a Part
    * @param max Maximum allowed of a Part
    * @param companyName Company Name of OutSourced Part
    *
    */
    public OutSourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
    /**setCompanyName method is a setter for the companyName
    *
    * @param companyName
    *
     * */
    public void setCompanyName(String companyName){

        this.companyName = companyName;
    }

    /** getCompanyName is a getter for the companyName
     *
     * @return returns companyName
     *
     */
    public String getCompanyName(){

        return companyName;
    }

}
