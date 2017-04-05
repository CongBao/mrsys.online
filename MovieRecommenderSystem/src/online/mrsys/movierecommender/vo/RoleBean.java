package online.mrsys.movierecommender.vo;

import java.io.Serializable;

import online.mrsys.movierecommender.function.Serializer;

public class RoleBean implements Serializable {
    
    private static final long serialVersionUID = -2919180918330661552L;
    
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
    public String toString() {
        return new String(Serializer.serialize(this));
    }

}
