package com.mycompany.personas;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

public class Personas {
    // Crea el arbol y mantiene activo el menu hasta que el usuario cancele
    // o seleccione la opcion de salida.
    public static void main(String[] args) {
        Lista lista = new Lista();
        Boolean salir = false;

        while (salir == false) {
            String opcion = JOptionPane.showInputDialog(
                    "1. Insertar persona\n"
                    + "2. Mostrar padre\n"
                    + "3. Mostrar todo\n"
                    + "4. Mostrar nivel de una persona\n"
                    + "5. Mostrar hermanos\n"
                    + "6. Mostrar personas de un nivel\n"
                    + "7. Mostrar nivel mas profundo\n"
                    + "8. Eliminar por nivel\n"
                    + "9. Ancestro comun mas cercano\n"
                    + "10. Trasladar rama (adopcion)\n"
                    + "11. Cargar datos de prueba\n"
                    + "12. Para mostrar todos los hijos\n"
                    + "13. Para mostrar todos los hermanos\n"
                    + "14. Para mostrar los tios\n"
                    + "15. Para mostrar los sobrinos\n"
                    + "16. Para mostrar los primos\n"
                    + "17. Para mostrar los Ancestros\n"
                    + "18. Para mostrar los Descendientes\n"
                    + "19. Visualizar arbol completo\n"
                    + "20. Visualizar la persona con el mayor numero de hijos directos\n"
                    + "21. Visualizar la persona con la menor edad\n"
                    + "22. Visualizar la cantidad de generaciones\n"
                    + "23. Actualizar Persona\n"
                    + "24. Eliminar persona por cedula\n"
                    + "0. Salir");

            if (opcion == null || opcion.trim().equals("0")) {
                salir = true;
            } else {
                ejecutar(lista, opcion.trim());
            }
        }
    }
    // Dirige la opcion elegida hacia la operacion correspondiente y solicita
    // solamente los datos necesarios para ejecutarla.
    private static void ejecutar(Lista lista, String opcion) {
        Integer ced, ced2, nivel;

        switch (opcion) {
            case "1":
                insertar(lista);
                break;
            case "2":
                ced = leerEntero("Cedula de la persona para encontrar su padre:");
                if (ced != null) {
                    Boolean sw = lista.mostrarInformacionPadre(lista.getAncestro(), ced, false);
                    if (sw == false) {
                        System.out.println("No se encontro la persona " + ced);
                    }
                }
                break;
            case "3":
                if (lista.getAncestro() == null) {
                    System.out.println("Arbol vacio");
                } else {
                    lista.mostrarTodo(lista.getAncestro());
                }
                break;
            case "4":
                ced = leerEntero("Cedula de la persona para saber su nivel:");
                if (ced != null) {
                    lista.mostrarNivelCed(lista.getAncestro(), ced);
                }
                break;
            case "5":
                ced = leerEntero("Cedula de la persona para mostrar sus hermanos:");
                if (ced != null) {
                    lista.mostrarInfoGen(lista.getAncestro(), ced);
                }
                break;
            case "6":
                nivel = leerNivel("Nivel que desea mostrar:");
                if (nivel != null) {
                    lista.mostrarInfoNiv(lista.getAncestro(), nivel);
                }
                break;
            case "7":
                lista.mostrarMasProfundo();
                break;
            case "8":
                nivel = leerNivel("Nivel que desea eliminar:");
                if (nivel != null) {
                    lista.eliminarPorNIvel(nivel);
                }
                break;
            case "9":
                ced = leerEntero("Cedula de la primera persona:");
                if (ced != null) {
                    ced2 = leerEntero("Cedula de la segunda persona:");
                    if (ced2 != null) {
                        lista.ancestroComun(ced, ced2);
                    }
                }
                break;
            case "10":
                ced = leerEntero("Cedula de la persona A (la que se traslada):");
                if (ced != null) {
                    ced2 = leerEntero("Cedula de la persona B (nuevo padre):");
                    if (ced2 != null) {
                        lista.trasladarRama(ced, ced2);
                    }
                }
                break;
            case "11":
                if (lista.getAncestro() != null) {
                    JOptionPane.showMessageDialog(null, "El arbol ya tiene datos, no se cargan los de prueba");
                } else {
                    cargarDatosPrueba(lista);
                }
                break;

            case "12":
                ced = leerEntero("Ingrese la cedula del padre para mostrar los hijos");
                if (ced != null) {
                    if (lista.MostrarTodosLosHijos(lista.getAncestro(), ced, true)) {
                        JOptionPane.showMessageDialog(null, "Persona no encontrada");
                    }
                }
                break;
            case "13":
                ced = leerEntero("Ingrese la cedula de el hermano para mostrar los hermanos");
                if (ced != null) {
                    if (lista.MostrarHermanos(lista.getAncestro(), ced, true)) {
                        JOptionPane.showMessageDialog(null, "Persona no encontrada");
                    }
                }
                break;
            case "14":
                ced = leerEntero("Ingrese la cedula de la persona para mostrar los tios");
                if (ced != null) {
                    if (lista.MostrarTios(lista.getAncestro(), ced, true)) {
                        JOptionPane.showMessageDialog(null, "Persona no encontrada");
                    }
                }
                break;
            case "15":
                ced = leerEntero("Ingrese la cedula de la persona para mostrar los sobrinos");
                if (ced != null) {
                    if (lista.MostrarSobrinos(lista.getAncestro(), ced, true)) {
                        JOptionPane.showMessageDialog(null, "Persona no encontrada");
                    }
                }
                break;
            case "16":
                ced = leerEntero("Ingrese la cedula de la persona para mostrar los primos");
                if (ced != null) {
                    if (lista.MostrarPrimos(lista.getAncestro(), ced, true)) {
                        JOptionPane.showMessageDialog(null, "Persona no encontrada");
                    }
                }
                break;
            case "17":
                ced = leerEntero("Ingrese la cedula de la persona para mostrar los Ancestros");
                if (ced != null) {
                    if (lista.MostrarAncestros(lista.getAncestro(), ced, true)) {
                        JOptionPane.showMessageDialog(null, "Persona no encontrada");
                    }
                }
                break;
            case "18":
                ced = leerEntero("Ingrese la cedula de la persona para mostrar los Descendientes");
                if (ced != null) {
                    if (lista.MostrarDescendientes(lista.getAncestro(), ced, true)) {
                        JOptionPane.showMessageDialog(null, "Persona no encontrada");
                    }
                }
                break;
            case "19":
                lista.visualizarArbol();
                break;
            case "20":
                lista.mostrarNodoMayorGrado();
                break;
            case "21":
                lista.mostrarFamiliarMasJoven();
                break;
            case "22":
                lista.mostrarAlturaArbol();
                break;
            case "23":
                actualizarPersona(lista);
                break;
            case "24":
                ced = leerEntero("Cedula de la persona que desea eliminar:");
                if (ced != null) {
                    lista.eliminarPersona(ced);
                }
                break;

            default:
                JOptionPane.showMessageDialog(null, "Elija una opcion entre 0 y 24");
        }
    }
    // Solicita y valida los nuevos datos antes de delegar la actualizacion
    // y el posible reordenamiento del nodo a la estructura Lista.
    private static void actualizarPersona(Lista lista) {
        if (lista.getAncestro() == null) {
            JOptionPane.showMessageDialog(null, "Arbol vacio");
            return;
        }

        Integer cedVieja = leerEntero("Cedula de la persona a actualizar:");
        if (cedVieja == null) {
            return;
        }
        if (!lista.existeCedula(cedVieja)) {
            JOptionPane.showMessageDialog(null, "No existe una persona con esa cedula");
            return;
        }

        String nuevoNombre = JOptionPane.showInputDialog("Nuevo nombre:");
        if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
            return;
        }

