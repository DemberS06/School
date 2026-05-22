package models;

public class Alumno {

    private int id;
    private String nombre;
    private String escuela;

    // Constructores
    public Alumno() {
    }

    public Alumno(int id, String nombre, String escuela) {
        this.id = id;
        this.nombre = nombre;
        this.escuela = escuela;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEscuela() {
        return escuela;
    }

    public void setEscuela(String escuela) {
        this.escuela = escuela;
    }
}
