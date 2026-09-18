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

    // Inicializa el arbol sin un ancestro registrado.
    public Lista() {
        ancestro = null;
    }

    // Retorna la raiz que da acceso a toda la lista generalizada.
    public Nodo getAncestro() {
        return ancestro;
    }

    // Reemplaza la referencia a la raiz del arbol genealogico.
    public void setAncestro(Nodo ancestro) {
        this.ancestro = ancestro;
    }

    // La primera persona se convierte en ancestro; las demas se insertan
    // como hijos del padre indicado, siempre que su cedula no este repetida.
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
    // Comprueba la raiz y, si es necesario, toda su descendencia.
    private Boolean existe(Integer ced) {
        return ancestro.getCedula().equals(ced) || esDescendiente(ancestro, ced);
    }
    
    // Busca una cedula dentro de todas las ramas que nacen de padre.
    private Boolean esDescendiente(Nodo padre, Integer ced) {
        Boolean sw = false;
        Nodo q = padre.getLiga();
        while (q != null && sw == false) {
            if (q.getCedula().equals(ced)) {
                sw = true;
            } else {
                if (q.getSw() == 1) {
                    // Se usa la cabecera de la sublista para conservar como punto
                    // de partida a la persona que encabeza esa rama.
                    sw = esDescendiente(q.getLigaLista(), ced);
                }
            }
            q = q.getLiga();
        }
        return sw;
    }
    // Recorre recursivamente las listas de hermanos hasta localizar al padre.
    // Inserta el nuevo hijo por cedula de menor a mayor y convierte al padre en
    // sublista cuando recibe su primer descendiente.
    private Boolean insertar(Nodo p, Persona persona, Integer cedula) {
        Boolean sw = false;
        Boolean swL = false;
        while (p != null && sw == false && swL == false) {
            if (p.getCedula().equals(cedula)) {
                swL = true;
            } else {
                if (p.getSw() == 1) {
                    // Se omite la cabecera de la sublista porque representa al padre;
                    // la busqueda debe continuar desde su primer hijo.
                    sw = insertar(p.getLigaLista().getLiga(), persona, cedula);
                }
                p = p.getLiga();
            }
        }
        if (swL == true) {
            Nodo dato = new Nodo(persona, null);
            if (p.getSw() == 1) {
                // Si el padre ya tiene hijos, su nodo apunta a una sublista cuya
                // cabecera es el propio padre y cuya liga inicia la lista de hijos.
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
                    // Una hoja se transforma en nodo de sublista al recibir su
                    // primer hijo; la persona pasa a la nueva cabecera.
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

    // Recorre en profundidad la lista generalizada e imprime cada persona.
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

    // Busca la cedula en profundidad. La cabecera p de la lista donde se
    // encuentra el dato representa a su padre.
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
                System.out.println("El padre es \n");
                System.out.println(p.getPersona().toString());
            }

        }
        return sw;

    }

    // Inicia la busqueda del nivel, considerando al ancestro en el nivel 1.
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

    // Recorre hermanos y sublistas; devuelve cero si la cedula no aparece
    // o el nivel acumulado cuando encuentra a la persona.
    public int mostrarNivelCed(Nodo p, Integer ced, Integer nivel) {
        int res = 0;
        while (p != null && res == 0) {
            if (p.getCedula().equals(ced)) {
                res = nivel;
            } else {
                if (p.getSw() == 1) {
                    // Cada llamada recursiva aumenta el nivel porque entra a la
                    // lista de hijos de la persona actual.
                    res = mostrarNivelCed(p.getLigaLista().getLiga(), ced, nivel + 1);
                }
                p = p.getLiga();
            }
        }
        return res;
    }

    // Valida el caso del ancestro e inicia la busqueda de los hermanos.
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

    // Localiza a la persona y, desde la cabecera de su lista familiar,
    // imprime todos los hijos del mismo padre excepto la cedula consultada.
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

    // Inicia en el nivel 1 y controla el mensaje cuando el nivel no existe.
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

    // Recorre todas las ramas hasta alcanzar la generacion solicitada.
    // Al llegar a ella imprime la lista completa de hermanos de esa rama.
    public Boolean mostrarInfoNiv(Nodo p, int nivelBus, int nivel) {
        Boolean sw = false;

        if (p != null && nivel == nivelBus) {

            // Todos los nodos enlazados por liga en este punto son hermanos y,
            // por tanto, pertenecen al mismo nivel.
            while (p != null) {
                System.out.println(p.getPersona().toString());
                p = p.getLiga();
            }
            sw = true;
        } else {
            while (p != null) {
                if (p.getSw() == 1) {
                    // Se explora cada sublista porque el nivel puede aparecer en
                    // varias ramas distintas del arbol.
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

    // Calcula la maxima profundidad y muestra las personas ubicadas alli.
    public void mostrarMasProfundo() {
        if (ancestro == null) {
            System.out.println("Arbol vacio");
        } else {
            int max = nivelMax(ancestro.getLiga(), 2);
            System.out.println("Nivel mas profundo: " + max);
            mostrarInfoNiv(ancestro, max);
        }
    }

    // Obtiene recursivamente el mayor nivel alcanzado por cualquier rama.
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

    // Desenlaza x de la lista de hijos y lo vuelve a insertar de menor a mayor
    // segun su cedula.
    public void ordenar(Nodo x, Nodo padre) {
        Nodo ant, q;
        ant = padre;
        while (ant.getLiga() != x) {
            ant = ant.getLiga();
        }
        ant.setLiga(x.getLiga());

        ant = padre;
        q = padre.getLiga();
        while (q != null && q.getCedula() < x.getCedula()) {
            ant = q;
            q = q.getLiga();
        }

        x.setLiga(q);
        ant.setLiga(x);
    }

    // Coloca al hijo de mayor edad al inicio de la lista para que pueda ocupar
    // el lugar del padre sin relacionar la edad con el orden de las cedulas.
    private void colocarHijoMayorEdadAlInicio(Nodo padre) {
        Nodo primero = padre.getLiga();

        if (primero != null) {
            Nodo mayorEdad = primero;
            Nodo anteriorMayorEdad = padre;
            Nodo anterior = primero;
            Nodo actual = primero.getLiga();

            while (actual != null) {
                // Una fecha de nacimiento anterior corresponde a una persona
                // de mayor edad.
                if (actual.getPersona().getFecha_nacimiento().before(
                        mayorEdad.getPersona().getFecha_nacimiento())) {
                    mayorEdad = actual;
                    anteriorMayorEdad = anterior;
                }
                anterior = actual;
                actual = actual.getLiga();
            }

            if (mayorEdad != primero) {
                anteriorMayorEdad.setLiga(mayorEdad.getLiga());
                mayorEdad.setLiga(primero);
                padre.setLiga(mayorEdad);
            }
        }
    }

    // Elimina una hoja directamente. Si p tiene descendencia, su hijo de mayor
    // edad ocupa su lugar y conserva el resto del linaje.
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
                    // En un nodo con hijos, la cabecera de la sublista contiene
                    // la informacion de la persona que se va a reemplazar.
                    s = p.getLigaLista();
                }

                // La lista se ajusta temporalmente para que el hijo de mayor edad
                // sea quien ascienda en la jerarquia.
                colocarHijoMayorEdadAlInicio(s);
                q = s.getLiga();

                if (q != null) {
                    // La cabecera conserva su posicion, pero adopta los datos del
                    // hijo promovido para no desconectar el resto de la rama.
                    s.setPersona(q.getPersona());

                    if (q.getSw() == 1) {
                        // Si el hijo promovido tambien era padre, su sublista debe
                        // integrarse y reordenarse en la nueva posicion.
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

    // Busca una persona por su cedula y la elimina conservando su descendencia.
    public void eliminarPersona(Integer cedula) {
        if (ancestro == null) {
            System.out.println("Arbol vacio");
        } else if (!existe(cedula)) {
            System.out.println("No existe una persona con la cedula " + cedula);
        } else if (ancestro.getCedula().equals(cedula)) {
            eliminar(ancestro, ancestro);
            System.out.println("Persona eliminada correctamente");
        } else {
            Nodo padre = buscarPadre(ancestro, cedula);
            Nodo persona = buscarEnLista(padre, cedula);
            Boolean teniaHijos = persona.getSw() == 1;

            eliminar(persona, padre);

            // Al promover a un hijo cambia la cedula representada por el nodo;
            // por eso debe recuperar su posicion en la lista de hermanos.
            if (teniaHijos == true) {
                ordenar(persona, padre);
            }

            System.out.println("Persona eliminada correctamente");
        }
    }

    // Inicia la eliminacion de una generacion y reporta si no fue hallada.
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

    // Elimina de derecha a izquierda los nodos del nivel para evitar perder
    // enlaces; cuando hay sublista, reubica la rama que asciende.
    private void eliminarLista(Nodo x, Nodo padre) {
        if (x != null) {
            // Se procesa primero el siguiente hermano para que los cambios de
            // enlaces no impidan visitar los demas nodos del nivel.
            eliminarLista(x.getLiga(), padre);
            Boolean teniaSublista = x.getSw() == 1;
            eliminar(x, padre);
            if (teniaSublista == true) {
                // La rama que ascendio tras eliminar al padre vuelve a ubicarse
                // segun el orden de cedulas de la lista superior.
                ordenar(x, padre);
            }
        }
    }

    // Busca el nivel solicitado. Al encontrarlo elimina sus nodos, mientras
    // que los descendientes se conectan con el abuelo y ascienden un nivel.
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
                        // Si la sublista queda sin hijos, el nodo deja de ser una
                        // sublista y vuelve a almacenar directamente a la persona.
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


    
    // Valida las cedulas y muestra el ascendiente compartido mas cercano.
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

    // Desciende mientras ambas personas permanezcan dentro de una misma
    // sublista; la ultima cabecera comun es el ancestro mas cercano.
    private Nodo ancestroComun(Nodo padre, Integer a, Integer b) {
        Nodo res = padre;
        Boolean sw = false;
        Nodo q = padre.getLiga();
        while (q != null && sw == false) {
            if (q.getSw() == 1) {
                Nodo sub = q.getLigaLista();
                // Solo se desciende por una rama cuando ambas personas siguen
                // estando dentro de ella; asi se conserva el ancestro mas cercano.
                if (esDescendiente(sub, a) && esDescendiente(sub, b)) {
                    res = ancestroComun(sub, a, b);
                    sw = true;
                }
            }
            q = q.getLiga();
        }
        return res;
    }

    // Devuelve la cabecera de la lista que contiene como hijo a la cedula.
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

    // Busca un nodo solamente entre los hijos directos de padre.
    private Nodo buscarEnLista(Nodo padre, Integer ced) {
        Nodo q = padre.getLiga();
        while (q != null && !q.getCedula().equals(ced)) {
            q = q.getLiga();
        }
        return q;
    }

    // Valida la adopcion: A debe existir, no puede ser la raiz y B no puede
    // pertenecer a la descendencia de A porque se produciria un ciclo.
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
                    Nodo PadreAntiguo = buscarPadre(ancestro, a);
                    Nodo nodoA = buscarEnLista(PadreAntiguo, a);

                    if (nodoA.getSw() == 1 && esDescendiente(nodoA.getLigaLista(), b)) {
                        System.out.println("B es descendiente de A, no se puede");
                    } else {
                        if (PadreAntiguo.getCedula().equals(b)) {
                            System.out.println("B ya es el padre de A");
                        } else {
                            moverRama(nodoA, PadreAntiguo, b);
                            System.out.println("Rama trasladada");
                        }
                    }
                }
            }
        }
    }

    // Desconecta a A de su padre, ajusta al padre anterior si queda sin hijos
    // y enlaza la rama completa en la lista de hijos de B segun su cedula.
    private void moverRama(Nodo nodoA, Nodo PadreAntiguo, Integer b) {
        Nodo ant = PadreAntiguo;
        while (ant.getLiga() != nodoA) {
            ant = ant.getLiga();
        }
        // Se retira A de la lista de hermanos sin modificar su posible sublista,
        // por lo que toda su descendencia viaja junto con el nodo.
        ant.setLiga(nodoA.getLiga());
        nodoA.setLiga(null);

        if (PadreAntiguo.getLiga() == null && PadreAntiguo != ancestro) {
            // Si el padre anterior queda sin hijos, deja de representarse como
            // sublista y vuelve a ser un nodo hoja dentro de la lista del abuelo.
            Nodo Abuelo = buscarPadre(ancestro, PadreAntiguo.getCedula());
            Nodo nodoPadre = buscarEnLista(Abuelo, PadreAntiguo.getCedula());
            nodoPadre.setPersona(PadreAntiguo.getPersona());
            nodoPadre.setLigaLista(null);
            nodoPadre.setSw(0);
        }

        Nodo B;
        if (ancestro.getCedula().equals(b)) {
            B = ancestro;
        } else {
            Nodo PadreB = buscarPadre(ancestro, b);
            Nodo nodoB = buscarEnLista(PadreB, b);
            if (nodoB.getSw() == 1) {
                B = nodoB.getLigaLista();
            } else {
                // Si B era una hoja, se crea la cabecera que permitira enlazar
                // a A como su primer hijo.
                B = new Nodo(nodoB.getPersona(), null);
                nodoB.setLigaLista(B);
                nodoB.setSw(1);
                nodoB.setPersona(null);
            }
        }

        ant = B;
        Nodo q = B.getLiga();
        // Se localiza la posicion de A de menor a mayor cedula dentro de sus
        // nuevos hermanos antes de volver a enlazar la rama completa.
        while (q != null && q.getCedula() < nodoA.getCedula()) {
            ant = q;
            q = q.getLiga();
        }
        nodoA.setLiga(q);
        ant.setLiga(nodoA);
    }

    // Busca al padre y recorre unicamente su lista de hijos directos.
    // Retorna true cuando la persona no fue encontrada.
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

    // Localiza a la persona y muestra los demas integrantes de la misma
    // lista de hijos, es decir, quienes comparten su padre.
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

    // Cuenta los hijos de la cabecera p, excluyendo a la persona consultada.
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

    // Busca a la persona, obtiene la cedula de su padre mediante la cabecera
    // de la lista y solicita los hermanos de ese padre.
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

    // Localiza al padre y muestra sus hermanos dentro de la lista del abuelo.
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

    // Recorre los hermanos de la persona y muestra los hijos directos de
    // aquellos que tengan una sublista de descendientes.
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

    // Obtiene al padre de la persona para buscar los hijos de sus hermanos.
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

    // Localiza a la persona y recorre su linea ascendente directa mediante
    // busquedas sucesivas del padre, hasta alcanzar el ancestro principal.
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

    // Localiza a la persona y recorre en profundidad toda su descendencia.
    // La pila conserva las ramas de hermanos pendientes durante el recorrido.
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

                    // La pila guarda los hermanos pendientes cuando el recorrido
                    // baja primero por la sublista de descendientes de un nodo.
                    while (!pila.isEmpty()) {
                        Nodo actual = pila.pop();
                        while (actual != null) {
                            System.out.println(actual.getPersona().toString());
                            if (actual.getSw() == 1) {
                                // ArrayDeque no admite null; solo se guarda el
                                // hermano cuando realmente existe.
                                if (actual.getLiga() != null) {
                                    pila.push(actual.getLiga());
                                }
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

    // Imprime la jerarquia completa con prefijos que representan las ramas.
    // Los hijos se apilan en orden inverso para mostrarlos en su orden original.
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
            // Se apilan en orden inverso porque la pila extrae primero el ultimo
            // elemento insertado y deben imprimirse en el orden original.
            pila.push(new Object[]{hijosRaiz.get(i), "", i == hijosRaiz.size() - 1});
        }

        while (!pila.isEmpty()) {
            // Cada arreglo conserva el nodo, el prefijo visual acumulado y si
            // corresponde al ultimo hijo de su lista.
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
                    // El nuevo prefijo mantiene visibles las conexiones con las
                    // generaciones superiores al imprimir cada hijo.
                    pila.push(new Object[]{hijos.get(i), nuevoPrefijo, i == hijos.size() - 1});
                }
            }
        }

        System.out.println("========================================");
    }

    // Recorre todo el arbol y compara la cantidad de hijos directos de cada
    // persona para conservar el nodo con el mayor grado encontrado.
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

                    // El hermano se guarda para visitarlo despues de terminar la
                    // rama que comienza en el primer hijo.
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

    // Recorre todas las ramas y conserva la persona cuya fecha de nacimiento
    // sea mas reciente, es decir, la de menor edad.
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

    // Recorre el arbol guardando el nivel de cada rama pendiente y conserva
    // el nivel maximo como la cantidad total de generaciones.
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
            // Cada entrada relaciona el primer nodo pendiente de una rama con el
            // nivel al que pertenece dentro del arbol.
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
                        // El hermano conserva el nivel actual; solamente los hijos
                        // se procesan en el nivel siguiente.
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

    // Valida la existencia de la persona y que la nueva cedula no se repita;
    // despues actualiza sus datos y, si cambia la cedula, reordena su nodo
    // dentro de la lista de hermanos correspondiente.
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
                // Primero se extrae el nodo de su posicion actual para evitar
                // romper la lista mientras se busca su nueva ubicacion.
                ant.setLiga(nodo.getLiga());

                ant = padre;
                Nodo q = padre.getLiga();
                while (q != null && q.getCedula() < nodo.getCedula()) {
                    ant = q;
                    q = q.getLiga();
                }
                // Finalmente se vuelve a enlazar respetando el orden por cedula.
                nodo.setLiga(q);
                ant.setLiga(nodo);
            }
        }

        System.out.println("Persona actualizada correctamente:");
        System.out.println(persona.toString());
    }

    // Expone la busqueda de cedula evitando consultar una estructura vacia.
    public Boolean existeCedula(Integer ced) {
        return ancestro != null && existe(ced);
    }

    // Localiza al padre y recorre los hijos de sus hermanos; esos nodos son
    // los primos de la persona que origino la consulta.
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
