# TechStore RD — Project Context

## What this is
A school web development project (3 "prácticas") building an ecommerce site called
**TechStore RD**, progressively layered: HTML5 → CSS3 → JavaScript. Built for a college
course. The student (Kevin) wants to genuinely understand every line, not just submit
working code — explanations matter as much as the code itself.

## Business context (for any copy/content decisions)
- Store name: **TechStore RD**
- Founder: **Gaggleman**, founded **2025**
- Based in **Santo Domingo, República Dominicana** — NOT Chile, NOT any other country
- Small business (1–10 people), sells electronics/tech products
- Currency: **RD$** (Dominican pesos) — never use Chilean pesos, USD, or any other currency
- Tax: **ITBIS 18%** (NOT Chilean IVA 19%)
- Phone: 809-000-1111 · Email: gagglecorp@nah.com
- Tone: accessible, honest, small-business — not a faceless megacorp

## Current project state
- **Práctica 1 (HTML5)** — ✅ Done. 7 pages: index, productos, producto, carrito,
  contacto, acerca, 404. All required semantic tags, forms, tables, multimedia,
  accessibility (ARIA, alt, label/for) are in place.
- **Práctica 2 (CSS3)** — ✅ Done. 3 files: estilos.css, responsive.css, animaciones.css.
  Dark grey + amber/orange color scheme (NO blue — explicitly requested). Flexbox, Grid,
  all 5 position values, animations, media queries (mobile/tablet/desktop), 6 bonus
  features (dark mode, hamburger menu, CSS slider, lightbox, loader, parallax).
- **Práctica 3 (JavaScript)** — 🔜 Not started yet. This is next.

## Design constraints — do not violate these
- **Color palette:** dark grey background (#111111/#1E1E1E) + amber/orange accents
  (#D97706/#F59E0B). Never reintroduce blue as a primary color.
- **No Chile references anywhere** — not in copy, not in placeholder data, not in
  comments. This was explicitly fixed once already; don't regress it.
- **Pure HTML5 only in Práctica 1 files** — no JS was allowed until Práctica 3 unlocks it.
- Images are currently **local placeholder graphics** generated with Pillow (in
  `img/productos/`, `img/categorias/`, `img/banner/`, `img/ui/`) because the sandbox
  network blocks external image hosts (Unsplash, placehold.co, etc. don't load files,
  only return "host not in allowlist" text). Kevin will swap in real photos himself.
- Dead/unbuilt links point to `404.html` rather than `href="#"`.
- Cart action buttons (vaciar carrito, actualizar cantidad) are intentionally inert
  pending Práctica 3 — they carry a visible note explaining this.

## File structure
```
TechStore/
├── index.html, productos.html, producto.html,
│   carrito.html, contacto.html, acerca.html, 404.html
├── css/
│   ├── estilos.css       (variables, selectors, layout, components)
│   ├── responsive.css    (mobile ≤768px / tablet 769–1024px / desktop ≥1025px)
│   └── animaciones.css   (keyframes, transitions, transforms, bonus effects)
├── img/{productos,categorias,banner,ui}/
├── audio/  (README explaining required promo audio files)
└── video/  (README explaining required product video + .vtt subtitles)
```

## Práctica 3 requirements (JavaScript — upcoming work)
Per the official rubric, needs:
- DOM manipulation (querySelector, createElement, classList, etc.)
- Event handling (click, submit, input, keydown, DOMContentLoaded, etc.)
- Variables, operators, conditionals, loops (for/while/forEach/map/filter/reduce)
- Functions (regular + arrow), objects, **classes** (Producto, Cliente, Carrito minimum)
- Array methods (push, pop, splice, sort, etc.)
- Form validation with regex (email, phone, password length)
- **LocalStorage** for cart persistence, theme preference
- **Fetch API** with async/await — consuming a public API (Fake Store API or
  JSONPlaceholder) or the project's own `data/productos.json` (15+ products)
- Try/catch error handling
- ES modules (export/import)
- Real functional ecommerce features: search, filter, add/remove cart items,
  quantity updates, subtotal/tax/total calculation, simulated checkout with random
  order number, stats dashboard
- Bonus (pick 3+): favorites list, product comparison, purchase history, persistent
  dark mode toggle, ratings system, countdown timer, simulated support chat, invoice generation

## Working style preferences
- Kevin mixes Spanish/English casually — respond naturally in kind, code/content in
  Spanish (matches the site), explanations can be English or Spanish depending on his message.
- He genuinely wants to learn — when adding JS, briefly explain *why*, not just *what*,
  especially for new concepts (classes, async/await, fetch, localStorage).
- He's also using this project as a way to learn web dev generally and plans a personal
  site for someone special later — unrelated to TechStore, a separate future project.
- Prefers iterative, concrete fixes over long explanations when something is visibly broken
  (e.g. "this is cut off", "this link goes nowhere") — fix it, then briefly summarize.
- College courses in parallel: Calculus I (will ask for math help separately, unrelated
  to this project).

## Known good patterns established in this project
- BEM-ish attribute selectors used throughout CSS (e.g. `section[aria-labelledby="..."]`)
  rather than utility classes — keep this convention when adding JS hooks (prefer adding
  `id` or `data-*` attributes to existing semantic elements over introducing new classes,
  unless a class is genuinely the right tool e.g. `.oculto`, `.reveal`).
- Spanish variable/function names in JS to match the rest of the codebase
  (e.g. `carritoCompras`, `agregarProducto`) — established already in code samples
  embedded in acerca.html's "Notas técnicas" section.
