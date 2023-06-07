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
import it.univaq.project.aule_web.data.model.Aula;
import java.sql.Date;
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

    private PreparedStatement sEventoRicorrenteByEvento, sEventiRicorrentiByDataAndAula;

    public EventoRicorrenteDAO_MySQL(DataLayer d) {
        super(d);

    }

    public void init() throws DataException {
        super.init();

        try {
            sEventoRicorrenteByEvento = this.dataLayer.getConnection().prepareStatement("SELECT * FROM Evento_ricorrente WHERE ID_evento = ?");
            sEventiRicorrentiByDataAndAula = this.dataLayer.getConnection().prepareStatement("SELECT * FROM Evento_ricorrente ev, Evento e WHERE (ev.data_evento BETWEEN (? + interval 1 day) AND ?) and ev.ID_evento = e.ID and e.ID_aula = ?");
        } catch (SQLException ex) {
            throw new DataException("Errore nell'inizializzazione del data layer", ex);
        }
    }

    @Override
    public void destroy() throws DataException {
        //anche chiudere i PreparedStamenent � una buona pratica...
        //also closing PreparedStamenents is a good practice...
        try {

            sEventoRicorrenteByEvento.close();
            sEventiRicorrentiByDataAndAula.close();

        } catch (SQLException ex) {
            //
        }
        super.destroy();
    }

    @Override
    public EventoRicorrente createEventoRicorrente() {
        return new EventoRicorrenteProxy(this.dataLayer);
    }

    private EventoRicorrente createEventoRicorrente(ResultSet rs) throws DataException {
        EventoRicorrenteProxy evento = (EventoRicorrenteProxy) this.createEventoRicorrente();
        try {
            evento.setKey(rs.getInt("ID"));
            evento.setDataEvento(rs.getDate("data_evento").toLocalDate());
            evento.setEventoKey(rs.getInt("ID_evento"));
        } catch (SQLException ex) {
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
                try ( ResultSet rs = sEventoRicorrenteByEvento.executeQuery()) {
                    while (rs.next()) {
                        eventi.add(this.createEventoRicorrente(rs));
                    }
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Errore DB", ex);
        }
        return eventi;
    }

    @Override
    public List<EventoRicorrente> EventiRicorrentiByDataAndAula(LocalDate data_inizio, LocalDate data_fine, Aula aula) throws DataException {
        List<EventoRicorrente> eventi = new ArrayList();

        try {
            sEventiRicorrentiByDataAndAula.setDate(1, Date.valueOf(data_inizio));
            sEventiRicorrentiByDataAndAula.setDate(2, Date.valueOf(data_fine));
            sEventiRicorrentiByDataAndAula.setInt(3, aula.getKey());
            try ( ResultSet rs = sEventiRicorrentiByDataAndAula.executeQuery()) {
                while (rs.next()) {
                    eventi.add(this.createEventoRicorrente(rs));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Errore DB", ex);
        }
        return eventi;
    }

}
