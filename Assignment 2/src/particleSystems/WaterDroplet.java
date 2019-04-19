package particleSystems;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

import utilityFunctions.SimpleSphere;

public class WaterDroplet {
	//Main Variables
	private GL2 gl;
	private GLUT glut;
	private static final int SCALE = 1;
	
	//Animation variables
	public float dy;
	public float dx;
	public float dz;
	private float gravity = 0.50f;
	
	//Object
	private SimpleSphere droplet;
	
	//Object data
	private float[] dropletPosition = new float[3];
	private float[] dropletColour4d;
	private double[] fishPosition;
	
	/**
	 * Constructs a water drop let
	 * 
	 * @param gl - GL2 variable for a water drop let
	 * @param glut - GLUT variable for a water drop let
	 * @param fishPosition - Current position of the fish
	 * @param colour4d - Colour of the water droplet
	 * 
	 * @author Nikkolas Diehl
	 */
	public WaterDroplet(GL2 gl, GLUT glut, float[] fishPosition, float[] colour4d) {
		//Main variables
		this.gl = gl;
		this.glut = glut;
		
		//Set colour
		this.dropletColour4d = colour4d;
		
		//Set dx, dy, dz
		dy = (float) (Math.random()*0.40f+0.40f);
		dx = (float) (Math.random()*0.30f-0.15f);
		dz = (float) (Math.random()*0.30f-0.15f);
		
		//Set up object
		droplet = new SimpleSphere(this.gl, this.glut);
		this.fishPosition = new double[]{fishPosition[0],fishPosition[1],fishPosition[2]};
	}
	
	/**
	 * Draws a water droplet with the given inputs
	 * @see utilityFunctions.SimpleSphere#drawSphere(float, double, double[], double[], float[]) - Simple sphere generation
	 * 
	 * @author Nikkolas Diehl
	 */
	public void drawWaterDroplet() {
		//Push
		gl.glPushMatrix();
		//GLOBAL TRANSLATION AND ROTATIONS
			gl.glTranslated(dropletPosition[0], dropletPosition[1], dropletPosition[2]);
				droplet.drawSphere(SCALE, 0.005f, new double[]{1,1,1}, fishPosition, dropletColour4d);
		//Pop
		gl.glPopMatrix();
	}
	
	/**
	 * Animate a particle
	 * @param time - timeDelta
	 * 
	 * @author Nikkolas Diehl
	 */
	public void animate(double time) {		
		//Add dx,dy,dz
		dropletPosition[0] += this.dx * time;
		dropletPosition[1] += this.dy * time;
		dropletPosition[2] += this.dz * time;
		
		//Add gravity
		this.dy -= this.gravity * time;
	}
	
	//Getters
	/**
	 * Get the current position of a droplet
	 * @return dropletPosition
	 * 
	 * @author Nikkolas Diehl
	 */
	public float[] getDropletPos() {
		return this.dropletPosition;
	}
}
