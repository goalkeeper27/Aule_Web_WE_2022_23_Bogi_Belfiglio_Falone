/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package it.univaq.project.aule_web.data.dao;

import it.univaq.aule_web.data.model.Aula;
import it.univaq.aule_web.framework.data.DataException;
import java.util.List;

/**
 *
 * @author Alberto Bogi
 */
public interface AulaDAO {
    
    public Aula createAula();
    
    public Aula getAula(int key) throws DataException;
    
    public List<Aula> getAuleByIds(Aula aula) throws DataException;
    
    void storeAula(Aula aula)throws DataException;
    
    void deleteAula(Aula aula) throws DataException;
    
    
}
