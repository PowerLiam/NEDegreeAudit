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


    public int[] getPair(){
        int [] ret = new int[2];
        for(RequirementSection r : requirements){
            ret[0] += r.getRegisteredCourses().size();
            ret[1] += r.getNumRequired();
        }
        return ret;
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
