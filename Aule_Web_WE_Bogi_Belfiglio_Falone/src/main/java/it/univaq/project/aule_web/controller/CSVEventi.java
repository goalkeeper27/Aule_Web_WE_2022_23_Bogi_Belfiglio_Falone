/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package it.univaq.project.aule_web.controller;

import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.project.aule_web.data.comparator.EventoComparator;
import it.univaq.project.aule_web.data.dao.impl.AuleWebDataLayer;
import it.univaq.project.aule_web.data.model.Aula;
import it.univaq.project.aule_web.data.model.Evento;
import it.univaq.project.aule_web.data.model.EventoRicorrente;
import it.univaq.project.aule_web.framework.data.DataException;
import it.univaq.project.aule_web.framework.result.CSVResult;
import it.univaq.project.aule_web.framework.result.StreamResult;
import it.univaq.project.aule_web.framework.result.TemplateResult;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CSVEventi extends AuleWebBaseController {

    private void action_download(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        try {
            Map data = new HashMap<>();
            StreamResult file_result = new StreamResult(getServletContext());
            TemplateResult result = new TemplateResult(getServletContext());
            if (request.getParameter("IDgruppo") != null) {
                int gruppo_key = Integer.valueOf(request.getParameter("IDgruppo"));
                data.put("IDgruppo", gruppo_key);
                data.put("outline_tpl","outline_with_select_without_login.ftl.html");
            }
            LocalDate inizio = LocalDate.parse(request.getParameter("data_inizio"));
            LocalDate fine = LocalDate.parse(request.getParameter("data_fine"));
            // lista di tutte le aule del gruppo specificato

            //lista di tutte gli eventi relativi alle aule e corsi specificate
            List<Evento> eventi = ((AuleWebDataLayer) request.getAttribute("datalayer")).getEventoDAO().getEventiByPeriodo(inizio, fine);

            List<EventoRicorrente> eventiRicorrenti = ((AuleWebDataLayer) request.getAttribute("datalayer")).getEventoRicorrenteDAO().getEventiRicorrentiByPeriod(inizio, fine);
            if (eventiRicorrenti != null) {
                for (EventoRicorrente ev : eventiRicorrenti) {
                    Evento e = ev.getEvento();
                    e.setDataEvento(ev.getDataEvento());
                    eventi.add(e);
                }
            }
            
            Collections.sort(eventi, new EventoComparator());

            File in = CSVResult.createCSVFile(eventi, getServletContext().getRealPath("CSV") + File.separatorChar + "eventi.csv");
            
            file_result.setResource(in);
            file_result.activate(request, response);
            result.activate("csv_eventi.ftl.html", data, response);
            

        } catch (DataException ex) {
            handleError("Data access exception: " + ex.getMessage(), request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        Map data = new HashMap<>();
        if (request.getParameter("IDgruppo") != null && !request.getParameter("IDgruppo").isEmpty()) {
            int gruppo_key = Integer.valueOf(request.getParameter("IDgruppo"));
            data.put("IDgruppo", gruppo_key);
            
            data.put("outline_tpl", "outline_with_select_without_login.ftl.html");
        } else {
            data.put("outline_tpl", "outline_without_login.ftl.html");
        }
        TemplateResult res = new TemplateResult(getServletContext());
        res.activate("csv_eventi.ftl.html", data, response);

    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        try {
            if ((request.getParameter("data_inizio") != null) && (request.getParameter("data_fine") != null)) {
                action_download(request, response);
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
