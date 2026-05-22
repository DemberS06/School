package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.Conexion;
import model.Materia;

public class MateriaDAO {

    public List<Materia> listar(){

        List<Materia> lista = new ArrayList<>();

        try{

            Connection con = Conexion.getConexion();

            String sql = "select * from materias";

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(sql);

            while(rs.next()){

                Materia m = new Materia();

                m.setId(rs.getInt("id"));
                m.setNombre(rs.getString("nombre"));

                lista.add(m);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return lista;
    }
}