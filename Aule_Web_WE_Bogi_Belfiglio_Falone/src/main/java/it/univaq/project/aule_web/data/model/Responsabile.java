/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.project.aule_web.data.model;

import it.univaq.project.aule_web.framework.data.DataItem;

public interface Responsabile extends DataItem<Integer> {
    
    String getNome();
    
    String getCognome();
    
    String getCodiceFiscale();
    
    String getEmail();
    
   
    void setNome(String nome);
    
    void setCognome(String cognome);
    
    void setCodiceFiscale(String cf);
    
    void setEmail(String email);
}
