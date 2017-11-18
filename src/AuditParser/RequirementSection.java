package AuditParser;

import java.util.ArrayList;

public class RequirementSection {
    private String header;
    private int numRequired;
    private ArrayList<Course> registeredCourses;
    private ArrayList<String> courseOptions;

    public RequirementSection(String header,
            int numRequired,
            ArrayList<Course> registeredCourses,
            ArrayList<String> courseOptions) {
        this.header = header;
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
}
