package com.dynamicguy.tasks.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Entity
@XmlRootElement
@Table(name = "roles", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "ROLE_ID")
	private Long id;

	@NotNull
	@Size(min = 1, max = 25)
	@Pattern(regexp = "[A-Za-z ]*", message = "must contain only letters and spaces")
	private String name;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "members_roles", joinColumns = { @JoinColumn(name = "ROLE_ID") }, inverseJoinColumns = { @JoinColumn(name = "MEMBER_ID") })
	private Set<Member> roleMembers = new HashSet<Member>(0);

	public Role() {

	}

	public Role(String name) {
		this.name = name;
	}

	public Role(String name, Set<Member> roleMembers) {
		this.name = name;
		this.roleMembers = roleMembers;
	}

	public Set<Member> getRoleMembers() {
		if (roleMembers == null) {
			roleMembers = new HashSet<Member>();
		}
		return roleMembers;
	}

	public void setRoleMembers(Set<Member> roleMembers) {
		this.roleMembers = roleMembers;
	}

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

}
