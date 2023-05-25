/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package it.univaq.project.aule_web.data.dao;

import it.univaq.aule_web.data.model.Responsabile;
import it.univaq.aule_web.framework.data.DataException;

/**
 *
 * @author Alberto Bogi
 */
public interface ResponsabileDAO {
    
    Responsabile getResponsabile(int key) throws DataException;
    
    Responsabile createResponsabile();
    
    
    
}
