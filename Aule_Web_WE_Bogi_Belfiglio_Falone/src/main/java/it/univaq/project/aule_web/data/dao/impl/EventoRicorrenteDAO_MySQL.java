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
import it.univaq.project.aule_web.data.model.Corso;
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

    private PreparedStatement sEventoRicorrenteByEvento, sEventiRicorrentiByPeriodAndAula, sEventiRicorrentiByPeriod, sEventiRicorrentiByDataAndAula, sEventiRicorrentiByPeriodAndCorso, sAllEventiIDByRicorrenze;

    public EventoRicorrenteDAO_MySQL(DataLayer d) {
        super(d);

    }

    public void init() throws DataException {
        super.init();

        try {
            sEventoRicorrenteByEvento = this.dataLayer.getConnection().prepareStatement("SELECT * FROM Evento_ricorrente WHERE ID_evento = ? AND data_evento >= CURDATE()");
            sEventiRicorrentiByPeriodAndAula = this.dataLayer.getConnection().prepareStatement("SELECT * FROM Evento_ricorrente ev, Evento e WHERE (ev.data_evento BETWEEN (? - interval 1 day) AND ?) and ev.ID_evento = e.ID and e.ID_aula = ?");
            sEventiRicorrentiByPeriod = this.dataLayer.getConnection().prepareStatement("SELECT * FROM Evento_ricorrente WHERE data_evento BETWEEN (? - interval 1 day) AND ?");
            sEventiRicorrentiByDataAndAula = this.dataLayer.getConnection().prepareStatement("SELECT ev.* FROM Evento_ricorrente ev, Evento e WHERE ev.data_evento = ? and ev.ID_evento = e.ID and e.ID_aula = ?");
            sEventiRicorrentiByPeriodAndCorso = this.dataLayer.getConnection().prepareStatement("SELECT ev.* FROM Evento_ricorrente ev, Evento e WHERE (ev.data_evento BETWEEN (? - interval 1 day) AND ?) and ev.ID_evento = e.ID and e.ID_corso = ?");
            sAllEventiIDByRicorrenze = this.dataLayer.getConnection().prepareStatement("SELECT ID_evento FROM Evento_ricorrente WHERE data_evento >= CURDATE() GROUP BY ID_evento");
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
            sEventiRicorrentiByPeriodAndAula.close();
            sEventiRicorrentiByPeriod.close();
            sEventiRicorrentiByDataAndAula.close();
            sEventiRicorrentiByPeriodAndCorso.close();

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
    public List<EventoRicorrente> getEventoRicorrenteByEvento(Evento evento) throws DataException {
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
    public List<EventoRicorrente> getEventiRicorrentiByPeriodAndAula(LocalDate data_inizio, LocalDate data_fine, Aula aula) throws DataException {
        List<EventoRicorrente> eventi = new ArrayList();

        try {
            sEventiRicorrentiByPeriodAndAula.setDate(1, Date.valueOf(data_inizio));
            sEventiRicorrentiByPeriodAndAula.setDate(2, Date.valueOf(data_fine));
            sEventiRicorrentiByPeriodAndAula.setInt(3, aula.getKey());
            try ( ResultSet rs = sEventiRicorrentiByPeriodAndAula.executeQuery()) {
                while (rs.next()) {
                    eventi.add(this.createEventoRicorrente(rs));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Errore DB", ex);
        }
        return eventi;
    }

    @Override
    public List<EventoRicorrente> getEventiRicorrentiByPeriod(LocalDate data_inizio, LocalDate data_fine) throws DataException {
        List<EventoRicorrente> eventi = new ArrayList();

        try {
            sEventiRicorrentiByPeriod.setDate(1, Date.valueOf(data_inizio));
            sEventiRicorrentiByPeriod.setDate(2, Date.valueOf(data_fine));
            try ( ResultSet rs = sEventiRicorrentiByPeriod.executeQuery()) {
                while (rs.next()) {
                    eventi.add(this.createEventoRicorrente(rs));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Errore DB", ex);
        }
        return eventi;
    }

    @Override
    public List<EventoRicorrente> getEventiRicorrentiByDataAndAula(LocalDate data, Aula aula) throws DataException {
        List<EventoRicorrente> eventi = new ArrayList();

        try {
            sEventiRicorrentiByDataAndAula.setDate(1, Date.valueOf(data));
            sEventiRicorrentiByDataAndAula.setInt(2, aula.getKey());
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

    @Override
    public List<EventoRicorrente> getEventiRicorrentiByPeriodAndCorso(LocalDate dataInizio, LocalDate dataFine, Corso corso) throws DataException {
        List<EventoRicorrente> eventi = new ArrayList();

        try {
            sEventiRicorrentiByPeriodAndCorso.setDate(1, Date.valueOf(dataInizio));
            sEventiRicorrentiByPeriodAndCorso.setDate(2, Date.valueOf(dataFine));
            sEventiRicorrentiByPeriodAndCorso.setInt(3, corso.getKey());
            try ( ResultSet rs = sEventiRicorrentiByPeriodAndCorso.executeQuery()) {
                while (rs.next()) {
                    eventi.add(this.createEventoRicorrente(rs));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Errore DB", ex);
        }
        return eventi;
    }

    @Override
    public List<Integer> getAllEventiIDByRicorrenze() throws DataException {
        List<Integer> eventi = new ArrayList();

        try {
            try ( ResultSet rs = sAllEventiIDByRicorrenze.executeQuery()) {
                while (rs.next()) {
                    eventi.add(rs.getInt(1));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Errore DB", ex);
        }
        return eventi;
    }

}
