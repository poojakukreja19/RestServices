package com.niit.collabration.rest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.niit.collabration.DAO.BlogDAO;
import com.niit.collabration.Model.Blog;



@RestController
@RequestMapping("/blog")
public class BlogRestServices {

		@Autowired private Blog blog;
		
		@Autowired private BlogDAO blogDAO;
		
				
			
			//getAllBlog - @GetMapping    //ResPonsEntity
			
			//http://localhost:8080/RestServices/blog/allblog
			@GetMapping("/allblog")
			public ResponseEntity< List<Blog>> getAllBlog()
			{
				List<Blog> blogList =  blogDAO.list();
				
				return   new ResponseEntity<List<Blog>>(blogList, HttpStatus.OK);
			}
			
			//http://localhost:8080/RestServices/blog/
			@GetMapping("/{id}")
			public ResponseEntity<Blog> getBlogByID(@PathVariable("id") int id)
			{
				System.out.println("**************Starting of the method getblogByID");
				System.out.println("***************Trying to get blog of the id " + id);
				blog = blogDAO.getblogById(id);

				if(blog==null)
				{
					blog = new Blog();
					blog.setErrorCode("404");
					blog.setErrorMessage("blog does not exist with the id :" + id);
				}
				else
				{
					blog.setErrorCode("200");
					blog.setErrorMessage("success");
				}
				
				System.out.println("********************Name of the blog is" +blog.getBlog_id());
				System.out.println("**************Ending of the method getblogByID");
			    return	new ResponseEntity<Blog>(blog , HttpStatus.OK);
			}
	
}
