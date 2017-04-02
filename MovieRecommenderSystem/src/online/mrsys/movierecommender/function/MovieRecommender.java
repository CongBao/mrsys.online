package online.mrsys.movierecommender.function;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import online.mrsys.movierecommender.domain.Movie;
import online.mrsys.movierecommender.domain.Rating;
import online.mrsys.movierecommender.domain.User;

public class MovieRecommender {
	
	/**
	 * Generate the recommendation list of the target user.
	 * 
	 * @param targetUser
	 *            the target user
	 * @return a list of movie-recommend pair
	 */
	public List<Entry<Movie, Float>> recommend(User targetUser) {
		// firstly find the negihbors and their similarities
		List<Entry<User, Float>> neighbors = getNeighbors(targetUser);
		HashMap<Movie, Float> estimatedRatings = new HashMap<>(1500);
		HashMap<Movie, Float> similarityTotal = new HashMap<>(1500);
		// secondly estiamte the target user's ratings on all the movies that
		// he/she hasn't rated
		for (Entry<User, Float> neighborSimilarity : neighbors) {
			User neighbor = neighborSimilarity.getKey();
			Float similarity = neighborSimilarity.getValue();
			for (Rating movieRating : neighbor.getRatings()) {
				Float rating = movieRating.getRating();
				Movie movie = movieRating.getMovie();
				if (!estimatedRatings.containsKey(movie)) {
					estimatedRatings.put(movie, rating * similarity);
					similarityTotal.put(movie, similarity);
				} else {
					estimatedRatings.replace(movie, estimatedRatings.get(movie) + similarity * rating);
					similarityTotal.replace(movie, similarityTotal.get(movie) + similarity);
				}
			}
		}
		for (Movie movie : estimatedRatings.keySet()) {
			estimatedRatings.replace(movie, estimatedRatings.get(movie) / similarityTotal.get(movie));
		}
		List<Entry<Movie, Float>> results = new ArrayList<>(estimatedRatings.entrySet());
		Collections.sort(results, (o1, o2) -> o1.getValue().compareTo(o2.getValue()));
		return results;
	}

	/**
	 * Get the similarity of two users.
	 * 
	 * @param user1
	 *            the first user
	 * @param user2
	 *            the second user
	 * @return the similarity between two users
	 */
	private float getSimilarity(User user1, User user2) {
		float similarity = 0.0f;
		float sum_x = 0.0f;
		float sum_y = 0.0f;
		float sum_xy = 0.0f;
		for (Rating rating1 : user1.getRatings()) {
			for (Rating rating2 : user2.getRatings()) {
				if (rating1.getMovie().getId() == rating2.getMovie().getId()) {
					sum_xy += rating1.getRating() * rating2.getRating();
					sum_y += rating2.getRating() * rating2.getRating();
					sum_x += rating1.getRating() * rating1.getRating();
				}
			}
		}
		if (sum_xy == 0.0f) {
			similarity = 0;
		} else {
			float sx_xy = (float) Math.sqrt(sum_x * sum_y);
			similarity = sum_xy / sx_xy;
		}
		return similarity;
	}

	/**
	 * Get the map of Neighbor-Similarity.
	 * 
	 * @param targetUser
	 *            the target user
	 * @return a list of neighbor-similarity pair
	 */
	private List<Entry<User, Float>> getNeighbors(User targetUser) {
		HashMap<User, Float> neighbors = new HashMap<>(1500);
		for (Rating rating : targetUser.getRatings()) {
			Movie movie = rating.getMovie();
			for (User neighbor : movie.getUsers()) {
				if (neighbor.getId() != targetUser.getId() && !neighbors.containsKey(neighbor)) {
					neighbors.put(neighbor, getSimilarity(neighbor, targetUser));
				}
			}
		}
		List<Entry<User, Float>> results = new ArrayList<>(neighbors.entrySet());
		Collections.sort(results, (o1, o2) -> o1.getValue().compareTo(o2.getValue()));
		return results;
	}
	
}
