/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
  theme: {
    fontFamily: {
      "sans-custom": ["Noto Sans", "sans-serif"],
      montserrat: ["Montserrat", "sans-serif"],
    },
    extend: {
      animation: {
        "fade-in": "fadeIn 5s ease-in-out",
      },
      keyframes: (theme) => ({
        fadeIn: {
          "0%": { backgroundColor: theme("colors.red.300") },
          "100%": { backgroundColor: theme("colors.transparent") },
        },
      }),
    },
  },
  plugins: [],
};
