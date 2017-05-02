package online.mrsys.movierecommender.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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
public class Movie implements Cloneable, Serializable {

    private static final long serialVersionUID = 6200071790747038611L;

    @Id
	@Column(name = "movie_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "imdb", length = 64)
	private String imdb;
	
	@Column(name = "year", nullable = false)
	private Integer year;
	
	@Column(name = "title", nullable = false, length = 256)
	private String title;
	
	@ManyToMany(targetEntity = User.class, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(name = "favorite",
	           joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "movie_id"),
	           inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"))
	private Set<User> users = new HashSet<>();
	
	@OneToMany(targetEntity = Rating.class, mappedBy = "movie")
	private Set<Rating> ratings = new HashSet<>();
	
	public Movie() {
	}

	public Movie(Integer id, String imdb, Integer year, String title, Set<User> users, Set<Rating> ratings) {
		super();
		this.id = id;
		this.imdb = imdb;
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

	public String getImdb() {
		return imdb;
	}

	public void setImdb(String imdb) {
		this.imdb = imdb;
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

	@Deprecated
	public Set<User> getUsers() {
		return users;
	}

	@Deprecated
	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Deprecated
	public Set<Rating> getRatings() {
		return ratings;
	}

	@Deprecated
	public void setRatings(Set<Rating> ratings) {
		this.ratings = ratings;
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
        Movie other = (Movie) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Movie(getId(), getImdb(), getYear(), getTitle(), new HashSet<>(), new HashSet<>());
    }

}
