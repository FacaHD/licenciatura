package com.Fabrica.data;

import com.Fabrica.GestArmazem.business.Gestor;


import java.sql.*;
import java.util.*;

public class GestorDAO implements Map<String, Gestor> {
    private static GestorDAO singleton = null;

    private static final String USERNAME = "jfc"; //TODO: alterar
    private static final String PASSWORD = "jfc"; //TODO: alterar
    private static final String CREDENTIALS = "?user="+USERNAME+"&password="+PASSWORD;
    private static final String DATABASE = "localhost:3306/dssprojeto";

    private GestorDAO () {
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
            String sql = "CREATE TABLE IF NOT EXISTS Gestores (" +
                    "codGestor varchar(10) NOT NULL PRIMARY KEY,"+
                    "mail varchar(50) DEFAULT NULL,"+
                    "passe varchar (50) DEFAULT NULL,"+
                    "logado BOOLEAN DEFAULT  NULL)";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static GestorDAO getInstance() {
        if (GestorDAO.singleton == null) {
            GestorDAO.singleton = new GestorDAO();
        }
        return GestorDAO.singleton;
    }

    @Override
    public int size() {
        int i = 0;
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS+"&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM Gestores")) {
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

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        boolean r;
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS+"&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
             Statement stm = conn.createStatement();
             ResultSet rs =
                     stm.executeQuery("SELECT codGestor FROM gestores WHERE codGestor='"+key.toString()+"'")) {
            r = rs.next();
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    @Override
    public boolean containsValue(Object value) {
        Gestor a = (Gestor) value;
        return this.containsKey(a.getCodGestor());
    }

    @Override
    public Gestor get(Object key) {
        Gestor t = null;
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS+"&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM Gestores WHERE codGestor='"+key+"'")) {
            if (rs.next()) {
                t=new Gestor(rs.getString("codGestor"),rs.getString("mail"),rs.getString("passe"),rs.getBoolean("logado"));


            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return t;
    }


    @Override
    public Gestor put(String key, Gestor value) {
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS+"&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
             Statement stm = conn.createStatement()) {

            stm.executeUpdate(
                    "INSERT INTO Gestores " +
                            "VALUES ('"+ value.getCodGestor()+ "', '"+
                            value.getMail()+"', '"+
                            value.getPass()+"', " +
                            value.isLogado() + ")" +
                            "ON DUPLICATE KEY UPDATE codGestor=VALUES(codGestor),logado=VALUES(logado);" );

        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return value;
    }



    @Override
    public Gestor remove(Object key) {
        Gestor t = this.get(key);
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS+"&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("DELETE FROM Gestores WHERE codGestor='"+key+"'");
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return t;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Gestor> m) {
        for(Gestor t : m.values()) {
            this.put(t.getCodGestor(), t);
        }

    }

    @Override
    public void clear() {
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS+"&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("TRUNCATE Gestores");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<Gestor> values() {
        Collection<Gestor> col = new HashSet<>();
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS+"&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT codGestor FROM Gestores")) {
            while (rs.next()) {
                col.add(this.get(rs.getString("codGestor")));
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return col;
    }

    @Override
    public Set<Entry<String, Gestor>> entrySet() {
        return null;
    }
}