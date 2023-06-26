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


function showFineRicorrenza() {
    let radioButtons = document.getElementsByName('ricorrenza');
    let data_fine_ricorrenza = document.getElementsByClassName('fine_ricorrenza');

    let ultimoRadio = radioButtons[radioButtons.length - 1];

    if (ultimoRadio.checked) {
        for (let i = 0; i < data_fine_ricorrenza.length; i++) {
            data_fine_ricorrenza[i].style.display = 'none';
        }
    } else {
        for (let i = 0; i < data_fine_ricorrenza.length; i++) {
            data_fine_ricorrenza[i].style.display = 'block';
        }
    }
}



function modify_state_button(id_button, ...ids_date) {
    let button = document.getElementById(id_button);
    let input_date = [];
    for (let i = 0; i < ids_date.length; i++) {
        input_date[i] = document.getElementById(ids_date[i]);
    }
    button.disabled = true;

    for (let i = 0; i < ids_date.length; i++) {
        input_settimana[i].addEventListener('input', function () {

            for (let i = 0; i < ids_date.length; i++) {
                if (input_settimana[i].value !== "")
                    return;

            }
            button.disabled = false;

        });

    }



}




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


function validateGruppoInputs(choise) {

    let input1;
    let input2;
    let input3;
    let button;

    if (choise === 1) {
        input1 = document.getElementById("input_gruppo_1").value;
        input3 = document.getElementById("input_gruppo_3").value;
        button = document.getElementById("gruppo_button");
        if (document.getElementById("add_tipo").checked) {
            let input2 = document.getElementById("input_gruppo_2").value;
            if (input1 && input2 && input3) {
                button.disabled = false;
                return;
            } else {
                button.disabled = true;
                return;
            }
        } else if (document.getElementById("select_tipo").checked) {
            let input2 = document.getElementsByName("tipo");
            if (input1 && input3) {
                for (let i = 0; i < input2.length; i++) {
                    if (input2[i].checked) {
                        button.disabled = false;
                        return;
                    }
                }
                button.disabled = true;
                return;
            }
            else{
                button.disabled = true;
                return;
            }
        }
    } else if (choise === 2) {
        let div_update = document.getElementById("gruppo_update");
        input1 = div_update.querySelector('input[id="input_gruppo_1"]').value;
        input3 = div_update.querySelector("#input_gruppo_3").value;
        button = div_update.querySelector("#gruppo_button");
        if (div_update.querySelector('input[id="add_tipo"]').checked) {
            let input2 = div_update.querySelector('input[id="input_gruppo_2"]').value;
            if (input1 && input2 && input3) {
                button.disabled = false;
                return;
            } else {
                button.disabled = true;
            }
        } else if (div_update.querySelector('input[id="select_tipo"]').checked) {
            let input2 = div_update.querySelectorAll('input[name="tipo"]');
            if (input1 && input3) {
                input2.forEach((elemento) => {
                    if(elemento.checked){
                        button.disabled = false;
                        return;
                    }
                    else{
                        button.disabled = true;
                        return;
                    }
                });
            }
            else{
                button.disabled = true;
                return;
            }

        }
    }
}




function searchGruppo() {
    let xhr = new XMLHttpRequest();
    let input = document.getElementById("ricerca_gruppo");
    let str = input.value;

    let url = "administration?operation=3&search=" + str;


    xhr.open("GET", url, true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var rispostaDiv = document.getElementById("table_gruppo");
            rispostaDiv.innerHTML = xhr.responseText;
        }
    };
    xhr.send();


}

function checkGruppo() {
    let xhr = new XMLHttpRequest();
    let radio = document.querySelector('input[name="IDgruppo"]:checked');

    let id = radio.value;


    let url = "administration?operation=3&IDgruppo=" + id;
    xhr.open("GET", url, true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            alert("ciao");
            var rispostaDiv = document.getElementById("gruppo_update");
            rispostaDiv.innerHTML = xhr.responseText;
        }
    };
    xhr.send();
}

function selectTipoGruppo(choise) {
    let xhr = new XMLHttpRequest();
    let radio;
    let rispostaDiv;
    let button;
    if (choise === 1) {
        radio = document.querySelector('input[name="select_tipo"]:checked');
        rispostaDiv = document.getElementById("tipo_gruppo");
        button = document.getElementById("gruppo_button");
    } else if (choise === 2) {
        let div_update = document.getElementById("gruppo_update");
        radio = document.querySelector('input[name="select_tipo2"]:checked');
        rispostaDiv = document.getElementById("tipo_gruppo2");
        button = div_update.querySelector("#gruppo_button");
    }
    //Ogni volta che modifico il tipo di selezione per il tipo del gruppo, disattivo il bottone di inserimento
    button.disabled = true;
    let url = "administration?operation=3&select_tipo_gruppo=" + radio.value;

    xhr.open("GET", url, true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            rispostaDiv.innerHTML = xhr.responseText;
        }
    };
    xhr.send();
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
            var rispostaDiv = document.getElementById("table_aule");
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
    alert(url);
    xhr.open("GET", url, true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            alert("ciao");
            var rispostaDiv = document.getElementById("aula_update");
            rispostaDiv.innerHTML = xhr.responseText;
        }
    };
    xhr.send();
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







