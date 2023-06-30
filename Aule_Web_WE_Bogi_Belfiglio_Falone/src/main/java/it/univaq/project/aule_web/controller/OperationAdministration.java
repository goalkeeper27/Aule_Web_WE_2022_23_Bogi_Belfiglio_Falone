/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package it.univaq.project.aule_web.controller;

import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.project.aule_web.data.dao.impl.AuleWebDataLayer;
import it.univaq.project.aule_web.data.model.Aula;
import it.univaq.project.aule_web.data.model.Evento;
import it.univaq.project.aule_web.data.model.Gruppo;
import it.univaq.project.aule_web.framework.data.DataException;
import it.univaq.project.aule_web.framework.result.TemplateResult;
import it.univaq.project.aule_web.framework.security.SecurityHelpers;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author acer
 */
public class OperationAdministration extends AuleWebBaseController {

    private void action_gruppo(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        Map data = new HashMap<>();
        int gruppo_key = SecurityHelpers.checkNumeric(request.getParameter("IDgruppo"));
        data.put("username", SecurityHelpers.checkSession(request).getAttribute("username"));
        data.put("outline_tpl", "outline_with_login.ftl.html");
        data.put("gruppo", ((AuleWebDataLayer) request.getAttribute("datalayer")).getGruppoDAO().getGruppo(gruppo_key));
        data.put("message", request.getParameter("message"));
        TemplateResult res = new TemplateResult(getServletContext());
        res.activate("operation_administration_response.ftl.html", data, response);
    }

    private void action_aula(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        Map data = new HashMap<>();
        int aula_key = SecurityHelpers.checkNumeric(request.getParameter("IDaula"));
        Aula aula = ((AuleWebDataLayer) request.getAttribute("datalayer")).getAulaDAO().getAula(aula_key);
        data.put("username", SecurityHelpers.checkSession(request).getAttribute("username"));
        data.put("outline_tpl", "outline_with_login.ftl.html");
        data.put("aula", aula);
        data.put("attrezzature", ((AuleWebDataLayer) request.getAttribute("datalayer")).getAttrezzaturaDAO().getAttrezzatureByAula(aula));
        data.put("gruppi", ((AuleWebDataLayer) request.getAttribute("datalayer")).getGruppoDAO().getGruppiByAula(aula));
        data.put("message", request.getParameter("message"));
        TemplateResult res = new TemplateResult(getServletContext());
        res.activate("operation_administration_response.ftl.html", data, response);
    }
    
    private void action_evento(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        Map data = new HashMap<>();
        int evento_key = SecurityHelpers.checkNumeric(request.getParameter("IDevento"));
        Evento evento = ((AuleWebDataLayer) request.getAttribute("datalayer")).getEventoDAO().getEvento(evento_key);
        data.put("username", SecurityHelpers.checkSession(request).getAttribute("username"));
        data.put("outline_tpl", "outline_with_login.ftl.html");
        data.put("evento", evento);
        data.put("message", request.getParameter("message"));
        TemplateResult res = new TemplateResult(getServletContext());
        res.activate("operation_administration_response.ftl.html", data, response);
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
        /*if (request.getParameter("insert_aula") != null) {
                    action_insert_aula(request, response);
                } else if (request.getParameter("modify_aula") != null) {
                    action_modify_aula(request, response);*/
        try {
            if (SecurityHelpers.checkSession(request) != null) {
                if (request.getParameter("IDgruppo") != null) {
                    action_gruppo(request, response);
                } else if (request.getParameter("IDaula") != null) {
                    action_aula(request, response);
                } else if (request.getParameter("IDevento") != null) {
                    action_evento(request, response);
                }
                else{
                    throw new DataException("UNKNOWN ERROR IN ADMINISTRATION OPERATION MANAGEMENT");
                }
            } else {
                response.sendRedirect("login");
            }

        } catch (IOException | TemplateManagerException ex) {
            handleError(ex, request, response);

        } catch (DataException ex) {
            //request.setAttribute("admin", 1);
            handleError(ex, request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
