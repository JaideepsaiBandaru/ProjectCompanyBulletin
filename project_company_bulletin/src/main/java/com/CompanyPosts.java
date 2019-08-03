package com;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "companyposts")

public class CompanyPosts {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer pid;

	@Transient
	private Integer cid;
	private String title;
	private String body;

	@ManyToOne
	private User companyUsers;

	@OneToMany(mappedBy = "Cpost", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Comment> comments;

	public CompanyPosts() {

	}

	public CompanyPosts(Integer pid, String title, String body, User companyUsers) {
		super();
		this.pid = pid;
		this.title = title;
		this.body = body;
		this.companyUsers = companyUsers;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@JsonIgnore
	public User getCompanyUsers() {
		return companyUsers;
	}

	public void setCompanyUsers(User companyUsers) {
		this.companyUsers = companyUsers;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "CompanyPosts [Postsid=" + pid + ", cid=" + cid + ", title=" + title + ", body=" + body + ", companyUsers="
				+ companyUsers + "]";
	}

}
