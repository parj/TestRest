/**
 * Created by parj on 28/10/2016.
 */


import groovy.json.JsonSlurper
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.RESTClient
import static groovyx.net.http.ContentType.TEXT
import static groovyx.net.http.ContentType.JSON
import org.apache.commons.io.IOUtils;



def url = "http://api.ft.com"
def apiPath = "/site/v1/pages"

def client = new RESTClient(url)
client.setHeaders("X-Api-Key": System.getenv("MY_HIDDEN_KEY"))

def response = client.get( path : apiPath, contentType: TEXT)
assert response.status == 200
def jsonResponse = convertResponseToJSON(response)

def finTechID = (jsonResponse."pages".find { it.title == '#fintechFT' }."id").toString()
println "finTechID -- ${finTechID}"

response = client.get( path: apiPath + "/" + finTechID, contentType: TEXT)
println "Status -- ${response.statusLine}"
println "Response -- ${response.getData()}"

/*
def inputFile = new File("/tmp/json.txt")

def json = new JsonSlurper().parse(inputFile)

println "ID - " + json.pages.find { it.title == '50 Leading Business Pioneers' }."id"*/

def convertResponseToJSON(HttpResponseDecorator restResponse) {
    def responseString = IOUtils.toString(restResponse.getData());
    return new JsonSlurper().parseText(responseString)
}