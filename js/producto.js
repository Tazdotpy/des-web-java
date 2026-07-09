/*
  producto.js — página de detalle de producto
  · Lee ?id= de la URL
  · Para 'macbook-air' (default): actualiza precio/stock/título dinámicamente
    sobre el HTML estático de Práctica 1.
  · Para cualquier otro producto: renderiza una página completa en
    #contenedor-producto y oculta el contenido estático del MacBook.
*/
import { Producto } from './modulos/Producto.js';
import { carritoGlobal } from './modulos/carritoGlobal.js';
import { actualizarContadorCarrito } from './app.js';

const fmt = n => 'RD$' + Math.round(n).toLocaleString('es-DO');

async function iniciarPaginaProducto() {
  const params = new URLSearchParams(window.location.search);
  const id     = params.get('id') ?? 'macbook-air';

  try {
    const resp = await fetch('data/productos.json');
    if (!resp.ok) throw new Error(`HTTP ${resp.status}`);
    const datos   = await resp.json();
    const datos_p = datos.find(d => d.id === id);
    if (!datos_p) { window.location.href = '404.html'; return; }

    const producto = new Producto(datos_p);

    if (id === 'macbook-air') {
      // Modo "mejora": el HTML estático ya está bien, solo actualizamos los datos
      mejorarPaginaEstatica(producto);
    } else {
      // Modo "dinámico": renderizamos toda la ficha del producto desde cero
      renderizarProductoDinamico(producto);
    }
  } catch (error) {
    const c = document.getElementById('contenedor-producto');
    if (c) {
      c.classList.remove('oculto');
      c.innerHTML = `<p role="alert">Error al cargar el producto: ${error.message}</p>`;
    }
  }
}

// ── Modo MacBook Air: solo actualiza los elementos dinámicos existentes ────────

function mejorarPaginaEstatica(p) {
  // Actualiza título pestaña y breadcrumb
  document.title = `${p.nombre} - TechStore RD`;
  const miga = document.getElementById('breadcrumb-producto');
  if (miga) miga.textContent = p.nombre;

  // Precio y stock ya tienen sus ids del HTML
  const elPrecio = document.getElementById('precio-producto');
  if (elPrecio) elPrecio.innerHTML = p.precioOriginal
    ? `Precio: <strong>${p.precioFormateado}</strong> <del>${p.precioOriginalFormateado}</del> <mark>${p.descuento}% OFF</mark>`
    : `Precio: <strong>${p.precioFormateado}</strong>`;

  const elStock = document.getElementById('stock-producto');
  if (elStock) elStock.innerHTML = p.enStock
    ? `Disponibilidad: <ins>En stock</ins> — <strong>¡Solo quedan ${p.stock} unidades!</strong>`
    : `Disponibilidad: <del>Agotado</del>`;

  // Hace funcional el formulario de agregar al carrito
  configurarFormAgregar(p);
}

// ── Modo dinámico: renderiza toda la ficha en #contenedor-producto ─────────────

