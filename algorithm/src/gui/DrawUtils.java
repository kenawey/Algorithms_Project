package gui;

import models.Edge;
import models.Node;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DrawUtils {
    private Graphics2D g;
    private static int radius = 30;

    public DrawUtils(Graphics2D graphics2D){
        g = graphics2D;
    }

    public static boolean isWithinBounds(MouseEvent e, Point p) {
        int x = e.getX();
        int y = e.getY();

        int boundX = (int) p.getX();
        int boundY = (int) p.getY();

        return (x <= boundX + radius && x >= boundX - radius) && (y <= boundY + radius && y >= boundY - radius);
    }

    public static boolean isOverlapping(MouseEvent e, Point p) {
        int x = e.getX();
        int y = e.getY();

        int boundX = (int) p.getX();
        int boundY = (int) p.getY();

        return (x <= boundX + 2.5*radius && x >= boundX - 2.5*radius) && (y <= boundY + 2.5*radius && y >= boundY - 2.5*radius);
    }

    public static boolean isOnEdge(MouseEvent e, Edge edge) {

        int dist = distToSegment( e.getPoint(),
                                  edge.getNodeOne().getCoord(),
                                  edge.getNodeTwo().getCoord() );
        if (dist<6)
            return true;
        return false;
    }

    public static Color parseColor(String colorStr) {
        return new Color(
                Integer.valueOf(colorStr.substring(1, 3), 16),
                Integer.valueOf(colorStr.substring(3, 5), 16),
                Integer.valueOf(colorStr.substring(5, 7), 16));
    }

    public void drawWeight(Edge edge) {
        Point from = edge.getNodeOne().getCoord();
        Point to = edge.getNodeTwo().getCoord();
        int x = (from.x + to.x)/2;
        int y = (from.y + to.y)/2;

        int rad = radius/2;
        g.fillOval(x-rad, y-rad, 2*rad, 2*rad);
        drawWeightText(String.valueOf(edge.getWeight()), x, y);
    }

    public void drawPath(List<Node> path) {
        List<Edge> edges = new ArrayList<>();
        for(int i = 0; i < path.size()-1; i++) {
            
                edges.add(new Edge(path.get(i), path.get(i+1)));
                
            
        }

        for(Edge edge : edges) {
            drawPath(edge);
        }
    }

    public void drawPath(Edge edge) {
        g.setColor(parseColor("#00BCD4"));
        drawBoldEdge(edge);
    }

    public void drawHoveredEdge(Edge edge) {
        g.setColor(parseColor("#E1BEE7"));
        drawBoldEdge(edge);
    }

    private void drawBoldEdge(Edge edge){
        Point from = edge.getNodeOne().getCoord();
        Point to = edge.getNodeTwo().getCoord();
        //g.setColor(Color.MAGENTA);
        g.setStroke(new BasicStroke(15));
        g.drawLine(from.x, from.y, to.x, to.y);
        int x = (from.x + to.x)/2;
        int y = (from.y + to.y)/2;

        int rad = 13;
        g.fillOval(x-rad, y-rad, 2*rad, 2*rad);
    }
    

    public void drawEdge(Edge edge) {
        g.setColor(parseColor("#555555"));
        drawBaseEdge(edge);
        drawWeight(edge);
    }

    private void drawBaseEdge(Edge edge){
        
        Point from = edge.getNodeOne().getCoord();
        Point to = edge.getNodeTwo().getCoord();
        if (edge.getT()=="Undirected"){
        g.setStroke(new BasicStroke(3));
        g.drawLine(from.x, from.y, to.x, to.y);}
        else {
            drawWeight(edge);
        Node u=edge.getNodeOne(),v=edge.getNodeTwo();
        g.setStroke(new BasicStroke(3));
        g.setColor(parseColor("#555555"));
        g.drawLine(u.getX(), u.getY(), v.getX(), v.getY());
        int r = 25; //thickness of vertex
	int s = 10; //thickness of line
	double ax = u.getX()+0.5;
	double ay = u.getY()+0.5;
	double length=Math.sqrt((v.getX()-u.getX())*(v.getX()-u.getX())+(v.getY()-u.getY())*(v.getY()-u.getY()));
	//length-=1;
        if (length==0) return;
	double sin = (v.getY()-u.getY())/length;
	double cos = (v.getX()-u.getX())/length ;
	double px = length-(r+1);
	double py = 0;
	double ly = s;
	double lx = length-(r+s+s);
	double rx = lx;
	double ry = -ly;
	double mx = length-(r+s+s/2);
	double my = 0;
	double tx; double ty;
	tx = px*cos-py*sin;
	ty = px*sin+py*cos;
	px = tx; py = ty;
	tx = lx*cos-ly*sin;
	ty = lx*sin+ly*cos;
	lx = tx; ly = ty;
	tx = rx*cos-ry*sin;
	ty = rx*sin+ry*cos;
	rx = tx; ry = ty;
	tx = mx*cos-my*sin;
	ty = mx*sin+my*cos;
	mx = tx; my = ty;
	px+=ax; py+=ay;
	mx+=ax; my+=ay;
	lx+=ax; ly+=ay;
	rx+=ax; ry+=ay;
	int[] xvals=new int[4];
        int[] yvals=new int[4];
	xvals[0]=(int)px;
	xvals[1]=(int)lx;
	xvals[2]=(int)mx;
	xvals[3]=(int)rx;
	yvals[0]=(int)py;
	yvals[1]=(int)ly;
	yvals[2]=(int)my;
	yvals[3]=(int)ry;
       
	g.fillPolygon(xvals,yvals,4);

        
        
        
        }
    }

    
    /*private void drawBaseEdge(Edge e){
  //g.setColor(parseColor("#555555"));
       
        drawWeight(e);
        Node u=e.getNodeOne(),v=e.getNodeTwo();
        g.setStroke(new BasicStroke(3));
        g.setColor(parseColor("#555555"));
        g.drawLine(u.getX(), u.getY(), v.getX(), v.getY());
        int r = 25; //thickness of vertex
	int s = 10; //thickness of line
	double ax = u.getX()+0.5;
	double ay = u.getY()+0.5;
	double length=Math.sqrt((v.getX()-u.getX())*(v.getX()-u.getX())+(v.getY()-u.getY())*(v.getY()-u.getY()));
	//length-=1;
        if (length==0) return;
	double sin = (v.getY()-u.getY())/length;
	double cos = (v.getX()-u.getX())/length ;
	double px = length-(r+1);
	double py = 0;
	double ly = s;
	double lx = length-(r+s+s);
	double rx = lx;
	double ry = -ly;
	double mx = length-(r+s+s/2);
	double my = 0;
	double tx; double ty;
	tx = px*cos-py*sin;
	ty = px*sin+py*cos;
	px = tx; py = ty;
	tx = lx*cos-ly*sin;
	ty = lx*sin+ly*cos;
	lx = tx; ly = ty;
	tx = rx*cos-ry*sin;
	ty = rx*sin+ry*cos;
	rx = tx; ry = ty;
	tx = mx*cos-my*sin;
	ty = mx*sin+my*cos;
	mx = tx; my = ty;
	px+=ax; py+=ay;
	mx+=ax; my+=ay;
	lx+=ax; ly+=ay;
	rx+=ax; ry+=ay;
	int[] xvals=new int[4];
        int[] yvals=new int[4];
	xvals[0]=(int)px;
	xvals[1]=(int)lx;
	xvals[2]=(int)mx;
	xvals[3]=(int)rx;
	yvals[0]=(int)py;
	yvals[1]=(int)ly;
	yvals[2]=(int)my;
	yvals[3]=(int)ry;
       
	g.fillPolygon(xvals,yvals,4);
      // g.setColor(Color.);
       // drawWeight(e);
        
}*/
    
    public void drawHalo(Node node){
        g.setColor(parseColor("#E91E63"));
        radius+=5;
        g.fillOval(node.getX() - radius, node.getY() - radius, 2 * radius, 2 * radius);
        radius-=5;
    }

    public void drawSourceNode(Node node){
        g.setColor(parseColor("#00BCD4"));
        g.fillOval(node.getX() - radius, node.getY() - radius, 2 * radius, 2 * radius);

        radius-=5;
        g.setColor(parseColor("#B2EBF2"));
        g.fillOval(node.getX() - radius, node.getY() - radius, 2 * radius, 2 * radius);

        radius+=5;
        g.setColor(parseColor("#00BCD4"));
        drawCentreText(String.valueOf(node.getId()), node.getX(), node.getY());
    }

    public void drawDestinationNode(Node node){
        g.setColor(parseColor("#F44336"));
        g.fillOval(node.getX() - radius, node.getY() - radius, 2 * radius, 2 * radius);

        radius-=5;
        g.setColor(parseColor("#FFCDD2"));
        g.fillOval(node.getX() - radius, node.getY() - radius, 2 * radius, 2 * radius);

        radius+=5;
        g.setColor(parseColor("#F44336"));
        drawCentreText(String.valueOf(node.getId()), node.getX(), node.getY());
    }

    public void drawNode(Node node){
        g.setColor(parseColor("#9C27B0"));
        g.fillOval(node.getX() - radius, node.getY() - radius, 2 * radius, 2 * radius);

        radius-=5;
        g.setColor(parseColor("#E1BEE7"));
        g.fillOval(node.getX() - radius, node.getY() - radius, 2 * radius, 2 * radius);

        radius+=5;
        g.setColor(parseColor("#9C27B0"));
        drawCentreText(String.valueOf(node.getId()), node.getX(), node.getY());
    }

    public void drawWeightText(String text, int x, int y) {
        g.setColor(parseColor("#cccccc"));
        FontMetrics fm = g.getFontMetrics();
        double t_width = fm.getStringBounds(text, g).getWidth();
        g.drawString(text, (int) (x - t_width / 2), (y + fm.getMaxAscent() / 2));
    }

    public void drawCentreText(String text, int x, int y) {
        FontMetrics fm = g.getFontMetrics();
        double t_width = fm.getStringBounds(text, g).getWidth();
        g.drawString(text, (int) (x - t_width / 2), (y + fm.getMaxAscent() / 2));
    }


    // Calculations
    private static int sqr(int x) {
        return x * x;
    }
    private static int dist2(Point v, Point w) {
        return sqr(v.x - w.x) + sqr(v.y - w.y);
    }
    private static int distToSegmentSquared(Point p, Point v, Point w) {
        double l2 = dist2(v, w);
        if (l2 == 0) return dist2(p, v);
        double t = ((p.x - v.x) * (w.x - v.x) + (p.y - v.y) * (w.y - v.y)) / l2;
        if (t < 0) return dist2(p, v);
        if (t > 1) return dist2(p, w);
        return dist2(p, new Point(
                (int)(v.x + t * (w.x - v.x)),
                (int)(v.y + t * (w.y - v.y))
        ));
    }
    private static int distToSegment(Point p, Point v, Point w) {
        return (int) Math.sqrt(distToSegmentSquared(p, v, w));
    }}
 
