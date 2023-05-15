package com.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.classes.*;

@WebServlet("/altas")
public class Altas extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    private Postgresql psql;

    public Altas() {
        super();
        psql = new Postgresql();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/templates/altas.html");
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String nameDog = request.getParameter("txtNameDog");
			String edad = request.getParameter("txtEdad");
			String raza = request.getParameter("txtRaza");
			String nameOwner = request.getParameter("txtNameOwner");
			boolean isConected = psql.connect();
			String msg = "";
			if(isConected) {
				String query = "INSERT INTO dogs (name, old, race, owner) VALUES('" + nameDog + "', " + edad + ", '" + raza + "', '" + nameOwner + "')";
				String querySelect = "SELECT id FROM dogs WHERE name = '" + nameDog + "' AND old=" + edad + " AND race='" + raza +  "' AND owner='" + nameOwner + "'";
				psql.query(query);
				int id = ((psql.query(querySelect, new String[] { "id" } )).get(0)).getInteger("id");
				msg = "El perrito " + nameDog + " fue guardado con exito con el ID " + id;
			}
			else
				msg = "El perrito " + nameDog + " no se logro guardar por falla de base de datos";
			System.out.println("\nValores Obtenidos -> " + nameDog + " " + edad + " " + raza + " " + nameOwner);
			forward(msg, request, response);
		}
		catch(Exception ex){
			System.out.println(ex);
			String msg = "El perrito no se logro guardar por " + ex;
			forward(msg, request, response);
		}
		finally {
			psql.disconect();
		}
	}
	
	protected void forward(String msg, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/message?msg=" + msg + "&external=true");
		dispatcher.forward(request, response);
	}

}
