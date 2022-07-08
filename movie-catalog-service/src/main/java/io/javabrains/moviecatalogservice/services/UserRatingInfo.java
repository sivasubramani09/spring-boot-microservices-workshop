package io.javabrains.moviecatalogservice.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.javabrains.moviecatalogservice.models.Rating;
import io.javabrains.moviecatalogservice.models.UserRating;

@Service
public class UserRatingInfo {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod = "getFallbackUserRating")
	public UserRating getUserRating(String userId) {
		return restTemplate.getForObject("http://ratings-data-service/ratingsdata/user/" + userId, UserRating.class);
	}
    
    public UserRating getFallbackUserRating(String userId) {
    	UserRating userRating = new UserRating();
    	userRating.setUserId(userId);
    	userRating.setRatings(Arrays.asList(
    		new Rating("0", 0)
    	));
		return userRating;
	}
}
