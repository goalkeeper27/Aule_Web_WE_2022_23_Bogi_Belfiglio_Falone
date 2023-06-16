/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */




let input_orario_attuale = document.getElementById('date');
let button_orario_attuale = document.getElementById('calendar');


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

function modify_state_button(id_button, ...ids_date) {
    let button = document.getElementById(id_button);
    let input_date = [];
    for (let i = 0; i < ids_date.length; i++) {
        input_date[i] = document.getElementById(ids_date[i]);
    }
    button.disabled = true;
    
    for (let i = 0; i < ids_date.length; i++) {
        input_settimana[i].addEventListener('input', function() {

            for (let i = 0; i < ids_date.length; i++) {
                if(input_settimana[i].value !== "")
                    return;

            }
            button.disabled = false;

        });

    }



}



