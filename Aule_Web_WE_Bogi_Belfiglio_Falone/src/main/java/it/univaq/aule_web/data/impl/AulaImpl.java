/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.aule_web.data.impl;

import it.univaq.aule_web.framework.data.DataItemImpl;
import it.univaq.aule_web.data.model.Aula;
import it.univaq.aule_web.data.model.Responsabile;

/**
 *
 * @author Stefano Belfiglio
 */
public class AulaImpl extends DataItemImpl<Integer> implements Aula {
    
    private Responsabile responsabile;
    private String nome;
    private String luogo;
    private String edificio;
    private int piano;
    private int capienza;
    private int numeroPreseElettriche;
    private int numeroPreseDiRete;
    private String noteGeneriche;
    
    public AulaImpl() {
        super();
        responsabile = null;
        nome = "";
        luogo = "";
        edificio = "";
        piano = 0;
        capienza = 0;
        numeroPreseElettriche = 0;
        numeroPreseDiRete = 0;
        noteGeneriche = "";
    } 

    @Override
    public Responsabile getResponsabile() {
        return this.responsabile;
    }

    @Override
    public void setResponsabile(Responsabile responsabile) {
        this.responsabile = responsabile;
    }

    @Override
    public String getNome() {
        return this.nome;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String getLuogo() {
        return this.luogo;
    }

    @Override
    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    @Override
    public String getEdificio() {
        return this.edificio;
    }

    @Override
    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    @Override
    public int getPiano() {
        return this.piano;
    }

    @Override
    public void setPiano(int piano) {
        this.piano = piano;
    }

    @Override
    public int getCapienza() {
        return this.capienza;
    }

    @Override
    public void setCapienza(int capienza) {
        this.capienza = capienza;
    }

    @Override
    public int getNumeroPreseElettriche() {
        return this.numeroPreseElettriche;
    }

    @Override
    public void setNumeroPreseElettriche(int numeroPreseElettriche) {
        this.numeroPreseElettriche = numeroPreseElettriche;
    }

    @Override
    public int getNumeroPreseDiRete() {
        return this.numeroPreseDiRete;
    }

    @Override
    public void setNumeroPreseDiRete(int numeroPreseDiRete) {
        this.numeroPreseDiRete = numeroPreseDiRete;
    }

    @Override
    public String getNoteGeneriche() {
        return this.noteGeneriche;
    }

    @Override
    public void setNoteGeneriche(String noteGeneriche) {
        this.noteGeneriche = noteGeneriche;
    }

    
}
