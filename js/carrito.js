/*
  carrito.js — gestión dinámica del carrito de compras
  · Renderiza items desde localStorage (via carritoGlobal)
  · Actualiza cantidades y elimina items en tiempo real
  · Calcula subtotal / ITBIS / envío / total
  · Simula checkout con validación, número de pedido y guardado en historial
  · Bonus: cupón de descuento, guardar en favoritos
*/
import { carritoGlobal } from './modulos/carritoGlobal.js';
import { Cliente } from './modulos/Cliente.js';
import { actualizarContadorCarrito } from './app.js';

// ── Formateo ──────────────────────────────────────────────────────────────────

const fmt = n => 'RD$' + Math.round(n).toLocaleString('es-DO');

// ── Render principal ──────────────────────────────────────────────────────────

function renderCarrito() {
  const tbody        = document.getElementById('cuerpo-carrito');
  const seccionVacia = document.getElementById('carrito-vacio');
  const seccionItems = document.getElementById('carrito-con-items');
  if (!tbody) return;

  const items = carritoGlobal.items;

  if (items.length === 0) {
    if (seccionVacia)  seccionVacia.classList.remove('oculto');
    if (seccionItems) seccionItems.classList.add('oculto');
    return;
  }

  if (seccionVacia)  seccionVacia.classList.add('oculto');
  if (seccionItems) seccionItems.classList.remove('oculto');

  tbody.innerHTML = '';
  items.forEach(item => tbody.appendChild(crearFilaItem(item)));

  actualizarTotales();
}

function crearFilaItem(item) {
  const tr = document.createElement('tr');
  tr.dataset.id = item.id;

  tr.innerHTML = `
    <td>
      <figure class="producto-mini">
        <img src="${item.imagen}" alt="${item.nombre}" width="70" height="70">
        <figcaption>
          <strong>${item.nombre}</strong><br>
          <small>SKU: ${item.id}</small>
        </figcaption>
      </figure>
    </td>
    <td>${fmt(item.precio)}</td>
    <td>
      <div class="control-cantidad">
        <button type="button" class="btn-menos" aria-label="Reducir cantidad de ${item.nombre}">−</button>
        <input type="number" class="qty-input" value="${item.cantidad}"
               min="1" max="10" aria-label="Cantidad de ${item.nombre}">
        <button type="button" class="btn-mas" aria-label="Aumentar cantidad de ${item.nombre}">+</button>
      </div>
    </td>
    <td>${fmt(item.precio * item.cantidad)}</td>
    <td>
      <button type="button" class="btn-eliminar" aria-label="Eliminar ${item.nombre} del carrito">🗑 Eliminar</button>
      <button type="button" class="btn-favorito" aria-label="Guardar ${item.nombre} en favoritos">♥ Guardar</button>
    </td>
  `;

  // Eventos de esta fila
  tr.querySelector('.btn-menos').addEventListener('click', () => {
    carritoGlobal.actualizarCantidad(item.id, item.cantidad - 1);
    renderCarrito(); actualizarContadorCarrito();
  });

  tr.querySelector('.btn-mas').addEventListener('click', () => {
    carritoGlobal.actualizarCantidad(item.id, item.cantidad + 1);
    renderCarrito(); actualizarContadorCarrito();
  });

  tr.querySelector('.qty-input').addEventListener('change', e => {
    carritoGlobal.actualizarCantidad(item.id, Number(e.target.value));
    renderCarrito(); actualizarContadorCarrito();
  });

  tr.querySelector('.btn-eliminar').addEventListener('click', () => {
    carritoGlobal.eliminar(item.id);
    renderCarrito(); actualizarContadorCarrito();
  });

  tr.querySelector('.btn-favorito').addEventListener('click', () => {
    guardarFavorito(item);
  });

  return tr;
}

// ── Totales ───────────────────────────────────────────────────────────────────

let descuentoCupon = 0;   // porcentaje aplicado por cupón

