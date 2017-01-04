<%@page import="org.codehaus.jackson.map.ObjectMapper"%>
<%@page import="com.tm.braveti.service.PreferencesService"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.tm.braveti.model.PreferencesDTO"%>
<%@page import="java.util.List"%>
<%
try{
	System.out.println("inside getPreferencesData Method ");
	PreferencesService prefData = new PreferencesService();
	PreferencesDTO preferencesDTO = prefData.getPreferencesData();
	System.out.println("Catagory :: "+preferencesDTO.getCategoryList());
	System.out.println("Price range :: "+preferencesDTO.getPriceRangeList());		
	//return// Response.status(200).entity(preferencesDTO).build();
ObjectMapper mapper = new ObjectMapper();
String jsonInString = mapper.writeValueAsString(preferencesDTO);
out.println(jsonInString);
}catch(Exception ex){
       ex.printStackTrace();
}
%>