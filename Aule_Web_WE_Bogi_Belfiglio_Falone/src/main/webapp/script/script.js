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



function showFineRicorrenza(choise) {
    let radio_button;
    let data_fine_ricorrenza;
    if (choise === 1) {
        //input_evento_8 rappresenta l'ID dell'input per la data fine di fine ricorrenza
        data_fine_ricorrenza = document.getElementById('fine_ricorrenza');
        //ultimoRadio = radioButtons[radioButtons.length - 1];
        radio_button = document.querySelector('input[name="ricorrenza"][type="radio"][value="4"]');
        if (radio_button.checked) {
            data_fine_ricorrenza.style.display = 'none';
        } else {
            let ricorrenze = document.getElementById("input_evento_6");
            ricorrenze.value = null;
            data_fine_ricorrenza.style.display = 'block';
        }
    }else if(choise === 2){
        let div_update = document.getElementById("evento_update");
        data_fine_ricorrenza = div_update.querySelector("#fine_ricorrenza");
        radio_button = div_update.querySelector('input[name="ricorrenza"][type="radio"][value="NESSUNA"]');
        
        if (radio_button.checked) {
            data_fine_ricorrenza.style.display = 'none';
        } else {
            let ricorrenze = div_update.querySelector("#input_evento_6");
            ricorrenze.value = null;
            data_fine_ricorrenza.style.display = 'block';
        }
    }
}

function showCorsi(choise) {
    let radio_button1;
    let radio_button2;
    let radio_button3;
    let div_corsi;
    if (choise === 1) {
        //input_evento_8 rappresenta l'ID dell'input per la data fine di fine ricorrenza
        div_corsi = document.getElementById('corsi_evento');
        //ultimoRadio = radioButtons[radioButtons.length - 1];
        radio_button1 = document.querySelector('input[name="tipologia"][type="radio"][value="1"]');
        radio_button2 = document.querySelector('input[name="tipologia"][type="radio"][value="2"]');
        radio_button3 = document.querySelector('input[name="tipologia"][type="radio"][value="3"]');
        if (radio_button1.checked || radio_button2.checked || radio_button3.checked) {
            let corsi = div_corsi.querySelectorAll('input[type="radio"]');
            corsi.forEach((elemento) => {
                if (elemento.checked) {
                    elemento.checked = false;
                }
            });
            div_corsi.style.display = 'block';
        } else {
            div_corsi.style.display = 'none';
        }
    } else if (choise === 2) {
        let div_update = document.getElementById('evento_update');
        div_corsi = div_update.querySelector('#corsi_evento');
        radio_button1 = div_update.querySelector('input[name="tipologia"][type="radio"][value="LEZIONE"]');
        radio_button2 = div_update.querySelector('input[name="tipologia"][type="radio"][value="ESAME"]');
        radio_button3 = div_update.querySelector('input[name="tipologia"][type="radio"][value="PARZIALE"]');
        if (radio_button1.checked || radio_button2.checked || radio_button3.checked) {
            let corsi = div_corsi.querySelectorAll('input[type="radio"]');
            corsi.forEach((elemento) => {
                if (elemento.checked) {
                    elemento.checked = false;
                }
            });
            div_corsi.style.display = 'block';
        } else {
            div_corsi.style.display = 'none';
        }

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
            } else {
                button.disabled = true;
                return;
            }
        }
    } else if (choise === 2) {
        let div_update = document.getElementById("gruppo_update");
        input1 = div_update.querySelector('#input_gruppo_1').value;
        input3 = div_update.querySelector("#input_gruppo_3").value;
        button = div_update.querySelector("#gruppo_button");
        if (div_update.querySelector('input[id="add_tipo"]').checked) {
            let input2 = div_update.querySelector('input[id="input_gruppo_2"]').value;
            if (input1 && input2 && input3) {
                button.disabled = false;
                return;
            } else {
                button.disabled = true;
                return;
            }
        } else if (div_update.querySelector('input[id="select_tipo"]').checked) {
            let input2 = div_update.querySelectorAll('input[name="tipo"]');
            if (input1 && input3) {
                input2.forEach((elemento) => {
                    if (elemento.checked) {
                        button.disabled = false;
                        return;
                    }
                });
                
            } else {
                button.disabled = true;
                return;
            }
        }
    }
}




