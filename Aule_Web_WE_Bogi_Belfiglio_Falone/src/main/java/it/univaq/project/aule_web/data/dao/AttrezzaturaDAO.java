/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package it.univaq.project.aule_web.data.dao;

import it.univaq.aule_web.data.model.Attrezzatura;
import it.univaq.aule_web.data.model.Aula;
import java.util.List;

/**
 *
 * @author Alberto Bogi
 */
public interface AttrezzaturaDAO {
    
    Attrezzatura createAttrezzatura();
    
    List<Attrezzatura> getAttrezzatureByAula(Aula aula);
    
    
    
}
