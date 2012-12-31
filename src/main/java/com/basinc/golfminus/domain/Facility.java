package com.basinc.golfminus.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.jboss.solder.core.Veto;

@Entity
@Veto
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Facility extends BaseEntity {

	@Column
	@NotNull
	private String name;
	
//	@XmlTransient
//	@JsonIgnore
//    @NotNull
    @OneToOne(cascade={CascadeType.ALL})
	private Address address;

    @Size(max=20)
    private String phone;
    
	@XmlTransient
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy="facility")
    private List<Course> courses = new ArrayList<Course>();
    
    public Facility() {};
    public Facility(String name, Address address) {this.name = name; this.address = address;};
    public Facility(String name, Address address, Course course) {
    	this(name,address); 
    	addCourse(course);
    };
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public List<Course> getCourses() {
		return courses;
	}
	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	
	public void addCourse(Course course) {
		course.setFacility(this);
		courses.add(course);
	}
	
	public void removeCourse(Course course) {
		course.setFacility(null);
		courses.remove(course);
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

}
