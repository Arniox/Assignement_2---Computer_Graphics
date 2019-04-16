package itemCreation;

import java.util.ArrayList;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

import utilityFunctions.Cone;
import utilityFunctions.Cylinder;
import utilityFunctions.Sphere;

public class Fish {
	//Main functions
	private GL2 gl;
	private GLUT glut;
	private GLU glu;
	
	//static values
	private static final float SCALE = 1;
	
	//Object stuffs
	private float[] fishPosition = new float[3];
	private float[] fishRotation = new float[4];
	
	//Animation variables
	private float degrees = 0;
	private int upDown = 1;
	public float FIN_ROTATION = 0f;
	public float TAIL_ROTATION = 0f;
	
	//Object Colours - Body, mouth, teeth
	private static final float[] FISH_UNDER_BODY_COLOUR = {0.490f,0.592f,0.757f,1.0f};
	private static final float[] FISH_TOP_BODY_COLOUR = {0.612f,0.760f,0.990f,1.0f};
	private static final float[] FISH_TEETH_COLOUR = {0.910f,0.910f,0.910f,1.0f};
	//Object Colours - Eyes
	private static final float[] FISH_EYE_BALL_COLOUR = {0.0f,0.0f,0.0f,1.0f};
	
	//Objects - Body, mouth, teeth
	Cylinder fishUnderBodyMiddle;
	Sphere fishUnderBodyMouth;
	Cylinder fishTeethTop;
	Cylinder fishTeethBottom;
	Cylinder fishTopBodyMiddle;
	Sphere fishTopBodyMouth;
	//Objects - Eyes
	Sphere eyeLidRight;
	Sphere eyeLidLeft;
	Sphere eyeBallRight;
	Sphere eyeBallLeft;
	//Objects - Front fins
	Cylinder finRightInner;
	Cylinder finLeftInner;
	Cylinder finRightMiddle;
	Cylinder finLeftMiddle;
	Cone finRightEnd;
	Cone finLeftEnd;
	//Objects - Tail parts
	Cylinder innerTailTop;
	Cylinder innerTailBottom;
	Cylinder outerTail;
	Cone tailFinRight;
	Cone tailFinLeft;
	
	
	//constructor
	public Fish(GL2 gl, GLUT glut) {
		this.gl = gl;
		this.glut = glut;
		this.glu = new GLU();
		
		//Set up objects - Body, mouth, teeth
		fishUnderBodyMiddle = new Cylinder(this.gl, this.glu);
		fishUnderBodyMouth = new Sphere(this.gl, this.glut);
		fishTeethTop = new Cylinder(this.gl, this.glu);
		fishTeethBottom = new Cylinder(this.gl, this.glu);
		fishTopBodyMiddle = new Cylinder(this.gl, this.glu);
		fishTopBodyMouth = new Sphere(this.gl, this.glut);
		//Set up objects - Eyes
		eyeLidRight = new Sphere(this.gl, this.glut);
		eyeLidLeft = new Sphere(this.gl, this.glut);
		eyeBallRight = new Sphere(this.gl, this.glut);
		eyeBallLeft = new Sphere(this.gl, this.glut);
		//Set up objects - Front fins
		finRightInner = new Cylinder(this.gl, this.glu);
		finLeftInner = new Cylinder(this.gl, this.glu);
		finRightMiddle = new Cylinder(this.gl, this.glu);
		finLeftMiddle = new Cylinder(this.gl, this.glu);
		finRightEnd = new Cone(this.gl, this.glut);
		finLeftEnd = new Cone(this.gl, this.glut);
		//Set up objects - Tail parts
		innerTailTop = new Cylinder(this.gl, this.glu);
		innerTailBottom = new Cylinder(this.gl, this.glu);
		outerTail = new Cylinder(this.gl, this.glu);
		tailFinRight = new Cone(this.gl, this.glut);
		tailFinLeft = new Cone(this.gl, this.glut);
	}
	
	//draw fish
	public void drawFish(float[] globalPos, float[] globalRotation) {
		//Set part positions
		this.fishPosition = globalPos;
		this.fishRotation = globalRotation;
		
		//Push
		gl.glPushMatrix();
		//GLOBAL TRANSLATION AND ROTATIONS
		gl.glTranslated(fishPosition[0], fishPosition[1], fishPosition[2]);
		gl.glRotated(fishRotation[0], fishRotation[1], fishRotation[2], fishRotation[3]);
		
			this.setUpEntireFish();
			this.animateParts();

		//Pop
		gl.glPopMatrix();
			
	}
	
