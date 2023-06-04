/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */




let input = document.getElementById('date');
let button = document.getElementById('calendar');
button.disabled = true;   //il bottone viene disattivato inizialmente
input.addEventListener('input', function(event){
   
   let val = event.target.value;  //prendiamo il valore dell'input corrente, nel nostro caso input[type = date]
   
   if(val===''){
       button.disabled = true;  //bottone disabilitato
   }
   else{
       button.disabled = false;  //bottone abilitato
   }
   
});

function check_button(){
    
let input = document.getElementById('week');
let button = document.getElementById('button_week');
button.disabled = true;   //il bottone viene disattivato inizialmente
input.addEventListener('input', function(event){
   
   let val = event.target.value;  //prendiamo il valore dell'input corrente, nel nostro caso input[type = date]
   
   if(val===''){
       button.disabled = true;  //bottone disabilitato
   }
   else{
       button.disabled = false;  //bottone abilitato
   }
   
});
}


