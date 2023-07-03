/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package it.univaq.project.aule_web.controller;

import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.project.aule_web.data.dao.impl.AttrezzaturaProxy;
import it.univaq.project.aule_web.data.dao.impl.AulaProxy;
import it.univaq.project.aule_web.data.dao.impl.AuleWebDataLayer;
import it.univaq.project.aule_web.data.dao.impl.EventoProxy;
import it.univaq.project.aule_web.data.model.Attrezzatura;
import it.univaq.project.aule_web.data.model.Aula;
import it.univaq.project.aule_web.data.model.Corso;
import it.univaq.project.aule_web.data.model.Evento;
import it.univaq.project.aule_web.data.model.Gruppo;
import it.univaq.project.aule_web.data.model.Responsabile;
import it.univaq.project.aule_web.data.model.enumerable.Ricorrenza;
import it.univaq.project.aule_web.data.model.enumerable.TipoLaurea;
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
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig
public class Administration extends AuleWebBaseController {

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
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

    private void action_insert_aula(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        Aula aula = ((AuleWebDataLayer) request.getAttribute("datalayer")).getAulaDAO().createAula();
        aula.setKey(null);
        aula.setNome(request.getParameter("nome").toUpperCase());
        aula.setLuogo((request.getParameter("via") + "," + request.getParameter("civico")).toUpperCase());
        aula.setEdificio(request.getParameter("edificio").toUpperCase());
        aula.setPiano(SecurityHelpers.checkNumeric(request.getParameter("piano")));
        aula.setCapienza(SecurityHelpers.checkNumeric(request.getParameter("capienza")));
        aula.setNumeroPreseElettriche(SecurityHelpers.checkNumeric(request.getParameter("prese_elettriche")));
        aula.setNumeroPreseDiRete(SecurityHelpers.checkNumeric(request.getParameter("prese_di_rete")));
        aula.setNoteGeneriche(request.getParameter("note"));
        ((AulaProxy) aula).setResponsabileKey(SecurityHelpers.checkNumeric(request.getParameter(("responsabile"))));

        List<Integer> gruppi_keys = new ArrayList<>();
        List<Attrezzatura> attrezzature = new ArrayList<>();
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String par = params.nextElement();
            if (par.startsWith("gruppo")) {
                int gk = SecurityHelpers.checkNumeric(request.getParameter(par));
                gruppi_keys.add(gk);
            } else if (par.startsWith("attrezzatura")) {
                Attrezzatura attrezzatura = ((AuleWebDataLayer) request.getAttribute("datalayer")).getAttrezzaturaDAO().getAttrezzatura(SecurityHelpers.checkNumeric(request.getParameter(par)));
                attrezzature.add(attrezzatura);
            }
        }

        int aula_key = ((AuleWebDataLayer) request.getAttribute("datalayer")).getAulaDAO().storeAula(aula, gruppi_keys);

        for (Attrezzatura attrezzatura : attrezzature) {
            ((AttrezzaturaProxy) attrezzatura).setAulaKey(aula_key);
            ((AuleWebDataLayer) request.getAttribute("datalayer")).getAttrezzaturaDAO().storeAttrezzatura(attrezzatura);
        }

