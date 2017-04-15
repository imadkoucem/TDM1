package model;

/**
 * Created by YMC on 09/04/2017.
 */

public class Casualty {

    private String name = "";
    private String address = "";
    private String phone = "";
    private String Age = "";

    public Casualty() {
    }

    public Casualty(String name, String address, String phone, String age) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        Age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String toString(){
        String s = "";
        s = "CASUALTY:\n" ;
        if(! name.equals("")) s+="Name: "+name+"\n";
        if(! address.equals("")) s+="Address: "+address+"\n";
        if(! phone.equals("")) s+="Phone: "+phone+"\n";
        if(! Age.equals("")) s+="Age: "+Age+"\n";
        return s;
    }
}
