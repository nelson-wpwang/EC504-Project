
import java.util.*;


public class Kdtree{
    private static double distance= Double.POSITIVE_INFINITY;
    private Node root;
    private int size=0;
    private double g_x;
    private double g_y;
    private double temp;
    private Kdtree(Point2D point){
        root = new Node(point);
        root.odd_even=true;
        size+=1;
        
    }
    // constructor
    public Kdtree(){
        root = null;
        
    }    
    // class for the internal node
    private class Node{
        private Node left,right;
        private boolean odd_even;
        private Point2D point;
        private RectHV rect;
        private Node(Point2D point){
            this.point=point;     
        }   
        private Node(RectHV rect){
            this.rect=rect;
        }

    }

    public           boolean isEmpty() {
      return size==0;   
    }// is the set empty? 
    public               int size() {
        return size;
    }// number of points in the set 
    public              void insert(Point2D p) {
        if ( p==null){
            return;
        }
        if (root==null){
            root=new Node(p);
            root.odd_even=true;
            root.left=new Node(new RectHV(0.0,0.0,root.point.x(),1.0));
            root.right=new Node(new RectHV(root.point.x(),0.0,1.0,1.0));
            root.left.odd_even=false;
            root.right.odd_even=false;            
            size=1;
            return;
        }
        Node current = root;
        boolean one=true;
        double temp;
        double x=p.x();
        double y=p.y();
        while(one){

            if ( current.odd_even){
                if ( current.point==null){
                    current.point=p;
                    temp=current.point.x();
                    current.left=new Node(new RectHV(current.rect.xmin(),current.rect.ymin(),temp,current.rect.ymax()));
                    current.right=new Node(new RectHV(temp,current.rect.ymin(),current.rect.xmax(),current.rect.ymax()));
                    current.left.odd_even=current.odd_even;
                    current.right.odd_even=current.odd_even;                        
                    one=false;                    
                }else{
                  
                    if (x<current.point.x()){
                        

                        current=current.left;                        
                    }else{
                        if ( p.equals(current.point)){
                            return;
                        }  
                        current=current.right;
                      
                    }                    
                }
                
                
            }else{
                if ( current.point==null){
                    current.point=p;
                    temp=current.point.y();
                    current.left=new Node(new RectHV(current.rect.xmin(),current.rect.ymin(),current.rect.xmax(),temp));
                    current.right=new Node(new RectHV(current.rect.xmin(),temp,current.rect.xmax(),current.rect.ymax()));
                    current.left.odd_even=current.odd_even;
                    current.right.odd_even=current.odd_even;                        
                    one=false;                    
                }else{
                 
                    if (y<current.point.y()){

                        current=current.left;                          

                    }else{
                        if ( p.equals(current.point)){
                            return;
                        }       
                        current=current.right;                          
                     
                    }                    
                }                
                
            }
            
            
            


        }
        
        size=size+1;       
    }// add the point to the set (if it is not already in the set)
    public           boolean contains(Point2D p) {
        if ( p==null){
            return false;
        }
        if(root==null){
            return false;
        }
        Node current=root;        
        boolean one=true;
        double y=p.y();
        double x=p.x();
        while(one){
            if (p.equals(current.point)){
             return true;   
            }

            
            if (current.odd_even){
                if (x<current.point.x()){
                    if (current.left.point == null){


                        return false;
                    }else{
                        current=current.left;
                    }
                }else{
                    if (current.right.point == null){
                        one=false;

                        return false;                        
                    }else{
                        current=current.right;
                    }
                }
            }else{
                if (y<current.point.y()){
                    if (current.left.point == null){

                        return false;
                    }else{
                        current=current.left;
                    }
                }else{
                    if (current.right.point == null){

                        return false;
                    }else{
                        current=current.right;
                    }
                }
            }
        }
        
        return false;

    }// does the set contain point p? 
    public              void draw(){
        
    }// draw all points to standard draw 
    private boolean cross(RectHV rec,Node node){
        if (node.point==null){
            return false;
        }
        
        
        
        double kkk=rec.xmax();
        kkk=node.point.x();        
        kkk=node.rect.ymin();

        if (node.odd_even){
            
            return rec.xmax() >= node.point.x() && rec.ymax() >= node.rect.ymin()
            && node.point.x() >= rec.xmin() && node.rect.ymax() >= rec.ymin();
        }else{
            return rec.xmax() >= node.rect.xmin() && rec.ymax() >= node.point.y()
            && node.rect.xmax() >= rec.xmin() && node.point.y() >= rec.ymin();            
        }

    }
    private LinkedList<Point2D> range(LinkedList<Point2D> result,Node current,RectHV rect){

        if (current.point==null){
            return result;
        }
        if (rect.contains(current.point)==true){
            result.add(current.point);
            result=range(result,current.left,rect);
            result=range(result,current.right,rect);                
        }else{
            if(current.left.rect.intersects(rect)){
                result=range(result,current.left,rect);
            } 
            if(current.right.rect.intersects(rect)){
                result=range(result,current.right,rect);
            }               
        }
        

        

        return result;
        
    }
    public Iterable<Point2D> range(RectHV rect) {
        if ( rect==null){
            return new LinkedList<Point2D>();
        }
        if ( root==null){
            return new LinkedList<Point2D>();
        }        
        LinkedList<Point2D> result=new LinkedList<Point2D>();
        Node current=root;
        return range(result,current,rect);
    }// all points that are inside the rectangle 
    public           Point2D nearest(Point2D p) {
        if ( p==null){
            return null;
        }        
        if( root==null){
            return null;
        }
        distance=Double.POSITIVE_INFINITY;
        g_x=p.x();
        g_y=p.y();
        return nearest(root,p,root.point);



    }// a nearest neighbor in the set to point p; null if the set is empty 
    private Point2D nearest(Node current,Point2D p,Point2D best){

        if (distance==0){
            return best;
        }
        temp=p.distanceSquaredTo(current.point);
        if(distance>temp){
            best=current.point;
            distance=temp;
            if (current.odd_even){
                if(g_x<current.point.x()){
                    if(current.left.point!=null){
                        best=nearest(current.left,p,best);                      
                    }

                    if(current.right.point!=null){
                        if(current.right.rect.distanceSquaredTo(p)<distance){
                            best=nearest(current.right,p,best);                      
                        }
                              
                    }
                }else{               
                    if(current.right.point!=null){
                        best=nearest(current.right,p,best);                      
                    }
                    if(current.left.point!=null){                    
                        if(current.left.rect.distanceSquaredTo(p)<distance){
                            best=nearest(current.left,p,best);                      
                        }
                    }
                }
            }else{
                if(g_y<current.point.y()){
                    if(current.left.point!=null){
                        best=nearest(current.left,p,best);                      
                    } 
                    if(current.right.point!=null){
                        if(current.right.rect.distanceSquaredTo(p)<distance){
                            best=nearest(current.right,p,best);                      
                        }
                    }
                }else{
                    if(current.right.point!=null){
                        best=nearest(current.right,p,best);                      
                    }
                    if(current.left.point!=null){
                        if(current.left.rect.distanceSquaredTo(p)<distance){
                            best=nearest(current.left,p,best);                      
                        }  
                    }
                }            
            }            
        }else{

            Node first=null;
            Node second=null;   
            
            if (current.odd_even){
                if(g_x<current.point.x()){
                    first=current.left;
                    second=current.right;               
                }else{
                    first=current.right;                              
                    second=current.left;              
                }
            }else{
                if(g_y<current.point.y()){
                    first=current.left;
                    second=current.right;               
                }else{
                    first=current.right;                              
                    second=current.left;              
                }            
            }
            
            
            
            if(second.rect.distanceSquaredTo(p)<distance){
                if(first.point!=null){
                    best=nearest(first,p,best);                      
                }
                if(second.point!=null){
                    best=nearest(second,p,best);                      
                }
                                       
            }else{
                if(first.rect.distanceSquaredTo(p)<distance){
                    if(first.point!=null){
                        best=nearest(first,p,best);                      
                    }  
                }                        
            }
        
        }
        return best;
    }

    
    
}