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
        int nivel = mostrarNivelCed(p, ced, 1);
        if (nivel == 0) {
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
    
    public void mostrarInfoGen(Nodo p, Integer ced) {
        Boolean sw = mostrarInfoGen(p, ced,false);
        if (sw == false) {
            System.out.println("No existe la persona con cedula " + ced);
        } else {
            System.out.println("Nivel dato: " + sw);
        }
    }

    public Boolean  mostrarInfoGen(Nodo p, Integer ced, Boolean sw) {
        Nodo q  = p;
        Boolean swL =false;
        while (q != null && swL==false && sw == false) {
            if (q.getCedula().equals(ced)) {
                 sw=true;
                 swL=true;
            } else {
                if (q.getSw() == 1) {
                     mostrarInfoGen(q.getLigaLista().getLiga(), ced,sw);
                }
                q = q.getLiga();
            }
        }
        if(swL==true){
            System.out.println("Hermanos de de tamas " + ced);
            while(p!=null){
                System.out.println(p.getPersona().toString());
                p=p.getLiga();
            }
        }

      return sw;
    }
    
    public void mostrarInfoNiv(Nodo p, int nivelBus) {
        Boolean sw = false;

        if (p != null) {
            if (nivelBus == 1) {
                System.out.println(p.getPersona().toString());  
                sw = true;
            } else {
                sw = mostrarInfoNiv(p.getLiga(), nivelBus, 2);  
            }
        }

        if (sw == false) {
            System.out.println("No existe el nivel " + nivelBus);
        }
    }

    public Boolean mostrarInfoNiv(Nodo p, int nivelBus, int nivel) {

        Boolean sw = false;
      
        if (p != null && nivel == nivelBus) {
          
            while (p != null) {
                System.out.println(p.getPersona().toString());
                p = p.getLiga();
            }
            sw = true;
        } else {
            while (p != null) {
                if (p.getSw() == 1) {
                    Boolean swR = mostrarInfoNiv(p.getLigaLista().getLiga(), nivelBus, nivel + 1);
                    if (swR == true) {
                        sw = true;
                    }
                }
                p = p.getLiga();
            }
        }

        return sw;
    }
    
    public void mostrarMasProfundo() {
        
        if (ancestro == null) {
            System.out.println("Arbol vacio");
        } else {
            int max = nivelMax(ancestro.getLiga(), 2);
            System.out.println("Nivel mas profundo: " + max);
            mostrarInfoNiv(ancestro, max);
        }
}

    public int nivelMax(Nodo p, int nivel) {
        if (p == null) {
            return nivel - 1;
        }

        int max = nivel;
        Nodo q = p;

        while (q != null) {
            if (q.getSw() == 1) {
                int r = nivelMax(q.getLigaLista().getLiga(), nivel + 1);
                if (r > max) {
                    max = r;
                }
            }
            q = q.getLiga();
        }

        return max;
    }
    

    
    
    
    
    public void ordenar(Nodo x, Nodo padre) {
        Nodo ant, q;
        ant = padre;
        while (ant.getLiga() != x) {
            ant = ant.getLiga();
        }
        ant.setLiga(x.getLiga());

     
        ant = padre;
        q = padre.getLiga();
        while (q != null && q.getCedula() > x.getCedula()) {
            ant = q;
            q = q.getLiga();
        }

      
        x.setLiga(q);
        ant.setLiga(x);
    }
    
    public void eliminar(Nodo p, Nodo padre) {
        Nodo s, q, ant;

        if (p == ancestro && ancestro.getLiga() == null) {
            ancestro = null;                      
        } else{
            if (p.getSw() == 0 && p != ancestro) {
                ant = padre;                   
                while (ant != null && ant.getLiga() != p) {
                    ant = ant.getLiga();
                }
                if (ant != null) {
                    ant.setLiga(p.getLiga());
                }

            } else {
                if (p == ancestro) {
                    s = ancestro;
                } else {
                    s = p.getLigaLista();
                }

                q = s.getLiga();

                if (q != null) {
                    s.setPersona(q.getPersona());

                    if (q.getSw() == 1) {
                        eliminar(q, s);
                        ordenar(q,s);
                    } else {
                        s.setLiga(q.getLiga());

                        if (s.getLiga() == null && p != ancestro) {
                            p.setPersona(s.getPersona());
                            p.setLigaLista(null);
                            p.setSw(0);
                        }
                    }
                }
            }
        }
    }
    
    public void eliminarPorNIvel(int nivelBus) {
        Boolean sw = false;

        if (ancestro != null) {
            if (nivelBus == 1) {
                eliminar(ancestro,ancestro);
                sw = true;
            } else {
                sw = eliminarPorNIvel(ancestro, nivelBus, 2);  
            }
        }

        if (sw == false) {
            System.out.println("No existe el nivel " + nivelBus);
        }
        }
    private void eliminarLista(Nodo x, Nodo cab) {
        if (x != null) {
            eliminarLista(x.getLiga(), cab); 

          
            eliminar(x, cab);
            if (x.getSw() == 1) {
                ordenar(x, cab);   
            }
        }
    }
    public Boolean eliminarPorNIvel(Nodo p, int nivelBus, int nivel) {

        Boolean sw = false;
        Nodo q = p.getLiga();
        if ( nivel == nivelBus) {
            if (q != null) {
                eliminarLista(q, p);
                sw = true;
            }
        } else {
            while (q != null) {
                if (q.getSw() == 1) {
                    Nodo padreSub=q.getLigaLista();
                    Boolean swR = eliminarPorNIvel(padreSub, nivelBus, nivel + 1);
                    if (swR == true) {
                        sw = true;
                    }
                    if (padreSub.getLiga() == null) {
                        q.setPersona(padreSub.getPersona());
                        q.setLigaLista(null);
                        q.setSw(0);
                    }
                }
                q = q.getLiga();
            }
        }

        return sw;
    }
}
