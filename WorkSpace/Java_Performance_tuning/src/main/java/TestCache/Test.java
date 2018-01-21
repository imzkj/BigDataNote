package TestCache;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//oscache»º´æ
public class Test extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/hzjwerp", "root", "root");
            PreparedStatement pstmt = con.prepareStatement("select * from tb_admin");
            ResultSet rs = pstmt.executeQuery();
           List<String> list = new ArrayList<String>();
            while(rs.next()) {
            	list.add(rs.getString("account"));
            }
            request.setCharacterEncoding("gb2312");
            request.setAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher("show.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
