package model;

/**
 * Created by YMC on 09/04/2017.
 */

public class Vehicule {

    private String type = "";
    private String manufacturer = "";
    private String model = "";
    private String ccolor = "";
    private String year = "";
    private String licencePlate = "";

    public Vehicule() {
    }

    public Vehicule(String type, String manufacturer, String model, String ccolor, String year, String licencePlate) {
        this.type = type;
        this.manufacturer = manufacturer;
        this.model = model;
        this.ccolor = ccolor;
        this.year = year;
        this.licencePlate = licencePlate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCcolor() {
        return ccolor;
    }

    public void setCcolor(String ccolor) {
        this.ccolor = ccolor;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }
}
