/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.project.aule_web.data.model;

import it.univaq.project.aule_web.framework.data.DataItem;

public interface Aula extends DataItem<Integer> {
    
    Responsabile getResponsabile();
    
    void setResponsabile(Responsabile responsabile);
    
    String getNome();
    
    void setNome(String nome);
    
    String getLuogo();
    
    void setLuogo(String luogo);
    
    String getEdificio();
    
    void setEdificio(String edificio);
    
    int getPiano();
    
    void setPiano(int piano);
    
    int getCapienza();
    
    void setCapienza(int capienza);
    
    int getNumeroPreseElettriche();
    
    void setNumeroPreseElettriche(int numeroPreseElettriche);
    
    int getNumeroPreseDiRete();
    
    void setNumeroPreseDiRete(int numeroDiPreseDiRete);
    
    String getNoteGeneriche();
    
    void setNoteGeneriche(String noteGeneriche);
    
    
    
}
