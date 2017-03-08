package online.mrsys.movierecommender.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "favorite")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Favorite implements Serializable {

	private static final long serialVersionUID = -4384174191175832946L;

	@Id
	@Column(name = "favorite_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "user_id")
	private Integer userId;
	
	@Column(name = "movie_id")
	private Integer movieId;
	
	public Favorite() {
	}

	public Favorite(Integer id, Integer userId, Integer movieId) {
		super();
		this.id = id;
		this.userId = userId;
		this.movieId = movieId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getMovieId() {
		return movieId;
	}

	public void setMovieId(Integer movieId) {
		this.movieId = movieId;
	}
	
}
