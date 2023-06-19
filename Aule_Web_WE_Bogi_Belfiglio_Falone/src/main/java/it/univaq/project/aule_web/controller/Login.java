/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.project.aule_web.controller;

import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.project.aule_web.data.dao.impl.AuleWebDataLayer;
import it.univaq.project.aule_web.data.model.Amministratore;
import it.univaq.project.aule_web.framework.data.DataException;
import it.univaq.project.aule_web.framework.result.TemplateResult;
import it.univaq.project.aule_web.framework.security.SecurityHelpers;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Alberto Bogi
 */
public class Login extends AuleWebBaseController {

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        TemplateResult result = new TemplateResult(getServletContext());
        request.setAttribute("outline_tpl", "");
        result.activate("login_admin.ftl.html", request, response);

    }

    private void action_login(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateManagerException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (!username.isEmpty() && !password.isEmpty()) {
            try {
                Amministratore a = ((AuleWebDataLayer) request.getAttribute("datalayer")).getAmministratoreDAO().getAmministratoreByUsername(username);
                if (a != null && SecurityHelpers.checkPasswordHashPBKDF2(password, a.getPassword())) {
                    //se la validazione ha successo
                    //if the identity validation succeeds
                    //          SecurityHelpers.createSession(request, username, u.getKey());
                    //se è stato trasmesso un URL di origine, torniamo a quell'indirizzo
                    //if an origin URL has been transmitted, return to it
                    SecurityHelpers.createSession(request, username, a.getKey());
                    response.sendRedirect("administration");

                } else {
                    action_default(request, response);
                }
            } catch (NoSuchAlgorithmException | InvalidKeySpecException | DataException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
            if(request.getParameter("login") != null) {
                action_login(request, response);
            } else {
                String https_redirect_url = SecurityHelpers.checkHttps(request);
                request.setAttribute("https-redirect", https_redirect_url);
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
