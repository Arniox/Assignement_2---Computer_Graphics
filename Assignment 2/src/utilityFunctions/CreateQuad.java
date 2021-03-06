package utilityFunctions;

import com.jogamp.opengl.GL2;

public class CreateQuad {
	//Main Variables
	private static GL2 gl;
	
	//Water height
	private float waterHeight = 0;
	
	//Constructor
	/**
	 * Constructs the cube
	 * 
	 * @param gl - GL2 variable for the 2D Quad
	 * 
	 * @author Nikkolas Diehl
	 */
	public CreateQuad(GL2 gl) {
		CreateQuad.gl = gl;
	}
	
	/**
	 * Draws a 2D Quad with the given inputs
	 * 
	 * @param waterWidth - width the water should be
	 * @param heightOfWater - height the water should be
	 * @param waterLength - length the water should be
	 * @param depthIntoTank - the depth, from the top of the tank, of relatively how deep the water should be
	 * @param waterColor4D - the colour of the water
	 * @param glassThickNess - the thickness of the glass for proper placement
	 * 
	 * @author Nikkolas Diehl
	 */
	public void drawQuad(float waterWidth, float heightOfWater, float waterLength, float depthIntoTank, float[] waterColor4D, float glassThickNess) {
		gl.glBegin(GL2.GL_QUADS);
		gl.glColor4fv(waterColor4D, 0);

		waterHeight = (heightOfWater-glassThickNess)*depthIntoTank;
		
		gl.glVertex3f((-1)*waterWidth+glassThickNess, waterHeight, (-1)*waterLength+glassThickNess);
		gl.glVertex3f((-1)*waterWidth+glassThickNess, waterHeight, (1)*waterLength-glassThickNess);
		gl.glVertex3f((1)*waterWidth-glassThickNess, waterHeight, (1)*waterLength-glassThickNess);
		gl.glVertex3f((1)*waterWidth-glassThickNess, waterHeight, (-1)*waterLength+glassThickNess);
		gl.glEnd();
	}
	
	/**
	 * Return the height of the water/quad 
	 * @return waterHeight
	 * 
	 * @author Nikkolas Diehl
	 */
	public float getWaterHeight() {
		return this.waterHeight;
	}
	
}
