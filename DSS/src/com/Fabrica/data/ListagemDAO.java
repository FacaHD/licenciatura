package com.Fabrica.data;

import com.Fabrica.GestArmazem.business.Localizacao;


import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ListagemDAO implements Map<String, Localizacao> {
    private static ListagemDAO singleton = null;

    private static final String USERNAME = "jfc"; //TODO: alterar
    private static final String PASSWORD = "jfc"; //TODO: alterar
    private static final String CREDENTIALS = "?user="+USERNAME+"&password="+PASSWORD;
    private static final String DATABASE = "localhost:3306/dssprojeto";

    private ListagemDAO() {
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
            String sql = "CREATE TABLE IF NOT EXISTS listagem (" +
                    "codPalete varchar(10) NOT NULL PRIMARY KEY," +
                    "Localizacao varchar(10) DEFAULT  NULL)";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }


    public static ListagemDAO getInstance() {
        if (ListagemDAO.singleton == null) {
            ListagemDAO.singleton = new ListagemDAO();
        }
        return ListagemDAO.singleton;
    }

    public int size() {
        int i = 0;
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS+"&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM listagem")) {
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
                     stm.executeQuery("SELECT CodPalete FROM listagem WHERE codPalete='"+key.toString()+"'")) {
            r = rs.next();
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    public boolean containsValue(Object value) {
        Localizacao a = (Localizacao) value;
        return this.containsKey(a.getIdPalete());
    }

    public Localizacao get(Object key) {
        Localizacao t = null;
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS+"&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM listagem WHERE codPalete='"+key+"'")) {
            if (rs.next()) {  // A chave existe na tabela
                t = new Localizacao(rs.getString("codPalete"),rs.getString("Localizacao"));
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return t;
    }


    public Localizacao put(String key, Localizacao value) {
        Localizacao res = null;
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS+"&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
             Statement stm = conn.createStatement()) {


            stm.executeUpdate(
                    "INSERT INTO listagem VALUES ('"+value.getIdPalete()+"','"+value.getLoca()+"') " +
                            "ON DUPLICATE KEY UPDATE Localizacao=VALUES(Localizacao); ");


        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return value;
    }



    public Localizacao remove(Object key) {
        Localizacao t = this.get(key);
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS+"&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("DELETE FROM listagem WHERE codPalete='"+key+"'");
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return t;
    }


    public void putAll(Map<? extends String, ? extends Localizacao> m) {
        for(Localizacao t : m.values())
            this.put(t.getIdPalete(), t);

    }


    public void clear() {
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS+"&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("TRUNCATE listagem");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }

    }


    public Set<String> keySet() {
        return null;
    }


    public Collection<Localizacao> values() {
        Collection<Localizacao> col = new HashSet<>();
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS+"&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT codPalete FROM listagem")) {
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

    public Set<Map.Entry<String, Localizacao>> entrySet() {
        throw new NullPointerException("public Set<Map.Entry<String,Localizacao>> entrySet() not implemented!");
    }
}


