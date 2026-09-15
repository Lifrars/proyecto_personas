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
    public void insertarPersona(Nodo p, Persona persona, Integer cedula) {
        if (ancestro == null) {
            ancestro = new Nodo(persona, null);
        } else {
            if (existe(persona.getCedula())) {
                System.out.println("Ya existe una persona con esa cedula");
            } else {
                Boolean sw = insertar(p, persona, cedula);
                if (sw == false) {
                    System.out.println("No existe el padre buscado");
                }
            }
        }
}
    
    private Boolean insertar(Nodo p, Persona persona, Integer cedula) {
        Boolean sw = false;
        Boolean swL = false;
        while (p != null && sw == false && swL == false) {
            if (p.getCedula().equals(cedula)) {
                swL = true;
            } else {
                if (p.getSw() == 1) {
                    sw = insertar(p.getLigaLista().getLiga(), persona, cedula);
                }
                p = p.getLiga();
            }
        }
        if (swL == true) {
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
                if (p == ancestro) {
                    Nodo ant = p;
                    Nodo x = p.getLiga();
                    while (x != null && x.getCedula() < persona.getCedula()) {
                        ant = x;
                        x = x.getLiga();
                    }
                    dato.setLiga(x);
                    ant.setLiga(dato);
                } else {
                    Nodo primogenito = new Nodo(p.getPersona(), dato);
                    p.setLigaLista(primogenito);
                    p.setSw(1);
                    p.setPersona(null);
                }
            }
            sw = true;
        }
        return sw;
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
        int nivel = 0;
        if (p != null) {
            if (p.getCedula().equals(ced)) {
                nivel = 1;
            } else {
                nivel = mostrarNivelCed(p.getLiga(), ced, 2);
            }
        }
        if (nivel == 0) {
            System.out.println("No existe la persona con cedula " + ced);
        } else {
            System.out.println("Nivel dato: " + nivel);
        }
    }

    public int mostrarNivelCed(Nodo p, Integer ced, Integer nivel) {
        int res = 0;
        while (p != null && res == 0) {
            if (p.getCedula().equals(ced)) {
                res = nivel;
            } else {
                if (p.getSw() == 1) {
                    res = mostrarNivelCed(p.getLigaLista().getLiga(), ced, nivel + 1);
                }
                p = p.getLiga();
            }
        }
        return res;
    }
    
    public void mostrarInfoGen(Nodo p, Integer ced) {
        if (p == null || p.getCedula().equals(ced)) {
            System.out.println("No tiene hermanos");
        } else {
            Boolean sw = mostrarInfoGen(p, ced, false);
            if (sw == false) {
                System.out.println("No existe la persona con cedula " + ced);
            }
        }
    }

    public Boolean mostrarInfoGen(Nodo padre, Integer ced, Boolean sw) {
        Nodo q = padre.getLiga();
        Boolean swL = false;
        while (q != null && swL == false && sw == false) {
            if (q.getCedula().equals(ced)) {
                swL = true;
            } else {
                if (q.getSw() == 1) {
                    sw = mostrarInfoGen(q.getLigaLista(), ced, sw);
                }
                q = q.getLiga();
            }
        }
        if (swL == true) {
            sw = true;
            System.out.println("Hermanos de " + ced + ":");
            Nodo h = padre.getLiga();
            while (h != null) {
                if (!h.getCedula().equals(ced)) {
                    System.out.println(h.getPersona().toString());
                }
                h = h.getLiga();
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
    private void eliminarLista(Nodo x, Nodo padre) {
        if (x != null) {
            eliminarLista(x.getLiga(), padre);
            Boolean teniaSublista = x.getSw() == 1;
            eliminar(x, padre);
            if (teniaSublista == true) {
                ordenar(x, padre);
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
    
    
    
    private Boolean existe(Integer ced) {
        return ancestro.getCedula().equals(ced) || esDescendiente(ancestro, ced);
    }

    private Boolean esDescendiente(Nodo padre, Integer ced) {
        Boolean sw = false;
        Nodo q = padre.getLiga();
        while (q != null && sw == false) {
            if (q.getCedula().equals(ced)) {
                sw = true;
            } else {
                if (q.getSw() == 1) {
                    sw = esDescendiente(q.getLigaLista(), ced);
                }
            }
            q = q.getLiga();
        }
        return sw;
    }

    public void ancestroComun(Integer a, Integer b) {
        if (ancestro == null) {
            System.out.println("Arbol vacio");
        } else if (a.equals(b)) {
            System.out.println("Las cedulas deben ser distintas");
        } else if (!existe(a) || !existe(b)) {
            System.out.println("Alguna de las personas no existe");
        } else if (ancestro.getCedula().equals(a) || ancestro.getCedula().equals(b)) {
            System.out.println("La raiz no tiene ascendientes, no hay ancestro comun");
        } else {
            Nodo padreComun = ancestroComun(ancestro, a, b);
            System.out.println("Ancestro comun mas cercano: " + padreComun.getPersona().toString());
        }
    }

    private Nodo ancestroComun(Nodo padre, Integer a, Integer b) {
        Nodo res = padre;
        Boolean sw = false;
        Nodo q = padre.getLiga();
        while (q != null && sw == false) {
            if (q.getSw() == 1) {
                Nodo sub = q.getLigaLista();
                if (esDescendiente(sub, a) && esDescendiente(sub, b)) {
                    res = ancestroComun(sub, a, b);
                    sw = true;
                }
            }
            q = q.getLiga();
        }
        return res;
    }
    private Nodo buscarPadre(Nodo padre, Integer ced) {
    Nodo res = null;
    Nodo q = padre.getLiga();
    while (q != null && res == null) {
        if (q.getCedula().equals(ced)) {
            res = padre;
        } else {
            if (q.getSw() == 1) {
                res = buscarPadre(q.getLigaLista(), ced);
            }
        }
        q = q.getLiga();
    }
    return res;
}

private Nodo buscarEnLista(Nodo padre, Integer ced) {
    Nodo q = padre.getLiga();
    while (q != null && !q.getCedula().equals(ced)) {
        q = q.getLiga();
    }
    return q;
}

public void trasladarRama(Integer a, Integer b) {
    if (ancestro == null) {
        System.out.println("Arbol vacio");
    } else {
        if (a.equals(b) || !existe(a) || !existe(b)) {
            System.out.println("Cedulas invalidas");
        } else {
            if (ancestro.getCedula().equals(a)) {
                System.out.println("No se puede trasladar la raiz");
            } else {
                Nodo cabPadreA = buscarPadre(ancestro, a);
                Nodo nodoA = buscarEnLista(cabPadreA, a);

                if (nodoA.getSw() == 1 && esDescendiente(nodoA.getLigaLista(), b)) {
                    System.out.println("B es descendiente de A, no se puede");
                } else {
                    if (cabPadreA.getCedula().equals(b)) {
                        System.out.println("B ya es el padre de A");
                    } else {
                        moverRama(nodoA, cabPadreA, b);
                        System.out.println("Rama trasladada");
                    }
                }
            }
        }
    }
}

private void moverRama(Nodo nodoA, Nodo cabPadreA, Integer b) {

    Nodo ant = cabPadreA;
    while (ant.getLiga() != nodoA) {
        ant = ant.getLiga();
    }
    ant.setLiga(nodoA.getLiga());
    nodoA.setLiga(null);

    
    if (cabPadreA.getLiga() == null && cabPadreA != ancestro) {
        Nodo cabAbuelo = buscarPadre(ancestro, cabPadreA.getCedula());
        Nodo nodoPadre = buscarEnLista(cabAbuelo, cabPadreA.getCedula());
        nodoPadre.setPersona(cabPadreA.getPersona());
        nodoPadre.setLigaLista(null);
        nodoPadre.setSw(0);
    }

  
    Nodo cabB;
    if (ancestro.getCedula().equals(b)) {
        cabB = ancestro;
    } else {
        Nodo cabPadreB = buscarPadre(ancestro, b);
        Nodo nodoB = buscarEnLista(cabPadreB, b);
        if (nodoB.getSw() == 1) {
            cabB = nodoB.getLigaLista();
        } else {
            cabB = new Nodo(nodoB.getPersona(), null);  
            nodoB.setLigaLista(cabB);
            nodoB.setSw(1);
            nodoB.setPersona(null);
        }
    }

  
    ant = cabB;
    Nodo q = cabB.getLiga();
    while (q != null && q.getCedula() > nodoA.getCedula()) {
        ant = q;
        q = q.getLiga();
    }
    nodoA.setLiga(q);
    ant.setLiga(nodoA);
}
    
}
