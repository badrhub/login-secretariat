package com.jvm.testPort;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier; 
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent; 
import gnu.io.SerialPortEventListener; 
import java.util.Enumeration;


public class Test4 implements SerialPortEventListener {
	SerialPort serialPort;
      
	private static final String PORT_NAMES[] = { 
			"COM39"// Windows
	};
	
	private BufferedReader input;
	private OutputStream output;
	private static final int TIME_OUT = 10000;
	private static final int DATA_RATE = 9600;

	public void initialize() {
                
		CommPortIdentifier portId = null;
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			System.out.println(currPortId.getName());
			for (String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)) {
					portId = currPortId;
					System.out.println(portId);
					break;
				}
			}
		}
		
		if (portId == null) {
			System.out.println("Could not find COM port.");
			return;
		}

		try {
			serialPort = (SerialPort) portId.open(this.getClass().getName(),
					TIME_OUT);
				serialPort.setSerialPortParams(DATA_RATE,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);

			input = new BufferedReader(new InputStreamReader(serialPort.getInputStream() ,"UTF-8") );
			output = serialPort.getOutputStream();

			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		System.out.println(" *********  " + oEvent);
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			System.out.println(" *********  " + oEvent.getEventType());
			String inputLine = "";
			
			 StringBuilder result = new StringBuilder();
			 char[] buf = new char[1024];

			try {
				   int r = 0;
			        while ((r = input.read(buf)) != -1) {
			            result.append(buf, 0, r);
			        }
			
					System.out.println("llllllllllllllllll");
			   
			    System.out.println(result);
				
				
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}
		
	}

	public static void main(String[] args) throws Exception {
		Test4 main = new Test4();
		main.initialize();
		Thread t=new Thread() {
			public void run() {
					try {Thread.sleep(1000000);} catch (InterruptedException ie) {}
			}
		};
		t.start();
		System.out.println("Started");
	}
}