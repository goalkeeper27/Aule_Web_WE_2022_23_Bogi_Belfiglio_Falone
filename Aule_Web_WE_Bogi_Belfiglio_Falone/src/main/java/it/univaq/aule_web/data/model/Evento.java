/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.aule_web.data.model;

import it.univaq.aule_web.data.DataItem;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 *
 * @author franc
 */
public interface Evento extends DataItem<Integer> {
     String getNome();
     String getDescrizione();
     Tipologia getTipologia();
     LocalDate getData_evento();
     LocalTime getOra_inizio();
     LocalTime getOra_fine();
     Ricorrenza getRicorrenza();
     LocalDate getData_fine_ricorrenza();
     Corso getCorso();
     Aula getAula();
     Responsabile getResponsabile();
     
     void setNome(String nome);
     void setDescrizione(String descrizione);
     void SetTipologia(Tipologia tipologia);
     void setData_evento(LocalDate data);
     void setOra_inizio(LocalTime orario);
     void setOra_fine(LocalTime orario);
     void setRicorrenza(Ricorrenza ricorrenza);
     void setData_fine_ricorrenza(LocalDate data);
     void setCorso(Corso corso);
     void setResponsabile(Responsabile responsabile);
     void setAula(Aula aula);
     
     
     
}
