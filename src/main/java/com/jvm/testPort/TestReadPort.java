package com.jvm.testPort;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;



import gnu.io.*;

public class TestReadPort {

	private static SerialPort p;
	
	public static void main(String[] args) throws IOException {
		 Enumeration ports = CommPortIdentifier.getPortIdentifiers();
	        System.out.println("start");
	        while(ports.hasMoreElements()) {
	            CommPortIdentifier port = (CommPortIdentifier) ports.nextElement();
	            System.out.println(port.getName() + " " + port.getPortType());
	          
	            switch(port.getPortType()) {
	                case CommPortIdentifier.PORT_PARALLEL:
	                    System.out.println("parell");
	                break;
	                case CommPortIdentifier.PORT_SERIAL:
	                    System.out.println("serial");
	                try {
	                    p = (SerialPort) port.open("core", 1000);
	                    int baudRate = 9600; // 57600bps
	                    p.setSerialPortParams(baudRate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
	                    
	                    
	                    InputStream in = p.getInputStream();
	                    OutputStream out = p.getOutputStream(); 
	                    
	                    
	                } catch (PortInUseException e) {
	                    System.out.println(e.getMessage());
	                } catch (UnsupportedCommOperationException e) {
	                    System.out.println(e.getMessage());
	                }
	                break;
	            }
	        }
	        System.out.println("stop");
	    }
	}
