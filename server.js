var express = require('express');
var port = process.env.PORT || 3000;
var app = express();

app.configure(function(){
    // Serve up content from public directory
    app.use(express.static(__dirname + '/public'));
    app.use(app.router);
    app.use(express.logger());
});

app.use(express.static('/', + '/src/main/resources/app'));
app.use('/lib',  express.static(__dirname + '/bower_components'));

app.listen(port, function(){
    console.log('Express server listening on port ' + port);
});


