from http.server import HTTPServer, BaseHTTPRequestHandler


class SimpleHTTPRequestHandler(BaseHTTPRequestHandler):

    def do_GET(self):
        self.send_response(200)
        self.end_headers()
        self.wfile.write(b'198.18.18.11:9300,198.18.18.11:9300')


httpd = HTTPServer(('0.0.0.0', 18000), SimpleHTTPRequestHandler)
httpd.serve_forever()
