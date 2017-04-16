package online.mrsys.movierecommender.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "favorite")
@IdClass(Favorite.class)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Favorite implements Cloneable, Serializable {

    private static final long serialVersionUID = -8069690169913677424L;

    @Id
    @Column(name = "user_id")
	private Integer userId;
	
    @Id
	@Column(name = "movie_id")
	private Integer movieId;
	
	public Favorite() {
	}

	public Favorite(Integer userId, Integer movieId) {
		super();
		this.userId = userId;
		this.movieId = movieId;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((movieId == null) ? 0 : movieId.hashCode());
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
        Favorite other = (Favorite) obj;
        if (movieId == null) {
            if (other.movieId != null)
                return false;
        } else if (!movieId.equals(other.movieId))
            return false;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        return true;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Favorite(getUserId(), getMovieId());
    }
	
}
