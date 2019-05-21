package itemCreation;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

import particleSystems.WaterController;

public class FishController {

	//Main Variables
	private GL2 gl;
	private GLUT glut;
	
	//Position values
	private float[] globalFishPosition;
	private float globalDefaultYPos;
	private float[] globalFishRotation;
	private float[] globalFishJumpRotation;
	
	//Animation
	private int index = 0;
	private float animationSpeed = 0.09f;
	private float jumpAnimationSpeed = 0.4f;
	private float jumpFixAnimSpeed = 0.8f;
	private int totalRot = 0;
	private int totalJumps = 0;
	
	//Animation
	private float dyFish;
	private float dxFish;
	private float dzFish;
	private float gravity = 0.50f;
	private float waterHeight = 0;
	
	//Animator
	private FishAnimationPath animationPath;
	private int jumpFrame = 0;
	private boolean currentlyJumping = false;
	//Fish
	public Fish fish;
	
	//Particles
	public WaterController waterController;
	
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
	public FishController(GL2 gl, GLUT glut, float[] globalPos, float globalYDefaultPos, float[] globalRot) {
		//Set initial main variables
		this.gl = gl;
		this.glut = glut;

		//Set initial fish location and rotation
		this.globalFishPosition = globalPos;
		this.globalDefaultYPos = globalYDefaultPos;
		this.globalFishRotation = globalRot;
		this.globalFishJumpRotation = new float[4];
		
		//Set up object
		animationPath = new FishAnimationPath(this.gl, this.glut);
		fish = new Fish(this.gl, this.glut);
		
		//Set up random jump frame from 0th frame to 1000th frame
		jumpFrame = (int)(Math.random()*1000);
		
		//Animation randoms
		dyFish = (float)(Math.random()*0.6f+0.4f);
		dxFish = (float)(Math.random()*0.2f)-0.1f;
		dzFish = (float)(Math.random()*0.2f)-0.1f;
		
		//Particle systems
		waterController = new WaterController(this.gl, this.glut);
	}
	
	//Drawer
	/**
	 * Runs the fish controller for animation and drawing of fish
	 * @see itemCreation.Fish#drawFish(float[], float[]) - Draw fish function
	 * @see itemCreation.Fish#animateParts() - Animate the fish parts
	 * @see itemCreation.FishAnimationPath#gatherTankSize(boolean, double, double, double) - Gather tank sizes and generate animation
	 * @see itemCreation.FishAnimationPath#getCircle_Animation() - Get the ArrayList of 3D points
	 * @see itemCreation.FishAnimationPath#getCircle_Rotation() - Get the ArrayList of 3D points including the angle
	 * @see particleSystems.WaterController#drawParticles(float) - Drawing of particles
	 * 
	 * @param tankW - Tank Width for the animation pathing
	 * @param tankH - Tank Height for the animation pathing
	 * @param tankL - Tank Length for the animation pathing
	 * 
	 * @author Nikkolas Diehl
	 */
	public void renderFishController(boolean drawDebug, float tankW, float tankH, float tankL, float tankWaterHeight) {
		//Get animation path
		animationPath.gatherTankSize(drawDebug, tankW, tankH, tankL);
		waterHeight = tankWaterHeight;
		
		//Draw
		//Push
		gl.glPushMatrix();
			//GLOBAL TRANSLATION AND ROTATIONS
			gl.glTranslated(globalFishPosition[0], globalFishPosition[1], globalFishPosition[2]);
			gl.glRotated(globalFishRotation[0], globalFishRotation[1], globalFishRotation[2], globalFishRotation[3]);
			gl.glRotated(globalFishJumpRotation[0], globalFishJumpRotation[1], globalFishJumpRotation[2], globalFishJumpRotation[3]);
				fish.drawFish(globalFishPosition, globalFishRotation);
		gl.glPopMatrix();
		
		//Draw particles
		waterController.drawParticles(tankWaterHeight);
		
		//Animate
		fish.animateParts();
	}
	
