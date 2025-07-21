function showForm(type) {
  const panel = document.getElementById('formPanel');
  const content = document.getElementById('formContent');

  if (type === 'signup') {
    content.innerHTML = `
      <button class="close-btn" onclick="closeForm()">✕</button>
      <h2>Signup</h2>
      <input id="signupName" type="text" placeholder="Full Name" required />
      <input id="signupEmail" type="email" placeholder="Email" required />
      <input id="signupPassword" type="password" placeholder="Password" required />
      <button onclick="registerUser()">Register</button>
    `;
  } else if (type === 'signin') {
    content.innerHTML = `
      <button class="close-btn" onclick="closeForm()">✕</button>
      <h2>Signin</h2>
      <input id="signinEmail" type="email" placeholder="Email" required />
      <input id="signinPassword" type="password" placeholder="Password" required />
      <button onclick="loginUser()">Login</button>
    `;
  }

  panel.classList.add('active');
}

function closeForm() {
  const panel = document.getElementById('formPanel');
  panel.classList.remove('active');
}
function registerUser() {
  
  const name = document.getElementById('signupName').value;
  const email = document.getElementById('signupEmail').value;
  const password = document.getElementById('signupPassword').value;

  if (!name || !email || !password) {
    alert("Please fill all fields");
    return;
  }

  registerHandler(name, email, password);
}


function registerHandler(name, email, password) {
  fetch("http://localhost:8000/register", {
    method: "POST",
    body: JSON.stringify({ name, email, password }),
    headers: {
      "Content-Type": "application/json"
    }
  })
    .then(res => {
      if (res.status === 409) {
        alert("Email already registered!");
        return null;
      } else if (!res.ok) {
        throw new Error("Server error. Please try again.");
      }
      return res.text();
    })
    .then(data => {
      if (data) {
        alert("Registration successful!");
        closeForm(); // Close the signup popup
      }
    })
    .catch(err => {
      console.error("Error:", err);
      alert("Could not connect to server. Is it running?");
    });
}


// Example: Login user function
function loginUser() {
  const email = document.getElementById('signinEmail').value;
  const password = document.getElementById('signinPassword').value;

  if (!email || !password) {
    alert("Please enter email and password");
    return;
  }

  fetch("http://localhost:8000/login", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({ email, password })
  })
    .then(res => {
      if (res.status === 401) {
        alert("Invalid credentials");
      } else {
        return res.text();
      }
    })
    .then(data => {
      if (data) {
        alert(data);
        closeForm();
      }
    })
    .catch(err => {
      console.error("Login error:", err);
      alert("Login failed");
    });
}


// Call your backend URL shortener
function shortenUrl(longUrl) {
  fetch("http://localhost:8000/shorten", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({ url: longUrl })
  })
  .then(response => response.json())
  .then(data => {
    console.log("Shortened URL:", data.shortUrl);
    alert("Shortened URL: " + data.shortUrl);
  })
  .catch(error => {
    console.error("Error:", error);
    alert("Failed to shorten URL.");
  });
}

// Optional: Hook a button to this function
// Example usage:
// shortenUrl("https://example.com");
