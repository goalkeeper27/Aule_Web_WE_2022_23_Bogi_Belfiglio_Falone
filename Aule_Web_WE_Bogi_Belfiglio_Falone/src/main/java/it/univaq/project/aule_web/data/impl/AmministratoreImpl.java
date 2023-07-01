/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.project.aule_web.data.impl;

import it.univaq.project.aule_web.data.model.Amministratore;
import it.univaq.project.aule_web.framework.data.DataItemImpl;

public class AmministratoreImpl extends DataItemImpl<Integer> implements Amministratore{
    
    private String username;
    private String password;
    
    public AmministratoreImpl(){
        username = "";
        password = "";
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }
    
}
