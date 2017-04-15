package model;

/**
 * Created by YMC on 09/04/2017.
 */

public class Police {

    private String eventNumber = "";
    private String caseNumber = "";
    private String unitNumber = "";
    private String stationNumber = "";

    public Police() {
    }

    public Police(String eventNumber, String caseNumber, String unitNumber, String stationNumber) {
        this.eventNumber = eventNumber;
        this.caseNumber = caseNumber;
        this.unitNumber = unitNumber;
        this.stationNumber = stationNumber;
    }

    public String getEventNumber() {
        return eventNumber;
    }

    public void setEventNumber(String eventNumber) {
        this.eventNumber = eventNumber;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    public String getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(String stationNumber) {
        this.stationNumber = stationNumber;
    }
}
