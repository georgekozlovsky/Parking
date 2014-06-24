package de.webalpha.controller;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import de.webalpha.model.Booking;
import de.webalpha.model.Member;

// The @Stateful annotation eliminates the need for manual transaction demarcation
@Stateful
// The @Model stereotype is a convenience mechanism to make this a request-scoped bean that has an
// EL name
// Read more about the @Model stereotype in this FAQ:
// http://sfwk.org/Documentation/WhatIsThePurposeOfTheModelAnnotation
@Model
public class BookingController {

   @Inject
   private Logger log;

   @Inject
   private EntityManager em;

   @Inject
   private Event<Booking> memberEventSrc;

   private Booking newBooking;

   @Produces
   @Named
   public Booking getNewBooking() {
      return newBooking;
   }

   public String register() throws Exception {
      log.info("Registering " + newBooking.getFirstName());
      em.persist(newBooking);
      memberEventSrc.fire(newBooking);
      initNewMember();
      
      return "";
   }

   @PostConstruct
   public void initNewMember() {
      newBooking = new Booking();
   }
}
