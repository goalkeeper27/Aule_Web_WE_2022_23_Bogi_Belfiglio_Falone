/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.aule_web.data.model;

import it.univaq.aule_web.data.model.enumerable.Tipologia;
import it.univaq.aule_web.data.model.enumerable.Ricorrenza;
import it.univaq.aule_web.framework.data.DataItem;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 *
 * @author Francesco Falone
 */
public interface Evento extends DataItem<Integer> {
     String getNome();
     
     String getDescrizione();
     
     Tipologia getTipologia();
     
     LocalDate getDataEvento();
     
     LocalTime getOraInizio();
     
     LocalTime getOraFine();
     
     Ricorrenza getRicorrenza();
     
     LocalDate getDataFineRicorrenza();
     
     Corso getCorso();
     
     Aula getAula();
     
     Responsabile getResponsabile();
     
     void setNome(String nome);
     
     void setDescrizione(String descrizione);
     
     void setTipologia(Tipologia tipologia);
     
     void setDataEvento(LocalDate data);
     
     void setOraInizio(LocalTime orario);
     
     void setOraFine(LocalTime orario);
     
     void setRicorrenza(Ricorrenza ricorrenza);
     
     void setDataFineRicorrenza(LocalDate data);
     
     void setCorso(Corso corso);
     
     void setResponsabile(Responsabile responsabile);
     
     void setAula(Aula aula);
     
     
     
}
