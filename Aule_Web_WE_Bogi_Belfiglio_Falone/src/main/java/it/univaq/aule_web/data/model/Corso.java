/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.aule_web.data.model;

import it.univaq.aule_web.data.model.enumerable.TipoLaurea;
import it.univaq.aule_web.framework.data.DataItem;

/**
 *
 * @author Francesco Falone
 */
public interface Corso extends DataItem<Integer>{
    
    String getNome();
    
    String getCorsoDiLaurea();
    
    TipoLaurea getTipoLaurea();
    
    int getAnnoDiFrequentazione();
    
    void setTipoLaurea(TipoLaurea tipoLaurea);
    
    void setCorsoDiLaurea(String corsoDiLaurea);
    
    void setNome(String nome);
    
    void setAnnoDiFrequentazione(int annoDiFrequentazione);
    
}
