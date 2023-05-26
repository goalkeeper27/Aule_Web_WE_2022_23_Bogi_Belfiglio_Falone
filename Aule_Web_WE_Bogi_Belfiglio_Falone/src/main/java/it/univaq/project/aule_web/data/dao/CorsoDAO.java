/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package it.univaq.project.aule_web.data.dao;

import it.univaq.aule_web.data.model.Corso;
import it.univaq.aule_web.framework.data.DataException;

/**
 *
 * @author Alberto Bogi
 */
public interface CorsoDAO {
    
    public Corso createCorso();
    
    public Corso getCorso(int key)throws DataException;
    
    public Corso getCorsoByName(String nome) throws DataException;
    
    public Corso getCorsoByPartialName(String nome) throws DataException;
    
    public void storeCorso(Corso corso)throws DataException;
    
    public void deleteCorso(Corso corso)throws DataException;
    
    
}
