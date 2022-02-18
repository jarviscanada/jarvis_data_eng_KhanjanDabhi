package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.tools.JsonParser;
import ca.jrvs.apps.twitter.model.Tweet;
import com.google.gdata.util.common.base.PercentEscaper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Repository
public class TwitterDao implements CrdDao<Tweet, String> {

    // URI constants
    private static final String API_BASE_URI = "https://api.twitter.com";
    private static final String POST_PATH = "/1.1/statuses/update.json";
    private static final String SHOW_PATH = "/1.1/statuses/show.json";
    private static final String DELETE_PATH = "/1.1/statuses/destroy.json";

    // URI symbols
    private static final String QUERY_SYM = "?";
    private static final String AMPERSAND = "&";
    private static final String EQUAL = "=";

    // HTTP response code
    private static final int HTTP_OK = 200;

    private final HttpHelper httpHelper;
    private final Logger logger = LoggerFactory.getLogger(TwitterDao.class);
    private final PercentEscaper percentEscaper = new PercentEscaper("", false);

    @Autowired
    public TwitterDao(HttpHelper httpHelper) {
        this.httpHelper = httpHelper;
    }

    /**
     * Create an Entity(Tweet) to the underlying storage
     *
     * @param tweet entity that need to be created
     * @return entity created successfully
     */
    @Override
    public Tweet create(Tweet tweet) {
        URI uri;
        try {
            uri = getPostUri(tweet);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Usage: syntax exception : " + e);
        }
        HttpResponse response = httpHelper.httpPost(uri);
        return parseResponseBody(response);
    }

    /**
     * Helper function to get the POST uri
     *
     * @param tweet the tweet need to be posted
     * @return the uri we can use
     * @throws URISyntaxException exception with wrong uri syntax
     */
    private URI getPostUri(Tweet tweet) throws URISyntaxException {
        List<Double> coordinates = tweet.getCoordinates().getCoordinates();
        return new URI(API_BASE_URI + POST_PATH + QUERY_SYM
                + "status" + EQUAL + percentEscaper.escape(tweet.getText())
                + AMPERSAND + "long" + EQUAL + percentEscaper.escape(String.valueOf(coordinates.get(0)))
                + AMPERSAND + "lat" + EQUAL + percentEscaper.escape(String.valueOf(coordinates.get(1))));
    }

    /**
     * Check response status code and Covert Response Entity to Tweet Object if the status code = 200
     * and the Entity body is not empty
     *
     * @param response HTTP request response
     * @return Tweet Object
     */
    protected Tweet parseResponseBody(HttpResponse response) {
        int status = response.getStatusLine().getStatusCode();
        if (status != TwitterDao.HTTP_OK) {
            throw new RuntimeException("Unexpected HTTP status: " + status);
        }

        HttpEntity httpEntity = response.getEntity();
        String tweetStr;
        if (httpEntity == null) {
            throw new RuntimeException("Empty Entity body");
        } else {
            try {
                tweetStr = EntityUtils.toString(httpEntity);
                // logger.info(tweetStr);
            } catch (IOException e) {
                throw new RuntimeException("Failed to convert Entity to String");
            }
        }

        try {
            return JsonParser.toObjectFromJson(tweetStr, Tweet.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert JSON to Tweet Object", e);
        }
    }

    /**
     * Find an Entity(Tweet) by its id
     *
     * @param id Entity Id
     * @return Tweet Entity
     */
    @Override
    public Tweet findById(String id) {
        URI uri;
        try {
            uri = new URI(API_BASE_URI + SHOW_PATH + QUERY_SYM + "id" + EQUAL + id);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Usage: Tweet ID not found:" + e);
        }
        return parseResponseBody(httpHelper.httpGet(uri));
    }

    @Override
    public Tweet deleteById(String tweetIdD) {
        URI uri;
        try {
            uri = new URI(API_BASE_URI + DELETE_PATH + QUERY_SYM + "id" + EQUAL + tweetIdD);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Tweet ID not found:" + e);
        }
        return parseResponseBody(httpHelper.httpPost(uri));
    }
}