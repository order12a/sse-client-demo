const sleep = require('system-sleep'),
    SSE = require('sse'),
    http = require('http');

const server = http.createServer(function(req, res) {
    res.writeHead(200, {'Content-Type': 'text/plain'});
    res.end('okay');
});

server.listen(8081, '127.0.0.1', function() {
    const sse = new SSE(server);
    sse.on('connection', function(client) {
        client.send({event:'INFO', data:'Connection established!'});
        sleep(1000);
        client.send({event:'INFO', data:'Hello World!'});
        sleep(1000);
        client.send({event:'INFO', data:'Hello World!'});
        sleep(1000);
        client.send({event:'INFO', data:'Hello World!'});
        sleep(1000);
        client.send({event:'INFO', data:'Possible break'});
        sleep(1000);
        client.send({event:'INFO', data:'Going to close connection!'});
        client.close();
    });
});
