package com.jvm.testPort;

import java.io.IOException;
import java.io.InputStream;
import com.fazecast.jSerialComm.SerialPort;

public class Test5 {
  static int x = 0;
  static SerialPort s1;
	
	public static void main(String[] args) throws IOException {
		 String val = "";
    SerialPort[] listPort = SerialPort.getCommPorts();
    for(SerialPort p : listPort) {
    	System.out.println("nom du port : " + p.getSystemPortName());
    	s1 = SerialPort.getCommPort(p.getSystemPortName());
    	if(s1.openPort()) {
    		System.out.println("maftouuuu7");
    	}else {
    		System.out.println("failed to open port");
    	}
    	    }
    if(s1 != null) {
	s1.setBaudRate(9600);
	InputStream in = s1.getInputStream();
	StringBuilder sb = new StringBuilder();
	for(int i =0 , x=0 ;true ; i++) {
		String str="" + (char)in.read();
		sb.append(str);
	    x++;
	if(x>12) {
	val = sb.substring(1, 11);
	System.out.println(val);	
	x =0 ;
	sb = new StringBuilder();
	}
	
	}
	}
    
    /*
     * public class Test5 {
  static int x = 0;
  static SerialPort s1;
	
	public static void main(String[] args) throws IOException {
		 String val = "";
    SerialPort[] listPort = SerialPort.getCommPorts();
    for(SerialPort p : listPort) {
    	System.out.println("nom du port : " + p.getSystemPortName());
    	s1 = SerialPort.getCommPort(p.getSystemPortName());
    	if(s1.openPort()) {
    		System.out.println("maftouuuu7");
    	}else {
    		System.out.println("failed to open port");
    	}
    	    }
    if(s1 != null) {
	s1.setBaudRate(9600);
	InputStream in = s1.getInputStream();
	StringBuilder sb = new StringBuilder();
	for(int i =0 , x=0 ;true ; i++) {
		String str="" + (char)in.read();
		//System.out.println(str);
		sb.append(str);
	x++;
	if(x>12) {
		val = sb.substring(1, 11);
	System.out.println(val);	
	x =0 ;
	sb = new StringBuilder();
	}
	
	}
	}
	}
	

}

     */
     
	}
	

}
