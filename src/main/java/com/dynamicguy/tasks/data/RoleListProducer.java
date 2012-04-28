package com.dynamicguy.tasks.data;

import com.dynamicguy.tasks.model.Role;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@RequestScoped
public class RoleListProducer {
	@Inject
	private EntityManager em;

	private List<Role> roles;

	@Produces
	@Named
	public List<Role> getRoles() {
		return roles;
	}

	public void onRoleListChanged(
			@Observes(notifyObserver = Reception.IF_EXISTS) final Role role) {
		retrieveAllRolesOrderedByName();
	}

	@PostConstruct
	public void retrieveAllRolesOrderedByName() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Role> criteria = cb.createQuery(Role.class);
		Root<Role> role = criteria.from(Role.class);
		criteria.select(role).orderBy(cb.asc(role.get("name")));
		roles = em.createQuery(criteria).getResultList();
	}
}
