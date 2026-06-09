package com.mycompany.sispedidos.ui;

import com.mycompany.sispedidos.model.Cliente;
import com.mycompany.sispedidos.model.Pedido;
import com.mycompany.sispedidos.model.Producto;
import com.mycompany.sispedidos.service.ClienteService;
import com.mycompany.sispedidos.service.PedidoService;
import com.mycompany.sispedidos.service.ProductoService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** PRESENTATION LAYER — console menus and user interaction. */
public class Menu {

    private final Scanner         sc          = new Scanner(System.in);
    private final ClienteService  clienteSvc  = new ClienteService();
    private final ProductoService productoSvc = new ProductoService();
    private final PedidoService   pedidoSvc   = new PedidoService();

    public void start() {
        boolean running = true;
        while (running) {
            printMainMenu();
            int opt = readInt();
            switch (opt) {
                case 1 -> menuClientes();
                case 2 -> menuProductos();
                case 3 -> menuPedidos();
                case 0 -> running = false;
                default -> System.out.println("  Invalid option.");
            }
        }
        System.out.println("\n  Goodbye!");
    }

    private void printMainMenu() {
        System.out.println("\n╔══════════════════════════════╗");
        System.out.println("║   SISTEMA DE MANEJOS DE ORDEN  ║");
        System.out.println("╠══════════════════════════════╣");
        System.out.println("║  1. Clientes                 ║");
        System.out.println("║  2. Productos                ║");
        System.out.println("║  3. Ordenes                  ║");
        System.out.println("║  0. Salir                     ║");
        System.out.println("╚══════════════════════════════╝");
        System.out.print("  Opcion: ");
    }

