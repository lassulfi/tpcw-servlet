/* 
 * TPCW_buy_request_servlet.java - Servlet Class implements the buy request
 *                                 web interaction.
 *
 ************************************************************************
 *
 * This is part of the the Java TPC-W distribution,
 * written by Harold Cain, Tim Heil, Milo Martin, Eric Weglarz, and Todd
 * Bezenek.  University of Wisconsin - Madison, Computer Sciences
 * Dept. and Dept. of Electrical and Computer Engineering, as a part of
 * Prof. Mikko Lipasti's Fall 1999 ECE 902 course.
 *
 * Copyright (C) 1999, 2000 by Harold Cain, Timothy Heil, Milo Martin, 
 *                             Eric Weglarz, Todd Bezenek.
 *
 * This source code is distributed "as is" in the hope that it will be
 * useful.  It comes with no warranty, and no author or distributor
 * accepts any responsibility for the consequences of its use.
 *
 * Everyone is granted permission to copy, modify and redistribute
 * this code under the following conditions:
 *
 * This code is distributed for non-commercial use only.
 * Please contact the maintainer for restrictions applying to 
 * commercial use of these tools.
 *
 * Permission is granted to anyone to make or distribute copies
 * of this code, either as received or modified, in any
 * medium, provided that all copyright notices, permission and
 * nonwarranty notices are preserved, and that the distributor
 * grants the recipient permission for further redistribution as
 * permitted by this document.
 *
 * Permission is granted to distribute this code in compiled
 * or executable form under the same conditions that apply for
 * source code, provided that either:
 *
 * A. it is accompanied by the corresponding machine-readable
 *    source code,
 * B. it is accompanied by a written offer, with no time limit,
 *    to give anyone a machine-readable copy of the corresponding
 *    source code in return for reimbursement of the cost of
 *    distribution.  This written offer must permit verbatim
 *    duplication by anyone, or
 * C. it is distributed by someone who received only the
 *    executable form, and is accompanied by a copy of the
 *    written offer of source code that they received concurrently.
 *
 * In other words, you are welcome to use, share and improve this codes.
 * You are forbidden to forbid anyone else to use, share and improve what
 * you give them.
 *
 ************************************************************************/

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Date;

public class TPCW_buy_request_servlet extends HttpServlet {
    
