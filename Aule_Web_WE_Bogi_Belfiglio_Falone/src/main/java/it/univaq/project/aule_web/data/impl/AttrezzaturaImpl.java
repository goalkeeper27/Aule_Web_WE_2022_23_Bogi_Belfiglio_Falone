/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.project.aule_web.data.impl;

import it.univaq.project.aule_web.framework.data.DataItemImpl;
import it.univaq.project.aule_web.data.model.Attrezzatura;
import it.univaq.project.aule_web.data.model.Aula;

/**
 *
 * @author Stefano Belfiglio
 */
public class AttrezzaturaImpl extends DataItemImpl<Integer> implements Attrezzatura {
    
    private Aula aula;
    private String nome;
    private int numeroDiSerie;
    
    public AttrezzaturaImpl() {
        aula = null;
        nome = "";
        numeroDiSerie = 0;
    }
    
    @Override
    public Aula getAula() {
        return this.aula;
    }

    @Override
    public void setAula(Aula aula) {
        this.aula = aula;
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
    public int getNumeroDiSerie() {
        return this.numeroDiSerie;
    }

    @Override
    public void setNumeroDiSerie(int numeroDiSerie) {
        this.numeroDiSerie = numeroDiSerie;
    }
    
}
