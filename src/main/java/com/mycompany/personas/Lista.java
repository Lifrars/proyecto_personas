/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.personas;

import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.Deque;
import java.util.List;
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

    public void mostrarTodo(Nodo p) {
        while (p != null) {
            if (p.getSw() == 1) {
                mostrarTodo(p.getLigaLista());
            } else {
                System.out.println(p.getCedula());
            }
            p = p.getLiga();
        }
    }

    public Boolean mostrarInformacionPadre(Nodo p, Integer ced, Boolean sw) {
        Nodo q = p;
        Boolean swL = false;
        while (q != null && swL == false && sw == false) {
            if (q.getCedula().equals(ced)) {
                sw = true;
                swL = true;

            } else {
                if (q.getSw() == 1) {
                    sw = mostrarInformacionPadre(q.getLigaLista(), ced, sw);
                }
                q = q.getLiga();
            }
        }
        if (swL == true) {
            if (ancestro.getCedula().equals(ced)) {
                System.out.println("El Ancestro no tiene padre");
            } else {
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
        } else {
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
                        ordenar(q, s);
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
                eliminar(ancestro, ancestro);
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
        if (nivel == nivelBus) {
            if (q != null) {
                eliminarLista(q, p);
                sw = true;
            }
        } else {
            while (q != null) {
                if (q.getSw() == 1) {
                    Nodo padreSub = q.getLigaLista();
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

    public boolean MostrarTodosLosHijos(Nodo p, int cedpadre, boolean sw) {
        Nodo q = p;
        while (q != null && sw) {
            if (cedpadre == q.getCedula()) {
                sw = false;
                if (q.getSw() == 0 && q != ancestro) {
                    JOptionPane.showMessageDialog(null, "La persona no es padre");
                } else {
                    if (q == ancestro) {
                        q = q.getLiga();
                    } else {
                        q = q.getLigaLista().getLiga();
                    }
                    System.out.println("Hijos: \n");
                    while (q != null) {
                        System.out.println(q.getPersona().toString());
                        q = q.getLiga();
                    }
                }
            } else {
                if (q.getSw() == 1) {
                    sw = MostrarTodosLosHijos(q.getLigaLista(), cedpadre, sw);
                }
                q = q.getLiga();
            }

        }
        return sw;

    }

    public boolean MostrarHermanos(Nodo p, int cedhermano, boolean sw) {
        Nodo q = p;
        while (q != null && sw) {
            if (cedhermano == q.getCedula()) {
                sw = false;
                if (q == ancestro) {
                    JOptionPane.showMessageDialog(null, "La persona es el ancestro, no tiene hermanos");
                } else {
                    if (ConteoHermanos(p, cedhermano) != 0) {
                        Nodo hermano = p.getLiga();
                        System.out.println("Hermanos: ");
                        while (hermano != null) {
                            if (hermano.getCedula() != cedhermano) {
                                System.out.println(hermano.getPersona().toString());
                            }
                            hermano = hermano.getLiga();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "La persona no tiene hermanos");
                    }
                }
            } else {
                if (q.getSw() == 1) {
                    sw = MostrarHermanos(q.getLigaLista(), cedhermano, sw);
                }
                q = q.getLiga();
            }

        }
        return sw;

    }

    public int ConteoHermanos(Nodo p, int ced) {
        int count = 0;
        Nodo q = p.getLiga();
        while (q != null) {
            if (q.getCedula() != ced) {
                count++;
            }
            q = q.getLiga();
        }
        return count;
    }

    public boolean MostrarTios(Nodo p, int cedpersona, boolean sw) {
        Nodo q = p;
        while (q != null && sw) {
            if (cedpersona == q.getCedula()) {
                sw = false;
                if (q == ancestro) {
                    JOptionPane.showMessageDialog(null, "La persona es el ancestro, no tiene tíos");
                } else {
                    int cedPadre = p.getCedula();
                    if (p == ancestro) {
                        JOptionPane.showMessageDialog(null, "El padre de la persona es el ancestro, el ancestro no tiene hermanos");
                    } else {
                        MostrarHermanosDelPadre(ancestro, cedPadre, true);
                    }
                }
            } else {
                if (q.getSw() == 1) {
                    sw = MostrarTios(q.getLigaLista(), cedpersona, sw);
                }
                q = q.getLiga();
            }
        }
        return sw;
    }

    public boolean MostrarHermanosDelPadre(Nodo p, int cedPadre, boolean sw) {
        Nodo q = p;
        while (q != null && sw) {
            if (cedPadre == q.getCedula()) {
                sw = false;
                if (q == ancestro) {
                    JOptionPane.showMessageDialog(null, "No hay tíos registrados");
                } else {
                    if (ConteoHermanos(p, cedPadre) != 0) {
                        Nodo tio = p.getLiga();
                        System.out.println("Tíos: ");
                        while (tio != null) {
                            if (tio.getCedula() != cedPadre) {
                                System.out.println(
                                        tio.getPersona().toString()
                                );
                            }
                            tio = tio.getLiga();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "La persona no tiene tíos registrados");
                    }
                }
            } else {
                if (q.getSw() == 1) {
                    sw = MostrarHermanosDelPadre(q.getLigaLista(), cedPadre, sw);
                }
                q = q.getLiga();
            }
        }
        return sw;
    }

    public boolean MostrarSobrinos(Nodo p, int cedpersona, boolean sw) {
        Nodo q = p;
        while (q != null && sw) {
            if (cedpersona == q.getCedula()) {
                sw = false;
                if (q == ancestro) {
                    JOptionPane.showMessageDialog(null, "La persona es el ancestro, no tiene sobrinos registrados");
                } else {
                    Nodo hermano = p.getLiga();
                    int count = 0;
                    System.out.println("Sobrinos: ");
                    while (hermano != null) {
                        if (hermano.getCedula() != cedpersona && hermano.getSw() == 1) {
                            Nodo sobrino = hermano.getLigaLista().getLiga();
                            while (sobrino != null) {
                                System.out.println(sobrino.getPersona().toString());
                                count++;
                                sobrino = sobrino.getLiga();
                            }
                        }
                        hermano = hermano.getLiga();
                    }
                    if (count == 0) {
                        JOptionPane.showMessageDialog(null, "La persona no tiene sobrinos");
                    }
                }
            } else {
                if (q.getSw() == 1) {
                    sw = MostrarSobrinos(q.getLigaLista(), cedpersona, sw);
                }
                q = q.getLiga();
            }
        }
        return sw;
    }

    public boolean MostrarPrimos(Nodo p, int cedpersona, boolean sw) {
        Nodo q = p;
        while (q != null && sw) {
            if (cedpersona == q.getCedula()) {
                sw = false;
                if (q == ancestro) {
                    JOptionPane.showMessageDialog(null, "La persona es el ancestro, no tiene primos registrados");
                } else {
                    int cedPadre = p.getCedula();
                    if (p == ancestro) {
                        JOptionPane.showMessageDialog(null, "La persona no tiene primos registrados");
                    } else {
                        MostrarSobrinosDelPadre(ancestro, cedPadre, true);
                    }
                }
            } else {
                if (q.getSw() == 1) {
                    sw = MostrarPrimos(q.getLigaLista(), cedpersona, sw);
                }
                q = q.getLiga();
            }
        }
        return sw;
    }

    public boolean MostrarAncestros(Nodo p, int cedpersona, boolean sw) {
        Nodo q = p;

        while (q != null && sw) {
            if (cedpersona == q.getCedula()) {
                sw = false;

                if (q == ancestro) {
                    JOptionPane.showMessageDialog(null, "La persona es el ancestro, no tiene ancestros registrados");
                } else {
                    Nodo padre = buscarPadre(ancestro, cedpersona);
                    System.out.println("Ancestros:");

                    while (padre != null) {
                        System.out.println(padre.getPersona().toString());

                        if (padre == ancestro) {
                            padre = null;
                        } else {
                            padre = buscarPadre(ancestro, padre.getCedula());
                        }
                    }
                }
            } else {
                if (q.getSw() == 1) {
                    sw = MostrarAncestros(q.getLigaLista(), cedpersona, sw);
                }

                q = q.getLiga();
            }
        }
        return sw;
    }

    public boolean MostrarDescendientes(Nodo p, int cedpersona, boolean sw) {
        Nodo q = p;

        while (q != null && sw) {
            if (cedpersona == q.getCedula()) {
                sw = false;

                Nodo primerHijo;
                if (q == ancestro) {
                    primerHijo = ancestro.getLiga();
                } else if (q.getSw() == 1) {
                    primerHijo = q.getLigaLista().getLiga();
                } else {
                    primerHijo = null;
                }

                if (primerHijo == null) {
                    JOptionPane.showMessageDialog(null, "La persona no tiene descendientes");
                } else {
                    System.out.println("Descendientes:");

                    Deque<Nodo> pila = new ArrayDeque<>();
                    pila.push(primerHijo);

                    while (!pila.isEmpty()) {
                        Nodo actual = pila.pop();
                        while (actual != null) {
                            System.out.println(actual.getPersona().toString());
                            if (actual.getSw() == 1) {
                                pila.push(actual.getLiga());
                                actual = actual.getLigaLista().getLiga();
                            } else {
                                actual = actual.getLiga();
                            }
                        }
                    }
                }
            } else {
                if (q.getSw() == 1) {
                    sw = MostrarDescendientes(q.getLigaLista(), cedpersona, sw);
                }
                q = q.getLiga();
            }
        }
        return sw;
    }

    public void visualizarArbol() {
        if (ancestro == null) {
            System.out.println("Arbol vacio");
            return;
        }

        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println("========== ARBOL GENEALOGICO ==========");
        System.out.println(ancestro.getPersona().getNombre() + " (C.C. "
                + ancestro.getCedula() + ", "
                + f.format(ancestro.getPersona().getFecha_nacimiento()) + ")");

        Deque<Object[]> pila = new ArrayDeque<>();

        List<Nodo> hijosRaiz = new ArrayList<>();
        Nodo h = ancestro.getLiga();
        while (h != null) {
            hijosRaiz.add(h);
            h = h.getLiga();
        }
        for (int i = hijosRaiz.size() - 1; i >= 0; i--) {
            pila.push(new Object[]{hijosRaiz.get(i), "", i == hijosRaiz.size() - 1});
        }

        while (!pila.isEmpty()) {
            Object[] actual = pila.pop();
            Nodo p = (Nodo) actual[0];
            String prefijo = (String) actual[1];
            boolean esUltimo = (boolean) actual[2];

            System.out.println(prefijo + (esUltimo ? "`-- " : "|-- ")
                    + p.getPersona().getNombre() + " (C.C. " + p.getCedula() + ", "
                    + f.format(p.getPersona().getFecha_nacimiento()) + ")");

            String nuevoPrefijo = prefijo + (esUltimo ? "    " : "|   ");

            if (p.getSw() == 1) {
                List<Nodo> hijos = new ArrayList<>();
                Nodo hijo = p.getLigaLista().getLiga();
                while (hijo != null) {
                    hijos.add(hijo);
                    hijo = hijo.getLiga();
                }
                for (int i = hijos.size() - 1; i >= 0; i--) {
                    pila.push(new Object[]{hijos.get(i), nuevoPrefijo, i == hijos.size() - 1});
                }
            }
        }

        System.out.println("========================================");
    }

    public void mostrarNodoMayorGrado() {
        if (ancestro == null) {
            System.out.println("Arbol vacio");
            return;
        }

        int mejorCantidad = 0;
        Nodo h = ancestro.getLiga();
        while (h != null) {
            mejorCantidad++;
            h = h.getLiga();
        }
        Nodo mejor = ancestro;

        Deque<Nodo> pila = new ArrayDeque<>();
        Nodo primero = ancestro.getLiga();
        if (primero != null) {
            pila.push(primero);
        }

        while (!pila.isEmpty()) {
            Nodo actual = pila.pop();

            while (actual != null) {
                if (actual.getSw() == 1) {
                    Nodo primerHijo = actual.getLigaLista().getLiga();

                    int cantidad = 0;
                    Nodo x = primerHijo;
                    while (x != null) {
                        cantidad++;
                        x = x.getLiga();
                    }

                    if (cantidad > mejorCantidad) {
                        mejorCantidad = cantidad;
                        mejor = actual;
                    }

                    Nodo hermano = actual.getLiga();
                    if (hermano != null) {
                        pila.push(hermano);
                    }
                    actual = primerHijo;
                } else {
                    actual = actual.getLiga();
                }
            }
        }

        System.out.println("Persona con mayor numero de hijos directos (" + mejorCantidad + "):");
        System.out.println(mejor.getPersona().toString());
    }

    public void mostrarFamiliarMasJoven() {
        if (ancestro == null) {
            System.out.println("Arbol vacio");
            return;
        }

        Nodo masJoven = ancestro;

        Deque<Nodo> pila = new ArrayDeque<>();
        Nodo primero = ancestro.getLiga();
        if (primero != null) {
            pila.push(primero);
        }

        while (!pila.isEmpty()) {
            Nodo actual = pila.pop();

            while (actual != null) {
                if (actual.getPersona().getFecha_nacimiento().after(masJoven.getPersona().getFecha_nacimiento())) {
                    masJoven = actual;
                }

                if (actual.getSw() == 1) {
                    Nodo primerHijo = actual.getLigaLista().getLiga();
                    Nodo hermano = actual.getLiga();
                    if (hermano != null) {
                        pila.push(hermano);
                    }
                    actual = primerHijo;
                } else {
                    actual = actual.getLiga();
                }
            }
        }

        System.out.println("Familiar mas joven:");
        System.out.println(masJoven.getPersona().toString());
    }

    public void mostrarAlturaArbol() {
        if (ancestro == null) {
            System.out.println("Arbol vacio");
            return;
        }

        int alturaMax = 1;

        Deque<Object[]> pila = new ArrayDeque<>();
        Nodo primero = ancestro.getLiga();
        if (primero != null) {
            pila.push(new Object[]{primero, 2});
        }

        while (!pila.isEmpty()) {
            Object[] datos = pila.pop();
            Nodo actual = (Nodo) datos[0];
            int nivel = (int) datos[1];

            while (actual != null) {
                if (nivel > alturaMax) {
                    alturaMax = nivel;
                }

                if (actual.getSw() == 1) {
                    Nodo primerHijo = actual.getLigaLista().getLiga();
                    Nodo hermano = actual.getLiga();
                    if (hermano != null) {
                        pila.push(new Object[]{hermano, nivel});
                    }
                    actual = primerHijo;
                    nivel = nivel + 1;
                } else {
                    actual = actual.getLiga();
                }
            }
        }

        System.out.println("Altura del arbol (cantidad de generaciones): " + alturaMax);
    }

    public void actualizarPersona(Integer cedulaVieja, String nuevoNombre, Integer cedulaNueva, Date nuevaFecha) {
        if (ancestro == null) {
            System.out.println("Arbol vacio");
            return;
        }
        if (!existe(cedulaVieja)) {
            System.out.println("No existe una persona con esa cedula");
            return;
        }
        if (!cedulaVieja.equals(cedulaNueva) && existe(cedulaNueva)) {
            System.out.println("Ya existe otra persona con la nueva cedula");
            return;
        }

        Nodo padre = null;
        Nodo nodo;
        if (ancestro.getCedula().equals(cedulaVieja)) {
            nodo = ancestro;
        } else {
            padre = buscarPadre(ancestro, cedulaVieja);
            nodo = buscarEnLista(padre, cedulaVieja);
        }

        Persona persona = nodo.getPersona();
        persona.setNombre(nuevoNombre);
        persona.setFecha_nacimiento(nuevaFecha);

        if (!cedulaVieja.equals(cedulaNueva)) {
            persona.setCedula(cedulaNueva);

            if (padre != null) {
                Nodo ant = padre;
                while (ant.getLiga() != nodo) {
                    ant = ant.getLiga();
                }
                ant.setLiga(nodo.getLiga());

                ant = padre;
                Nodo q = padre.getLiga();
                while (q != null && q.getCedula() < nodo.getCedula()) {
                    ant = q;
                    q = q.getLiga();
                }
                nodo.setLiga(q);
                ant.setLiga(nodo);
            }
        }

        System.out.println("Persona actualizada correctamente:");
        System.out.println(persona.toString());
    }

    public Boolean existeCedula(Integer ced) {
        return ancestro != null && existe(ced);
    }

    public boolean MostrarSobrinosDelPadre(Nodo p, int cedPadre, boolean sw) {
        Nodo q = p;
        while (q != null && sw) {
            if (cedPadre == q.getCedula()) {
                sw = false;
                int count = 0;
                Nodo tio = p.getLiga();
                while (tio != null) {
                    if (tio.getCedula() != cedPadre && tio.getSw() == 1) {
                        Nodo primo = tio.getLigaLista().getLiga();
                        while (primo != null) {
                            if (count == 0) {
                                System.out.println("Primos: ");
                            }
                            System.out.println(primo.getPersona().toString());
                            count++;
                            primo = primo.getLiga();
                        }
                    }
                    tio = tio.getLiga();
                }
                if (count == 0) {
                    JOptionPane.showMessageDialog(null, "La persona no tiene primos");
                }
            } else {
                if (q.getSw() == 1) {
                    sw = MostrarSobrinosDelPadre(q.getLigaLista(), cedPadre, sw);
                }
                q = q.getLiga();
            }
        }
        return sw;
    }
}
