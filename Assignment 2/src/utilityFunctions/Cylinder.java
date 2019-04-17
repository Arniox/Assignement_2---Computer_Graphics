package utilityFunctions;

import java.util.ArrayList;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

public class Cylinder {
	//Main Variables
	private static GL2 gl;
	private static GLU glu;
	
	//Quadric Object
	public GLUquadric quadric;
	
	//Clipping variables
	private static final double[] CLIP_TOP = {0,-1.0f,0,0};
	private static final double[] CLIP_BOTTOM = {0,1.0f,0,0};
	private static final double[] CLIP_BACK = {0,0,-1.0f,0};
	private static final double[] CLIP_FRONT = {0,0,1.0f,0};
	
	/**
	 * Creates a Cylinder Object
	 * 
	 * @param gl - GL2 variable for the Cylinder
	 * @param glu - GLUT variable for the Cylinder
	 * 
	 * @author Nikkolas Diehl
	 */
	public Cylinder(GL2 gl, GLU glu) {
		Cylinder.gl = gl;
		Cylinder.glu = glu;
		
		this.quadric = glu.gluNewQuadric();
	}
	
	/**
	 * Draws a cylinder with the given values
	 * 
	 * @param scale - The scale of the entire cylinder. It is advised to keep this at 1
	 * @param clippingOptions - Clipping options for the object
	 * <ul><li>Index 0: clip top</li><li>Index 1: clip bottom</li></ul>
	 * @param baseRadius - The radius of the base of the cylinder
	 * @param topRadius - The radius of the top of the cylinder
	 * @param heightOfCylinder - The height of the cylinder
	 * @param sizeScale - The size scale of the object in the x, y, z direction
	 * @param translate - The translation of the object in the x, y, z direction
	 * @param colour4d - The colour of the object in 4D. RGBA
	 * @param extraRotations - Rotate with more options beyond the base rotation.
	 * <ul><li>ArrayList<float[]> input<ul><li>float[] index 0: angle</li><li>float[] index 1: x</li><li>float[] index 2: y</li><li>float[] index 3: z</li></ul></li></ul>
	 * @param extraClipping - Translation beyond the normal clipping planes. This translates the sphere inwards or outwards of the clipping plane
	 * <ul><li>ArrayList<float[]> input<ul><li>float[] index 0: x</li><li>float[] index 1: y</li><li>float[] index 2: z</li></ul></li></ul>
	 * 
	 * @author Nikkolas Diehl
	 */
	public void drawCylinder(float scale, boolean[] clippingOptions, float baseRadius, float topRadius, float heightOfCylinder, 
							 double[] sizeScale, double[] translate, float[] colour4d,
							 ArrayList<float[]> extraRotations, ArrayList<float[]> extraClipping) {		
		//Push
		gl.glPushMatrix();

		//In specific order:
		// - Basic translations
		// - Extra Rotations
		// - Scaling
		// - Clipping plane added
		// - Extra translations beyond the clipping plane for custom cuts
		// - Draw

		gl.glTranslated(translate[0], translate[1], translate[2]);
		//Add extra rotations
		if(extraRotations!=null) {
			for(float[] rotation : extraRotations) {
				gl.glRotated(rotation[0], rotation[1], rotation[2], rotation[3]);
			}
		}
		gl.glScaled(sizeScale[0]*scale, sizeScale[1]*scale, sizeScale[2]*scale);
		
		//Clip after translate
		if(clippingOptions[0]) {
			gl.glClipPlane(GL2.GL_CLIP_PLANE0, CLIP_TOP, 0);
			gl.glEnable(GL2.GL_CLIP_PLANE0);
		}
		if(clippingOptions[1]) {
			gl.glClipPlane(GL2.GL_CLIP_PLANE1, CLIP_BOTTOM, 0);
			gl.glEnable(GL2.GL_CLIP_PLANE1);
		}
		if(clippingOptions[2]){
			gl.glClipPlane(GL2.GL_CLIP_PLANE2, CLIP_BACK, 0);
			gl.glEnable(GL2.GL_CLIP_PLANE2);
		}
		if(clippingOptions[3]) {
			gl.glClipPlane(GL2.GL_CLIP_PLANE3, CLIP_FRONT, 0);
			gl.glEnable(GL2.GL_CLIP_PLANE3);
		}

		//Translate into clipping plane to cut a sphere deeper
		if(extraClipping!=null) {
			for(float[] extraClip : extraClipping) {
				gl.glTranslated(extraClip[0], extraClip[1], extraClip[2]);
			}
		}
		
		//Color and draw
		gl.glColor4fv(colour4d, 0);
		glu.gluCylinder(quadric, baseRadius, topRadius, heightOfCylinder, 100, 100);
		
		//Disable all clipping planes
		gl.glDisable(GL2.GL_CLIP_PLANE0);
		gl.glDisable(GL2.GL_CLIP_PLANE1);
		gl.glDisable(GL2.GL_CLIP_PLANE2);
		gl.glDisable(GL2.GL_CLIP_PLANE3);
		
		//Pop
		gl.glPopMatrix();
	}
}
