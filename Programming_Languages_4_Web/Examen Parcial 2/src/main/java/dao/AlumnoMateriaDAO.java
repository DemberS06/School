package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import db.Conexion;
import model.AlumnoMateria;

public class AlumnoMateriaDAO {

    public void agregar(int idAlumno,int idMateria,int calificacion){

        try{

            Connection con = Conexion.getConexion();

            String sql = "insert into alumno_materia values (?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1,idAlumno);
            ps.setInt(2,idMateria);
            ps.setInt(3,calificacion);

            ps.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public List<AlumnoMateria> listar(int idAlumno){

        List<AlumnoMateria> lista = new ArrayList<>();

        try{

            Connection con = Conexion.getConexion();

            String sql = "select am.*, m.nombre "
                       + "from alumno_materia am "
                       + "join materias m on m.id = am.id_materia " 
                       + "where id_alumno=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1,idAlumno);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                AlumnoMateria am = new AlumnoMateria();

                am.setIdAlumno(rs.getInt("id_alumno"));
                am.setIdMateria(rs.getInt("id_materia"));
                am.setCalificacion(rs.getInt("calificacion"));
                am.setNombreMateria(rs.getString("nombre"));

                lista.add(am);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return lista;
    }
}