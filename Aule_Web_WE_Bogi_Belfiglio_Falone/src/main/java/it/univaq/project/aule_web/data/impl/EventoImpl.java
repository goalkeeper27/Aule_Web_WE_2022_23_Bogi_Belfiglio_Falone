/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.project.aule_web.data.impl;

import it.univaq.project.aule_web.framework.data.DataItemImpl;
import it.univaq.project.aule_web.data.model.Aula;
import it.univaq.project.aule_web.data.model.Corso;
import it.univaq.project.aule_web.data.model.Evento;
import it.univaq.project.aule_web.data.model.Responsabile;
import it.univaq.project.aule_web.data.model.enumerable.Ricorrenza;
import it.univaq.project.aule_web.data.model.enumerable.Tipologia;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class EventoImpl extends DataItemImpl<Integer> implements Evento {

    private String nome;
    private String descrizione;
    private Tipologia tipologia;
    private LocalDate dataEvento;
    private LocalTime oraInizio;
    private LocalTime oraFine;
    private Ricorrenza ricorrenza;
    private LocalDate dataFineRicorrenza;
    private Corso corso;
    private Aula aula;
    private Responsabile responsabile;

    public EventoImpl() {
        super();
        nome = "";
        descrizione = "";
        tipologia = null;
        dataEvento = null;
        oraFine = null;
        oraInizio = null;
        ricorrenza = null;
        dataFineRicorrenza = null;
        corso = null;
        aula = null;
        responsabile = null;

    }

    @Override
    public String getNome() {
        return this.nome;
    }

    @Override
    public String getDescrizione() {
        return this.descrizione;
    }

    @Override
    public Tipologia getTipologia() {
        return this.tipologia;
    }

    @Override
    public LocalDate getDataEvento() {
        return this.dataEvento;
    }

    @Override
    public LocalTime getOraInizio() {
        return this.oraInizio;
    }

    @Override
    public LocalTime getOraFine() {
        return this.oraFine;
    }

    @Override
    public Ricorrenza getRicorrenza() {
        return this.ricorrenza;
    }

    @Override
    public LocalDate getDataFineRicorrenza() {
        return this.dataFineRicorrenza;
    }

    @Override
    public Corso getCorso() {
        return this.corso;
    }

    @Override
    public Aula getAula() {
        return this.aula;
    }

    @Override
    public Responsabile getResponsabile() {
        return this.responsabile;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    @Override
    public void setTipologia(Tipologia tipologia) {
        this.tipologia = tipologia;
    }

    @Override
    public void setDataEvento(LocalDate data) {
        this.dataEvento = data;
    }

    @Override
    public void setOraInizio(LocalTime orario) {
        this.oraInizio = orario;
    }

    @Override
    public void setOraFine(LocalTime orario) {
        this.oraFine = orario;
    }

    @Override
    public void setRicorrenza(Ricorrenza ricorrenza) {
        this.ricorrenza = ricorrenza;
    }

    @Override
    public void setDataFineRicorrenza(LocalDate data) {
        this.dataFineRicorrenza = data;
    }

    @Override
    public void setCorso(Corso corso) {
        this.corso = corso;
    }

    @Override
    public void setResponsabile(Responsabile responsabile) {
        this.responsabile = responsabile;
    }

    @Override
    public void setAula(Aula aula) {
        this.aula = aula;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        
        Evento evento = (Evento) obj;
        if (this.getKey() != evento.getKey()) {
            return false;
        } else if (this.getVersion() != evento.getVersion()) {
            return false;
        } else if (!this.getNome().equals(evento.getNome())) {
            return false;
        } else if (!this.getDescrizione().equals(evento.getDescrizione())) {
            return false;
        } else if (!this.getDataEvento().equals(evento.getDataEvento())) {
            return false;
        } else if (!this.getOraFine().equals(evento.getOraFine())) {
            return false;
        } else if (!this.getOraInizio().equals(evento.getOraInizio())) {
            return false;
        } else if (!this.getRicorrenza().equals(evento.getRicorrenza())) {
            return false;
        } else if (!this.getTipologia().equals(evento.getTipologia())) {
            return false;
        } else {
            return true;
        }

    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.nome);
        hash = 59 * hash + Objects.hashCode(this.descrizione);
        hash = 59 * hash + Objects.hashCode(this.tipologia);
        hash = 59 * hash + Objects.hashCode(this.dataEvento);
        hash = 59 * hash + Objects.hashCode(this.oraInizio);
        hash = 59 * hash + Objects.hashCode(this.oraFine);
        hash = 59 * hash + Objects.hashCode(this.ricorrenza);
        hash = 59 * hash + Objects.hashCode(this.dataFineRicorrenza);
        return hash;
    }

}
