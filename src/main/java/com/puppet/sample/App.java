package com.puppet.sample;

import spark.Request;
import spark.Spark;
import static spark.Spark.before;
import static spark.Spark.get;

public class App {

  public String enMsg() {
    return "Hello World!";
  }

  private static String requestInfoToString(Request request) {
    StringBuilder sb = new StringBuilder();
    sb.append(request.requestMethod());
    sb.append(" " + request.url());
    sb.append(" " + request.body());
    return sb.toString();
  }

  public static void main(String[] args) {

    try {
      Spark.port(Integer.parseInt(System.getProperty("appPort")));
    } catch (Exception e) {
      Spark.port(9999);
    }

    Spark.threadPool(10, 5, 600);

    before((request, response) -> {
      System.out.println(requestInfoToString(request));
    });

    get("/", (request, response) ->
            "Hello!!! My version is 1.0 and I am built from Develop branch on port 9999!"
    );

    get("/hello", (request, response) -> "Hello World from Spark Server!");

    get("/hello-json", (request, response) -> {
      response.type("application/json");
      return "{ \"message\": \"Hello World in JSON!\", \"status\": \"success\" }";
    });
    
    get("/greet", (request, response) -> {
      response.type("application/json");
      String name = request.queryParams("name");
      if (name == null || name.isEmpty()) name = "Guest";
      return "{ \"message\": \"Hello, " + name + "!\" }";
    });
  }
}
