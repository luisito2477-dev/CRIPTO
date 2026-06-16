/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}", // <-- Aquí le dices que busque en tus componentes de React
  ],
  theme: {
    extend: {},
  },
  plugins: [],
}