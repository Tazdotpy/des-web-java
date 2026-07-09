/*
  Exporta una única instancia del Carrito compartida entre todos los módulos.
  Los ES modules son singletons: la misma instancia se reutiliza en cada import.
*/
import { Carrito } from './Carrito.js';

export const carritoGlobal = new Carrito();
