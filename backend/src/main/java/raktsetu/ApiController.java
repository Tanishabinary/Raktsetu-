package raktsetu;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ApiController {

    // 1. REGISTER DONOR ENDPOINT
    @PostMapping("/donors")
    public Map<String, Object> registerDonor(@RequestBody Map<String, Object> donorData) {
        Map<String, Object> response = new HashMap<>();

        try {
            String name = (String) donorData.get("name");
            int age = Integer.parseInt(donorData.get("age").toString());
            String bloodGroup = (String) donorData.get("bloodGroup");
            String phone = (String) donorData.get("phone");
            String city = (String) donorData.get("city");

            // Validate using your existing Java logic
            if (name == null || age < 18 || age > 65 || !raktsetu.isValidGroup(bloodGroup)) {
                response.put("status", "error");
                response.put("message", "Invalid donor details!");
                return response;
            }

            // Create Donor using your existing Donor class
            Donor d = new Donor(raktsetu.nextId, name, age, bloodGroup, phone, city);
            raktsetu.donors.add(d);
            
            // Save to text file using your existing DonorFile class
            DonorFile.saveDonors(raktsetu.donors);

            raktsetu.nextId++;

            response.put("status", "success");
            response.put("message", "Donor registered successfully!");
            response.put("donorId", d.donorId);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error registering donor: " + e.getMessage());
        }

        return response;
    }

    // 2. GET ALL DONORS
    @GetMapping("/donors")
    public List<Donor> getAllDonors() {
        return raktsetu.donors;
    }

    // 3. SUBMIT BLOOD REQUEST ENDPOINT
    @PostMapping("/requests")
    public Map<String, Object> submitRequest(@RequestBody Map<String, Object> requestData) {
        Map<String, Object> response = new HashMap<>();

        try {
            String patientName = (String) requestData.get("patientName");
            String bloodGroup = (String) requestData.get("bloodGroup");
            int units = Integer.parseInt(requestData.get("units").toString());
            String urgency = (String) requestData.get("urgency");

            if (!raktsetu.isValidGroup(bloodGroup) || units <= 0) {
                response.put("status", "error");
                response.put("message", "Invalid request details!");
                return response;
            }

            // Create BloodRequest using your existing BloodRequest class
            BloodRequest r = new BloodRequest(raktsetu.nextRequestId, patientName, bloodGroup, units, urgency);
            raktsetu.requests.add(r);

            // Save to file using your existing save method
            raktsetu.saveRequests();

            raktsetu.nextRequestId++;

            response.put("status", "success");
            response.put("message", "Blood request submitted successfully!");
            response.put("requestId", r.requestId);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error submitting request: " + e.getMessage());
        }

        return response;
    }

    // 4. GET ALL BLOOD REQUESTS
    @GetMapping("/requests")
    public List<BloodRequest> getAllRequests() {
        return raktsetu.requests;
    }
}
