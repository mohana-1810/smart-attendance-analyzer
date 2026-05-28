function validateLogin() {

    let u = document.getElementById("username").value.trim();
    let p = document.getElementById("password").value.trim();

    if(u === "" || p === "") {
        alert("Please fill all fields");
        return false;
    }

    return true;
}