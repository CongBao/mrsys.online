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
public class Rating implements Cloneable, Serializable {

    private static final long serialVersionUID = 8106312321125729956L;

    @Id
	@Column(name = "rating_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "rating", nullable = false)
	private Float rating;
	
	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
	private User user;
	
	@ManyToOne(targetEntity = Movie.class)
	@JoinColumn(name = "movie_id", referencedColumnName = "movie_id", nullable = false)
	private Movie movie;
	
	public Rating() {
	}

	public Rating(Integer id, Float rating, User user, Movie movie) {
		super();
		this.id = id;
		this.rating = rating;
		this.user = user;
		this.movie = movie;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Rating other = (Rating) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Rating(getId(), getRating(), (User) getUser().clone(), (Movie) getMovie().clone());
    }

}
