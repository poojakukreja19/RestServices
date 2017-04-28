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

import com.niit.collabration.DAO.JobDAO;
import com.niit.collabration.Model.Event;
import com.niit.collabration.Model.Job;
import com.niit.collabration.Model.Userdetail;


@RestController
@RequestMapping("/job")
public class JobRestServices {

		
			
			@Autowired private Job job;
			
			@Autowired  private JobDAO jobDAO;
				
			
			//getAllJob - @GetMapping    //ResPonsEntity
			
			//http://localhost:8082/RestServices/job/alljob
			@GetMapping("/alljob")
			public ResponseEntity< List<Job>> getAllJob()
			{
				List<Job> jobList =  jobDAO.list();
				
				return   new ResponseEntity<List<Job>>(jobList, HttpStatus.OK);
			}
			
			//http://localhost:8082/RestServices/job/
			@GetMapping("/{id}")
			public ResponseEntity<Job> getJobByID(@PathVariable("id") int id)
			{
				System.out.println("**************Starting of the method getJobByID");
				System.out.println("***************Trying to get job of the id " + id);
				job = jobDAO.getjobById(id);
				
				if(job==null)
				{
					job = new Job();
					job.setErrorCode("404");
					job.setErrorMessage("Job does not exist with the id :" + id);
				}
				else
				{
					job.setErrorCode("200");
					job.setErrorMessage("success");
				}
				
				System.out.println("**************** Name of the job is " + job.getJob_tittle());
				System.out.println("**************Ending of the method getJobByID");
			  return	new ResponseEntity<Job>(job , HttpStatus.OK);
			}
			
			//http://localhost:8082/RestServices/job/updatejob/{id}
			@PostMapping("/updatejob/{id}")
			public ResponseEntity<Job> updatejob(@RequestBody Job job,@PathVariable int id)
				{
					
				        Job Newjob = jobDAO.getjobById(id);
				        Newjob.setJob_id(job.getJob_id());
				        Newjob.setJob_tittle(job.getJob_tittle());
				        Newjob.setJob_description(job.getJob_description());
				        Newjob.setJob_qualification(job.getJob_qualification());
				        Newjob.setJob_status(job.getJob_status());                         
                         
				    jobDAO. update(Newjob);
					
					return new ResponseEntity<Job>(job , HttpStatus.OK);

				}		
	
			
	
			//http://localhost:8082/RestServices/job/createjob
			@PostMapping("/createjob")
			public Job createjob(@RequestBody Job newJob)
			{
				System.out.println("*******************Calling createjob method*************************** ");
			//before creating job, check whether the id exist in the db or not
				
				job = jobDAO.getjobById(newJob.getJob_id());
				if( job ==null)
				{
					System.out.println("***********************Job does not exist...trying to create new Job***************");
					//id does not exist in the db
					jobDAO.save(newJob);
					//NLP - NullPointerException
					//Whenever you call any method/variable on null object - you will get NLP
					newJob.setErrorCode("200");
					newJob.setErrorMessage("Thank you for newjob registration.");
					
				}
				else
				{
					System.out.println("**********************Please choose another id as it is existed*****************");
					//id already exist in db.
					newJob.setErrorCode("800");
					newJob.setErrorMessage("Please choose another  job id as it is exist");
					
				}
		    	return newJob;	
			}	
			
			//http://localhost:8082/RestServices/job/delete/{id}
			@DeleteMapping("delete/{id}")
			public Job deleteJob(@PathVariable("id") int id)
			{
				
				//whether record exist with this id or not
				
				
			    if(	jobDAO.getjobById(id)==null)
			    {
			    	job.setErrorCode("404");
			    	job.setErrorMessage("Could not delete.Event does not exist with this id " + id);
			    }
			    else
			    {
			    	  if (jobDAO.deletejobById(id))
			    	  {
			    		  job.setErrorCode("200");
			    		  job.setErrorMessage("Successfully deleted");
			    	  }
			    	  else
			    	  {
			    		  job.setErrorCode("404");
			    		  job.setErrorMessage("Could not delete. Please contact administrator");
			    	  }  	
			    }
			    
			    return job;
				
}
			}
			
	

	

