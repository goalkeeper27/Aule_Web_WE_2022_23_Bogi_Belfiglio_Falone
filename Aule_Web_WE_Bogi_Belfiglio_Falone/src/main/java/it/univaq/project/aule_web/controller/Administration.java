/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package it.univaq.project.aule_web.controller;

import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.project.aule_web.data.dao.impl.AttrezzaturaProxy;
import it.univaq.project.aule_web.data.dao.impl.AulaProxy;
import it.univaq.project.aule_web.data.dao.impl.AuleWebDataLayer;
import it.univaq.project.aule_web.data.dao.impl.GruppoProxy;
import it.univaq.project.aule_web.data.impl.AulaImpl;
import it.univaq.project.aule_web.data.model.Attrezzatura;
import it.univaq.project.aule_web.data.model.Aula;
import it.univaq.project.aule_web.data.model.Gruppo;
import it.univaq.project.aule_web.framework.data.DataException;
import it.univaq.project.aule_web.framework.result.TemplateResult;
import it.univaq.project.aule_web.framework.security.SecurityHelpers;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author acer
 */
public class Administration extends AuleWebBaseController {

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        Map data = new HashMap<>();
        data.put("username", SecurityHelpers.checkSession(request).getAttribute("username"));
        data.put("outline_tpl", "outline_with_login.ftl.html");

        TemplateResult res = new TemplateResult(getServletContext());
        res.activate("", data, response);
    }

    private void action_aule(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        Map data = new HashMap<>();
        data.put("username", SecurityHelpers.checkSession(request).getAttribute("username"));
        data.put("outline_tpl", "");
        data.put("gruppi", ((AuleWebDataLayer) request.getAttribute("datalayer")).getGruppoDAO().getAllGruppi());
        data.put("tipi_gruppo", ((AuleWebDataLayer) request.getAttribute("datalayer")).getGruppoDAO().getTipiGruppo());
        data.put("attrezzature", ((AuleWebDataLayer) request.getAttribute("datalayer")).getAttrezzaturaDAO().getAttrezzaturaDisponibile());
        data.put("responsabili", ((AuleWebDataLayer) request.getAttribute("datalayer")).getResponsabileDAO().getAllResponsabili());

        TemplateResult res = new TemplateResult(getServletContext());
        res.activate("aule_administration.ftl.html", data, response);
    }
    
    private void action_gruppi(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        Map data = new HashMap<>();
        data.put("username", SecurityHelpers.checkSession(request).getAttribute("username"));
        data.put("outline_tpl", "");
        
        
        TemplateResult res = new TemplateResult(getServletContext());
        res.activate("gruppi_administration.ftl.html", data, response);
    }

    private void action_insert_aula(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        Aula aula = ((AuleWebDataLayer) request.getAttribute("datalayer")).getAulaDAO().createAula();
        aula.setKey(null);
        aula.setNome(request.getParameter("nome"));
        aula.setLuogo(request.getParameter("via") +","+ request.getParameter("civico"));
        aula.setEdificio(request.getParameter("edificio"));
        aula.setPiano(Integer.parseInt(request.getParameter("piano")));
        aula.setCapienza(Integer.parseInt(request.getParameter("capienza")));
        aula.setNumeroPreseElettriche(Integer.parseInt(request.getParameter("prese_elettriche")));
        aula.setNumeroPreseDiRete(Integer.parseInt(request.getParameter("prese_di_rete")));
        aula.setNoteGeneriche(request.getParameter("note"));
        ((AulaProxy) aula).setResponsabileKey(Integer.parseInt(request.getParameter(("responsabile"))));

        List<Integer> gruppi_keys = new ArrayList<>();
        List<Attrezzatura> attrezzature = new ArrayList<>();
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String par = params.nextElement();
            if (par.startsWith("gruppo")) {
                int gk = Integer.parseInt(request.getParameter(par));
                gruppi_keys.add(gk);
            }
            else if(par.startsWith("attrezzatura")){
                Attrezzatura attrezzatura = ((AuleWebDataLayer) request.getAttribute("datalayer")).getAttrezzaturaDAO().getAttrezzatura(Integer.parseInt(request.getParameter(par)));
                attrezzature.add(attrezzatura);
            }
        }
       
        int aula_key = ((AuleWebDataLayer) request.getAttribute("datalayer")).getAulaDAO().storeAula(aula, gruppi_keys);
        
        for(Attrezzatura attrezzatura: attrezzature){
               ((AttrezzaturaProxy)attrezzatura).setAulaKey(aula_key);
               ((AuleWebDataLayer) request.getAttribute("datalayer")).getAttrezzaturaDAO().storeAttrezzatura(attrezzatura);
        }

        Map data = new HashMap<>();
        data.put("username", SecurityHelpers.checkSession(request).getAttribute("username"));
        data.put("outline_tpl", "outline_with_login.ftl.html");
        data.put("attrezzature", attrezzature);
        data.put("aula", aula);
        data.put("mex", "inserimento aula avvenuto con successo");
        TemplateResult res = new TemplateResult(getServletContext());
        res.activate("", data, response);

    }

    
    private void action_insert_gruppo(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        Gruppo gruppo = ((AuleWebDataLayer) request.getAttribute("datalayer")).getGruppoDAO().createGruppo();
        gruppo.setKey(null);
        gruppo.setNome(request.getParameter("nome"));
        
        gruppo.setTipoGruppo(request.getParameter("tipo"));
        gruppo.setDescrizione(request.getParameter("descrizione"));
           

        Map data = new HashMap<>();
        data.put("username", SecurityHelpers.checkSession(request).getAttribute("username"));
        data.put("outline_tpl", "outline_with_login.ftl.html");
        data.put("gruppo", gruppo);
        data.put("mex", "inserimento gruppo avvenuto con successo");
        TemplateResult res = new TemplateResult(getServletContext());
        res.activate("", data, response);

    }
    private void action_uno(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        Map data = new HashMap<>();
        data.put("username", SecurityHelpers.checkSession(request).getAttribute("username"));
        data.put("outline_tpl", "");
        data.put("tipi_gruppo", ((AuleWebDataLayer) request.getAttribute("datalayer")).getGruppoDAO().getTipiGruppo());
        data.put("gruppi", ((AuleWebDataLayer) request.getAttribute("datalayer")).getGruppoDAO().getAllGruppi());
        TemplateResult res = new TemplateResult(getServletContext());
        res.activate("gruppi.ftl.html", data, response);
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
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        try {
            if (SecurityHelpers.checkSession(request) != null) {
                if (request.getParameter("operation") != null) {
                    switch (Integer.parseInt(request.getParameter("operation"))) {
                        case 1:
                            action_uno(request, response);
                            break;
                        case 2:
                            action_aule(request, response);
                            break;
                        case 3:
                            action_gruppi(request, response);
                            break;
                        default:
                            break;
                    }
                } else if (request.getParameter("insert_aula") != null) {
                    action_insert_aula(request, response);
                } else if (request.getParameter("insert_gruppo") != null) {
                    action_insert_gruppo(request, response);
                }else {
                    action_default(request, response);
                }
            } else {
                response.sendRedirect("homepage");
            }

        } catch (IOException | TemplateManagerException ex) {
            handleError(ex, request, response);
        } catch (DataException ex) {
            Logger.getLogger(Administration.class.getName()).log(Level.SEVERE, null, ex);
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
