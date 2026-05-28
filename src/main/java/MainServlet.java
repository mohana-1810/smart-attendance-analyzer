import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;

public class MainServlet extends HttpServlet {

    private Connection getCon() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
        	    "jdbc:mysql://localhost:3306/attendance_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
        	    "your_username",
        	    "your_password"
        	);
    }

    // =========================
    // HANDLE ANALYSIS
    // =========================
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException {

        String action = req.getParameter("action");

        if("analysis".equals(action)) {
            showAnalysis(req, res);
        }
    }

    // =========================
    // HANDLE INPUT
    // =========================
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException {

        try {
            int id = Integer.parseInt(req.getParameter("id"));
            String name = req.getParameter("name");
            int attended = Integer.parseInt(req.getParameter("attended"));
            int total = Integer.parseInt(req.getParameter("total"));

            Connection con = getCon();

            // Insert student (avoid duplicates)
            PreparedStatement ps1 = con.prepareStatement(
                "INSERT IGNORE INTO students VALUES (?, ?)"
            );
            ps1.setInt(1, id);
            ps1.setString(2, name);
            ps1.executeUpdate();

            // Insert attendance
            PreparedStatement ps2 = con.prepareStatement(
                "INSERT INTO attendance VALUES (?, ?, ?)"
            );
            ps2.setInt(1, id);
            ps2.setInt(2, attended);
            ps2.setInt(3, total);
            ps2.executeUpdate();

            ps1.close();
            ps2.close();
            con.close();

            res.sendRedirect("dashboard.jsp");

        } catch(Exception e) {
            e.printStackTrace();
            res.getWriter().println("Error: " + e.getMessage());
        }
    }

    // =========================
    // ANALYSIS LOGIC
    // =========================
    private void showAnalysis(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException {

        ArrayList<Integer> ids = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Integer> perc = new ArrayList<>();
        ArrayList<Integer> bunks = new ArrayList<>();

        try {
            Connection con = getCon();
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(
                "SELECT s.student_id, s.name, a.attended, a.total " +
                "FROM students s JOIN attendance a ON s.student_id = a.student_id"
            );

            while(rs.next()) {
                int id = rs.getInt("student_id");
                String name = rs.getString("name");
                int attended = rs.getInt("attended");
                int total = rs.getInt("total");

                // attendance %
                int p = (attended * 100) / total;

                // GREEDY (bunk calculation per student)
                int bunk = 0;
                while((attended * 100.0)/(total + bunk + 1) >= 75) {
                    bunk++;
                }

                ids.add(id);
                names.add(name);
                perc.add(p);
                bunks.add(bunk);
            }

            req.setAttribute("ids", ids);
            req.setAttribute("names", names);
            req.setAttribute("percentages", perc);
            req.setAttribute("bunks", bunks);

            rs.close();
            st.close();
            con.close();

            req.getRequestDispatcher("analysis.jsp").forward(req, res);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}