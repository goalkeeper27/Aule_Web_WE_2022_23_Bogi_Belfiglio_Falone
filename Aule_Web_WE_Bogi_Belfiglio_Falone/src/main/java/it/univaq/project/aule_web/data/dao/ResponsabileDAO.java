/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package it.univaq.project.aule_web.data.dao;

import it.univaq.project.aule_web.data.model.Responsabile;
import it.univaq.project.aule_web.framework.data.DataException;
import java.util.List;

/**
 *
 * @author Alberto Bogi
 */
public interface ResponsabileDAO {
    
    Responsabile getResponsabile(int key) throws DataException;
    
    Responsabile getResponsabileByEmail(String email)throws DataException;
    
    List<Responsabile> getAllResponsabili()throws DataException;
    
    Responsabile createResponsabile();
    
    void storeResponsabile(Responsabile responsabile)throws DataException;
    
    void deleteResponsabileByEmail(String email)throws DataException;
    
    
    
    
    
}
