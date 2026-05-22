package model;

public class AlumnoMateria {

    private int idAlumno;
    private int idMateria;
    private int calificacion;
    private String nombreMateria;

    public int getIdAlumno() { return idAlumno; }
    public void setIdAlumno(int idAlumno) { this.idAlumno = idAlumno; }

    public int getIdMateria() { return idMateria; }
    public void setIdMateria(int idMateria) { this.idMateria = idMateria; }

    public int getCalificacion() { return calificacion; }
    public void setCalificacion(int calificacion) { this.calificacion = calificacion; }

    public String getNombreMateria() { return nombreMateria; }
    public void setNombreMateria(String nombreMateria) { this.nombreMateria = nombreMateria; }
}