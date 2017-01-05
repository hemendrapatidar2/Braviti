<%@page import="com.tm.braveti.predictivemodel.UserPreferences"%>
<%@page import="org.codehaus.jackson.map.ObjectMapper"%>
<%@page import="com.tm.braveti.model.OfferCategory"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.tm.braveti.predictivemodel.SparkRecommender"%>
<%@page import="com.tm.braveti.model.OfferDTO"%>
<%@page import="java.util.List"%>
<%
try{
System.out.println("inside getUserOffer Method " + request.getParameter("userName")
              + "Location " + request.getParameter("location"));
String userName = request.getParameter("userName");
String location = request.getParameter("location");
List<OfferDTO> offerListDTO = new ArrayList<OfferDTO>();
SparkRecommender recommender=new SparkRecommender();
UserPreferences userPreferences=new UserPreferences();
offerListDTO=recommender.recommendOffers(userName, location, userPreferences);
for (OfferDTO offerDTO : offerListDTO) {
System.out.println("outlet Name:: " + offerDTO.getStoreName());
for (OfferCategory offerCategory : offerDTO.getOfferList()) {
       System.out.println("category for this store : "
                     + offerCategory.getCategoryName() + " offer :: "
                     + offerCategory.getOfferDescription());
}
}
ObjectMapper mapper = new ObjectMapper();
String jsonInString = mapper.writeValueAsString(offerListDTO);
System.out.println(jsonInString);
out.println(jsonInString);
}catch(Exception ex){
       ex.printStackTrace();
}
%>
