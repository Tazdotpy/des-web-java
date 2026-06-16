Practica 1. HTML

Proyecto completo de una tienda de tecnología (Ecommerce) desarrollada únicamente en HTML5 con estilos CSS. El proyecto cumple con todos los requisitos de la Práctica 1 Integral de HTML5.

```
TechStore/
├── index.html                  Página principal
├── productos.html              Catálogo de productos con filtros
├── producto.html               Detalle del producto
├── carrito.html                Carrito de compras
├── contacto.html               Formulario de contacto
├── acerca.html                 Información sobre la tienda
├── css/
│   └── estilos.css            Estilos CSS (opcional pero incluido)
├── img/                        Carpeta para imágenes
├── audio/                      Carpeta para archivos de audio
└── video/                      Carpeta para videos
```


 1. index.html - Página Principal
-  Header con logo y menú de navegación
-  Barra de búsqueda con form
-  Banner promocional con figure, img y figcaption
-  4 categorías de productos (Laptops, Smartphones, Accesorios, Gaming)
-  8 productos destacados con imagen, precio, descuento
-  Navegación completa entre páginas

 2. productos.html - Catálogo de Productos
-  Filtros avanzados usando:
  - `<aside>` para sección de filtros
  - `<fieldset>` y `<legend>` para agrupación
  - `<input type="checkbox">` para marcas
  - `<select>` y `<option>` para categorías
  - `<datalist>` para sugerencias de colores
  - `<input type="radio">` para disponibilidad
-  Grid de 8 productos con detalles
-  Acceso a página de detalles

 3. producto.html - Detalle del Producto
-  Galería responsiva con `<picture>` y `<source>`
-  Video del producto con `<video>` y controles
-  Audio promocional con `<audio>` y controles
-  Tabla completa de especificaciones técnicas:
  - `<caption>` para título
  - `<thead>`, `<tbody>`, `<tfoot>`
  - Múltiples `<tr>`, `<th>`, `<td>`
-  Opciones de compra con cantidad
-  Productos relacionados

 4. carrito.html - Carrito de Compras
-  Tabla de items del carrito
-  Elementos `<progress>` mostrando progreso del pedido
-  Elemento `<meter>` mostrando porcentaje de descuento
-  Resumen de costos detallado
-  Métodos de pago disponibles
-  Recomendaciones de productos

 5. contacto.html - Formulario de Contacto Avanzado
-  Todos los tipos de input requeridos:
  - `type="text"` - Nombre
  - `type="email"` - Correo electrónico
  - `type="password"` - Contraseña
  - `type="tel"` - Teléfono
  - `type="number"` - Número de pedido
  - `type="date"` - Fecha de pedido
  - `type="datetime-local"` - Fecha y hora
  - `type="color"` - Selector de color
  - `type="range"` - Rango de presupuesto
  - `type="file"` - Adjuntar archivo
  - `type="url"` - Sitio web
  - `type="checkbox"` - Newsletter y términos
  - `type="radio"` - Método de contacto
  - `type="hidden"` - Datos ocultos
  - `type="submit"` y `type="reset"` - Botones

-  Atributos de validación:
  - `required` - Campos obligatorios
  - `placeholder` - Textos de ayuda
  - `min`, `max`, `step` - Restricciones numéricas
  - `pattern` - Expresiones regulares
  - `readonly` - Campos solo lectura
  - `disabled` - Campos deshabilitados
  - `autocomplete` - Autocompletado
  - `multiple` - Múltiples archivos

-  Laboratorio HTML5 con elementos avanzados:
  - `<ruby>`, `<rt>`, `<rp>` - Anotaciones de texto
  - `<bdi>` - Texto bidireccional
  - `<bdo>` - Dirección de texto
  - `<wbr>` - Saltos de palabra
  - `<data>` - Datos máquina-legibles
  - `<mark>` - Texto destacado
  - `<time>` - Elemento temporal

 6. acerca.html - Acerca de Nosotros
-  `<main>` como contenedor principal
-  Múltiples `<section>` y `<article>`
-  `<details>` y `<summary>` para contenido expandible
-  `<blockquote>` y `<cite>` para citas
-  `<abbr>` para abreviaturas (HTML, ISO, TSO, etc.)
-  `<time>` para fechas y horarios
-  `<address>` con información de contacto
-  Testimonios de clientes
-  Preguntas frecuentes con detalles

  Estilos CSS Incluidos

Se incluye `css/estilos.css` con:
- Diseño responsivo (Mobile-first)
- Grid layout para productos
- Flexbox para navegación
- Tema de colores profesional
- Animaciones y transiciones
- Media queries para diferentes pantallas

 📊Etiquetas HTML5 Utilizadas

 Estructura Semántica
- `<html>`, `<head>`, `<body>`, `<header>`, `<nav>`, `<main>`, `<section>`, `<article>`, `<aside>`, `<footer>`

 Metadatos
- `<meta>`, `<title>`, `<link>`

 Texto Semántico
- `<h1>` a `<h6>`, `<p>`, `<span>`, `<strong>`, `<em>`, `<small>`, `<mark>`, `<del>`, `<ins>`, `<sub>`, `<sup>`, `<code>`, `<blockquote>`, `<cite>`, `<abbr>`, `<time>`, `<address>`, `<ruby>`, `<rt>`, `<rp>`, `<bdi>`, `<bdo>`, `<wbr>`, `<data>`

 Agrupación
- `<div>`, `<hr>`, `<ul>`, `<ol>`, `<li>`, `<dl>`, `<dt>`, `<dd>`

 Multimedia
- `<img>`, `<picture>`, `<source>`, `<figure>`, `<figcaption>`, `<audio>`, `<video>`

 Tablas
- `<table>`, `<caption>`, `<thead>`, `<tbody>`, `<tfoot>`, `<tr>`, `<th>`, `<td>`, `<colgroup>`, `<col>`

 Formularios
- `<form>`, `<label>`, `<input>` (todos los tipos), `<textarea>`, `<button>`, `<select>`, `<option>`, `<optgroup>`, `<datalist>`, `<fieldset>`, `<legend>`, `<meter>`, `<progress>`

 Interactivos
- `<details>`, `<summary>`

 Accesibilidad Implementada

-  Todas las imágenes tienen atributos `alt=""` descriptivos
-  Todos los formularios usan `<label>` con `for=""`
-  Estructura semántica clara
-  Contraste de colores adecuado
-  Navegación clara entre páginas

