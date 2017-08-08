AJS.$(window).load(Reload);
	function Reload(){
		var dataProjectKey;
		jQuery(function() {

			var $el = AJS.$('#feedbackreport-table');
			dataProjectKey = $el.attr('data-project-key');			
			//console.log(dataProjectKey);		
			var url;	

		if(dataProjectKey == ""){
			url = AJS.contextPath() + '/rest/feedbackreportresource/latest/feedback-report';
		}
		else{
			url = AJS.contextPath() + '/rest/feedbackreportresource/latest/feedback-report/projectKey?keyFilter=' + dataProjectKey;
		}
			var dat;
		  jQuery.ajax({
	//url: contextPath + '/rest/feedbackreportresource/latest/feedback-report'
			  url: url, type: 'GET',dataType: "json",async:true,
	
	success: function(response) {
	        //	console.log(response);
	        	var trHTML = '  <thead><tr>	<th style="display: none;">Id</th><th>Owner Name</th><th>Project Name</th><th>Issue Key</th><th>Summary</th><th>Question</th><th>Rating</th><th>OverallRating</th><th>Comment</th><th>CreatedDate</th></tr></thead><tbody style="overflow-y:scroll; height:100px;">'//<th>Delete</th></tr>';
	            AJS.$.each(response, function (i, item) {
	            //	console.log(item);
	             var datVal = item.createdate;
	            	var project= item.project;
	            //	var summary = item.summary;
	            //	console.log(summary);
	           //  console.log(datVal);
	            if(datVal == undefined || project == undefined){
	            	dat = " ";
	            	project = " ";
	         //    console.log(project);
	            }
	            
	            else
	            	{
	            //	 console.log(datVal);
		             var date = new Date(datVal);
		             dat = (("0" + date.getDate()).slice(-2)+'-' + ("0" + (date.getMonth() + 1)).slice(-2) + '-'+date.getFullYear());
		        //     console.log(dat);
	            	}
	              if(item.question == undefined && item.rating == undefined)
	            	{
	            	  item.question = " ";
	            	  item.rating = " ";
	            	}
	              if(item.overallrating == undefined && item.comment == undefined)
	              {
	            	  item.overallrating = " ";
	            	item.comment = " ";
	              }
	              if(item.summary == undefined)
	            	{
	            	  item.summary = " ";
	            	}
	             // console.log(item.summary);
	             button = '<button class="aui-button" id = "deleteCall" >Delete</button>';
	             trHTML += '<tr><td style = "display:none" class = "del">'+ item.id  + '</td><td>'+ item.assignee + '</td><td>' + project + '</td><td>' + item.issuekey + '</td><td>' + item.summary + '</td><td>'+ item.question + '</td><td>'+ item.rating+'</td><td>'+ item.overallrating + '</td><td>' + item.comment + '</td><td>' +dat + '</td></tr>' //<td>'+button+'</td></tr>';
	            });
	            AJS.$('#feedbackreport-table').append(trHTML);
	       },
	     error: function (xhr, ajaxOptions, thrownError) {
	      // alert(xhr.status + " "+ thrownError);
	     }});
	});
	};	

jQuery(function(){
jQuery("#dataexport").click(function () {	  
    var uri = AJS.$("#feedbackreport-table").battatech_excelexport({
            containerid: 'feedbackreport-table',
            datatype: 'table', 
            returnUri : true
            });
    var todayDate = new Date();
    todayDate = (("0" + todayDate.getDate()).slice(-2)+'-' + ("0" + (todayDate.getMonth() + 1)).slice(-2) + '-'+todayDate.getFullYear());
    AJS.$(this).attr('download','FeedbackReport_'+ todayDate +'.xls') // set file name (you want to put formatted date here)
    .attr('href',uri)                     // data to download
    .attr('target','_blank')
    // open in new window (optional)
       });
});
AJS.$(document).ready(function(){
	
	AJS.$("#txtFromDate").datepicker({
        numberOfMonths: 1,
        dateFormat: 'yy-mm-dd',
        onSelect: function(selected) {
       	AJS.$("#txtToDate").datepicker("option","minDate", selected)
        }
    });
	AJS.$("#txtToDate").datepicker({ 
        numberOfMonths: 1,
        dateFormat: 'yy-mm-dd',
        onSelect: function(selected) {
    AJS.$("#txtFromDate").datepicker("option","maxDate", selected)
        }
	});
	
	AJS.$("#deleteCall").live('click',function(){		
	   var id = AJS.$(this).closest("tr").find("td:first").text();
	  // console.log(AJS.$(this).closest("tr"));
   	AJS.$(this).closest("tr").remove();

       AJS.$.ajax({
			url: contextPath + '/rest/feedbackreportresource/latest/feedback-report/' + id,
            type: "DELETE", // <- Change here
            contentType: "application/json",
            success: function() {
            	//alert("removed sucessfully")
            	//	location.reload();
            },
            error: function() {
            }
        });
	});
});

