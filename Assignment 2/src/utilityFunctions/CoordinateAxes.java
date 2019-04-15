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
	public void drawAxes(float[] fishPosition) {
		gl.glDisable(GL2.GL_LIGHTING);
		
		//Draw axis lines
		gl.glLineWidth(2.0f);
		gl.glBegin(GL2.GL_LINES);
			//Line Positive X - Red
			gl.glColor3d(1, 0, 0);
			gl.glVertex3d(0, 0, 0);
			gl.glVertex3d(1, 0, 0);
			//Line Negative X - Red
			gl.glColor3d(1, 0, 0);
			gl.glVertex3d(0, 0, 0);
			gl.glVertex3d(-1, 0, 0);
			//Line Positive Y - Green
			gl.glColor3d(0, 1, 0);
			gl.glVertex3d(0, 0, 0);
			gl.glVertex3d(0, 1, 0);
			//Line Negative Y - Green
			gl.glColor3d(0, 1, 0);
			gl.glVertex3d(0, 0, 0);
			gl.glVertex3d(0, -1, 0);
			//Line Positive Z - Blue
			gl.glColor3d(0, 0, 1);
			gl.glVertex3d(0, 0, 0);
			gl.glVertex3d(0, 0, 1);
			//Line Negative Z - Blue
			gl.glColor3d(0, 0, 1);
			gl.glVertex3d(0, 0, 0);
			gl.glVertex3d(0, 0, -1);
			
			//Draw Line to fish
			gl.glColor3d(1.0f, 1.0f, 0.0f);
			gl.glVertex3d(0,0,0);
			gl.glVertex3f(fishPosition[0], fishPosition[1], fishPosition[2]);
		gl.glEnd();
		
		//Draw sphere
		gl.glColor3d(0, 0, 0);
		gl.glEnable(GL2.GL_LIGHTING);
		glut.glutSolidSphere(0.01, 50, 50);
		
		//Draw text X - red
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glRasterPos3f(1,0,0);
		glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "X");
		gl.glEnd();
		//Draw text -X - red
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glRasterPos3f(-1,0,0);
		glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "-X");
		gl.glEnd();
		
		//Draw text Y - Green
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glRasterPos3f(0,1,0);
		glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "Y");
		gl.glEnd();
		//Draw text -Y - Green
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glRasterPos3f(0,-1,0);
		glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "-Y");
		gl.glEnd();
		
		//Draw text Z - Blue
		gl.glColor3f(0.0f, 0.0f, 1.0f);
		gl.glRasterPos3f(0,0,1);
		glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "Z");
		gl.glEnd();
		//Draw text -Z - Blue
		gl.glColor3f(0.0f, 0.0f, 1.0f);
		gl.glRasterPos3f(0,0,-1);
		glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "-Z");
		gl.glEnd();
		
		//Draw text of distance from 0,0,0 to fish
		float distanceBetween = (float)Math.sqrt((Math.pow((fishPosition[0]-0f), 2f))+(Math.pow((fishPosition[1]-0f), 2f))+(Math.pow((fishPosition[2]-0f), 2f)));
		gl.glColor3f(0.0f, 0.0f, 0.0f);
		gl.glRasterPos3f(fishPosition[0]/2, fishPosition[1]/2, fishPosition[2]/2);
		glut.glutBitmapString(GLUT.BITMAP_HELVETICA_10, distanceBetween+"");
		gl.glEnd();
	}
}
