TechStore Online — Carpeta de Video
=====================================

Archivos de video requeridos por el proyecto:

  macbook-air-m2.mp4    — Video de presentación del MacBook Air M2
  macbook-air-m2.webm   — Mismo video en formato WebM (compatibilidad cruzada)
  subtitulos-es.vtt     — Subtítulos en español (formato WebVTT)

Cómo agregar los archivos:
  - Descarga videos libres de derechos desde:
      https://www.pexels.com/videos/
      https://pixabay.com/videos/
  - Convierte a WebM con: ffmpeg -i video.mp4 video.webm

Formato del archivo de subtítulos (.vtt):
  WEBVTT

  00:00:01.000 --> 00:00:04.000
  Bienvenido al nuevo MacBook Air M2.

  00:00:04.500 --> 00:00:08.000
  Potencia increíble en un diseño ultradelgado.

Formatos soportados por HTML5:
  <video controls>
    <source src="video/macbook-air-m2.mp4" type="video/mp4">
    <source src="video/macbook-air-m2.webm" type="video/webm">
    <track kind="subtitles" src="video/subtitulos-es.vtt" srclang="es">
  </video>
