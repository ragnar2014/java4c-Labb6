//Laboration 6- Grafik
//David Nilsson L�fvall
//Dalo1300
//L�rare: Mikael Nilsson / Robert Jonsson
/*Detta �r en klass f�r att lagra typen Street. Jag fick g�ra en s�dan klass f�r att kunna g�ra en l�mplig j�mf�relse
 * mellan olika Streets. Jag hade problem med detta med enbart klassen Street. Varje StreetHolder har en vector med 
 * koordinater f�r att rita ut gatan samt namn och f�rg */
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
