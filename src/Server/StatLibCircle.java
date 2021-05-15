package Server;

import static java.lang.StrictMath.sqrt;
import static java.lang.StrictMath.pow;

public class StatLibCircle {


    //methods
    public static double dist(Point a, Point b)
    {
        return sqrt(pow(a.x - b.x, 2)
                + pow(a.y - b.y, 2));
    }

    public static Point circleCenter(float bx, float by, float cx, float cy)
    {
        float B = bx * bx + by * by;
        float C = cx * cx + cy * cy;
        float D = bx * cy - by * cx;
        Point p = new Point((cy * B - by * C) / (2 * D),
                (bx * C - cx * B) / (2 * D));
        return p;

    }



    public static Circle circleFrom(Point A, Point B, Point C)
    {
        Point I = circleCenter(B.x - A.x, B.y - A.y,
                C.x - A.x, C.y - A.y);

        Point t = new Point(I.x + A.x, I.y + A.y);

        Circle c = new Circle(t, dist(t,A));
        return c;
    }
    public static Circle circleFrom2(Point A, Point B)
    {
        // Set the center to be the midpoint of A and B
        Point C =new Point((float)((A.x + B.x) / 2.0), (float)((A.y + B.y) / 2.0 ));

        // Set the radius to be half the distance AB
        Circle t = new Circle(C, dist(A, B) / 2.0);
        return t;
    }

    public static Circle reCircle(Point[] ab, Point[] n){

        Circle c = null;
        if(ab.length == 0){
            return null;
        }
        if(ab.length == 1){
            c.p = ab[0];
            c.r = 0;
            return c;
        }
        if(ab.length == 2){
            return circleFrom2(ab[0],ab[1]);
        }
        if(ab.length == 3){
            return circleFrom(ab[0],ab[1],ab[2]);
        }
        return null;
    }

    public static Circle welzlCircle(Point[] ab, Point[] n, int size){
        if((ab.length == 0)||(n.length == 3)) {
            return reCircle(ab, n);
        }
        Point p = ab[size-1];
        Circle d = welzlCircle(ab, n, size-1);
        if(isInside(d,p)){
            return d;
        }
        for(int i = 0 ; i< n.length; i++){
            if(n[i] != null) {
                n[i] = p;
                return welzlCircle(ab, n, size-1);
            }
        }
        return welzlCircle(ab, n, size-1);

    }


    public static boolean isInside(Circle c, Point p)
    {
        return dist(c.p, p) <= c.r;
    }


}
