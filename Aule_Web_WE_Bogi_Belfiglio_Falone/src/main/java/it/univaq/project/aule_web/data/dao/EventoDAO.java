/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package it.univaq.project.aule_web.data.dao;

import it.univaq.project.aule_web.data.model.Aula;
import it.univaq.project.aule_web.data.model.Corso;
import it.univaq.project.aule_web.data.model.Evento;
import it.univaq.project.aule_web.data.model.Gruppo;
import it.univaq.project.aule_web.framework.data.DataException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Alberto Bogi
 */
public interface EventoDAO {
    
    public Evento getEvento(int key) throws DataException;
    
    public Evento createEvento();
    
    // Mostrare gli eventi associati a una specifica aula in una determinata settimana o in giornata
    public List<Evento> getEventoInAWeekByAula(Aula aula, LocalDate dataInizio, LocalDate dataFine) throws DataException;
    
    public List<Evento> getEventoInADayByAula(Aula aula, LocalDate data) throws DataException;
    
    // Mostrare tutti gli eventi attuali e quelli delle prossime tre ore
    public List<Evento> getCurrentEventoByAula(Aula aula)throws DataException;
    
    // Mostrare gli eventi associati a uno specifico corso in una determinata settimana
    public List<Evento> getEventoInAWeekByCorso(Corso corso, LocalDate dataInizio, LocalDate dataFine) throws DataException;
    
    
    public void storeEvento(Evento evento)throws DataException;
    
    public void deleteEvento(Evento evento) throws DataException;
    
}
