package com.mycompany.sispedidos.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DATA LAYER — manages the SQLite connection.
 * The .db file is created in the project root on first run.
 */
public class DatabaseConnection {

    private static final String DB_URL = "jdbc:sqlite:tienda_pedidos.db";
    private static Connection instance;

    private DatabaseConnection() {}

    public static Connection getConnection() throws SQLException {
        if (instance == null || instance.isClosed()) {
            instance = DriverManager.getConnection(DB_URL);
            instance.setAutoCommit(true);
            initSchema(instance);
        }
        return instance;
    }

    private static void initSchema(Connection conn) {
        String schema =
            "CREATE TABLE IF NOT EXISTS Cliente (" +
            "  id_cliente INTEGER PRIMARY KEY AUTOINCREMENT," +
            "  nombre     TEXT NOT NULL," +
            "  apellido   TEXT NOT NULL," +
            "  email      TEXT UNIQUE NOT NULL," +
            "  telefono   TEXT," +
            "  direccion  TEXT" +
            ");" +
            "CREATE TABLE IF NOT EXISTS Producto (" +
            "  id_producto INTEGER PRIMARY KEY AUTOINCREMENT," +
            "  nombre      TEXT NOT NULL," +
            "  descripcion TEXT," +
            "  precio      REAL NOT NULL CHECK(precio >= 0)," +
            "  stock       INTEGER NOT NULL DEFAULT 0 CHECK(stock >= 0)" +
            ");" +
            "CREATE TABLE IF NOT EXISTS Pedido (" +
            "  id_pedido  INTEGER PRIMARY KEY AUTOINCREMENT," +
            "  id_cliente INTEGER NOT NULL," +
            "  fecha      TEXT NOT NULL DEFAULT (date('now'))," +
            "  estado     TEXT NOT NULL DEFAULT 'PENDIENTE'" +
            "             CHECK(estado IN ('PENDIENTE','CONFIRMADO','CANCELADO'))," +
            "  FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente)" +
            ");" +
            "CREATE TABLE IF NOT EXISTS DetallePedido (" +
            "  id_detalle  INTEGER PRIMARY KEY AUTOINCREMENT," +
            "  id_pedido   INTEGER NOT NULL," +
            "  id_producto INTEGER NOT NULL," +
            "  cantidad    INTEGER NOT NULL CHECK(cantidad > 0)," +
            "  precio_unit REAL NOT NULL," +
            "  FOREIGN KEY (id_pedido)   REFERENCES Pedido(id_pedido)," +
            "  FOREIGN KEY (id_producto) REFERENCES Producto(id_producto)" +
            ");";

        try (Statement st = conn.createStatement()) {
            for (String sql : schema.split(";")) {
                String trimmed = sql.trim();
                if (!trimmed.isEmpty()) st.execute(trimmed);
            }
        } catch (SQLException e) {
            System.err.println("Schema init error: " + e.getMessage());
        }
    }

    public static void close() {
        try {
            if (instance != null && !instance.isClosed()) instance.close();
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
}
