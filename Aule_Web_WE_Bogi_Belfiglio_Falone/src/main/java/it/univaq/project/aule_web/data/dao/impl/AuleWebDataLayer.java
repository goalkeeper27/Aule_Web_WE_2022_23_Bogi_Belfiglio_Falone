/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.project.aule_web.data.dao.impl;


import it.univaq.project.aule_web.data.model.Amministratore;
import it.univaq.project.aule_web.data.model.Attrezzatura;
import it.univaq.project.aule_web.data.model.Aula;
import it.univaq.project.aule_web.data.model.Corso;
import it.univaq.project.aule_web.data.model.Evento;
import it.univaq.project.aule_web.data.model.EventoRicorrente;
import it.univaq.project.aule_web.data.model.Gruppo;
import it.univaq.project.aule_web.data.model.Responsabile;
import it.univaq.project.aule_web.framework.data.DataException;
import it.univaq.project.aule_web.framework.data.DataLayer;

import it.univaq.project.aule_web.data.dao.AmministratoreDAO;
import it.univaq.project.aule_web.data.dao.AttrezzaturaDAO;
import it.univaq.project.aule_web.data.dao.AulaDAO;
import it.univaq.project.aule_web.data.dao.CorsoDAO;
import it.univaq.project.aule_web.data.dao.EventoDAO;
import it.univaq.project.aule_web.data.dao.EventoRicorrenteDAO;
import it.univaq.project.aule_web.data.dao.GruppoDAO;
import it.univaq.project.aule_web.data.dao.ResponsabileDAO;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 *
 * @author Alberto Bogi
 */
public class AuleWebDataLayer extends DataLayer{
    
    
    public AuleWebDataLayer(DataSource datasource) throws SQLException {
        super(datasource);
    }

    @Override
    public void init() throws DataException {
        
        registerDAO(Amministratore.class, new AmministratoreDAO_MySQL(this));
        registerDAO(Attrezzatura.class, new AttrezzaturaDAO_MySQL(this));
        registerDAO(Aula.class, new AulaDAO_MySQL(this));
        registerDAO(Corso.class, new CorsoDAO_MySQL(this));
        registerDAO(Evento.class, new EventoDAO_MySQL(this));
        registerDAO(EventoRicorrente.class, new EventoRicorrenteDAO_MySQL(this));
        registerDAO(Gruppo.class, new GruppoDAO_MySQL(this));
        registerDAO(Responsabile.class, new ResponsabileDAO_MySQL(this));
    }

    //helpers    
    public AmministratoreDAO getAmministratoreDAO() {
        return (AmministratoreDAO) getDAO(Amministratore.class);
    }

    public AttrezzaturaDAO getAttrezzaturaDAO() {
        return (AttrezzaturaDAO) getDAO(Attrezzatura.class);
    }

    public AulaDAO getAulaDAO() {
        return (AulaDAO) getDAO(Aula.class);
    }

    public CorsoDAO getCorsoDAO() {
        return (CorsoDAO) getDAO(Corso.class);
    }
    
    public EventoDAO getEventoDAO() {
        return (EventoDAO) getDAO(Evento.class);
    }
    
    public EventoRicorrenteDAO getEventoRicorrenteDAO() {
        return (EventoRicorrenteDAO) getDAO(EventoRicorrente.class);
    }
    
    public GruppoDAO getGruppoDAO() {
        return (GruppoDAO) getDAO(Gruppo.class);
    }
    
    public ResponsabileDAO getResponsabileDAO() {
        return (ResponsabileDAO) getDAO(Responsabile.class);
    }

    
}
