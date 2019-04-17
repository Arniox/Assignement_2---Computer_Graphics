package utilityFunctions;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

public class Cube {
	//Main Variables
	public static GL2 gl;
	public static GLUT glut;
	
	//Constructor
	/**
	 * Constructs the cube
	 * 
	 * @param gl - GL2 variable for the cube
	 * @param glut - GLUT variable for the cube
	 * 
	 * @author Nikkolas Diehl
	 */
	public Cube(GL2 gl, GLUT glut){
		Cube.gl = gl;
		Cube.glut = glut;
	}
	
	//Draw cube
	/**
	 * Draws a Cube with the given input
	 * 
	 * @param scale - Scale of the entire cube. It is advised to keep this at 1
	 * @param xScale - The scaling of the cube in the x direction
	 * @param yScale - The scaling of the cube in the y direction
	 * @param zScale - The scaling of the cube in the z direction
	 * @param color4d - The colour in 4D for the cube. RGBA
	 * @param translate - The translation of the cube in the x, y, z direction
	 * @param rotate - The rotation by angle of the cube, in the x, y, z direction
	 * 
	 * @author Nikkolas Diehl
	 */
	public void drawCube(float scale, double xScale, double yScale, double zScale, float[] color4d, double[] translate, double[] rotate) {
		//Push
		gl.glPushMatrix();

		gl.glRotated(rotate[0], rotate[1], rotate[2], rotate[3]);
		gl.glScaled(xScale, yScale, zScale);
		gl.glTranslated(translate[0], translate[1], translate[2]);
		gl.glColor4fv(color4d, 0);
		glut.glutSolidCube(scale);
		
		//Pop
		gl.glPopMatrix();
	}
	
}
