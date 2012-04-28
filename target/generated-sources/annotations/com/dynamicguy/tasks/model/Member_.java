package com.dynamicguy.tasks.model;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Member.class)
public abstract class Member_ {

	public static volatile SingularAttribute<Member, Long> id;
	public static volatile SingularAttribute<Member, String> phoneNumber;
	public static volatile SingularAttribute<Member, String> email;
	public static volatile SingularAttribute<Member, String> name;
	public static volatile SetAttribute<Member, Role> memberRoles;

}

