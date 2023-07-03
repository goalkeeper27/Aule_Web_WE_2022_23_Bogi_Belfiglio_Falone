



function checkDailyEventDateInput() {
    let form = document.getElementById("daily_event");
    let input = form.querySelector('input[type="date"]').value;
    let button = form.querySelector('button');

    if (input) {
        button.disabled = false;
        return;
    } else {
        button.disabled = true;
        return;
    }
}

function checkInputWeek() {
    let input = document.getElementById("input_week").value;
    let button = document.getElementById("button_week");

    if (input) {
        button.disabled = false;
        return;
    } else {
        button.disabled = true;
        return;
    }
}



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
    } else if (choise === 2) {
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


function validateAulaInputs(choise) {

    let input1;
    let input2;
    let input3;
    let input4;
    let input5;
    let input6;
    let input7;
    let input8;
    let input9;
    let input10;
    let input11;
    let inputResponsabile;
    let inputAttrezzatura;
    let inputGruppi;
    let button;

    if (choise === 1) {
        input1 = document.getElementById("input_aula_1").value;
        input2 = document.getElementById("input_aula_2").value;
        input3 = document.getElementById("input_aula_3").value;
        input4 = document.getElementById("input_aula_4").value;
        input5 = document.getElementById("input_aula_5").value;
        input6 = document.getElementById("input_aula_6").value;
        input7 = document.getElementById("input_aula_7").value;
        input8 = document.getElementById("input_aula_8").value;
        input9 = document.getElementById("input_aula_9").value;
        inputResponsabile = document.querySelector('input[type="radio"][name="responsabile"]:checked');
        inputAttrezzatura = document.querySelectorAll('input[type="checkbox"][class="attrezzatura"]:checked').length;
        inputGruppi = document.querySelectorAll('select');
        button = document.getElementById("aula_button");
        if (input1 && input2 && input3 && input4 && input5 && input6 && input7 && input8 && input9 &&
                inputResponsabile && inputAttrezzatura > 0) {
            for (let i = 0; i < inputGruppi.length; i++) {
                if (inputGruppi[i].value === "0") {
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
        let div_update = document.getElementById("aula_update");
        input1 = div_update.querySelector("#input_aula_1").value;
        input2 = div_update.querySelector("#input_aula_2").value;
        input3 = div_update.querySelector("#input_aula_3").value;
        input4 = div_update.querySelector("#input_aula_4").value;
        input5 = div_update.querySelector("#input_aula_5").value;
        input6 = div_update.querySelector("#input_aula_6").value;
        input7 = div_update.querySelector("#input_aula_7").value;
        input8 = div_update.querySelector("#input_aula_8").value;
        input9 = div_update.querySelector("#input_aula_9").value;
        inputResponsabile = div_update.querySelector('input[type="radio"][name="responsabile"]:checked').value;
        inputAttrezzatura = div_update.querySelectorAll('input[type="checkbox"][class="attrezzatura"]:checked');
        button = div_update.querySelector("#aula_button");
        if (input1 && input2 && input3 && input4 && input5 && input6 && input7 && input8 && input9 && inputResponsabile && inputAttrezzatura.length > 0) {
            button.disabled = false;
            return;
        } else {
            button.disabled = true;
            return;
        }
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
        input2 = div_update.querySelector("#input_evento_2").value;
        input3 = div_update.querySelector("#input_evento_3").value;
        input4 = div_update.querySelector("#input_evento_4").value;
        input5 = div_update.querySelector("#input_evento_5").value;
        inputAula = div_update.querySelector('input[name="aula"][type="radio"]:checked');
        inputResponsabile = div_update.querySelector('input[name="responsabile"][type="radio"]:checked');
        inputTipologia = div_update.querySelector('input[name="tipologia"][type="radio"]:checked');
        inputRicorrenza = div_update.querySelector('input[name="ricorrenza"][type="radio"]:checked');
        button = div_update.querySelector("#button_events");
        if (input1 && input2 && input3 && input4 && input5 && inputAula && inputResponsabile && inputTipologia && inputRicorrenza) {
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

function inputCorso() {
    let xhr = new XMLHttpRequest();
    let form = document.getElementById("select_form");

    form.querySelector('input[type="radio"][name="operation"]:checked').checked = false;
    let url = "administration?input_corso=1";
    xhr.open("GET", url, true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            let rispostaDiv = document.getElementById("contenuto");
            rispostaDiv.innerHTML = xhr.responseText;
        }
    };
    xhr.send();
}

function inputResponsabile() {
    let xhr = new XMLHttpRequest();
    let form = document.getElementById("select_form");

    form.querySelector('input[type="radio"][name="operation"]:checked').checked = false;
    let url = "administration?input_responsabile=1";
    xhr.open("GET", url, true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            let rispostaDiv = document.getElementById("contenuto");
            rispostaDiv.innerHTML = xhr.responseText;
        }
    };
    xhr.send();
}


function validateCorsoInput() {
    let input1 = document.getElementById("input_corso_1").value;
    let input2 = document.getElementById("input_corso_2").value;
    let input3 = document.getElementById("input_corso_3").value;
    let input4 = document.getElementById("input_corso_4").value;
    let button = document.getElementById("corso_button");
    if (input1 && input2 && input3 && input4) {
        button.disabled = false;
    } else {
        button.disabled = true;
    }
    return;
}

function validateResponsabileInput() {
    let input1 = document.getElementById("input_responsabile_1").value;
    let input2 = document.getElementById("input_responsabile_2").value;
    let input3 = document.getElementById("input_responsabile_3").value;
    let input4 = document.getElementById("input_responsabile_4").value;
    let button = document.getElementById("responsabili_button");
    if (input1 && input2 && input3 && input4) {
        button.disabled = false;
    } else {
        button.disabled = true;
    }
    return;
}

function modifyAnnoFrequentazione() {
    let select = document.getElementById("input_corso_3").value;
    let anno = document.getElementById("input_corso_4");
    if (select === "1") {
        anno.min = 1;
        anno.max = 3;
    } else if (select === "2") {
        anno.min = 1;
        anno.max = 2;
    } else if (select === "3") {
        anno.min = 1;
        anno.max = 6;
    }
    anno.value = null;
}


function createCorso() {
    let nome = document.getElementById("input_corso_1").value;
    let corso_laurea = document.getElementById("input_corso_2").value;
    let tipo_laurea = document.getElementById("input_corso_3").value;
    let anno_frequentazione = document.getElementById("input_corso_4").value;
    let xhr = new XMLHttpRequest();

    let url = "administration?insert_corso=1&nome=" + nome + "&corso_laurea=" + corso_laurea +
            "&tipo_laurea=" + tipo_laurea + "&anno_frequentazione=" + anno_frequentazione;
    xhr.open("GET", url, true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            let rispostaDiv = document.getElementById("contenuto");
            rispostaDiv.innerHTML = xhr.responseText;
        }
    };
    xhr.send();

}

function createResponsabile() {
    let nome = document.getElementById("input_responsabile_1").value;
    let cognome = document.getElementById("input_responsabile_2").value;
    let codice_fiscale = document.getElementById("input_responsabile_3").value;
    let email = document.getElementById("input_responsabile_4").value;
    let xhr = new XMLHttpRequest();

    let url = "administration?insert_responsabile=1&nome=" + nome + "&cognome=" + cognome +
            "&codice_fiscale=" + codice_fiscale + "&email=" + email;
    xhr.open("GET", url, true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            let rispostaDiv = document.getElementById("contenuto");
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

function checkCSVButton() {
    let date1 = document.getElementById("input_csv_date_1").value;
    let date2 = document.getElementById("input_csv_date_2").value;
    let button = document.getElementById("button_csv_eventi");

    if (date1 && date2) {
        button.disabled = false;
        return;
    } else {
        button.disabled = true;
        return;
    }
}

function verifyCorrectnessTimeEvento(choise) {
    let input_data;
    let input_time_1;
    let input_time_2;
    let div_message;
    if (choise === 1) {
        input_data = document.getElementById("input_evento_3");
        input_time_1 = document.getElementById("input_evento_4");
        input_time_2 = document.getElementById("input_evento_5");
        div_message = document.getElementById("request_time_input");
    }else if(choise === 2){
        let div_update = document.getElementById("evento_update");
        input_data = div_update.querySelector("#input_evento_3");
        input_time_1 = div_update.querySelector("#input_evento_4");
        input_time_2 = div_update.querySelector("#input_evento_5"); 
        div_message = div_update.querySelector("#request_time_input");
    }
    if (input_data.value) {
        let verify_data = new Date(input_data.value);
        let current_data = new Date();
        verify_data.setHours(0, 0, 0, 0);
        current_data.setHours(0, 0, 0, 0);
        if (verify_data < current_data) {
            div_message.style.display = 'block';
            input_data.value = "";
            return;
        } else if (verify_data.getTime() === current_data.getTime()) {
            if (input_time_1.value) {
                let timeComponents = input_time_1.value.split(":");
                let hours = parseInt(timeComponents[0], 10);
                let minutes = parseInt(timeComponents[1], 10);

                let time_start = new Date();
                time_start.setHours(hours);
                time_start.setMinutes(minutes);
                let current_time = new Date();

                if (time_start < current_time) {
                    div_message.style.display = 'block';
                    input_time_1.value = "";
                    return;

                }
            }
        } else {
            if (input_time_1.value && input_time_2.value) {
                let timeComponents = input_time_1.value.split(":");
                let hours = parseInt(timeComponents[0], 10);
                let minutes = parseInt(timeComponents[1], 10);

                let time_start = new Date();
                time_start.setHours(hours);
                time_start.setMinutes(minutes);

                timeComponents = input_time_2.value.split(":");
                hours = parseInt(timeComponents[0], 10);
                minutes = parseInt(timeComponents[1], 10);
                let time_end = new Date();
                time_end.setHours(hours);
                time_end.setMinutes(minutes);
                if (time_end <= time_start) {
                    div_message.style.display = 'block';
                    input_time_2.value = "";
                    return;

                }
            }

        }

    }

    if (input_time_1.value) {
        let timeComponents = input_time_1.value.split(":");
        let minutes = parseInt(timeComponents[1], 10);
        if (minutes % 15 !== 0) {
            div_message.style.display = 'block';
            input_time_1.value = "";
            return;
        }
    }

    if (input_time_2.value) {
        let timeComponents = input_time_2.value.split(":");
        let minutes = parseInt(timeComponents[1], 10);
        if (minutes % 15 !== 0) {
            div_message.style.display = 'block';
            input_time_2.value = "";
            return;
        }
    }


    div_message.style.display = 'none';
    return;

}







