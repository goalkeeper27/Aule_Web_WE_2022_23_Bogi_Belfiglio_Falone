/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.project.aule_web.data.dao.impl;

import it.univaq.project.aule_web.data.model.Evento;
import it.univaq.project.aule_web.data.model.EventoRicorrente;
import it.univaq.project.aule_web.framework.data.DAO;
import it.univaq.project.aule_web.framework.data.DataException;
import it.univaq.project.aule_web.framework.data.DataLayer;
import it.univaq.project.aule_web.data.dao.EventoRicorrenteDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alberto Bogi
 */
public class EventoRicorrenteDAO_MySQL extends DAO implements EventoRicorrenteDAO {

    private PreparedStatement sEventoRicorrenteByEvento;

    public EventoRicorrenteDAO_MySQL(DataLayer d) {
        super(d);

    }

    public void init() throws DataException {
        super.init();

        try {
            sEventoRicorrenteByEvento = this.dataLayer.getConnection().prepareStatement("SELECT * FROM Evento_ricorrente WHERE ID_evento = ?");
        } catch (SQLException ex) {
            throw new DataException("Errore nell'inizializzazione del data layer", ex);
        }
    }
    
    @Override
    public EventoRicorrente createEventoRicorrente(){
        return new EventoRicorrenteProxy(this.dataLayer);
    }
    
    private EventoRicorrente createEventoRicorrente(ResultSet rs)throws DataException{
        EventoRicorrenteProxy evento = (EventoRicorrenteProxy)this.createEventoRicorrente();
        try{
            evento.setKey(rs.getInt("ID"));
            evento.setDataEvento(rs.getDate("data_evento").toLocalDate());
            evento.setEventoKey(rs.getInt("ID_evento"));
        }
        catch(SQLException ex){
            throw new DataException("erroe DB", ex);
        }
        
        return evento;
    }

    @Override
    public List<EventoRicorrente> EventoRicorrenteByEvento(Evento evento) throws DataException {
        List<EventoRicorrente> eventi = new ArrayList();

        try {
            if (evento.getKey() != null && evento.getKey() > 0) {
                sEventoRicorrenteByEvento.setInt(1, evento.getKey());
                try(ResultSet rs = sEventoRicorrenteByEvento.executeQuery()) {
                    while(rs.next()){
                        eventi.add(this.createEventoRicorrente(rs));
                    }
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Errore DB", ex);
        }
        return eventi;
    }

}
