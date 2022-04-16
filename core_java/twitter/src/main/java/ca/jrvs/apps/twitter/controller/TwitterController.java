package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.tools.TweetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import java.util.List;

@org.springframework.stereotype.Controller
public class TwitterController implements Controller {
    private static final String COORD_SEP = ":";
    private static final String COMMA = ",";

    private Service service;

    @Autowired
    public TwitterController(Service service) {
        this.service = service;
    }

    @Override
    public Tweet postTweet(String[] args) {
        if (args.length != 3) {
            System.out.println("USAGE: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"");
            throw new IllegalArgumentException("USAGE: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"");
        }
        String tweetText = args[1];
        String coords = args[2];
        String[] coordArray = coords.split(COORD_SEP);

        if (coordArray.length != 2 || StringUtils.isEmpty(tweetText)) {
            System.out.println("Invalid location format: \n" +
                    "USAGE: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"");
            throw new IllegalArgumentException("Invalid location format: \n" +
                    "USAGE: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"");
        }

        Double lat = null;
        Double lon = null;

        try {
            lat = Double.parseDouble(coordArray[0]);
            lon = Double.parseDouble(coordArray[1]);
        } catch (Exception e) {
            System.out.println("Invalid location format: \n" +
                    "USAGE: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"" + e);
            throw new IllegalArgumentException("Invalid location format: \n" +
                    "USAGE: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"", e);
        }

        Tweet postTweet = TweetUtil.createTweet(tweetText, lat, lon);
        return service.postTweet(postTweet);
    }

    @Override
    public Tweet showTweet(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("USAGE: TwitterCLIApp show \"tweet_id\" \"field: 1,field: 2\"");
        }
        String tweetId = args[1];
        String[] fields = {};
        if (args.length >2) {
            fields = args[2].split(COMMA);
        }

        return service.showTweet(tweetId, fields);
    }

    @Override
    public List<Tweet> deleteTweet(String[] args) {
        if (args.length <2 ) {
            throw new IllegalArgumentException("USAGE: TwitterCLIApp delete \"id1,id2,..\"");
        }
        String[] tweetIds = args[1].split(",");

        return service.deleteTweets(tweetIds);
    }
}