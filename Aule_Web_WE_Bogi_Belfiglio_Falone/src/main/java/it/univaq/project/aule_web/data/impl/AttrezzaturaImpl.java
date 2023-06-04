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
    private String numeroDiSerie;
    
    public AttrezzaturaImpl() {
        aula = null;
        nome = "";
        numeroDiSerie = null;
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
    public String getNumeroDiSerie() {
        return this.numeroDiSerie;
    }

    @Override
    public void setNumeroDiSerie(String numeroDiSerie) {
        this.numeroDiSerie = numeroDiSerie;
    }
    
}
