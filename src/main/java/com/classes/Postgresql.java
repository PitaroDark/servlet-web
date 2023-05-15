package com.classes;

import java.sql.*;
import java.util.ArrayList;
import org.json.simple.JsonObject;

public class Postgresql {
	
	private String port;
	private String host;
	private String dbName;
	private String url;
	private String usuario;
	private String password;
	private Connection conn;
	private Statement stmt;
	
	public Postgresql() {
		this.host = "127.0.0.1";
		this.port = "5431";
		this.dbName = "postgres";
		this.url = "jdbc:postgresql://" + this.host + ":" + this.port + "/" + this.dbName;
		this.usuario = "postgres";
	    this.password = "mfmssmcl";
	    this.conn = null;
	    this.stmt = null;
	}
	
	/*
	    *@return boolean
	*/
	public boolean connect() {
		try {
			conn = DriverManager.getConnection(url, usuario, password);
			if(conn == null)
				return false;
			stmt = this.conn.createStatement();
			return true;
		}
		catch(Exception e) {
			System.out.println(e);
			return false;
		}
	}
	
	/*
	    *@param query to be carried out  
	    *@return string[]
	*/
	public void query(String query) {
		try {
			stmt.addBatch(query);
			stmt.executeBatch();
			//stmt.executeQuery(query); //Funciona pero me da error al no devolver nada
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public ArrayList<JsonObject> query(String query, String[] args) {
		ArrayList<JsonObject> jsonArray = new ArrayList<JsonObject>(); 
		try {
			ResultSet res = stmt.executeQuery(query);
			while(res.next()) {
				JsonObject json = getArgs(res, args);
				jsonArray.add(json);
			}
		}
		catch(Exception ex) {
			JsonObject json = new JsonObject();
			json.put("Error", ex);
			jsonArray.add(json);
		}
		return jsonArray;
	}
	
	private JsonObject getArgs(ResultSet result, String[] args) {
		JsonObject json = new JsonObject();
		try {
			for(String arg: args)
				json.put(arg, result.getString(arg));
		}
		catch(Exception ex) {
			json.put("Error", ex);
		}
		return json;
	}
	
	/*
	    *@return boolean
	*/
	public boolean disconect() {
		try {
			this.stmt.close();
			this.stmt = null;
			this.conn.close();
			this.conn = null;
			return true;
		}
		catch(Exception e) {
			System.out.println(e);
			return false;
		}
	}
	
}
