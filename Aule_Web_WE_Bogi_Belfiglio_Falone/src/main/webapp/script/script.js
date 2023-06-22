/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */




let input_orario_attuale = document.getElementById('date');
let button_orario_attuale = document.getElementById('calendar');

if (button_orario_attuale.disabled !== null)
    button_orario_attuale.disabled = true;   //il bottone viene disattivato inizialmente

input_orario_attuale.addEventListener('input', function (event) {

    let val = event.target.value;  //prendiamo il valore dell'input corrente, nel nostro caso input[type = date]

    if (val === '') {
        button_orario_attuale.disabled = true;  //bottone disabilitato
    } else {
        button_orario_attuale.disabled = false;  //bottone abilitato
    }

});



let input_settimana = document.getElementById('week');
let button_settimana = document.getElementById('button_week');


button_settimana.disabled = true;   //il bottone viene disattivato inizialmente

input_settimana.addEventListener('input', function (event) {

    let val = event.target.value;  //prendiamo il valore dell'input corrente, nel nostro caso input[type = date]

    if (val === '') {
        button_settimana.disabled = true;  //bottone disabilitato
    } else {
        button_settimana.disabled = false;  //bottone abilitato
    }

});





function sendInfo() {

    let xhr = new XMLHttpRequest();
    let form = document.getElementById("select_form");

    let id = form.operation.value;
    let url = "administration?operation=" + id;

    xhr.open("GET", url, true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var rispostaDiv = document.getElementById("contenuto");
            rispostaDiv.innerHTML = xhr.responseText;
        }
    };
    xhr.send();
}


function validateInputs() {
    
    let input1 = document.getElementById("input1").value;
    let input2 = document.getElementById("input2").value;
    let input3 = document.getElementById("input3").value;
    let input4 = document.getElementById("input4").value;
    let input5 = document.getElementById("input5").value;
    let input6 = document.getElementById("input6").value;
    let input7 = document.getElementById("input7").value;
    let input8 = document.getElementById("input8").value;
    let input9 = document.getElementById("input9").value;
    let input10 = document.getElementById("input10").value;
    let input11 = document.getElementsByClassName("input11");
    let input12 = document.getElementsByClassName("input12");
    let button = document.getElementById("button");

    if (input1 && input2 && input3 && input4 && input5 && input6 && input7 && input8 && input9 && input10) {
        for (var i = 0; i < input11.length; i++) {
            var select = input11[i];
            var selectedOption = select.options[select.selectedIndex];
            if (selectedOption.value === "0") {
                button.disabled = true;
                return;
            }
        }
        for (var i = 0; i < input12.length; i++) {
            var checkbox = input12[i];
            if (checkbox.checked) {
                button.disabled = false;
                return;
            }
        }
        button.disabled = true;

    } else {
        button.disabled = true;
    }

}


function showFineRicorrenza(){
    let radioButtons = document.getElementsByName('ricorrenza');
    let data_fine_ricorrenza = document.getElementsByClassName('fine_ricorrenza');
    
    let ultimoRadio = radioButtons[radioButtons.length - 1];
    
    if (ultimoRadio.checked) {
        for(let i = 0; i < data_fine_ricorrenza.length; i++){
            data_fine_ricorrenza[i].style.display = 'none';
        }
    } else {
        for(let i = 0; i < data_fine_ricorrenza.length; i++){
            data_fine_ricorrenza[i].style.display = 'block';
        }
    }
}


function validateEventsInputs() {
    var input1 = document.getElementById("input1Ev").value;
    var input2 = document.getElementById("input2Ev").value;
    var input4 = document.getElementById("input4Ev").value;
    var input5 = document.getElementById("input5Ev").value;
    var input6 = document.getElementById("input6Ev").value;
    var inputTipologia = document.getElementsByTagName("input[name='tipologia']");
    var inputRicorrenza = document.getElementsByTagName("input[name='ricorrenza']");
    var inputCorsi = document.getElementsByTagName("input[name='corsi']");
    var inputAule = document.getElementsByTagName("input[name='aule']");
    var inputResponsabili = document.getElementsByTagName("input[name='responsabile']");
    var button = document.getElementById("buttonEvents");
    
    let selezionatiTipologia = false;
    let selezionatiRicorrenza = false;
    let selezionatiCorso = false;
    let selezionatiAula = false;
    let selezionatiResponsabile = false;

    for (let i = 0; i < inputTipologia.length; i++) {
        if (inputTipologia[i].checked) {
          selezionatiTipologia = true;
          break;
        }
      }
      
    for (let i = 0; i < inputRicorrenza.length; i++) {
        if (inputRicorrenza[i].checked) {
          selezionatiRicorrenza = true;
          break;
        }
    }
    
    for (let i = 0; i < inputCorsi.length; i++) {
        if (inputCorsi[i].checked) {
          selezionatiCorso = true;
          break;
        }
    }
    
    for (let i = 0; i < inputAule.length; i++) {
        if (inputAule[i].checked) {
          selezionatiAula = true;
          break;
        }
    }
    
    for (let i = 0; i < inputResponsabili.length; i++) {
        if (inputResponsabili[i].checked) {
          selezionatiResponsabile = true;
          break;
        }
    }
    
    if (selezionatiTipologia && selezionatiRicorrenza && selezionatiCorso && selezionatiAula &&
            selezionatiResponsabile && input1.checked && input2.checked && input4.checked && input5.checked && input6.checked) {
        button.disabled = false;
    } else {
        button.disabled = true;
    }
}


function searchAula() {
    let xhr = new XMLHttpRequest();
    let input = document.getElementById("ricerca_aula");
    let str = input.value;

    let url = "administration?operation=2&search=" + str;

    xhr.open("GET", url, true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            let rispostaDiv = document.getElementById("table_aule");
            rispostaDiv.innerHTML = xhr.responseText;
        }
    };
    xhr.send();

}


function checkAula() {
    let xhr = new XMLHttpRequest();
    let radio = document.querySelector('input[name="IDaula"]:checked');

    let id = radio.value;
    //alert(id);

    let url = "administration?operation=2&IDaula=" + id;

    xhr.open("GET", url, true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function () { 
        if (xhr.readyState === 4 && xhr.status === 200) {   
            let rispostaDiv = document.getElementById("aula_update");
            rispostaDiv.innerHTML = xhr.responseText;
        }
    };
    xhr.send();
}



function exportCSVAula() {

    let xhr = new XMLHttpRequest();
    let radio = document.querySelector('input[name="IDaula"]:checked');

    let id = radio.value;


    let url = "administration?operation=2&csv=1&IDaula=" + id;
    alert(url);
    xhr.open("GET", url, true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var rispostaDiv = document.getElementById("contenuto");
            rispostaDiv.innerHTML = xhr.responseText;
        }
    };
    xhr.send();
}







