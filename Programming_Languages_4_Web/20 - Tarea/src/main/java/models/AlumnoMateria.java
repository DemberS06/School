package models;

public class AlumnoMateria {

    private int idAlumno;
    private int idMateria;
    private int calificacion;
    private String nombreMateria;

    // Constructores
    public AlumnoMateria() {
    }

    public AlumnoMateria(int idAlumno, int idMateria, int calificacion, String nombreMateria) {
        this.idAlumno = idAlumno;
        this.idMateria = idMateria;
        this.calificacion = calificacion;
        this.nombreMateria = nombreMateria;
    }

    // Getters y Setters
    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }
}