    // --- Customers ---
    private void menuClientes() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- CLIENTES ---");
            System.out.println("  1. Nuevo Cliente");
            System.out.println("  2. Mostrar todos los clientes");
            System.out.println("  0. Atras");
            System.out.print("  Option: ");
            switch (readInt()) {
                case 1 -> registerCliente();
                case 2 -> listClientes();
                case 0 -> back = true;
                default -> System.out.println("  Invalid option.");
            }
        }
    }

    private void registerCliente() {
        System.out.println("\n  -- Nuevo Cliente --");
        System.out.print("  Primer nombre : "); String nombre   = sc.nextLine().trim();
        System.out.print("  Apellido  : "); String apellido = sc.nextLine().trim();
        System.out.print("  Email      : "); String email    = sc.nextLine().trim();
        System.out.print("  Telefono      : "); String tel      = sc.nextLine().trim();
        System.out.print("  Direccion    : "); String dir      = sc.nextLine().trim();
        try {
            Cliente c = clienteSvc.registerCliente(nombre, apellido, email, tel, dir);
            System.out.println("  ✓ Customer saved with ID: " + c.getIdCliente());
        } catch (Exception e) {
            System.out.println("  ✗ Error: " + e.getMessage());
        }
    }

    private void listClientes() {
        try {
            List<Cliente> list = clienteSvc.listAll();
            if (list.isEmpty()) { System.out.println("  No hay clientes registrados"); return; }
            System.out.println("\n  --- Lista de clientes ---");
            list.forEach(c -> System.out.println("  " + c));
        } catch (SQLException e) {
            System.out.println("  ✗ DB error: " + e.getMessage());
        }
    }

    // --- Products ---
    private void menuProductos() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- PRODUCTOS ---");
            System.out.println("  1. Nuevo producto");
            System.out.println("  2. Mostrar todos los productos");
            System.out.println("  0. Atras");
            System.out.print("  Option: ");
            switch (readInt()) {
                case 1 -> registerProducto();
                case 2 -> listProductos();
                case 0 -> back = true;
                default -> System.out.println("  Invalid option.");
            }
        }
    }

    private void registerProducto() {
        System.out.println("\n  -- Nuevo producto --");
        System.out.print("  Nombre        : "); String nombre = sc.nextLine().trim();
        System.out.print("  Descripcion : "); String desc   = sc.nextLine().trim();
        System.out.print("  Precio       : "); double precio = readDouble();
        System.out.print("  Stock       : "); int    stock  = readInt();
        try {
            Producto p = productoSvc.registerProducto(nombre, desc, precio, stock);
            System.out.println("   Producto guardado con ID: " + p.getIdProducto());
        } catch (Exception e) {
            System.out.println("  ✗ Error: " + e.getMessage());
        }
    }

    private void listProductos() {
        try {
            List<Producto> list = productoSvc.listAll();
            if (list.isEmpty()) { System.out.println("  No hay productos registrados."); return; }
            System.out.println("\n  --- Lista de productos ---");
            list.forEach(p -> System.out.println("  " + p));
        } catch (SQLException e) {
            System.out.println("  ✗ DB error: " + e.getMessage());
        }
    }

    // --- Orders ---
    private void menuPedidos() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- ORDENES ---");
            System.out.println("  1. Nueva orden");
            System.out.println("  2. Mostrar todas las ordenes");
            System.out.println("  3. Ordenes por clientes");
            System.out.println("  4. Actualizar status de la orden ");
            System.out.println("  0. Atras");
            System.out.print("  Option: ");
            switch (readInt()) {
                case 1 -> createPedido();
                case 2 -> listPedidos();
                case 3 -> pedidosByCliente();
                case 4 -> updateEstado();
                case 0 -> back = true;
                default -> System.out.println("  Invalid option.");
            }
        }
    }

    private void createPedido() {
        System.out.println("\n  -- Nueva Orden --");
        System.out.print("  Customer ID  : "); int idCliente = readInt();

        List<int[]> lines = new ArrayList<>();
        boolean addingLines = true;
        while (addingLines) {
            System.out.print("  ID de producto (0 hasta el final): "); int idProd = readInt();
            if (idProd == 0) {
                addingLines = false;
            } else {
                System.out.print("  Cantidad                : "); int qty = readInt();
                lines.add(new int[]{idProd, qty});
            }
        }

        if (lines.isEmpty()) {
            System.out.println("  Orden cancelada..");
            return;
        }

        try {
            Pedido p = pedidoSvc.createPedido(idCliente, lines);
            System.out.println("  ✓ Numero de orden #" + p.getIdPedido() +
                               " created. Total: $" + String.format("%.2f", p.getTotal()));
        } catch (Exception e) {
            System.out.println("  ✗ Error: " + e.getMessage());
        }
    }

    private void listPedidos() {
        try {
            List<Pedido> list = pedidoSvc.listAll();
            if (list.isEmpty()) { System.out.println("  No hay ordenes registradas."); return; }
            System.out.println("\n  --- Lista de ordenes ---");
            for (Pedido p : list) {
                System.out.println("  " + p);
                p.getDetalles().forEach(d -> System.out.println(d));
            }
        } catch (SQLException e) {
            System.out.println("  ✗ DB error: " + e.getMessage());
        }
    }

    private void pedidosByCliente() {
        System.out.print("  ID de cliente: "); int id = readInt();
        try {
            List<Pedido> list = pedidoSvc.listByCliente(id);
            if (list.isEmpty()) { System.out.println("  No se encontraron ordenes de este cliente."); return; }
            list.forEach(p -> {
                System.out.println("  " + p);
                p.getDetalles().forEach(d -> System.out.println(d));
            });
        } catch (Exception e) {
            System.out.println("  ✗ Error: " + e.getMessage());
        }
    }

    private void updateEstado() {
        System.out.print("  ID de orden : "); int id = readInt();
        System.out.println("  Status   : 1-PENDIENTE  2-CONFIRMADO  3-CANCELADO");
        System.out.print("  Opcion   : ");
        String estado = switch (readInt()) {
            case 1 -> "PENDIENTE";
            case 2 -> "CONFIRMADO";
            case 3 -> "CANCELADO";
            default -> null;
        };
        if (estado == null) { System.out.println("  Invalid choice."); return; }
        try {
            pedidoSvc.changeEstado(id, estado);
            System.out.println("  Estado de la orden cambiado a  " + estado);
        } catch (Exception e) {
            System.out.println("  ✗ Error: " + e.getMessage());
        }
    }

    // --- Input helpers ---
    private int readInt() {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private double readDouble() {
        try {
            return Double.parseDouble(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
