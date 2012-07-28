/*
 * LineFollowerP.java        1.0 27/07/2012
 *
 * Copyright (c) 2012 www.lejosconlego.com
 * 
 */
package com.lejosconlego.linefollower;
import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;

/**
*  
* Line Follower Proporcional básico para Lego Mindstorms NXT 2.0
* Detalles y consideraciones sobre la implementación en:
* www.lejosconlego.com
* 
* @version 1.0 27/07/2012
* @author Germán Kern
*
*/
public class LineFollowerP {
	public static void main (String[] aArg) throws Exception {

		float linea = 0;		// lectura del sensor de luz sobre la línea
		float suelo = 0;		// lectura del sensor de luz fuera de la línea
		float velMax=0;	// velocidad (grados por segundo) máxima de los motores
		float kp = 0;		// constante de proporcionalidad
		float ajuste;			
		float error;		
		float offset;		
		
		ColorSensor lSensor = new ColorSensor(SensorPort.S3);
		lSensor.setFloodlight(true);
				
		/* Parámetros a determinar antes de comenzar: */
		linea = 600;		
		suelo = 240;		
		velMax=50;	
		offset  = (linea + suelo)/2;
		kp = velMax/(suelo-offset);
	
		LCD.drawString("Presione ENTER", 0, 1);
		LCD.drawString("para comenzar", 0, 2);

		Button.ENTER.waitForPressAndRelease();

		LCD.drawString("Presione ESCAPE", 0, 0);
		LCD.drawString("para terminar ", 0, 1);

		while (!Button.ESCAPE.isDown()){
			error = lSensor.getRawLightValue() - offset;
			ajuste = kp * error;
			Motor.B.setSpeed(Math.round(velMax + ajuste));
			Motor.C.setSpeed(Math.round(velMax - ajuste));
			Motor.B.forward();
			Motor.C.forward();	
		}
	}
}
