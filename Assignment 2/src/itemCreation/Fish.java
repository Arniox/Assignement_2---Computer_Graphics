package itemCreation;

import java.util.ArrayList;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

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
	Sphere EyeLidRight;
	Sphere EyeLidLeft;
	Sphere EyeBallRight;
	Sphere EyeBallLeft;
	
	//constructor
	public Fish(GL2 gl, GLUT glut) {
		this.gl = gl;
		this.glut = glut;
		this.glu = new GLU();
		
		//Set up objects - Body, mouth, teeth
		fishUnderBodyMiddle = new Cylinder(this.gl, this.glut, this.glu);
		fishUnderBodyMouth = new Sphere(this.gl, this.glut);
		fishTeethTop = new Cylinder(this.gl, this.glut, this.glu);
		fishTeethBottom = new Cylinder(this.gl, this.glut, this.glu);
		fishTopBodyMiddle = new Cylinder(this.gl, this.glut, this.glu);
		fishTopBodyMouth = new Sphere(this.gl, this.glut);
		//Set up objects - Eyes
		EyeLidRight = new Sphere(this.gl, this.glut);
		EyeLidLeft = new Sphere(this.gl, this.glut);
		EyeBallRight = new Sphere(this.gl, this.glut);
		EyeBallLeft = new Sphere(this.gl, this.glut);
	}
	
	//draw fish
	public void drawFish(float[] globalPos, float[] globalRotation) {
		//Set part positions
		this.fishPosition = globalPos;
		this.fishRotation = globalRotation;
		
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
		//Cylinder = Scale, Clipping options, Base radius, Top radius, Height of cylinder, Size scale, Translation, Rotation, Color4d, Extra rotations, Extra translations
		//Sphere = Scale, Clipping options, Sphere radius, Size scale, Translation, Rotation, Colour4d, Extra rotations, Extra translations
		fishUnderBodyMiddle.drawCylinder(SCALE, new boolean[]{true, false}, 0.05f, 0.043f, 0.2f, new double[]{1,1,1},
							  			 new double[]{fishPosition[0],fishPosition[1],fishPosition[2]},
							  			 new double[]{fishRotation[0], fishRotation[1], fishRotation[2], fishRotation[3]}, FISH_UNDER_BODY_COLOUR,
							  			 null, null);
		fishUnderBodyMouth.drawSphere(SCALE, new boolean[]{true, false, true, false}, 0.05f, new double[]{1,1,3.5},
									  	 new double[]{fishPosition[0],fishPosition[1],fishPosition[2]}, 
									  	 new double[]{fishRotation[0], fishRotation[1], fishRotation[2], fishRotation[3]}, FISH_UNDER_BODY_COLOUR,
									  	 extraBottomMouthRotations, null);
		fishTeethTop.drawCylinder(SCALE, new boolean[]{true, false}, 0.04f, 0.037f, 0.04f, new double[]{1,3.8,1},
										 new double[]{fishPosition[0],fishPosition[1]+0.008,fishPosition[2]+0.01},
										 new double[]{fishRotation[0], fishRotation[1], fishRotation[2], fishRotation[3]}, FISH_TEETH_COLOUR,
										 extraTopTeethRotations, null);
		fishTeethBottom.drawCylinder(SCALE, new boolean[]{true, false}, 0.038f, 0.039f, 0.022f, new double[]{1,3.8,1},
										 new double[]{fishPosition[0],fishPosition[1]-0.008,fishPosition[2]+0.01},
										 new double[]{fishRotation[0], fishRotation[1], fishRotation[2], fishRotation[3]}, FISH_TEETH_COLOUR,
										 extraBottomTeethRotations, null);
		fishTopBodyMiddle.drawCylinder(SCALE, new boolean[]{false, true}, 0.0584f, 0.0525f, 0.2f, new double[]{1,1,1},
										 new double[]{fishPosition[0],fishPosition[1],fishPosition[2]},
										 new double[]{fishRotation[0], fishRotation[1], fishRotation[2], fishRotation[3]}, FISH_TOP_BODY_COLOUR,
										 null, extraTopMouthClipping);
		fishTopBodyMouth.drawSphere(SCALE, new boolean[]{false, true, true, false}, 0.0584f, new double[]{1,1,3.25},
										 new double[]{fishPosition[0],fishPosition[1],fishPosition[2]},
										 new double[]{fishRotation[0], fishRotation[1], fishRotation[2], fishRotation[3]}, FISH_TOP_BODY_COLOUR,
										 extraTopMouthRotation, extraTopMouthClipping);
		
		//Extra rotations and translation clipping for Eyes
		//EyeLidRight
		ArrayList<float[]> extraEyeLidRightRotations = new ArrayList<float[]>();
		extraEyeLidRightRotations.add(new float[]{90,0,1,0});
		
		
		//Set parts - Eyes
		//Sphere = Scale, Clipping options, Sphere radius, Size scale, Translation, Rotation, Colour4d, Extra rotations, Extra translations
		EyeLidRight.drawSphere(SCALE, new boolean[]{false, false, true, false}, 0.005f, new double[]{1,1,1},
										 new double[]{fishPosition[0]+0.1,fishPosition[1],fishPosition[2]},
										 new double[]{fishRotation[0], fishRotation[1], fishRotation[2], fishRotation[3]}, FISH_UNDER_BODY_COLOUR,
										 extraEyeLidRightRotations, null);
		
		
	}
	
}
