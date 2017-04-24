package com.niit.collabration.rest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.niit.collabration.DAO.ForumDAO;
import com.niit.collabration.Model.Forum;

@RestController
@RequestMapping("/forum")
public class ForumRestServices {
			
			@Autowired private Forum forum;
			
			@Autowired  private ForumDAO forumDAO;
				
			
			//getAllUsers - @GetMapping    //ResPonsEntity
			
			//http://localhost:8080/RestServices/forum/allforum
			@GetMapping("/allforum")
			public ResponseEntity< List<Forum>> getAllForum()
			{
				List<Forum> forumList =  forumDAO.list();
				
				return   new ResponseEntity<List<Forum>>(forumList, HttpStatus.OK);
			}
			
			//http://localhost:8080/RestServices/Forum/
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
	
}
