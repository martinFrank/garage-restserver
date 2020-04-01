package com.github.martinfrank.garage.restserver;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class GarageServer {

    private final HttpServer server;
    private final GpioController gpio;
    private final WebServerRequestHandler webServerRequestHandler;


    public static void main(String[] args) {
        try {
            System.out.println("main...");
            GarageServer server = new GarageServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public GarageServer() throws IOException {


        gpio = GpioFactory.getInstance();
        System.out.println("gpio:" + gpio);
        webServerRequestHandler = new WebServerRequestHandler(gpio);
        System.out.println("webServerRequestHandler:" + webServerRequestHandler);
        server = HttpServer.create(new InetSocketAddress(8000), 0);
        System.out.println("server:" + server);
        server.createContext("/", webServerRequestHandler);
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("server.start()");
//        //new DistanceMeasure(gpio, RaspiPin.GPIO_27, RaspiPin.GPIO_28);
    }


}
