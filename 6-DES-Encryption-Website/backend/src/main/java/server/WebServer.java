/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package server;

/**
 *
 * @author luish
 */
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import controller.CryptoController;
import exception.*;
import java.util.HashMap;
import java.util.Map;
import transaction.*;


public class WebServer {
    
    private static final Logger LOGGER = Logger.getLogger(WebServer.class.getName());

    private static final String STATUS_ENDPOINT = "/api/status";
    private static final String BPMDES_ENDPOINT = "/api/bmpdes";

    private final int port;
    private HttpServer server;
    private Gson gson;
    private CryptoController cryptoController;

    public static void main(String[] args) {
        int serverPort = 8080;
        if (args.length == 1) {
            serverPort = Integer.parseInt(args[0]);
        }
        
        WebServer webServer = new WebServer(serverPort);
        webServer.startServer();
    }

    public WebServer(int port) {
        this.port = port;
        this.cryptoController = new CryptoController();
        this.gson = new Gson();
    }

    public void startServer() {
        try {
            this.server = HttpServer.create(new InetSocketAddress(port), 0);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Server could not start at port " + port, e);
            return;
        }

        server.createContext(STATUS_ENDPOINT, this::handleStatusCheckRequest);
        server.createContext(BPMDES_ENDPOINT, this::handleBpmdesRequest);

        server.setExecutor(Executors.newFixedThreadPool(8));
        server.start();
        
        LOGGER.info(" Server running succesfully at port " + port);
    }

    private void handleStatusCheckRequest(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String remoteIp = exchange.getRemoteAddress().getAddress().getHostAddress();
        
        if (method.equalsIgnoreCase("OPTIONS")) {
            LOGGER.fine("Preflight OPTIONS received in " + STATUS_ENDPOINT + " from " + remoteIp);
            addCORSHeaders(exchange);
            exchange.sendResponseHeaders(204, -1);
            exchange.close();
            return;
        }
        
        if (!method.equalsIgnoreCase("GET")) {
            LOGGER.warning("Method not allowed [" + method + "] in " + STATUS_ENDPOINT);
            exchange.sendResponseHeaders(405, -1);
            exchange.close();
            return;
        }

        LOGGER.info("Status check requested from " + remoteIp);
        String responseMessage = "WebServer is Alive\n";
        sendResponse(responseMessage.getBytes(), exchange);
    }
    
    private void handleBpmdesRequest(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String remoteIp = exchange.getRemoteAddress().getAddress().getHostAddress();
        
        try {
            // Manejando CORS OPTIONS Preflight
            if (method.equalsIgnoreCase("OPTIONS")) {
                LOGGER.fine("Preflight OPTIONS received in " + BPMDES_ENDPOINT + " from " + remoteIp);
                addCORSHeaders(exchange);
                exchange.sendResponseHeaders(204, -1);
                exchange.close();
                return;
            }
            
            if (!method.equalsIgnoreCase("POST")) {
                LOGGER.warning("Method not allowed [" + method + "] in " + BPMDES_ENDPOINT + " from " + remoteIp);
                exchange.sendResponseHeaders(405, -1);
                exchange.close();
                return;
            }

            // Monitorear la llegada del archivo
            LOGGER.info("Processing request received from IP: " + remoteIp);

            byte[] requestBytes = exchange.getRequestBody().readAllBytes();
            String body = new String(requestBytes).trim();

            DESRequest desRequest = gson.fromJson(body, DESRequest.class);

            // log descriptivo para ver que orden mandó el Frontend
            LOGGER.info(String.format(" Executing action: [%s] | Mode: [%s] | File: [%s]", 
                    desRequest.getAction(), desRequest.getMode(), desRequest.getFileName()));

            // Ejecución del algoritmo
            DESResponse desResponse = cryptoController.encrypt_decrypt(desRequest);
            
            // Éxito completo
            LOGGER.info("Operation completed Successfully. Sending response...");
            sendJsonResponse(exchange, 200, gson.toJson(desResponse));
            
        } catch (CryptoException e) {
            
            LOGGER.warning("Validation error: " + e.getMessage());
            sendError(exchange, 400, e.getMessage());
        } catch (Exception e) {
            // Error fatal del servidor
            LOGGER.log(Level.SEVERE, "Critical error in the server: " + e.getMessage(), e);
            sendError(exchange, 500, "Internal Server error: " + e.getMessage());
        }
    }

    private void sendResponse(byte[] responseBytes, HttpExchange exchange) throws IOException {
        addCORSHeaders(exchange);
        exchange.sendResponseHeaders(200, responseBytes.length);
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(responseBytes);
        outputStream.flush();
        outputStream.close();
        exchange.close();
    }
    
    private void sendError(HttpExchange exchange, int statusCode, String mensaje) throws IOException {
        // Aseguramos que las cabeceras CORS estén inyectadas desde el inicio
        addCORSHeaders(exchange);
        exchange.getResponseHeaders().set("Content-Type", "application/json");

        // Construimos el JSON de error
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", mensaje);
        byte[] responseBytes = gson.toJson(errorMap).getBytes();

        // Enviamos el código (400 o 500) y los bytes
        exchange.sendResponseHeaders(statusCode, responseBytes.length);

        try (OutputStream outputStream = exchange.getResponseBody()) {
            outputStream.write(responseBytes);
            outputStream.flush();
        }
        exchange.close();
    }

    private void sendJsonResponse(HttpExchange exchange, int statusCode, String jsonResponse) throws IOException {
        byte[] responseBytes = jsonResponse.getBytes();
        addCORSHeaders(exchange);
        exchange.getResponseHeaders().set("Content-Type", "application/json");

        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        try (OutputStream outputStream = exchange.getResponseBody()) {
            outputStream.write(responseBytes);
            outputStream.flush();
        }
        exchange.close();
    }
    
    private void addCORSHeaders(HttpExchange exchange) {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type, X-Test, X-Debug");
    }
}
