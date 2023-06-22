/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
*/
package it.univaq.project.aule_web.controller;

 

import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.project.aule_web.data.comparator.EventoComparator;
import it.univaq.project.aule_web.data.dao.impl.AttrezzaturaProxy;
import it.univaq.project.aule_web.data.dao.impl.AulaProxy;
import it.univaq.project.aule_web.data.dao.impl.AuleWebDataLayer;
import it.univaq.project.aule_web.data.dao.impl.EventoProxy;
import it.univaq.project.aule_web.data.dao.impl.GruppoProxy;
import it.univaq.project.aule_web.data.impl.AulaImpl;
import it.univaq.project.aule_web.data.model.Attrezzatura;
import it.univaq.project.aule_web.data.model.Aula;
import it.univaq.project.aule_web.framework.data.DataException;
import it.univaq.project.aule_web.framework.result.TemplateResult;
import it.univaq.project.aule_web.framework.security.SecurityHelpers;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import it.univaq.project.aule_web.data.model.Evento;
import it.univaq.project.aule_web.data.model.EventoRicorrente;
import it.univaq.project.aule_web.data.model.Gruppo;
import it.univaq.project.aule_web.data.model.enumerable.Ricorrenza;
import it.univaq.project.aule_web.data.model.enumerable.Tipologia;
import it.univaq.project.aule_web.framework.data.DataException;
import it.univaq.project.aule_web.framework.result.CSVResult;
import it.univaq.project.aule_web.framework.result.StreamResult;
import it.univaq.project.aule_web.framework.result.TemplateResult;
import it.univaq.project.aule_web.framework.security.SecurityHelpers;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.security.MessageDigest;


import java.security.MessageDigest;

import java.time.LocalDate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


/**
 *
 * @author acer
 */

