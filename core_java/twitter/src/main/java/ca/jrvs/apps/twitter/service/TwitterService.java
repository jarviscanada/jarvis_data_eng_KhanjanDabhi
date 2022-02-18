package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import org.springframework.beans.factory.annotation.Autowired;;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class TwitterService implements Service{

    private CrdDao dao;
    private static final int MAX_LENGTH=140;
    private static final int MAX_LATITUDE=90;
    private static final int MAX_LONGITUDE=180;
    private static final int MIN_LATITUDE=-90;
    private static final int MIN_LONGITUDE=-180;

    @Autowired
    public TwitterService(CrdDao dao) { this.dao = dao; }

    @Override
    public Tweet postTweet(Tweet tweet) {
        validatePostTweet(tweet);
        return (Tweet) dao.create(tweet);
    }

    @Override
    public Tweet showTweet(String id, String[] fields) {
        validateId(id);
        return(Tweet) dao.findById(id);
    }

    @Override
    public List<Tweet> deleteTweets(String[] ids) {
        validateManyIds(ids);
        List<Tweet> tweets = new ArrayList<>();
        for(String id : ids)
        {
            tweets.add((Tweet)dao.deleteById(id));
        }
        return tweets;
    }

    private void validatePostTweet(Tweet tweet){
        int textLength = tweet.getText().length();
        Double latitude = tweet.getCoordinates().getCoordinates().get(0);
        Double longitude =  tweet.getCoordinates().getCoordinates().get(1);

        //checking length of tweet text
        if(textLength > MAX_LENGTH){
            throw new RuntimeException("Max text length exceeded, Tweet should be less than 140 characters in length");
        }
        //checking range of latitude in coordinates
        if(latitude < MIN_LATITUDE && latitude > MAX_LATITUDE)
        {
            throw new RuntimeException("Latitude value out of range, Latitude should be in range of  [-90,90]");
        }
        //checking range of longitude in coordinates
        if(longitude < MIN_LONGITUDE && longitude > MAX_LONGITUDE)
        {
            throw new RuntimeException("Longitude value out of range, Longitude should be in range of [-180,180]");
        }
    }


    private void validateId(String strId){
        String regex = "\\d+";
        if(strId == null){
            throw new NullPointerException("ID field cannot be empty");
        }
        if(!strId.matches(regex))
        {
            throw new RuntimeException("Not correct format for ID, ID must be a LONG in regular expresson of [0-9]+");
        }
    }

    private void validateManyIds(String ...ids){
        for(String id : ids){
            validateId(id);
        }
    }

}