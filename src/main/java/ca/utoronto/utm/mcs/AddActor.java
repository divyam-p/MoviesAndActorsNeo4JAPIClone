package ca.utoronto.utm.mcs;

import java.io.IOException;
import java.sql.Driver;
import java.io.OutputStream;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ca.utoronto.utm.mcs.Utils;
import org.json.JSONException;
import org.json.JSONObject;

public class AddActor implements HttpHandler
{
  public void handle(HttpExchange r) {
    try {
      if(r.getRequestMethod().equals("PUT")) {
        handlePut(r);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  
  public void handlePut(HttpExchange r) throws IOException, JSONException{
    String body = Utils.convert(r.getRequestBody());
    JSONObject deserialized = new JSONObject(body);
    
    String actor = "";
    String actorID = "";
    if(deserialized.has("name")) {
      actor = deserialized.getString("name"); 
    }
    if(deserialized.has("actorId")) {
      actorID = deserialized.getString("actorId");
    } 
    if(!deserialized.has("name") || !deserialized.has("actorId")) {
      r.sendResponseHeaders(400, -1);
    }
    else {
      Neo4jDatabase neo = new Neo4jDatabase();
      int neoReturn = neo.insertActor(actor, actorID);
      if(neoReturn == 1) {
        r.sendResponseHeaders(500, -1);
      }
      else if(neoReturn == 2) { 
        r.sendResponseHeaders(400, -1);
      }
      else {
        r.sendResponseHeaders(200, -1);
      }
    }
    
  }
}
