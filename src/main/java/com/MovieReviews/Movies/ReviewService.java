package com.MovieReviews.Movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository; //repositories are used to talk to the db

    @Autowired
    private MongoTemplate mongoTemplate; //templates are used to talk to DB

    public Review createReview(String reviewBody, String imdbId) {
        Review review = reviewRepository.insert(new Review(reviewBody));

        mongoTemplate.update(Movie.class) //using template to perform an update call on movie class field "reviewIds"
                .matching(Criteria.where("imdbId").is(imdbId)) // which is an empty array.This line indicates wat movie we are updating
                .apply(new Update().push("reviewIds").value(review))
                .first(); //make sure we are getting a single movie

        return review;
    }
}
