/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.project.aule_web.data.model;

import it.univaq.project.aule_web.framework.data.DataItem;

/**
 *
 * @author Stefano Belfiglio
 */
public interface Gruppo extends DataItem<Integer> {
    
    String getTipoGruppo();
    
    void setTipoGruppo(String tipoGruppo);
    
    String getNome();
    
    void setNome(String nome);
    
    String getDescrizione();
    
    void setDescrizione(String descrizione);
    
}
