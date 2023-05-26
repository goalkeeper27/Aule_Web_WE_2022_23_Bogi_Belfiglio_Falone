/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.project.aule_web.data.dao.impl;

import it.univaq.aule_web.data.model.Aula;
import it.univaq.aule_web.data.model.Corso;
import it.univaq.aule_web.data.model.Evento;
import it.univaq.aule_web.framework.data.DAO;
import it.univaq.aule_web.framework.data.DataException;
import it.univaq.aule_web.framework.data.DataLayer;
import it.univaq.project.aule_web.data.dao.EventoDAO;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Alberto Bogi
 */
public class EventoDAO_MySQL extends DAO implements EventoDAO{
    
    private PreparedStatement sEventoByID, sEventoByAulaAndPeriodo, sCurrentEvento, sEventoByPeriodoAndCorso;
    private PreparedStatement iEvento, uEvento, dEvento;
    
    public EventoDAO_MySQL(DataLayer d){
        super(d);
        
    }
    
    public void init()throws DataException{
        /*
        try{
            sEventoByID = this.dataLayer.getConnection().prepareStatement("SELECT * FROM Evento WHERE ID = ?"); 
        }
*/
    }

    @Override
    public Evento getEvento(int key) throws DataException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Evento createEvento() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Evento> getEventoByAulaAndPeriodo(Aula aula, LocalDate dataInizio, Integer period) throws DataException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Evento> getCurrentEvento() throws DataException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Evento> getEventByPeriodoAndCorso(Corso corso, LocalDate data) throws DataException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Evento storeEvento(Evento evento) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteEvento(Evento evento) throws DataException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
