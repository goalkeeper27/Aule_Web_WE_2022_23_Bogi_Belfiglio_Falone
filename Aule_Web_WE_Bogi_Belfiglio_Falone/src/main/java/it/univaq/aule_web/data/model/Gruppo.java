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
public interface Gruppo extends DataItem<Integer> {
    
    TipoGruppo getTipoGruppo();
    
    void setTipoGruppo(TipoGruppo tipoGruppo);
    
    String getNome();
    
    void setNome(String nome);
    
    String getDescrizione();
    
    void setDescrizione(String descrizione);
    
}
