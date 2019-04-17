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
	private float interp = 0;
	
	//Animator
	private FishAnimationPath animationPath;
	//Fish
	public Fish fish;
	
	//Constructor
	/**
	 * Constructs the fish controller, the animation pathing for the fish and the entire fish
	 * @see itemCreation.FishAnimationPath#FishAnimationPath(GL2, GLUT) - Constructor for FishAnimationPath
	 * @see itemCreation.Fish#Fish(GL2, GLUT) - Constructor for Fish
	 * 
	 * @param gl - GL2 variable for the fish controller
	 * @param glut - GLUT variable for the fish controller
	 * @param globalPos - Global position of the fish (initial state)
	 * @param globalRot - Global rotation of the fish (initial state)
	 * 
	 * @author Nikkolas Diehl
	 */
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
	/**
	 * Runs the fish controller for animation and drawing of fish
	 * @see itemCreation.Fish#drawFish(float[], float[]) - Draw fish function
	 * @see itemCreation.Fish#animateParts() - Animate the fish parts
	 * @see itemCreation.FishAnimationPath#gatherTankSize(boolean, double, double, double) - Gather tank sizes and generate animation
	 * @see itemCreation.FishAnimationPath#getCircle_Animation() - Get the ArrayList of 3D points
	 * @see itemCreation.FishAnimationPath#getCircle_Rotation() - Get the ArrayList of 3D points including the angle
	 * 
	 * @param tankW - Tank Width for the animation pathing
	 * @param tankH - Tank Height for the animation pathing
	 * @param tankL - Tank Length for the animation pathing
	 * 
	 * @author Nikkolas Diehl
	 */
	public void renderFishController(float tankW, float tankH, float tankL) {
		//Get animation path
		animationPath.gatherTankSize(true, tankW, tankH, tankL);

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
	
	//Animation
	public void animateFish(double time, int frame) {
		//If first frame, then set fish to default location
		if(frame==0) {
			globalFishPosition = animationPath.getCircle_Animation().get(index);
			globalFishRotation = animationPath.getCircle_Rotation().get(index);
		}
		if(animationPath.isValuesClose(globalFishPosition, animationPath.getCircle_Animation().get(index))) {
			if(index>=animationPath.getCircle_Animation().size()-1) {
				index=0;
			}else {
				index++;
			}
			interp = 0.0f;
		}else {
			interp += 0.0008f;
			if(interp >= 1.0f) interp = 0.0f;
			//Get the position in the ring
			globalFishPosition = new float[]{((1-interp) * globalFishPosition[0] + interp * animationPath.getCircle_Animation().get(index)[0]),
											 ((1-interp) * globalFishPosition[1] + interp * animationPath.getCircle_Animation().get(index)[1]),
											 ((1-interp) * globalFishPosition[2] + interp * animationPath.getCircle_Animation().get(index)[2])};

			//Get the rortation in the ring
			if(animationPath.getCircle_Rotation().get(index)[0]<=-175) {
				globalFishRotation = new float[]{(animationPath.getCircle_Rotation().get(index)[0]*-1),
					 							 (animationPath.getCircle_Rotation().get(index)[1]),
					 							 (animationPath.getCircle_Rotation().get(index)[2]),
					 							 (animationPath.getCircle_Rotation().get(index)[3])};
			}else{
				globalFishRotation = new float[]{((1-interp) * globalFishRotation[0] + interp * animationPath.getCircle_Rotation().get(index)[0]),
												 (animationPath.getCircle_Rotation().get(index)[1]),
												 (animationPath.getCircle_Rotation().get(index)[2]),
												 (animationPath.getCircle_Rotation().get(index)[3])};
			}
			
		}
	}
	
	//Getters
	/**
	 * Returns the global fish position
	 * @return globalFishPosition
	 * 
	 * @author Nikkolas Diehl
	 */
	public float[] getGlobalFishPos() {
		return this.globalFishPosition;
	}
	/**
	 * Returns the global fish rotation
	 * @return globalFishRotation
	 * 
	 * @author Nikkolas Diehl
	 */
	public float[] getGlobalFishRot() {
		return this.globalFishRotation;
	}
	/**
	 * Returns the index at which the fish is in then animation path
	 * @return index
	 * 
	 * @author Nikkolas Diehl
	 */
	public int getFishPosIndex() {
		return this.index;
	}
}