function renderizarProductoDinamico(p) {
  const contenedor = document.getElementById('contenedor-producto');
  const estatico   = document.getElementById('seccion-estatica-macbook');

  // Ocultar el HTML estático del MacBook Air
  if (estatico) estatico.classList.add('oculto');

  // Actualizar título y breadcrumb
  document.title = `${p.nombre} - TechStore RD`;
  const miga = document.getElementById('breadcrumb-producto');
  if (miga) miga.textContent = p.nombre;

  const badgeHTML = p.badge
    ? `<mark class="badge badge-${p.badge.toLowerCase()}">${p.badge}</mark>`
    : '';

  const precioHTML = p.precioOriginal
    ? `<p class="precio-detalle">
         <strong>${p.precioFormateado}</strong>
         <del>${p.precioOriginalFormateado}</del>
         <mark class="descuento">${p.descuento}% OFF</mark>
       </p>`
    : `<p class="precio-detalle"><strong>${p.precioFormateado}</strong></p>`;

  const stockHTML = p.enStock
    ? `<p class="stock-ok">✅ En stock — ${p.stock} disponibles</p>`
    : `<p class="stock-agotado">❌ Agotado</p>`;

  const btnHTML = p.enStock
    ? `<div class="agregar-carrito-dinamico">
         <label for="qty-dinamico">Cantidad:</label>
         <input type="number" id="qty-dinamico" value="1"
                min="1" max="${Math.min(p.stock, 10)}" aria-label="Cantidad">
         <button type="button" id="btn-agregar-dinamico" class="btn-agregar">
           🛒 Agregar al carrito
         </button>
         <button type="button" id="btn-fav-dinamico">♥ Favoritos</button>
       </div>`
    : `<button type="button" disabled>Sin stock</button>`;

  contenedor.innerHTML = `
    <article class="ficha-producto-dinamica" aria-labelledby="nombre-prod-din">
      <header>
        ${badgeHTML}
        <h1 id="nombre-prod-din">${p.nombre}</h1>
        <p>
          Marca: <strong>${p.marca.charAt(0).toUpperCase() + p.marca.slice(1)}</strong> |
          Color: <strong>${p.color}</strong>
        </p>
        <p>${p.estrellas} <small>(${p.resenas} reseñas)</small></p>
      </header>

      <div class="ficha-layout">
        <figure class="ficha-imagen">
          <img src="${p.imagen}" alt="${p.nombre}" width="420" height="420">
        </figure>

        <div class="ficha-info">
          ${precioHTML}
          <p><abbr title="Impuesto al Valor Agregado">ITBIS</abbr> 18% incluido:
            <strong>${fmt(p.precio * 0.18)}</strong>
          </p>
          ${stockHTML}
          <p class="ficha-descripcion">${p.descripcion}</p>
          ${btnHTML}
          <p class="envio-info">🚚 Envío gratis en compras mayores a RD$5,000</p>
          <ul class="beneficios-lista">
            <li>✅ Garantía oficial 12 meses</li>
            <li>🔄 Devolución gratuita en 30 días</li>
            <li>💳 Hasta 12 cuotas sin interés</li>
          </ul>
        </div>
      </div>
    </article>
  `;

  contenedor.classList.remove('oculto');

  // Eventos
  const btnAgregar = document.getElementById('btn-agregar-dinamico');
  if (btnAgregar) {
    btnAgregar.addEventListener('click', () => {
      const qty = Number(document.getElementById('qty-dinamico')?.value ?? 1);
      carritoGlobal.agregar(p, qty);
      actualizarContadorCarrito();
      const texto = btnAgregar.textContent;
      btnAgregar.textContent = '✅ ¡Agregado!';
      setTimeout(() => { btnAgregar.textContent = texto; }, 2000);
    });
  }

  const btnFav = document.getElementById('btn-fav-dinamico');
  if (btnFav) {
    btnFav.addEventListener('click', () => guardarFavorito(p));
  }
}

// ── Formulario estático (MacBook Air) ─────────────────────────────────────────

function configurarFormAgregar(producto) {
  const form = document.getElementById('form-agregar');
  if (!form) return;

  form.addEventListener('submit', e => {
    e.preventDefault();
    const qty = Number(form.querySelector('#cantidad')?.value ?? 1);
    carritoGlobal.agregar(producto, qty);
    actualizarContadorCarrito();
    const btn = form.querySelector('button[type="submit"]');
    if (btn) {
      const original = btn.textContent;
      btn.textContent = '✅ ¡Agregado!';
      setTimeout(() => { btn.textContent = original; }, 2000);
    }
  });

  const btnFav = form.querySelector('button[aria-label*="avoritos"]');
  if (btnFav) {
    btnFav.addEventListener('click', () => guardarFavorito(producto));
  }
}

// ── Favoritos ─────────────────────────────────────────────────────────────────

function guardarFavorito(producto) {
  try {
    const favs = JSON.parse(localStorage.getItem('techstore_favoritos') ?? '[]');
    if (!favs.some(f => f.id === producto.id)) {
      favs.push({ id: producto.id, nombre: producto.nombre,
                  precio: producto.precio, imagen: producto.imagen });
      localStorage.setItem('techstore_favoritos', JSON.stringify(favs));
      alert(`♥ ${producto.nombre} guardado en favoritos.`);
    } else {
      alert(`${producto.nombre} ya está en tus favoritos.`);
    }
  } catch { /* silencioso */ }
}

document.addEventListener('DOMContentLoaded', iniciarPaginaProducto);
