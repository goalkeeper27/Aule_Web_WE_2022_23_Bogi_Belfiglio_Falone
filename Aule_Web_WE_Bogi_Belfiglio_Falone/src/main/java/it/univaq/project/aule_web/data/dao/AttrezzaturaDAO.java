/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package it.univaq.project.aule_web.data.dao;

import it.univaq.project.aule_web.data.model.Attrezzatura;
import it.univaq.project.aule_web.data.model.Aula;
import it.univaq.project.aule_web.framework.data.DataException;
import java.util.List;

public interface AttrezzaturaDAO {
    
    Attrezzatura createAttrezzatura();
    
    Attrezzatura getAttrezzatura(int key) throws DataException;
    
    Attrezzatura getAttrezzaturaDisponibileByNome(String numeroSerie) throws DataException;
    
    List<Attrezzatura> getAttrezzatureByAula(Aula aula) throws DataException;
    
    List<Attrezzatura> getAttrezzaturaDisponibile()throws DataException;
    
    void storeAttrezzatura(Attrezzatura attrezzatura) throws DataException;
    
    void deleteAttrezzaturaByID(Attrezzatura attrezzatura) throws DataException;
    
    
    
}
