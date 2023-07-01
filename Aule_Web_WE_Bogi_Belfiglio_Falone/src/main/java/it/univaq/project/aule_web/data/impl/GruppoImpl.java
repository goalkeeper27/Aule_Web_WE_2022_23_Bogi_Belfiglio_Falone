/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.project.aule_web.data.impl;

import it.univaq.project.aule_web.framework.data.DataItemImpl;
import it.univaq.project.aule_web.data.model.Gruppo;

public class GruppoImpl extends DataItemImpl<Integer> implements Gruppo{
    
    private String tipoGruppo;
    private String nome;
    private String descrizione;
    
    public GruppoImpl() {
        tipoGruppo = null;
        nome = "";
        descrizione = "";
    }

    @Override
    public String getTipoGruppo() {
        return this.tipoGruppo;
    }

    @Override
    public void setTipoGruppo(String tipoGruppo) {
        this.tipoGruppo = tipoGruppo;
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
