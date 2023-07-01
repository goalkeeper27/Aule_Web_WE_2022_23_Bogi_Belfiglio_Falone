/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package it.univaq.project.aule_web.data.dao;

import it.univaq.project.aule_web.data.model.Amministratore;
import it.univaq.project.aule_web.framework.data.DataException;

public interface AmministratoreDAO {
    
    Amministratore createAmministratore();

    Amministratore getAmministratoreByID(int key)throws DataException;
    
    Amministratore getAmministratoreByUsername(String username) throws DataException;
    
}
