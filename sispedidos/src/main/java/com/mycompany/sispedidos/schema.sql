-- Order Management System Schema
-- SQLite

CREATE TABLE IF NOT EXISTS Cliente (
    id_cliente   INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre       TEXT    NOT NULL,
    apellido     TEXT    NOT NULL,
    email        TEXT    UNIQUE NOT NULL,
    telefono     TEXT,
    direccion    TEXT
);

CREATE TABLE IF NOT EXISTS Producto (
    id_producto  INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre       TEXT    NOT NULL,
    descripcion  TEXT,
    precio       REAL    NOT NULL CHECK(precio >= 0),
    stock        INTEGER NOT NULL DEFAULT 0 CHECK(stock >= 0)
);

CREATE TABLE IF NOT EXISTS Pedido (
    id_pedido    INTEGER PRIMARY KEY AUTOINCREMENT,
    id_cliente   INTEGER NOT NULL,
    fecha        TEXT    NOT NULL DEFAULT (date('now')),
    estado       TEXT    NOT NULL DEFAULT 'PENDIENTE'
                         CHECK(estado IN ('PENDIENTE','CONFIRMADO','CANCELADO')),
    FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente)
);

CREATE TABLE IF NOT EXISTS DetallePedido (
    id_detalle   INTEGER PRIMARY KEY AUTOINCREMENT,
    id_pedido    INTEGER NOT NULL,
    id_producto  INTEGER NOT NULL,
    cantidad     INTEGER NOT NULL CHECK(cantidad > 0),
    precio_unit  REAL    NOT NULL,
    FOREIGN KEY (id_pedido)   REFERENCES Pedido(id_pedido),
    FOREIGN KEY (id_producto) REFERENCES Producto(id_producto)
);
