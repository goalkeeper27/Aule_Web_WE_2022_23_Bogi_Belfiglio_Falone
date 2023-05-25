/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package it.univaq.project.aule_web.data.dao;

import it.univaq.aule_web.data.model.Gruppo;
import it.univaq.aule_web.framework.data.DataException;

/**
 *
 * @author Alberto Bogi
 */
public interface GruppoDAO {
    
    public Gruppo createGruppo();
    
    public Gruppo getGruppoByTipoAndNome(String tipo, String nome) throws DataException;
    
    public void storeGruppo(Gruppo gruppo)throws DataException;
    
    public void deleteGruppo(Gruppo gruppo) throws DataException;
    
}
