/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.aule_web.data.model;

import it.univaq.aule_web.data.DataItem;

/**
 *
 * @author franc
 */
public interface Responsabile extends DataItem<Integer> {
    String getNome();
    String getCognome();
    String getCodice_fiscale();
    String getEmail();
    
    
    
    void setNome(String nome);
    void setCognome(String cognome);
    void setCodice_fiscale(String cf);
    void setEmail(String email);
}
