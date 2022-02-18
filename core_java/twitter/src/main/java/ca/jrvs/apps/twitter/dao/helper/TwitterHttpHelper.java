package ca.jrvs.apps.twitter.dao.helper;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;

@Component
public class TwitterHttpHelper implements HttpHelper {

    /**
     * Dependencies are specified as private member variables
     */
    final OAuthConsumer consumer;
    HttpClient httpClient;

    /**
     * Constructor Setup dependencies using secrets
     *
     * @param consumerKey    Consumer key
     * @param consumerSecret Consumer secret
     * @param accessToken    Access token
     * @param tokenSecret    Access secret
     */
    public TwitterHttpHelper(String consumerKey, String consumerSecret, String accessToken,
                             String tokenSecret) {
        consumer = initConnection(consumerKey, consumerSecret, accessToken, tokenSecret);
    }

    /**
     * Default constructor
     */
    public TwitterHttpHelper() {
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");

        consumer = initConnection(consumerKey, consumerSecret, accessToken, tokenSecret);
    }

    /**
     * Initialize connection
     *
     * @param consumerKey    Consumer key
     * @param consumerSecret Consumer secret
     * @param accessToken    Access token
     * @param tokenSecret    Access secret
     * @return OAuthConsumer
     */
    private OAuthConsumer initConnection(String consumerKey, String consumerSecret,
                                         String accessToken, String tokenSecret) {
        final OAuthConsumer consumer;
        consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
        consumer.setTokenWithSecret(accessToken, tokenSecret);
        httpClient = HttpClientBuilder.create().build();
        return consumer;
    }

    @Override
    /*
     * HTTP POST request
     */
    public HttpResponse httpPost(URI uri) {
        try {
            return executeHttpRequest(HttpMethod.POST, uri, null);
        } catch (OAuthException | IOException e) {
            throw new RuntimeException("Execution failed", e);
        }
    }

    @Override
  /*
    HTTP GET request
   */
    public HttpResponse httpGet(URI uri) {
        try {
            return executeHttpRequest(HttpMethod.GET, uri, null);
        } catch (OAuthException | IOException e) {
            throw new RuntimeException("Execution failed", e);
        }
    }

    /**
     * Execute the HTTP request
     * @param method HTTP Method
     * @param uri URI
     * @param stringEntity Body
     * @return HTP Response
     * @throws OAuthException Auth exception
     * @throws IOException IO exception
     */
    private HttpResponse executeHttpRequest (HttpMethod method, URI uri, StringEntity stringEntity)
            throws OAuthException, IOException
    {
        if(method == HttpMethod.GET)
        {
            HttpGet request = new HttpGet(uri);
            consumer.sign(request);
            return httpClient.execute(request);
        } else if (method == HttpMethod.POST)
        {
            HttpPost request = new HttpPost(uri);
            if(stringEntity != null){
                request.setEntity(stringEntity);
            }
            consumer.sign(request);
            return httpClient.execute(request);
        } else {
            throw new IllegalArgumentException("Unknown HTTP method: " + method.name());
        }
    }
    }
