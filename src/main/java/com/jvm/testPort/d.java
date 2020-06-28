package com.jvm.testPort;

import java.io.*;

import java.util.*;

import javax.comm.*;

public class d implements Runnable, SerialPortEventListener {
    static CommPortIdentifier portId;
    
	
	static Enumeration<?> portList;

    InputStream inputStream;
    SerialPort serialPort;
    Thread readThread;

    public static void main(String[] args) {
        portList = CommPortIdentifier.getPortIdentifiers();

        while (portList.hasMoreElements()) {
            portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                 if (portId.getName().equals("COM39")) {
            //                if (portId.getName().equals("/dev/term/a")) {
                 
                 }
            }
        }
    }
    public d() {
        try {
            serialPort = (SerialPort) portId.open("dApp", 2000);
        } catch (PortInUseException e) {System.out.println(e);}
        try {
            inputStream = serialPort.getInputStream();
        } catch (IOException e) {System.out.println(e);}
        try {
            serialPort.addEventListener(this);
        } catch (TooManyListenersException e) {
            System.out.println(e);
        }
        serialPort.notifyOnDataAvailable(true);
        try {
            serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
        } catch (UnsupportedCommOperationException e) {
            System.out.println(e);
        }
        readThread = new Thread(this);
        readThread.start();
    }

    public void run() {
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {System.out.println(e);}
    }
    public void serialEvent(SerialPortEvent event) {
        switch(event.getEventType()) {
            case SerialPortEvent.BI:
            case SerialPortEvent.OE:
            case SerialPortEvent.FE:
            case SerialPortEvent.PE:
            case SerialPortEvent.CD:
            case SerialPortEvent.CTS:
            case SerialPortEvent.DSR:
            case SerialPortEvent.RI:
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
            break;
            case SerialPortEvent.DATA_AVAILABLE:
            byte[] readBuffer = new byte[100];

            try {
                while (inputStream.available() > -1) {
                  
                }
                System.out.print(new String(readBuffer));
            } catch (IOException e) {System.out.println(e);}
            break;
        }
    }
}