function doSearch()
{
var searchVal = "";
var fromDate= "";
var toDate ="";
	searchVal =AJS.$("#quicksearchid").val();//.trim();
	if(AJS.$("#txtFromDate").val() == ''){
		fromDate = "1970-01-01 00:00:00" 
		//	alert("from date null" +fromDate);
	}
	else{
		fromDate =AJS.$("#txtFromDate").val().toString() + " 00:00:00";
	}
	if(AJS.$("#txtToDate").val() == ''){
		toDate = "1970-01-01 00:00:00" 
		//	alert("to date null " +toDate);
	}
	else{
	toDate = AJS.$("#txtToDate").val();	
	var date = new Date(toDate);
	date.setDate(date.getDate() + 1);
	//alert(date);
	var dat = (date.getFullYear() +'-' + ("0" + (date.getMonth() + 1)).slice(-2) + '-'+ ("0" + date.getDate()).slice(-2));
	toDate = dat.toString() + " 00:00:00";
	//alert(toDate);

	}
	//console.log(fromDate);
	jQuery(function(){
		jQuery("#feedbackreport-table").empty();
		jQuery(function() {
			

			var $el = AJS.$('#feedbackreport-table');
			dataProjectKey = $el.attr('data-project-key');
			
			console.log(dataProjectKey);
			
			
			if(fromDate == "1970-01-01 00:00:00" && toDate == "1970-01-01 00:00:00")
				{
					var propRest = AJS.contextPath() + '/rest/feedbackreportresource/latest/feedback-report/search?searchVal='+ searchVal+'&keyFilter='+ dataProjectKey;
					//console.log(propRest);
				}
			else{
				var propRest = AJS.contextPath() + '/rest/feedbackreportresource/latest/feedback-report/searchdate?searchVal='+ searchVal+'&fromDate='+ fromDate +'&toDate='+ toDate+'&keyFilter='+ dataProjectKey;
			//	console.log(propRest);
			}
			
		    //alert(searchVal);
		   // console.log(propRest);
		    jQuery.ajax({
				  url: propRest, 
				  type: 'GET',
				  dataType: "json",
				  contentType: "application/json", 
		        success: function(response) {
		        	//alert(response);
		        //	console.log(response);
		        	var trHTML = '  <thead><tr>	<th style="display: none;">Id</th><th>Owner Name</th><th>Project Name</th><th>Issue Key</th><th>Summary</th><th>Question</th><th>Rating</th><th>OverallRating</th><th>Comment</th><th>CreatedDate</th></tr></thead><tbody style="overflow-y:scroll; height:100px;">'//<th>Delete</th></tr>';
		            AJS.$.each(response, function (i, item) {
		             var datVal = item.createdate;
		             var project = item.project;
		          //   console.log(project);
		           // console.log(datVal);
		             if(datVal == ' ' || datVal == undefined){
			            	//alert(datVal);
			            	dat = ' ';
			         }
		             else
		             {
		            	// console.log(datVal);
			             var date = new Date(datVal);
			             dat = (("0" + date.getDate()).slice(-2)+'-' + ("0" + (date.getMonth() + 1)).slice(-2) + '-'+date.getFullYear());
			         //    console.log(dat);
		             }
		              if(item.question == undefined && item.rating == undefined)
		            	{
		            	  item.question = " ";
		            	  item.rating = " ";
		            	}
		              if(item.overallrating == undefined && item.comment == undefined)
		              {
		            	  item.overallrating = " ";
		            	item.comment = " ";
		              }
		              if(project == undefined){
		            	  project = " ";
		              }
		              
		              if(item.summary == undefined)
		            	{
		            	  item.summary = " ";
		            	}  
		              console.log(item.summary);

		     //        var date = new Date(datVal);
		     //        var dat = (("0" + date.getDate()).slice(-2)+'-' + ("0" + (date.getMonth() + 1)).slice(-2) + '-'+date.getFullYear());
		            // console.log(dat); 
		             button = '<button class="aui-button" id = "deleteCall" >Delete</button>';
		           //  console.log(item.project);
		             trHTML += '<tbody><tr><td style = "display:none" class = "del">'+ item.id  + '</td><td>'+ item.assignee + '</td><td>' + project + '</td><td>' + item.issuekey +'</td><td>' +item.summary + '</td><td>' + item.question + '</td><td>'+ item.rating+'</td><td>'+ item.overallrating + '</td><td>' + item.comment + '</td><td>' +dat + '</td></tr></tbody>'; //<td>'+button+'</td></tr>';
		            });
		            AJS.$('#feedbackreport-table').append(trHTML);
		       },
		     error: function (xhr, ajaxOptions, thrownError) {
		       alert(xhr.status + " "+ thrownError);
		     }});
		});

});
};
function clearSearch(){
	location.reload();
};

