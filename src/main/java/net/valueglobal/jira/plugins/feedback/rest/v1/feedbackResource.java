package net.valueglobal.jira.plugins.feedback.rest.v1;

import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
//import com.atlassian.jira.bc.admin.ApplicationPropertiesService;
//import com.atlassian.jira.bc.admin.ApplicationProperty;
//import com.atlassian.jira.bc.admin.ApplicationPropertyMetadata;
//import com.atlassian.jira.rest.api.util.ErrorCollection;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.security.PermissionManager;
import com.atlassian.jira.util.dbc.Assertions;
import com.atlassian.sal.api.websudo.WebSudoNotRequired;
import net.valueglobal.jira.plugins.feedback.model.ProjectModel;

import net.valueglobal.jira.plugins.feedback.services.feedbackservice;
import org.apache.log4j.Logger;


@Path ("/admin-templates")
@Produces ( { MediaType.APPLICATION_JSON })
@WebSudoNotRequired
public class feedbackResource
{
   private static final Logger log = Logger.getLogger(feedbackResource.class);

    private final feedbackservice feedbackservice;

    public feedbackResource(
            final feedbackservice feedbackservice)
    {
        this.feedbackservice = Assertions.notNull("feedbackservice", feedbackservice);
    }


    @GET
    public Response getTemplate(@QueryParam ("key") String key,@QueryParam("keyFilter") String keyFilter)
    {
        if (key != null)
        {
            final ProjectModel template = feedbackservice.getTemplateById(key);
            if (template != null) {
    			return Response.ok(template).build();
    		} else {
    			return Response.status(Response.Status.NOT_FOUND).entity("No Template Found: " + key).build();
    		}

        }
                else 
        {
    		final List<ProjectModel> templates =  feedbackservice.getAllTemplates();
    		
    		if (templates != null) {
    			return Response.ok(templates).build();
    		} else {
    			return Response.status(Response.Status.NOT_FOUND).entity("No Templates Found: " + key).build();
    		}
        }	
    }

   /* @PUT
    @Path("/{id}")
    public Response setTemplateViaRestfulTable(@PathParam ("id") final String key,@PathParam ("projectKey") final String projectKey, final Template template)
    {
        final Template saved = feedbackservice.saveTemplate(key,projectKey,template);
        if (saved != null) {
			return Response.ok(saved).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).entity("Error in updating the Template: " ).build();
		}
    }*/
    
    
    @PUT
    @Path("/{id}")
    public Response setTemplateViaRestfulTable(@PathParam ("id") final String key, final ProjectModel template)
    {
        final ProjectModel saved = feedbackservice.saveTemplate(key,template);
        if (saved != null) {
			return Response.ok(saved).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).entity("Error in updating the Template: " ).build();
		}
    }

    @POST
    public Response createTemplate(final ProjectModel template){
        final ProjectModel saved = feedbackservice.saveTemplate(template);
        if (template.isMessageList() != true) {
   				return Response.ok(saved).build();
   			} else {
   				//if(saved.getMessageList() == true)
   						return Response.status(Response.Status.NOT_FOUND).entity(saved).build();
   			}
    }
 
    @DELETE
    @Path ("/{id}")
    public void deleteProject(@PathParam ("id") final String id){
    	final ProjectModel saved = feedbackservice.getTemplateById(id);
    	feedbackservice.removeTemplate(saved);
    }

  
    
    
    

    
    
/*    

    @GET
    public Response getProjectTemplateById(@QueryParam ("Projectkey") String key,@QueryParam("ProjectkeyFilter") String keyFilter)
    {
        if (key != null)
        {
            final ProjectTemplate template = feedbackservice.getProjectTemplateById(key);
            if (template != null) {
    			return Response.ok(template).build();
    		} else {
    			return Response.status(Response.Status.NOT_FOUND).entity("No Template Found: " + key).build();
    		}

        }
                else 
        {
    		final List<ProjectTemplate> templates =  feedbackservice.getAllTemplatesByProjectKey();
    		
    		if (templates != null) {
//    			return Response.ok(new TemplateList(templates)).build();
    			return Response.ok(templates).build();
    		} else {
    			return Response.status(Response.Status.NOT_FOUND).entity("No Templates Found: " + key).build();
    		}
        }	
    }

    @PUT
    @Path("/{id}")
    public Response setTemplateViaRestfulTable(@PathParam ("id") final String key,@PathParam ("projectKey") final String projectKey, final Template template)
    {
        final Template saved = feedbackservice.saveTemplate(key,projectKey,template);
        if (saved != null) {
			return Response.ok(saved).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).entity("Error in updating the Template: " ).build();
		}
    }
    
    
    @PUT
    @Path("/{Projectid}")
    public Response setProjectTemplateViaRestfulTable(@PathParam ("Projectid") final String key, final ProjectTemplate template)
    {
        final ProjectTemplate saved = feedbackservice.saveProjectTemplate(key,template);
        if (saved != null) {
			return Response.ok(saved).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).entity("Error in updating the Template: " ).build();
		}
    }

    @POST
    public Response createProjectTemplate(final ProjectTemplate template){
        final ProjectTemplate saved = feedbackservice.saveProjectTemplate(template);
        if (saved != null) {
			return Response.ok(saved).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).entity("Error in creating the Template: " ).build();
		}
    }
 
    @DELETE
    @Path ("/{Projectid}")
    public void delete(@PathParam ("Projectid") final String id){
    	final ProjectTemplate saved = feedbackservice.getProjectTemplateById(id);
    	feedbackservice.removeProjectTemplate(saved);
    }
*/
    
}
