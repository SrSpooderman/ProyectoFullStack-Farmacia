function send(){
    /*Son las variables que extraigo del documento */
    let email = document.getElementById("email").value;
    let password = document.getElementById("password").value;
    let session = sessionStorage.getItem("ClauSession");
    
    /*Se transforma la contraseña en un hash */
    var shaObj = new jsSHA("SHA-1", "TEXT");
    shaObj.update(password);
    var password_hash = shaObj.getHash("HEX");
    
    
    /*Se hace el envio al backend*/
    var http = new XMLHttpRequest();
    http.open("POST", "http://localhost:3000/Pastillas/Login", true);
    
    var enviar = "email=" + encodeURIComponent(email) + "&password=" + encodeURIComponent(password_hash) + "&session=" + session;

    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    http.send(enviar);

    http.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            let respuesta = this.responseText;
            console.log(respuesta);
            if (respuesta == null || respuesta == "") {
                console.log("Constraseña erronea");
            }else {
                sessionStorage.setItem("ClauSession", respuesta);
                sessionStorage.setItem("ClauMail", email);
                window.location.href = "/Gestio.html";
            }
        }
    }
}