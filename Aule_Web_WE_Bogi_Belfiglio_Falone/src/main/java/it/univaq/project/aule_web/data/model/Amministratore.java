/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package it.univaq.project.aule_web.data.model;

import it.univaq.project.aule_web.framework.data.DataItem;

/**
 *
 * @author Alberto Bogi
 */
public interface Amministratore extends DataItem<Integer>{
    
    public String getUsername();
    
    public String getPassword();
    
    public void setUsername(String username);
    
    public void setPassword(String password);
    
}
