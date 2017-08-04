var path = require('path');

module.exports = {
  entry: './src/main/webapp/app/Views/Home/Home.js',
  output: {
    filename: 'bundle.js',
    path: path.resolve(__dirname, './dist/main/webapp/app/')
  },
  module: {
	loaders: [
	{
		test: /\.html$/,
		loader: "text-loader"
	}
	]
  },
  resolve: {
	  modules: [
		"node_modules",
		path.resolve(__dirname, "./src/main/webapp/app/")
	  ],
	  extensions: [".js"]
	  
  }
};