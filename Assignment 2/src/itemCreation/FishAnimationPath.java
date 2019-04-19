package itemCreation;

import java.util.ArrayList;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

public class FishAnimationPath {
	//Main variables
	private GL2 gl;
	private GLUT glut;	

	//Animation Variables
	private ArrayList<float[]> circleAnimationLocationVertices = new ArrayList<float[]>();
	//Rotation Variables
	private ArrayList<float[]> circleRotationLocationVertices = new ArrayList<float[]>();
	
	//Sizing variables
	private boolean change = false;
	private double width = 0;
	private double height = 0;
	private double length = 0;
	
	//Contructor
	/**
	 * Constructs the fish animation pathing system
	 * 
	 * @param gl - GL2 variable for the fish animation pathing
	 * @param glut - GLUT varible for the fish animation pathing
	 * 
	 * @author Nikkolas Diehl
	 */
	public FishAnimationPath(GL2 gl, GLUT glut) {
		this.gl = gl;
		this.glut = glut;
	}
	
	/**
	 * Gathers the width, height and length of the tank and checks if their up to date or changing
	 * @see #changeAnimationRing() - Re-calculate the animation path for ring animation
	 * 
	 * @param drawDebug - True or false to draw the debug line of the paths
	 * @param W - Width of tank
	 * @param H - Height of tank
	 * @param L - Length of tank
	 * 
	 * @author Nikkolas Diehl
	 */
	public void gatherTankSize(boolean drawDebug, double W, double H, double L) {
		//if the width, height or length are not correct, alter them
		if(width!=W || height!=H || length!=L) {
			width = W;
			height = H;
			length = L;
			
			//Set change to true and change
			change = true;
			//Set the ring animation pathing			
			circleAnimationLocationVertices = new ArrayList<float[]>();
			circleRotationLocationVertices = new ArrayList<float[]>();
			this.changeAnimationRing(drawDebug);
		}else {
			change = false;
		}
	}
	
	/**
	 * Creates and generates a ring for animation that fits inside the tank with some room to spare.
	 * @param drawDebug - True or false to draw the debug line of the paths
	 * 
	 * @author Nikkolas Diehl
	 */
	private void changeAnimationRing(boolean drawDebug) {
		//Create floating point variables for the animation
		float x;
		float y = ((float)height/10)/2;
		float z;
		
		//Make the animation ring and iterate by 15 degrees each time
		double radians;
		for(int deg=0;deg<360;deg++) {
			radians = Math.toRadians(deg);
			
			//Set X and Z. Y is just the fish height
			//This should generate a point along a ring that fits inside the tank no matter what
			x = (float)((width/10)/4.5 * Math.cos(radians));
			z = (float)((length/10)/4.5 * Math.sin(radians));
			
			if(drawDebug) {
				//Debugging
				gl.glColor3d(1.0, 0.0, 0.0);
				gl.glPointSize(3);
					gl.glBegin(GL.GL_POINTS);
					gl.glVertex3f(x, y, z);
				gl.glEnd();
			}
			
			//Set location
			circleAnimationLocationVertices.add(new float[]{x, y, z});
			//Set angle;
			circleRotationLocationVertices.add(new float[]{180-deg,0,1,0});
			
		}
		System.out.println("Ring is "+width+" across and "+length+" long and "+height+" high. There is "+circleAnimationLocationVertices.size()+" steps along this ring");
	}
	
	/**
	 * Get a new float at index i of the parametric expression shown as<br>
	 * <b>Y = L+((w+(-x^2*k))^t)/h {minimum<x<maximum}</b>
	 * 
	 * @param drawDebug - True or false to draw the debug line of the paths
	 * @param i - i in the loop for creating the path
	 * @param heightLimitUpWards - the height of the entire parametric expression
	 * @param steepness - the depth of the curve
	 * @param type - this is a multiplier of the parametric expression. This changes the entire style of the path (iterate by 0.1)
	 * @param multiplier - this is another multiplier of the parametric expression. This changes the entire style of the path as well 9iterate by 0.1)
	 * @param steepnessDivision - similar to the steepness operator by with more control.
	 * 
	 * @return a new float containing x, y, z
	 * 
	 * @author Nikkolas Diehl
	 */
	private float[] setJumpAtI(boolean drawDebug, float i,float heightLimitUpWards,float steepness,float type, float multiplier, float steepnessDivision) {
		float x = i;
		float y = heightLimitUpWards+((float)Math.pow((type+((float)Math.pow((-1)*i, 2)*steepness)), multiplier))/steepnessDivision;
			  y+=((float)((width/10)/4.5)+(height/10)/2)-0.45;
		float z = 0;
		
		if(drawDebug) {
			//Debugging
			gl.glColor3d(1.0, 0.0, 0.0);
			gl.glPointSize(3);
				gl.glBegin(GL.GL_POINTS);
				gl.glVertex3f(x, y, z);
			gl.glEnd();
		}
		
		return new float[]{x,y,z};
	}
	
	//Check if position is close to ArrayList<float[]> at index n
	/**
	 * Find out if the position in 3D space of something is close to the location you wish to get to
	 * 
	 * @param position - The current position or 3D point
	 * @param location - The location or 3D point that you wish to move to
	 * 
	 * @return boolean - True or false of if the position is close (within 0.02f) to the location
	 * 
	 * @author Nikkolas Diehl
	 */
	public boolean isValuesClose(float[] position, float[] location, float distance) {
		if(((position[0]<=location[0]+distance)&&(position[0]>=location[0]-distance))&&
		   ((position[1]<=location[1]+distance)&&(position[1]>=location[1]-distance))&&
		   ((position[2]<=location[2]+distance)&&(position[2]>=location[2]-distance))) {
			return true;
		}else {
			return false;
		}
	}
	
	//Getters
	/**
	 * Gets the circleAnimationLocationVertices
	 * @return circleAnimationLocationVertices - This is an arraylist of 3D points around a ring. x, y, z
	 * 
	 * @author Nikkolas Diehl
	 */
	public ArrayList<float[]> getCircle_Animation(){
		return this.circleAnimationLocationVertices;
	}
	/**
	 * Gets the circleRotationLocationVertices
	 * @return circleRotationLocationVertices - This is an arraylist of 4D points around a ring. Angle, x, y, z
	 * 
	 * @author Nikkolas Diehl
	 */
	public ArrayList<float[]> getCircle_Rotation(){
		return this.circleRotationLocationVertices;
	}
}
