/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.project.aule_web.data.dao.impl;

import it.univaq.project.aule_web.data.model.Aula;
import it.univaq.project.aule_web.data.model.Corso;
import it.univaq.project.aule_web.data.model.Evento;
import it.univaq.project.aule_web.data.model.enumerable.Ricorrenza;
import it.univaq.project.aule_web.data.model.enumerable.Tipologia;
import it.univaq.project.aule_web.framework.data.DAO;
import it.univaq.project.aule_web.framework.data.DataException;
import it.univaq.project.aule_web.framework.data.DataItemProxy;
import it.univaq.project.aule_web.framework.data.DataLayer;
import it.univaq.project.aule_web.framework.data.OptimisticLockException;
import it.univaq.project.aule_web.data.dao.EventoDAO;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alberto Bogi
 */
public class EventoDAO_MySQL extends DAO implements EventoDAO {

    private PreparedStatement sEventoByID, sEventoInAWeekByAula, sEventoInADayByAula, sCurrentEventoByAula, sEventoInAWeekByCorso, sEventiByPeriodo, sAllEventi, sEventiByPartialName;
    private PreparedStatement iEvento, uEvento, dEvento;

    public EventoDAO_MySQL(DataLayer d) {
        super(d);

    }

    public void init() throws DataException {

        try {
            super.init();
            sAllEventi = this.dataLayer.getConnection().prepareStatement("SELECT * FROM Evento WHERE data_evento >= CURDATE()");
            sEventoByID = this.dataLayer.getConnection().prepareStatement("SELECT * FROM Evento WHERE ID = ?");
            sEventoInAWeekByAula = this.dataLayer.getConnection().prepareStatement("SELECT * FROM Evento WHERE ID_aula = ? AND (data_evento BETWEEN (? - INTERVAL 1 DAY) AND ?)");
            sEventoInADayByAula = this.dataLayer.getConnection().prepareStatement("SELECT * FROM Evento WHERE ID_aula = ? AND data_evento = ?");
            sCurrentEventoByAula = this.dataLayer.getConnection().prepareStatement("SELECT * FROM Evento WHERE data_evento = CURDATE() AND ((ora_inizio BETWEEN (CURTIME() - INTERVAL 15 MINUTE) AND (CURTIME() + INTERVAL 3 HOUR)) OR(CURTIME() BETWEEN ora_inizio AND ora_fine))AND ID_aula = ? ORDER BY data_evento, ora_inizio;");
            sEventoInAWeekByCorso = this.dataLayer.getConnection().prepareStatement("SELECT * FROM Evento WHERE ID_corso = ? AND (data_evento BETWEEN ? AND ?)");
            sEventiByPeriodo = this.dataLayer.getConnection().prepareStatement("SELECT * FROM Evento E WHERE (E.data_evento BETWEEN (? - INTERVAL 1 DAY) AND ?)");
            sEventiByPartialName = this.dataLayer.getConnection().prepareStatement("SELECT * FROM Evento E WHERE substring(E.nome,1,?) = ? AND E.data_evento >= CURDATE();");
            iEvento = this.dataLayer.getConnection().prepareStatement("INSERT INTO Evento(nome, descrizione, tipologia, data_evento, ora_inizio, ora_fine, ricorrenza, data_fine_ricorrenza, ID_corso, ID_responsabile, ID_aula) VALUES (?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uEvento = this.dataLayer.getConnection().prepareStatement("UPDATE Evento SET nome = ?, descrizione = ?, tipologia = ?,"
                    + "data_evento = ?, ora_inizio = ?, ora_fine = ?, ricorrenza = ?, data_fine_ricorrenza = ?, ID_corso = ?, ID_responsabile =?,"
                    + "ID_aula = ?, versione = ? WHERE ID = ? AND versione = ?");
            dEvento = this.dataLayer.getConnection().prepareStatement("DELETE FROM Evento WHERE ID = ?");
        } catch (SQLException ex) {
            throw new DataException("Errore nell'inizializzazione del data layer", ex);
        }

    }
    
