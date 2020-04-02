package com.github.martinfrank.garage.restserver;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

import java.util.Timer;
import java.util.TimerTask;

public class DistanceMeasure {

    private final GpioController gpio;
    private final GpioPinDigitalOutput burstPin;
    private final GpioPinDigitalInput echoPin;
    private final Timer timer;
    private long risingTime;


    public DistanceMeasure(GpioController gpio, Pin burstPin, Pin echoPin) {
        this.gpio = gpio;
        this.burstPin = gpio.provisionDigitalOutputPin(burstPin,   // PIN NUMBER
                "BurstPin",           // PIN FRIENDLY NAME (optional)
                PinState.LOW);      // PIN STARTUP STATE (optional)
        this.echoPin = gpio.provisionDigitalInputPin(echoPin,   // PIN NUMBER
                "EchoPin");      // PIN STARTUP STATE (optional)
        timer = new Timer(true);
        this.echoPin.addListener(new GpioPinListenerDigital() {

            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                System.out.println("state:" + event.getEdge() + " int:" + event.getEdge().getValue());
                if (event.getEdge().getValue() == 2) { //rising
                    risingTime = System.nanoTime();
                }
                if (event.getEdge().getValue() == 3) { //falling
                    long endTime = System.nanoTime(); // Store the echo pin HIGH end time to calculate ECHO pin HIGH time.
                    double delta = (endTime - risingTime);
                    System.out.println("distance: " + (delta / 582000D));
                }
            }
        });

        //FIXME maybe start externally
        startMeasuring(1000);
    }

    public void startMeasuring(long pollInternalInMillis) {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
//                nanoTime = System.nanoTime();
//                System.out.println("sending burst    nanotime: "+nanoTime);
//                burstPin.pulse(1);
//                burstPin.pulse(10, TimeUnit.MICROSECONDS);
                try {
                    burstPin.high(); // Make trigger pin HIGH
                    Thread.sleep((long) 0.01);// Delay for 10 microseconds
                    burstPin.low(); //Make trigger pin LOW
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

//                try {
//                    burstPin.high(); // Make trigger pin HIGH
//                    Thread.sleep((long) 0.01);// Delay for 10 microseconds
//                    burstPin.low(); //Make trigger pin LOW
//
//                    while (echoPin.isLow()) { //Wait until the ECHO pin gets HIGH
//
//                    }
//                    long startTime = System.nanoTime(); // Store the current time to calculate ECHO pin HIGH time.
//                    int amout = 0;
//                    while (echoPin.isHigh()) {
////                        Thread.sleep(0,10);
////                        amout = amout + 1;
////                        if (amout > 1000){
////                            throw new InterruptedException("error during reading");
////                        }
//                    }
////                    System.out.println("amout:"+amout);
//                    long endTime = System.nanoTime(); // Store the echo pin HIGH end time to calculate ECHO pin HIGH time.
//                    double delta =(endTime - startTime);
//                    System.out.println("delta = "+delta);
//
//                    System.out.println("Distance :" + ((endTime - startTime) / 582000D) + " cm"); //Printing out the distance in cm
//
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        }, 0, pollInternalInMillis);
    }

    public long getLastMeasuredDistanceInMillimeters() {
        return 0;
    }
}