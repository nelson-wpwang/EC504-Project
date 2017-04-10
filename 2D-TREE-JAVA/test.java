import java.util.Scanner;

public class test {

    public static void main(String[] args) {
    	Kdtree million = new Kdtree();
    	
    	for (double i=500; i<1000;i++){
    		for (double j =500;j<1000;j++){
    		
    			million.insert(new Point2D(i/1000,j/1000));
    			million.insert(new Point2D((1000-i)/1000,j/1000));
    			million.insert(new Point2D(i/1000,(1000-j)/1000));
    			million.insert(new Point2D((1000-i)/1000,(1000-j)/1000));	
    		}
    	}
    	
       System.out.println("Hello World!"); // Display the string.
       System.out.println("Totally 999*999 points, that is"+million.size()); // Display the string.
       System.out.println("x,y is range from 0.000 to 0.999, every 0.001 there will be a point, a grid."); // Display the string.

       System.out.println("The nearest neighbor for (0.2011,0.2019)"+million.nearest(new Point2D(0.2011,0.2019))); // Display the string.
       System.out.println("The nearest neighbor for (0.6011,0.2011)"+million.nearest(new Point2D(0.6011,0.2011))); // Display the string.
       System.out.println("The nearest neighbor for (0.1118,0.6011)"+million.nearest(new Point2D(0.1118,0.6011))); // Display the string.
       System.out.println("The nearest neighbor for (0.2017,0.9017)"+million.nearest(new Point2D(0.2017,0.9017))); // Display the string.
       System.out.println("The nearest neighbor for (0.2818,0.2701)"+million.nearest(new Point2D(0.2818,0.2701))); // Display the string.
       // create a scanner so we can read the command-line input
       Scanner scanner = new Scanner(System.in);
       double x;
       double y;

       while (true) {
    	   
    	   System.out.println("Start input double for x and y");
    	   System.out.println("now for x:");
   	   
    	   
    	   // if the next is a double, print found and the double
    	   if (scanner.hasNextDouble()) {
    		   x=scanner.nextDouble();
        	   System.out.println("now for y:");
    		   
        	   if (scanner.hasNextDouble()) {
        		   
        		   y=scanner.nextDouble();
        		   System.out.print("The nearest neighbor:");
            	   System.out.println(million.nearest(new Point2D(x,y)));

        	   }    	  
    	   }
       }

       
       


    }
}
