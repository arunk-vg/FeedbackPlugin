package net.valueglobal.jira.plugins.feedback.feedbackquesCF;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.issue.customfields.impl.GenericTextCFType;
import com.atlassian.jira.issue.customfields.manager.GenericConfigManager;
import com.atlassian.jira.issue.customfields.persistence.CustomFieldValuePersister;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.fields.layout.field.FieldLayoutItem;
import com.atlassian.jira.util.dbc.Assertions;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.valueglobal.jira.plugins.feedback.services.feedbackservice;
import net.valueglobal.jira.plugins.feedback.model.ProjectModel;

public class FeedbackquesCF extends GenericTextCFType {
	
	private final feedbackservice Service;

	
    protected FeedbackquesCF(CustomFieldValuePersister customFieldValuePersister,
			GenericConfigManager genericConfigManager,final feedbackservice Service ) {
		super(customFieldValuePersister, genericConfigManager);
		
	    
	        this.Service = Assertions.notNull("Service", Service);
	    
		
	}


	private static final Logger log = LoggerFactory.getLogger(FeedbackquesCF.class);

    
    @SuppressWarnings("null")
	@Override
    public Map<String, Object> getVelocityParameters(final Issue issue,
                                                     final CustomField field,
                                                     final FieldLayoutItem fieldLayoutItem) {
        final Map<String, Object> map = super.getVelocityParameters(issue, field, fieldLayoutItem);
     
        
   /*    
        HttpSession session = null;
        session.getAttribute("parent_version");
        
        String l = session.toString();*/
        
      /*  List<ProjectModel> template = Service.getAllProjects();
        if (issue.getProjectObject().getKey().equals("JIRA")){

            map.put("questions", template);

        	}*/
            
         /*  List<ProjectModel> template = null;

            map.put("ProjectModel", feedbackservice.class);
            map.put("List", template);*/

            
          /*  List<ProjectModel> template = Service.getProjects("JIRA");// if (issue.getProjectObject().getKey().equals("JIRA")){

                 map.put("questions", template);*/
        
        List<ProjectModel> project = Service.getAllProjects();

                 
                 map.put("questions", project);
                 map.put("ques", project);

            
            return map;        

    }
}