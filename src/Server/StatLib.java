// Amir Yehuda 307966499 24/11/2020
package Server;


public class StatLib {


	// simple average
	public static float avg(float[] x){
		float sum = 0;
		for(int i=0; i<x.length; i++){
			sum+=x[i];
		}
		return (sum/x.length);
	}

	// returns the variance of X and Y
	public static float var(float[] x){
		float u=avg(x);
		float sum=0;
		for(int i=0; i<x.length; i++) {
			sum+=(x[i]*x[i]);
		}
		return ((sum/x.length)-(u*u));
	}

	// returns the covariance of X and Y
	public static float cov(float[] x, float[] y){
		float sum=0;
		for(int i=0; i<x.length; i++) {
			sum+= (x[i]-avg(x))*(y[i]-avg(y));
		}
		return (sum/x.length);
	}


	// returns the Pearson correlation coefficient of X and Y
	public static float pearson(float[] x, float[] y){
		double square_x, square_y;
		square_x= Math.sqrt((double)(var(x)));
		square_y= Math.sqrt((double)(var(y)));
		return (float)(cov(x,y)/(float)(square_x*square_y));
	} 

	// performs a linear regression and returns the line equation
	public static Line linear_reg(Point[] points){
		float []valueX =new float[points.length];
		float []valueY =new float[points.length];
		for (int i=0; i<points.length; i++)
		{
			valueX[i]=points[i].x;
			valueY[i]=points[i].y;
		}
		float a = cov(valueX, valueY)/var(valueX);
		float b= avg(valueY)- (a*avg(valueX));
		Line newLine= new Line(a,b);
		return newLine;
	}

	// returns the deviation between point p and the line equation of the points
	public static float dev(Point p,Point[] points){
		float a =linear_reg(points).a;
		float b =linear_reg(points).b;
		Line newLine =new Line(a,b);
		return (dev(p, newLine));

	}

	// returns the deviation between point p and the line
	public static float dev(Point p,Line l){

		return (Math.abs(p.y-l.f(p.x)));
	}
	
}
