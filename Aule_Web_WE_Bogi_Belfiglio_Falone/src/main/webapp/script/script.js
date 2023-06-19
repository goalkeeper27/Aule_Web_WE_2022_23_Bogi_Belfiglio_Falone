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

    var xhr = new XMLHttpRequest();
    var form = document.getElementById("select_form");

    let id = form.operation.value;
    var url = "administration?operation=" + id;

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
    var input1 = document.getElementById("input1").value;
    var input2 = document.getElementById("input2").value;
    var input3 = document.getElementById("input3").value;
    var input4 = document.getElementById("input4").value;
    var input5 = document.getElementById("input5").value;
    var input6 = document.getElementById("input6").value;
    var input7 = document.getElementById("input7").value;
    var input8 = document.getElementById("input8").value;
    var input9 = document.getElementById("input9").value;
    var input10 = document.getElementById("input10").value;
    var input11 = document.getElementsByClassName("input11");
    var input12 = document.getElementsByClassName("input12");
    var button = document.getElementById("button");

    if (input1 && input2 && input3 && input4 && input5 && input6 && input7 && input8 && input9 && input10) {
        for (var i = 0; i < input11.length; i++) {
            var select = input11[i];
            var selectedOption = select.options[select.selectedIndex];
            if (selectedOption.value === "null") {
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
/**/
/*
 
 */




/*
 * function checked(){
 
 var items=getElementsByname('checkbox');
 
 var selectedlist=[];
 
 for(var i=0; i<items.length; i++)       
 {
 if(items[i].type=='checkbox' && items[i].checked==true)                 
 selectedlist+=items[i].value+"\n";
 }
 
 alert(selectedlist);
 }
 * 
 */