var $ = AJS.$
(function ($) {
    var $defaults = {
        containerid: null
        , datatype: 'table'
        , dataset: null
        , columns: null
        , returnUri: false
        , worksheetName: "My Worksheet"
        , encoding: "utf-8"
    };

    var $settings = $defaults;

    $.fn.battatech_excelexport = function (options) {
        $settings = $.extend({}, $defaults, options);

        var gridData = [];
        var excelData;

        return Initialize();

        function Initialize() {
            var type = $settings.datatype.toLowerCase();

            BuildDataStructure(type);

            switch (type) {
                case 'table':
                    excelData = Export(ConvertFromTable());
                    break;
                case 'json':
                    excelData = Export(ConvertDataStructureToTable());
                    break;
                case 'xml':
                    excelData = Export(ConvertDataStructureToTable());
                    break;
                case 'jqgrid':
                    excelData = Export(ConvertDataStructureToTable());
                    break;
            }

            if ($settings.returnUri) {
                return excelData;
            }
            else {
                window.open(excelData);
            }
        }

        function BuildDataStructure(type) {
            switch (type) {
                case 'table':
                    break;
                case 'json':
                    gridData = $settings.dataset;
                    break;
                case 'xml':
                    $($settings.dataset).find("row").each(function (key, value) {
                        var item = {};

                        if (this.attributes != null && this.attributes.length > 0) {
                            $(this.attributes).each(function () {
                                item[this.name] = this.value;
                            });

                            gridData.push(item);
                        }
                    });
                    break;
                case 'jqgrid':
                    $($settings.dataset).find("rows > row").each(function (key, value) {
                        var item = {};

                        if (this.children != null && this.children.length > 0) {
                            $(this.children).each(function () {
                                item[this.tagName] = $(this).text();
                            });

                            gridData.push(item);
                        }
                    });
                    break;
            }
        }

        function ConvertFromTable() {
            var result = $('<div>').append($('#' + $settings.containerid).clone());
          
            result.find("td:nth-child(1)").remove();
            result.find("th:nth-child(1)").remove();
            result.find("td:nth-child(9)").remove();
            result.find("th:nth-child(9)").remove();
            
            //result.find('[style*="display: none"]').remove();    
            result = result.html();
            return result;
        }

        function ConvertDataStructureToTable() {
            var result = "<table>";

            result += "<thead><tr>";
            $($settings.columns).each(function (key, value) {
                if (this.ishidden != true) {
                    result += "<th";
                    if (this.width != null) {
                        result += " style='width: " + this.width + "'";
                    }
                    result += ">";
                    result += this.headertext;
                    result += "</th>";
                }
            });
            result += "</tr></thead>";

            result += "<tbody>";
            $(gridData).each(function (key, value) {
                result += "<tr>";
                $($settings.columns).each(function (k, v) {
                    if (value.hasOwnProperty(this.datafield)) {
                        if (this.ishidden != true) {
                            result += "<td";
                            if (this.width != null) {
                                result += " style='width: " + this.width + "'";
                            }
                            result += ">";
                            result += value[this.datafield];
                            result += "</td>";
                        }
                    }
                });
                result += "</tr>";
            });
            result += "</tbody>";
            result += "</table>";
            return result;
        }

        function Export(htmltable) {
            var excelFile = "<html xmlns:o='urn:schemas-microsoft-com:office:office' xmlns:x='urn:schemas-microsoft-com:office:excel' xmlns='http://www.w3.org/TR/REC-html40'>";
            excelFile += "<head>";
            excelFile += '<meta http-equiv="Content-type" content="text/html;charset=' + $defaults.encoding + '" />';
            excelFile += "<!--[if gte mso 9]>";
            excelFile += "<xml>";
            excelFile += "<x:ExcelWorkbook>";
            excelFile += "<x:ExcelWorksheets>";
            excelFile += "<x:ExcelWorksheet>";
            excelFile += "<x:Name>";
            excelFile += "{worksheet}";
            excelFile += "</x:Name>";
            excelFile += "<x:WorksheetOptions>";
            excelFile += "<x:DisplayGridlines/>";
            excelFile += "</x:WorksheetOptions>";
            excelFile += "</x:ExcelWorksheet>";
            excelFile += "</x:ExcelWorksheets>";
            excelFile += "</x:ExcelWorkbook>";
            excelFile += "</xml>";
            excelFile += "<![endif]-->";
            excelFile += "</head>";
            excelFile += "<body>";
            excelFile += htmltable.replace(/"/g, '\'');
            excelFile += "</body>";
            excelFile += "</html>";
            
             uri = "data:application/vnd.ms-excel;base64,";
            var ctx = { worksheet: $settings.worksheetName, table: htmltable };
            return (uri + base64(format(excelFile, ctx)));
        }

        function base64(s) {
            return window.btoa(unescape(encodeURIComponent(s)));
        }

        function format(s, c) {
            return s.replace(/{(\w+)}/g, function (m, p) { return c[p]; });
            //replace(/Delete/g, "" ).replace(/Id/g,"")
        }
    };
});