/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package it.univaq.project.aule_web.data.dao;

import it.univaq.project.aule_web.data.model.Evento;
import it.univaq.project.aule_web.data.model.EventoRicorrente;
import it.univaq.project.aule_web.framework.data.DataException;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Alberto Bogi
 */
public interface EventoRicorrenteDAO {
    
    EventoRicorrente createEventoRicorrente();
    
    List<EventoRicorrente> EventoRicorrenteByEvento(Evento evento)throws DataException;
    
    List<EventoRicorrente> EventiRicorrentiByData(LocalDate data_inizio, LocalDate data_fine)throws DataException;
    
    
    
    
}
