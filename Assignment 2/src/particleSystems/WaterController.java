package particleSystems;

import java.util.ArrayList;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

public class WaterController {

	//Main variables
	private GL2 gl;
	private GLUT glut;
	
	//Data
	private float averageHeight = 0;
	
	//Objects
	private ArrayList<WaterDroplet> waterDroplets = new ArrayList<WaterDroplet>();
	
	/**
	 * Constructs the water droplet controller
	 * @param gl - GL2 variable for the water controller
	 * @param glut - GLUT variable for the water controller
	 * 
	 * @author Nikkolas Diehl
	 */
	public WaterController(GL2 gl, GLUT glut) {
		//Main variables
		this.gl = gl;
		this.glut = glut;
	}
	
	/**
	 * Adds a particle
	 * @see particleSystems.WaterDroplet#WaterDroplet(GL2, GLUT, float[], float[]) - Generating a water droplet
	 * 
	 * @param currentFishPosition - The current position of the fish
	 * 
	 * @author Nikkolas Diehl
	 */
	public void addParticles(float[] currentFishPosition, int max, int min) {
		//Set a random amount of drops between 100 and 200 at the current fish position
		int randomLimit = (int) (Math.random()*(max-min)+min);
		for(int i=0;i<randomLimit;i++) {
			this.waterDroplets.add(new WaterDroplet(this.gl, this.glut, currentFishPosition, new float[]{0.031f, 0.294f, 0.819f, 1.0f}));
		}
		
		System.out.println("Created: "+randomLimit+" particles");
	}
	
	/**
	 * Draws the water particles
	 * @see particleSystems.WaterDroplet#drawWaterDroplet() - Drawing a water droplet with given inputs
	 * 
	 * @param waterheight - The height of the water
	 * 
	 * @author Nikkolas Diehl
	 */
	public void drawParticles(float waterHeight) {
		float sum = 0;
		
		for(int i=0;i<waterDroplets.size();i++) {
			waterDroplets.get(i).drawWaterDroplet();
			
			sum+=waterDroplets.get(i).getDropletPos()[1];
			
			if(waterDroplets.get(i).getDropletPos()[1]<0 && waterDroplets.get(i).dy<0) {
				waterDroplets.remove(i);
			}
		}

		if(!waterDroplets.isEmpty()) {
			averageHeight = sum/waterDroplets.size();
		}else {
			averageHeight = 0.0f;
		}
	}
	
	/**
	 * Animates the particles
	 * @see particleSystems.WaterDroplet#animate(double) - Animating a water droplet
	 * 
	 * @param time - timeDelta
	 * 
	 * @author Nikkolas Diehl
	 */
	public void animateParticles(double time) {
		for(int i=0;i<waterDroplets.size();i++) {
			waterDroplets.get(i).animate(time);
		}
	}
	
	//Getters
	/**
	 * Get the average height of all particles
	 * @return averageHeight
	 * 
	 * @author Nikkolas Diehl
	 */
	public float getAverageHeight() {
		return this.averageHeight;
	}
	
}
