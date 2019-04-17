package mainFunctions;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.gl2.GLUT;

import itemCreation.Fish;
import itemCreation.FishController;
import itemCreation.Tank;
import utilityFunctions.CoordinateAxes;
import utilityFunctions.Timing;


public class Main implements GLEventListener, KeyListener{
	private static GLCanvas canvas;
	private static GL2 gl;
	private static GLUT glut;
	
	//Set up public variables
	public Timing time;
	public int frame;
	
	//Objects - Tank
	private Tank tank;
	private FishController fishController;
	//tank scaling
	private float tankWidth = 15f;
	private float tankHeight = 8f;
	private float tankLength = 15f;
	private float[] tankGlobalPos = {0f,(tankHeight/(tankHeight*2)),0f};
	
	//Fish parts
	float xPosition = 0f;
	float yPosition = (tankHeight/10)/2;
	float zPosition = 0f;
	private float[] fishGlobalPos = {xPosition,yPosition,zPosition};
	private float[] fishGlobalRotation = {45f,0,1,0};
	
	//track-ball camera
	private TrackballCamera camera;
	//Axis
	private CoordinateAxes coordinateAxis;
	
	//Main
	public static void main(String[] args) {
		//Build GUIView which contains everything
		Frame frame = new Frame("Fish Tank");
		
		//Set up GL info
		GLProfile profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities capabilities = new GLCapabilities(profile);
		canvas = new GLCanvas(capabilities);
		
		//Canvas stuff
		Main fishTank = new Main();
		canvas.addGLEventListener(fishTank);
		canvas.addKeyListener(fishTank);
		frame.add(canvas);
		
		frame.setSize(800, 800);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setAlwaysOnTop(false);
		frame.setResizable(true);
		
		//Animator set up
		final Animator animator = new Animator(canvas);
		
		//Window listener
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				new Thread(new Runnable() {
					public void run() {
						animator.stop();
						System.exit(0);
					}
				}).start();
			}
		});
		
		//Last setup
		animator.start();
		canvas.requestFocusInWindow();

	}

	@Override
	public void display(GLAutoDrawable drawable) {
		time.count();
		
		//Update fish position:
		fishGlobalPos[0] = xPosition;
		fishGlobalPos[1] = yPosition;
		fishGlobalPos[2] = zPosition;
		
		// select and clear the model-view matrix
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();

		//Modular resizing
		this.useKeyPresses();
		
		//drawing
			//Camera
			camera.draw(gl, true);
			//Axis
			coordinateAxis.drawAxes(fishController.getGlobalFishPos(), fishController.getGlobalFishRot(), fishController.getFishPosIndex());
			//Draw all other non transparent objects
			fishController.renderFishController(tankWidth, tankHeight, tankLength);
			//Draw transparent objects last
			gl.glEnable(GL2.GL_BLEND);
			gl.glDepthMask(false);
				//Draw transparent objects with no depth testing and blending for true transparancy
				tank.drawTank(tankWidth, tankHeight, tankLength, tankGlobalPos);
			gl.glDepthMask(true);
			gl.glDisable(GL2.GL_BLEND);
			
			//Animate
			fishController.animateFish(time.delta, frame);

		gl.glFlush();

		//Iterate frame count
		frame++;
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		gl = drawable.getGL().getGL2();
		glut = new GLUT();
		//enable V-sync
		gl.setSwapInterval(1);

		//Set up the drawing area and shading mode
		gl.glClearColor(0.8f, 0.8f, 0.8f, 1.0f);
		gl.glShadeModel(GL2.GL_SMOOTH);
		
		//Set up the camera
		camera = new TrackballCamera(canvas);
		camera.setDistance(1.5);
		camera.setFieldOfView(60);
		camera.setLookAt(0, 0 ,0);
		
		//Create Axis
		coordinateAxis = new CoordinateAxes(gl, glut);
		
	    //Initiating Code
		time = new Timing();
		
		//set up objects
		tank = new Tank(gl, glut);
		fishController = new FishController(gl, glut, fishGlobalPos, fishGlobalRotation);
		
		//use the lights
		this.lights(gl);
		
		//Console output
		System.out.println("Commands:");
		System.out.println("W: to increase length, S: to decrease length");
		System.out.println("A: to increase width, D: to decrease width");
		System.out.println("=: to increase width, -: to decrease width");
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		camera.newWindowSize(width, height);
	}
	
	private void lights(GL2 gl) {
		// lighting stuff
		float ambient[] = { 0, 0, 0, 1 };
		float diffuse[] = {1f, 1f, 1f, 1 };
		float specular[] = { 1, 1, 1, 1 };
		
		float[] ambientLight = { 0.1f, 0.1f, 0.1f,0f };  // weak RED ambient 
		gl.glLightfv(GL2.GL_LIGHT3, GL2.GL_AMBIENT, ambientLight, 0); 
		
		float position0[] = { 5, 5, 5, 0 };
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, position0, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambient, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuse, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, specular, 0);
		
		float position1[] = { -10, -10, -10, 0 };
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, position1, 0);
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT, ambient, 0);
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, diffuse, 0);
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPECULAR, specular, 0);
		
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_LIGHT0);
		gl.glEnable(GL2.GL_LIGHT1);
	
		//lets use use standard color functions
		gl.glEnable(GL2.GL_COLOR_MATERIAL);
		//normalise the surface normals for lighting calculations
		gl.glEnable(GL2.GL_NORMALIZE);
	}

	//Test for key events and change objects
	public void useKeyPresses() {
		//Modular re-sizing
		if(tank.widthIncreasing) {
			this.tankWidth += 0.1f;
		}
		if(tank.widthDecreasing) {
			this.tankWidth -= 0.1f;
		}
		if(tank.lengthIncreasing) {
			this.tankLength += 0.1f;
		}
		if(tank.lengthDecreasing) {
			this.tankLength -= 0.1f;
		}
		if(tank.heightIncreasing) {
			this.tankHeight += 0.1f;
		}
		if(tank.heightDecreasing) {
			this.tankHeight -= 0.1f;
		}
	}
	
	//key events
	@Override
	public void keyPressed(KeyEvent e) {
		//Check what is increasing and set to true
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			tank.lengthIncreasing = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			tank.lengthDecreasing = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			tank.widthIncreasing = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			tank.widthDecreasing = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_EQUALS) {
			tank.heightIncreasing = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_MINUS) {
			tank.heightDecreasing = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//Check what is no longer increasing and set to false
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			tank.lengthIncreasing = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			tank.lengthDecreasing = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			tank.widthIncreasing = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			tank.widthDecreasing = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_EQUALS) {
			tank.heightIncreasing = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_MINUS) {
			tank.heightDecreasing = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

}
