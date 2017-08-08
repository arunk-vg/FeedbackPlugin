package net.valueglobal.jira.plugins.FeedbackReport.rest;

//import com.atlassian.plugins.rest.common.security.AnonymousAllowed;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import net.valueglobal.jira.plugins.FeedbackReport.service.FeedbackReportService;
import net.valueglobal.jira.plugins.FeedbackReport.rest.FeedbackReportResourceModel;

import org.apache.log4j.Logger;

import com.atlassian.sal.api.websudo.WebSudoNotRequired;
import com.atlassian.sal.api.websudo.WebSudoRequired;



/**
 * A resource of message.
 */
@Path("/feedback-report")
@WebSudoNotRequired
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class FeedbackReportResource {

	   private static final Logger log = Logger.getLogger(FeedbackReportResource.class);

	   private final FeedbackReportService feedbackReportService;
	   
	  public FeedbackReportResource(final FeedbackReportService feedbackReportService ){
		  this.feedbackReportService = feedbackReportService; 
	   }
	  
	  @GET
      @Path("/projectKey")
	    public Response getReportByProjectKey(@QueryParam ("keyFilter") String keyFilter)
	    {         
	        final List<FeedbackReportResourceModel> report = feedbackReportService.getReportByProjectKey(keyFilter);
	            if (report != null) 
	            {
	    			return Response.ok(report).build();
	    		} 
	            else 
	            {
	    			return Response.status(Response.Status.NOT_FOUND).entity("No Template Found: for Search").build();
	    		}
	    } 
	  
	   @GET
	    public Response getReport()
	    {
		   final List<FeedbackReportResourceModel> report = feedbackReportService.getAllReports();
		   if(report != null)
		   {
			return Response.ok(report).build();  
		   }
		   else
		   {
			return Response.status(Response.Status.NOT_FOUND).entity("No Templates Found").build();   
		   }
		   
	    }   
		@GET
	    @Path("/searchdate")
	    public Response getTemplate(@QueryParam ("searchVal") String searchVal, @QueryParam ("fromDate") Timestamp from,@QueryParam ("toDate") Timestamp to,@QueryParam ("keyFilter") String keyFilter)
	    {         
	        final List<FeedbackReportResourceModel> report = feedbackReportService.getReportByString(searchVal,from,to,keyFilter);
	            if (report != null) 
	            {
	    			return Response.ok(report).build();
	    		} 
	            else 
	            {
	    			return Response.status(Response.Status.NOT_FOUND).entity("No Template Found: for Search").build();
	    		}
	    }
		
		@GET
	    @Path("/search")
	    public Response getReport(@QueryParam ("searchVal") String searchVal,@QueryParam ("keyFilter") String keyFilter)
	    {         
	        final List<FeedbackReportResourceModel> report = feedbackReportService.getReportByString(searchVal,keyFilter);
	            if (report != null) 
	            {
	    			return Response.ok(report).build();
	    		} 
	            else 
	            {
	    			return Response.status(Response.Status.NOT_FOUND).entity("No Template Found: for Search").build();
	    		}
	    }
	   @POST
	   @Path("/saveReport")
	    public Response setReport(FeedbackReportResourceModel rat)
	    {
		//   final FeedbackReportResourceModel rep = new FeedbackReportResourceModel();
		  /* rep.setIssueKey("issuekey1");
		   rep.setQuestion("asdfsd");
		   rep.setRating("10");*/
			//rat.setReopened("TestingReopen");

		   final FeedbackReportResourceModel report = feedbackReportService.setAllReports(rat);
		   if(report != null)
		   {
			return Response.ok(report).build();  
		   }
		   else
		   {
			return Response.status(Response.Status.NOT_FOUND).entity("No Templates Found").build();   
		   }
		   
	    }
	   
	   @DELETE
	    @Path ("/{id}")
	    public void deleteProject(@PathParam ("id") final String id){
	    	final FeedbackReportResourceModel saved = feedbackReportService.getReportById(id);
	    	feedbackReportService.removeTemplate(saved);
	    }	   
	 
	   
	   
	   

}