/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package it.univaq.project.aule_web.controller;

import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.project.aule_web.data.comparator.EventoComparator;
import it.univaq.project.aule_web.framework.result.TemplateResult;
import it.univaq.project.aule_web.data.dao.impl.AuleWebDataLayer;
import it.univaq.project.aule_web.data.model.Aula;
import it.univaq.project.aule_web.data.model.Corso;
import it.univaq.project.aule_web.data.model.Evento;
import it.univaq.project.aule_web.data.model.EventoRicorrente;
import it.univaq.project.aule_web.framework.data.DataException;
import it.univaq.project.aule_web.framework.security.SecurityHelpers;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Eventi extends AuleWebBaseController {

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        try {
            Map data = new HashMap<>();

            int gruppo_key = Integer.valueOf(request.getParameter("IDgruppo"));
            // lista di tutte le aule del gruppo specificato

            List<Aula> aule = ((AuleWebDataLayer) request.getAttribute("datalayer")).getAulaDAO().getAuleByGruppoID(gruppo_key);

            //lista di tutte gli eventi relativi alle aule e corsi specificate
            List<Evento> eventi = new ArrayList<>();

            for (Aula aula : aule) {
                eventi.addAll(((AuleWebDataLayer) request.getAttribute("datalayer")).getEventoDAO().getCurrentEventoByAula(aula));
                //data.put(aula.getNome(), ((AuleWebDataLayer) request.getAttribute("datalayer")).getEventoDAO().getCurrentEventoByAula(aula));
            }

            data.put("aule", aule);

            Collections.sort(eventi, new EventoComparator());
            data.put("eventi", eventi);

            data.put("outline_tpl", "outline_with_select_without_login.ftl.html");
            //serve per indicare quali bottoni mostrare sul browser per la selezione delle ricerche specifiche
            data.put("select_button", 1);
            data.put("IDgruppo", gruppo_key);
            TemplateResult res = new TemplateResult(getServletContext());
            res.activate("eventi_attuali.html", data, response);
        } catch (DataException ex) {
            handleError("Data access exception: " + ex.getMessage(), request, response);
        }
    }

    private void action_eventi_aula(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        try {
            Map data = new HashMap<>();

            int aula_key = Integer.valueOf(request.getParameter("IDaula"));
            int gruppo_key = Integer.valueOf(request.getParameter("IDgruppo"));
            Aula aula = ((AuleWebDataLayer) request.getAttribute("datalayer")).getAulaDAO().getAula(aula_key);

            data.put("select_button", 1);
            data.put("IDgruppo", gruppo_key);
            data.put("aula", (aula));
            data.put("outline_tpl", "outline_with_select_without_login.ftl.html");

            TemplateResult res = new TemplateResult(getServletContext());

            if (request.getParameter("week") != null) {
                String settimana[] = request.getParameter("week").split("-W");

                Calendar calendar = Calendar.getInstance();
                calendar.clear();
                calendar.set(Calendar.YEAR, Integer.valueOf(settimana[0]));
                calendar.set(Calendar.WEEK_OF_YEAR, Integer.valueOf(settimana[1]));

                LocalDate dataInizio = calendar.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate dataFine = dataInizio.plusDays(6);

                List<Evento> eventi = ((AuleWebDataLayer) request.getAttribute("datalayer")).getEventoDAO().getEventoInAWeekByAula(aula, dataInizio, dataFine);

                //aggiungo inoltre le eventuali ricorrenze dei vari eventi  che ci sono in quella settimana
                List<EventoRicorrente> eventiRicorrenti = ((AuleWebDataLayer) request.getAttribute("datalayer")).getEventoRicorrenteDAO().getEventiRicorrentiByPeriodAndAula(dataInizio, dataFine, aula);
                if (eventiRicorrenti != null) {
                    for (EventoRicorrente ev : eventiRicorrenti) {
                        Evento e = ev.getEvento();
                        if (e != null) {
                            e.setDataEvento(ev.getDataEvento());
                            eventi.add(e);
                        }
                    }
                }

                Collections.sort(eventi, new EventoComparator());
                data.put("eventi", eventi);

                //Inserisco il range di date utili per la visualizzazione degli eventi
                List<LocalDate> datas = new ArrayList();
                LocalDate d = dataInizio;
                for (int i = 0; i < 6; i++) {
                    datas.add(d);
                    d = d.plusDays(1);
                }

                data.put("date", datas);

                //utili per visualizzazione messaggio in caso di mancanza di eventi in una specifica settimana
                data.put("data_inizio", dataInizio);
                data.put("data_fine", dataFine);

                res.activate("eventi_aula.ftl.html", data, response);
            } else {
                res.activate("eventi_aula.ftl.html", data, response);
            }
        } catch (DataException ex) {
            handleError("Data access exception: " + ex.getMessage(), request, response);
        }
    }

    private void action_eventi_corso(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        try {
            Map data = new HashMap<>();

            int corso_key = Integer.valueOf(request.getParameter("IDcorso"));
            int gruppo_key = Integer.valueOf(request.getParameter("IDgruppo"));
            Corso corso = ((AuleWebDataLayer) request.getAttribute("datalayer")).getCorsoDAO().getCorso(corso_key);

            data.put("select_button", 3);
            data.put("IDgruppo", gruppo_key);
            data.put("corso", (corso));
            data.put("outline_tpl", "outline_with_select_without_login.ftl.html");
            TemplateResult res = new TemplateResult(getServletContext());

            if (request.getParameter("week") != null) {
                String settimana[] = request.getParameter("week").split("-W");

                Calendar calendar = Calendar.getInstance();
                calendar.clear();
                calendar.set(Calendar.YEAR, Integer.valueOf(settimana[0]));
                calendar.set(Calendar.WEEK_OF_YEAR, Integer.valueOf(settimana[1]));
                LocalDate dataInizio = calendar.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate dataFine = dataInizio.plusDays(6);

                List<Evento> eventi = ((AuleWebDataLayer) request.getAttribute("datalayer")).getEventoDAO().getEventoInAWeekByCorso(corso, dataInizio, dataFine);

                //aggiungo inoltre le eventuali ricorrenze dei vari eventi  che ci sono in quella settimana
                List<EventoRicorrente> eventiRicorrenti = ((AuleWebDataLayer) request.getAttribute("datalayer")).getEventoRicorrenteDAO().getEventiRicorrentiByPeriodAndCorso(dataInizio, dataFine, corso);
                if (eventiRicorrenti != null) {
                    for (EventoRicorrente ev : eventiRicorrenti) {
                        Evento e = ev.getEvento();
                        if (e != null) {
                            e.setDataEvento(ev.getDataEvento());
                            eventi.add(e);
                        }
                    }
                }

                Collections.sort(eventi, new EventoComparator());

                data.put("eventi", eventi);

                //Inserisco il range di date utili per la visualizzazione degli eventi
                List<LocalDate> datas = new ArrayList();
                LocalDate d = dataInizio;
                for (int i = 0; i < 7; i++) {
                    d = d.plusDays(1);
                    datas.add(d);

                }

                data.put("date", datas);

                //utili per visualizzazione messaggio in caso di mancanza di eventi in una specifica settimana
                data.put("data_inizio", dataInizio);
                data.put("data_fine", dataFine);

                //CODICE PER TROVARE EVENTI DAL PERIODO
                res.activate("eventi_corso.ftl.html", data, response);
            } else {
                res.activate("eventi_corso.ftl.html", data, response);
            }
        } catch (DataException ex) {
            handleError("Data access exception: " + ex.getMessage(), request, response);
        }
    }

    private void action_eventi_giornaliero(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        try {
            Map data = new HashMap<>();

            int gruppo_key = SecurityHelpers.checkNumeric(request.getParameter("IDgruppo"));
            List<Aula> aule = ((AuleWebDataLayer) request.getAttribute("datalayer")).getAulaDAO().getAuleByGruppoID(gruppo_key);

            data.put("select_button", 1);
            data.put("IDgruppo", gruppo_key);

            data.put("aule", aule);
            data.put("outline_tpl", "outline_with_select_without_login.ftl.html");

            TemplateResult res = new TemplateResult(getServletContext());

            LocalDate giorno = LocalDate.parse(request.getParameter("data"));

            List<Evento> eventi = new ArrayList();
            for (Aula aula : aule) {
                eventi.addAll(((AuleWebDataLayer) request.getAttribute("datalayer")).getEventoDAO().getEventoInADayByAula(aula, giorno));
                List<EventoRicorrente> eventiRicorrenti = ((AuleWebDataLayer) request.getAttribute("datalayer")).getEventoRicorrenteDAO().getEventiRicorrentiByDataAndAula(giorno, aula);
                for (EventoRicorrente ev : eventiRicorrenti) {
                    Evento e = ev.getEvento();
                    if (e != null) {
                        e.setDataEvento(ev.getDataEvento());
                        eventi.add(e);
                    }
                }
            }

            Collections.sort(eventi, new EventoComparator());
            data.put("eventi", eventi);

            //utili per visualizzazione messaggio in caso di mancanza di eventi in uno specifico giorno
            data.put("data", giorno);

            //CODICE PER TROVARE EVENTI DAL GIORNO
            res.activate("eventi_giornalieri.ftl.html", data, response);

        } catch (DataException ex) {
            handleError("Data access exception: " + ex.getMessage(), request, response);
        }
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            if (request.getParameter("data") != null) {
                action_eventi_giornaliero(request, response);
            } else if (request.getParameter("IDaula") != null) {
                action_eventi_aula(request, response);
            } else if (request.getParameter("IDcorso") != null) {
                action_eventi_corso(request, response);
            } else {
                action_default(request, response);
            }

        } catch (IOException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
