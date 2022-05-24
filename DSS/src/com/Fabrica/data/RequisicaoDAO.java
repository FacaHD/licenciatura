package com.Fabrica.data;


import com.Fabrica.GestArmazem.business.Palete;
import com.Fabrica.GestRequisicoes.Requisicao;

import java.sql.*;
import java.util.*;

public class RequisicaoDAO implements Map<String, Requisicao> {
    private static RequisicaoDAO singleton = null;

    private static final String USERNAME = "jfc"; //TODO: alterar
    private static final String PASSWORD = "jfc"; //TODO: alterar
    private static final String CREDENTIALS = "?user="+USERNAME+"&password="+PASSWORD;
    private static final String DATABASE = "localhost:3306/dssprojeto";;



    private RequisicaoDAO() {
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
            String sql = "CREATE TABLE IF NOT EXISTS requisicoes (" +
                    "codigo varchar(10) NOT NULL PRIMARY KEY)";
            stm.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS Palete (" +
                    "CodPalete varchar(10) NOT NULL PRIMARY KEY," +
                    "material varchar(10) DEFAULT NULL," +
                    "Requisicao varchar(10), foreign key(Requisicao) references requisicoes(codigo))";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }


    public static RequisicaoDAO getInstance() {
        if (RequisicaoDAO.singleton == null) {
            RequisicaoDAO.singleton = new RequisicaoDAO();
        }
        return RequisicaoDAO.singleton;
    }

    @Override
    public int size() {
        int i = 0;
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS+"&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM Requisicao")) {
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
                     stm.executeQuery("SELECT codigo FROM requisicoes WHERE codigo='"+key.toString()+"'")) {
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
        Requisicao a = (Requisicao) value;
        return this.containsKey(a.getCodReq());
    }

    @Override
    public Requisicao get(Object key) {
        Requisicao t = null;
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS+"&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM requisicoes WHERE codigo='"+key+"'")) {
            if (rs.next()) {
                Collection<Palete> paletes = new TreeSet<>();
                t = new Requisicao(rs.getString("codigo"), paletes);
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return t;
    }

    @Override
    public Requisicao put(String key, Requisicao value) {
        Requisicao res = null;
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS+"&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
             Statement stm = conn.createStatement()) {


            stm.executeUpdate(
                    "INSERT INTO requisicoes VALUES ('"+value.getCodReq()+"') ");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }


    @Override
    public Requisicao remove(Object key) {
        Requisicao t = this.get(key);
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS+"&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("DELETE FROM Requisicao WHERE Codigo='"+key+"'");
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return t;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Requisicao> m) {
        for(Requisicao t : m.values())
            this.put(t.getCodReq(), t);

    }

    @Override
    public void clear() {
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS+"&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("TRUNCATE requisicoes");
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
    public Collection<Requisicao> values() {
        Collection<Requisicao> col = new HashSet<>();
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS+"&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT codigo FROM requisicoes")) {
            while (rs.next()) {
                col.add(this.get(rs.getString("codigo")));
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return col;
    }

    @Override
    public Set<Entry<String, Requisicao>> entrySet() {
        throw new NullPointerException("public Set<Map.Entry<String,Palete>> entrySet() not implemented!");
    }
}