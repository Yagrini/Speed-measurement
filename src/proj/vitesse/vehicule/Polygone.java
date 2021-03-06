package proj.vitesse.vehicule;

import org.opencv.core.Point;

import java.util.ArrayList;
import java.util.List;

public class Polygone {
	private int nombreSommets;
	private List<Point> polygone;
//===========================================================================
// Constructeurs
//===========================================================================
	public Polygone() {
		super();
		this.polygone = new ArrayList<Point>();
	}
	public Polygone(int nombreSommets, List<Point> polygone) {
		super();
		this.nombreSommets = nombreSommets;
		this.polygone = polygone;
	}

//===========================================================================
// Methodes
//===========================================================================
	boolean surSegment(Point p, Point q, Point r)
	{
	    if (q.x <= Math.max(p.x, r.x) && q.x >= Math.min(p.x, r.x) &&
	            q.y <= Math.max(p.y, r.y) && q.y >= Math.min(p.y, r.y))
	        return true;
	    return false;
	}
	int orientation(Point p, Point q, Point r)
	{
	    double val = (q.y - p.y) * (r.x - q.x) -
	              (q.x - p.x) * (r.y - q.y);
	 
	    if (val == 0) return 0;  // colinear
	    return (val > 0)? 1: 2; // clock or counterclock wise
	}
	boolean intersection(Point p1, Point q1, Point p2, Point q2)
	{
	    // Determiner orientations 
	    int o1 = orientation(p1, q1, p2);
	    int o2 = orientation(p1, q1, q2);
	    int o3 = orientation(p2, q2, p1);
	    int o4 = orientation(p2, q2, q1);
	 
	    // cas générals
	    if (o1 != o2 && o3 != o4)
	        return true;
	 
	    // cas spécials
	    // p1, q1 and p2 are colinear and p2 lies on segment p1q1
	    if (o1 == 0 && surSegment(p1, p2, q1)) return true;
	 
	    // p1, q1 and p2 are colinear and q2 lies on segment p1q1
	    if (o2 == 0 && surSegment(p1, q2, q1)) return true;
	 
	    // p2, q2 and p1 are colinear and p1 lies on segment p2q2
	    if (o3 == 0 && surSegment(p2, p1, q2)) return true;
	 
	     // p2, q2 and q1 are colinear and q1 lies on segment p2q2
	    if (o4 == 0 && surSegment(p2, q1, q2)) return true;
	 
	    return false; 
	}
	boolean aLinterieur(Point p)
	{
		Point extreme = new Point(10000, p.y);
		 
	    int count = 0, i = 0;
	    do
	    {
	        int next = (i+1)%nombreSommets;
	 
	        if (intersection(polygone.get(i), polygone.get(next), p, extreme))
	        {
	            if (orientation(polygone.get(i), p, polygone.get(next)) == 0)
	               return surSegment(polygone.get(i), p, polygone.get(next));
	 
	            count++;
	        }
	        i = next;
	    } while (i != 0);
	 
	    return (count%2==1);  
	}

//===========================================================================
// Getters & Setters
//===========================================================================
	/*public int getNombreSommets() {
		return nombreSommets;
	}
	public void setNombreSommets(int nombreSommets) {
		this.nombreSommets = nombreSommets;
	}
	public List<Point> getPolygone() {
		return polygone;
	}
	public void setPolygone(List<Point> polygone) {
		this.polygone = polygone;
	}*/
	public void ajouterSommet(Point sommet) {
		this.nombreSommets++;
		this.polygone.add(sommet);
	}
	public Point getSommet(int indice) {
		return this.polygone.get(indice);
	}
}
