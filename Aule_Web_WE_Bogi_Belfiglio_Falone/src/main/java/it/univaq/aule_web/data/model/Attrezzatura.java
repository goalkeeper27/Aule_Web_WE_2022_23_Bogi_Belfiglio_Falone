/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.aule_web.data.model;

import it.univaq.aule_web.data.DataItem;

/**
 *
 * @author stefa
 */
public interface Attrezzatura extends DataItem<Integer>{
    
    Aula getAula();
    
    void setAula(Aula aula);
    
    String getNome();
    
    void setNome(String nome);
    
    int getNumeroDiSerie();
    
    void setNumeroDiSerie(int numeroDiSerie);
       
}
