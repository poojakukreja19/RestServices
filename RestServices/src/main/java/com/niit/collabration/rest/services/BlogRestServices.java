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

import com.niit.collabration.DAO.BlogDAO;
import com.niit.collabration.Model.Blog;
import com.niit.collabration.Model.Userdetail;



@RestController
@RequestMapping("/blog")
public class BlogRestServices {

		@Autowired private Blog blog;
		
		@Autowired private BlogDAO blogDAO;
		
				
			
			//getAllBlog - @GetMapping    //ResPonsEntity
			
			//http://localhost:8082/RestServices/blog/allblog
			@GetMapping("/allblog")
			public ResponseEntity< List<Blog>> getAllBlog()
			{
				List<Blog> blogList =  blogDAO.list();
				
				return   new ResponseEntity<List<Blog>>(blogList, HttpStatus.OK);
			}
			
			//http://localhost:8082/RestServices/blog/
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
				
				System.out.println("********************Id of the blog is" +blog.getBlog_id());
				System.out.println("**************Ending of the method getblogByID");
			    return	new ResponseEntity<Blog>(blog , HttpStatus.OK);
			}
	
			//http://localhost:8082/RestServices/blog/updateblog/{id}
			@PostMapping("/updateblog/{id}")
			public ResponseEntity<Blog> updateBlog(@RequestBody Blog blog,@PathVariable int id)
				{
					
                         Blog Newblog = blogDAO.getblogById(id);
                         Newblog.setBlog_id(blog.getBlog_id());
                         Newblog.setBlog_tittle(blog.getBlog_tittle());
                         Newblog.setBog_description(blog.getBog_description());
                         Newblog.setBlog_reason(blog.getBlog_reason());
                         Newblog.setBlog_status(blog.getBlog_status());
                         
				blogDAO. update(Newblog);
					
					return new ResponseEntity<Blog>(blog , HttpStatus.OK);

				}
			//http://localhost:8082/RestServices/blog/createblog
			@PostMapping("/createblog")
			public Blog createBlog(@RequestBody Blog newBlog)
			{
				System.out.println("*******************Calling createblog method*************************** ");
			//before creating blog, check whether the id exist in the db or not
				
				blog = blogDAO.getblogById(newBlog.getBlog_id());
				if( blog ==null)
				{
					System.out.println("***********************Blog does not exist...trying to create new Blog***************");
					//id does not exist in the db
					blogDAO.save(newBlog);
					//NLP - NullPointerException
					//Whenever you call any method/variable on null object - you will get NLP
					newBlog.setErrorCode("200");
					newBlog.setErrorMessage("Thank you fo registration.");
					
				}
				else
				{
					System.out.println("**********************Please choose another blog_id as it is existed*****************");
					//id already exist in db.
					newBlog.setErrorCode("800");
					newBlog.setErrorMessage("Please choose another id as it is exist");
					
				}
		    	return newBlog;	
			}			
			
			//http://localhost:8082/RestServices/blog/delete/{id}
			@DeleteMapping("delete/{id}")
			public Blog deleteBlog(@PathVariable("id") int id)
			{
				
				//whether record exist with this id or not
				
				
			    if(	blogDAO.getblogById(id)==null)
			    {
			    	blog.setErrorCode("404");
			    	blog.setErrorMessage("Could not delete.  blog does not exist with this id " + id);
			    }
			    else
			    {
			    	  if (blogDAO.deleteblogById(id))
			    	  {
			    		  blog.setErrorCode("200");
			    		  blog.setErrorMessage("Successfully deleted");
			    	  }
			    	  else
			    	  {
			    		  blog.setErrorCode("404");
			    		  blog.setErrorMessage("Could not delete. Please contact administrator");
			    	  }  	
			    }
			    
			    return blog;
				
}
}

