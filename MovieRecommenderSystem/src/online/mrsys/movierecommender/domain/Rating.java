package online.mrsys.movierecommender.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "rating")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Rating implements Serializable {

	private static final long serialVersionUID = 4129958619460012035L;

	@Id
	@Column(name = "rating_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "rate", nullable = false)
	private Float rate;
	
	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
	private User user;
	
	@ManyToOne(targetEntity = Movie.class)
	@JoinColumn(name = "movie_id", referencedColumnName = "movie_id", nullable = false)
	private Movie movie;
	
	public Rating() {
	}

	public Rating(Integer id, Float rate, User user, Movie movie) {
		super();
		this.id = id;
		this.rate = rate;
		this.user = user;
		this.movie = movie;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Float getRate() {
		return rate;
	}

	public void setRate(Float rate) {
		this.rate = rate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

}
