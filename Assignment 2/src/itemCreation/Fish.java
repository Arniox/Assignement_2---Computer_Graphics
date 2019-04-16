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
		//Set up objects - Front fints
		finRightInner = new Cylinder(this.gl, this.glu);
		finLeftInner = new Cylinder(this.gl, this.glu);
		finRightMiddle = new Cylinder(this.gl, this.glu);
		finLeftMiddle = new Cylinder(this.gl, this.glu);
		finRightEnd = new Cone(this.gl, this.glut);
		finLeftEnd = new Cone(this.gl, this.glut);
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
		
			//Set up main parts
			this.setMainBodyParts();
			this.setEyeParts();
			
			//Rotations for right fin animations
			gl.glPushMatrix();
			gl.glRotated(-0, 0, 0, 1);
				this.setRightFinParts();
			gl.glPopMatrix();
			
			//Rotations for left fin animations
			gl.glPushMatrix();
			gl.glRotated(0, 0, 0, 1);
				this.setLeftFinParts();		
			gl.glPopMatrix();

		//Pop
		gl.glPopMatrix();
			
	}
	
	/**
	 * Sets up the main body parts. This includes body, teeth and mouth parts. Splitting up the drawing of the fish parts allows for easier control
	 * @author Nikkolas Diehl
	 */
	private void setMainBodyParts() {
		//Extra rotations and translation clipping for Body, mouth and teeth
		//Teeth
		ArrayList<float[]> extraTopTeethRotations = new ArrayList<float[]>();
		extraTopTeethRotations.add(new float[]{90,1,0,0});
		ArrayList<float[]> extraBottomTeethRotations = new ArrayList<float[]>();
		extraBottomTeethRotations.add(new float[]{84,1,0,0});
		//Bottom Mouth piece
		ArrayList<float[]> extraBottomMouthRotations = new ArrayList<float[]>();
		extraBottomMouthRotations.add(new float[]{-8f,1,0,0});
		//Top Mouth piece and body piece
		ArrayList<float[]> extraTopMouthClipping = new ArrayList<float[]>();
		extraTopMouthClipping.add(new float[]{0,-0.03f,0});
		ArrayList<float[]> extraTopMouthRotation = new ArrayList<float[]>();
		extraTopMouthRotation.add(new float[]{1.5f,1,0,0});
		
		//Set parts - Body, mouth, teeth
		//Cylinder = Scale, Clipping options, Base radius, Top radius, Height of cylinder, Size scale, Translation, Color4d, Extra rotations, Extra translations
		//Sphere = Scale, Clipping options, Sphere radius, Size scale, Translation, Colour4d, Extra rotations, Extra translations
		fishUnderBodyMiddle.drawCylinder(SCALE, new boolean[]{true, false}, 0.05f, 0.043f, 0.2f, new double[]{1,1,1},
							  			 new double[]{0,0,0},
							  			 FISH_UNDER_BODY_COLOUR, null, null);
		fishUnderBodyMouth.drawSphere(SCALE, new boolean[]{true, false, true, false}, 0.05f, new double[]{1,1,3.5},
									  	 new double[]{0,0,0}, 
									  	 FISH_UNDER_BODY_COLOUR, extraBottomMouthRotations, null);
		fishTeethTop.drawCylinder(SCALE, new boolean[]{true, false}, 0.04f, 0.037f, 0.04f, new double[]{1,3.8,1},
										 new double[]{0,0.008,-0.002},
										 FISH_TEETH_COLOUR, extraTopTeethRotations, null);
		fishTeethBottom.drawCylinder(SCALE, new boolean[]{true, false}, 0.038f, 0.039f, 0.022f, new double[]{1,3.8,1},
										 new double[]{0,-0.008,-0.002},
										 FISH_TEETH_COLOUR, extraBottomTeethRotations, null);
		fishTopBodyMiddle.drawCylinder(SCALE, new boolean[]{false, true}, 0.0584f, 0.0525f, 0.2f, new double[]{1,1,1},
										 new double[]{0,0,0},
										 FISH_TOP_BODY_COLOUR, null, extraTopMouthClipping);
		fishTopBodyMouth.drawSphere(SCALE, new boolean[]{false, true, true, false}, 0.0584f, new double[]{1,1,3.25},
										 new double[]{0,0,0},
										 FISH_TOP_BODY_COLOUR, extraTopMouthRotation, extraTopMouthClipping);
	}
	
	/**
	 * Sets up the eye parts. This includes the eye balls and eye lids. Splitting up the drawing of the fish parts allows for easier control
	 * @author Nikkolas Diehl
	 */
	private void setEyeParts() {
		//Extra rotations and translation clipping for Eyes
		//EyeLidRight
		ArrayList<float[]> extraEyeRightLidRotations = new ArrayList<float[]>();
		extraEyeRightLidRotations.add(new float[]{90,0,1,0});
		extraEyeRightLidRotations.add(new float[]{-30,1,0,0});
		//EyeLidLEft
		ArrayList<float[]> extraEyeLeftLidRotations = new ArrayList<float[]>();
		extraEyeLeftLidRotations.add(new float[]{90,0,1,0});
		extraEyeLeftLidRotations.add(new float[]{30,1,0,0});
		
		
		
		//Set parts - Eyes
		//Sphere = Scale, Clipping options, Sphere radius, Size scale, Translation, Colour4d, Extra rotations, Extra translations
		eyeLidRight.drawSphere(SCALE, new boolean[]{false, false, true, false}, 0.005f, new double[]{0.8,0.8,0.8},
										 new double[]{0.049,0.004,0.01},
										 FISH_UNDER_BODY_COLOUR, extraEyeRightLidRotations, null);
		eyeBallRight.drawSphere(SCALE, new boolean[]{false, false, false, false}, 0.004f, new double[]{0.8,0.8,0.8},
										 new double[]{0.047,0.0027,0.01},
										 FISH_EYE_BALL_COLOUR, null, null);
		eyeLidLeft.drawSphere(SCALE, new boolean[]{false, false, false, true}, 0.005f, new double[]{0.8,0.8,0.8},
										 new double[]{-0.049,0.004,0.01},
										 FISH_UNDER_BODY_COLOUR, extraEyeLeftLidRotations, null);
		eyeBallLeft.drawSphere(SCALE, new boolean[]{false, false, false, false}, 0.004f, new double[]{0.8,0.8,0.8},
										 new double[]{-0.047,0.0027,0.01},
										 FISH_EYE_BALL_COLOUR, null, null);
	}
	
	/**
	 * Sets up the right front fin parts. This includes the right and left inner fins, right and left middle fin parts and the right and left outer fin parts.<br>Splitting up the drawing of the fish parts allows for easier control
	 * @author Nikkolas Diehl
	 */
	private void setRightFinParts() {
		//Extra rotations and translation clipping for front fins
		ArrayList<float[]> extraRightFinRotations = new ArrayList<float[]>();
		extraRightFinRotations.add(new float[]{90,0,1,0});
		extraRightFinRotations.add(new float[]{10,0,0,1});
		
		//Animation referencing
		double[] finRightInnerTranslations = {0.045,0,0.06};
		//Middle fin is referenced off of inner fin
		double[] finRightMiddleTranslations = {finRightInnerTranslations[0]+0.0085,finRightInnerTranslations[1],finRightInnerTranslations[2]-0.0005};
		double[] finRightEndTranslations = {finRightMiddleTranslations[0]+0.0478,finRightMiddleTranslations[1]-0.0018,finRightMiddleTranslations[2]+0.0081};
		
		//Set parts - Front fins
		//Cylinder = Scale, Clipping options, Base radius, Top radius, Height of cylinder, Size scale, Translation, Color4d, Extra rotations, Extra translations
		//Cone = Scale, Clipping options, Base radius, Height, Size scale, Translation, Colour4d, Extra rotations, Extra clipping
		finRightInner.drawCylinder(SCALE, new boolean[]{false, false}, 0.001f, 0.008f, 0.01f, new double[]{1,0.2,1},
										 finRightInnerTranslations, FISH_TOP_BODY_COLOUR, extraRightFinRotations, null);
		//Rotations and translations of middle fin is referenced off of the inner fin
		extraRightFinRotations.add(new float[]{-10,0,1,0});
		finRightMiddle.drawCylinder(SCALE, new boolean[]{false, false}, 0.0076f, 0.007f, 0.05f, new double[]{1,0.2,1},
										 finRightMiddleTranslations, FISH_TOP_BODY_COLOUR, extraRightFinRotations, null);
		//Rotations and translations of the end fin is referenced off of the middle fin
		extraRightFinRotations.add(new float[]{-12,0,1,0});
		finRightEnd.drawCone(SCALE, new boolean[]{false, false, false, false}, 0.0073f, 0.025f, new double[]{1,0.2,1},
										 finRightEndTranslations, FISH_TOP_BODY_COLOUR, extraRightFinRotations, null);
		
	}
	
	/**
	 * Sets up the right front fin parts. This includes the right and left inner fins, right and left middle fin parts and the right and left outer fin parts.<br>Splitting up the drawing of the fish parts allows for easier control
	 * @author Nikkolas Diehl
	 */
	private void setLeftFinParts() {
		//Extra rotations and translation clipping for front fins
		ArrayList<float[]> extraLeftFinRotations = new ArrayList<float[]>();
		extraLeftFinRotations.add(new float[]{-90,0,1,0});
		extraLeftFinRotations.add(new float[]{-10,0,0,1});

		//Animation referencing
		double[] finLeftInnerTranslations = {-0.045,0,0.06};
		//Middle fin is referenced off of inner fin
		double[] finLeftMiddleTranslations = {finLeftInnerTranslations[0]-0.0085,finLeftInnerTranslations[1],finLeftInnerTranslations[2]-0.0005};
		//End fin is referenced off of middle fin
		double[] finLeftEndTranslations = {finLeftMiddleTranslations[0]-0.0478,finLeftMiddleTranslations[1]-0.0018,finLeftMiddleTranslations[2]+0.0081};
		
		//Set parts - Front fins
		//Cylinder = Scale, Clipping options, Base radius, Top radius, Height of cylinder, Size scale, Translation, Color4d, Extra rotations, Extra translations
		//Cone = Scale, Clipping options, Base radius, Height, Size scale, Translation, Colour4d, Extra rotations, Extra clipping
		finLeftInner.drawCylinder(SCALE, new boolean[]{false, false}, 0.001f, 0.008f, 0.01f, new double[]{1,0.2,1},
				 						 finLeftInnerTranslations, FISH_TOP_BODY_COLOUR, extraLeftFinRotations, null);
		//Rotations and translations of middle fin is referenced off of the inner fin
		extraLeftFinRotations.add(new float[]{10,0,1,0});
		finLeftMiddle.drawCylinder(SCALE, new boolean[]{false, false}, 0.0076f, 0.007f, 0.05f, new double[]{1,0.2,1},
										 finLeftMiddleTranslations, FISH_TOP_BODY_COLOUR, extraLeftFinRotations, null);
		//Rotations and translations of the end fin is referenced off of the middle fin
		extraLeftFinRotations.add(new float[]{12,0,1,0});
		finLeftEnd.drawCone(SCALE, new boolean[]{false, false, false, false}, 0.0073f, 0.025f, new double[]{1,0.2,1},
										 finLeftEndTranslations, FISH_TOP_BODY_COLOUR, extraLeftFinRotations, null);	
		
	}
	
}
