package net.valueglobal.jira.plugins.FeedbackReport.webwork;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.project.Project;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.security.PermissionManager;
import com.atlassian.jira.security.Permissions;
import com.atlassian.jira.web.ExecutingHttpRequest;
import com.atlassian.jira.web.action.JiraWebActionSupport;
import com.atlassian.sal.api.websudo.WebSudoRequired;

@WebSudoRequired
public class FeedbackProjectReportAction extends JiraWebActionSupport
{
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(FeedbackProjectReportAction.class);
   
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


    
    public FeedbackProjectReportAction(final PermissionManager permissionManager, final JiraAuthenticationContext authenticationContext)
    {
        this.permissionManager = permissionManager;
        this.authenticationContext = authenticationContext;
        
    }

/*    @Override
    protected String doExecute() throws Exception
    {
    	

    	if(!permissionManager.hasPermission(Permissions.PROJECT_ADMIN, getProjectManager().getProjectByKey(projectKey) , authenticationContext.getLoggedInUser()) )
    	
//    	if(!permissionManager.hasPermission(Permissions.PROJECT_ADMIN, authenticationContext.getLoggedInUser()))
        {
            return "securitybreach";
        }
        else
        {
            return super.doExecute();
        }
    }*/
	
	protected String doexecute() throws Exception {
		
		Project project = getProjectManager().getProjectObjByKey(projectKey);

		HttpServletRequest request = ExecutingHttpRequest.get();

		request.setAttribute((new StringBuilder()).append("com.atlassian.jira.projectconfig.util.ServletRequestProjectConfigRequestCache").append(":project").toString(), project);
    return "success"; //returns SUCCESS
    }
    /*
    public String doCreate() {
    	FeedbackReport feedbackrep = ao.create(FeedbackReport.class); 
    	feedbackrep.setQuestion("Test");
    	feedbackrep.setRating("10");
    	feedbackrep.save();
    	setInfo(0);
    	return SUCCESS;
    }
    
    public String doSelect() {
        FeedbackReport[] feedbackget = ao.find(FeedbackReport.class, Query.select().where("rating = ?", getSelectid()));
        if (feedbackget.length == 0) {
            setInfo(0);
            return ERROR;
        }
        FeedbackReport feedback = feedbackget[0];
        setInfo(feedback.getID());
        return SUCCESS;
    }

    private int info;
    public int getInfo() {
        return info;
    }
    public String getInfoStr() {
        return "Latest stored ID value is " + Integer.toString(info);
    }
    public void setInfo(int value) {
        info = value;
    }

    private String selectid;
    public void setSelectid(String value) {
        selectid = value;
    }

    public String getSelectid() {
        return selectid;
    }*/
}
