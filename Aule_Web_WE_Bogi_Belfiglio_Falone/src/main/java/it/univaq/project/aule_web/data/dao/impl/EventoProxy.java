/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.project.aule_web.data.dao.impl;

import it.univaq.project.aule_web.data.impl.EventoImpl;
import it.univaq.project.aule_web.data.model.Aula;
import it.univaq.project.aule_web.data.model.Corso;
import it.univaq.project.aule_web.data.model.Responsabile;
import it.univaq.project.aule_web.data.model.enumerable.Ricorrenza;
import it.univaq.project.aule_web.data.model.enumerable.Tipologia;
import it.univaq.project.aule_web.framework.data.DataException;
import it.univaq.project.aule_web.framework.data.DataItemProxy;
import it.univaq.project.aule_web.framework.data.DataLayer;
import it.univaq.project.aule_web.data.dao.AulaDAO;
import it.univaq.project.aule_web.data.dao.CorsoDAO;
import it.univaq.project.aule_web.data.dao.ResponsabileDAO;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alberto Bogi
 */
public class EventoProxy extends EventoImpl implements DataItemProxy {

    protected boolean modified;
    protected DataLayer dataLayer;

    protected int corsoKey;
    protected int responsabileKey;
    protected int aulaKey;

    protected EventoProxy(DataLayer d) {
        super();
        this.modified = false;
        this.dataLayer = d;
        this.corsoKey = 0;
        this.aulaKey = 0;
        this.responsabileKey = 0;
    }

    @Override
    public Corso getCorso() {
        if (super.getCorso() == null && this.corsoKey > 0) {
            try {
                super.setCorso(((CorsoDAO) dataLayer.getDAO(Corso.class)).getCorso(this.corsoKey));
            } catch (DataException ex) {
                Logger.getLogger(EventoProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getCorso();
    }

    @Override
    public Aula getAula() {
        if (super.getAula() == null && this.aulaKey > 0) {
            try {
                super.setAula(((AulaDAO) dataLayer.getDAO(Aula.class)).getAula(this.aulaKey));
            } catch (DataException ex) {
                Logger.getLogger(EventoProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getAula();
    }

    @Override
    public Responsabile getResponsabile() {
        if (super.getResponsabile() == null && this.responsabileKey > 0) {
            try {
                super.setResponsabile(((ResponsabileDAO) dataLayer.getDAO(Responsabile.class)).getResponsabile(this.responsabileKey));
            } catch (DataException ex) {
                Logger.getLogger(EventoProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getResponsabile();
    }

    @Override
    public void setNome(String nome) {
        super.setNome(nome);
        this.modified = true;
    }

    @Override
    public void setDescrizione(String descrizione) {
        super.setDescrizione(descrizione);
        this.modified = true;
    }

    @Override
    public void setTipologia(Tipologia tipologia) {
        super.setTipologia(tipologia);
        this.modified = true;
    }

    @Override
    public void setDataEvento(LocalDate data) {
        super.setDataEvento(data);
        this.modified = true;
    }

    @Override
    public void setOraInizio(LocalTime orario) {
        super.setOraInizio(orario);
        this.modified = true;
    }

    @Override
    public void setOraFine(LocalTime orario) {
        super.setOraFine(orario);
        this.modified = true;
    }

    @Override
    public void setRicorrenza(Ricorrenza ricorrenza) {
        super.setRicorrenza(ricorrenza);
        this.modified = true;
    }

    @Override
    public void setDataFineRicorrenza(LocalDate data) {
        super.setDataFineRicorrenza(data);
        this.modified = true;
    }

    @Override
    public void setCorso(Corso corso) {
        super.setCorso(corso);
        if (corso != null) {
            this.corsoKey = corso.getKey();
        } else {
            this.corsoKey = 0;
        }
        this.modified = true;
    }

    @Override
    public void setResponsabile(Responsabile responsabile) {
        super.setResponsabile(responsabile);
        if (responsabile != null) {
            this.responsabileKey = responsabile.getKey();
        } else {
            this.responsabileKey = 0;
        }
        this.modified = true;
    }

    @Override
    public void setAula(Aula aula) {
        super.setAula(aula);
        if (aula != null) {
            this.aulaKey = aula.getKey();
        } else {
            this.aulaKey = 0;
        }
        this.modified = true;
    }

    @Override
    public void setKey(Integer key) {
        super.setKey(key);
        this.modified = true;
    }

    @Override
    public boolean isModified() {
        return this.modified;
    }

    @Override
    public void setModified(boolean dirty) {
        this.modified = dirty;
    }

    public void setResponsabileKey(int key) {
        this.responsabileKey = key;
        super.setResponsabile(null);
    }

    public void setAulaKey(int key) {
        this.aulaKey = key;
        super.setAula(null);
    }

    public void setCorsoKey(int key) {
        this.corsoKey = key;
        super.setCorso(null);
    }

    public int getResponsabileKey() {
        return this.responsabileKey;
    }

    public int getAulaKey() {
        return this.aulaKey;
    }

    public int getCorsoKey() {
        return this.corsoKey;
    }

    @Override
    public String toString() {
        return this.getKey() + "," + this.getNome() + "," + this.getDescrizione() + "," + this.getDataEvento() + "," + this.getOraFine() + "," + this.getOraInizio() + "," + this.getRicorrenza() + "," + this.getDataFineRicorrenza() + "," + this.getAulaKey() + "," + this.getResponsabileKey() + "," + this.getCorsoKey();
    }

    @Override
    public boolean equals(Object obj) {
        EventoProxy evento = (EventoProxy)obj;
        if (super.equals(evento)) {
            if (this.corsoKey != evento.corsoKey) {
                return false;
            }
            if (this.responsabileKey != evento.responsabileKey) {
                return false;
            }
            return this.aulaKey == evento.aulaKey;
        }
        else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + this.corsoKey;
        hash = 59 * hash + this.responsabileKey;
        hash = 59 * hash + this.aulaKey;
        return hash + super.hashCode();
    }

    
    

}
