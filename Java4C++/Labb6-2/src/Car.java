//Laboration 6- Grafik
//David Nilsson L�fvall
//Dalo1300
//L�rare: Mikael Nilsson / Robert Jonsson
/*DEtta �r en klass f�r att lagra typen Car som �r en typ av Vehicle*/

import java.awt.Color;
import java.awt.Shape;

import traffic.common.*;

public class Car implements Comparable{
		
	private Vehicle vehicleToCar;
	String name;
	private java.awt.geom.Point2D.Double position;
	private Color iColor;
	private Boolean parked;
	
	public Car(Vehicle car){
		vehicleToCar= car;
		name= car.getId();
		position= car.getPosition();
		iColor= car.getColor();
		parked= car.isParked();
		
	}
	
	public java.awt.geom.Point2D.Double getPos(){
		return position;
	}
	public void setParked(Boolean setPark){
		parked= setPark;
	}
	
	public String getName(){
		return name;
	}
	public Boolean isParked(){
		return parked;
		
	}
	public Color getColor(){
		return iColor;
	}
	
	public void setNewPos(java.awt.geom.Point2D.Double aPosition){
		position= aPosition;
	}

	@Override
	//Returnerar 0 om det �r samam och 1 om de inte �r samma
	public int compareTo(Object arg0) {
		Vehicle n1= (Vehicle) arg0;
		
		return this.getName().compareTo(n1.getId());
		
		// TODO Auto-generated method stub
	}

}
