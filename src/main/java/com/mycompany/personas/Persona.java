/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.personas;

import java.util.Date;

/**
 *
 * @author wesly
 */
public class Persona {
    private String nombre;
    private Integer cedula;
    private Date fecha_nacimiento;

    public Persona(String nombre, Integer cedula, Date fecha_nacimiento) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCedula() {
        return cedula;
    }

    public void setCedula(Integer Cedula) {
        this.cedula = Cedula;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

 

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    @Override
    public String toString() {
        return "Persona{" + "nombre=" + nombre + ", cedula=" + cedula + ", fecha_nacimiento=" + fecha_nacimiento + '}';
    }
    
    
    
    
}
