package utilityFunctions;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

public class Cube {
	//Main Variables
	public static GL2 gl;
	public static GLUT glut;
	
	//Constructor
	public Cube(GL2 gl, GLUT glut){
		Cube.gl = gl;
		Cube.glut = glut;
	}
	
	//Draw cube
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
