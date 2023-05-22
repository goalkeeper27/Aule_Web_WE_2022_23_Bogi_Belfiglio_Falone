/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.aule_web.data.impl;

import it.univaq.aule_web.data.DataItemImpl;
import it.univaq.aule_web.data.model.Responsabile;

/**
 *
 * @author franc
 */
public class ResponsabileImpl extends DataItemImpl<Integer> implements Responsabile{
    private String nome;
    private String cognome;
    private String codice_fiscale;
    private String email;
    
    public ResponsabileImpl(){
        super();
        nome = "";
        cognome = "";
        codice_fiscale = "";
        email = "";
    }

    @Override
    public String getNome() {
       return this.nome;
    }

    @Override
    public String getCognome() {
        return this.cognome;
    }

    @Override
    public String getCodice_fiscale() {
        return this.codice_fiscale;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    @Override
    public void setCodice_fiscale(String cf) {
       this.codice_fiscale = cf;
    }

    @Override
    public void setEmail(String email) {
       this.email = email;
    }
    
}
