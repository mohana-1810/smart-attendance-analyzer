import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException {
    	System.out.println("LoginServlet Hit");		
        String u = req.getParameter("username");
        String p = req.getParameter("password");

        // backend validation
        if(u == null || p == null || u.equals("") || p.equals("")) {
            res.getWriter().println("Invalid input");
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
            	    "jdbc:mysql://localhost:3306/attendance_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
            	    "your_username",
            	    "your_password"
            	);

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM users WHERE username=? AND password=?"
            );

            ps.setString(1, u);
            ps.setString(2, p);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                HttpSession s = req.getSession();
                s.setAttribute("user", u);
                res.sendRedirect("dashboard.jsp");
            } else {
                res.getWriter().println("Login Failed");
            }
            rs.close();
            ps.close();
            con.close();
        } catch(Exception e) {
            e.printStackTrace();
            res.getWriter().println("Error: " + e.getMessage());
        }
        
    }
}