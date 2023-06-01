/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.project.aule_web.data.impl;

import it.univaq.project.aule_web.framework.data.DataItemImpl;
import it.univaq.project.aule_web.data.model.Responsabile;

/**
 *
 * @author franc
 */
public class ResponsabileImpl extends DataItemImpl<Integer> implements Responsabile{
    
    private String nome;
    private String cognome;
    private String codiceFiscale;
    private String email;
    
    public ResponsabileImpl(){
        super();
        nome = "";
        cognome = "";
        codiceFiscale = "";
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
    public String getCodiceFiscale() {
        return this.codiceFiscale;
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
    public void setCodiceFiscale(String cf) {
       this.codiceFiscale = cf;
    }

    @Override
    public void setEmail(String email) {
       this.email = email;
    }
    
}
