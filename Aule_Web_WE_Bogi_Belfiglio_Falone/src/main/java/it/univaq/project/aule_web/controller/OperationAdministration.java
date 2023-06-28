/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package it.univaq.project.aule_web.controller;

import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.project.aule_web.data.dao.impl.AuleWebDataLayer;
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
            
            if (request.getParameter("IDgruppo") != null) {
                action_gruppo(request, response);
            } /*else if (request.getParameter("modify_gruppo") != null) {
                action_modify_gruppo(request, response);
            } else if (request.getParameter("insert_eventi") != null) {
                    action_insert_evento(request, response);
                } else if (request.getParameter("modify_evento") != null) {
                    action_modify_evento(request, response);
                } else {
                    action_default(request, response);*/


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
