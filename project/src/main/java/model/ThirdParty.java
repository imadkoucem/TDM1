package model;

/**
 * Created by YMC on 09/04/2017.
 */

public class ThirdParty {

    private String name = "";
    private String adress = "";
    private String phone = "";
    private String licenceNumber = "";

    public ThirdParty() {
    }

    public ThirdParty(String name, String adress, String phone, String licenceNumber) {
        this.name = name;
        this.adress = adress;
        this.phone = phone;
        this.licenceNumber = licenceNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLicenceNumber() {
        return licenceNumber;
    }

    public void setLicenceNumber(String licenceNumber) {
        this.licenceNumber = licenceNumber;
    }
}
