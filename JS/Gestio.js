function logOut(){
    sessionStorage.removeItem("ClauSession")
    sessionStorage.removeItem("ClauMail");
    window.location.href = "/Login.html";
}
function alta(){
    window.location.href = "/Alta.html";
}
function getTable(){
    let mail = sessionStorage.getItem("ClauMail");
    let session = sessionStorage.getItem("ClauSession");
    var divaltas_xips = document.getElementById("altas_xips")

    var http = new XMLHttpRequest();
    http.open("POST", "http://localhost:3000/Pastillas/ServeXips", true);

    var enviar = "mail=" + encodeURIComponent(mail) + "&session=" + encodeURIComponent(session);

    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    http.send(enviar);

    http.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            let respuesta = this.responseText;
            divaltas_xips.innerHTML = respuesta;
        }
    }
}