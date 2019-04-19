package utilityFunctions;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

public class CoordinateAxes {
	//Main variavles
	private GL2 gl;
	private GLUT glut;
	
	//Constructor
	/**
	 * Constructs the coordinate axis
	 * 
	 * @param gl - GL2 variable for the coordinate axis
	 * @param glut - GLUT variable for the coordinate axis
	 * 
	 * @author Nikkolas Diehl
	 */
	public CoordinateAxes(GL2 gl, GLUT glut) {
		this.gl = gl;
		this.glut = glut;
	}
	
	//Draw the axes lines and sphere
	/**
	 * Draws the axis and debugging lines
	 * @see #drawAxisLines() - Draw the axis lines
	 * @see #drawFishDebugLines(float[]) - Draw the debug lines to the fish
	 * @see #drawDataDebugLines(float[]) - Draw the debug lines opposing the fish
	 * @see #drawCenterSphere() - Draw the center sphere at 0,0,0
	 * @see #drawXYZText() - Draws the x,y,z,-x,-y,-z text on the axis lines
	 * @see #drawDistanceText(float[],float[]) - Draws the distance texts along the debug fish lines at x,y,z and connecting line
	 * 
	 * @param fishPosition - Global fish position
	 * @param fishRotation - Global fish rotation
	 * 
	 * @author Nikkolas Diehl
	 */
	public void debug(int frame, int jumpFrame, float[] fishPosition, float[] fishRotation, int indexCircleOfFish, int totalRevolutions, int totalJumps, float averageHeightOfParticles) {
		gl.glDisable(GL2.GL_LIGHTING);

		
		this.drawAxisLines();
		this.drawFishDebugLines(fishPosition);
		this.drawDataDebugLines(fishPosition);
		this.drawCenterSphere();
		this.drawXYZText();
		this.drawDistanceText(fishPosition, fishRotation);
		
		this.drawData(fishPosition, totalRevolutions, frame, jumpFrame, totalJumps, indexCircleOfFish, averageHeightOfParticles);
	}
	
	private void drawData(float[] fishPosition, int totalRevolutions, int frame, int jumpFrame, int totalJumps, int indexCircleOfFish, float averageHeightOfParticles) {
		//Draw total jumps
		gl.glRasterPos3f(fishPosition[0]*-1,fishPosition[1],fishPosition[2]*-1);
		glut.glutBitmapString(GLUT.BITMAP_HELVETICA_10, "Total jumps: "+totalJumps);
		//Draw total revolutions around ring
		gl.glRasterPos3f(fishPosition[0]*-1,fishPosition[1]+0.03f,fishPosition[2]*-1);
		glut.glutBitmapString(GLUT.BITMAP_HELVETICA_10, "Total revolutions: "+totalRevolutions);
		//Jump frame
		gl.glRasterPos3f(fishPosition[0]*-1,fishPosition[1]+0.06f,fishPosition[2]*-1);
		glut.glutBitmapString(GLUT.BITMAP_HELVETICA_10, "Next jump frame is: "+jumpFrame);
		//Frame count
		gl.glRasterPos3f(fishPosition[0]*-1,fishPosition[1]+0.09f,fishPosition[2]*-1);
		glut.glutBitmapString(GLUT.BITMAP_HELVETICA_10, "Frames passed: "+frame);
		//Circle index of animation
		gl.glRasterPos3f(fishPosition[0]*-1,fishPosition[1]+0.12f,fishPosition[2]*-1);
		glut.glutBitmapString(GLUT.BITMAP_HELVETICA_10, "Index in circle animation: "+indexCircleOfFish);
		//Average Height of particles
		gl.glRasterPos3f(fishPosition[0]*-1,fishPosition[1]+0.15f,fishPosition[2]*-1);
		glut.glutBitmapString(GLUT.BITMAP_HELVETICA_10, "Average height of water particles: "+averageHeightOfParticles);
	}
	
	/**
	 * Draw the axis lines for x, y, z, -x, -y, -z
	 * 
	 * @author Nikkolas Diehl
	 */
	private void drawAxisLines() {
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

		gl.glEnd();
	}
	
