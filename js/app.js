/*
  app.js — lógica global presente en todas las páginas:
  · Tema oscuro/claro persistido en localStorage
  · Contador del carrito en el header
  · Menú hamburguesa en móvil
*/
import { carritoGlobal } from './modulos/carritoGlobal.js';

// ── Tema ──────────────────────────────────────────────────────────────────────

function inicializarTema() {
  // Si el usuario guardó preferencia de modo claro, la aplicamos al arrancar
  if (localStorage.getItem('techstore_tema') === 'claro') {
    document.documentElement.classList.add('tema-claro');
  }
}

function configurarBotonTema() {
  const btn = document.getElementById('btn-tema');
  if (!btn) return;

  const esClaro = () => document.documentElement.classList.contains('tema-claro');
  btn.textContent = esClaro() ? '🌙 Modo oscuro' : '☀️ Modo claro';

  btn.addEventListener('click', () => {
    document.documentElement.classList.toggle('tema-claro');
    localStorage.setItem('techstore_tema', esClaro() ? 'claro' : 'oscuro');
    btn.textContent = esClaro() ? '🌙 Modo oscuro' : '☀️ Modo claro';
  });
}

// ── Carrito (contador en header) ──────────────────────────────────────────────

export function actualizarContadorCarrito() {
  document.querySelectorAll('[data-carrito-contador]').forEach(el => {
    el.textContent = carritoGlobal.cantidad;
  });
}

// ── Menú hamburguesa ──────────────────────────────────────────────────────────

function configurarHamburguesa() {
  const btn = document.getElementById('btn-menu');
  const nav = document.querySelector('header nav[aria-label="Navegación principal"]');
  if (!btn || !nav) return;

  btn.addEventListener('click', () => {
    const abierto = nav.classList.toggle('nav-abierta');
    btn.setAttribute('aria-expanded', String(abierto));
  });

  // Cerrar menú al hacer click fuera
  document.addEventListener('click', (e) => {
    if (!btn.contains(e.target) && !nav.contains(e.target)) {
      nav.classList.remove('nav-abierta');
      btn.setAttribute('aria-expanded', 'false');
    }
  });
}

// ── Init ──────────────────────────────────────────────────────────────────────

document.addEventListener('DOMContentLoaded', () => {
  inicializarTema();
  actualizarContadorCarrito();
  configurarBotonTema();
  configurarHamburguesa();
});
