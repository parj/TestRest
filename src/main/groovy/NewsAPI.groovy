import groovy.json.JsonSlurper
import org.apache.commons.io.IOUtils
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.utils.URIBuilder
import org.apache.http.impl.client.HttpClientBuilder

final HOST =  "newsapi.org"
final PATH = "/v1/articles"
final NEWS_SOURCE = "bbc-news"
final SOURCE = "source"
final API_KEY = "apiKey"
final SORT_BY = "sortBy"
final MY_HIDDEN_KEY = System.getenv("MY_HIDDEN_KEY")

URIBuilder builder = new URIBuilder();
builder.setScheme("https").setHost(HOST).setPath(PATH)
    .setParameter(SOURCE, NEWS_SOURCE)
    .setParameter(API_KEY, MY_HIDDEN_KEY)
URI uri = builder.build()

HttpClient client = HttpClientBuilder.create().build();
HttpGet request = new HttpGet(uri);
HttpResponse response = client.execute(request);

println "STATUS -- ${response.getStatusLine().getStatusCode()}"

def jsonResponse = convertResponseToJSON(response.getEntity().getContent())
println "RESPONSE -- ${jsonResponse}"

def convertResponseToJSON(Object restResponse) {
    return new JsonSlurper().parseText(IOUtils.toString(restResponse))
}