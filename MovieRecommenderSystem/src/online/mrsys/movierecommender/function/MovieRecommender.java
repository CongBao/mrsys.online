package online.mrsys.movierecommender.function;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import online.mrsys.movierecommender.domain.*;

public class MovieRecommender {
	private List<User> userList;
	private List<Movie> movieList;

	public MovieRecommender(List<User> userList, List<Movie> movieList) {
		this.userList = userList;
		this.movieList = movieList;
	}

	// get the similarity of two users
	private float getSimilarity(User user1, User user2){
		float similarity = 0.0f;
		float sum_x = 0.0f;
		float sum_y = 0.0f;
		float sum_xy = 0.0f;
		for (Rating rating1 : user1.getRatings()){
			for (Rating rating2 : user2.getRatings()){
				if (rating1.getMovie().getId() == rating2.getMovie().getId()){
					sum_xy += rating1.getRating() * rating2.getRating();
					sum_y += rating2.getRating() * rating2.getRating();
					sum_x += rating1.getRating() * rating1.getRating();
				}
			}
		}
		if (sum_xy == 0.0f){
			similarity =  0;
		} else {
			float sx_xy = (float) Math.sqrt(sum_x * sum_y);
			similarity = sum_xy / sx_xy;
		}
		return similarity;
	}
	
	// get the map of Neighbor-Similarity
	private List<Entry<User, Float>> getNeighbors(User targetUser){
		HashMap<User, Float> neighbors = new HashMap<User, Float>();
		for (Rating rating : targetUser.getRatings()){
			Movie movie = rating.getMovie();
			for (User neighbor : movie.getUsers()){
				if (neighbor.getId() != targetUser.getId() && !neighbors.containsKey(neighbor)){
					neighbors.put(neighbor, getSimilarity(neighbor, targetUser));
				}
			}
		}
		List<Entry<User, Float>> results = new ArrayList<Entry<User, Float>>(neighbors.entrySet());
		Collections.sort(results, new Comparator<Entry<User, Float>>() {
			@Override
			public int compare(Entry<User, Float> o1, Entry<User, Float> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}	
		});
		return results;
	}
	
	// generate the recommendation list of the target user
	public List<Entry<Movie, Float>> recommend(User targetUser) {
		// firstly find the negihbors and their similarities
		List<Entry<User, Float>> neighbors = getNeighbors(targetUser);
		HashMap<Movie, Float> estimatedRatings = new HashMap<Movie, Float>();
		HashMap<Movie, Float> similarityTotal = new HashMap<Movie, Float>();
		// secondly estiamte the target user's ratings on all the movies that he/she hasn't rated
		for (Entry<User,Float> neighborSimilarity : neighbors){
			User neighbor = neighborSimilarity.getKey();
			Float similarity = neighborSimilarity.getValue();
			for (Rating movieRating : neighbor.getRatings()){
				Float rating = movieRating.getRating();
				Movie movie = movieRating.getMovie();
				if (!estimatedRatings.containsKey(movie)){
					estimatedRatings.put(movie, rating * similarity);
					similarityTotal.put(movie, similarity);
				} else {
					estimatedRatings.replace(movie, estimatedRatings.get(movie) + similarity * rating);
					similarityTotal.replace(movie, similarityTotal.get(movie) + similarity);
				}
			}
		}
		for (Movie movie : estimatedRatings.keySet()){
			estimatedRatings.replace(movie, estimatedRatings.get(movie) / similarityTotal.get(movie));
		}
		List<Entry<Movie, Float>> results = new ArrayList<Entry<Movie, Float>>(estimatedRatings.entrySet());
		Collections.sort(results, new Comparator<Entry<Movie, Float>>() {
			@Override
			public int compare(Entry<Movie, Float> o1, Entry<Movie, Float> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}	
		});
		return results;
	}
}