function actualizarTotales() {
  const subtotalBase = carritoGlobal.subtotal;
  const descuento    = Math.round(subtotalBase * descuentoCupon);
  const subtotal     = subtotalBase - descuento;

  // Recalculamos manualmente para incluir el descuento del cupón
  const itbis  = Math.round(subtotal * 0.18);
  const envio  = subtotal > 0 && subtotal < 5000 ? 350 : 0;
  const total  = subtotal + itbis + envio;

  setText('sub-carrito',   fmt(subtotal));
  setText('envio-carrito', envio === 0 ? 'Gratis' : fmt(envio));
  setText('itbis-carrito', fmt(itbis));
  setText('total-carrito', fmt(total));
  setText('total-resumen', fmt(total));

  // Cupón: mostrar ahorro si aplica
  const filaCupon = document.getElementById('fila-cupon');
  const ahorroCupon = document.getElementById('ahorro-cupon');
  if (filaCupon) {
    filaCupon.classList.toggle('oculto', descuento === 0);
    if (ahorroCupon) ahorroCupon.textContent = `−${fmt(descuento)}`;
  }
}

function setText(id, texto) {
  const el = document.getElementById(id);
  if (el) el.textContent = texto;
}

// ── Cupón ─────────────────────────────────────────────────────────────────────

const CUPONES = { 'TECH10': 0.10, 'TECHSTORE20': 0.20, 'BIENVENIDO': 0.05 };

function configurarCupon() {
  const form      = document.getElementById('form-cupon');
  const resultado = document.getElementById('resultado-cupon');
  if (!form) return;

  form.addEventListener('submit', e => {
    e.preventDefault();
    const codigo = form.querySelector('#cupon').value.trim().toUpperCase();
    const pct    = CUPONES[codigo];

    if (pct) {
      descuentoCupon = pct;
      resultado.textContent = `✅ Cupón aplicado: ${pct * 100}% de descuento`;
      resultado.className   = 'cupon-ok';
    } else {
      descuentoCupon = 0;
      resultado.textContent = '❌ Código no válido. Prueba: TECH10';
      resultado.className   = 'cupon-error';
    }
    actualizarTotales();
  });
}

// ── Vaciar carrito ────────────────────────────────────────────────────────────

function configurarVaciar() {
  const btn = document.getElementById('btn-vaciar');
  if (!btn) return;
  btn.addEventListener('click', () => {
    if (!confirm('¿Seguro que quieres vaciar el carrito?')) return;
    carritoGlobal.vaciar();
    descuentoCupon = 0;
    renderCarrito();
    actualizarContadorCarrito();
  });
}

// ── Checkout ──────────────────────────────────────────────────────────────────

function configurarCheckout() {
  const btnPago   = document.getElementById('btn-pago');
  const modal     = document.getElementById('modal-checkout');
  const formPago  = document.getElementById('form-checkout');
  const btnCerrar = document.getElementById('btn-cerrar-modal');
  if (!btnPago || !modal || !formPago) return;

  btnPago.addEventListener('click', () => {
    if (carritoGlobal.cantidad === 0) return;
    modal.classList.remove('oculto');
    modal.setAttribute('aria-hidden', 'false');
  });

  if (btnCerrar) {
    btnCerrar.addEventListener('click', () => cerrarModal(modal));
  }

  modal.addEventListener('click', e => {
    if (e.target === modal) cerrarModal(modal);
  });

  formPago.addEventListener('submit', e => {
    e.preventDefault();
    if (!validarFormCheckout(formPago)) return;

    const nombre = formPago.querySelector('#checkout-nombre').value.trim();
    const email  = formPago.querySelector('#checkout-email').value.trim();

    procesarPedido(nombre, email, modal);
  });
}

function cerrarModal(modal) {
  modal.classList.add('oculto');
  modal.setAttribute('aria-hidden', 'true');
}

function validarFormCheckout(form) {
  const nombre = form.querySelector('#checkout-nombre').value.trim();
  const email  = form.querySelector('#checkout-email').value.trim();
  let valido = true;

  const errNombre = form.querySelector('#error-checkout-nombre');
  const errEmail  = form.querySelector('#error-checkout-email');

  if (!Cliente.validar('nombre', nombre)) {
    if (errNombre) errNombre.textContent = 'Ingresa tu nombre completo (solo letras).';
    valido = false;
  } else {
    if (errNombre) errNombre.textContent = '';
  }

  if (!Cliente.validar('email', email)) {
    if (errEmail) errEmail.textContent = 'Ingresa un correo válido. Ej: usuario@correo.com';
    valido = false;
  } else {
    if (errEmail) errEmail.textContent = '';
  }

  return valido;
}

