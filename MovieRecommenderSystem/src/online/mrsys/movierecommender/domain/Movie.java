package online.mrsys.movierecommender.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "movie")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Movie implements Serializable {

	private static final long serialVersionUID = -6753131595535925807L;

	@Id
	@Column(name = "movie_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "year", nullable = false)
	private Integer year;
	
	@Column(name = "title", nullable = false, length = 64)
	private String title;
	
	@ManyToMany(targetEntity = User.class)
	@JoinTable(name = "favorite",
	           joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "movie_id"),
	           inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"))
	private Set<User> users = new HashSet<>();
	
	@OneToMany(targetEntity = Rating.class, mappedBy = "movie")
	private Set<Rating> ratings = new HashSet<>();
	
	public Movie() {
	}

	public Movie(Integer id, Integer year, String title, Set<User> users, Set<Rating> ratings) {
		super();
		this.id = id;
		this.year = year;
		this.title = title;
		this.users = users;
		this.ratings = ratings;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(Set<Rating> ratings) {
		this.ratings = ratings;
	}

}
