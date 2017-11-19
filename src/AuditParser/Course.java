package AuditParser;

import java.awt.*;

public class Course {
    private String semester;
    private String department; // "      FL17 CS  1800     4.00 RG  IP Discrete Structures (Hon)    "
    private String courseno;
    private String  credits;
    private String registration;
    private String progress;
    private String name;

    public Course(String semester,
                  String department,
                  String courseno,
                  String credits,
                  String registration,
                  String progress,
                  String name) {
        this.semester = semester;
        this.department = department;
        this.courseno = courseno;
        this.credits = credits;
        this.registration = registration;
        this.progress = progress;
        this.name = name;
    }

    public String getSemester() {
        return semester;
    }

    public String getDepartment() {
        return department;
    }

    public String getCourseno() {
        return courseno;
    }

    public String getCredits() {
        return credits;
    }

    public String getRegistration() {
        return registration;
    }

    public String getProgress() {
        return progress;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "\nCourse{" +
                "semester='" + semester + '\'' +
                ", department='" + department + '\'' +
                ", courseno='" + courseno + '\'' +
                ", credits='" + credits + '\'' +
                ", registration='" + registration + '\'' +
                ", progress='" + progress + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

}
