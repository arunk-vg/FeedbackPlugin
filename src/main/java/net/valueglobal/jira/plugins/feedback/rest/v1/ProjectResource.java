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

import com.atlassian.jira.JiraApplicationContext;
import com.atlassian.jira.bc.project.ProjectService;
import com.atlassian.jira.plugin.webfragment.model.JiraHelper;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.util.JiraContactHelper;
import com.atlassian.jira.util.dbc.Assertions;
import com.atlassian.sal.api.websudo.WebSudoRequired;

import net.valueglobal.jira.plugins.feedback.model.ProjectModel;
//import net.valueglobal.jira.plugins.feedback.model.Template;
import net.valueglobal.jira.plugins.feedback.services.feedbackservice;

import org.apache.log4j.Logger;


@Path ("/project-templates")
@Produces ( { MediaType.APPLICATION_JSON })
//@WebSudoRequired
public class ProjectResource
{
    private static final Logger log = Logger.getLogger(ProjectResource.class);

    private final feedbackservice knowledgeBaseService;

    public ProjectResource(
            final feedbackservice knowledgeBaseService)
    {
        this.knowledgeBaseService = Assertions.notNull("knowledgeBaseService", knowledgeBaseService);

    }


    @GET
    public Response getProject(@QueryParam ("key") String key,@QueryParam("keyFilter") String keyFilter)
    {
       // knowledgeBaseService.projectKey(keyFilter);
    	if (key != null)
        {
    		final List<ProjectModel> project =  knowledgeBaseService.getProjects(keyFilter);
    		
           // final ProjectModel project = knowledgeBaseService.getProjectById(key);
            if (project != null) {
    			return Response.ok(project).build();
    		} else {
    			return Response.status(Response.Status.NOT_FOUND).entity("No ProjectModel Found: " + key).build();
    		}

        }
        else 
        {
    		final List<ProjectModel> projects =  knowledgeBaseService.getProjects(keyFilter);

        	
    		//final List<ProjectModel> projects =  knowledgeBaseService.getAllProjects(keyFilter);

    		//final List<ProjectModel> projects =  knowledgeBaseService.getProjects(keyFilter);
    		
    		if (projects != null) {
    			return Response.ok(projects).build();
    		} else {
    			return Response.status(Response.Status.NOT_FOUND).entity("No ProjectModel Found: " + key).build();
    		}
        }	
    }

    @PUT
    @Path("{id}")
    public Response setProjectViaRestfulTable(@PathParam ("id") final String key, final ProjectModel project)
    {
        
    	final ProjectModel saved = knowledgeBaseService.saveProject(key,project);
        log.warn(project);
        log.warn(saved);
        if (saved != null) {
			return Response.ok(saved).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).entity("Error in updating the Template: " ).build();
		}
    }

    @POST
    public Response createTemplate(final ProjectModel project, @QueryParam("keyFilter") String keyFilter){
    	//project.setProjectKey(keyFilter); 
    	final ProjectModel saved = knowledgeBaseService.saveProject1(project,keyFilter);
        if (project.isMessageList() != true) {
				return Response.ok(saved).build();
			} else {
				//if(saved.getMessageList() == true)
						return Response.status(Response.Status.NOT_FOUND).entity(saved).build();
			}
    }

    /*@POST
    @Path("/enableTemplate")
    public Response enableTemplate(@QueryParam("id") int id, @QueryParam("enabled") boolean enabled){
        final ProjectModel orginal = knowledgeBaseService.getProjectById(id);
       // orginal.setEnabled(!orginal.isEnabled());
        ProjectModel saved = knowledgeBaseService.saveProject(orginal);
        if (saved != null) {
			return Response.ok(saved).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).entity("Error in creating the Template: " ).build();
		}
    }*/
    
    @DELETE
    @Path ("/{id}")
    public void delete(@PathParam ("id") final String id){
    	final ProjectModel saved = knowledgeBaseService.getProjectById(id);
    	knowledgeBaseService.removeProject(saved);
    }


}
