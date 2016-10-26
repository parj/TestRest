import groovyx.net.http.RESTClient
import static groovyx.net.http.ContentType.JSON


def url = 'http://api.duckduckgo.com'

def client = new RESTClient(url)
def response = client.get( path : '/',
                        contentType: JSON,
                        query : ['q':'DuckDuckGo', 'format':'json'])

assert response.status == 200
println "Status -- ${response.statusLine}"
println "Response -- ${response.getData()}"