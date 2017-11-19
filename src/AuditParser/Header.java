package AuditParser;


import java.util.ArrayList;

public class Header {
    private String name;
    private String status;
    private ArrayList<RequirementSection> requirements;

    public Header(String name, String status, ArrayList<RequirementSection> requirements) {
        this.name = name;
        this.status = status;
        this.requirements = requirements;
    }

    public String getName() {
        return name;
    }
    public String getStatus() {
        return status;
    }

    public ArrayList<RequirementSection> getRequirements() {
        return requirements;
    }
}
