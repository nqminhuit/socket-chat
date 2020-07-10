const path = require("path");

module.exports = {
  entry: "./src/elements/login-form.js",
  output: {
    filename: "main.js",
    path: path.resolve(__dirname, "dist"),
  },
};
