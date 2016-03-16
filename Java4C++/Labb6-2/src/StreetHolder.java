//Laboration 6- Grafik
//David Nilsson Löfvall
//Dalo1300
//Lärare: Mikael Nilsson / Robert Jonsson
/*Detta är en klass för att lagra typen Street. Jag fick göra en sådan klass för att kunna göra en lämplig jämförelse
 * mellan olika Streets. Jag hade problem med detta med enbart klassen Street. Varje StreetHolder har en vector med 
 * koordinater för att rita ut gatan samt namn och färg */
import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.Vector;


import traffic.common.*;


public class StreetHolder implements Comparable {

	private Street newStreet;
	String name;
	Vector<Point2D.Double> streetPoints= null;
	Color color= null;
	
	public StreetHolder(Street aStreet){
		newStreet= aStreet;
		name= aStreet.getName();
		streetPoints= aStreet.getPoints();
	}
	
	public String getName(){
		return name;
	}
	
	public Vector getPoints(){
		return streetPoints;
	}
	@Override
	public int compareTo(Object o) {
		Street tempStreet= (Street) o;
		// TODO Auto-generated method stub
		return this.getName().compareTo(tempStreet.getName());
	}

}