    public void destroy() throws DataException {
        try {
            sAllEventi.close();
            sEventoByID.close();
            sEventoInAWeekByAula.close();
            sEventoInADayByAula.close();
            sCurrentEventoByAula.close();
            sEventoInAWeekByCorso.close();
            sEventiByPeriodo.close();
            sEventiByPartialName.close();
            iEvento.close();
            uEvento.close();
            dEvento.close();

        } catch (SQLException ex) {

        }
        super.destroy();
    }

    private Evento createEvento(ResultSet rs) throws DataException {
        EventoProxy evento = (EventoProxy) this.createEvento();
        try {
            evento.setKey(rs.getInt("ID"));
            evento.setDataEvento((rs.getDate("data_evento").toLocalDate()));
            evento.setNome(rs.getString("nome"));
            evento.setDescrizione(rs.getString("descrizione"));
            switch (rs.getString("tipologia")) {
                case "LEZIONE":
                    evento.setTipologia(Tipologia.LEZIONE);
                    evento.setCorsoKey(rs.getInt("ID_corso"));
                    break;
                case "ESAME":
                    evento.setTipologia(Tipologia.ESAME);
                    evento.setCorsoKey(rs.getInt("ID_corso"));
                    break;
                case "PARZIALE":
                    evento.setTipologia(Tipologia.PARZIALE);
                    evento.setCorsoKey(rs.getInt("ID_corso"));
                    break;
                case "SEMINARIO":
                    evento.setTipologia(Tipologia.SEMINARIO);
                    evento.setCorsoKey(0);
                    break;
                case "LAUREA":
                    evento.setTipologia(Tipologia.LAUREA);
                    evento.setCorsoKey(0);
                    break;
                case "RIUNIONE":
                    evento.setTipologia(Tipologia.RIUNIONE);
                    evento.setCorsoKey(0);
                    break;
                case "ALTRO":
                    evento.setTipologia(Tipologia.ALTRO);
                    evento.setCorsoKey(0);
                    break;
            }
            if (rs.getString("ricorrenza") != null) {
                switch (rs.getString("ricorrenza")) {
                    case "GIORNALIERA":
                        evento.setRicorrenza(Ricorrenza.GIORNALIERA);
                        evento.setDataFineRicorrenza(rs.getDate("data_fine_ricorrenza").toLocalDate());
                        break;
                    case "SETTIMANALE":
                        evento.setRicorrenza(Ricorrenza.SETTIMANALE);
                        evento.setDataFineRicorrenza(rs.getDate("data_fine_ricorrenza").toLocalDate());
                        break;
                    case "MENSILE":
                        evento.setRicorrenza(Ricorrenza.MENSILE);
                        evento.setDataFineRicorrenza(rs.getDate("data_fine_ricorrenza").toLocalDate());
                        break;
                    case "NESSUNA":
                        evento.setRicorrenza(Ricorrenza.NESSUNA);
                        evento.setDataFineRicorrenza(null);
                        break;
                }

            } else {
                evento.setRicorrenza(null);
                evento.setDataFineRicorrenza(null);
            }
            evento.setOraInizio(rs.getTime("ora_inizio").toLocalTime());
            evento.setOraFine(rs.getTime("ora_fine").toLocalTime());
            evento.setAulaKey(rs.getInt("ID_aula"));
            evento.setResponsabileKey(rs.getInt("ID_responsabile"));
            evento.setVersion(rs.getInt("versione"));
        } catch (SQLException ex) {
            throw new DataException("Errore nella creazione dell'oggetto evento");
        }
            
        return evento;
    }

    @Override
    public Evento getEvento(int key) throws DataException {
        Evento evento = (EventoProxy) this.createEvento();
        try {
            sEventoByID.setInt(1, key);
            try ( ResultSet rs = sEventoByID.executeQuery()) {
                if (rs.next()) {
                    evento = this.createEvento(rs);

                }
            }

        } catch (SQLException ex) {
            throw new DataException("Errore nel caricamento dell'evento da ID", ex);
        }

        return evento;
    }

