package com.mycompany.personas;

import com.mycompany.personas.Lista;
import com.mycompany.personas.Persona;
import static java.lang.Integer.parseInt;
import javax.swing.JOptionPane;
import java.util.Date;                    // ← FALTABA
import java.text.SimpleDateFormat;        // ← FALTABA

/**
 *
 * @author wesly
 */
public class Personas {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        Lista lista = new Lista();
        String opcion;

        do {
            opcion = JOptionPane.showInputDialog(
                "1. Para insertar al principio\n"
              + "2. Para insertar al final\n"
              + "3. Para mostrar\n"
              + "4. Para eliminar\n"
                        + "5. Para \n"
                        + "6. Para insertar datos prueba\n"
              + "5. Para salir");

            if (opcion == null) {
                break;
            }

            String valor;
            Boolean sw=false;
            Integer cedula;
            switch (opcion) {
                case "1":
                    cedula = parseInt(JOptionPane.showInputDialog("Digite la cedula del padre donde va insertar"));
                    Persona persona = crearPersona();
                   
                    if (persona != null) {
                        lista.insertarPersona(lista.getAncestro(), persona, cedula);
                    }
                   
                    break;
                case "2":
                    cedula = parseInt(JOptionPane.showInputDialog("Digite la cedula de la persona , para encontrar al padre "));

                    sw = lista.mostrarInformacionPadre(lista.getAncestro(), cedula, sw);
                    if(sw==false){
                        System.out.println("No se encontro el dato");
                    }
                case "3":
                    lista.mostrarTodo(lista.getAncestro());
                    break;
                case "4":
                    break;
                case "5":
                    break;
                case "6":
                    cargarDatosPrueba(lista);
                    break;   
                default:
                    JOptionPane.showMessageDialog(null, "Elija una opcion entre 1 y 5");
            }
        } while (!opcion.equals("5"));
    }

    // ← EL MÉTODO DEBE ESTAR AQUÍ DENTRO DE LA CLASE
    public static Persona crearPersona() {
        try {
            String nombre = JOptionPane.showInputDialog("Digite el nombre:");

            String cedulaStr = JOptionPane.showInputDialog("Digite la cédula:");
            Integer cedula = Integer.parseInt(cedulaStr);

            String fechaStr = JOptionPane.showInputDialog("Digite la fecha de nacimiento (dd/MM/yyyy):");
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            formato.setLenient(false);
            Date fecha = formato.parse(fechaStr);

            return new Persona(nombre, cedula, fecha);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: La cédula debe ser un número.");
            return null;
        } catch (java.text.ParseException e) {
            JOptionPane.showMessageDialog(null, "Error: Fecha inválida. Use dd/MM/yyyy.");
            return null;
        }
    }
    // ---------- DATOS DE PRUEBA ----------

private static Persona nuevaPersona(String nombre, int cedula, String fecha) {
    try {
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        f.setLenient(false);
        return new Persona(nombre, cedula, f.parse(fecha));
    } catch (java.text.ParseException e) {
        return null; // no debería pasar con las fechas de abajo
    }
}

public static void cargarDatosPrueba(Lista lista) {

    // Nivel 0: ancestro (la cédula del padre se ignora cuando la lista está vacía)
    lista.insertarPersona(lista.getAncestro(), nuevaPersona("Antonio Ramirez", 1000, "05/03/1940"), 0);

    // Nivel 1: hijos de 1000  -> se insertan desordenados a propósito
    lista.insertarPersona(lista.getAncestro(), nuevaPersona("Beatriz Ramirez", 1500, "12/07/1965"), 1000);
    lista.insertarPersona(lista.getAncestro(), nuevaPersona("Bernardo Ramirez", 1200, "30/01/1963"), 1000);
    lista.insertarPersona(lista.getAncestro(), nuevaPersona("Carmen Ramirez",  1800, "22/11/1970"), 1000);

    // Nivel 2: hijos de 1200  -> el primero convierte el nodo 1200 en sublista (sw = 1)
    lista.insertarPersona(lista.getAncestro(), nuevaPersona("Daniel Ramirez", 1250, "14/04/1990"), 1200);
    lista.insertarPersona(lista.getAncestro(), nuevaPersona("Diana Ramirez",  1210, "09/09/1988"), 1200);
    lista.insertarPersona(lista.getAncestro(), nuevaPersona("David Ramirez",  1290, "02/02/1995"), 1200);

    // Nivel 2: hijos de 1500
    lista.insertarPersona(lista.getAncestro(), nuevaPersona("Elena Soto",   1560, "18/06/1992"), 1500);
    lista.insertarPersona(lista.getAncestro(), nuevaPersona("Esteban Soto", 1510, "25/12/1989"), 1500);

    // Nivel 3: hijos de 1250 -> obliga a la recursión dentro de una sublista
    lista.insertarPersona(lista.getAncestro(), nuevaPersona("Fernanda Ramirez", 1258, "07/07/2015"), 1250);
    lista.insertarPersona(lista.getAncestro(), nuevaPersona("Felipe Ramirez",   1252, "03/03/2013"), 1250);

    // 1800 se queda como hoja, sin hijos

    JOptionPane.showMessageDialog(null, "Datos de prueba cargados (11 personas).");
}

}  