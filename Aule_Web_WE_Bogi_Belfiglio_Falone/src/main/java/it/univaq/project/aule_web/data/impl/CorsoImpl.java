/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.project.aule_web.data.impl;

import it.univaq.project.aule_web.framework.data.DataItemImpl;
import it.univaq.project.aule_web.data.model.Corso;
import it.univaq.project.aule_web.data.model.enumerable.TipoLaurea;

public class CorsoImpl extends DataItemImpl<Integer> implements Corso{
    private String nome;
    private TipoLaurea tipoLaurea;
    private String corsoDiLaurea;
    private int annoDiFrequentazione;
    
    public CorsoImpl(){
        super();
        nome = "";
        corsoDiLaurea = "";
        annoDiFrequentazione = 0;
        tipoLaurea = null;
    }
     
    
    @Override
    public String getNome(){
         return this.nome;
     }

    @Override
    public String getCorsoDiLaurea() {
        return this.corsoDiLaurea;
    }

    @Override
    public TipoLaurea getTipoLaurea() {
        return this.tipoLaurea;
    }

    @Override
    public int getAnnoDiFrequentazione() {
        return this.annoDiFrequentazione;
    }

    @Override
    public void setTipoLaurea(TipoLaurea tipoLaurea) {
        this.tipoLaurea = tipoLaurea;
    }

    @Override
    public void setCorsoDiLaurea(String corsoDiLaurea) {
        this.corsoDiLaurea = corsoDiLaurea;
        
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public void setAnnoDiFrequentazione(int annoDiFrequentazione) {
        this.annoDiFrequentazione = annoDiFrequentazione;
    }

   
}
