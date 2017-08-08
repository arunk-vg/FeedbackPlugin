package net.valueglobal.jira.plugins.feedback.webwork;

import javax.servlet.http.HttpServletRequest;

import com.atlassian.jira.project.Project;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.security.PermissionManager;
import com.atlassian.jira.security.Permissions;
import com.atlassian.jira.web.ExecutingHttpRequest;
import com.atlassian.jira.web.action.JiraWebActionSupport;



@SuppressWarnings("deprecation")
public class ProjectFeedbackQuestion extends JiraWebActionSupport {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final PermissionManager permissionManager;
    private final JiraAuthenticationContext authenticationContext;
    private String projectKey;

    public String getProjectKey()
    {
        return projectKey;
    }

    public void setProjectKey(String projectKey)
    {
        this.projectKey = projectKey;
    }


    
    public ProjectFeedbackQuestion(final PermissionManager permissionManager, final JiraAuthenticationContext authenticationContext)
    {
        this.permissionManager = permissionManager;
        this.authenticationContext = authenticationContext;
        
    }

    @Override
    protected String doExecute() throws Exception
    {
    	

    	if(!permissionManager.hasPermission(Permissions.PROJECT_ADMIN, getProjectManager().getProjectByKey(projectKey) , authenticationContext.getLoggedInUser()) )
    	
//    	if(!permissionManager.hasPermission(Permissions.PROJECT_ADMIN, authenticationContext.getLoggedInUser()))
        {
            return "securitybreach";
        }
        else
        {
        	Project project = getProjectManager().getProjectObjByKey(projectKey);
    		HttpServletRequest request = ExecutingHttpRequest.get();
    		request.setAttribute((new StringBuilder()).append("com.atlassian.jira.projectconfig.util.ServletRequestProjectConfigRequestCache").append(":project").toString(), project);
    		
            return super.doExecute();
        }
    }

	
}
