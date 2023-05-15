package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/message")
public class message extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	
    public message() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			String msg = request.getParameter("msg");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Mensaje</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>" + msg + "</h1>");
			out.println("<br><br><br><br>");
			out.println("<form action=\"/servlets/message\" method=\"POST\">"
					+ "<input type=\"submit\" value=\"Regresar a Inicio\">"
					+ "</form>");
			out.println("</body>");
			out.println("</html>");
		}
		catch(Exception ex){
			System.out.print(ex);
		}
		finally {
			out.close();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean isExternal = Boolean.parseBoolean(request.getParameter("external"));
		if(!isExternal) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.html");
			dispatcher.forward(request, response);
		}
		else 
			doGet(request, response);
	}

}
