package com.sketchyy.rsoi1.oauth;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Created by Sketchy on 06.11.2014.
 */
public class MyOAuthClient {
	private String clientId;
	private String clientSecret;
	
	private String tokenUri;
	private String apiUri;
	private String redirectUri;
	private String scope;
	
	public MyOAuthClient(String clientId, String clientSecret) {
		super();
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		
		Properties props = PropsHolder.getProperties();
		tokenUri = props.getProperty("token_uri");
		apiUri = props.getProperty("api_uri");
		redirectUri = props.getProperty("redirect_uri");
		scope = props.getProperty("scope");
	}

	public JSONObject sendTokenRequest(String authCode) throws Exception {
		URIBuilder uriBuilder = new URIBuilder(tokenUri);
		HttpPost post = new HttpPost(uriBuilder.build());		 
		post.setHeader("User-Agent", "Mozilla/5.0");
		
		// fill post parameters
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("client_id", clientId));
		urlParameters.add(new BasicNameValuePair("client_secret", clientSecret));
		urlParameters.add(new BasicNameValuePair("redirect_uri", redirectUri));
		urlParameters.add(new BasicNameValuePair("scope", scope));
		urlParameters.add(new BasicNameValuePair("grant_type", "authorization_code"));
		urlParameters.add(new BasicNameValuePair("code", authCode));
		
		// send post request
		post.setEntity(new UrlEncodedFormEntity(urlParameters));
		return sendPost(post);
    }

	public JSONObject getAccountCharacters(JSONObject json) throws Exception{
		URIBuilder uriBuilder = new URIBuilder(apiUri);
		uriBuilder.setParameter("access_token", (String)json.get("access_token"))
            .setParameter("account_id", json.get("accountId").toString());
		HttpPost post = new HttpPost(uriBuilder.build());
		return sendPost(post); 
	}

	private JSONObject sendPost(HttpPost post) throws Exception{
		// Send POST
        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(post);
        
        // Copy content in UTF8
        StringWriter content = new StringWriter();
        IOUtils.copy(response.getEntity().getContent(), content, "UTF-8");
        
        // Make JSON from string
    	JSONParser parser   = new JSONParser();
        JSONObject jsonResp  = (JSONObject) parser.parse(content.toString());
        return jsonResp;
	}
}
