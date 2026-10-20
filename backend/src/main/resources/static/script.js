const ageInput = document.getElementById("age");
const donatedBefore = document.getElementById("donatedBefore");
const ageMessage = document.getElementById("ageMessage");

function checkAgeEligibility() {
    const age = Number(ageInput.value);
    const history = donatedBefore.value;

    if (ageInput.value === "" || history === "") {
        ageMessage.textContent = "";
        return;
    }

    if (age < 18) {
        ageMessage.textContent =
            "You must be at least 18 years old to donate blood.";
    } else if (age > 65) {
        ageMessage.textContent =
            "You are outside the general age criteria. Please consult a blood bank.";
    } else if (history === "no" && age > 60) {
        ageMessage.textContent =
            "First-time donors should generally be no older than 60.";
    } else {
        ageMessage.textContent =
            "You meet the general age criteria. Other eligibility checks are required.";
    }
}

ageInput.addEventListener("input", checkAgeEligibility);
donatedBefore.addEventListener("change", checkAgeEligibility);


// Use a relative path so it automatically works locally and on Render
fetch('/api/requests')
    .then(response => response.json())
    .then(data => console.log("Backend says:", data))
    .catch(error => console.error("Connection error:", error));