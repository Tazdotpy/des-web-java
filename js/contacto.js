/*
  contacto.js — validación del formulario de contacto
  · Usa expresiones regulares de la clase Cliente para validar en tiempo real
  · Muestra mensajes de error/éxito junto a cada campo
  · Previene envío si hay errores
*/
import { Cliente } from './modulos/Cliente.js';

// ── Helpers ───────────────────────────────────────────────────────────────────

function setError(campo, mensaje) {
  const err = document.getElementById(`error-${campo}`);
  const input = document.getElementById(campo);
  if (err)   err.textContent = mensaje;
  if (input) input.setAttribute('aria-invalid', mensaje ? 'true' : 'false');
}

function limpiarError(campo) {
  setError(campo, '');
}

// ── Validaciones individuales ─────────────────────────────────────────────────

function validarCampo(id, regla, mensajeError) {
  const input = document.getElementById(id);
  if (!input) return true;

  const valor = input.value.trim();

  // Campo vacío con required
  if (input.required && !valor) {
    setError(id, 'Este campo es obligatorio.');
    return false;
  }

  // Validar formato si tiene valor
  if (valor && !Cliente.validar(regla, valor)) {
    setError(id, mensajeError);
    return false;
  }

  limpiarError(id);
  return true;
}

// ── Validar todo el formulario ────────────────────────────────────────────────

function validarFormulario() {
  const resultados = [
    validarCampo('nombre',     'nombre',   'Solo letras y espacios, entre 2 y 60 caracteres.'),
    validarCampo('email',      'email',    'Formato inválido. Ej: usuario@correo.com'),
    validarCampo('telefono',   'telefono', 'Formato inválido. Ej: 809-000-1111'),
    validarCampo('contrasena', 'password', 'Mínimo 8 caracteres, una mayúscula y un número.'),
  ];

  // every devuelve true solo si TODOS son válidos
  return resultados.every(r => r === true);
}

// ── Feedback en tiempo real (keyup por campo) ─────────────────────────────────

function configurarValidacionEnVivo() {
  const reglas = [
    { id: 'nombre',     regla: 'nombre',   msg: 'Solo letras y espacios, entre 2 y 60 caracteres.' },
    { id: 'email',      regla: 'email',    msg: 'Formato inválido. Ej: usuario@correo.com' },
    { id: 'telefono',   regla: 'telefono', msg: 'Formato inválido. Ej: 809-000-1111' },
    { id: 'contrasena', regla: 'password', msg: 'Mínimo 8 caracteres, una mayúscula y un número.' },
  ];

  reglas.forEach(({ id, regla, msg }) => {
    const input = document.getElementById(id);
    if (!input) return;

    // Validamos al salir del campo (blur) para no molestar mientras escribe
    input.addEventListener('blur', () => validarCampo(id, regla, msg));

    // Y limpiamos el error mientras escribe si ya era válido
    input.addEventListener('input', () => {
      if (Cliente.validar(regla, input.value.trim())) limpiarError(id);
    });
  });
}

// ── Submit ────────────────────────────────────────────────────────────────────

function configurarSubmit() {
  const form = document.getElementById('form-contacto');
  if (!form) return;

  form.addEventListener('submit', e => {
    e.preventDefault();

    if (!validarFormulario()) {
      // Hacer scroll al primer error visible
      const primerError = form.querySelector('[aria-invalid="true"]');
      if (primerError) primerError.focus();
      return;
    }

    // Simular envío exitoso
    const nombre = document.getElementById('nombre')?.value.trim() ?? '';
    mostrarExito(form, nombre);
  });
}

function mostrarExito(form, nombre) {
  const seccionExito = document.getElementById('exito-contacto');
  if (seccionExito) {
    seccionExito.classList.remove('oculto');
    seccionExito.querySelector('[data-nombre]').textContent = nombre;
    form.classList.add('oculto');
    seccionExito.focus();
  } else {
    // Fallback si no hay sección de éxito en el HTML
    form.innerHTML = `
      <p role="alert" style="color: var(--color-primario); font-size: 1.1rem;">
        ✅ ¡Gracias, <strong>${nombre}</strong>! Tu mensaje fue recibido.
        Te responderemos en menos de 24 horas.
      </p>
    `;
  }
}

// ── Init ──────────────────────────────────────────────────────────────────────

document.addEventListener('DOMContentLoaded', () => {
  configurarValidacionEnVivo();
  configurarSubmit();
});
