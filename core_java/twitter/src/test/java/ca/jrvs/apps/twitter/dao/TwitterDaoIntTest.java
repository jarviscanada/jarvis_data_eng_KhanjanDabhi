package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.tools.JsonParser;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.tools.TweetUtil;
import com.google.gdata.util.common.base.PercentEscaper;
import net.minidev.json.JSONUtil;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class TwitterDaoIntTest {

    private TwitterDao dao;
    private JsonParser JsonUtil;

    @Before
    public void setUp() {
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");
        System.out.println(consumerKey + "|" + consumerSecret + "|" + accessToken + "|" + tokenSecret);
        //Set up dependency
        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
        //pass dependency
        this.dao = new TwitterDao(httpHelper);
        this.JsonUtil = new JsonParser();
    }

    @Test
    public void create() throws Exception{
        String hashtag = "#abcd";
        String text = "@Unit Test Twitter Test " + hashtag + " " + System.currentTimeMillis();
        Double lat = 1d;
        Double lon = 1d;

        Tweet postTweet = TweetUtil.createTweet(text, lon, lat);
        System.out.println(JsonUtil.toJson(postTweet, true, false));
        Tweet tweet = dao.create(postTweet);

        assertEquals(text, tweet.getText());
        assertNotNull(tweet.getCoordinates());
        assertEquals(2, tweet.getCoordinates().getCoordinates().size());
        assertEquals(lon, tweet.getCoordinates().getCoordinates().get(0));
        assertEquals(lat, tweet.getCoordinates().getCoordinates().get(1));
    }

    @Test
    public void findById() {
        Tweet tweet = dao.findById("1234");
        assertEquals("Tue Apr 04 19:35:51 +0000 2006",tweet.getCreatedAt());
        assertNotNull(tweet.getText());
    }

    @Test
    public void deleteById() {
        //update string id with newly created tweet ID
        String id = "1491884240564367360";
        Tweet tweet = dao.deleteById(id);
        assertEquals(id, tweet.getIdStr());
        assertNotNull(tweet);
        assertNotNull(tweet.getText());
    }
}