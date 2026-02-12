package com.isma.crud.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.io.InputStream;

public class DB {

	private static final Properties props = new Properties();

	// Bloque estático: se ejecuta una sola vez cuando la clase se carga
	static {
	    try {
	        InputStream input = DB.class
	                .getClassLoader()
	                .getResourceAsStream("db.properties");

	        if (input == null) {
	            throw new RuntimeException("No se encontró db.properties en resources");
	        }

	        props.load(input);

	    } catch (Exception e) {
	        throw new RuntimeException("Error cargando configuración de BD", e);
	    }
	}

	// Método para obtener conexión
	public static Connection getConnection() {
	    try {
	        return DriverManager.getConnection(
	                props.getProperty("db.url"),
	                props.getProperty("db.user"),
	                props.getProperty("db.password")
	        );
	    } catch (Exception e) {
	        throw new RuntimeException("Error conectando a PostgreSQL", e);
	    }
	}


}
