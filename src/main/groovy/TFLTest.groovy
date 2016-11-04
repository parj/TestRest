import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.RESTClient
import static groovyx.net.http.ContentType.TEXT
import static groovyx.net.http.ContentType.JSON
import org.apache.commons.io.IOUtils;

def url = "https://api.tfl.gov.uk"
final LINE = "Line"
final INTERESTED_LINES = "jubilee,central"
final REQUEST = "Arrivals"
final APP_ID = "app_id"
final APP_KEY = "app_key"
final MY_HIDDEN_KEY_APP_ID = System.getenv("MY_HIDDEN_KEY_APP_ID")
final MY_HIDDEN_KEY_APP = System.getenv("MY_HIDDEN_KEY_APP")


def client = new RESTClient(url)
def response = client.get(  path: "/${LINE}/${INTERESTED_LINES}/${REQUEST}",
                            contentType: TEXT,
                            query: [APP_ID: MY_HIDDEN_KEY_APP_ID, APP_KEY: MY_HIDDEN_KEY_APP])
println "Status -- ${response.statusLine}"
assert response.status == 200

def jsonResponse = convertResponseToJSON(response)
def canaryWharf = jsonResponse.find { it.stationName == 'Canary Wharf Underground Station' }
println canaryWharf


def convertResponseToJSON(HttpResponseDecorator restResponse) {
    def responseString = IOUtils.toString(restResponse.getData());
    return new JsonSlurper().parseText(responseString)
}