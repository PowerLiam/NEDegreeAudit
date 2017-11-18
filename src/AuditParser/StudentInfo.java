package AuditParser;

/**
 * Created by Chris on 11/18/2017.
 */
public class StudentInfo {

    public String getNUID() {
        return NUID;
    }

    public String getFirst_name() {
        return first_name;
    }

    @Override
    public String toString() {
        return "StudentInfo{" +
                "NUID='" + NUID + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", grad_date='" + grad_date + '\'' +
                ", program_code='" + program_code + '\'' +
                ", cat_year='" + cat_year + '\'' +
                ", degree='" + degree + '\'' +
                ", focus='" + focus + '\'' +
                ", focus_type='" + focus_type + '\'' +
                '}';
    }

    public String getLast_name() {
        return last_name;
    }

    public String getGrad_date() {
        return grad_date;
    }

    public String getProgram_code() {
        return program_code;
    }

    public String getCat_year() {
        return cat_year;
    }

    public String getDegree() {
        return degree;
    }

    public String getFocus() {
        return focus;
    }

    public String getFocus_type() {
        return focus_type;
    }

    private String NUID;
    private String first_name;
    private String last_name;
    private String grad_date;
    private String program_code;
    private String cat_year;
    private String degree;
    private String focus;
    private String focus_type;


    public StudentInfo(
            String NUID,
            String first_name,
            String last_name,
            String grad_date,
            String program_code,
            String cat_year,
            String degree,
            String focus,
            String focus_type){
        this.NUID =  NUID;
        this.first_name = first_name;
        this.last_name = last_name;
        this.grad_date = grad_date;
        this.program_code = program_code;
        this.cat_year = cat_year;
        this.degree = degree;
        this.focus = focus;
        this.focus_type = focus_type;
    }



}
