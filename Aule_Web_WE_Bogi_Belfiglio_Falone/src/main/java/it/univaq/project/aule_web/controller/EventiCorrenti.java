/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package it.univaq.project.aule_web.controller;

import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.project.aule_web.framework.result.TemplateResult;
import it.univaq.project.aule_web.data.dao.impl.AuleWebDataLayer;
import it.univaq.project.aule_web.data.model.Aula;
import it.univaq.project.aule_web.data.model.Evento;
import it.univaq.project.aule_web.framework.data.DataException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Alberto Bogi
 */
public class EventiCorrenti extends AuleWebBaseController {

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        try {
            Map data = new HashMap<>();
            
            int gruppo_key = Integer.valueOf(request.getParameter("IDgruppo"));
            // lista di tutte le aule del gruppo specificato
            List<Aula> aule = ((AuleWebDataLayer)request.getAttribute("datalayer")).getAulaDAO().getAuleByGruppoID(gruppo_key);
            
            //lista di tutte gli eventi relativi alle aule specificate
            List<Evento> eventi = new ArrayList<>();
            
            for(Aula aula: aule){
                eventi.addAll(((AuleWebDataLayer)request.getAttribute("datalayer")).getEventoDAO().getCurrentEventoByAula(aula));
            }

            data.put("aule", aule);
            data.put("eventi", eventi);
            data.put("outline_tpl", "outline_with_select_without_login.ftl.html");
            data.put("IDgruppo", gruppo_key);
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
