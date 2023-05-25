/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package it.univaq.aule_web.framework.data;

/**
 *
 * @author Alberto Bogi
 */
public interface DataItemProxy {

    boolean isModified();

    void setModified(boolean dirty);

}
