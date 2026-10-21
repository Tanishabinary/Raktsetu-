package raktsetu;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

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

            if (name == null || age < 18 || age > 65 || !raktsetu.isValidGroup(bloodGroup)) {
                response.put("status", "error");
                response.put("message", "Invalid donor details!");
                return response;
            }

            Donor d = new Donor(raktsetu.nextId, name, age, bloodGroup, phone, city);
            raktsetu.donors.add(d);
            
            DonorFile.saveDonors(raktsetu.donors);

            raktsetu.nextId++;

            response.put("status", "success");
            response.put("message", "Donor registered successfully!");
            response.put("donorId", d.getDonorId());
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

            BloodRequest r = new BloodRequest(raktsetu.nextRequestId, patientName, bloodGroup, units, urgency);
            raktsetu.requests.add(r);

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

    // 5. GET REQUEST STATUS BY ID
    @GetMapping("/requests/status/{id}")
    public Map<String, Object> getRequestStatus(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        
        for (BloodRequest req : raktsetu.requests) {
            if (req.requestId == id) {
                response.put("status", "success");
                response.put("requestStatus", req.status);
                response.put("patientName", req.patientName);
                response.put("bloodGroup", req.bloodGroup);
                return response;
            }
        }
        
        response.put("status", "error");
        response.put("message", "Request ID not found!");
        return response;
    }
}

@Controller
class WebController {
    @GetMapping("/")
    public String index() {
        return "index.html";
    }
}