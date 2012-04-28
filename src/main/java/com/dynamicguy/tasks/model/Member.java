package com.dynamicguy.tasks.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@XmlRootElement
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Member implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@Size(min = 1, max = 25)
	@Pattern(regexp = "[A-Za-z ]*", message = "must contain only letters and spaces")
	private String name;

	@NotNull
	@NotEmpty
	@Email
	private String email;

	@NotNull
	@Size(min = 10, max = 12)
	@Digits(fraction = 0, integer = 12)
	@Column(name = "phone_number")
	private String phoneNumber;

	private Set<Role> memberRoles;

	public boolean hasRole(String roleName) {
		Set<Role> myRoles = getMemberRoles();
		for (Role next : myRoles) {
			if (next.getName().equals(roleName)) {
				return true;
			}
		}
		return false;
	}

	public void removeRole(Role role) {
		getMemberRoles().remove(role);
		role.getRoleMembers().remove(this);
	}

	public void addRole(Role role) {
		getMemberRoles().add(role);
		role.getRoleMembers().add(this);
	}

	@Transient
	public Role[] getRoles() {
		return (Role[]) getMemberRoles().toArray(new Role[0]);
	}

	@ManyToMany(targetEntity = Role.class, cascade = {
			CascadeType.PERSIST, CascadeType.MERGE })
	protected Set<Role> getMemberRoles() {
		if (memberRoles == null) {
			memberRoles = new HashSet<Role>();
		}
		return memberRoles;
	}

	protected void setMemberRoles(Set<Role> memberRoles) {
		this.memberRoles = memberRoles;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}