package AuditParser;

import java.util.ArrayList;

public class RequirementSection {
    private String header;
    private String status;
    private int numRequired;
    private ArrayList<Course> registeredCourses;
    private ArrayList<String> courseOptions;

    public RequirementSection(String header,
            String status,
            int numRequired,
            ArrayList<Course> registeredCourses,
            ArrayList<String> courseOptions) {
        this.header = header;
        this.status = status;
        this.numRequired = numRequired;
        this.registeredCourses = registeredCourses;
        this.courseOptions = courseOptions;
    }

    public String getHeader() {
        return header;
    }

    public int getNumRequired() {
        return numRequired;
    }

    public ArrayList<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public ArrayList<String> getCourseOptions() {
        return courseOptions;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "RequirementSection{" +
                "header='" + header + '\'' +
                ", status='" + status + '\'' +
                ", numRequired=" + numRequired +
                ", registeredCourses=" + registeredCourses.toString() +
                ", courseOptions=" + courseOptions.toString() +
                '}';
    }
}
