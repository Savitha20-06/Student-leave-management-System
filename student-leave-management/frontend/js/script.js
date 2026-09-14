// Base URL of the Spring Boot REST API
const API_BASE = "http://localhost:8080/api/leaves";

const leaveForm = document.getElementById("leaveForm");
const formMessage = document.getElementById("formMessage");
const searchBtn = document.getElementById("searchBtn");
const myLeavesBody = document.getElementById("myLeavesBody");

// ---------- Apply for Leave ----------
leaveForm.addEventListener("submit", async function (e) {
  e.preventDefault();

  const leaveRequest = {
    studentName: document.getElementById("studentName").value,
    rollNumber: document.getElementById("rollNumber").value,
    department: document.getElementById("department").value,
    fromDate: document.getElementById("fromDate").value,
    toDate: document.getElementById("toDate").value,
    reason: document.getElementById("reason").value
  };

  try {
    const response = await fetch(API_BASE, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(leaveRequest)
    });

    if (response.ok) {
      formMessage.style.color = "green";
      formMessage.textContent = "Leave request submitted successfully!";
      leaveForm.reset();
    } else {
      formMessage.style.color = "red";
      formMessage.textContent = "Something went wrong. Please try again.";
    }
  } catch (err) {
    formMessage.style.color = "red";
    formMessage.textContent = "Could not connect to server. Is the backend running?";
    console.error(err);
  }
});

// ---------- Check My Leave Status ----------
searchBtn.addEventListener("click", async function () {
  const rollNumber = document.getElementById("searchRoll").value.trim();
  if (!rollNumber) return;

  try {
    const response = await fetch(`${API_BASE}/student/${rollNumber}`);
    const leaves = await response.json();

    myLeavesBody.innerHTML = "";

    if (leaves.length === 0) {
      myLeavesBody.innerHTML = `<tr><td colspan="4">No leave requests found.</td></tr>`;
      return;
    }

    leaves.forEach(leave => {
      const row = document.createElement("tr");
      row.innerHTML = `
        <td>${leave.fromDate}</td>
        <td>${leave.toDate}</td>
        <td>${leave.reason}</td>
        <td class="status-${leave.status}">${leave.status}</td>
      `;
      myLeavesBody.appendChild(row);
    });
  } catch (err) {
    console.error(err);
    myLeavesBody.innerHTML = `<tr><td colspan="4">Could not connect to server.</td></tr>`;
  }
});
