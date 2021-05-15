package Server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TimeSeries {


	public static class col
	{
		public String name;
		
		public List<Float> floats;

		public col(String name) 
		{
			this.name = name;
			this.floats = new ArrayList<Float>();
		}

		public String getName() {return name;}

		public void setName(String name) {this.name = name;}

		public List<Float> getFloats() {return floats;}

		public void setFloats(ArrayList<Float> floats) {this.floats = floats;}
	}

	
	
	private col[] col;
	
	public col[] getCols() {return col;}
	
	
	
	public TimeSeries(String csvFileName)
	{
		String line = "";
		
		try {
			
			@SuppressWarnings("resource")
			BufferedReader buf = new BufferedReader(new FileReader(csvFileName));
			
			line = buf.readLine();
			String[] parameterLine = line.split(",");
		
			col = new col[parameterLine.length];
			
			for(int i = 0; i < parameterLine.length; i++) {
				col[i] = new col(parameterLine[i]);
			}

			while((line = buf.readLine()) != null) 
			{
				parameterLine = line.split(",");
				
				for (int j = 0; j < parameterLine.length; j++) {
					col[j].getFloats().add(Float.parseFloat(parameterLine[j]));
				}
			}
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	
	
	
	
	public float[] fromListToArr(List<Float> list)
	{
		float[] floatArr = new float[list.size()];
		
		for(int i = 0; i < list.size(); i++)
			floatArr[i] = list.get(i);
		
		return floatArr;		
	}
	
	
	
	public Point[] fromArrToPoint(float[] x, float[] y)
	{
		Point[] points = new Point[x.length];
		
		for (int i = 0; i < x.length; i++) 
			points[i] = new Point(x[i], y[i]);
		
		return points;
	}
	
}
