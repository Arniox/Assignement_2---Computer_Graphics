package itemCreation;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

import utilityFunctions.CreateQuad;
import utilityFunctions.Cube;

public class Tank {
	//Main functions
	private GL2 gl;
	private GLUT glut;
	
	//Size of tank
	private float wallWidth;
	private float wallLength;
	private float wallHeight;
	
	private float tankWidth;
	private float tankHeight;
	private float tankLength;
	
	//Tank Special variables
	public boolean widthIncreasing = false;
	public boolean widthDecreasing = false;
	public boolean lengthIncreasing = false;
	public boolean lengthDecreasing = false;
	public boolean heightIncreasing = false;
	public boolean heightDecreasing = false;
	//tank position
	public boolean tankGoingUp = false;
	public boolean tankGoingDown = false;
	
	//static values
	private static final float SCALE = 1;
	private static final float[] GLASS_COLOR4D = {0.70f, 0.94f, 1.0f, 0.2f};
	private static final float[] WATER_COLOR4D = {0.031f, 0.294f, 0.819f, 0.4f};
	private static final float PLANE_THICKNESS = 0.01f;
	
	//Object stuffs
	private float[] globalPosition = new float[3];
	
	//Objects
	Cube positiveXWall;
	Cube negativeXWall;
	Cube outwardsYWall;
	Cube inwardsYWall;
	//floor
	Cube floor;
	//water
	CreateQuad water;
	
	//constructor
	public Tank(GL2 gl, GLUT glut) {
		this.gl = gl;
		this.glut = glut;
		
		//Set up cubes
		positiveXWall = new Cube(this.gl, this.glut);
		negativeXWall = new Cube(this.gl, this.glut);
		outwardsYWall = new Cube(this.gl, this.glut);
		inwardsYWall = new Cube(this.gl, this.glut);
		floor = new Cube(this.gl, this.glut);
		water = new CreateQuad(this.gl);
	}
	
	//draw tank
	public void drawTank(float W, float H, float L, float[] globalPos) {
		//Set position
		this.globalPosition = globalPos;
		
		//Set sizes
		this.wallWidth = W/10;
		this.wallLength = L/10;
		this.wallHeight = H/10;
		//Set scaling
		this.tankWidth = W*10;
		this.tankHeight = H*10;
		this.tankLength = L*10;
		
		//formate is: scale, xScale, yScale, zScale, color4fv, translated[], rotated[]
		this.positiveXWall.drawCube(SCALE, PLANE_THICKNESS, wallHeight, wallLength, GLASS_COLOR4D, 
				new double[]{(tankWidth/2)+globalPosition[0],
							  globalPosition[1],
							  globalPosition[2]}, new double[]{0,0,0,0});
		this.negativeXWall.drawCube(SCALE, PLANE_THICKNESS, wallHeight, wallLength, GLASS_COLOR4D, 
				new double[]{((-tankWidth)/2)+globalPosition[0],
						      globalPosition[1],
						      globalPosition[2]}, new double[]{0,0,0,0});
		this.outwardsYWall.drawCube(SCALE, PLANE_THICKNESS, wallHeight, wallWidth, GLASS_COLOR4D, 
				new double[]{(tankLength/2)+globalPosition[0],
							  globalPosition[1],
							  globalPosition[2]}, new double[]{90,0,1,0});
		this.inwardsYWall.drawCube(SCALE, PLANE_THICKNESS, wallHeight, wallWidth, GLASS_COLOR4D, 
				new double[]{((-tankLength)/2)+globalPosition[0],
							  globalPosition[1],
							  globalPosition[2]}, new double[]{90,0,1,0});
		
		//floor
		this.floor.drawCube(SCALE, wallWidth, PLANE_THICKNESS, wallLength, GLASS_COLOR4D, 
				new double[]{globalPosition[0],
							  globalPosition[1],
							  globalPosition[2]}, new double[]{0,0,0,0});
		
		//Water
		this.water.drawQuad((wallWidth/2), wallHeight, (wallLength/2), 0.9f, WATER_COLOR4D, PLANE_THICKNESS);
	}
	
	//Getters
	public float[] getWallSizes() {
		return new float[]{this.wallWidth, this.wallHeight, this.wallLength};
	}
	public float[] getTankSizes() {
		return new float[]{this.tankWidth, this.tankHeight, this.tankLength};
	}
	
}
