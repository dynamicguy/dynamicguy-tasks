package com.dynamicguy.tasks.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.richfaces.cdi.push.Push;

import com.dynamicguy.tasks.model.Role;
import com.dynamicguy.tasks.service.RoleRegistration;

@Model
public class RoleController {

   public static final String PUSH_CDI_TOPIC = "pushCdi";

   @Inject
   private FacesContext facesContext;

   @Inject
   private RoleRegistration roleRegistration;

   @Inject
   @Push(topic = PUSH_CDI_TOPIC) Event<String> pushEvent;

   private Role newRole;
   private Role role;

   @Produces
   @Named
   public Role getNewRole() {
      return newRole;
   }

   @Produces
   @Named
   public Role getRole() {
      return role;
   }

   public void setRole(Role role) {
      this.role = role;
   }

   public void register() throws Exception {
      roleRegistration.register(newRole);
      facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful"));
      pushEvent.fire(String.format("New role added: %s (id: %d)", newRole.getName(), newRole.getId()));
      initNewRole();
   }

   @PostConstruct
   public void initNewRole() {
      newRole = new Role();
   }
}
