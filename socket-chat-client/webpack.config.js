const path = require("path");

let SRC_DIR = path.resolve(__dirname, "src");
let DST_DIR = path.resolve(__dirname, "dist");

module.exports = {
  entry: `${SRC_DIR}/app/jsx/chat-box.jsx`,
  output: {
    filename: "main.js",
    path: DST_DIR,
  },
  module: {
    rules: [
      {
        test: /\.js?/,
        include: SRC_DIR,
        use: {
          loader: "babel-loader",
          options: {
            presets: ["@babel/preset-react"],
          },
        },
      },
      {
        test: /\.css$/i,
        use: ["style-loader", "css-loader"],
      },
    ],
  },
};
