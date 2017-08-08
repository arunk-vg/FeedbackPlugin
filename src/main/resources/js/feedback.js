var formRating1 = new Array();
var questions = new Array();
var overallRate;
var owner;
var comment;
var flag;
var issueKey;
var summary;

function myFunction() 
{

	issueKey = JIRA.Issue.getIssueKey();// For Getting Issue Key  
    var count = document.getElementsByName('rating').length;
    var hiddenWeightage = document.getElementsByName('weightage').length; 
    //target = document.getElementById('customfield_11413'); //For feedback overall Rating
    //owner = document.getElementById('customfield_10200-val').innerText;
   // var test = owner.value;
  //  console.log(owner);
  owner  = jQuery('#customfield_10200-val').text();
    if(hiddenWeightage === count)
   	{
   		var total = 0;
   		var totalRound;
		for(x=0;x<count;x++)
		{
			var formRating = document.getElementsByName('rating')[x].value;
			var formWeightage = document.getElementsByName('weightage')[x].value;	
			var quest = document.getElementsByName('questions')[x].value;			
			var overallRating = (formRating * formWeightage)/100;				
			formRating1[x] = formRating;
			questions[x] = quest;			
			total=total+overallRating;
			totalRound = total.toFixed(2);
			//target.value=totalRound;					
			}		
/*		var ownerName = document.getElementById('ownerName').value;
		console.log(ownerName);*/	
		//console.log(questions);
		//console.log(formRating1);
		//target.value=total;
		overallRate = totalRound;
		 AJS.$('#overrate').html(overallRate);

		//console.log(overallRate);
		//target.style.display='block';	
   	}
    return totalRound;
};

function Med(owner,project,isskey,summary,quest,rating,overallRate,comment,createddate){

   var rat = {assignee:owner,project:project,issuekey:isskey,summary:summary,question:quest,rating:rating,overallrating:overallRate,comment:comment,createdate:createddate};
//console.log(rat);
   jQuery.ajax({
	    type: 'POST',
	    url: AJS.contextPath() + '/rest/feedbackreportresource/latest/feedback-report/saveReport',
	    data:JSON.stringify(rat), 
	    dataType: "json",
	    contentType: "application/json",
	    success: function (data) {
	      //  console.log("party hard"); 
	    },
	    error: function (data) {
	     //   console.log("restful Error"); 
	    }
	});
	flag = true;
};

AJS.$(document).ready(function() {
    AJS.$(document).bind('dialogContentReady', function(event, dialog) {

        dialog.$popup.find("#issue-workflow-transition-submit").click(function () {
        	comment = jQuery('#feedbackcomment').val();
			//console.log(comment);
			var e = document.getElementById("resolution");
			if(e != null)
		        {
					var resolution = e.options[e.selectedIndex].text;
					//console.log(resolution);
		        }
          if(AJS.$('#issue-workflow-transition-submit').val() == "Close" && comment != "" && overallRate != "" && resolution != "Please select...")
        	  {
        	 // console.log(AJS.$('#issue-workflow-transition-submit').val() == "Close"); 
        	  var arrayLength = formRating1.length;
        	  
			  if(arrayLength > 0){
				  
				  var createddate = createDate();				  
	        	  var projectName=AJS.$.trim(AJS.$("#project-name-val").text());
	        	//  console.log(createddate);

        		for (var i = 0; i < arrayLength; i++) {
        			if(formRating1[i] != ""){
        	/*		var assigneeName = document.getElementById('assigneeName').value;
        			console.log(assigneeName);*/
        			Med(owner.trim(),projectName.trim(),issueKey.trim(),summary.trim(),questions[i].trim(),formRating1[i].trim(),' ',' ',createddate.trim());
        			//console.log(arrayLength);
        			      			/*formobj.disabled=true;
        			formobj.value='Ratings saved...';*/
        			/*console.log(i,arrayLength,i.count(),i.length);
        			if(i === arrayLength)
        			{console.log("Workings");
        			}*/
        		}        		
        		}
        		Med(owner.trim(),projectName.trim(),issueKey.trim(),summary.trim(),' ',' ',overallRate.trim(),comment.trim(),createddate.trim()); 

			  }   
        		/*if(flag == true){
        			console.log("after saving");
        		alert("Feedback Questions Saved Successfully");
        		}*/
        		}          
        });
    });
});


function createDate(){
	var createddate;  
	
  	 jQuery.ajax(
  			 {
  				 url: contextPath + "/rest/api/2/issue/"+ issueKey,
  				 async: false,
  				 success: function(output) 
  				 {
  					 var dateCreated = output.fields.created;
  					 summary = output.fields.summary;
  					 //console.log(summary);
  					 //console.log(dateCreated);
  			/*		date = new Date(dateCreated);
  					console.log(date);*/
  				createddate = dateCreated;//(date.getFullYear()+'-' + ("0" + (date.getMonth() + 1)).slice(-2) + '-'+("0" + date.getDate()).slice(-2));//prints expected format.

  				//	createddate = getFormattedDate(dateCreated);
  					 
  					// console.log(createddate);
  				 },
  				 error: function (xhr, ajaxOptions, thrownError) {
  	       alert(xhr.status + " "+ thrownError);
  	     }});
	//	 console.log("console.log",createddate);   	
	return createddate;
};



function clearSearch(){
	location.reload();
};

