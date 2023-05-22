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
    TipoLaurea getTipo_laurea();
    int getAnno_di_frequentazione();
    
    void setTipo_laurea(TipoLaurea tipo_laurea);
    void setCorsoDiLaurea(String corso_di_laurea);
    void setNome(String nome);
    void setAnno_di_frequentazione(int anno_di_frequentazione);
    
}
