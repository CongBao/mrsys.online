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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User implements Cloneable, Serializable {

    private static final long serialVersionUID = 5153247670782885713L;

    @Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "account", nullable = false, length = 64, unique = true)
	private String account;
	
	@Column(name = "password", nullable = false, length = 64)
	private String password;
	
	@Column(name = "email", nullable = false, length = 64, unique = true)
	private String email;
	
	@Column(name = "mail_verified")
	@Type(type = "yes_no")
	private Boolean mailVerified = false;
	
	@Column(name = "recommendation")
	private byte[] recommendation;
	
	@ManyToOne(targetEntity = Role.class)
	@JoinColumn(name = "role_id", referencedColumnName = "role_id", nullable = false)
	private Role role;
	
	@ManyToMany(targetEntity = Movie.class, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(name = "favorite",
	           joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
	           inverseJoinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "movie_id"))
	private Set<Movie> movies = new HashSet<>();
	
	@OneToMany(targetEntity = Rating.class, mappedBy = "user")
	private Set<Rating> ratings = new HashSet<>();
	
	public User() {
	}

    public User(Integer id, String account, String password, String email, Boolean mailVerified, byte[] recommendation,
            Role role, Set<Movie> movies, Set<Rating> ratings) {
        super();
        this.id = id;
        this.account = account;
        this.password = password;
        this.email = email;
        this.mailVerified = mailVerified;
        this.recommendation = recommendation;
        this.role = role;
        this.movies = movies;
        this.ratings = ratings;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getMailVerified() {
        return mailVerified;
    }

    public void setMailVerified(Boolean mailVerified) {
        this.mailVerified = mailVerified;
    }

    public byte[] getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(byte[] recommendation) {
        this.recommendation = recommendation;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Deprecated
    public Set<Movie> getMovies() {
        return movies;
    }

    @Deprecated
    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
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
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
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
		User other = (User) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new User(getId(), getAccount(), getPassword(), getEmail(), getMailVerified(), getRecommendation(), (Role) getRole().clone(), new HashSet<>(), new HashSet<>());
    }
	
}
