// Base URL of the Spring Boot REST API
const API_BASE = "http://localhost:8080/api/leaves";

const leavesBody = document.getElementById("leavesBody");
const refreshBtn = document.getElementById("refreshBtn");

// ---------- Load All Leaves ----------
async function loadLeaves() {
  leavesBody.innerHTML = `<tr><td colspan="9">Loading...</td></tr>`;

  try {
    const response = await fetch(API_BASE);
    const leaves = await response.json();

    if (leaves.length === 0) {
      leavesBody.innerHTML = `<tr><td colspan="9">No leave requests found.</td></tr>`;
      return;
    }

    leavesBody.innerHTML = "";

    leaves.forEach(leave => {
      const row = document.createElement("tr");
      row.innerHTML = `
        <td>${leave.id}</td>
        <td>${leave.studentName}</td>
        <td>${leave.rollNumber}</td>
        <td>${leave.department || "-"}</td>
        <td>${leave.fromDate}</td>
        <td>${leave.toDate}</td>
        <td>${leave.reason}</td>
        <td class="status-${leave.status}">${leave.status}</td>
        <td>
          <button class="action-btn approve-btn" onclick="updateStatus(${leave.id}, 'APPROVED')">Approve</button>
          <button class="action-btn reject-btn" onclick="updateStatus(${leave.id}, 'REJECTED')">Reject</button>
          <button class="action-btn delete-btn" onclick="deleteLeave(${leave.id})">Delete</button>
        </td>
      `;
      leavesBody.appendChild(row);
    });
  } catch (err) {
    console.error(err);
    leavesBody.innerHTML = `<tr><td colspan="9">Could not connect to server. Is the backend running?</td></tr>`;
  }
}

// ---------- Approve / Reject ----------
async function updateStatus(id, status) {
  try {
    await fetch(`${API_BASE}/${id}/status`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ status })
    });
    loadLeaves();
  } catch (err) {
    console.error(err);
    alert("Could not update status.");
  }
}

// ---------- Delete ----------
async function deleteLeave(id) {
  if (!confirm("Delete this leave request?")) return;

  try {
    await fetch(`${API_BASE}/${id}`, { method: "DELETE" });
    loadLeaves();
  } catch (err) {
    console.error(err);
    alert("Could not delete leave request.");
  }
}

refreshBtn.addEventListener("click", loadLeaves);

// Load leaves as soon as the page opens
loadLeaves();