function procesarPedido(nombre, email, modal) {
  // Número de pedido aleatorio
  const numeroPedido = 'TS-' + Date.now().toString(36).toUpperCase();

  // Guardar en historial de compras (bonus)
  const pedido = {
    numero:  numeroPedido,
    fecha:   new Date().toLocaleDateString('es-DO', { dateStyle: 'long' }),
    items:   carritoGlobal.items,
    total:   carritoGlobal.total,
    nombre,
    email,
  };
  guardarEnHistorial(pedido);

  // Limpiar carrito
  carritoGlobal.vaciar();
  descuentoCupon = 0;

  // Mostrar pantalla de éxito
  cerrarModal(modal);
  mostrarExito(pedido);
  actualizarContadorCarrito();
}

function mostrarExito(pedido) {
  const main = document.querySelector('main');
  if (!main) return;

  main.innerHTML = `
    <section class="pedido-exitoso" aria-labelledby="titulo-exito">
      <h1 id="titulo-exito">🎉 ¡Pedido confirmado!</h1>
      <p>Gracias, <strong>${pedido.nombre}</strong>. Tu pedido fue recibido.</p>
      <p>Número de pedido: <strong>${pedido.numero}</strong></p>
      <p>Te enviaremos la confirmación a: <strong>${pedido.email}</strong></p>
      <p>Total cobrado: <strong>${fmt(pedido.total)}</strong></p>
      <ul>
        <li><a href="index.html">← Volver al inicio</a></li>
        <li><a href="productos.html">Seguir comprando</a></li>
      </ul>
    </section>
  `;
}

// ── Historial de compras (bonus) ──────────────────────────────────────────────

function guardarEnHistorial(pedido) {
  try {
    const historial = JSON.parse(localStorage.getItem('techstore_historial') ?? '[]');
    historial.unshift(pedido);                        // más reciente primero
    historial.splice(10);                             // máximo 10 pedidos guardados
    localStorage.setItem('techstore_historial', JSON.stringify(historial));
  } catch { /* si localStorage falla, no bloqueamos el flujo */ }
}

function renderHistorial() {
  const contenedor = document.getElementById('historial-pedidos');
  if (!contenedor) return;

  try {
    const historial = JSON.parse(localStorage.getItem('techstore_historial') ?? '[]');
    if (historial.length === 0) {
      contenedor.innerHTML = '<p>Aún no tienes pedidos anteriores.</p>';
      return;
    }

    contenedor.innerHTML = historial.map(p => `
      <article class="pedido-historial">
        <p><strong>${p.numero}</strong> — ${p.fecha}</p>
        <p>${p.items.length} producto(s) · Total: ${fmt(p.total)}</p>
      </article>
    `).join('');
  } catch {
    contenedor.innerHTML = '<p>No se pudo cargar el historial.</p>';
  }
}

// ── Favoritos (bonus) ─────────────────────────────────────────────────────────

function guardarFavorito(item) {
  try {
    const favs = JSON.parse(localStorage.getItem('techstore_favoritos') ?? '[]');
    const yaExiste = favs.some(f => f.id === item.id);
    if (!yaExiste) {
      favs.push({ id: item.id, nombre: item.nombre, precio: item.precio, imagen: item.imagen });
      localStorage.setItem('techstore_favoritos', JSON.stringify(favs));
      alert(`♥ ${item.nombre} guardado en favoritos.`);
    } else {
      alert(`${item.nombre} ya está en tus favoritos.`);
    }
  } catch { /* silencioso */ }
}

// ── Sugeridos (También te puede interesar) ────────────────────────────────────

async function configurarSugeridos() {
  try {
    const resp  = await fetch('data/productos.json');
    if (!resp.ok) return;
    const datos = await resp.json();

    document.querySelectorAll('.btn-sugerido[data-id]').forEach(btn => {
      btn.addEventListener('click', () => {
        const d = datos.find(p => p.id === btn.dataset.id);
        if (!d) return;
        carritoGlobal.agregar(d);
        actualizarContadorCarrito();
        renderCarrito();
        btn.textContent = '✅ Agregado';
        setTimeout(() => { btn.textContent = '🛒 Agregar'; }, 2000);
      });
    });
  } catch { /* silencioso */ }
}

// ── Init ──────────────────────────────────────────────────────────────────────

document.addEventListener('DOMContentLoaded', () => {
  renderCarrito();
  configurarCupon();
  configurarVaciar();
  configurarCheckout();
  renderHistorial();
  configurarSugeridos();
});
