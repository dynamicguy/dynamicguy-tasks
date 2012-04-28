package com.dynamicguy.tasks.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.dynamicguy.tasks.model.Role;

@Path("/roles")
@RequestScoped
public class RoleResourceRESTService {
	@Inject
	private EntityManager em;

	@GET
	@Produces("text/xml")
	public List<Role> listAllRoles() {
		@SuppressWarnings("unchecked")
		final List<Role> results = em.createQuery(
				"select r from Role r order by r.name").getResultList();
		return results;
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces("text/xml")
	public Role lookupRoleById(@PathParam("id") long id) {
		return em.find(Role.class, id);
	}
}