@MultipartConfig
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
        data.put("aule", ((AuleWebDataLayer) request.getAttribute("datalayer")).getAulaDAO().getAllAule());
        data.put("mex", null);
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
        aula.setLuogo(request.getParameter("via") + "," + request.getParameter("civico"));
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
            } else if (par.startsWith("attrezzatura")) {
                Attrezzatura attrezzatura = ((AuleWebDataLayer) request.getAttribute("datalayer")).getAttrezzaturaDAO().getAttrezzatura(Integer.parseInt(request.getParameter(par)));
                attrezzature.add(attrezzatura);
            }
        }

 

        int aula_key = ((AuleWebDataLayer) request.getAttribute("datalayer")).getAulaDAO().storeAula(aula, gruppi_keys);

 

        for (Attrezzatura attrezzatura : attrezzature) {
            ((AttrezzaturaProxy) attrezzatura).setAulaKey(aula_key);
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
    
    private void action_import_aula(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException, TemplateManagerException, DataException {
        Part file_to_upload = request.getPart("file_to_upload");
        
        //create a file (with a unique name) and copy the uploaded file to it
        //creiamo un nuovo file (con nome univoco) e copiamoci il file scaricato
        File uploaded_file = File.createTempFile("import_","",new File(getServletContext().getRealPath("CSV")));
        try ( InputStream is = file_to_upload.getInputStream();  OutputStream os = new FileOutputStream(uploaded_file)) {
            byte[] buffer = new byte[1024];
            int read;
            while ((read = is.read(buffer)) > 0) {
                os.write(buffer, 0, read);
            }
        }
        Map data = new HashMap<>();
        data.put("username", SecurityHelpers.checkSession(request).getAttribute("username"));
        data.put("outline_tpl", "outline_with_login.ftl.html");
        data.put("mex","aoooooooooooooooo");
        Map<String,String> input = CSVResult.readCSVAulaFile(uploaded_file);
        Aula aula = ((AuleWebDataLayer)request.getAttribute("datalayer")).getAulaDAO().createAula();
        aula.setNome(input.get("nome"));
        aula.setLuogo(input.get("luogo"));
        aula.setEdificio(input.get("edificio"));
        aula.setPiano(SecurityHelpers.checkNumeric(input.get("piano")));
        aula.setCapienza(SecurityHelpers.checkNumeric(input.get("capienza")));
        aula.setNumeroPreseElettriche(SecurityHelpers.checkNumeric(input.get("prese_elettriche")));
        aula.setNumeroPreseDiRete(SecurityHelpers.checkNumeric(input.get("prese_di_rete")));
        aula.setNoteGeneriche(input.get("note"));
        TemplateResult res = new TemplateResult(getServletContext());
        res.activate("", data, response);
    }

 /*   private void action_import_aula(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException, TemplateManagerException, DataException {
        Part file_to_upload = request.getPart("file_to_upload");

        //create a file (with a unique name) and copy the uploaded file to it
        //creiamo un nuovo file (con nome univoco) e copiamoci il file scaricato
        File uploaded_file = File.createTempFile("import_","",new File(getServletContext().getRealPath("CSV")));
        try ( InputStream is = file_to_upload.getInputStream();  OutputStream os = new FileOutputStream(uploaded_file)) {
            byte[] buffer = new byte[1024];
            int read;
            while ((read = is.read(buffer)) > 0) {
                os.write(buffer, 0, read);
            }
        }
        Map data = new HashMap<>();
        data.put("username", SecurityHelpers.checkSession(request).getAttribute("username"));
        data.put("outline_tpl", "outline_with_login.ftl.html");
        data.put("mex","aoooooooooooooooo");
        Map<String,String> input = CSVResult.readCSVAulaFile(uploaded_file);
        Aula aula = ((AuleWebDataLayer)request.getAttribute("datalayer")).getAulaDAO().createAula();
        aula.setNome(input.get("nome"));
        aula.setLuogo(input.get("luogo"));
        aula.setEdificio(input.get("edificio"));
        aula.setPiano(SecurityHelpers.checkNumeric(input.get("piano")));
        aula.setCapienza(SecurityHelpers.checkNumeric(input.get("capienza")));
        aula.setNumeroPreseElettriche(SecurityHelpers.checkNumeric(input.get("prese_elettriche")));
        aula.setNumeroPreseDiRete(SecurityHelpers.checkNumeric(input.get("prese_di_rete")));
        aula.setNoteGeneriche(input.get("note"));
        TemplateResult res = new TemplateResult(getServletContext());
        res.activate("", data, response);
    }*/

 


    private void action_modify_aula(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        Aula aula = ((AuleWebDataLayer) request.getAttribute("datalayer")).getAulaDAO().getAula(SecurityHelpers.checkNumeric(request.getParameter("IDaula")));

 

        if (!aula.getNome().equals(request.getParameter("nome"))) {
            aula.setNome(request.getParameter("nome"));
        }
        if (!aula.getLuogo().equals(request.getParameter("via") + "," + request.getParameter("civico"))) {
            aula.setLuogo(request.getParameter("via") + "," + request.getParameter("civico"));
        }
        if (!aula.getEdificio().equals(request.getParameter("edificio"))) {
            aula.setEdificio(request.getParameter("edificio"));
        }
        if (aula.getPiano() != SecurityHelpers.checkNumeric(request.getParameter("piano"))) {
            aula.setPiano(SecurityHelpers.checkNumeric(request.getParameter("piano")));
        }
        if (aula.getCapienza() != SecurityHelpers.checkNumeric(request.getParameter("capienza"))) {
            aula.setCapienza(SecurityHelpers.checkNumeric(request.getParameter("capienza")));
        }
        if (aula.getNumeroPreseElettriche() != SecurityHelpers.checkNumeric(request.getParameter("prese_elettriche"))) {
            aula.setNumeroPreseElettriche(SecurityHelpers.checkNumeric(request.getParameter("prese_elettriche")));
        }
        if (aula.getNumeroPreseDiRete() != SecurityHelpers.checkNumeric(request.getParameter("prese_di_rete"))) {
            aula.setNumeroPreseDiRete(SecurityHelpers.checkNumeric(request.getParameter("prese_di_rete")));
        }
        if (!aula.getNoteGeneriche().equals(request.getParameter("note"))) {
            aula.setNoteGeneriche(request.getParameter("note"));
        }
        if (aula instanceof AulaProxy) {
            if (((AulaProxy) aula).getResponsabileKey() != SecurityHelpers.checkNumeric(request.getParameter("responsabile"))) {
                ((AulaProxy) aula).setResponsabileKey(SecurityHelpers.checkNumeric(request.getParameter("responsabile")));
            }
        }

 

        List<Integer> gruppi_keys = new ArrayList<>();
        List<Integer> attr_keys = new ArrayList<>();
        //List<Boolean> a = new ArrayList();
        List<Attrezzatura> attrezzatureNuove = new ArrayList<>();
        List<Attrezzatura> attrezzatureVecchie = ((AuleWebDataLayer) request.getAttribute("datalayer")).getAttrezzaturaDAO().getAttrezzatureByAula(aula);
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String par = params.nextElement();
            if (par.startsWith("gruppo")) {
                int gk = SecurityHelpers.checkNumeric(request.getParameter(par));
                if (gk != 0) {
                    gruppi_keys.add(gk);
                }
            } else if (par.startsWith("attrezzatura")) {
                Attrezzatura attrezzatura = ((AuleWebDataLayer) request.getAttribute("datalayer")).getAttrezzaturaDAO().getAttrezzatura(Integer.parseInt(request.getParameter(par)));
                attrezzatureNuove.add(attrezzatura);
                attr_keys.add(attrezzatura.getKey());
            }
        }

 

        int aula_key = ((AuleWebDataLayer) request.getAttribute("datalayer")).getAulaDAO().storeAula(aula, gruppi_keys);

 

        //aggiornamento delle nuove attrezzature
        for (Attrezzatura attrezzatura : attrezzatureNuove) {
            //if (((AttrezzaturaProxy) attrezzatura).getAulaKey() != aula_key) {
            ((AttrezzaturaProxy) attrezzatura).setAulaKey(aula_key);
            ((AuleWebDataLayer) request.getAttribute("datalayer")).getAttrezzaturaDAO().storeAttrezzatura(attrezzatura);
            //}
        }

 

        //aggiornamento vecchie attrezzature(non saranno più attrezzature di questa aula)
        for (Attrezzatura attrezzatura : attrezzatureVecchie) {
            if (!attr_keys.contains(attrezzatura.getKey())) {
                ((AttrezzaturaProxy) attrezzatura).setAulaKey(0);
                ((AuleWebDataLayer) request.getAttribute("datalayer")).getAttrezzaturaDAO().storeAttrezzatura(attrezzatura);
            }
        }

 

        Map data = new HashMap<>();
        data.put("username", SecurityHelpers.checkSession(request).getAttribute("username"));
        data.put("outline_tpl", "outline_with_login.ftl.html");
        data.put("attrezzature", attrezzatureNuove);
        data.put("aula", aula);
        data.put("mex", "aggiornamento aula avvenuto con successo");
        TemplateResult res = new TemplateResult(getServletContext());
        res.activate("", data, response);

 

    }

 

 

    private void action_export_aula(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        try {
            Map data = new HashMap<>();
            StreamResult file_result = new StreamResult(getServletContext());
            TemplateResult result = new TemplateResult(getServletContext());
            Aula aula = ((AuleWebDataLayer) request.getAttribute("datalayer")).getAulaDAO().getAula(SecurityHelpers.checkNumeric(request.getParameter("IDaula")));
            //data.put("IDaula", gruppo_key);
            data.put("outline_tpl", "outline_with_login.ftl.html");

 

            List<Attrezzatura> attrezzature = ((AuleWebDataLayer) request.getAttribute("datalayer")).getAttrezzaturaDAO().getAttrezzatureByAula(aula);

 

            List<Gruppo> gruppi = ((AuleWebDataLayer) request.getAttribute("datalayer")).getGruppoDAO().getGruppiByAula(aula);

 

            File in = CSVResult.createCSVAulaFile(aula, attrezzature, gruppi, getServletContext().getRealPath("CSV") + File.separatorChar + "aula.csv");

 

            file_result.setResource(in);
            file_result.activate(request, response);
            result.activate("aule_administration.ftl.html", data, response);

 

        } catch (DataException ex) {
            handleError("Data access exception: " + ex.getMessage(), request, response);
        }
    }

 

    private void action_modify_table(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        Map data = new HashMap<>();
        data.put("username", SecurityHelpers.checkSession(request).getAttribute("username"));
        data.put("outline_tpl", "");
        data.put("aule", ((AuleWebDataLayer) request.getAttribute("datalayer")).getAulaDAO().getAuleByPartialName(request.getParameter("search")));
        //data.put("search", request.getParameter("search"));
        TemplateResult res = new TemplateResult(getServletContext());
        res.activate("table_aule_research.ftl.html", data, response);
    }

 

    private void action_view_aula(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        Map data = new HashMap<>();
        data.put("username", SecurityHelpers.checkSession(request).getAttribute("username"));
        data.put("outline_tpl", "");
        int aula_key = SecurityHelpers.checkNumeric(request.getParameter("IDaula"));
        Aula aula = ((AuleWebDataLayer) request.getAttribute("datalayer")).getAulaDAO().getAula(aula_key);
        data.put("aula", aula);
        List<Attrezzatura> attrezzature = ((AuleWebDataLayer) request.getAttribute("datalayer")).getAttrezzaturaDAO().getAttrezzatureByAula(aula);
        attrezzature.addAll(((AuleWebDataLayer) request.getAttribute("datalayer")).getAttrezzaturaDAO().getAttrezzaturaDisponibile());
        data.put("attrezzature", attrezzature);
        data.put("gruppi", ((AuleWebDataLayer) request.getAttribute("datalayer")).getGruppoDAO().getAllGruppi());
        data.put("tipi_gruppo", ((AuleWebDataLayer) request.getAttribute("datalayer")).getGruppoDAO().getTipiGruppo());
        data.put("responsabili", ((AuleWebDataLayer) request.getAttribute("datalayer")).getResponsabileDAO().getAllResponsabili());
        TemplateResult res = new TemplateResult(getServletContext());
        res.activate("update_aula.ftl.html", data, response);
    }
    
    private void action_eventi(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        Map data = new HashMap<>();
        data.put("username", SecurityHelpers.checkSession(request).getAttribute("username"));
        data.put("outline_tpl", "");
        
        data.put("corsi", ((AuleWebDataLayer) request.getAttribute("datalayer")).getCorsoDAO().getAllCorsi());
        data.put("aule", ((AuleWebDataLayer) request.getAttribute("datalayer")).getAulaDAO().getAllAule());
        data.put("responsabili", ((AuleWebDataLayer) request.getAttribute("datalayer")).getResponsabileDAO().getAllResponsabili());
        
        TemplateResult res = new TemplateResult(getServletContext());
        res.activate("eventi_administration.ftl.html", data, response);
    }
    
    private void action_insert_evento(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        Evento evento = ((AuleWebDataLayer) request.getAttribute("datalayer")).getEventoDAO().createEvento();
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
        evento.setKey(null);
        evento.setNome(request.getParameter("nome"));
        evento.setDescrizione(request.getParameter("descrizione"));
        int scelta_tipologia = SecurityHelpers.checkNumeric(request.getParameter("tipologia"));
        switch(scelta_tipologia){
            case 1:
                evento.setTipologia(Tipologia.LEZIONE);
                ((EventoProxy) evento).setCorsoKey(SecurityHelpers.checkNumeric(request.getParameter("corsi")));
                break;
            case 2:
                evento.setTipologia(Tipologia.ESAME);
                ((EventoProxy) evento).setCorsoKey(SecurityHelpers.checkNumeric(request.getParameter("corsi")));
                break; 
            case 3:
                evento.setTipologia(Tipologia.SEMINARIO);
                ((EventoProxy) evento).setCorsoKey(0);
                break;
            case 4:
                evento.setTipologia(Tipologia.PARZIALE);
                ((EventoProxy) evento).setCorsoKey(SecurityHelpers.checkNumeric(request.getParameter("corsi")));
                break;
            case 5:
                evento.setTipologia(Tipologia.RIUNIONE);
                ((EventoProxy) evento).setCorsoKey(0);
                break;
            case 6:
                evento.setTipologia(Tipologia.LAUREE);
                ((EventoProxy) evento).setCorsoKey(0);
                break;
            case 7:
                evento.setTipologia(Tipologia.ALTRO);
                ((EventoProxy) evento).setCorsoKey(0);
                break;
        }
        evento.setDataEvento(LocalDate.parse(request.getParameter("data"),formatterDate));
        evento.setOraInizio(LocalTime.parse(request.getParameter("ora_inizio"),formatterTime));
        evento.setOraFine(LocalTime.parse(request.getParameter("ora_fine"),formatterTime));
        int scelta_ricorrenza = SecurityHelpers.checkNumeric(request.getParameter("ricorrenza"));
        switch(scelta_ricorrenza){
            case 8:
                evento.setRicorrenza(Ricorrenza.GIORNALIERA);
                evento.setDataFineRicorrenza(LocalDate.parse(request.getParameter("data_fine_ricorrenza"),formatterDate));
                break;
            case 9:
                evento.setRicorrenza(Ricorrenza.SETTIMANALE);
                evento.setDataFineRicorrenza(LocalDate.parse(request.getParameter("data_fine_ricorrenza"),formatterDate));
                break;
            case 10:
                evento.setRicorrenza(Ricorrenza.MENSILE);
                evento.setDataFineRicorrenza(LocalDate.parse(request.getParameter("data_fine_ricorrenza"),formatterDate));
                break;
            case 11:
                evento.setRicorrenza(Ricorrenza.NESSUNA);
                evento.setDataFineRicorrenza(LocalDate.now());
                break;
        }

        ((EventoProxy) evento).setAulaKey(SecurityHelpers.checkNumeric(request.getParameter("aule")));
        ((EventoProxy) evento).setResponsabileKey(SecurityHelpers.checkNumeric(request.getParameter("responsabile")));

        ((AuleWebDataLayer) request.getAttribute("datalayer")).getEventoDAO().storeEvento(evento);
        
        Map data = new HashMap<>();
        data.put("username", SecurityHelpers.checkSession(request).getAttribute("username"));
        data.put("outline_tpl", "outline_with_login.ftl.html");
        data.put("evento", evento);
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
                            if(request.getParameter("search") != null){
                                action_eventi(request, response);
                            }
                            break;
                        case 2:
                            if (request.getParameter("search") != null) {
                                action_modify_table(request, response);
                            } else if (request.getParameter("IDaula") != null) {
                                action_view_aula(request, response);
                            } else {
                                action_aule(request, response);
                            }
                            break;
                        case 3:
                            action_gruppi(request, response);
                            break;
                        default:
                            break;
                    }
                } else if (request.getParameter("insert_aula") != null) {
                    action_insert_aula(request, response);
                } else if (request.getParameter("modify_aula") != null) {
                    action_modify_aula(request, response);
                } else if (request.getParameter("export_aula") != null) {
                    action_export_aula(request, response);
                } else if(request.getContentType() != null && request.getContentType().startsWith("multipart/form-data")){
                    action_import_aula(request, response);
                }else if (request.getParameter("insert_gruppo") != null) {
                    action_insert_gruppo(request, response);
                }else if (request.getParameter("insert_evento") != null) {
                    action_insert_evento(request, response);
                } else {

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

