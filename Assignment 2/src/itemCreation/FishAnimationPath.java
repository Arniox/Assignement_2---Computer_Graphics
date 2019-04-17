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
	private ArrayList<float[]> jumpAnimationLocationVertices = new ArrayList<float[]>();
	//Rotation Variables
	private ArrayList<float[]> circleRotationLocationVertices = new ArrayList<float[]>();
	private ArrayList<float[]> jumpRotationLocationVertices = new ArrayList<float[]>();
	
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
	 * @param W - Width of tank
	 * @param H - Height of tank
	 * @param L - Length of tank
	 * 
	 * @author Nikkolas Diehl
	 */
	public void gatherTankSize(boolean jumpOrSwim, double W, double H, double L) {
		//if the width, height or length are not correct, alter them
		if(width!=W || height!=H || length!=L) {
			width = W;
			height = H;
			length = L;
			
			//Set change to true and change
			change = true;
			//Depending on if the fish is jumping or swimming
			if(jumpOrSwim) {
				circleAnimationLocationVertices = new ArrayList<float[]>();
				this.changeAnimationRing();
			}else {
				System.out.println("jump not programmed yet");
			}
		}else {
			change = false;
		}
	}
	
	/**
	 * Creates and generates a ring for animation that fits inside the tank with some room to spare.
	 * 
	 * @author Nikkolas Diehl
	 */
	private void changeAnimationRing() {
		//Create floating point variables for the animation
		float x;
		float y = ((float)height/10)/2;
		float z;
		
		//Make the animation ring and iterate by 15 degrees each time
		double radians;
		for(int deg=0;deg<360;deg+=15) {
			radians = Math.toRadians(deg);
			
			//Set X and Z. Y is just the fish height
			//This should generate a point along a ring that fits inside the tank no matter what
			x = (float)((width/10)/4.5 * Math.cos(radians));
			z = (float)((length/10)/4.5 * Math.sin(radians));
			
			//Debugging
			gl.glColor3d(1.0, 0.0, 0.0);
			gl.glPointSize(3);
				gl.glBegin(GL.GL_POINTS);
				gl.glVertex3f(x, y, z);
			gl.glEnd();
			
			circleAnimationLocationVertices.add(new float[]{x, y, z});
			circleRotationLocationVertices.add(new float[]{180-deg,0,1,0});
			
		}
		System.out.println("Ring is "+width+" across and "+length+" long and "+height+" high. There is "+circleAnimationLocationVertices.size()+" steps along this ring");
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
	public boolean isValuesClose(float[] position, float[] location) {
		if(((position[0]<=location[0]+0.001f)&&(position[0]>=location[0]-0.001f))&&
		   ((position[1]<=location[1]+0.001f)&&(position[1]>=location[1]-0.001f))&&
		   ((position[2]<=location[2]+0.001f)&&(position[2]>=location[2]-0.001f))) {
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
	 * Gets the jumpAnimationLocationVertices
	 * @return jumpAnimationLocationVertices - This is an arraylist of 3D points following a jump (bell curve). x, y, z
	 * 
	 * @author Nikkolas Diehl
	 */
	public ArrayList<float[]> getJump_Animation(){
		return this.jumpAnimationLocationVertices;
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
	/**
	 * Gets the jumpRotationLocationVertices
	 * @return jumpRotationLocationVertices - This is an arraylist of 4D points following a jump (bell curve). Angle, x, y, z
	 * 
	 * @author Nikkolas Diehl
	 */
	public ArrayList<float[]> getJump_Rotation(){
		return this.jumpRotationLocationVertices;
	}
}