	//Animation
	public void animateFish(float time, int frame, float tankW, float tankH, float tankL) {
		//Animate particles
		waterController.animateParticles(time);
		
		//If first frame, then set fish to default location
		if(frame==0) {
			globalFishPosition = animationPath.getCircle_Animation().get(index);
			globalFishRotation = animationPath.getCircle_Rotation().get(index);
		}
		
		//Set all animation
		//If its jump frame or currently jumping is true
		if(frame==jumpFrame || currentlyJumping==true) {
			currentlyJumping = true;
			
			//If fish hits water surface, create particles
			if(globalFishPosition[1]>=waterHeight-0.01f && globalFishPosition[1]<=waterHeight+0.01f) {
				waterController.addParticles(globalFishPosition,200,100);
			}
			
			//Animate jump
			globalFishPosition[0] += this.dxFish * time;
			globalFishPosition[1] += this.dyFish * time;
			globalFishPosition[2] += this.dzFish * time;
			this.dyFish -= this.gravity * time;
			
			//Animate angle
			globalFishJumpRotation = new float[]{((1-time*jumpAnimationSpeed) * globalFishJumpRotation[0] + time*jumpAnimationSpeed * 90),1,0,0};
			
			//No longer jumping
			if(globalFishPosition[1]<= globalDefaultYPos) {
				//Reset jump frame and fish dy and go back to circle animation
				this.dyFish = (float)(Math.random()*(tankH/100)+(tankH/100));
				
				//Reset dx so it should be more into tank rather than away
				if(globalFishPosition[0]<0) {
					dxFish = (float)(Math.random()*(tankW/100));
				}else if(globalFishPosition[0]>0) {
					dxFish = (float)(Math.random()*(tankW/100))-(tankW/100);
				}
				//Reset dy so it should be more into tank rather than away
				if(globalFishPosition[0]<0) {
					dzFish = (float)(Math.random()*(tankL/100));
				}else if(globalFishPosition[0]>0) {
					dzFish = (float)(Math.random()*(tankL/100))-(tankL/100);
				}
				
				currentlyJumping = false;
				totalJumps++;
				
				//Randomise to 10,000th frame now so less jumping
				jumpFrame = (int)(Math.random()*10000)+frame;
			}
			
		}//If its not the jump frame and not currently jumping, run circle animation
		else if(currentlyJumping==false){
			if(animationPath.isValuesClose(globalFishPosition, animationPath.getCircle_Animation().get(index),0.2f)) {
				//If reach end of animation
				if(index>=animationPath.getCircle_Animation().size()-1) {
					index=0;
					globalFishRotation = animationPath.getCircle_Rotation().get(index);
					totalRot++;
				}else {
					index++;
				}
			}else {
				//Get the position in the ring
				this.interpolatePosition(time, animationSpeed);

				//Get the rotation in the ring
				globalFishRotation = new float[]{((1-time*animationSpeed) * globalFishRotation[0] + time*animationSpeed * animationPath.getCircle_Rotation().get(index)[0]),
												 (animationPath.getCircle_Rotation().get(index)[1]),
												 (animationPath.getCircle_Rotation().get(index)[2]),
												 (animationPath.getCircle_Rotation().get(index)[3])};		

				//Correct jumping angle
				globalFishJumpRotation = new float[]{((1-time*jumpFixAnimSpeed) * globalFishJumpRotation[0] + time*jumpFixAnimSpeed * 0),1,0,0};
			}
		}
	}
	
	/**
	 * Interpolate from fish position to animation point
	 * 
	 * @param time - timeDelta
	 * @param animationSpeed - The animation speed to use for interpolation
	 * @param indexType - The index type (either jumping or circle)
	 * @param animationType - The type of animation ArrayList (either jumping or circle)
	 * 
	 * @author Nikkolas Diehl
	 */
	private void interpolatePosition(float time, float animationSpeed) {
		globalFishPosition = new float[]{((1-time*animationSpeed) * globalFishPosition[0] + time*animationSpeed * animationPath.getCircle_Animation().get(index)[0]),
										 ((1-time*animationSpeed) * globalFishPosition[1] + time*animationSpeed * animationPath.getCircle_Animation().get(index)[1]),
										 ((1-time*animationSpeed) * globalFishPosition[2] + time*animationSpeed * animationPath.getCircle_Animation().get(index)[2])};
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
	 * @return circleIndex
	 * 
	 * @author Nikkolas Diehl
	 */
	public int getFishPosIndex() {
		return this.index;
	}
	/**
	 * Returns the total rotation at which the fish is in then animation path
	 * @return totalRot
	 * 
	 * @author Nikkolas Diehl
	 */
	public int getTotalRevolutions() {
		return this.totalRot;
	}
	/**
	 * Returns the total jumps that the fish has completed
	 * @return totalJumps
	 * 
	 * @author Nikkolas Diehl
	 */
	public int getTotalJumps() {
		return this.totalJumps;
	}
	/**
	 * Returns the jump frame that has been chosen
	 * @return jumpFrame
	 * 
	 * @author Nikkolas Diehl
	 */
	public int getCurrentJumpFrame() {
		return this.jumpFrame;
	}
	/**
	 * Get the water controller object
	 * @return waterController
	 * 
	 * @author Nikkolas Diehl
	 */
	public WaterController getWaterController() {
		return this.waterController;
	}
}
