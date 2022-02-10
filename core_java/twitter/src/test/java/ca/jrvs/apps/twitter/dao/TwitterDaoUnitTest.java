package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.tools.JsonParser;
import ca.jrvs.apps.twitter.tools.TweetUtil;
import ca.jrvs.apps.twitter.model.Tweet;
import net.minidev.json.JSONUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TwitterDaoUnitTest {
    @Mock
    HttpHelper mockHelper;

    @InjectMocks
    TwitterDao dao;

    @Test
    public void showTweet() throws Exception
    {
        //test failed request
        String hashtag = "#abc";
        String text = "unitTest sometext " + hashtag + " " + System.currentTimeMillis();
        double lat = 1d;
        double lon = -1d;
        //Exception is expected here
        when(mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));
        try
        {
            dao.create(TweetUtil.createTweet(text, lon, lat));
            fail();
        } catch(RuntimeException e){
            assertTrue(true);
        }

        //Test happy path
        //Make a spyDao which can fake parseResponseBody return value
        String tweetJsonStr = "{\n" +
                "   \"created_at\": \"Thu Feb 10 14:10:52 +0000 2022\",\n" +
                "   \"id\": 1491852072848920578,\n" +
                "   \"id_str\": \"1491852072848920578\",\n" +
                "   \"text\": \"first_tweet2)\",\n" +
                "   \"entities\": {\n" +
                "       \"hashtags\": []," +
                "       \"user_mentions\": []" +
                "   },\n" +
                "   \"coordinates\": null,\n" +
                "   \"retweet_count\": 0,\n" +
                "   \"favorite_count\": 0,\n" +
                "   \"favorited\": false,\n" +
                "   \"retweeted\": false\n" +
                "}";

        when(mockHelper.httpPost(isNotNull())).thenReturn(null);
        TwitterDao spyDao = spy(dao);
        Tweet expectedTweet = JsonParser.toObjectFromJson(tweetJsonStr, Tweet.class);
        //mock parseResponseBody
        doReturn(expectedTweet).when(spyDao).parseResponseBody(any());
        Tweet tweet = spyDao.create(TweetUtil.createTweet(text, lon, lat));
        assertNotNull(tweet);
        assertNotNull(tweet.getText());
    }


    @Test
    public void postTweet() throws Exception
    {

    }


    @Test
    public void deleteTweet() throws Exception
    {

    }

}