/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.aule_web.data.impl;

import it.univaq.aule_web.data.DataItemImpl;
import it.univaq.aule_web.data.model.Corso;
import it.univaq.aule_web.data.model.TipoLaurea;

/**
 *
 * @author franc
 */
public class CorsoImpl extends DataItemImpl<Integer> implements Corso{
    String nome;
    TipoLaurea tipo_laurea;
    String corso_di_laurea;
    int anno_di_frequentazione;
    
    public CorsoImpl(){
        super();
        nome = "";
        corso_di_laurea = "";
        anno_di_frequentazione = 3;
        tipo_laurea = null;
    }
     
    
    @Override
    public String getNome(){
         return nome;
     }

    @Override
    public String getCorsoDiLaurea() {
        return corso_di_laurea;
    }

    @Override
    public TipoLaurea getTipo_laurea() {
        return tipo_laurea;
    }

    @Override
    public int getAnno_di_frequentazione() {
        return anno_di_frequentazione;
    }

    @Override
    public void setTipo_laurea(TipoLaurea tipo_laurea) {
        this.tipo_laurea = tipo_laurea;
    }

    @Override
    public void setCorsoDiLaurea(String corso_di_laurea) {
        this.corso_di_laurea = corso_di_laurea;
        
    }

    @Override
    public void setNome(String nome) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setAnno_di_frequentazione(int anno_di_frequentazione) {
        this.anno_di_frequentazione = anno_di_frequentazione;
    }

   
}
