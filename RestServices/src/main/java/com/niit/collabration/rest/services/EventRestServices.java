package com.niit.collabration.rest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.niit.collabration.DAO.EventDAO;
import com.niit.collabration.Model.Event;
import com.niit.collabration.Model.Userdetail;


@RestController
@RequestMapping("/event")
public class EventRestServices {

			
			@Autowired private Event event;
			
			@Autowired  private EventDAO eventDAO;
				
			
			//getAllEvent - @GetMapping    //ResPonsEntity
			
			//http://localhost:8082/RestServices/event/allevent
			@GetMapping("/allevent")
			public ResponseEntity< List<Event>> getAllEvent()
			{
				List<Event> eventList =  eventDAO.list();
				
				return   new ResponseEntity<List<Event>>(eventList, HttpStatus.OK);
			}
			
			//http://localhost:8082/RestServices/event/
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
			
			//http://localhost:8082/RestServices/event/updateevent/{id}
			@PostMapping("/updateevent/{id}")
			public ResponseEntity<Event> updateEvent(@RequestBody Event event,@PathVariable int id)
				{
					
                         Event Newevent = eventDAO.geteventById(id);
                         Newevent.setEvent_id(event.getEvent_id());
                         Newevent.setEvent_tittle(event.getEvent_tittle());
                         Newevent.setEvent_description(event.getEvent_description());
                         Newevent.setEvent_status(event.getEvent_status());
                         Newevent.setImage(event.getImage());
                          
                         
				eventDAO. update(Newevent);
					
					return new ResponseEntity<Event>(event , HttpStatus.OK);

				}	
			//http://localhost:8082/RestServices/event/createevent
			@PostMapping("/createevent")
			public Event createEvent(@RequestBody Event newEvent)
			{
				System.out.println("*******************Calling createEvent method*************************** ");
			//before creating event, check whether the id exist in the db or not
				
				event = eventDAO.geteventById(newEvent.getEvent_id());
				if( event ==null)
				{
					System.out.println("***********************Event does not exist...trying to create new Event***************");
					//id does not exist in the db
					eventDAO.save(newEvent);
					//NLP - NullPointerException
					//Whenever you call any method/variable on null object - you will get NLP
					newEvent.setErrorCode("200");
					newEvent.setErrorMessage("Thank you for Event registration.");
					
				}
				else
				{
					System.out.println("**********************Please choose another event as it is  not existed*****************");
					//id already exist in db.
					newEvent.setErrorCode("800");
					newEvent.setErrorMessage("Please choose another event as it is not exist");
					
				}
		    	return newEvent;	
			}			
			
			//http://localhost:8082/RestServices/event/delete/{id}
			@DeleteMapping("delete/{id}")
			public Event deleteEvent(@PathVariable("id") int id)
			{
				
				//whether record exist with this id or not
				
				
			    if(	eventDAO.geteventById(id)==null)
			    {
			    	event.setErrorCode("404");
			    	event.setErrorMessage("Could not delete.Event does not exist with this id " + id);
			    }
			    else
			    {
			    	  if (eventDAO.deleteeventById(id))
			    	  {
			    		  event.setErrorCode("200");
			    		  event.setErrorMessage("Successfully deleted");
			    	  }
			    	  else
			    	  {
			    		  event.setErrorCode("404");
			    		  event.setErrorMessage("Could not delete. Please contact administrator");
			    	  }  	
			    }
			    
			    return event;
				
}

	
}
