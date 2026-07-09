export class Cliente {
  constructor({ nombre, email, telefono, direccion = '' }) {
    this.nombre    = nombre;
    this.email     = email;
    this.telefono  = telefono;
    this.direccion = direccion;
  }

  /*
    Expresiones regulares para validar cada campo.
    Se usan en contacto.js y en el checkout del carrito.
  */
  static REGEX = {
    nombre:    /^[A-Za-záéíóúÁÉÍÓÚüÜñÑ\s]{2,60}$/,
    email:     /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/,
    telefono:  /^(\+?1[\s.-]?)?\(?\d{3}\)?[\s.-]?\d{3}[\s.-]?\d{4}$/,
    password:  /^(?=.*[A-Z])(?=.*[0-9]).{8,}$/,
  };

  static validar(campo, valor) {
    return Cliente.REGEX[campo]?.test(valor.trim()) ?? false;
  }
}
