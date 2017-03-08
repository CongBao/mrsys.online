package online.mrsys.movierecommender.vo;

import java.io.Serializable;

public class MovieBean implements Serializable {
	
	private static final long serialVersionUID = 2598891223637703190L;
	
	private Integer id;
	private Integer year;
	private String title;
	
	public MovieBean() {
	}

	public MovieBean(Integer id, Integer year, String title) {
		super();
		this.id = id;
		this.year = year;
		this.title = title;
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

	@Override
	public String toString() {
		return id + "," + year + "," + title;
	}

}
