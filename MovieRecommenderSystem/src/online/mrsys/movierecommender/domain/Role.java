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
public class Role implements Cloneable, Serializable {
    
    private static final long serialVersionUID = -7984433652600168582L;
    
    public static final Role ADMIN = new Role(new Integer(1), "ADMIN", new HashSet<>());
	public static final Role USER = new Role(new Integer(2), "USER", new HashSet<>());

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((role == null) ? 0 : role.hashCode());
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
        Role other = (Role) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (role == null) {
            if (other.role != null)
                return false;
        } else if (!role.equals(other.role))
            return false;
        return true;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Role(getId(), getRole(), new HashSet<>());
    }
	
}
