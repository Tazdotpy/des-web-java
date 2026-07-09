/*
  inicio.js — lógica de la página de inicio
  · Carga productos.json y activa los botones "Agregar al carrito"
    de los productos destacados usando data-id
  · Usa el <template> de Práctica 1 para demostrar la API de templates
*/
import { Producto } from './modulos/Producto.js';
import { carritoGlobal } from './modulos/carritoGlobal.js';
import { actualizarContadorCarrito } from './app.js';

let catalogo = [];

async function cargarCatalogo() {
  try {
    const resp  = await fetch('data/productos.json');
    if (!resp.ok) throw new Error(`HTTP ${resp.status}`);
    const datos = await resp.json();
    catalogo    = datos.map(d => new Producto(d));
    activarBotonesInicio();
    usarTemplate();
  } catch (e) {
    console.error('inicio.js: no se pudo cargar el catálogo', e.message);
  }
}

function activarBotonesInicio() {
  document.querySelectorAll('.btn-agregar-inicio[data-id]').forEach(btn => {
    btn.addEventListener('click', () => {
      const producto = catalogo.find(p => p.id === btn.dataset.id);
      if (!producto || !producto.enStock) return;

      carritoGlobal.agregar(producto);
      actualizarContadorCarrito();

      const original = btn.textContent;
      btn.textContent = '✅ Agregado';
      btn.disabled    = true;
      setTimeout(() => {
        btn.textContent = original;
        btn.disabled    = false;
      }, 2000);
    });
  });
}

/*
  usarTemplate: demuestra el uso del elemento <template> de Práctica 1.
  Clona la plantilla para mostrar un producto "destacado del mes" en
  la sección donde esté definida.
*/
function usarTemplate() {
  const template    = document.getElementById('template-producto');
  const contenedor  = document.getElementById('productos-desde-template');
  if (!template || !contenedor || catalogo.length === 0) return;

  // Tomamos los 3 productos con mayor calificación como "destacados"
  const destacados = [...catalogo]
    .sort((a, b) => b.calificacion - a.calificacion)
    .slice(0, 3);

  destacados.forEach(p => {
    const clon = template.content.cloneNode(true);
    clon.querySelector('img').src          = p.imagen;
    clon.querySelector('img').alt          = p.nombre;
    clon.querySelector('.nombre-producto').textContent  = p.nombre;
    clon.querySelector('.descripcion-producto').textContent = p.descripcion.slice(0, 80) + '…';
    clon.querySelector('.precio-producto').textContent  = p.precioFormateado;

    const btn = clon.querySelector('button');
    btn.dataset.id    = p.id;
    btn.textContent   = p.enStock ? '🛒 Agregar' : 'Sin stock';
    btn.disabled      = !p.enStock;
    btn.classList.add('btn-agregar-inicio');

    contenedor.appendChild(clon);
  });

  // Activar los botones recién insertados
  activarBotonesInicio();
}

document.addEventListener('DOMContentLoaded', cargarCatalogo);
