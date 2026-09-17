/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.personas;

/**
 *
 * @author sala313
 */
public class Nodo {
    private int sw;
    private Nodo liga;
    private Nodo ligaLista;
    private Persona persona;

    //Constructor para crear una persona
    public Nodo (  Persona persona,Nodo sig) {
        this.sw = 0;
        this.liga = sig;
        this.persona = persona;
    }
    //Constructor para crear una sublista
    public Nodo (Nodo sublista, Nodo sig) {
        this.sw = 1;
        this.liga = sig;
        this.ligaLista = sublista;
    }

    public int getSw() {
        return sw;
    }

    public void setSw(int sw) {
        this.sw = sw;
    }

    public Nodo getLiga() {
        return liga;
    }

    public void setLiga(Nodo liga) {
        this.liga = liga;
    }

    public Nodo getLigaLista() {
        return ligaLista;
    }

    public void setLigaLista(Nodo ligaLista) {
        this.ligaLista = ligaLista;
    }
    //Este metodo trae la persona independientemente de si el nodo es una sublista o un dato atómico
    public Persona getPersona() {
        if (sw == 0) {
            return persona;
        } else {
            return ligaLista.getPersona();
       }
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
    //Este metodo trae la cedula independientemente de si el nodo es una sublista o un dato atómico
    public Integer getCedula() {
        if (sw == 0) {
            return persona.getCedula();
        } else {
            return ligaLista.getPersona().getCedula();
       }
        
   
}
    
    

 


    
    
}
