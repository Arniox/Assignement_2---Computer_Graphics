package itemCreation;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

public class FishController {

	//Main Variables
	private GL2 gl;
	private GLUT glut;
	
	//Position values
	private float[] globalFishPosition;
	private float[] globalFishRotation;
	
	//Animation
	private int index = 0;
	
	//Animator
	private FishAnimationPath animationPath;
	//Fish
	public Fish fish;
	
	//Constructor
	public FishController(GL2 gl, GLUT glut, float[] globalPos, float[] globalRot) {
		//Set initial main variables
		this.gl = gl;
		this.glut = glut;

		//Set initial fish location and rotation
		this.globalFishPosition = globalPos;
		this.globalFishRotation = globalRot;
		
		//Set up object
		animationPath = new FishAnimationPath(this.gl, this.glut);
		fish = new Fish(this.gl, this.glut);
	}
	
	//Drawer
	public void renderFishController(float tankW, float tankH, float tankL) {
		//Get animation path
		animationPath.gatherTankSize(true, tankW, tankH, tankL);
		
		globalFishPosition = animationPath.getCircle_Animation().get(0);
		
		//Draw
		//Push
		gl.glPushMatrix();
			//GLOBAL TRANSLATION AND ROTATIONS
			gl.glTranslated(globalFishPosition[0], globalFishPosition[1], globalFishPosition[2]);
			gl.glRotated(globalFishRotation[0], globalFishRotation[1], globalFishRotation[2], globalFishRotation[3]);
				fish.drawFish(globalFishPosition, globalFishRotation);
		gl.glPopMatrix();
			
		
		//Animate
		fish.animateParts();
	}
	
	//Getters
	public float[] getGlobalFishPos() {
		return this.globalFishPosition;
	}
}
