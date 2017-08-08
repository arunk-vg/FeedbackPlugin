package net.valueglobal.jira.plugins.feedback.webwork;


/*import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.security.PermissionManager;
import com.atlassian.jira.security.Permissions;*/
import com.atlassian.jira.web.action.JiraWebActionSupport;
import com.atlassian.sal.api.websudo.WebSudoRequired;

@WebSudoRequired
public class Configurationfeedbackquestion extends JiraWebActionSupport {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

/*  private final PermissionManager permissionManager;
    private final JiraAuthenticationContext authenticationContext;

    public Configurationfeedbackquestion(final PermissionManager permissionManager, final JiraAuthenticationContext authenticationContext)
    {
        this.permissionManager = permissionManager;
        this.authenticationContext = authenticationContext;
    }
*/
    @Override
    protected String doExecute() throws Exception
    {
    	return "success";
    	
    	/*if(!permissionManager.hasPermission(Permissions.SYSTEM_ADMIN, authenticationContext.getLoggedInUser()))
        {
            return "securitybreach";
        }
        else
        {
            return super.doExecute();
        }*/
    }

	
}
