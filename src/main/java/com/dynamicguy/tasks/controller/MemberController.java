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

import com.dynamicguy.tasks.model.Member;
import com.dynamicguy.tasks.service.MemberRegistration;

// The @Model stereotype is a convenience mechanism to make this a request-scoped bean that has an
// EL name
// Read more about the @Model stereotype in this FAQ:
// http://sfwk.org/Documentation/WhatIsThePurposeOfTheModelAnnotation
@Model
public class MemberController {

   public static final String PUSH_CDI_TOPIC = "pushCdi";

   @Inject
   private FacesContext facesContext;

   @Inject
   private MemberRegistration memberRegistration;

   @Inject
   @Push(topic = PUSH_CDI_TOPIC) Event<String> pushEvent;

   private Member newMember;
   private Member member;

   @Produces
   @Named
   public Member getNewMember() {
      return newMember;
   }

   @Produces
   @Named
   public Member getMember() {
      return member;
   }

   public void setMember(Member member) {
      this.member = member;
   }

   public void register() throws Exception {
      memberRegistration.register(newMember);
      facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful"));
      pushEvent.fire(String.format("New member added: %s (id: %d)", newMember.getName(), newMember.getId()));
      initNewMember();
   }

   @PostConstruct
   public void initNewMember() {
      newMember = new Member();
   }
}