    @Override
    public Evento createEvento() {
        return new EventoProxy(this.getDataLayer());
    }

    @Override
    public List<Evento> getCurrentEventoByAula(Aula aula) throws DataException {
        List<Evento> eventi = new ArrayList();
        try {
            sCurrentEventoByAula.setInt(1, aula.getKey());
            try ( ResultSet rs = sCurrentEventoByAula.executeQuery()) {
                while (rs.next()) {
                    eventi.add(this.createEvento(rs));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Errore nel caricamento degli eventi", ex);
        }

        return eventi;
    }

    @Override
    public List<Evento> getEventoInAWeekByCorso(Corso corso, LocalDate dataInizio, LocalDate dataFine) throws DataException {
        List<Evento> eventi = new ArrayList();
        try {
            sEventoInAWeekByCorso.setInt(1, corso.getKey());
            sEventoInAWeekByCorso.setDate(2, Date.valueOf(dataInizio));
            sEventoInAWeekByCorso.setDate(3, Date.valueOf(dataFine));
            try ( ResultSet rs = sEventoInAWeekByCorso.executeQuery()) {
                while (rs.next()) {
                    eventi.add(this.createEvento(rs));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Errore nel caricamento eventi", ex);
        }

        return eventi;
    }

    @Override
    public void storeEvento(Evento evento) throws DataException {
        try {
            EventoProxy eventoProxy = (EventoProxy) evento;

            if (eventoProxy.getKey() != null && eventoProxy.getKey() > 0) {
                if (eventoProxy instanceof DataItemProxy && !((DataItemProxy) eventoProxy).isModified()) {
                    return;
                }
                uEvento.setString(1, eventoProxy.getNome());
                uEvento.setString(2, eventoProxy.getDescrizione());

                switch (eventoProxy.getTipologia().toString()) {
                    case "LEZIONE":
                        uEvento.setString(3, "LEZIONE");
                        uEvento.setInt(9, eventoProxy.getCorsoKey());
                        break;
                    case "ESAME":
                        uEvento.setString(3, "ESAME");
                        uEvento.setInt(9, eventoProxy.getCorsoKey());
                        break;
                    case "PARZIALE":
                        uEvento.setString(3, "PARZIALE");
                        uEvento.setInt(9, eventoProxy.getCorsoKey());
                        break;
                    case "SEMINARIO":
                        uEvento.setString(3, "SEMINARIO");
                        uEvento.setNull(9, java.sql.Types.INTEGER);
                        break;
                    case "LAUREA":
                        uEvento.setString(3, "LAUREA");
                        uEvento.setNull(9, java.sql.Types.INTEGER);
                        break;
                    case "RIUNIONE":
                        uEvento.setString(3, "RIUNIONE");
                        uEvento.setNull(9, java.sql.Types.INTEGER);
                        break;
                    case "ALTRO":
                        uEvento.setString(3, "ALTRO");
                        uEvento.setNull(9, java.sql.Types.INTEGER);
                        break;
                }

                uEvento.setDate(4, Date.valueOf(eventoProxy.getDataEvento()));
                uEvento.setTime(5, Time.valueOf(eventoProxy.getOraInizio()));
                uEvento.setTime(6, Time.valueOf(eventoProxy.getOraFine()));

                switch (eventoProxy.getRicorrenza().toString()) {
                    case "GIORNALIERA":
                        uEvento.setString(7, "GIORNALIERA");
                        uEvento.setDate(8, Date.valueOf(eventoProxy.getDataFineRicorrenza()));
                        break;
                    case "MENSILE":
                        uEvento.setString(7, "MENSILE");
                        uEvento.setDate(8, Date.valueOf(eventoProxy.getDataFineRicorrenza()));
                        break;
                    case "SETTIMANALE":
                        uEvento.setString(7, "SETTIMANALE");
                        uEvento.setDate(8, Date.valueOf(eventoProxy.getDataFineRicorrenza()));
                        break;
                    case "NESSUNA":
                        uEvento.setString(7, "NESSUNA");
                        uEvento.setNull(8, java.sql.Types.DATE);

                }

                uEvento.setInt(10, eventoProxy.getResponsabileKey());
                uEvento.setInt(11, eventoProxy.getAulaKey());
                long versioneCorrente = eventoProxy.getVersion();
                long versioneSuccessiva = versioneCorrente + 1;

                uEvento.setLong(12, versioneSuccessiva);
                uEvento.setLong(14, versioneCorrente);
                uEvento.setInt(13, eventoProxy.getKey());

                if (uEvento.executeUpdate() == 0) {
                    throw new OptimisticLockException(eventoProxy);
                } else {
                    eventoProxy.setVersion(versioneSuccessiva);
                }
            } else { //insert
                iEvento.setString(1, eventoProxy.getNome());
                iEvento.setString(2, eventoProxy.getDescrizione());

                switch (eventoProxy.getTipologia().toString()) {
                    case "LEZIONE":
                        iEvento.setString(3, "LEZIONE");
                        iEvento.setInt(9, eventoProxy.getCorsoKey());
                        break;
                    case "ESAME":
                        iEvento.setString(3, "ESAME");
                        iEvento.setInt(9, eventoProxy.getCorsoKey());
                        break;
                    case "PARZIALE":
                        iEvento.setString(3, "PARZIALE");
                        iEvento.setInt(9, eventoProxy.getCorsoKey());
                        break;
                    case "SEMINARIO":
                        iEvento.setString(3, "SEMINARIO");
                        iEvento.setNull(9, java.sql.Types.INTEGER);
                        break;
                    case "LAUREA":
                        iEvento.setString(3, "LAUREA");
                        iEvento.setNull(9, java.sql.Types.INTEGER);
                        break;
                    case "RIUNIONE":
                        iEvento.setString(3, "RIUNIONE");
                        iEvento.setNull(9, java.sql.Types.INTEGER);
                        break;
                    case "ALTRO":
                        iEvento.setString(3, "ALTRO");
                        iEvento.setNull(9, java.sql.Types.INTEGER);
                        break;
                }

                iEvento.setDate(4, Date.valueOf(eventoProxy.getDataEvento()));
                iEvento.setTime(5, Time.valueOf(eventoProxy.getOraInizio()));
                iEvento.setTime(6, Time.valueOf(eventoProxy.getOraFine()));

                switch (eventoProxy.getRicorrenza().toString()) {
                    case "GIORNALIERA":
                        iEvento.setString(7, "GIORNALIERA");
                        iEvento.setDate(8, Date.valueOf(eventoProxy.getDataFineRicorrenza()));
                        break;
                    case "MENSILE":
                        iEvento.setString(7, "MENSILE");
                        iEvento.setDate(8, Date.valueOf(eventoProxy.getDataFineRicorrenza()));
                        break;
                    case "SETTIMANALE":
                        iEvento.setString(7, "SETTIMANALE");
                        iEvento.setDate(8, Date.valueOf(eventoProxy.getDataFineRicorrenza()));
                        break;
                    case "NESSUNA":
                        iEvento.setString(7, "NESSUNA");
                        iEvento.setNull(8, java.sql.Types.DATE);

                }

                iEvento.setInt(10, eventoProxy.getResponsabileKey());
                iEvento.setInt(11, eventoProxy.getAulaKey());

                if (iEvento.executeUpdate() == 1) {
                    try ( ResultSet keys = iEvento.getGeneratedKeys()) {

                        if (keys.next()) {
                            int key = keys.getInt(1);
                            eventoProxy.setKey(key);
                            //inseriamo il nuovo oggetto nella cache
                            //add the new object to the cache
                            //dataLayer.getCache().add(Article.class, article);
                        }
                    }
                }
            }

            if (eventoProxy instanceof DataItemProxy) {
                ((DataItemProxy) eventoProxy).setModified(false);
            }
        } catch (SQLException | OptimisticLockException ex) {
            throw new DataException("Data error", ex);
        }
    }

    @Override
    public void deleteEvento(Evento evento) throws DataException {
        try {
            if (evento.getKey() != null && evento.getKey() > 0) {
                dEvento.setInt(1, evento.getKey());
                dEvento.execute();

            }
        } catch (SQLException ex) {
            throw new DataException("Errore nell'eliminazione dell'evento", ex);
        }
    }

    /**
     * Viene visualizzata la lista degli eventi associata ad una specifica aula
     * in una specifica settimama
     *
     * @param aula
     * @param dataInizio
     * @param dataFine
     * @return
     * @throws DataException
     */
    @Override
    public List<Evento> getEventoInAWeekByAula(Aula aula, LocalDate dataInizio, LocalDate dataFine) throws DataException {
        List<Evento> eventi = new ArrayList();
        try {
            sEventoInAWeekByAula.setInt(1, aula.getKey());
            sEventoInAWeekByAula.setDate(2, Date.valueOf(dataInizio));
            sEventoInAWeekByAula.setDate(3, Date.valueOf(dataFine));
            try ( ResultSet rs = sEventoInAWeekByAula.executeQuery()) {
                while (rs.next()) {
                    eventi.add(this.createEvento(rs));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Errore nel caricamento degli eventi", ex);
        }

        return eventi;

    }

    /**
     * ************
     * Viene visualizzata la lista degli eventi efettuati in uno specifico
     * giorno in una specifica aula
     *
     * @param aula
     * @param data
     * @return
     * @throws DataException
     */
    @Override
    public List<Evento> getEventoInADayByAula(Aula aula, LocalDate data) throws DataException {
        List<Evento> eventi = new ArrayList<>();
        try {
            sEventoInADayByAula.setInt(1, aula.getKey());
            sEventoInADayByAula.setDate(2, Date.valueOf(data));
            try ( ResultSet rs = sEventoInADayByAula.executeQuery()) {
                while (rs.next()) {
                    eventi.add(this.createEvento(rs));
                }
            }

        } catch (SQLException ex) {
            throw new DataException("Errore durante il caricamento degli eventi", ex);
        }

        return eventi;
    }

    @Override
    public List<Evento> getEventiByPeriodo(LocalDate inizio, LocalDate fine) throws DataException {
        List<Evento> eventi = new ArrayList<>();
        try {
            sEventiByPeriodo.setDate(1, Date.valueOf(inizio));
            sEventiByPeriodo.setDate(2, Date.valueOf(fine));
            try ( ResultSet rs = sEventiByPeriodo.executeQuery()) {
                while (rs.next()) {
                    eventi.add(this.createEvento(rs));
                }
            }

        } catch (SQLException ex) {
            throw new DataException("Errore durante il caricamento degli eventi", ex);
        }

        return eventi;
    }

    @Override
    public List<Evento> getAllEventi() throws DataException {
        List<Evento> eventi = new ArrayList<>();
        try ( ResultSet rs = sAllEventi.executeQuery()) {
            while (rs.next()) {
                eventi.add(createEvento(rs));
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare Aula dal nome e dalla posizione digitati", ex);
        }
        return eventi;
    }

    @Override
    public List<Evento> getEventiByPartialName(String search) throws DataException {
        List<Evento> eventi = new ArrayList<>();
        try {
            sEventiByPartialName.setInt(1, search.length());
            sEventiByPartialName.setString(2, search);
            try ( ResultSet rs = sEventiByPartialName.executeQuery()) {
                while (rs.next()) {
                    eventi.add(createEvento(rs));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("error DB", ex);
        }
        return eventi;
    }
}
