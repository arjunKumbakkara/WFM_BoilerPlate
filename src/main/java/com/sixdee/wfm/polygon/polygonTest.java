package com.sixdee.wfm.polygon;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0] 
 */

public class polygonTest {
	/*@Autowired
	PointInPolygonUtil pointInPolygonFinder;*/
	
	double dynaLat=12.916308;
	double dynaLong=12.916308;
	
	
	 public static void main(String args[])
	    {
		 PointInPolygonUtil pointInPolygonFinder = new PointInPolygonUtil();
		 long startTime = System.currentTimeMillis();
		 for (int i=0;i<=10000;i++) {
			 Point p = new Point(12.916308, 77.610316);
			 System.out.println("Creating the polygon " +"12.960659,77.591074 | 12.947276,77.633284 | 12.911137, 77.653875 | 12.905782,77.562248" );
			 Point polygon1[] = { new Point(12.960659,77.591074), new Point(12.947276,77.633284),
					new Point(12.911137, 77.653875), new Point(12.905782,77.562248) };
				int n = 4;
    			p = new Point(12.954898, 77.504699);
				System.out.println("Point to be found IN/OUT--> " +"12.954898 | 77.504699" );
	
				System.out.println("Point P(" + p.x + ", " + p.y
						+ ") lies inside polygon1: " + pointInPolygonFinder.isInside(polygon1, n, p));
		 }
		 long endTime = System.currentTimeMillis();
			System.out.println("Time taken for 10000 polygo creation and point finding!!--> " + (endTime - startTime) + " milliseconds");
	    }
}
	

