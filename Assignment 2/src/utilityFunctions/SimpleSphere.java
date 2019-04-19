package utilityFunctions;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

public class SimpleSphere {
	//Main Variables
	private static GL2 gl;
	private static GLUT glut;
	
	/**
	 * Creates a Sphere Object
	 * 
	 * @param gl - GL2 variable for the Sphere
	 * @param glut - GLUT variable for the Sphere
	 * 
	 * @author Nikkolas Diehl
	 */
	public SimpleSphere(GL2 gl, GLUT glut){
		SimpleSphere.gl = gl;
		SimpleSphere.glut = glut;
	}
	
	/**
	 * Draw a sphere with given inputs
	 * 
	 * @param scale - The scale of the entire object. It is advised to keep this at scale 1
	 * @param radius - The radius of the sphere
	 * @param sizeScale - The size scaling of the sphere in the direction x, y, z
	 * @param translate - The translation of the sphere in the direction x, y, z
	 * @param colour4d - The colour of the sphere in 4D. RGBA
	 * 
	 * @author Nikkolas Diehl
	 */
	public void drawSphere(float scale, double radius, double[] sizeScale, double[] translate, float[] colour4d) {
		//Push
		gl.glPushMatrix();

		//In specific order:
		// - Basic translations
		// - Scaling
		// - Draw

		gl.glTranslated(translate[0], translate[1], translate[2]);
		gl.glScaled(sizeScale[0]*scale, sizeScale[1]*scale, sizeScale[2]*scale);
		
		//Color and draw
		gl.glColor4fv(colour4d, 0);
		glut.glutSolidSphere(radius, 5, 5);
		
		//Pop
		gl.glPopMatrix();
	}
	
}
