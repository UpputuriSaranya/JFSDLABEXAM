package com.klef.jfsd.exam;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "student2")
class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String gender;
    private String department;
    private String dob;
    private Long contactnumber;
    private Double cgpa;
    private int nob;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public Long getContactnumber() {
        return contactnumber;
    }

    public void setContactnumber(Long contactnumber) {
        this.contactnumber = contactnumber;
    }

    public Double getCgpa() {
        return cgpa;
    }

    public void setCgpa(Double cgpa) {
        this.cgpa = cgpa;
    }

   

	public int getNob() {
        return nob;
    }

    public void setNob(int nob) {
        this.nob = nob;
    }
    @Override
   	public String toString() {
   		return "Student [id=" + id + ", name=" + name + ", gender=" + gender + ", department=" + department + ", dob="
   				+ dob + ", contactnumber=" + contactnumber + ", cgpa=" + cgpa + ", nob=" + nob + "]";
   	}
}
