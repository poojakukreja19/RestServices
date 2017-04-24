package com.niit.collabration.rest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.niit.collabration.DAO.UserdetailDAO;
import com.niit.collabration.Model.Userdetail;

  
	@RestController
	@RequestMapping("/user")
	public class UserRestServices {

		//getUser  -  @GetMapping
			//createUser -  @PostMapping
			//updateUser  - @PutMapping
			//getAllUsers - @GetMapping
			//validateCredentials  -  @PostMapping
			
			@Autowired private Userdetail userdetail;
			
			@Autowired  private UserdetailDAO userdetailDAO;
				
			
			//getAllUsers - @GetMapping    //ResPonsEntity
			
			//http://localhost:8080/RestServices/user/alluser
			@GetMapping("/alluser")
			public ResponseEntity< List<Userdetail>> getAllUser()
			{
				List<Userdetail> userdetailList =  userdetailDAO.list();
				
				return   new ResponseEntity<List<Userdetail>>(userdetailList, HttpStatus.OK);
			}
			
			//http://localhost:8080/RestServices/user/niit
			@GetMapping("/{id}")
			public ResponseEntity<Userdetail> getUserByID(@PathVariable("id") int id)
			{
				System.out.println("**************Starting of the method getUserByID");
				System.out.println("***************Trying to get userdetails of the id " + id);
				userdetail = userdetailDAO.getuserById(id);
				
				if(userdetail==null)
				{
					userdetail = new Userdetail();
					userdetail.setErrorCode("404");
					userdetail.setErrorMessage("User does not exist with the id :" + id);
				}
				else
				{
					userdetail.setErrorCode("200");
					userdetail.setErrorMessage("success");
				}
				
				System.out.println("**************** Name of teh user is " + userdetail.getName());
				System.out.println("**************Ending of the method getUserByID");
			  return	new ResponseEntity<Userdetail>(userdetail , HttpStatus.OK);
			}
			
//			//http://localhost:8080/RestServices/user/updateuser/{id}
//			@PostMapping("/updateuser/{id}")
//			public Userdetail updateUserDetails(@RequestBody Userdetail updateUserdetail)
//				{
//					
//					//check whether the id exist or not
//					
//					userdetail=  userdetailDAO.getuserById(updateUserdetail.getId());
//					userdetail.setId(47);
//					userdetail.setName("simran");
//					userdetail.setPassword("simran");
//					userdetail.setIs_online('N');
//					userdetail.setRole("CUSTOMER");
//					
//					if(userdetail!=null)
//					{
//						userdetailDAO.update(updateUserdetail);
//						updateUserdetail.setErrorCode("200");
//						updateUserdetail.setErrorMessage("Successfully updated the details");
//					}
//					else
//					{
//						updateUserdetail.setErrorCode("800");
//						updateUserdetail.setErrorMessage("Could not updated. User does not exist with thid id " + updateUserdetail.getId());
//					}
//					
//					return updateUserdetail;
//					
//				}
			
			//http://localhost:8080/RestServices/user/createuser
			@PostMapping("/createuser/")
			public Userdetail createUser(@RequestBody Userdetail newUser)
			{
				System.out.println("*******************Calling createUser method*************************** ");
				//before creating user, check whether the id exist in the db or not
				
				userdetail = userdetailDAO.getuserById(newUser.getId());
				if( userdetail ==null)
				{
					System.out.println("***********************User does not exist...trying to create new user***************");
					//id does not exist in the db
					userdetailDAO.save(newUser);
					//NLP - NullPointerException
					//Whenever you call any method/variable on null object - you will get NLP
					newUser.setErrorCode("200");
					newUser.setErrorMessage("Thank you fo registration.");
					
				}
				else
				{
					System.out.println("**********************Please choose another id as it is existed*****************");
					//id already exist in db.
					newUser.setErrorCode("800");
					newUser.setErrorMessage("Please choose another id as it is exist");
					
					
				}
				return newUser;
				
				
			}
			}
			
			
	
