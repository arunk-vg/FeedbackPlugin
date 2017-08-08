package net.valueglobal.jira.plugins.feedback.webwork;


import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.security.PermissionManager;
import com.atlassian.jira.security.Permissions;
import com.atlassian.jira.web.action.JiraWebActionSupport;
import com.atlassian.sal.api.websudo.WebSudoRequired;

@SuppressWarnings("deprecation")
@WebSudoRequired
public class TemplateFeedbackQuestion extends JiraWebActionSupport {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final PermissionManager permissionManager;
    private final JiraAuthenticationContext authenticationContext;

    public TemplateFeedbackQuestion(final PermissionManager permissionManager, final JiraAuthenticationContext authenticationContext)
    {
        this.permissionManager = permissionManager;
        this.authenticationContext = authenticationContext;
    }

    @Override
    protected String doExecute() throws Exception
    {
    	
    	if(!permissionManager.hasPermission(Permissions.SYSTEM_ADMIN, authenticationContext.getLoggedInUser()))
        {
            return "securitybreach";
        }
        else
        {
            return super.doExecute();
        }
    }

	
}
