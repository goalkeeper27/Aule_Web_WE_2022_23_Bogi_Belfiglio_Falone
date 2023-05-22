/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.aule_web.data.impl;

import it.univaq.aule_web.data.DataItemImpl;
import it.univaq.aule_web.data.model.Aula;
import it.univaq.aule_web.data.model.Corso;
import it.univaq.aule_web.data.model.Evento;
import it.univaq.aule_web.data.model.Responsabile;
import it.univaq.aule_web.data.model.Ricorrenza;
import it.univaq.aule_web.data.model.Tipologia;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author franc
 */
public class EventoImpl extends DataItemImpl<Integer> implements Evento{
    
    private String nome;
    private String descrizione;
    private Tipologia tipologia;
    private LocalDate data_evento;
    private LocalTime ora_inizio;
    private LocalTime ora_fine;
    private Ricorrenza ricorrenza;
    private LocalDate data_fine_ricorrenza;
    private Corso corso;
    private Aula aula;
    private Responsabile responsabile;
    
    public EventoImpl(){
        super();
        nome = "";
        descrizione = "";
        tipologia = null;
        data_evento = null;
        ora_fine = null;
        ora_inizio = null;
        ricorrenza = null;
        data_fine_ricorrenza = null;
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
    public LocalDate getData_evento() {
        return this.data_evento;
    }

    @Override
    public LocalTime getOra_inizio() {
        return this.ora_inizio;
    }

    @Override
    public LocalTime getOra_fine() {
        return this.ora_fine;
    }

    @Override
    public Ricorrenza getRicorrenza() {
        return this.ricorrenza;
    }

    @Override
    public LocalDate getData_fine_ricorrenza() {
        return this.data_fine_ricorrenza;
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
    public void setData_evento(LocalDate data) {
        this.data_evento = data;
    }

    @Override
    public void setOra_inizio(LocalTime orario) {
        this.ora_inizio = orario;
    }

    @Override
    public void setOra_fine(LocalTime orario) {
        this.ora_fine = orario;
    }

    @Override
    public void setRicorrenza(Ricorrenza ricorrenza) {
        this.ricorrenza = ricorrenza;
    }

    @Override
    public void setData_fine_ricorrenza(LocalDate data) {
        this.data_fine_ricorrenza = data;
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
    
}
