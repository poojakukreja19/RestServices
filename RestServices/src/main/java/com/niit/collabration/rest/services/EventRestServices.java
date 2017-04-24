package com.niit.collabration.rest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.niit.collabration.DAO.EventDAO;
import com.niit.collabration.Model.Event;


@RestController
@RequestMapping("/event")
public class EventRestServices {

			
			@Autowired private Event event;
			
			@Autowired  private EventDAO eventDAO;
				
			
			//getAllEvent - @GetMapping    //ResPonsEntity
			
			//http://localhost:8080/RestServices/event/allevent
			@GetMapping("/allevent")
			public ResponseEntity< List<Event>> getAllEvent()
			{
				List<Event> eventList =  eventDAO.list();
				
				return   new ResponseEntity<List<Event>>(eventList, HttpStatus.OK);
			}
			
			//http://localhost:8080/RestServices/event/
			@GetMapping("/{id}")
			public ResponseEntity<Event> getEventByID(@PathVariable("id") int id)
			{
				System.out.println("**************Starting of the method getEventByID");
				System.out.println("***************Trying to get Event of the id " + id);
				event = eventDAO.geteventById(id);
				
				if(event==null)
				{
					event = new Event();
					event.setErrorCode("404");
					event.setErrorMessage("event does not exist with the id :" + id);
				}
				else
				{
					event.setErrorCode("200");
					event.setErrorMessage("success");
				}
				
				System.out.println("**************** Name of the event is " + event.getEvent_tittle());
				System.out.println("**************Ending of the method getEventByID");
			  return	new ResponseEntity<Event>(event , HttpStatus.OK);
			}
	
}
