/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package it.univaq.project.aule_web.data.dao;

import it.univaq.project.aule_web.data.model.Aula;
import it.univaq.project.aule_web.data.model.Gruppo;
import it.univaq.project.aule_web.framework.data.DataException;
import java.util.List;

/**
 *
 * @author Alberto Bogi
 */
public interface GruppoDAO {
    
    public Gruppo createGruppo();
     
    public Gruppo getGruppo(int key) throws DataException;
    
    public Gruppo getGruppoByTipoAndNome(String tipo, String nome) throws DataException;
    
    public List<Gruppo> getGruppiByPartialName(String search) throws DataException;
    
    public List<Gruppo> getAllGruppi() throws DataException;
    
    public List<String> getTipiGruppo() throws DataException;
    
    public List<Gruppo> getGruppiByAula(Aula aula) throws DataException;
    
    public void storeGruppo(Gruppo gruppo)throws DataException;
    
    public void deleteGruppo(Gruppo gruppo) throws DataException;
    
}
