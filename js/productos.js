/*
  productos.js — catálogo dinámico
  · Carga productos.json con fetch + async/await
  · Renderiza tarjetas con createElement y template strings
  · Filtra por categoría, marca, precio y texto de búsqueda
  · Ordena por precio, nombre o calificación
  · Agrega productos al carrito con carritoGlobal
*/
import { Producto } from './modulos/Producto.js';
import { carritoGlobal } from './modulos/carritoGlobal.js';
import { actualizarContadorCarrito } from './app.js';

let todosLosProductos = [];   // array de instancias Producto

// ── Fetch ─────────────────────────────────────────────────────────────────────

async function cargarProductos() {
  try {
    const respuesta = await fetch('data/productos.json');
    if (!respuesta.ok) throw new Error(`Error HTTP ${respuesta.status}`);
    const datos = await respuesta.json();
    // Convertimos cada objeto plano en una instancia de la clase Producto
    todosLosProductos = datos.map(d => new Producto(d));
    renderizar(todosLosProductos);
  } catch (error) {
    mostrarError(`No se pudo cargar el catálogo: ${error.message}`);
  }
}

function mostrarError(mensaje) {
  const grid = document.getElementById('grid-productos');
  if (grid) grid.innerHTML = `<p class="error-carga" role="alert">${mensaje}</p>`;
}

// ── Render ────────────────────────────────────────────────────────────────────

function crearTarjeta(producto) {
  const article = document.createElement('article');
  article.dataset.id = producto.id;

  // Badge (NUEVO / OFERTA / AGOTADO)
  const badgeHTML = producto.badge
    ? `<mark class="badge badge-${producto.badge.toLowerCase()}">${producto.badge}</mark>`
    : '';

  // Precio con descuento opcional
  const precioHTML = producto.precioOriginal
    ? `<p class="precio">
         <strong>${producto.precioFormateado}</strong>
         <del>${producto.precioOriginalFormateado}</del>
         <mark class="descuento">${producto.descuento}% OFF</mark>
       </p>`
    : `<p class="precio"><strong>${producto.precioFormateado}</strong></p>`;

  // Botón deshabilitado si está agotado
  const btnHTML = producto.enStock
    ? `<button type="button" class="btn-agregar" data-id="${producto.id}"
         aria-label="Agregar ${producto.nombre} al carrito">
         🛒 Agregar
       </button>`
    : `<button type="button" disabled aria-disabled="true">Sin stock</button>`;

  article.innerHTML = `
    <header>
      ${badgeHTML}
      <figure>
        <img src="${producto.imagen}" alt="${producto.nombre}" width="180" height="180" loading="lazy">
      </figure>
      <h3><a href="producto.html?id=${producto.id}">${producto.nombre}</a></h3>
    </header>
    <p class="descripcion-corta">${producto.descripcion}</p>
    ${precioHTML}
    <p class="estrellas">${producto.estrellas} <small>(${producto.resenas} reseñas)</small></p>
    <p class="disponibilidad">
      ${producto.enStock ? '<ins>En stock</ins>' : '<del>Agotado</del>'}
    </p>
    <footer>
      <a href="producto.html?id=${producto.id}">Ver detalle</a>
      ${btnHTML}
    </footer>
  `;

  return article;
}

function renderizar(productos) {
  const grid      = document.getElementById('grid-productos');
  const contador  = document.getElementById('contador-resultados');
  if (!grid) return;

  grid.innerHTML = '';

  if (productos.length === 0) {
    grid.innerHTML = '<p class="sin-resultados">No se encontraron productos con esos filtros.</p>';
    if (contador) contador.textContent = 'Mostrando 0 productos';
    return;
  }

  // forEach para recorrer y appendChild para manipular el DOM
  productos.forEach(p => grid.appendChild(crearTarjeta(p)));

  if (contador) {
    contador.textContent = `Mostrando ${productos.length} de ${todosLosProductos.length} productos`;
  }

  // Eventos de los botones recién creados
  grid.querySelectorAll('.btn-agregar').forEach(btn => {
    btn.addEventListener('click', () => agregarAlCarrito(btn.dataset.id));
  });
}

// ── Agregar al carrito ────────────────────────────────────────────────────────

