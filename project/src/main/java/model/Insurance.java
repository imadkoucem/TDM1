package model;

/**
 * Created by YMC on 09/04/2017.
 */

public class Insurance {

    private String agencyName = "";
    private String policyNumber = "";
    private String agentName = "";
    private String agentNumber = "";

    public Insurance() {
    }

    public Insurance(String agencyName, String policyNumber, String agentName, String agentNumber) {
        this.agencyName = agencyName;
        this.policyNumber = policyNumber;
        this.agentName = agentName;
        this.agentNumber = agentNumber;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentNumber() {
        return agentNumber;
    }

    public void setAgentNumber(String agentNumber) {
        this.agentNumber = agentNumber;
    }
}
