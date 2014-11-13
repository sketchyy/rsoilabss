package com.sketchyy.rsoi1.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.sketchyy.rsoi1.oauth.Character;
import com.sketchyy.rsoi1.oauth.MyOAuthClient;
import com.sketchyy.rsoi1.oauth.PropsHolder;

/**
 * Servlet implementation class Authorizer
 */
@WebServlet("/Authorizer")
public class Authorizer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Authorizer() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		String code = request.getParameter("code");
		if (code == null) {
			// redirect to error page
			request.setAttribute("errmessage", "Can't get authorization code from BATTLE.NET");
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
		
		// Send POST to battle.net
	    try {
	    	MyOAuthClient myOAuth = new MyOAuthClient(PropsHolder.getProperties().getProperty("client_id"),
	    			PropsHolder.getProperties().getProperty("client_secret"));
	    	
	    	JSONObject responseJSON = myOAuth.sendTokenRequest(code);
	    	JSONObject responseCharacters = myOAuth.getAccountCharacters(responseJSON);
	    	
	    	// Parse JSON to get array of characters    	
	    	JSONArray charactersJson = (JSONArray)responseCharacters.get("characters"); 
	    	List<Character> characters = new ArrayList<Character>(charactersJson.size());
	    	
	    	@SuppressWarnings("unchecked")
			Iterator<JSONObject> it = charactersJson.iterator();
	    	while (it.hasNext()) {
				JSONObject charJson = (JSONObject) it.next();
				System.out.println(charJson);
				
				Character character = new Character();
				character.setName((String)charJson.get("name"));
				character.setGuild((String)charJson.get("guild"));
				character.setRealm((String)charJson.get("realm"));
				character.setThumbnail((String)charJson.get("thumbnail"));
				character.setLevel((Long)charJson.get("level"));
				characters.add(character);
			}
	    	
			request.setAttribute("characters", characters);	
			request.getRequestDispatcher("main.jsp").forward(request, response);
		} catch (Exception e) {
			// Redirect to error page
			request.setAttribute("errmessage", e.getMessage());
			request.getRequestDispatcher("error.jsp").forward(request, response);
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
