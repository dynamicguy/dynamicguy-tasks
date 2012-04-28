package com.dynamicguy.tasks.service;

import com.dynamicguy.tasks.model.Role;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.logging.Logger;

@Stateless
public class RoleRegistration {

   @Inject
   private Logger log;

   @Inject
   private EntityManager em;

   @Inject
   private Event<Role> roleEventSrc;

   public void register(Role role) throws Exception {
      log.info("Registering " + role.getName());
      em.persist(role);
      roleEventSrc.fire(role);
   }
}
