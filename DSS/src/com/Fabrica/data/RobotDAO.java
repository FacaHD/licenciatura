package com.Fabrica.data;


import com.Fabrica.GestRobots.Robot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.sql.ResultSet;



public class RobotDAO implements Map<String, Robot> {
    private static RobotDAO singleton = null;

    private static final String USERNAME = "jfc"; //TODO: alterar
    private static final String PASSWORD = "jfc"; //TODO: alterar
    private static final String CREDENTIALS = "?user="+USERNAME+"&password="+PASSWORD;
    private static final String DATABASE = "localhost:3306/dssprojeto";

    private RobotDAO() {
        //Driver é carregado automaticamente quando se abre uma conexão
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
//            // Driver não disponível
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://" + DATABASE + CREDENTIALS + "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS robots (" +
                    "codRobot varchar(10) NOT NULL PRIMARY KEY," +
                    "ocupado BOOLEAN DEFAULT FALSE," +
                    "palete varchar(10) DEFAULT  NULL," +
                    "idPrateleira varchar(10) DEFAULT NULL)";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    /**
     * Implementação do padrão Singleton
     *
     * @return devolve a instância única desta classe
     */
    public static RobotDAO getInstance() {
        if (RobotDAO.singleton == null) {
            RobotDAO.singleton = new RobotDAO();
        }
        return RobotDAO.singleton;
    }

    /**
     * @return número de robot na base de dados
     */
    @Override
    public int size() {
        int i = 0;
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://" + DATABASE + CREDENTIALS + "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM robots")) {
            if (rs.next()) {
                i = rs.getInt(1);
            }
        } catch (Exception e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return i;

    }

    /**
     * Método que verifica se existem robot
     *
     * @return true se existirem 0 robot
     */
    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Método que cerifica se um cod de robot existe na base de dados
     *
     * @param key codRobot do robot
     * @return true se a robot existe
     * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
     */
    @Override
    public boolean containsKey(Object key) {
        boolean r;
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://" + DATABASE + CREDENTIALS+"&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
             Statement stm = conn.createStatement();
             ResultSet rs =
                     stm.executeQuery("SELECT codRobot FROM robots WHERE codRobot='" + key.toString() + "'")) {
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
        Robot a = (Robot) value;
        return this.containsKey(a.getCodRobot());
    }

    @Override
    public Robot get(Object key) {
        Robot t = null;
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS+"&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM robots WHERE codRobot='"+key+"'")) {
            if (rs.next()) {  // A chave existe na tabela
                t=new Robot(rs.getString("codRobot"),rs.getBoolean("ocupado"),rs.getString("palete"),rs.getString("idPrateleira"));

            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return t;
    }

    @Override
    public Robot put(String key, Robot value) {
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS+"&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
             Statement stm = conn.createStatement()) {

            stm.executeUpdate(
                    "INSERT INTO Robots " +
                            "VALUES ('"+ value.getCodRobot()+ "', "+
                            value.isOcupado() +",'" +value.getPalete() +"','"+ value.getIdPrateleira() +"')"+
                            "ON DUPLICATE KEY UPDATE palete=VALUES(palete), ocupado=VALUES(ocupado), " +
                            "idPrateleira=VALUES(idPrateleira)");


        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return value;
    }

    @Override
    public Robot remove(Object key) {
        Robot r = this.get(key);
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://" + DATABASE + CREDENTIALS+"&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("DELETE FROM robots WHERE codRobot='" + key + "'");
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    //no lo sei
    @Override
    public void putAll(Map<? extends String, ? extends Robot> m) {
        for(Robot t : m.values()) {
            this.put(t.getCodRobot(), t);
        }
    }

    @Override
    public void clear() {
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://" + DATABASE + CREDENTIALS+"&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("TRUNCATE robots");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    @Override
    public Set<String> keySet() {
        throw new NullPointerException("Not implemented!");
    }

    @Override
    public Collection<Robot> values() {
        Collection<Robot> col = new HashSet<>();
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS+"&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT codRobot FROM robots")) {
            while (rs.next()) {
                col.add(this.get(rs.getString("codRobot")));
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return col;
    }

    @Override
    public Set<Entry<String, Robot>> entrySet() {
        throw new NullPointerException("public Set<Map.Entry<String,Robot>> entrySet() not implemented!");
    }
}