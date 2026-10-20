package raktsetu;


public class BloodRequest {

    int requestId;
    String patientName;
    String bloodGroup;
    int units;
    String urgency;
    String status;

    BloodRequest(int requestId, String patientName,
                 String bloodGroup, int units,
                 String urgency) {

        this.requestId = requestId;
        this.patientName = patientName;
        this.bloodGroup = bloodGroup;
        this.units = units;
        this.urgency = urgency;
        this.status = "Pending";
    }

    void displayRequest() {

        System.out.println("Request ID: " + requestId);
        System.out.println("Patient Name: " + patientName);
        System.out.println("Blood Group: " + bloodGroup);
        System.out.println("Units Required: " + units);
        System.out.println("Urgency: " + urgency);
        System.out.println("Status: " + status);
        System.out.println("----------------------");
    }
}



