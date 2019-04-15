package utilityFunctions;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

public class CoordinateAxes {
	//Main variavles
	private GL2 gl;
	private GLUT glut;
	
	//Constructor
	public CoordinateAxes(GL2 gl, GLUT glut) {
		this.gl = gl;
		this.glut = glut;
	}
	
	//Draw the axes lines and sphere
	public void drawAxes() {
		gl.glDisable(GL2.GL_LIGHTING);
		
		//Draw axis lines
		gl.glLineWidth(2.0f);
		gl.glBegin(GL2.GL_LINES);
			//Line 1 - Red
			gl.glColor3d(1, 0, 0);
			gl.glVertex3d(0, 0, 0);
			gl.glVertex3d(2, 0, 0);
			//Line 2 - Green
			gl.glColor3d(0, 1, 0);
			gl.glVertex3d(0, 0, 0);
			gl.glVertex3d(0, 2, 0);
			//Line 3 - Blue
			gl.glColor3d(0, 0, 1);
			gl.glVertex3d(0, 0, 0);
			gl.glVertex3d(0, 0, 2);
		gl.glEnd();
		
		//Draw sphere
		gl.glEnable(GL2.GL_LIGHTING);
		glut.glutSolidSphere(0.01, 50, 50);
	}
}
