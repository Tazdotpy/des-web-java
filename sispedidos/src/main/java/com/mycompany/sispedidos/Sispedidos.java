package com.mycompany.sispedidos;

import com.mycompany.sispedidos.db.DatabaseConnection;
import com.mycompany.sispedidos.ui.Menu;


public class Sispedidos {
    public static void main(String[] args) {
        try {
            DatabaseConnection.getConnection();
            new Menu().start();
        } catch (Exception e) {
            System.err.println("Fatal error: " + e.getMessage());
        } finally {
            DatabaseConnection.close();
        }
    }
}
