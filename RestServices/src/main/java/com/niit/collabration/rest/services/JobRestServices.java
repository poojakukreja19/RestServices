package com.niit.collabration.rest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.niit.collabration.DAO.JobDAO;
import com.niit.collabration.Model.Job;


@RestController
@RequestMapping("/job")
public class JobRestServices {

		
			
			@Autowired private Job job;
			
			@Autowired  private JobDAO jobDAO;
				
			
			//getAllJob - @GetMapping    //ResPonsEntity
			
			//http://localhost:8080/RestServices/job/alljob
			@GetMapping("/alljob")
			public ResponseEntity< List<Job>> getAllJob()
			{
				List<Job> jobList =  jobDAO.list();
				
				return   new ResponseEntity<List<Job>>(jobList, HttpStatus.OK);
			}
			
			//http://localhost:8080/RestServices/job/
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
			
	
}
