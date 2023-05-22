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
public interface Corso extends DataItem<Integer>{
    String getNome();
    String getCorsoDiLaurea();
    String getTipo_laurea();
    int getAnno_di_frequentazione();
    
    void setTipo_laurea(String tipo_laurea);
    void setCorsoDiLaurea();
    void setNome(String nome);
    void setAnno_di_frequentazione();
    
}
