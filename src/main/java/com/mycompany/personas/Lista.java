/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.personas;

import javax.swing.JOptionPane;

/**
 *
 * @author sala313
 */
public class Lista {

    private Nodo ancestro;
    public Lista() {
        ancestro = null;
    }

    public Nodo getAncestro() {
        return ancestro;
    }

    public void setAncestro(Nodo ancestro) {
        this.ancestro = ancestro;
    }
    
    public void insertarPersona(Nodo p,Persona persona,Integer cedula){
        Boolean sw = false;
     
        if(ancestro==null){
            ancestro=new Nodo(persona,null);
        }else{
            while(p!=null && sw== false){

                if(p.getCedula().equals(cedula)){
        
                   sw=true;
                }else{
                    if(p.getSw()== 1){
                         insertarPersona(p.getLigaLista().getLiga(),persona,cedula);
                    }
                    p=p.getLiga();
                }
            }
            if(sw==false){
                System.out.println("No existe el padre buscado");
            }else{
                Nodo dato = new Nodo(persona, null);

                if (p.getSw() == 1) {

                    Nodo ant = p.getLigaLista();
                    Nodo x = ant.getLiga();

                    while (x != null && x.getCedula() < persona.getCedula()) {
                        ant = x;
                        x = x.getLiga();
                    }

                    dato.setLiga(x);
                    ant.setLiga(dato);

                } else {
                    if (p == ancestro){
                        Nodo ant=p;
                        Nodo x = p.getLiga();
                        while (x != null && x.getCedula() < persona.getCedula()) {
                            ant = x;
                            x = x.getLiga();
                        }
                        
                        dato.setLiga(x);
                        ant.setLiga(dato);
                    }else{
                        Nodo primogenito = new Nodo(p.getPersona(), dato);
                        p.setLigaLista(primogenito);
                        p.setSw(1);
                        p.setPersona(null);
                    }
             
                }
        }
    }
        
     


    }
    
    public void mostrarTodo(Nodo p){
        while (p!= null){
            if(p.getSw()==1){
                mostrarTodo(p.getLigaLista());
            }else{
                System.out.println(p.getCedula());
            }
            p=p.getLiga();
        }
    }
    
    public Boolean mostrarInformacionPadre(Nodo p, Integer ced,Boolean sw){
        Nodo q = p;
        Boolean swL=false;
        while(q!= null && swL==false && sw==false){
            if (q.getCedula().equals(ced)){
                sw= true;
                swL= true;
                System.out.println("Dato");
            }else{
                if(q.getSw()==1){
                   sw = mostrarInformacionPadre(q.getLigaLista(),ced,sw);
                }
                q=q.getLiga();
            }
        }
        if(swL==true){
            if( ancestro.getCedula().equals(ced)){
                System.out.println("El Ancestro no tiene padre");
            }
            else{
                System.out.println(p.getPersona().toString());
            }
            
        }
        return sw;
      
    }
    public void mostrarNivelCed(Nodo p, Integer ced) {
        int nivel = mostrarNivelCed(p, ced, 0);
        if (nivel == -1) {
            System.out.println("No existe la persona con cedula " + ced);
        } else {
            System.out.println("Nivel dato: " + nivel);
        }
    }

    public int mostrarNivelCed(Nodo p, Integer ced, Integer nivel) {

        while (p != null) {

            if (p.getCedula().equals(ced)) {
                if (nivel == 0 && p != ancestro) {
                    return 1;
                }else{
                    if(p==ancestro){
                        return 0;
                    }
                     return nivel+1;
                }
               
            } else {
                if (p.getSw() == 1) {
                    int r = mostrarNivelCed(p.getLigaLista().getLiga(), ced, nivel + 1);
                    if (r != -1) {
                        return r;
                    }
                }
                p = p.getLiga();
            }
        }

        return -1;
    }
}
