/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.aule_web.data.impl;

import it.univaq.aule_web.data.DataItemImpl;
import it.univaq.aule_web.data.model.TipoGruppo;

/**
 *
 * @author stefa
 */
public class TipoGruppoImpl extends DataItemImpl<Integer> implements TipoGruppo{
    
    private String nome;
    private String descrizione;
    
    public TipoGruppoImpl() {
        nome = "";
        descrizione = "";
    }

    @Override
    public String getNome() {
        return this.nome;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String getDescrizione() {
        return this.descrizione;
    }

    @Override
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    
}
