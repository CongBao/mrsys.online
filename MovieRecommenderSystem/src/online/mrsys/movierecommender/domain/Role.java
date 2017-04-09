package online.mrsys.movierecommender.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Role implements Serializable {

	private static final long serialVersionUID = -3903976498900538656L;

	@Id
	@Column(name = "role_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "role", nullable = false, length = 32, unique = true)
	private String role;
	
	@OneToMany(targetEntity = User.class, mappedBy = "role")
	private Set<User> users = new HashSet<>();
	
	public Role() {
	}

	public Role(Integer id, String role, Set<User> users) {
		super();
		this.id = id;
		this.role = role;
		this.users = users;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Deprecated
	public Set<User> getUsers() {
		return users;
	}

	@Deprecated
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
}
