package kvnb.hostelservicemanagement;

public class Complaint {
    private String complaintType;
    private String complaintDescription;
    private String atimeStart;
    private String atimeEnd;
    private String rusolved;
    private String id;
    public Complaint() {

    }

    public Complaint(String complaintType, String complaintDescription, String atimeStart, String atimeEnd,String rusolved) {
        this.complaintType=complaintType;
        this.complaintDescription=complaintDescription;
        this.atimeStart=atimeStart;
        this.atimeEnd=atimeEnd;
        this.rusolved=rusolved;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getComplaintType() {
        return complaintType;
    }

    public String getAtimeEnd() {
        return atimeEnd;
    }

    public String getAtimeStart() {
        return atimeStart;
    }

    public String getComplaintDescription() {
        return complaintDescription;
    }

    public String getRusolved() {
        return rusolved;
    }

}