        response.sendRedirect("operation?IDaula=" + aula_key + "&message=" + URLEncoder.encode("inserimento aula avvenuto con successo", "utf-8"));

    }

    private void action_import_aula(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        Part file_to_upload = request.getPart("file_to_upload");

        //create a file (with a unique name) and copy the uploaded file to it
        //creiamo un nuovo file (con nome univoco) e copiamoci il file scaricato
        File uploaded_file = new File(getServletContext().getRealPath("CSV") + File.separatorChar + "import_aula.csv");

        try ( InputStream is = file_to_upload.getInputStream();  OutputStream os = new FileOutputStream(uploaded_file)) {
            byte[] buffer = new byte[1024];
            int read;
            while ((read = is.read(buffer)) > 0) {
                os.write(buffer, 0, read);
            }
        }

        Map<String, String> input = CSVResult.readCSVAulaFile(uploaded_file);
        uploaded_file.deleteOnExit();

        Aula aula = ((AuleWebDataLayer) request.getAttribute("datalayer")).getAulaDAO().createAula();
        aula.setNome(input.get("nome").toUpperCase());
        aula.setLuogo(input.get("luogo").toUpperCase());
        aula.setEdificio(input.get("edificio").toUpperCase());
        aula.setPiano(SecurityHelpers.checkNumeric(input.get("piano")));
        aula.setCapienza(SecurityHelpers.checkNumeric(input.get("capienza")));
        aula.setNumeroPreseElettriche(SecurityHelpers.checkNumeric(input.get("prese_elettriche")));
        aula.setNumeroPreseDiRete(SecurityHelpers.checkNumeric(input.get("prese_di_rete")));
        aula.setNoteGeneriche(input.get("note"));

        List<Attrezzatura> attrezzatureImport = new ArrayList();
        List<Attrezzatura> attrezzatureDisponibili = ((AuleWebDataLayer) request.getAttribute("datalayer")).getAttrezzaturaDAO().getAttrezzaturaDisponibile();
        String[] attr = input.get("attrezzature").split(",");
        for (int i = 0; i < attr.length; i++) {
            Attrezzatura attrezzatura = ((AuleWebDataLayer) request.getAttribute("datalayer")).getAttrezzaturaDAO().getAttrezzaturaDisponibileByNome(attr[i]);
            if (attrezzatura != null) {
                attrezzatureImport.add(attrezzatura);
                Iterator<Attrezzatura> iterator = attrezzatureDisponibili.iterator();
                while (iterator.hasNext()) {
                    Attrezzatura attrezzaturaIterator = iterator.next();
                    if (attrezzaturaIterator.getKey() == attrezzatura.getKey()) {
                        iterator.remove();
                        break;
                    }
                }
            }

        }

        Responsabile responsabile = ((AuleWebDataLayer) request.getAttribute("datalayer")).getResponsabileDAO().getResponsabileByEmail(input.get("responsabile"));
        aula.setResponsabile(responsabile);

        List<Gruppo> gruppiImport = new ArrayList();
        List<Gruppo> gruppiTotali = ((AuleWebDataLayer) request.getAttribute("datalayer")).getGruppoDAO().getAllGruppi();

        String[] gr = input.get("gruppi").split(",");
        for (int i = 0; i < gr.length; i++) {
            String[] g = gr[i].split(";");
            Gruppo gruppo = ((AuleWebDataLayer) request.getAttribute("datalayer")).getGruppoDAO().getGruppoByTipoAndNome(g[0], g[1]);
            if (gruppo != null) {
                gruppiImport.add(gruppo);
                Iterator<Gruppo> iterator = gruppiTotali.iterator();
                while (iterator.hasNext()) {
                    Gruppo gruppoIterator = iterator.next();
                    if (gruppoIterator.getKey() == gruppo.getKey()) {
                        iterator.remove();
                        break;
                    }
                }
            }
        }

        Map data = new HashMap<>();
        data.put("username", SecurityHelpers.checkSession(request).getAttribute("username"));
        data.put("outline_tpl", "outline_with_login.ftl.html");
        data.put("gruppi_totali", gruppiTotali);
        data.put("gruppi_import", gruppiImport);
        data.put("tipi_gruppo", ((AuleWebDataLayer) request.getAttribute("datalayer")).getGruppoDAO().getTipiGruppo());
        data.put("attrezzature_disponibili", attrezzatureDisponibili);
        data.put("attrezzature_import", attrezzatureImport);
        data.put("responsabili", ((AuleWebDataLayer) request.getAttribute("datalayer")).getResponsabileDAO().getAllResponsabili());
        data.put("aula", aula);
        data.put("aule", ((AuleWebDataLayer) request.getAttribute("datalayer")).getAulaDAO().getAllAule());
        data.put("import", 1);
        TemplateResult res = new TemplateResult(getServletContext());
        res.activate("aule_administration.ftl.html", data, response);

    }

    private void action_modify_aula(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        Aula aula = ((AuleWebDataLayer) request.getAttribute("datalayer")).getAulaDAO().getAula(SecurityHelpers.checkNumeric(request.getParameter("IDaula")));

        if (!aula.getNome().equals(request.getParameter("nome"))) {
            aula.setNome(request.getParameter("nome").toUpperCase());
        }
        if (!aula.getLuogo().equals(request.getParameter("via") + "," + request.getParameter("civico"))) {
            aula.setLuogo((request.getParameter("via") + "," + request.getParameter("civico")).toUpperCase());
        }
        if (!aula.getEdificio().equals(request.getParameter("edificio"))) {
            aula.setEdificio(request.getParameter("edificio").toUpperCase());
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
                Attrezzatura attrezzatura = ((AuleWebDataLayer) request.getAttribute("datalayer")).getAttrezzaturaDAO().getAttrezzatura(SecurityHelpers.checkNumeric(request.getParameter(par)));
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

        response.sendRedirect("operation?IDaula=" + aula_key + "&message=" + URLEncoder.encode("aggiornamento aula avvenuto con successo", "utf-8"));

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

    private void action_modify_table_aula(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        Map data = new HashMap<>();
        data.put("username", SecurityHelpers.checkSession(request).getAttribute("username"));
        data.put("outline_tpl", "");
        if (request.getParameter("search").isEmpty()) {
            data.put("aule", ((AuleWebDataLayer) request.getAttribute("datalayer")).getAulaDAO().getAllAule());
        } else {
            data.put("aule", ((AuleWebDataLayer) request.getAttribute("datalayer")).getAulaDAO().getAuleByPartialName(request.getParameter("search")));
        }
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

    private void action_gruppi(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        Map data = new HashMap<>();
        data.put("username", SecurityHelpers.checkSession(request).getAttribute("username"));
        data.put("outline_tpl", "");
        data.put("gruppi", ((AuleWebDataLayer) request.getAttribute("datalayer")).getGruppoDAO().getAllGruppi());

        TemplateResult res = new TemplateResult(getServletContext());
        res.activate("gruppi_administration.ftl.html", data, response);
    }

    private void action_insert_gruppo(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        Map data = new HashMap<>();
        data.put("username", SecurityHelpers.checkSession(request).getAttribute("username"));

        data.put("outline_tpl", "outline_with_login.ftl.html");
        Gruppo gruppo = ((AuleWebDataLayer) request.getAttribute("datalayer")).getGruppoDAO().createGruppo();
        gruppo.setKey(null);
        gruppo.setNome(request.getParameter("nome").toUpperCase());
        gruppo.setTipoGruppo(request.getParameter("tipo").toUpperCase());
        gruppo.setDescrizione(request.getParameter("descrizione"));

        ((AuleWebDataLayer) request.getAttribute("datalayer")).getGruppoDAO().storeGruppo(gruppo);

        response.sendRedirect("operation?IDgruppo=" + gruppo.getKey() + "&message=" + URLEncoder.encode("inserimento gruppo avvenuto con successo", "utf-8"));

    }

    private void action_modify_gruppo(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        Gruppo gruppo = ((AuleWebDataLayer) request.getAttribute("datalayer")).getGruppoDAO().getGruppo(SecurityHelpers.checkNumeric(request.getParameter("IDgruppo")));

        if (!gruppo.getNome().equals(request.getParameter("nome"))) {
            gruppo.setNome(request.getParameter("nome").toUpperCase());
        }
        if (!gruppo.getTipoGruppo().equals(request.getParameter("tipo"))) {
            gruppo.setTipoGruppo(request.getParameter("tipo").toUpperCase());
        }
        if (!gruppo.getDescrizione().equals(request.getParameter("descrizione"))) {
            gruppo.setDescrizione(request.getParameter("descrizione"));
        }

        ((AuleWebDataLayer) request.getAttribute("datalayer")).getGruppoDAO().storeGruppo(gruppo);

        response.sendRedirect("operation?IDgruppo=" + gruppo.getKey() + "&message=" + URLEncoder.encode("aggiornamento gruppo avvenuto con successo", "utf-8"));

    }

    private void action_modify_table_gruppo(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        Map data = new HashMap<>();
        data.put("username", SecurityHelpers.checkSession(request).getAttribute("username"));
        data.put("outline_tpl", "");

        if (request.getParameter("search").isEmpty()) {
            data.put("gruppi", ((AuleWebDataLayer) request.getAttribute("datalayer")).getGruppoDAO().getAllGruppi());
        } else {
            data.put("gruppi", ((AuleWebDataLayer) request.getAttribute("datalayer")).getGruppoDAO().getGruppiByPartialName(request.getParameter("search")));
        }

        TemplateResult res = new TemplateResult(getServletContext());
        res.activate("table_gruppi_research.ftl.html", data, response);
    }

    private void action_view_gruppo(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        Map data = new HashMap<>();
        data.put("username", SecurityHelpers.checkSession(request).getAttribute("username"));
        data.put("outline_tpl", "");
        int gruppo_key = SecurityHelpers.checkNumeric(request.getParameter("IDgruppo"));
        Gruppo gruppo = ((AuleWebDataLayer) request.getAttribute("datalayer")).getGruppoDAO().getGruppo(gruppo_key);
        List<String> tipi = ((AuleWebDataLayer) request.getAttribute("datalayer")).getGruppoDAO().getTipiGruppo();
        data.put("tipi", tipi);
        data.put("gruppo", gruppo);
        TemplateResult res = new TemplateResult(getServletContext());
        res.activate("update_gruppo.ftl.html", data, response);
    }

    private void action_select_tipo_gruppo(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        Map data = new HashMap<>();
        data.put("username", SecurityHelpers.checkSession(request).getAttribute("username"));
        data.put("outline_tpl", "");
        if (SecurityHelpers.checkNumeric(request.getParameter("select_tipo_gruppo")) == 0) {
            List<String> tipi = ((AuleWebDataLayer) request.getAttribute("datalayer")).getGruppoDAO().getTipiGruppo();
            data.put("tipi", tipi);
            data.put("select", 0);
        } else if (SecurityHelpers.checkNumeric(request.getParameter("select_tipo_gruppo")) == 1) {
            data.put("select", 1);
        }
        TemplateResult res = new TemplateResult(getServletContext());
        res.activate("select_tipo_gruppo.ftl.html", data, response);
    }

    private void action_eventi(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        Map data = new HashMap<>();
        data.put("username", SecurityHelpers.checkSession(request).getAttribute("username"));
        data.put("outline_tpl", "");

        data.put("corsi", ((AuleWebDataLayer) request.getAttribute("datalayer")).getCorsoDAO().getAllCorsi());
        data.put("aule", ((AuleWebDataLayer) request.getAttribute("datalayer")).getAulaDAO().getAllAule());
        data.put("eventi", ((AuleWebDataLayer) request.getAttribute("datalayer")).getEventoDAO().getAllEventi());
        data.put("responsabili", ((AuleWebDataLayer) request.getAttribute("datalayer")).getResponsabileDAO().getAllResponsabili());

        TemplateResult res = new TemplateResult(getServletContext());
        res.activate("eventi_administration.ftl.html", data, response);
    }

    private void action_insert_evento(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        Evento evento = ((AuleWebDataLayer) request.getAttribute("datalayer")).getEventoDAO().createEvento();
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
        evento.setKey(null);
        evento.setNome(request.getParameter("nome").toUpperCase());
        evento.setDescrizione(request.getParameter("descrizione"));
        int scelta_tipologia = SecurityHelpers.checkNumeric(request.getParameter("tipologia"));
        switch (scelta_tipologia) {
            case 1:
                evento.setTipologia(Tipologia.LEZIONE);
                ((EventoProxy) evento).setCorsoKey(SecurityHelpers.checkNumeric(request.getParameter("corso")));
                break;
            case 2:
                evento.setTipologia(Tipologia.ESAME);
                ((EventoProxy) evento).setCorsoKey(SecurityHelpers.checkNumeric(request.getParameter("corso")));
                break;
            case 3:
                evento.setTipologia(Tipologia.PARZIALE);
                ((EventoProxy) evento).setCorsoKey(SecurityHelpers.checkNumeric(request.getParameter("corso")));
                break;
            case 4:
                evento.setTipologia(Tipologia.SEMINARIO);
                ((EventoProxy) evento).setCorsoKey(0);
                break;
            case 5:
                evento.setTipologia(Tipologia.RIUNIONE);
                ((EventoProxy) evento).setCorsoKey(0);
                break;
            case 6:
                evento.setTipologia(Tipologia.LAUREA);
                ((EventoProxy) evento).setCorsoKey(0);
                break;
            case 7:
                evento.setTipologia(Tipologia.ALTRO);
                ((EventoProxy) evento).setCorsoKey(0);
                break;
        }
        evento.setDataEvento(LocalDate.parse(request.getParameter("data"), formatterDate));
        evento.setOraInizio(LocalTime.parse(request.getParameter("ora_inizio"), formatterTime));
        evento.setOraFine(LocalTime.parse(request.getParameter("ora_fine"), formatterTime));
        int scelta_ricorrenza = SecurityHelpers.checkNumeric(request.getParameter("ricorrenza"));
        switch (scelta_ricorrenza) {
            case 1:
                evento.setRicorrenza(Ricorrenza.GIORNALIERA);
                evento.setDataFineRicorrenza(LocalDate.parse(request.getParameter("data_fine_ricorrenza"), formatterDate));
                break;
            case 2:
                evento.setRicorrenza(Ricorrenza.SETTIMANALE);
                evento.setDataFineRicorrenza(LocalDate.parse(request.getParameter("data_fine_ricorrenza"), formatterDate));
                break;
            case 3:
                evento.setRicorrenza(Ricorrenza.MENSILE);
                evento.setDataFineRicorrenza(LocalDate.parse(request.getParameter("data_fine_ricorrenza"), formatterDate));
                break;
            case 4:
                evento.setRicorrenza(Ricorrenza.NESSUNA);
                evento.setDataFineRicorrenza(null);
                break;
        }

        ((EventoProxy) evento).setAulaKey(SecurityHelpers.checkNumeric(request.getParameter("aula")));
        ((EventoProxy) evento).setResponsabileKey(SecurityHelpers.checkNumeric(request.getParameter("responsabile")));

        ((AuleWebDataLayer) request.getAttribute("datalayer")).getEventoDAO().storeEvento(evento);

        response.sendRedirect("operation?IDevento=" + evento.getKey() + "&message=" + URLEncoder.encode("inserimento evento avvenuto con successo", "utf-8"));

    }

    private void action_modify_evento(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
        Evento evento = ((AuleWebDataLayer) request.getAttribute("datalayer")).getEventoDAO().getEvento(SecurityHelpers.checkNumeric(request.getParameter("IDevento")));

        if (!evento.getNome().equals(request.getParameter("nome"))) {
            evento.setNome(request.getParameter("nome").toUpperCase());
        }

        if (!evento.getDescrizione().equals(request.getParameter("descrizione"))) {
            evento.setDescrizione(request.getParameter("descrizione"));
        }

        String scelta_tipologia = request.getParameter("tipologia");

        switch (scelta_tipologia) {
            case "LEZIONE":
                evento.setTipologia(Tipologia.LEZIONE);
                ((EventoProxy) evento).setCorsoKey(SecurityHelpers.checkNumeric(request.getParameter("corso")));
                break;
            case "ESAME":
                evento.setTipologia(Tipologia.ESAME);
                ((EventoProxy) evento).setCorsoKey(SecurityHelpers.checkNumeric(request.getParameter("corso")));
                break;
            case "SEMINARIO":
                evento.setTipologia(Tipologia.SEMINARIO);
                ((EventoProxy) evento).setCorsoKey(0);
                break;
            case "PARZIALE":
                evento.setTipologia(Tipologia.PARZIALE);
                ((EventoProxy) evento).setCorsoKey(SecurityHelpers.checkNumeric(request.getParameter("corso")));
                break;
            case "RIUNIONE":
                evento.setTipologia(Tipologia.RIUNIONE);
                ((EventoProxy) evento).setCorsoKey(0);
                break;
            case "LAUREA":
                evento.setTipologia(Tipologia.LAUREA);
                ((EventoProxy) evento).setCorsoKey(0);
                break;
            case "ALTRO":
                evento.setTipologia(Tipologia.ALTRO);
                ((EventoProxy) evento).setCorsoKey(0);
                break;
        }

        if (!evento.getDataEvento()
                .toString().equals(request.getParameter("data"))) {
            evento.setDataEvento(LocalDate.parse(request.getParameter("data"), formatterDate));
        }

        if (!evento.getOraInizio()
                .toString().equals(request.getParameter("ora_inizio"))) {
            evento.setOraInizio(LocalTime.parse(request.getParameter("ora_inizio"), formatterTime));
        }

        if (!evento.getOraFine()
                .toString().equals(request.getParameter("ora_fine"))) {
            evento.setOraFine(LocalTime.parse(request.getParameter("ora_fine"), formatterTime));
        }

        if (!evento.getRicorrenza()
                .toString().equals(request.getParameter("ricorrenza"))) {
            String scelta_ricorrenza = request.getParameter("ricorrenza");

            switch (scelta_ricorrenza) {
                case "GIORNALIERA":
                    evento.setRicorrenza(Ricorrenza.GIORNALIERA);
                    evento.setDataFineRicorrenza(LocalDate.parse(request.getParameter("data_fine_ricorrenza"), formatterDate));
                    break;
                case "SETTIMANALE":
                    evento.setRicorrenza(Ricorrenza.SETTIMANALE);
                    evento.setDataFineRicorrenza(LocalDate.parse(request.getParameter("data_fine_ricorrenza"), formatterDate));
                    break;
                case "MENSILE":
                    evento.setRicorrenza(Ricorrenza.MENSILE);
                    evento.setDataFineRicorrenza(LocalDate.parse(request.getParameter("data_fine_ricorrenza"), formatterDate));
                    break;
                case "NESSUNA":
                    evento.setRicorrenza(Ricorrenza.NESSUNA);
                    evento.setDataFineRicorrenza(null);
                    break;
            }
        }

        /*if (evento.getCorso().getKey() != SecurityHelpers.checkNumeric(request.getParameter("corsi"))) {
            Corso corso = ((AuleWebDataLayer) request.getAttribute("datalayer")).getCorsoDAO().getCorso(SecurityHelpers.checkNumeric(request.getParameter("corsi")));
            evento.setCorso(corso);
        }*/
        if (evento.getAula()
                .getKey() != SecurityHelpers.checkNumeric(request.getParameter("aula"))) {
            Aula aula = ((AuleWebDataLayer) request.getAttribute("datalayer")).getAulaDAO().getAula(SecurityHelpers.checkNumeric(request.getParameter("aula")));
            evento.setAula(aula);
        }

        if (evento.getResponsabile()
                .getKey() != SecurityHelpers.checkNumeric(request.getParameter("responsabile"))) {
            Responsabile responsabile = ((AuleWebDataLayer) request.getAttribute("datalayer")).getResponsabileDAO().getResponsabile(SecurityHelpers.checkNumeric(request.getParameter("responsabile")));
            evento.setResponsabile(responsabile);
        }

        ((AuleWebDataLayer) request.getAttribute("datalayer")).getEventoDAO().storeEvento(evento);

        response.sendRedirect("operation?IDevento=" + evento.getKey() + "&message=" + URLEncoder.encode("aggiornamento evento avvenuto con successo", "utf-8"));
    }

    private void action_view_evento(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        Map data = new HashMap<>();
        data.put("username", SecurityHelpers.checkSession(request).getAttribute("username"));
        data.put("outline_tpl", "");
        int evento_key = SecurityHelpers.checkNumeric(request.getParameter("IDevento"));
        Evento evento = ((AuleWebDataLayer) request.getAttribute("datalayer")).getEventoDAO().getEvento(evento_key);
        data.put("evento", evento);
        data.put("corsi", ((AuleWebDataLayer) request.getAttribute("datalayer")).getCorsoDAO().getAllCorsi());
        data.put("aule", ((AuleWebDataLayer) request.getAttribute("datalayer")).getAulaDAO().getAllAule());
        data.put("responsabili", ((AuleWebDataLayer) request.getAttribute("datalayer")).getResponsabileDAO().getAllResponsabili());
        TemplateResult res = new TemplateResult(getServletContext());
        res.activate("update_evento.ftl.html", data, response);
    }

    private void action_modify_table_eventi(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        Map data = new HashMap<>();
        data.put("username", SecurityHelpers.checkSession(request).getAttribute("username"));
        data.put("outline_tpl", "");

        //prendiamo in considerazione anche le ricorrenze degli eventi, ma modificheremo semplicemente l'evento principale. A cascata verranno
        //effettuate le modifiche anche per le ricorrenze
        Set<Evento> eventi = new HashSet<>();
        if (request.getParameter("search").isEmpty()) {
            eventi.addAll(((AuleWebDataLayer) request.getAttribute("datalayer")).getEventoDAO().getAllEventi());
            List<Integer> idEventiByRicorrenza = ((AuleWebDataLayer) request.getAttribute("datalayer")).getEventoRicorrenteDAO().getAllEventiIDByRicorrenze();
            for (Integer id : idEventiByRicorrenza) {
                eventi.add(((AuleWebDataLayer) request.getAttribute("datalayer")).getEventoDAO().getEvento(id));
            }
            data.put("eventi", eventi);
        } else {
            data.put("eventi", ((AuleWebDataLayer) request.getAttribute("datalayer")).getEventoDAO().getEventiByPartialName(request.getParameter("search")));
        }
        TemplateResult res = new TemplateResult(getServletContext());
        res.activate("table_eventi_research.ftl.html", data, response);
    }

    
    private void action_input_corso(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        Map data = new HashMap<>();
        data.put("username", SecurityHelpers.checkSession(request).getAttribute("username"));
        data.put("outline_tpl", "");
        data.put("corsi", ((AuleWebDataLayer) request.getAttribute("datalayer")).getCorsoDAO().getAllCorsi());
        TemplateResult res = new TemplateResult(getServletContext());
        res.activate("corsi_administration.ftl.html", data, response);
    }

    private void action_insert_corso(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        Corso corso = ((AuleWebDataLayer) request.getAttribute("datalayer")).getCorsoDAO().createCorso();
        corso.setKey(null);
        corso.setNome(request.getParameter("nome").toUpperCase());
        corso.setCorsoDiLaurea(request.getParameter("corso_laurea").toUpperCase());
        switch (SecurityHelpers.checkNumeric(request.getParameter("tipo_laurea"))) {
            case 1:
                corso.setTipoLaurea(TipoLaurea.TRIENNALE);
                break;
            case 2:
                corso.setTipoLaurea(TipoLaurea.MAGISTRALE);
                break;
            case 3:
                corso.setTipoLaurea(TipoLaurea.CICLO_UNICO);
        }
        corso.setAnnoDiFrequentazione(SecurityHelpers.checkNumeric(request.getParameter("anno_frequentazione")));
        ((AuleWebDataLayer) request.getAttribute("datalayer")).getCorsoDAO().storeCorso(corso);

        response.sendRedirect("administration?input_corso=1");
    }

    private void action_input_responsabile(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        Map data = new HashMap<>();
        data.put("username", SecurityHelpers.checkSession(request).getAttribute("username"));
        data.put("outline_tpl", "");
        data.put("responsabili", ((AuleWebDataLayer) request.getAttribute("datalayer")).getResponsabileDAO().getAllResponsabili());
        TemplateResult res = new TemplateResult(getServletContext());
        res.activate("responsabili_administration.ftl.html", data, response);
    }

    private void action_insert_responsabile(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        Responsabile responsabile = ((AuleWebDataLayer) request.getAttribute("datalayer")).getResponsabileDAO().createResponsabile();
        responsabile.setKey(null);
        responsabile.setNome(request.getParameter("nome").toUpperCase());
        responsabile.setCognome(request.getParameter("cognome").toUpperCase());
        responsabile.setCodiceFiscale(request.getParameter("codice_fiscale").toUpperCase());
        responsabile.setEmail(request.getParameter("email"));
        ((AuleWebDataLayer) request.getAttribute("datalayer")).getResponsabileDAO().storeResponsabile(responsabile);

        response.sendRedirect("administration?input_responsabile=1");
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
                    switch (SecurityHelpers.checkNumeric(request.getParameter("operation"))) {
                        case 1:
                            if (request.getParameter("search") != null) {
                                action_modify_table_eventi(request, response);
                            } else if (request.getParameter("IDevento") != null) {
                                action_view_evento(request, response);
                            } else {
                                action_eventi(request, response);
                            }
                            break;
                        case 2:
                            if (request.getParameter("search") != null) {
                                action_modify_table_aula(request, response);
                            } else if (request.getParameter("IDaula") != null) {
                                action_view_aula(request, response);
                            } else {
                                action_aule(request, response);
                            }
                            break;
                        case 3:
                            if (request.getParameter("search") != null) {
                                action_modify_table_gruppo(request, response);
                            } else if (request.getParameter("IDgruppo") != null) {
                                action_view_gruppo(request, response);
                            } else if (request.getParameter("select_tipo_gruppo") != null) {
                                action_select_tipo_gruppo(request, response);
                            } else {
                                action_gruppi(request, response);
                            }
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
                } else if (request.getContentType() != null && request.getContentType().startsWith("multipart/form-data")) {
                    action_import_aula(request, response);
                } else if (request.getParameter("insert_gruppo") != null) {
                    action_insert_gruppo(request, response);
                } else if (request.getParameter("modify_gruppo") != null) {
                    action_modify_gruppo(request, response);
                } else if (request.getParameter("insert_eventi") != null) {
                    action_insert_evento(request, response);
                } else if (request.getParameter("modify_evento") != null) {
                    action_modify_evento(request, response);
                } else if (request.getParameter("input_corso") != null) {
                    action_input_corso(request, response);
                } else if (request.getParameter("insert_corso") != null) {
                    action_insert_corso(request, response);
                } else if (request.getParameter("input_responsabile") != null) {
                    action_input_responsabile(request, response);
                } else if (request.getParameter("insert_responsabile") != null) {
                    action_insert_responsabile(request, response);
                }else {
                    action_default(request, response);
                }
            } else {
                response.sendRedirect("login");
            }

        } catch (IOException | TemplateManagerException ex) {
            handleError(ex, request, response);

        } catch (DataException ex) {
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
