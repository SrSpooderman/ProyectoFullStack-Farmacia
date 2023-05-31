function gotoGestion(){
    window.location.href = "/Gestio.html";
}

function getPatients(){
    let mail = sessionStorage.getItem("ClauMail");
    let session = sessionStorage.getItem("ClauSession");
    var select_Patients = document.getElementById("select-pacientes");

    var http = new XMLHttpRequest();
    http.open("POST", "http://localhost:3000/Pastillas/ServePatients", true);

    var enviar = "mail=" + encodeURIComponent(mail) + "&session=" + encodeURIComponent(session);

    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    http.send(enviar);

    http.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            let respuesta = JSON.parse(this.responseText);
            
            for (let obj of respuesta){
                let option = document.createElement("option");
                option.value = obj.mail;
                option.textContent = obj.name;
                select_Patients.appendChild(option);
            }
        }
    }
}

function getMedicines(){
    let mail = sessionStorage.getItem("ClauMail");
    let session = sessionStorage.getItem("ClauSession");
    var select_Med = document.getElementById("select-medicamentos");

    var http = new XMLHttpRequest();
    http.open("POST", "http://localhost:3000/Pastillas/ServeMedicines", true);

    var enviar = "mail=" + encodeURIComponent(mail) + "&session=" + encodeURIComponent(session);

    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    http.send(enviar);

    http.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            let respuesta = JSON.parse(this.responseText);
            
            for (let obj of respuesta){
                let option = document.createElement("option");
                option.value = obj.id;
                option.textContent = obj.name;
                select_Med.appendChild(option);
            }
        }
    }
}

function enviar(){
    let mail = sessionStorage.getItem("ClauMail");
    let session = sessionStorage.getItem("ClauSession");
    var idXip = document.getElementById("id-xip").value;
    var mailP = document.getElementById("select-pacientes").value;
    var idMed = document.getElementById("select-medicamentos").value;
    var date = document.getElementById("fecha-limite").value;

    var http = new XMLHttpRequest();
    http.open("POST", "http://localhost:3000/Pastillas/Release", true);

    var enviar = "mail=" + mail + "&session=" + session + "&idXip=" + idXip + "&mailP=" + mailP + "&idMed=" + idMed + "&date=" + date;

    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    http.send(enviar);

    http.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            let respuesta = this.responseText;
            alert(respuesta);
        }
    }
}