	/**
	 * Sets up and draws all parts of the fish.
	 * @author Nikkolas Diehl
	 * 
	 * @see #setMainBodyParts() - Setting up main body
	 * @see #setTeeth() - Setting up the teeth
	 * @see #setRightEye() - Setting up the right eye
	 * @see #setLeftEye() - Setting up the left eye
	 * @see #setRightFinParts() - Setting up the right fin
	 * @see #setLeftFinParts() - Setting up the left fin
	 * @see #setTailParts() - Setting up the tail
	 */
	private void setUpEntireFish() {
		//Set up main parts
		gl.glPushMatrix();
			this.setMainBodyParts();
		gl.glPopMatrix();
		
		//Set up teeth
		gl.glPushMatrix();
			this.setTeeth();
		gl.glPopMatrix();
		
		//Rotations for Right eye
		gl.glPushMatrix();
			this.setRightEye();
		gl.glPopMatrix();
		
		//Rotations for Left eye
		gl.glPushMatrix();
			this.setLeftEye();
		gl.glPopMatrix();
		
		//Rotations for right fin animations
		gl.glPushMatrix();
			this.setRightFinParts();
		gl.glPopMatrix();
		
		//Rotations for left fin animations
		gl.glPushMatrix();
			this.setLeftFinParts();		
		gl.glPopMatrix();
		
		//Rotations for fin
		gl.glPushMatrix();
			this.setTailParts();
		gl.glPopMatrix();
	}
	
	//Animation
	/**
	 * Animates only the parts. This doesn't require time. Just animates based on frame rate
	 * @author Nikkolas Diehl
	 */
	public void animateParts() {
		//Check if parts going up or down
		if(degrees>=15) {
			upDown=2;
		}else if(degrees<=-15) {
			upDown=1;
		}
		
		//Animate fins and tail
		FIN_ROTATION = degrees;
		TAIL_ROTATION = degrees*-1;
		
		//Counting up or down
		switch(upDown) {
		//up
		case 1:
			degrees+=0.5f;
			break;
		//down
		case 2:
			degrees-=0.5f;
			break;
		}
	}
	
	//Fish setup
	
	/**
	 * Sets up the main body parts. This includes body and mouth parts. Splitting up the drawing of the fish parts allows for easier control
	 * @author Nikkolas Diehl
	 */
	private void setMainBodyParts() {
		//Top Mouth piece and body piece
		ArrayList<float[]> extraTopMouthClipping = new ArrayList<float[]>();
		extraTopMouthClipping.add(new float[]{0,-0.03f,0});
		
		//Set parts - Body, mouth, teeth
		//Middle body has no extra translations
		fishUnderBodyMiddle.drawCylinder(SCALE, new boolean[]{true, false, false, false}, 0.05f, 0.043f, 0.2f, new double[]{1,1,1},
	  			 						 new double[]{0,0,0}, FISH_UNDER_BODY_COLOUR, null, null);
		fishTopBodyMiddle.drawCylinder(SCALE, new boolean[]{false, true, false, false}, 0.0584f, 0.0525f, 0.2f, new double[]{1,1,1},
				 						 new double[]{0,0,0}, FISH_TOP_BODY_COLOUR, null, extraTopMouthClipping);
		//Mouth pieces have slight rotation
		gl.glRotated(-8f,1,0,0);
			fishUnderBodyMouth.drawSphere(SCALE, new boolean[]{true, false, true, false}, 0.05f, new double[]{1,1,3.5},
				  	 					 new double[]{0,0,0}, FISH_UNDER_BODY_COLOUR, null, null);
		//Undo previous rotation and make new
		gl.glRotated(9.5f,1,0,0);
			fishTopBodyMouth.drawSphere(SCALE, new boolean[]{false, true, true, false}, 0.0584f, new double[]{1,1,3.25},
					 					 new double[]{0,0,0}, FISH_TOP_BODY_COLOUR, null, extraTopMouthClipping);
	}
	
