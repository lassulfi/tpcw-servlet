import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.mahout.cf.taste.recommender.RecommendedItem;

/**
 * C02T06 - Atualização da IHC
 * 
 * 
 * @author esoft35
 * @author esoft47
 *
 */
public class TPCW_recommendation_processing {

	public static void DisplayRecommendations(PrintWriter out, HttpSession session, HttpServletRequest req,
			HttpServletResponse res, int new_sid) {
		
		// If we have seen this session id before
		if (!session.isNew()) {
			int C_ID[] =  (int[]) session.getValue("C_ID");
			
			
			int I_ID = TPCW_Database.getABookAnyBook().getId();
			String url;

			List<RecommendedItem> recommendations = Bookstore.getUserRecommendations(C_ID[0], 5);
			
			String SHOPPING_ID = req.getParameter("SHOPPING_ID");

			// Create table and "You may also like..." row
			out.print("<TABLE ALIGN=CENTER BORDER=0 WIDTH=660>\n");
			out.print("<TR ALIGN=CENTER VALIGN=top>\n");
			out.print("<TD COLSPAN=5><B><FONT COLOR=#ff0000 SIZE=+1>" + "You may also like..."
					+ "</FONT></B></TD></TR>\n");
			out.print("<TR ALIGN=CENTER VALIGN=top>\n");

			// Create links and references to book images
			for (int i = 0; i < recommendations.size(); i++) {
				url = "TPCW_product_detail_servlet";
				url = url + "?I_ID=" + String.valueOf(recommendations.get(i).getItemID());
				if (SHOPPING_ID != null)
					url = url + "&SHOPPING_ID=" + SHOPPING_ID;
				else if (new_sid != -1)
					url = url + "&SHOPPING_ID=" + new_sid;
				if (C_ID != null)
					url = url + "&C_ID=" + C_ID;
				out.print("<TD><A HREF=\"" + res.encodeUrl(url));
				out.print("\"><IMG SRC=\"/../images/" + related.get(i).getThumbnail() + "\" ALT=\"Book "
						+ String.valueOf(i + 1) + "\" WIDTH=\"100\" HEIGHT=\"150\"></A>\n");
				out.print("</TD>");
			}
			out.print("</TR></TABLE>\n");
		}
		
	}

}
