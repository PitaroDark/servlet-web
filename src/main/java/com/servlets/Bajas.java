package com.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.classes.Postgresql;

@WebServlet("/bajas")
public class Bajas extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private Postgresql psql;

    public Bajas() {
        super();
        psql = new Postgresql();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/templates/bajas.html");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String id = request.getParameter("txtId");
			String nameOwner = request.getParameter("txtNameOwner");
			String msg = "";
			if(psql.connect()) {
				String query = "DELETE FROM dogs WHERE id = '" + id + "' AND owner = '" + nameOwner + "'";
				psql.query(query);
				msg = "El perrito de " + nameOwner + " fue eliminado con exito";
			}
			else
				msg = "El perrito de " + nameOwner + " no se logro eliminar por falla de base de datos";
			System.out.println("\nValores Obtenidos -> " + id + " "  + nameOwner);
			forward(msg, request, response);
		}
		catch(Exception ex){
			System.out.println(ex);
			String msg = "El perrito no se logro eliminar por " + ex;
			forward(msg, request, response);
		}
		finally {
			psql.disconect();
		}
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	protected void forward(String msg, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/message?msg=" + msg + "&external=true");
		dispatcher.forward(request, response);
	}

}
