//Laboration 6- Grafik
//David Nilsson Löfvall
//Dalo1300
//Lärare: Mikael Nilsson / Robert Jonsson
/*TrafficHandlern har som uppgift att fånga och lagra Street och Vehicle med sina publika funktioner. Den har även som uppgift att
 * kapsla in de variabler som håller dessa objekt*/

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.Timer;

import com.sun.xml.internal.ws.util.StringUtils;

import traffic.common.*;
import traffic.common.MCastSocket;

public class TrafficHandler {
	
	private MCastSocket conn=null;
	private MCastSocket conn_cars= null;
	private Vector<Car> cars=null;
	private Vector<StreetHolder> streets= null;
	private Vector<Point2D.Double> cars_pos;
	private Vector<Point2D.Double> streetPoints=null;
	private Vector<Double> tempPoints;
	private Vector<Double> allX,allY=null;
	private Vector <Double> xtremes;
    private double maxX, minX, maxY, minY;
    private Boolean run= true;
    private Boolean runCars= true;
    

	//private double maxX, maxY, minX, minY;
	
	public TrafficHandler(){
		conn_cars= new MCastSocket("232.0.0.10", 12000);
		conn= new MCastSocket("232.0.0.10", 11000);
		cars= new Vector();
		streets= new Vector();
		streetPoints= new Vector();
		allX= new Vector();
		allY= new Vector();
		xtremes= new Vector();
		cars_pos= new Vector();
		

	}
	public boolean isInteger( String input )
	{
	   try
	   {
	      Integer.parseInt( input );
	      return true;
	   }
	   catch( Exception e)
	   {
	      return false;
	   }
	}
	//Fånga gator
public void getStreets(){
		
	
	do{
		
		Street newStreet= (Street) conn.receive();
		StreetHolder newHolder= new StreetHolder(newStreet);
		Boolean found = null;
		
		//Kollar om det är stora listan som har numeriska namn då ena listan med gator endast har numeriska namn
		if(isInteger(newHolder.getName()))
		{
			if(streets.size()==330)
			{
				try {
					//Avslutar anslutning vilket borde knäcka while loopen
					conn.leaveGroup();
					run=false;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		else
		{
			if(streets.size()==33)
			{
				try {
					//Avslutar anslutning vilket borde knäcka while loopen
					conn.leaveGroup();
					run=false;
					} 
				catch (IOException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
		
		
		if(streets.size()==0)
		{
			streets.add(newHolder);
			//Lagra alla Point2D i egen vector
			streetPoints.addAll(newHolder.getPoints());
		}
		
		else
		{
			/*Går genom gator som är inlagda och jämför med nya objektet
			 * om gatan hittas läggs den inte till om inte så läggs den till som ny*/
			for(int i=0; i<streets.size();i++)
			{
				if(streets.get(i).getName().equals(newStreet.getName()))
				{
					found= true;
					System.out.println("Found duplicate");
					break;
				}
				
				else
				{
					found= false;
				}
				
				
			}
			
			if(!found)
			{
				streets.add(new StreetHolder(newStreet));
				streetPoints.addAll(newHolder.getPoints());
				System.out.println(newStreet.getName());
				
			}
			
		}
		
	}
		
	while(run);
	
	


	}
	//Fånga bilar	
public void getCars(){
		do{
			Vehicle newVehicle= (Vehicle) conn_cars.receive();;
			Car newCar= new Car(newVehicle);
			Boolean found = false;
		
		if(cars.size()==0)
		{
			cars.add(newCar);
			cars_pos.add(newCar.getPos());
		}
		
		else
		{
			for(int i=0; i<cars.size();i++)
			{
				//Finns bilen redan i kontainern så läggs den inte till
				if(cars.get(i).getName().equals(newCar.getName()))
				{
					found=true;
					break;
				}	
			}
			
			if(!found)
			{
				cars.add(newCar);
				cars_pos.add(newCar.getPos());

			}
			
			if(streets.size()==33 && cars.size()==6)
			{	
					runCars=false;
				
			}
			
			if(streets.size()==330 && cars.size()==12)
			{
					runCars=false;
				
			}
		}
		
		}while(runCars);
		
		
	}
	public Vector returnStreets(){
		return streets;
	}
	
	public Vector returnCars(){
		return cars;
	}
	
	
	//Hitta max och min
	public double getMaxMin(int pos){
		Vector vectorX= new Vector();
		Vector vectorY= new Vector();
		allX= new Vector();
		allY= new Vector();
		for(int i=0;i<streets.size();i++)
		{
			streetPoints.addAll(streets.get(i).getPoints());         

		}
		   
	    for(int a=0; a<streetPoints.size();a++)
	    {
	    	allX.add(streetPoints.get(a).getX());
	    	allY.add(streetPoints.get(a).getY());
	    }
	    
		Collections.sort(allX);
	    Collections.sort(allY);
	    maxX= (double) Collections.max(allX);
    	maxY= (double) Collections.max(allY);
    	minX= (double) Collections.min(allX);
    	minY= (double) Collections.min(allY);
		
    	//Lagrar dem på bestämda platser
        xtremes.add(minX);
        xtremes.add(maxX);
        xtremes.add(minY);
        xtremes.add(maxY);
        

        return xtremes.elementAt(pos);

	}
	
	//Uppdaterar bilarnas position
	public void updateCars(){
	
		Vehicle newVehicle= (Vehicle) conn_cars.receive();;
		Car newCar= new Car(newVehicle);
		
		for(int i=0; i<cars.size();i++)
		{
			if(cars.get(i).getName().equals(newCar.getName()))
			{
				//Om bilen hittas så tas nuvarande bilen bort, läggs till på nytt via MCasSocketen och ny position sparas
				cars.remove(i);
				cars.add(i, newCar);
				cars.get(i).setNewPos(newCar.getPos());
				
			}
		}
		
		for(int i=0; i<cars.size();i++)
		{
			System.out.println(cars.get(i).getName());
		}
		
	}
	
}