        Integer cedNueva = leerEntero("Nueva cedula:");
        if (cedNueva == null) {
            return;
        }
        if (cedNueva <= 0) {
            JOptionPane.showMessageDialog(null, "La cedula debe ser positiva");
            return;
        }

        String fechaStr = JOptionPane.showInputDialog("Nueva fecha de nacimiento (dd/MM/yyyy):");
        if (fechaStr == null) {
            return;
        }

        try {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            formato.setLenient(false);
            Date nuevaFecha = formato.parse(fechaStr.trim());
            if (nuevaFecha.after(new Date())) {
                JOptionPane.showMessageDialog(null, "La fecha no puede ser futura");
            } else {
                lista.actualizarPersona(cedVieja, nuevoNombre.trim(), cedNueva, nuevaFecha);
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Fecha invalida. Use dd/MM/yyyy");
        }
    }

    // ---------- LECTURA DE DATOS ----------
    // Devuelve null si cancela o si no escribe un numero
    private static Integer leerEntero(String mensaje) {
        Integer num = null;
        String texto = JOptionPane.showInputDialog(mensaje);
        if (texto != null) {
            try {
                num = Integer.parseInt(texto.trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Debe digitar un numero entero");
            }
        }
        return num;
    }
// Lee y valida que el nivel pertenezca al rango positivo del arbol.
    private static Integer leerNivel(String mensaje) {
        Integer nivel = leerEntero(mensaje);
        if (nivel != null && nivel < 1) {
            JOptionPane.showMessageDialog(null, "El nivel debe ser mayor o igual a 1");
            nivel = null;
        }
        return nivel;
    }
    // Solicita padre solo cuando ya existe una raiz; luego crea la persona
    // y delega en Lista su insercion dentro de la estructura.
    private static void insertar(Lista lista) {
        Integer cedPadre = 0;
        Boolean seguir = true;
        // si el arbol esta vacio, la persona sera el ancestro y no se pide padre
        if (lista.getAncestro() != null) {
            cedPadre = leerEntero("Cedula del padre donde va a insertar:");
            if (cedPadre == null) {
                seguir = false;
            }
        }

        if (seguir == true) {
            Persona persona = crearPersona();
            if (persona != null) {
                lista.insertarPersona(lista.getAncestro(), persona, cedPadre);
            }
        }
    }
    // Construye una persona a partir de datos validados: nombre no vacio,
    // cedula positiva y fecha real que no sea posterior al dia actual.
    public static Persona crearPersona() {
        Persona persona = null;

        String nombre = JOptionPane.showInputDialog("Digite el nombre:");
        if (nombre != null && !nombre.trim().isEmpty()) {
            Integer cedula = leerEntero("Digite la cedula:");
            if (cedula != null) {
                if (cedula <= 0) {
                    JOptionPane.showMessageDialog(null, "La cedula debe ser positiva");
                } else {
                    String fechaStr = JOptionPane.showInputDialog("Fecha de nacimiento (dd/MM/yyyy):");
                    if (fechaStr != null) {
                        try {
                            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                            formato.setLenient(false);
                            Date fecha = formato.parse(fechaStr.trim());
                            if (fecha.after(new Date())) {
                                JOptionPane.showMessageDialog(null, "La fecha no puede ser futura");
                            } else {
                                persona = new Persona(nombre.trim(), cedula, fecha);
                            }
                        } catch (ParseException e) {
                            JOptionPane.showMessageDialog(null, "Fecha invalida. Use dd/MM/yyyy");
                        }
                    }
                }
            }
        }
        return persona;
    }

    // ---------- DATOS DE PRUEBA ----------
    // Convierte una fecha textual valida en el objeto usado por los datos de prueba.
    private static Persona nuevaPersona(String nombre, int cedula, String fecha) {
        Persona persona = null;
        try {
            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
            f.setLenient(false);
            persona = new Persona(nombre, cedula, f.parse(fecha));
        } catch (ParseException e) {
            System.out.println("Fecha mal escrita en datos de prueba: " + fecha);
        }
        return persona;
    }

    public static void cargarDatosPrueba(Lista lista) {
        // Forma un arbol de varias generaciones para ejercitar inserciones,
        // ordenamiento por cedula, recorridos y operaciones entre ramas.
        Nodo a = null; // solo para legibilidad, siempre se pasa lista.getAncestro()

        // Nivel 1: ancestro (la cedula del padre se ignora porque el arbol esta vacio)
        lista.insertarPersona(lista.getAncestro(), nuevaPersona("Antonio Ramirez", 1000, "05/03/1940"), 0);

        // Nivel 2: hijos de 1000, insertados desordenados a proposito
        lista.insertarPersona(lista.getAncestro(), nuevaPersona("Beatriz Ramirez", 1500, "12/07/1965"), 1000);
        lista.insertarPersona(lista.getAncestro(), nuevaPersona("Bernardo Ramirez", 1200, "30/01/1963"), 1000);
        lista.insertarPersona(lista.getAncestro(), nuevaPersona("Carmen Ramirez", 1800, "22/11/1970"), 1000);

        // Nivel 3: hijos de 1200 (el primero convierte a 1200 en sublista)
        lista.insertarPersona(lista.getAncestro(), nuevaPersona("Daniel Ramirez", 1250, "14/04/1990"), 1200);
        lista.insertarPersona(lista.getAncestro(), nuevaPersona("Diana Ramirez", 1210, "09/09/1988"), 1200);
        lista.insertarPersona(lista.getAncestro(), nuevaPersona("David Ramirez", 1290, "02/02/1995"), 1200);

        // Nivel 3: hijos de 1500
        lista.insertarPersona(lista.getAncestro(), nuevaPersona("Elena Soto", 1560, "18/06/1992"), 1500);
        lista.insertarPersona(lista.getAncestro(), nuevaPersona("Esteban Soto", 1510, "25/12/1989"), 1500);

        // Nivel 4: hijos de 1250 (obliga a la recursion dentro de una sublista)
        lista.insertarPersona(lista.getAncestro(), nuevaPersona("Fernanda Ramirez", 1258, "07/07/2015"), 1250);
        lista.insertarPersona(lista.getAncestro(), nuevaPersona("Felipe Ramirez", 1252, "03/03/2013"), 1250);


        JOptionPane.showMessageDialog(null, "Datos de prueba cargados (11 personas).");
    }
}
