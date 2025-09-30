package com.projeto.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * O que é importante perceber é que, na prática, conectar ao banco de dados em JDBC é instanciar um objeto do tipo Connection.
 *
 * A ideia de capturar uma exceção do tipo SQLException e lançar a uma DbException possui duas motivações principais:
 *
 * 1 - Ter uma exceção personalizada.
 * 2 - Sabe-se que SQLException é derivada de Exception. Portanto, seu tratamento é obrigatório. Por outro lado, DbException é derivada de RuntimeException. Assim, no código principal, não será necessário ficar o tempo todo implementando um try-catch.
 */
public class DB {

    private static Connection conn = null;

    //deve retornar o objeto conn com a conexão estabelecida
    public static Connection getConnection() {

        if (conn == null) {

            try {

                //obtendo os dados das propriedades do banco de dados
                Properties props = loadProperties();

                //obtendo a url do banco de dados a partir do campo "dburl" definido em db.properties
                String url = props.getProperty("dburl");

                //atribuindo conn à conexão de fato, que recebe a url do banco e o restante das propriedades
                conn = DriverManager.getConnection(url, props);

            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }

        }

        return conn;

    }

    //método responsável por fechar a conexão com o banco de dados.
    public static void closeConnection() {

        //testando se a conexão está instanciada
        if (conn != null) {

            try {
                //fechando conexão
                conn.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }

        }

    }

    //método auxiliar para fechar conexão do objeto Statement
    public static void closeStatement(Statement st) {

        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }

    }

    //método auxiliar para fechar conexão do objeto ResultSet 
    public static void closeResultSet(ResultSet rs) {

        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }

        }

    }
    //acessando dados do arquivo db.properties
    private static Properties loadProperties() {

        //lendo arquivo db.properties
        try (FileInputStream fs = new FileInputStream("db.properties")) {

            //instancia um objeto do tipo Properties
            Properties props = new Properties();

            //carrega as informações de db.properties no objeto Properties
            props.load(fs);

            //retorna o objeto Properties
            return props;

        } catch (IOException ioe) {
            throw new DbException(ioe.getMessage());
        }

    }

}
