<%@page import="org.codehaus.jackson.map.ObjectMapper"%>
<%@page import="com.tm.braveti.predictivemodel.CustomResponse"%>
<%@page import="com.tm.braveti.predictivemodel.PreferencesUtility"%>
<%
CustomResponse customResponse=null;
try{
	PreferencesUtility.setUserPreferences(request.getParameter("userId"), request.getParameter("categories"), request.getParameter("pricerange"));
	 customResponse = new CustomResponse(new Boolean(true),"");
}catch(Exception ex){
	 customResponse = new CustomResponse(new Boolean(false),ex.getLocalizedMessage());
}
ObjectMapper mapper = new ObjectMapper();
String jsonInString = mapper.writeValueAsString(customResponse);
System.out.println(jsonInString);
out.println(jsonInString);
%>