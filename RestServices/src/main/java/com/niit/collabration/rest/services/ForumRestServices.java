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

import com.niit.collabration.DAO.ForumDAO;
import com.niit.collabration.Model.Forum;
import com.niit.collabration.Model.Userdetail;

@RestController
@RequestMapping("/forum")
public class ForumRestServices {
			
			@Autowired private Forum forum;
			
			@Autowired  private ForumDAO forumDAO;
				
			
			//getAllUsers - @GetMapping    //ResPonsEntity
			
			//http://localhost:8082/RestServices/forum/allforum
			@GetMapping("/allforum")
			public ResponseEntity< List<Forum>> getAllForum()
			{
				List<Forum> forumList =  forumDAO.list();
				
				return   new ResponseEntity<List<Forum>>(forumList, HttpStatus.OK);
			}
			
			//http://localhost:8082/RestServices/forum/{id}
			@GetMapping("/{id}")
			public ResponseEntity<Forum> getForumByID(@PathVariable("id") int id)
			{
				System.out.println("**************Starting of the method getForumByID");
				System.out.println("***************Trying to get Forum of the id " + id);
				forum = forumDAO.getforumById(id);
				
				if(forum==null)
				{
					forum = new Forum();
					forum.setErrorCode("404");
					forum.setErrorMessage("User does not exist with the id :" + id);
				}
				else
				{
					forum.setErrorCode("200");
					forum.setErrorMessage("success");
				}
				
				System.out.println("**************** Name of teh user is " + forum.getForum_id());
				System.out.println("**************Ending of the method getUserByID");
			  return	new ResponseEntity<Forum>(forum , HttpStatus.OK);
			}
			
			//http://localhost:8082/RestServices/forum/updateforum/{id}
			@PostMapping("/updateforum/{id}")
			public ResponseEntity<Forum> UpdateForum(@RequestBody Forum forum,@PathVariable int id)
				{
					
                         Forum Newforum = forumDAO.getforumById(id);
                         Newforum.setForum_id(forum.getForum_id());
                         Newforum.setFreindID(forum.getFreindID());
                         Newforum.setUserID(forum.getUserID());
                         Newforum.setComment(forum.getComment());
                                               
				forumDAO. update(Newforum);
					
					return new ResponseEntity<Forum>(forum , HttpStatus.OK);

				}		
	
     		//http://localhost:8082/RestServices/forum/createforum
			@PostMapping("/createforum")
			public Forum createForum(@RequestBody Forum newForum)
			{
				System.out.println("*******************Calling createforum method*************************** ");
			//before creating user, check whether the id exist in the db or not
				
				forum = forumDAO.getforumById(newForum.getForum_id());
				if( forum ==null)
				{
					System.out.println("***********************Forum does not exist...trying to create newForum***************");
					//id does not exist in the db
					forumDAO.save(newForum);
					//NLP - NullPointerException
					//Whenever you call any method/variable on null object - you will get NLP
					newForum.setErrorCode("200");
					newForum.setErrorMessage("Thank you newForum registration.");
				}
				else
				{
					System.out.println("**********************Please choose another newForum id as it is existed*****************");
					//id already exist in db.
					newForum.setErrorCode("800");
					newForum.setErrorMessage("Please choose another newForum id as it is exist");
					
				}
		    	return newForum;	
			}			
			
			//http://localhost:8082/RestServices/forum/delete/{id}
			@DeleteMapping("delete/{id}")
			public Forum deleteForum(@PathVariable("id") int id)
			{
				
				//whether record exist with this id or not
				
				
			    if(	forumDAO.getforumById(id)==null)
			    {
			    	forum.setErrorCode("404");
			    	forum.setErrorMessage("Could not delete.Forum does not exist with this id " + id);
			    }
			    else
			    {
			    	  if (forumDAO.deleteforumById(id))
			    	  {
			    		  forum.setErrorCode("200");
			    		  forum.setErrorMessage("Successfully deleted");
			    	  }
			    	  else
			    	  {
			    		  forum.setErrorCode("404");
			    		  forum.setErrorMessage("Could not delete. Please contact administrator");
			    	  }  	
			    }
			    
			    return forum;
				
}
	
}