function showFineRicorrenzaUpdate() {
    let radioButtons = document.getElementsByName('ricorrenzaUp');
    let data_fine_ricorrenza = document.getElementsByClassName('fine_ricorrenza_update');
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



function searchEvento() {
    let xhr = new XMLHttpRequest();
    let input = document.getElementById("ricerca_evento");
    let str = input.value;
    let url = "administration?operation=1&search=" + str;
    xhr.open("GET", url, true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            let rispostaDiv = document.getElementById("table_eventi");
            rispostaDiv.innerHTML = xhr.responseText;
        }
    };
    xhr.send();
}




function checkEvento() {
    let xhr = new XMLHttpRequest();
    let radio = document.querySelector('input[name="IDevento"]:checked');
    let id = radio.value;
    let url = "administration?operation=1&IDevento=" + id;
    xhr.open("GET", url, true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var rispostaDiv = document.getElementById("evento_update");
            rispostaDiv.innerHTML = xhr.responseText;
        }
    };
    xhr.send();
}


function validateEventsInputs(choise) {
    let input1;
    let input2;
    let input3;
    let input4;
    let input5;
    let input6;
    let inputTipologia;
    let inputCorso;
    let inputRicorrenza;
    let inputAula;
    let inputResponsabile;
    let button;

    if (choise === 1) {
        input1 = document.getElementById("input_evento_1").value;
        input2 = document.getElementById("input_evento_2").value;
        input3 = document.getElementById("input_evento_3").value;
        input4 = document.getElementById("input_evento_4").value;
        input5 = document.getElementById("input_evento_5").value;
        inputAula = document.querySelector('input[name="aula"][type="radio"]:checked');
        inputResponsabile = document.querySelector('input[name="responsabile"][type="radio"]:checked');
        //input6 = document.getElementById("input_evento_6").value;
        inputTipologia = document.querySelector('input[name="tipologia"][type="radio"]:checked');
        inputRicorrenza = document.querySelector('input[name="ricorrenza"][type="radio"]:checked');
        button = document.getElementById("button_events");
        if (input1 && input2 && input3 && input4 && input5 && inputAula && inputResponsabile && inputTipologia && inputRicorrenza) {
            if (inputTipologia.value === "1" || inputTipologia.value === "2" || inputTipologia.value === "3") {
                inputCorso = document.querySelector('input[name="corso"][type="radio"]:checked');
                if (!inputCorso) {
                    button.disabled = true;
                    return;
                }
            }
            if (inputRicorrenza.value !== "4") {
                input6 = document.getElementById("input_evento_6").value;
                if (!input6) {
                    button.disabled = true;
                    return;
                }
            }

            button.disabled = false;
            return;
        } else {
            button.disabled = true;
            return;
        }

    } else if (choise === 2) {
        let div_update = document.getElementById("evento_update");
        input1 = div_update.querySelector("#input_evento_1").value;
        //alert(input1);
        input2 = div_update.querySelector("#input_evento_2").value;
        //alert(input2);
        input3 = div_update.querySelector("#input_evento_3").value;
        //alert(input3);
        input4 = div_update.querySelector("#input_evento_4").value;
        //alert(input4);
        input5 = div_update.querySelector("#input_evento_5").value;
        //alert(input5);
        inputAula = div_update.querySelector('input[name="aula"][type="radio"]:checked');
        //alert(inputAula.value);
        inputResponsabile = div_update.querySelector('input[name="responsabile"][type="radio"]:checked');
        //alert(inputResponsabile.value);
        //input6 = document.getElementById("input_evento_6").value;
        inputTipologia = div_update.querySelector('input[name="tipologia"][type="radio"]:checked');
        //alert(inputTipologia.value);
        inputRicorrenza = div_update.querySelector('input[name="ricorrenza"][type="radio"]:checked');
        //alert(inputRicorrenza.value);
        button = div_update.querySelector("#button_events");
        if (input1 && input2 && input3 && input4 && input5 && inputAula && inputResponsabile && inputTipologia && inputRicorrenza) {
            //alert("not empty");
            if (inputTipologia.value === "LEZIONE" || inputTipologia.value === "ESAME" || inputTipologia.value === "PARZIALE") {
                inputCorso = div_update.querySelector('input[name="corso"][type="radio"]:checked');
                if (!inputCorso) {
                    button.disabled = true;
                    return;
                }
            }
            if (inputRicorrenza.value !== "NESSUNA") {
                input6 = div_update.querySelector("#input_evento_6").value;
                if (!input6) {
                    button.disabled = true;
                    return;
                }
            }

            button.disabled = false;
            return;
        } else {
            button.disabled = true;
            return;
        }
        

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







