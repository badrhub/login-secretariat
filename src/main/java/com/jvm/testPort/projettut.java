package com.jvm.testPort;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import gnu.io.RXTXCommDriver;

public class projettut{
  
	public projettut(){
       super();
    }
//connexion au port:

void connect ( String portName ) throws Exception{
CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);	
//générer une liste des ports disponibles. Elle choisit ensuite un port dans la liste et appelle
// CommPortIdentifier.open pour créer un objet CommPort qui est casté ensuite en SerialPort. 

  if ( portIdentifier.isCurrentlyOwned() ) {
      // si le port est deja connecté
  System.out.println("Error: Port is currently in use");
   }else
   {
CommPort commPort = portIdentifier.open(this.getClass().getName(),0);

if ( commPort instanceof SerialPort ) {

//si le port est présent mais pas connecté
SerialPort serialPort = (SerialPort) commPort;
serialPort.setSerialPortParams(9600,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
//serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
InputStream in = serialPort.getInputStream();
OutputStream out = serialPort.getOutputStream();

(new Thread(new SerialReader(in))).start();


}
else {
// si le port n'est pas présent
  System.out.println("Error: Only serial ports are handled by this example.");
  }
} 
}

/** */
public static class SerialReader implements Runnable  {

InputStream in;

public SerialReader ( InputStream in ){
this.in = in;
}

public void run (){
byte[] buffer = new byte[1024];
int len = -1;

try{
	StringBuilder s = new StringBuilder();
	while ( ( len = this.in.read(buffer)) > -1 ){
		char c = (char)len;
         	String ss  = new String(buffer,0,len);
         	s.append(ss);
			System.out.print(c);
	
          }
     }catch ( IOException e ) {
    e.printStackTrace();
  } 
}
}


public static void main ( String[] args )
{
try
{
(new projettut()).connect("COM39");
}
catch ( Exception e )
{
// TODO Auto-generated catch block
e.printStackTrace();
}
}
}
