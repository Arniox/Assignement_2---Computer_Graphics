package itemCreation;

import java.util.ArrayList;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

public class FishAnimationPath {
	//Main variables
	private GL2 gl;
	private GLUT glut;	

	//Animation Variables
	private ArrayList<float[]> circleAnimationLocationVertices = new ArrayList<float[]>();
	private ArrayList<float[]> jumpAnimationLocationVertices = new ArrayList<float[]>();
	
	//Sizing variables
	private boolean change = false;
	private double width = 0;
	private double height = 0;
	private double length = 0;
	
	//Contructor
	public FishAnimationPath(GL2 gl, GLUT glut) {
		this.gl = gl;
		this.glut = glut;
	}
	
	/**
	 * Gathers the width, height and length of the tank and checks if their up to date or changing
	 * @author Nikkolas Diehl
	 * 
	 * @param W - Width of tank
	 * @param H - Height of tank
	 * @param L - Length of tank
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
			z = (float)((length/10)/4.5 * Math.cos(radians));
			
			circleAnimationLocationVertices.add(new float[]{x, y, z});
		}
		System.out.println("Ring is "+width+" across and "+length+" long and "+height+" high. There is "+circleAnimationLocationVertices.size()+" steps along this ring");
	}
	
	//Getters
	/**
	 * @return circleAnimationLocationVertices - This is an arraylist of 3D points around a ring
	 * @author Nikkolas Diehl
	 */
	public ArrayList<float[]> getCircle_Animation(){
		return this.circleAnimationLocationVertices;
	}
	/**
	 * 
	 * @return jumpAnimationLocationVertices - This is an arraylist of 3D points following a jump (bell curve)
	 * @author Nikkolas Diehl
	 */
	public ArrayList<float[]> getJump_Animation(){
		return this.jumpAnimationLocationVertices;
	}
}
