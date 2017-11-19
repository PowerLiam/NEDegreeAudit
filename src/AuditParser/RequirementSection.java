package AuditParser;

import java.util.ArrayList;

public class RequirementSection {
    private String title; // if blank, don't render
    private String status; // "IP-" "IP+" "-" or "+"
    private int numRequired;
    private ArrayList<Course> registeredCourses; // courses you have already taken or are registered for
    private ArrayList<String> courseOptions; // potential course numbers you could take

    public RequirementSection(String header,
            String status,
            int numRequired,
            ArrayList<Course> registeredCourses,
            ArrayList<String> courseOptions) {
        this.title = header;
        this.status = status;
        this.numRequired = numRequired;
        this.registeredCourses = registeredCourses;
        this.courseOptions = courseOptions;
    }

    public String getTitle() {
        return title;
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
                "header='" + title + '\'' +
                ", status='" + status + '\'' +
                ", numRequired=" + numRequired +
                ", registeredCourses=" + registeredCourses.toString() +
                ", courseOptions=" + courseOptions.toString() +
                '}';
    }
}