function agregarAlCarrito(id) {
  const producto = todosLosProductos.find(p => p.id === id);
  if (!producto) return;

  carritoGlobal.agregar(producto);
  actualizarContadorCarrito();
  mostrarToast(`✅ ${producto.nombre} agregado al carrito`);
}

function mostrarToast(mensaje) {
  let toast = document.getElementById('toast');
  if (!toast) {
    toast = document.createElement('div');
    toast.id = 'toast';
    toast.setAttribute('role', 'status');
    toast.setAttribute('aria-live', 'polite');
    document.body.appendChild(toast);
  }
  toast.textContent = mensaje;
  toast.classList.add('toast-visible');
  setTimeout(() => toast.classList.remove('toast-visible'), 2800);
}

// ── Filtros y búsqueda ────────────────────────────────────────────────────────

function obtenerFiltros() {
  const form = document.getElementById('form-filtros');
  if (!form) return {};

  const data = new FormData(form);

  // Array de categorías seleccionadas (puede ser múltiple por los checkboxes)
  const categorias = data.getAll('cat');
  const marcas     = data.getAll('marca').filter(Boolean);
  const precioMin  = Number(data.get('precio_min') ?? 0) || 0;
  const precioMax  = Number(data.get('precio_max') ?? Infinity) || Infinity;
  const disponibilidad = data.getAll('disponibilidad');
  const soloStock      = disponibilidad.includes('en_stock');
  const soloOferta     = disponibilidad.includes('oferta');

  return { categorias, marcas, precioMin, precioMax, soloStock, soloOferta };
}

function filtrarYOrdenar() {
  const { categorias, marcas, precioMin, precioMax, soloStock, soloOferta } = obtenerFiltros();
  const busqueda = (document.getElementById('busqueda')?.value ?? '').toLowerCase().trim();
  const orden    = document.getElementById('ordenar')?.value ?? 'relevancia';

  // filter + every para aplicar múltiples condiciones a la vez
  let resultado = todosLosProductos.filter(p => {
    if (categorias.length > 0 && !categorias.includes(p.categoria)) return false;
    if (marcas.length > 0 && !marcas.includes(p.marca)) return false;
    if (p.precio < precioMin || p.precio > precioMax) return false;
    if (soloStock && !p.enStock) return false;
    if (soloOferta && !p.precioOriginal) return false;
    if (busqueda && !p.nombre.toLowerCase().includes(busqueda)
                 && !p.descripcion.toLowerCase().includes(busqueda)) return false;
    return true;
  });

  // sort con diferentes criterios
  resultado = resultado.sort((a, b) => {
    switch (orden) {
      case 'precio_asc':   return a.precio - b.precio;
      case 'precio_desc':  return b.precio - a.precio;
      case 'nombre':       return a.nombre.localeCompare(b.nombre, 'es');
      case 'calificacion': return b.calificacion - a.calificacion;
      default:             return 0;   // relevancia = orden original
    }
  });

  renderizar(resultado);
}

// ── Init ──────────────────────────────────────────────────────────────────────

document.addEventListener('DOMContentLoaded', async () => {
  await cargarProductos();

  // Filtros: escucha cambios en tiempo real
  const formFiltros = document.getElementById('form-filtros');
  if (formFiltros) {
    formFiltros.addEventListener('change', filtrarYOrdenar);
    formFiltros.addEventListener('submit', e => { e.preventDefault(); filtrarYOrdenar(); });
    formFiltros.addEventListener('reset', () => {
      // Tras resetear, el DOM aún no actualizó los valores; usamos setTimeout
      setTimeout(filtrarYOrdenar, 0);
    });
  }

  // Búsqueda en tiempo real (en el header también, desde productos.html)
  const inputBusqueda = document.getElementById('busqueda');
  if (inputBusqueda) {
    // Pre-filtrar si llegó ?q= desde otra página
    const params = new URLSearchParams(window.location.search);
    const q = params.get('q');
    if (q) { inputBusqueda.value = q; filtrarYOrdenar(); }

    inputBusqueda.addEventListener('input', filtrarYOrdenar);
  }

  const formBusqueda = document.querySelector('form[role="search"]');
  if (formBusqueda) {
    formBusqueda.addEventListener('submit', e => { e.preventDefault(); filtrarYOrdenar(); });
  }
});
