/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.project.aule_web.controller;

import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResult;
import it.univaq.project.aule_web.framework.data.DataException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import it.univaq.project.aule_web.data.dao.impl.AuleWebDataLayer;
import it.univaq.project.aule_web.data.model.Gruppo;

/**
 *
 * @author Stefano Belfiglio
 */
public class FirstPageAula extends AuleWebBaseController {
    
    
    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        try {
            Map data = new HashMap<>();
            String gruppo_key = request.getParameter("IDgruppo");
            data.put("aule", ((AuleWebDataLayer) request.getAttribute("datalayer")).getAulaDAO().getAuleByGruppoID(gruppo_key));
            data.put("outline_tpl", "outline_without_login.ftl.html");
            TemplateResult res = new TemplateResult(getServletContext());
            res.activate("aule.ftl.html",data, response);
        } catch (DataException ex) {
            handleError("Data access exception: " + ex.getMessage(), request, response);
        }
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            action_default(request, response);

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