	/**
	 * Draws the debug lines from 0,0,0 to the fish for x, y, z and the connecting diagonal line
	 * @param fishPosition - The current fishPosition
	 * 
	 * @author Nikkolas Diehl
	 */
	private void drawFishDebugLines(float[] fishPosition) {
		//Draw Lines to fish
		gl.glLineWidth(2.0f);
		gl.glBegin(GL2.GL_LINES);
		
		gl.glColor3d(1.0f, 1.0f, 0.0f);
		//Draw the X line
			gl.glVertex3d(0,0,fishPosition[2]);
			gl.glVertex3f(fishPosition[0],0,fishPosition[2]);
		//Draw the Y line
			gl.glVertex3d(fishPosition[0],0,fishPosition[2]);
			gl.glVertex3f(fishPosition[0],fishPosition[1],fishPosition[2]);
		//Draw the Z line
			gl.glVertex3d(fishPosition[0],0,0);
			gl.glVertex3f(fishPosition[0],0,fishPosition[2]);
		//Draw connecting line
			gl.glVertex3d(0,0,0);
			gl.glVertex3f(fishPosition[0], fishPosition[1], fishPosition[2]);	
			
		gl.glEnd();
	}
	
	/**
	 * Draws the data debug values above the debug lines
	 * @param fishPosition - the current fish position
	 * 
	 * @author Nikkolas Diehl
	 */
	private void drawDataDebugLines(float[] fishPosition) {
		//Draw opposing lines to data
		gl.glLineWidth(2.0f);
		gl.glBegin(GL2.GL_LINES);
		
		gl.glColor3d(0.69,0.48,1.0);
		gl.glVertex3d(0,0,0);
		gl.glVertex3d(fishPosition[0]*-1, fishPosition[1], fishPosition[2]*-1);
		
		gl.glEnd();
	}
	
	/**
	 * Draw the center sphere at 0,0,0
	 * 
	 * @author Nikkolas Diehl
	 */
	private void drawCenterSphere() {
		//Draw sphere
		gl.glColor3d(0, 0, 0);
		gl.glEnable(GL2.GL_LIGHTING);
		glut.glutSolidSphere(0.01, 50, 50);
	}
	
	/**
	 * Draw the x,y,z,-x,-y,-z text on the axis lines
	 * 
	 * @author Nikkolas Diehl
	 */
	private void drawXYZText() {
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
	}
	
	/**
	 * Draws texts on each debug fish line that shows the distance from 0,0,0 to x, y, z and the connecting line
	 * @param fishPosition - The current fish position
	 * @param fishRotation - The current rotation of the fish
	 * 
	 * @author Nikkolas Diehl
	 */
	private void drawDistanceText(float[] fishPosition, float[] fishRotation) {
		//Draw distance texts
		gl.glColor3f(0.0f, 0.0f, 0.0f);
		//Draw X text
			gl.glRasterPos3f(fishPosition[0]/2,0.002f,fishPosition[2]);
			glut.glutBitmapString(GLUT.BITMAP_HELVETICA_10, fishPosition[0]+"");
			gl.glEnd();
		//Draw Y text
			gl.glRasterPos3f(fishPosition[0]-0.002f,fishPosition[1]/2,fishPosition[2]-0.002f);
			glut.glutBitmapString(GLUT.BITMAP_HELVETICA_10, fishPosition[1]+"");
			gl.glEnd();
		//Draw Z text
			gl.glRasterPos3f(fishPosition[0],0.002f,fishPosition[2]/2);
			glut.glutBitmapString(GLUT.BITMAP_HELVETICA_10, fishPosition[2]+"");
			gl.glEnd();
		//Draw Angle text
			gl.glRasterPos3f(fishPosition[0],fishPosition[1]+0.06f,fishPosition[2]);
			glut.glutBitmapString(GLUT.BITMAP_HELVETICA_10, "Rotation: "+fishRotation[0]+" °");
			gl.glEnd();
			
		//Draw text of distance from 0,0,0 to fish
		float distanceBetween = (float)Math.sqrt((Math.pow((fishPosition[0]-0f), 2f))+(Math.pow((fishPosition[1]-0f), 2f))+(Math.pow((fishPosition[2]-0f), 2f)));
		gl.glRasterPos3f(fishPosition[0]/2, fishPosition[1]/2, fishPosition[2]/2);
		glut.glutBitmapString(GLUT.BITMAP_HELVETICA_10, distanceBetween+"");
		gl.glEnd();
	}
}
