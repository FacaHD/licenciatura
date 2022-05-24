package com.Fabrica.data;

import com.Fabrica.GestArmazem.business.Palete;


import java.sql.*;
import java.util.*;

public class PaleteDAO implements Map<String, Palete> {
    private static com.Fabrica.data.PaleteDAO singleton = null;
    private static final String USERNAME = "jfc"; //TODO: alterar
    private static final String PASSWORD = "jfc"; //TODO: alterar
    private static final String CREDENTIALS = "?user="+USERNAME+"&password="+PASSWORD;
    private static final String DATABASE = "localhost:3306/dssprojeto";


    private PaleteDAO() {
        //Driver é carregado automaticamente quando se abre uma conexão
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
//            // Driver não disponível
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS+"&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS paletes (" +
                    "codPalete varchar(10) NOT NULL PRIMARY KEY," +
                    "idRobot varchar(10) DEFAULT  NULL," +
                    "idPrateleira varchar(10) DEFAULT  NULL)";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }


    public static PaleteDAO getInstance() {
        if (PaleteDAO.singleton == null) {
            PaleteDAO.singleton = new PaleteDAO();
        }
        return PaleteDAO.singleton;
    }

    public int size() {
        int i = 0;
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS+"&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM paletes")) {
            if(rs.next()) {
                i = rs.getInt(1);
            }
        }
        catch (Exception e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return i;

    }


    public boolean isEmpty() {
        return this.size() == 0;
    }


    public boolean containsKey(Object key) {
        boolean r;
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS+"&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
             Statement stm = conn.createStatement();
             ResultSet rs =
                     stm.executeQuery("SELECT CodPalete FROM paletes WHERE codPalete='"+key.toString()+"'")) {
            r = rs.next();
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    public boolean containsValue(Object value) {
        Palete a = (Palete) value;
        return this.containsKey(a.getCodPal());
    }

    public Palete get(Object key) {
        Palete t = null;
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS+"&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM paletes WHERE codPalete='"+key+"'")) {
            if (rs.next()) {

                t = new Palete(rs.getString("codPalete"),rs.getString("idRobot"),rs.getString("idPrateleira"));
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return t;
    }


    public Palete put(String key, Palete value) {
        Palete res = null;
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS+"&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
             Statement stm = conn.createStatement()) {


            stm.executeUpdate(
                    "INSERT INTO paletes VALUES ('"+value.getCodPal()+"','"+value.getIdRobot()+"','"+value.getIdPrateleira()+"') ");


        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }



    public Palete remove(Object key) {
        Palete t = this.get(key);
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS+"&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("DELETE FROM paletes WHERE codPalete='"+key+"'");
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return t;
    }


    public void putAll(Map<? extends String, ? extends Palete> m) {
        for(Palete t : m.values())
            this.put(t.getCodPal(), t);

    }


    public void clear() {
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS+"&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("TRUNCATE paletes");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }

    }


    public Set<String> keySet() {
        return null;
    }


    public Collection<Palete> values() {
        Collection<Palete> col = new HashSet<>();
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS+"&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT codPalete FROM paletes")) {
            while (rs.next()) {
                col.add(this.get(rs.getString("codPalete")));
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return col;
    }

    public Set<Map.Entry<String, Palete>> entrySet() {
        throw new NullPointerException("public Set<Map.Entry<String,Palete>> entrySet() not implemented!");
    }
}
