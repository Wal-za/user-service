const API = "http://localhost:8080";

// ================= REGISTER (RequestParam backend)
async function register() {

    const name = document.getElementById("rname").value;
    const email = document.getElementById("remail").value;

    const url = `${API}/users/register?name=${encodeURIComponent(name)}&email=${encodeURIComponent(email)}`;

    const res = await fetch(url, { method: "POST" });

    alert(await res.text());
}

// ================= LOGIN
async function login() {

    const email = document.getElementById("lemail").value;
    const password = document.getElementById("lpassword").value;

    const res = await fetch(`${API}/auth/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password })
    });

    if (!res.ok) {
        alert(await res.text());
        return;
    }

    const data = await res.json();
    localStorage.setItem("token", data.token);

    alert("Login exitoso");
}

// ================= FORGOT PASSWORD
async function forgot() {

    const email = document.getElementById("femail").value;

    const res = await fetch(`${API}/users/forgot-password`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email })
    });

    alert(await res.text());
}

// ================= RESET PASSWORD
async function resetPassword() {

    const token = document.getElementById("token").value;
    const newPassword = document.getElementById("newpass").value;

    const res = await fetch(`${API}/users/reset-password`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ token, newPassword })
    });

    alert(await res.text());
}