	/**
	 * Sets up the teeth parts. This includes the top and bottom teeth parts. Splitting up the drawing of the fish parts allows for easier control'
	 * @author Nikkolas Diehl
	 */
	private void setTeeth() {
		//Set up teeth
		//Translate and rotate for top teeth part
		gl.glTranslated(0,0.008,-0.002);
		gl.glRotated(90,1,0,0);
			fishTeethTop.drawCylinder(SCALE, new boolean[]{true, false, false, false}, 0.04f, 0.037f, 0.04f, new double[]{1,3.8,1},
					 					 new double[]{0,0,0}, FISH_TEETH_COLOUR, null, null);
		//Undo rotation from top teeth and make new
		gl.glTranslated(0,-0.002, 0.008);
		gl.glRotated(-6, 1, 0, 0);
			fishTeethBottom.drawCylinder(SCALE, new boolean[]{true, false, false, false}, 0.038f, 0.039f, 0.022f, new double[]{1,3.8,1},
					 					 new double[]{0,0,0}, FISH_TEETH_COLOUR, null, null);
	}
	
	/**
	 * Sets up the right eye parts. This includes the eye ball and the eye lid. Splitting up the drawing of the fish parts allows for easier control
	 * @author Nikkolas Diehl
	 */
	private void setRightEye() {
		//Set parts - Right Eye
		gl.glTranslated(0.045, 0.004, 0.01);
			eyeBallRight.drawSphere(SCALE, new boolean[]{false, false, false, false}, 0.004f, new double[]{0.8,0.8,0.8},
					 					 new double[]{0,0,0}, FISH_EYE_BALL_COLOUR, null, null);
		//Translation of eye lid is referenced off of the eye ball
		gl.glTranslated(0.002, 0.002, 0);
		gl.glRotated(90,0,1,0);
		gl.glRotated(-30,1,0,0);
			eyeLidRight.drawSphere(SCALE, new boolean[]{false, false, true, false}, 0.005f, new double[]{0.8,0.8,0.8},
					 					 new double[]{0,0,0}, FISH_UNDER_BODY_COLOUR, null, null);
		
	}
	
	/**
	 * Sets up the left eye parts. This includes the eye ball and the eye lid. Splitting up the drawing of the fish parts allows for easier control
	 * @author Nikkolas Diehl
	 */
	private void setLeftEye() {
		//Set parts - Left Eye
		gl.glTranslated(-0.045,0.004,0.01);
			eyeBallLeft.drawSphere(SCALE, new boolean[]{false, false, false, false}, 0.004f, new double[]{0.8,0.8,0.8},
					 					 new double[]{0,0,0}, FISH_EYE_BALL_COLOUR, null, null);
		//Translation of eye lid is referenced off of the eye ball
		gl.glTranslated(-0.002, 0.002, 0);
		gl.glRotated(90,0,1,0);
		gl.glRotated(30,1,0,0);
			eyeLidLeft.drawSphere(SCALE, new boolean[]{false, false, false, true}, 0.005f, new double[]{0.8,0.8,0.8},
					 					 new double[]{0,0,0}, FISH_UNDER_BODY_COLOUR, null, null);
		
	}
	
	/**
	 * Sets up the right front fin parts. This includes the right and left inner fins, right and left middle fin parts and the right and left outer fin parts.<br>Splitting up the drawing of the fish parts allows for easier control
	 * @author Nikkolas Diehl
	 */
	private void setRightFinParts() {		
		//Set parts - Front fins
		gl.glTranslated(0.045, 0, 0.06);
		gl.glRotated(90,0,1,0);
		gl.glRotated(10,0,0,1);
		gl.glRotated(FIN_ROTATION, 1, 0, 0);
			finRightInner.drawCylinder(SCALE, new boolean[]{false, false, false, false}, 0.001f, 0.008f, 0.01f, new double[]{1,0.2,1},
										 new double[]{0,0,0}, FISH_TOP_BODY_COLOUR, null, null);
		//Rotations and translations of middle fin is referenced off of the inner fin
		gl.glTranslated(0, 0, 0.01);
		gl.glRotated(-10, 0, 1, 0);
		gl.glRotated((FIN_ROTATION), 1, 0, 0);
			finRightMiddle.drawCylinder(SCALE, new boolean[]{false, false, false, false}, 0.0076f, 0.007f, 0.05f, new double[]{1,0.2,1},
										 new double[]{0,0,0}, FISH_TOP_BODY_COLOUR, null, null);
		//Rotations and translations of the end fin is referenced off of the middle fin
		gl.glTranslated(0, 0, 0.05);
		gl.glRotated(-12,0,1,0);
		gl.glRotated((FIN_ROTATION)*-1, 1, 0, 0);
			finRightEnd.drawCone(SCALE, new boolean[]{false, false, false, false}, 0.0073f, 0.025f, new double[]{1,0.2,1},
										 new double[]{0,0,0}, FISH_TOP_BODY_COLOUR, null, null);		
		
	}
	
