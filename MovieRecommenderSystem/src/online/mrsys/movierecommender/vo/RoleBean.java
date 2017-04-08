package online.mrsys.movierecommender.vo;

import java.io.Serializable;

import online.mrsys.movierecommender.util.Serializer;

public class RoleBean implements Serializable {
    
    private static final long serialVersionUID = 581157896062449167L;
    
    public static final RoleBean ADMIN = new RoleBean(new Integer(1), "ADMIN");
    public static final RoleBean USER = new RoleBean(new Integer(2), "USER");
    
    private Integer id;
	private String role;
	
	public RoleBean() {
	}

	public RoleBean(Integer id, String role) {
		super();
		this.id = id;
		this.role = role;
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
        RoleBean other = (RoleBean) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return new String(Serializer.serialize(this));
    }

}
