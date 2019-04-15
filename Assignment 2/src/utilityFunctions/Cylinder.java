package utilityFunctions;

import java.util.ArrayList;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.gl2.GLUT;

public class Cylinder {
	//Main Variables
	private static GL2 gl;
	private static GLUT glut;
	private static GLU glu;
	
	//Quadric Object
	public GLUquadric quadric;
	
	//Clipping variables
	private static final double[] CLIP_TOP = {0,-1.0f,0,0};
	private static final double[] CLIP_BOTTOM = {0,1.0f,0,0};
	
	//Constructor
	public Cylinder(GL2 gl, GLUT glut, GLU glu) {
		this.gl = gl;
		this.glut = glut;
		this.glu = glu;
		
		this.quadric = glu.gluNewQuadric();
	}
	
	/**
	 * Draws a cylinder with the given values
	 * @author Nikkolas Diehl
	 * 
	 * @param scale - The scale of the entire cylinder. It is advised to keep this at 1
	 * @param clippingOptions - Clipping options for the object
	 * <ul><li>Index 0: clip top</li><li>Index 1: clip bottom</li></ul>
	 * @param baseRadius - The radius of the base of the cylinder
	 * @param topRadius - The radius of the top of the cylinder
	 * @param heightOfCylinder - The height of the cylinder
	 * @param sizeScale - The size scale of the object in the x, y, z direction
	 * @param translate - The translation of the object in the x, y, z direction
	 * @param rotate - The rotation of the object by the angle alpha in the x, y, z direction
	 * @param colour4d - The colour of the object in 4D. RGBA
	 * @param extraRotations - Rotate with more options beyond the base rotation.
	 * <ul><li>ArrayList<float[]> input<ul><li>float[] index 0: angle</li><li>float[] index 1: x</li><li>float[] index 2: y</li><li>float[] index 3: z</li></ul></li></ul>
	 * @param extraClipping - Clipping beyond the normal clipping options. This translates the sphere inwards or outwards of the clipping plane
	 * <ul><li>ArrayList<float[]> input<ul><li>float[] index 0: x</li><li>float[] index 1: y</li><li>float[] index 2: z</li></ul></li></ul>
	 * 
	 */
	public void drawCylinder(float scale, boolean[] clippingOptions, float baseRadius, float topRadius, float heightOfCylinder, 
							 double[] sizeScale, double[] translate, double[] rotate, float[] colour4d,
							 ArrayList<float[]> extraRotations, ArrayList<float[]> extraClipping) {		
		//Push
		gl.glPushMatrix();

		//In specific order:
		// - Basic rotations
		// - Basic translations
		// - Extra Rotations
		// - Scaling
		// - Clipping plane added
		// - Extra translations beyond the clipping plane for custom cuts
		// - Draw
		gl.glRotated(rotate[0], rotate[1], rotate[2], rotate[3]);
		gl.glTranslated(translate[0], translate[1], translate[2]);
		//Add extra rotations
		if(extraRotations!=null) {
			for(float[] rotation : extraRotations) {
				gl.glRotated(rotation[0], rotation[1], rotation[2], rotation[3]);
			}
		}
		gl.glScaled(sizeScale[0], sizeScale[1], sizeScale[2]);
		
		//Clip after translate
		if(clippingOptions[0]) {
			gl.glClipPlane(GL2.GL_CLIP_PLANE0, CLIP_TOP, 0);
			gl.glEnable(GL2.GL_CLIP_PLANE0);
		}
		if(clippingOptions[1]) {
			gl.glClipPlane(GL2.GL_CLIP_PLANE1, CLIP_BOTTOM, 0);
			gl.glEnable(GL2.GL_CLIP_PLANE1);
		}

		//Translate into clipping plane to cut a sphere deeper
		if(extraClipping!=null) {
			for(float[] extraClip : extraClipping) {
				gl.glTranslated(extraClip[0], extraClip[1], extraClip[2]);
			}
		}
		
		gl.glColor4fv(colour4d, 0);
		glu.gluCylinder(quadric, baseRadius, topRadius, heightOfCylinder, 100, 100);
		
		//Disable all clipping planes
		gl.glDisable(GL2.GL_CLIP_PLANE0);
		gl.glDisable(GL2.GL_CLIP_PLANE1);
		
		//Pop
		gl.glPopMatrix();
	}
	
	public void rotateObject(float angle, float xRot, float yRot, float zRot) {
		gl.glRotated(angle, xRot, yRot, zRot);
	}
}
