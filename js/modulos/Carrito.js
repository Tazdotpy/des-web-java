/*
  Carrito usa private class fields (#) para que nadie pueda
  modificar this.#items desde fuera — solo a través de los métodos.
  Así garantizamos que el localStorage siempre esté sincronizado.
*/
const CLAVE    = 'techstore_carrito';
const ITBIS    = 0.18;          // 18% impuesto dominicano
const UMBRAL_ENVIO = 5000;      // envío gratis sobre RD$5,000

export class Carrito {
  #items;

  constructor() {
    this.#items = this.#cargar();
  }

  // ── Persistencia ──────────────────────────────────────────
  #cargar() {
    try {
      return JSON.parse(localStorage.getItem(CLAVE) ?? '[]');
    } catch {
      return [];
    }
  }

  #guardar() {
    localStorage.setItem(CLAVE, JSON.stringify(this.#items));
  }

  // ── Lectura ───────────────────────────────────────────────
  get items()    { return [...this.#items]; }         // copia defensiva
  get cantidad() { return this.#items.reduce((t, i) => t + i.cantidad, 0); }

  // ── Mutaciones ────────────────────────────────────────────
  agregar(producto, cantidad = 1) {
    const existente = this.#items.find(i => i.id === producto.id);
    if (existente) {
      existente.cantidad = Math.min(existente.cantidad + cantidad, 10);
    } else {
      this.#items.push({
        id:      producto.id,
        nombre:  producto.nombre,
        precio:  producto.precio,
        imagen:  producto.imagen,
        cantidad,
      });
    }
    this.#guardar();
  }

  eliminar(id) {
    this.#items = this.#items.filter(i => i.id !== id);
    this.#guardar();
  }

  actualizarCantidad(id, cantidad) {
    if (cantidad < 1) { this.eliminar(id); return; }
    const item = this.#items.find(i => i.id === id);
    if (item) {
      item.cantidad = Math.min(cantidad, 10);
      this.#guardar();
    }
  }

  vaciar() {
    this.#items = [];
    this.#guardar();
  }

  // ── Totales ───────────────────────────────────────────────
  get subtotal() { return this.#items.reduce((t, i) => t + i.precio * i.cantidad, 0); }
  get itbis()    { return Math.round(this.subtotal * ITBIS); }
  get envio()    { return this.subtotal > 0 && this.subtotal < UMBRAL_ENVIO ? 350 : 0; }
  get total()    { return this.subtotal + this.itbis + this.envio; }
}
