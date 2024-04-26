package week02.JSON;

public class AddressDTO {
    public String street;
    public String city;
    public int zipCode;

    @Override
    public String toString() {
        return "Address: " + street + " - " + city + ":" + zipCode;
    }
}