  public void doGet(HttpServletRequest req, HttpServletResponse res)
      throws IOException, ServletException {
      PrintWriter out = res.getWriter();
      String url;
      // Set the content type of this servlet's result.
      res.setContentType("text/html");

      HttpSession session = req.getSession(false);
 
      String C_ID = req.getParameter("C_ID");
      String SHOPPING_ID = req.getParameter("SHOPPING_ID");
      String RETURNING_FLAG = req.getParameter("RETURNING_FLAG");
      
      Customer cust = null;
      
      out.print("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD W3 HTML//EN\">\n");
      out.print("<HTML><HEAD><TITLE>TPC-W Buy Request</TITLE></HEAD>\n");
      out.print("<BODY BGCOLOR=\"ffffff\">\n");
      out.print("<H1 ALIGN=\"CENTER\">TPC Web Commerce Benchmark " +
		"(TPC-W)</H1>\n");
      out.print("<H2 ALIGN=\"CENTER\">Buy Request Page</H2>\n");


      if(RETURNING_FLAG==null){
	  out.print("ERROR: RETURNING_FLAG not set!</BODY><HTML>");
	  return;
      }
      if(RETURNING_FLAG.equals("Y")){
	  String UNAME = req.getParameter("UNAME");
	  String PASSWD = req.getParameter("PASSWD");
	  if(UNAME.length() == 0 || PASSWD.length() == 0){
	      out.print("Error: Invalid Input</BODY></HTML>");
	      return;
	  }

	  cust = TPCW_Database.getCustomer(UNAME);
	  TPCW_Database.refreshSession(cust.getId());
	  if(!PASSWD.equals(cust.getPasswd())){
	      out.print("Error: Incorrect Password</BODY></HTML>");
	      return;
	  }
      }
      else if(RETURNING_FLAG.equals("N")){
	  cust = TPCW_Database.createNewCustomer(req.getParameter("FNAME"),
	          req.getParameter("LNAME"), req.getParameter("STREET_1"),
	          req.getParameter("STREET_2"), req.getParameter("CITY"),
	          req.getParameter("STATE"), req.getParameter("ZIP"),
	          req.getParameter("COUNTRY"), req.getParameter("PHONE"),
	          req.getParameter("EMAIL"), new Date(req.getParameter("BIRTHDATE")),
	          req.getParameter("DATA"));
      }
      else out.print("ERROR: RETURNING_FLAG not set to Y or N!\n");
      
      if(SHOPPING_ID == null){
	  out.print("ERROR: Shopping Cart ID not set!</BODY></HTML>");
	  return;
      }
      //Update the shopping cart cost and get the current contents
      Cart mycart = TPCW_Database.getCart(Integer.parseInt(SHOPPING_ID));
      
      //Print out the web page
      out.print("<HR><FORM ACTION=\"TPCW_buy_confirm_servlet;jsessionid="+
		req.getRequestedSessionId()+"\" METHOD=\"GET\">\n");
      out.print("<TABLE BORDER=\"0\" WIDTH=\"90%\">\n");
      out.print("<TR ALIGN=\"LEFT\" VALIGN=\"TOP\">\n");
      out.print("<TD VALIGN=\"TOP\" WIDTH=\"45%\">\n");
      out.print("<H2>Billing Information:</H2>\n");
      out.print("<TABLE WIDTH=\"100%\" BORDER=\"0\"><TR>\n");
      
      out.print("<TR><TD>Firstname:</TD><TD>" + cust.getFname()+"</TD></TR>\n");
      out.print("<TR><TD>Lastname: </TD><TD>" + cust.getLname() +"</TD></TR>\n");
      out.print("<TR><TD>Addr_street_1:</TD><TD>" + cust.getAddress().getStreet1() + 
		"</TD></TR>\n");
      out.print("<TR><TD>Addr_street_2:</TD><TD>" + cust.getAddress().getStreet2() + 
		"</TD></TR>\n");
      out.print("<TR><TD>City:</TD><TD>" + cust.getAddress().getCity() + "</TD></TR>\n");
      out.print("<TR><TD>State:</TD><TD>" + cust.getAddress().getState() + "</TD></TR>\n");
      out.print("<TR><TD>Zip:</TD><TD>" + cust.getAddress().getZip() + "</TD></TR>\n");
      out.print("<TR><TD>Country:</TD><TD>" + cust.getAddress().getCountry().getName() + 
		"</TD></TR>\n");
      out.print("<TR><TD>Email:</TD><TD>" + cust.getEmail() + "</TD></TR>\n");
      out.print("<TR><TD>Phone:</TD><TD>" + cust.getPhone()+ "</TD></TR>\n");
      if(RETURNING_FLAG.equals("N")){
	  out.print("<TR><TD>USERNAME:</TD><TD>" + cust.getUname() + "</TD></TR>\n");
	  out.print("<TR><TD>C_ID:</TD><TD>" + cust.getId() + "</TD></TR>\n");
      }
      out.print("</TABLE></TD>");

      //
      //The Shipping Info Form
      //

      out.print("<TD VALIGN=\"TOP\" WIDTH=\"45%\">\n");
      out.print("<H2>Shipping Information:</H2>\n");
      out.print("<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"0\" WIDTH=\"100%\">\n");
      out.print("<TR><TD WIDTH=\"50%\">Addr_street_1:</TD>\n");
      out.print("<TD><INPUT NAME=\"STREET_1\" SIZE=\"40\" VALUE=\"\"></TD></TR>\n");
      out.print("<TR><TD>Addr_street_ 2:</TD>\n");
      out.print("<TD><INPUT NAME=\"STREET_2\" SIZE=\"40\" VALUE=\"\"></TD></TR>\n");
      out.print("<TR><TD>City:</TD><TD><INPUT NAME=\"CITY\" SIZE=\"30\" VALUE=\"\"></TD></TR>\n");
      out.print("<TR><TD>State:</TD><TD><INPUT NAME=\"STATE\" SIZE=\"20\" VALUE=\"\"></TD></TR>\n");
      out.print("<TR><TD>Zip:</TD><TD><INPUT NAME=\"ZIP\" SIZE=\"10\" VALUE=\"\"></TD></TR>\n");
      out.print("<TR><TD>Country:</TD><TD><INPUT NAME=\"COUNTRY\" VALUE=\"\" SIZE=\"40\"></TD></TR>\n");
      
      //
      //Order Information Section
      //

      out.print("</TABLE></TD></TR></TABLE>\n");
      out.print("<HR><H2>Order Information:</H2>\n");
      out.print("<TABLE BORDER=\"1\" CELLSPACING=\"0\" CELLPADDING=\"0\">\n");
      out.print("<TR><TD><B>Qty</B></TD><TD><B>Product</B></TD></TR>\n");

      //Insert Shopping Cart Contents Here!
      //
      for(CartLine thisline : mycart.getLines()){
	  out.print("<TR><TD VALIGN=\"TOP\">" + thisline.getQty() + "</TD>\n");
	  out.print("<TD VALIGN=\"TOP\">Title:<I>"+thisline.getBook().getTitle() +
		    "</I> - Backing: " + thisline.getBook().getBacking());
	  out.print("<BR>SRP. $" + thisline.getBook().getSrp());
	  out.print("<FONT COLOR=\"#aa0000\">\n");
	  out.print("<B>Your Price:" + thisline.getBook().getCost()+ "</B>\n");
	  out.print("</FONT></TD></TR>");
      }

      out.print("</TABLE>\n");
      out.print("<P><BR></P><TABLE BORDER=\"0\">\n");
      out.print("<TR><TD><B>Subtotal with discount (" + cust.getDiscount()
		+  "%):</B></TD><TD ALIGN=\"RIGHT\"><B>$" 
		+ mycart.subTotal(cust.getDiscount()) +"</B></TD></TR>\n");
      out.print("<TR><TD><B>Tax</B></TD><TD ALIGN=\"RIGHT\"><B>$" + mycart.tax(cust.getDiscount()) +"</B></TD></TR>\n");
      out.print("<TR><TD><B>Shipping &amp; Handling</B></TD><TD ALIGN=\"RIGHT\"><B>$" + mycart.shipCost()+"</B></TD></TR>\n");
      out.print("<TR><TD><B>Total</B></TD><TD ALIGN=\"RIGHT\"><B>$" + mycart.total(cust.getDiscount()) +"</B></TD></TR></TABLE>\n");
      
      //
      //Credit Card Stuff
      //
      out.print("<HR WIDTH=\"700\"><P><BR></P>\n");

      out.print("<TABLE BORDER=\"1\" CELLPADDING=\"5\" "+
		"CELLSPACING=\"0\"><TR>\n");
      out.print("<TD>Credit Card Type</TD>\n");
      out.print("<TD><INPUT TYPE=\"RADIO\" NAME=\"CC_TYPE\" VALUE=\"Visa\" " + 
		"CHECKED=\"CHECKED\">Visa\n");
      out.print("<INPUT TYPE=\"RADIO\" NAME=\"CC_TYPE\" " + 
		"VALUE=\"Master\">MasterCard\n");
      out.print("<INPUT TYPE=\"RADIO\" NAME=\"CC_TYPE\" " + 
		"VALUE=\"Discover\">Discover\n");
      out.print("<INPUT TYPE=\"RADIO\" NAME=\"CC_TYPE\" " + 
		"VALUE=\"Amex\">American Express\n");
      out.print("<INPUT TYPE=\"RADIO\" NAME=\"CC_TYPE\" " + 
		"VALUE=\"Diners\">Diners</TD></TR>\n");
     
      out.print("<TR><TD>Name on Credit Card</TD>\n");
      out.print("<TD><INPUT NAME=\"CC_NAME\" SIZE=\"30\" VALUE=\"\"></TD></TR>\n");
      out.print("<TR><TD>Credit Card Number</TD>\n");
      out.print("<TD><INPUT NAME=\"CC_NUMBER\" SIZE=\"16\" VALUE=\"\"></TD></TR>\n");
      out.print("<TR><TD>Credit Card Expiration Date</TD>\n");
      out.print("<TD><INPUT NAME=\"CC_EXPIRY\" SIZE=\"15\" VALUE=\"\"></TD></TR>\n");

      out.print("<TR><TD>Shipping Method</TD>\n");
      out.print("<TD><INPUT TYPE=\"RADIO\" NAME=\"SHIPPING\" VALUE=\"AIR\" CHECKED=\"CHECKED\">AIR");
      out.print("<INPUT TYPE=\"RADIO\" NAME=\"SHIPPING\" VALUE=\"UPS\">UPS\n");
      out.print("<INPUT TYPE=\"RADIO\" NAME=\"SHIPPING\" VALUE=\"FEDEX\">FEDEX\n");
      out.print("<INPUT TYPE=\"RADIO\" NAME=\"SHIPPING\" VALUE=\"SHIP\">SHIP\n");
      out.print("<INPUT TYPE=\"RADIO\" NAME=\"SHIPPING\" VALUE=\"COURIER\">COURIER\n");
      out.print("<INPUT TYPE=\"RADIO\" NAME=\"SHIPPING\" VALUE=\"MAIL\">MAIL\n");
      out.print("</TD></TR></TABLE><P><CENTER>\n");
    //      out.print("<INPUT TYPE=HIDDEN NAME=\"" + TPCW_Util.SESSION_ID + 
    //		"\" value = \"" + req.getRequestedSessionId() + "\">\n");
      if(SHOPPING_ID != null)
	  out.print("<INPUT TYPE=HIDDEN NAME=\"SHOPPING_ID\" value = \"" + 
		    SHOPPING_ID + "\">\n");
      out.print("<INPUT TYPE=HIDDEN NAME=\"C_ID\" value = \"" + 
		cust.getId() + "\">\n");
      out.print("<INPUT TYPE=\"IMAGE\" NAME=\"Confirm Buy\" SRC=\"/../images/submit_B.gif\">\n");
      url = "TPCW_shopping_cart_interaction?ADD_FLAG=N&C_ID=" + cust.getId();
      if(SHOPPING_ID != null)
	  url = url + "&SHOPPING_ID=" + SHOPPING_ID;
      out.print("<A HREF=\"" +res.encodeUrl(url));
      out.print("\"><IMG SRC=\"/../images/shopping_cart_B.gif\" " +
		"ALT=\"Shopping Cart\"></A>\n");
      
      url = "TPCW_order_inquiry_servlet?C_ID=" + cust.getId();
      if(SHOPPING_ID != null)
	  url = url + "&SHOPPING_ID=" + SHOPPING_ID;
      
      out.print("<A HREF=\"" + res.encodeUrl(url));
      out.print("\"><IMG SRC=\"/../images/order_status_B.gif\" " +
		"ALT=\"Order Status\"></A>\n");
      out.print("</P></CENTER></BODY></HTML>");
      out.close();
      return;
  }
}
