package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.Conexion;
import model.Alumno;

public class AlumnoDAO {

    public void agregar(Alumno a){

        try{

            Connection con = Conexion.getConexion();

            String sql = "insert into alumnos values (?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1,a.getId());
            ps.setString(2,a.getNombre());
            ps.setString(3,a.getEscuela());

            ps.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public List<Alumno> listar(){

        List<Alumno> lista = new ArrayList<>();

        try{

            Connection con = Conexion.getConexion();

            String sql = "select * from alumnos";

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(sql);

            while(rs.next()){

                Alumno a = new Alumno();

                a.setId(rs.getInt("id"));
                a.setNombre(rs.getString("nombre"));
                a.setEscuela(rs.getString("escuela"));

                lista.add(a);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return lista;
    }
}