package com.Fabrica.data;

import com.Fabrica.GestArmazem.business.Prateleira;


import java.sql.*;
import java.util.*;

public class PrateleiraDAO implements Map<String, Prateleira>  {
    private static com.Fabrica.data.PrateleiraDAO singleton = null;

    private static final String USERNAME = "jfc"; //TODO: alterar
    private static final String PASSWORD = "jfc"; //TODO: alterar
    private static final String CREDENTIALS = "?user="+USERNAME+"&password="+PASSWORD;
    private static final String DATABASE = "localhost:3306/dssprojeto";


    private PrateleiraDAO() {
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
            String sql = "CREATE TABLE IF NOT EXISTS prateleiras (" +
                    "codPrat varchar(10) NOT NULL PRIMARY KEY," +
                    "espacoDisp INT(10) DEFAULT 0)";
            stm.executeUpdate(sql);

            sql = "CREATE  TABLE IF NOT EXISTS paletesNasPrateleiras(" +
                    "codPalete varchar(10) DEFAULT NULL , " +
                    "prateleira varchar(10), foreign key(prateleira) references prateleiras(codPrat))";

            stm.executeUpdate(sql);

        } catch (SQLException e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }


    public static PrateleiraDAO getInstance() {
        if (PrateleiraDAO.singleton == null) {
            PrateleiraDAO.singleton = new PrateleiraDAO();
        }
        return PrateleiraDAO.singleton;
    }

    public int size() {
        int i = 0;
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS+"&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM prateleiras")) {
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
                     stm.executeQuery("SELECT codPrat FROM prateleiras WHERE codPrat='"+key.toString()+"'")) {
            r = rs.next();
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    public boolean containsValue(Object value) {
        Prateleira a = (Prateleira) value;
        return this.containsKey(a.getCodPrat());
    }

    public Prateleira get(Object key) {
        Prateleira t = null;
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS+"&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
             Statement stm = conn.createStatement();
             Statement stm1 = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM prateleiras WHERE codPrat='"+key+"'")) {
            if (rs.next()) {  // A chave existe na tabela
                Collection<String> paletes=getPaletes(key.toString(),stm1);


                t=new Prateleira(rs.getString("codPrat"), rs.getInt("espacoDisp"),paletes);
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return t;
    }
    private Collection<String> getPaletes(String tid, Statement stm) throws SQLException {
        Collection<String> paletes = new TreeSet<>();
        try (ResultSet rsa = stm.executeQuery("SELECT codPalete FROM paletesNasPrateleiras WHERE prateleira='"+tid+"'")) {
            while(rsa.next()) {
                paletes.add(rsa.getString("codPalete"));
            }
        }
        catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return paletes;
    }


    public Prateleira put(String key, Prateleira value) {
        Prateleira res = null;

        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS+"&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
             Statement stm = conn.createStatement()) {

            stm.executeUpdate(
                    "INSERT INTO prateleiras VALUES ('"+value.getCodPrat()+"',"+value.getEspacosDisp()+")"+
                            "ON DUPLICATE KEY UPDATE codPrat=Values(codPrat), espacoDisp =VALUES(espacoDisp);");

            Collection<String> oldAl = getPaletes(key, stm);
            Collection<String> newAl = value.getPrateleirasManhosas();
            String replace = "";
            if(newAl!=null) { newAl.removeAll(oldAl);
                String ola= newAl.toString();
                String replace1=ola.replace("]", "");
                replace = replace1.replace("[", "");
            }
            if(value.getPrateleirasManhosas()!=null) oldAl.removeAll(value.getPrateleirasManhosas());

            stm.executeUpdate("INSERT INTO paletesNasPrateleiras VALUES ('"+replace+"','"+value.getCodPrat()+"')");


        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return value;
    }



    public Prateleira remove(Object key) {
        Prateleira t = this.get(key);
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS+"&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("DELETE FROM prateleiras WHERE codPrat='"+key+"'");
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return t;
    }


    public void putAll(Map<? extends String, ? extends Prateleira> m) {
        for(Prateleira t : m.values())
            this.put(t.getCodPrat(), t);

    }


    public void clear() {
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS+"&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("TRUNCATE prateleiras;");

        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }

    }


    public Set<String> keySet() {
        return null;
    }


    public Collection<Prateleira> values() {
        Collection<Prateleira> col = new HashSet<>();
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS+"&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT codPrat FROM prateleiras")) {
            while (rs.next()) {
                col.add(this.get(rs.getString("codPrat")));
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return col;
    }

    public Set<Map.Entry<String, Prateleira>> entrySet() {
        throw new NullPointerException("public Set<Map.Entry<String,Palete>> entrySet() not implemented!");
    }


}