	/**
	 * Sets up the right front fin parts. This includes the right and left inner fins, right and left middle fin parts and the right and left outer fin parts.<br>Splitting up the drawing of the fish parts allows for easier control
	 * @author Nikkolas Diehl
	 */
	private void setLeftFinParts() {		
		//Set parts - Front fins
		gl.glTranslated(-0.045, 0, 0.06);
		gl.glRotated(-90, 0, 1, 0);
		gl.glRotated(-10,0,0,1);
		gl.glRotated(FIN_ROTATION, 1, 0, 0);
			finLeftInner.drawCylinder(SCALE, new boolean[]{false, false, false, false}, 0.001f, 0.008f, 0.01f, new double[]{1,0.2,1},
										 new double[]{0,0,0}, FISH_TOP_BODY_COLOUR, null, null);
		//Rotations and translations of middle fin is referenced off of the inner fin
		gl.glTranslated(0, 0, 0.01);
		gl.glRotated(10,0,1,0);
		gl.glRotated((FIN_ROTATION), 1, 0, 0);
			finLeftMiddle.drawCylinder(SCALE, new boolean[]{false, false, false, false}, 0.0076f, 0.007f, 0.05f, new double[]{1,0.2,1},
										 new double[]{0,0,0}, FISH_TOP_BODY_COLOUR, null, null);
		//Rotations and translations of the end fin is referenced off of the middle fin
		gl.glTranslated(0, 0, 0.05);
		gl.glRotated(12,0,1,0);
		gl.glRotated((FIN_ROTATION)*-1, 1, 0, 0);
			finLeftEnd.drawCone(SCALE, new boolean[]{false, false, false, false}, 0.0073f, 0.025f, new double[]{1,0.2,1},
										 new double[]{0,0,0}, FISH_TOP_BODY_COLOUR, null, null);		
		
	}
	
	/**
	 * Sets up the tail parts. This includes the top and bottom inner tail part, the end tail part and the two fins. Splitting up the drawing of the fish parts allows for easier control
	 * @author Nikkolas Diehl
	 */
	private void setTailParts() {
		//Extra rotations and translation clipping for tail parts
		ArrayList<float[]> extraTopTailClipping = new ArrayList<float[]>();
		extraTopTailClipping.add(new float[]{0,-0.03f,0});

		float tailBaseThickness = 0.036f;
		
		//Set parts - Tail parts
		//Rotate and then translate
		gl.glTranslated(0, 0, 0.18);
		gl.glRotated(TAIL_ROTATION, 1, 0, 0);
			innerTailTop.drawCylinder(SCALE, new boolean[]{false, true, false, false}, 0.0525f, tailBaseThickness, 0.14f, new double[]{1,1,1},
											 new double[]{0,0,0}, FISH_TOP_BODY_COLOUR, null, extraTopTailClipping);
			innerTailBottom.drawCylinder(SCALE, new boolean[]{true, false, false, false}, 0.043f, tailBaseThickness*0.55f, 0.14f, new double[]{1,1,1},
											 new double[]{0,0,0}, FISH_UNDER_BODY_COLOUR, null, null);
		
		//Rotations and translations of the end tail part is referenced off of the inner tail parts
		gl.glTranslated(0, -0.007, 0.135);
		gl.glRotated((TAIL_ROTATION*-0.5), 1, 0, 0);
			outerTail.drawCylinder(SCALE, new boolean[]{false, false, false, false}, tailBaseThickness*0.55f, 0.0f, 0.06f, new double[]{1,0.7,1},
											 new double[]{0,0,0}, FISH_TOP_BODY_COLOUR, null, null);

		//Rotations and translations of the tail fins parts are referenced off of the outer tail part
		gl.glTranslated(0, 0, 0.045f);
		gl.glRotated(80, 0, 1, 0);
			tailFinRight.drawCone(SCALE, new boolean[]{false, false, false, false}, 0.013f, 0.12f, new double[]{1,0.2,1},
											 new double[]{0,0,0}, FISH_TOP_BODY_COLOUR, null, null);
		gl.glRotated(-160, 0, 1, 0);
			tailFinLeft.drawCone(SCALE, new boolean[]{false, false, false, false}, 0.013f, 0.12f, new double[]{1,0.2,1},
											 new double[]{0,0,0}, FISH_TOP_BODY_COLOUR, null, null);
		
	}
	
}
