package utilityFunctions;

import java.util.ArrayList;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

public class Sphere {
	//Main Variables
	private static GL2 gl;
	private static GLUT glut;
	
	//Clipping Variables
	private static final double[] CLIP_TOP = {0,-1.0f,0,0};
	private static final double[] CLIP_BOTTOM = {0,1.0f,0,0};
	private static final double[] CLIP_BACK = {0,0,-1.0f,0};
	private static final double[] CLIP_FRONT = {0,0,1.0f,0};
	
	//Constructor
	public Sphere(GL2 gl, GLUT glut){
		this.gl = gl;
		this.glut = glut;
	}
	
	/**
	 * Draw a sphere with given inputs
	 * @author Nikkolas Diehl
	 * 
	 * @param scale - The scale of the entire object. It is advised to keep this at scale 1
	 * @param clippingOptions - Options for clipping the sphere.
	 * <ul><li>Index 0: clip top</li><li>Index 1: clip bottom</li><li>Index 2: clip back</li><li>Index 3: clip front</li></ul>
	 * @param radius - The radius of the sphere
	 * @param sizeScale - The size scaling of the sphere in the direction x, y, z
	 * @param translate - The translation of the sphere in the direction x, y, z
	 * @param rotate - The rotation of the sphere by the angle alpha, by x, y, z
	 * @param colour4d - The colour of the sphere in 4D. RGBA
	 * @param extraRotations - Rotations beyond the normal rotation options.
	 * <ul><li>ArrayList<float[]> input<ul><li>float[] index 0: angle</li><li>float[] index 1: x</li><li>float[] index 2: y</li><li>float[] index 3: z</li></ul></li></ul>
	 * @param extraClipping - Clipping beyond the normal clipping options. This translates the sphere inwards or outwards of the clipping plane
	 * <ul><li>ArrayList<float[]> input<ul><li>float[] index 0: x</li><li>float[] index 1: y</li><li>float[] index 2: z</li></ul></li></ul>
	 */
	public void drawSphere(float scale, boolean[] clippingOptions, double radius, double[] sizeScale, double[] translate, double[] rotate, float[] colour4d, 
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
		//Rotate into clipping plane to cut a sphere deeper
		if(extraRotations!=null) {
			for(float[] extraRot : extraRotations) {
				gl.glRotated(extraRot[0], extraRot[1], extraRot[2], extraRot[3]);
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
		
		gl.glColor4fv(colour4d, 0);
		glut.glutSolidSphere(radius, 100, 100);
		
		//Disable all clipping planes
		gl.glDisable(GL2.GL_CLIP_PLANE0);
		gl.glDisable(GL2.GL_CLIP_PLANE1);
		gl.glDisable(GL2.GL_CLIP_PLANE2);
		gl.glDisable(GL2.GL_CLIP_PLANE3);
		
		//Pop
		gl.glPopMatrix();
	}
	
}
