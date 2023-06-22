/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.project.aule_web.controller;

import it.univaq.project.aule_web.framework.data.DataLayer;
import it.univaq.project.aule_web.data.dao.impl.AuleWebDataLayer;
import it.univaq.project.aule_web.framework.controller.AbstractBaseController;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.sql.DataSource;

/**
 *
 * @author Alberto Bogi
 */

public abstract class AuleWebBaseController extends AbstractBaseController {
    
    @Override
    protected DataLayer createDataLayer(DataSource ds) throws ServletException {
        try {
            return new AuleWebDataLayer(ds);
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
    
}
