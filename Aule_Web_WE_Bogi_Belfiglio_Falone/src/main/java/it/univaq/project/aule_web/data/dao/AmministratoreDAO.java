/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package it.univaq.project.aule_web.data.dao;

import it.univaq.aule_web.data.model.Amministratore;
import it.univaq.aule_web.framework.data.DataException;

/**
 *
 * @author Alberto Bogi
 */
public interface AmministratoreDAO {
    
    public Amministratore getAmministratoreByID(int key)throws DataException;
    
    public Amministratore getAmministratoreByUsernameAndPassword(String username, String password) throws DataException;
    
    public Amministratore createAmministratore();
    
}
