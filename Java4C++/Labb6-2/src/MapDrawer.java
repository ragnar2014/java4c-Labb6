//Laboration 6- Grafik
//David Nilsson L�fvall
//Dalo1300
//L�rare: Mikael Nilsson / Robert Jonsson
/*MapDrawer �r den klassen som f�r informationen fr�n TrafficHandlern och ritar ut det p� sig sj�lv*/

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.Timer;

public class MapDrawer extends JPanel implements ActionListener{
	//S�tter storleken p� panelen genom final ints f�r d� skulle man i framtiden
	//kunna anv�nda dessa som parameter kontainers s� man kan v�lja hur stort f�nstret
	//ska vara vid varje k�rtillf�lle
	final private int width= 600;
	final private int height= 450;
	
	double maxX, maxY, minX, minY;
	private Vector<Double> maxAndMin;
	private Vector<Point2D.Double> mapPoints;
	private Vector<Line2D.Double> lines;
	private Vector <Car> cars;
	private TrafficHandler handler;
	private Timer timer;
	private double newY, newY2;
	
	private Vector<StreetHolder> streets;

	
	public MapDrawer(){
		super(true);
		timer= new Timer(3,this);
		handler=new TrafficHandler();
		maxAndMin= new Vector();
		lines= new Vector();
		handler.getStreets();
		handler.getCars();
		
		//F�ngar bilar och gator
		mapPoints= handler.returnStreets();
		cars= handler.returnCars();
		
        setPreferredSize(new Dimension(600, 450));

        minX= handler.getMaxMin(0);
		maxX= handler.getMaxMin(1);
		minY= handler.getMaxMin(2);
		maxY= handler.getMaxMin(3);
	
	    timer.start();
		
	}
	
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);	
	    Graphics2D g2 = (Graphics2D)g;
	    streets	= handler.returnStreets();
	    
	    //G�r genom varje gata
	    for(int i=0; i<streets.size();i++)
	    {
	    	//Lagrar en gatas vektor med koordinater
	    	Vector <Point2D.Double> points= streets.get(i).getPoints();
	    	
	    	//Loopar genom vektorn f�r att kunna rita ut koordinaterna f�r en gata
	    	for(int j=0; j+1<points.size();j++ )
	    	{	Point2D.Double p1 = null, p2= null;
	        	Line2D.Double newLine=null;
	        	
	        	
	        	//Inverterar y-axeln d� 0 p� y-axeln b�rjar l�ngst upp till v�nster ist�llet f�r l�ngst ner till v�nster
	        	//H�r skalar jag �ven om koordinaterna f�r att passa panelstorleken
	    		double getY= (points.get(j).getY()-minY)/(maxY-minY)*(height);
	    		newY= (height-getY);
	    		double getY2= (points.get(j+1).getY()-minY)/(maxY-minY)*(height);
	    		newY2= (height-getY2);
	    		
	    		
	    		//Skalar om x-koordinaterna f�r att passa panelstorleken
	    		p1= new Point2D.Double(((points.get(j).getX()-minX)/(maxX-minX))*(width-5), newY);
			    p2= new Point2D.Double(((points.get(j+1).getX()-minX)/(maxX-minX))*(width-5), newY2);
			    
			    newLine= new Line2D.Double(p1, p2);
			    
			    //Ritar ut linje
			    Stroke stroke = new BasicStroke(5f);
			    g2.setStroke(stroke);
			    g2.draw(newLine);
	    	}
	    }
	    

	    //Ritar ut bilar
	    for(int c=0; c<cars.size(); c++)
		{
	    	//Konverterar och inverterar s�som jag gjorde med gatorna
    		double x= ((cars.get(c).getPos().getX()-minX)/(maxX-minX)*width);
    		double y=(int) ((cars.get(c).getPos().getY()- minY)/(maxY-minY)*(height));
    		double scaleY= (height-y);
    		
    		//Kollar status p� varje bil, olika statusar inneb�r olika geometriska former
    		if(cars.get(c).isParked())
    		{
        		Shape rectalShape= new Rectangle2D.Double(x, scaleY, 7, 7);
        		g2.setColor(cars.get(c).getColor());
        		g2.fill(rectalShape);
        		g2.draw(rectalShape);

    		}
    		else
    		{
    			Shape ovalShape= new Ellipse2D.Double(x, scaleY, 7, 7);
    			g2.setColor(cars.get(c).getColor());
    			g2.fill(ovalShape);
    			g2.draw(ovalShape);
    		}
    		
			
		}
	    
	}


	@Override
	//Action som triggas efter timern och kallar repaint som k�r paintComponent
	public void actionPerformed(ActionEvent arg0) {
		handler.updateCars();
		repaint();

		
	}
	
}
