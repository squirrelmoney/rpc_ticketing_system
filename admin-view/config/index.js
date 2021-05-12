const path = require('path');

module.exports = {
    	
build: {
    env: require("./prod.env.js"),
    index: path.resolve(__dirname, "../dist/index.html"),
    assetsRoot: path.resolve(__dirname, "../dist/"),
    assetsSubDirectory: "./",
    assetsPublicPath: "/",
    productionSourceMap: true,
    devtool: "#source-map",
    productionGzip: false,
    productionGzipExtensions: ["js", "css"],
    bundleAnalyzerReport: process.env.npm_config_report
}
}