package model;

/**InHouse Class creates a Part with InHouse machineId*/
public class InHouse extends Part{
    private int machineId;

    /**The InHouse method is a super constructor used to create instance of an InHouse Part
     *
     * @param id id for InHouse Part
    * @param name name for InHouse Part
    * @param price price for InHouse Part
    * @param stock inventory for InHouse Part
    * @param min minimum of allowed Parts
     * @param max maximum of allowed Parts
     * @param machineId machineId for InHouse Part
     *
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {

        super(id, name, price, stock, min, max);
        this.machineId = machineId;

    }

    /**setMachineId method is a setter for the machineId
    *
    * @param machineId sets the machine id
     *
    */
    public void setMachineId(int machineId){

        this.machineId = machineId;
    }

     /**getMachineId is the getter for the machineId
     *
     * @return machineId for InHouse part
      *
     */
    public int getMachineId(){

        return machineId;
    }
}
