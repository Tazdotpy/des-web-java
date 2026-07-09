export class Producto {
  constructor(datos) {
    this.id            = datos.id;
    this.nombre        = datos.nombre;
    this.descripcion   = datos.descripcion;
    this.precio        = datos.precio;
    this.precioOriginal = datos.precioOriginal ?? null;
    this.categoria     = datos.categoria;
    this.marca         = datos.marca;
    this.color         = datos.color;
    this.imagen        = datos.imagen;
    this.stock         = datos.stock;
    this.calificacion  = datos.calificacion;
    this.resenas       = datos.resenas;
    this.badge         = datos.badge ?? null;
  }

  get descuento() {
    if (!this.precioOriginal) return 0;
    return Math.round((1 - this.precio / this.precioOriginal) * 100);
  }

  get enStock() {
    return this.stock > 0;
  }

  get precioFormateado() {
    return 'RD$' + this.precio.toLocaleString('es-DO');
  }

  get precioOriginalFormateado() {
    return this.precioOriginal
      ? 'RD$' + this.precioOriginal.toLocaleString('es-DO')
      : null;
  }

  get estrellas() {
    const llenas = Math.floor(this.calificacion);
    const vacias  = 5 - llenas;
    return '⭐'.repeat(llenas) + '☆'.repeat(vacias);
  }
}
