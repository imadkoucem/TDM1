package model;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;



public class Data {

    public static String dirFile;
    public static String APPLICATION_PACKAGE_NAME ;
    public static String fileName = "";

    public static String dateTime = "";
    public static String location = "";
    public static String desciption = "";

    public static ThirdParty thirdParty = null;

    public static List<Vehicule> listVehicule = new ArrayList<>();

    public static Insurance insurance = null ;

    public static Police police = null ;

    public static List<Casualty> listCasualties = new ArrayList<>();

    public static List<Witness> listWitnesses = new ArrayList<>();

    public static Bitmap capture = null;

    public static List<Bitmap> image = new ArrayList<>();

    public static boolean isFileSaved = false;

    public static Folder folder = new Folder();
}
