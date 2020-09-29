package ca.utoronto.utm.mcs;

import static org.neo4j.driver.Values.parameters;
import java.io.OutputStream;
import java.util.List;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.Value;


public class Neo4jDatabase {
  
  private Driver driver;
  private String uriDb;
  
  public Neo4jDatabase() {
    uriDb = "bolt://localhost:7687";
    driver = GraphDatabase.driver(uriDb, AuthTokens.basic("neo4j","1234"));
  }
  
  public int insertActor(String name, String id) {
    try(Session session = driver.session()){
      session.writeTransaction(tx -> tx.run("MERGE (a:actor {Name: $x, id: $y})"
          , parameters("x", name, "y", id)));
      session.close();
      return 0;
    }
    catch(Exception e){
      return 1;
    }
  }
  
  public int insertMovie(String name, String id) {
    try(Session session = driver.session()){
      session.writeTransaction(tx -> tx.run("MERGE (a:movie {Name: $x, id: $y})"
          , parameters("x", name, "y", id)));
      session.close();
      return 0;
    }
    catch(Exception e){
      return 1;
    }
  }
  
  public int insertRelationship(String actorID, String movieID) { 
    try(Session session = driver.session()){
      

      Result result = session.writeTransaction(tx -> tx.run("MATCH(j:actor{id:$x}) RETURN j", parameters("x", 60)));

      if(result.hasNext()) {
        System.out.println("hi");
      }
      else {
        System.out.println("Hello");
      }
      return 0;
//      try(Session session2 = driver.session()){
//        session2.writeTransaction(tx -> tx.run("MATCH (a:actor {id:$x}),"
//            + "(b:movie {id:$y})\n" + 
//             "MERGE (a)-[r:WORK]->(b)\n" + 
//             "RETURN r", parameters("x", actorID, "y", movieID)));
//        return 0; 
//      }
//      catch(Exception e) {
//        return 1;
//      }
    }
    catch(Exception e){
      return 1;
    }
  }
}
