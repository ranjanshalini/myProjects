
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;;

/**
 * Servlet implementation class DbManager
 */
@WebServlet("/DbManager")
public class DbManager extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * Default constructor. 
     */
    public DbManager() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String queryString = "select * from url_table";
		ArrayList<UrlData> urlList = new ArrayList<UrlData>();
		int rowCount = 0;
		try{
			ResultSet resultset = ConnectionBean.getConString().createStatement().executeQuery(queryString);
			
			while (resultset.next()) {
				rowCount++;
				int urlId = resultset.getInt("urlId");
	            String urlName = resultset.getString("urlName");
	            String pwd = resultset.getString("urlPwd");
	            String desc = resultset.getString("urlDesc");
	            UrlData ud = new UrlData();
	            ud.setId(rowCount);
	            ud.setTableId(urlId);
	            ud.setUrlName(urlName);
	            ud.setPassword(pwd);
	            ud.setDescription(desc);
	            
	            urlList.add(ud);
			}

            String json = new Gson().toJson(urlList);
            response.setContentType("application/json");
            response.getWriter().write(json);
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		InputStreamReader isr = new InputStreamReader(request.getInputStream());
		BufferedReader in = new BufferedReader(isr);
		String json = in.readLine();
		
		UrlData ud1 = new UrlData();
		ud1 = new Gson().fromJson(json, UrlData.class);
		
		String queryString;
		
		if(ud1.getTableId()==0){
			queryString = "Insert into url_table values ("+ud1.getId()+","+ "'"+ud1.getUrlName()+"' , "+"'"+ud1.getPassword()+"' , "+"'"+ud1.getDescription()+"')";
		}
		else{
			queryString = "Update url_table SET urlPwd ='"+ud1.getPassword()+
											 "', urlName ='"+ud1.getUrlName()+
											 "', urlDesc ='"+ud1.getDescription()+
											 "' where urlId ="+ud1.getTableId();
		}
		try {
			ConnectionBean.getConString().createStatement().executeUpdate(queryString);
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
	}

}
