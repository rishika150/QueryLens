package com.rishika.sqlanalyzer.config;

public class DatabaseConfig {

    private String host;
    private String port;
    private String database;
    private String username;
    private String password;

    public DatabaseConfig(String host, String port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    public String getJdbcUrl() {
        return "jdbc:mysql://" + host + ":" + port + "/" + database
                + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
