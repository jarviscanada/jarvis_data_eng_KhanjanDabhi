package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.tools.TweetUtil;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import java.util.List;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

@Ignore
public class TwitterServiceIntTest {

    private TwitterService service;

    @Before
    public void setUp() {
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");

        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken,
                tokenSecret);

        TwitterDao dao = new TwitterDao(httpHelper);
        service = new TwitterService(dao);
    }

    @Test
    public void PostTweet() {
        Tweet postedTweet = TweetUtil.createTweet("Integration Testing-PostTweet(). Service Layer Twitter API from JAVA" + System.currentTimeMillis(),
                30.0f, 31.0f);

        Tweet postTweet = service.postTweet(postedTweet);

        assertEquals(postedTweet.getText(), postTweet.getText());
        assertNotNull(postTweet.getCoordinates());
        assertEquals(2, postTweet.getCoordinates().getCoordinates().size());
        assertEquals(postedTweet.getCoordinates().getCoordinates().get(0),
                postTweet.getCoordinates().getCoordinates().get(0));
        assertEquals(postedTweet.getCoordinates().getCoordinates().get(1),
                postTweet.getCoordinates().getCoordinates().get(1));
    }

    @Test
    public void ShowTweet() {
        Tweet tweetBePosted = TweetUtil.createTweet(
                "Integration Testing-ShowTweet(). Service Layer Twitter API from JAVA" + System.currentTimeMillis(), 40.0f, 41.0f);

        Tweet showTweet = service.postTweet(tweetBePosted);

        assertEquals(tweetBePosted.getText(), showTweet.getText());
        assertNotNull(showTweet.getCoordinates());
        assertEquals(2, showTweet.getCoordinates().getCoordinates().size());
        assertEquals(tweetBePosted.getCoordinates().getCoordinates().get(0),
                showTweet.getCoordinates().getCoordinates().get(0));
        assertEquals(tweetBePosted.getCoordinates().getCoordinates().get(1),
                showTweet.getCoordinates().getCoordinates().get(1));
    }

    @Test
    public void DeleteTweet() {
        Tweet tweet1BeDeleted = TweetUtil.createTweet(
                "Integration Testing-DeleteTweet(). Service Layer Twitter API from JAVA Tweet NO. 1" + System.currentTimeMillis(), 50.0f, 51.0f);
        Tweet tweet2BeDeleted = TweetUtil.createTweet(
                "Integration Testing-DeleteTweet(). Service Layer Twitter API from JAVA Tweet NO. 2" + System.currentTimeMillis(), 50.0f, 51.0f);

        Tweet deletedTweet1 = service.postTweet(tweet1BeDeleted);
        Tweet deletedTweet2 = service.postTweet(tweet2BeDeleted);

        String Tweet1Id = deletedTweet1.getIdStr();
        String Tweet2Id = deletedTweet2.getIdStr();

        String[] ids = {Tweet1Id, Tweet2Id};
        List<Tweet> deletedTweets = service.deleteTweets(ids);

        assertEquals(deletedTweet1.getText(), deletedTweets.get(0).getText());
        assertEquals(deletedTweet2.getText(), deletedTweets.get(1).getText());
    }

}