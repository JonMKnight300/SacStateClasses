package a4;

import java.nio.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.lang.Math;
import javax.swing.*;
import java.awt.Color;

import java.io.*;
import static com.jogamp.opengl.GL4.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.texture.*;
import com.jogamp.common.nio.Buffers;
import org.joml.*;
import com.jogamp.opengl.util.*;
import com.jogamp.opengl.GLContext;

import static com.jogamp.opengl.GL.GL_ARRAY_BUFFER;
import static com.jogamp.opengl.GL.GL_CCW;
import static com.jogamp.opengl.GL.GL_CULL_FACE;
import static com.jogamp.opengl.GL.GL_DEPTH_TEST;
import static com.jogamp.opengl.GL.GL_FLOAT;
import static com.jogamp.opengl.GL.GL_TEXTURE0;
import static com.jogamp.opengl.GL.GL_TEXTURE_2D;
import static com.jogamp.opengl.GL.GL_TRIANGLES;


public class Starter extends JFrame implements GLEventListener, MouseListener, MouseMotionListener, MouseWheelListener
{
	private GLCanvas myCanvas;
	private double startTime = 0.0;
	private double elapsedTime;
	private int renderingProgram1, renderingProgram2, renderingProgramCubeMap, renderingProgramBlend, renderingProgramGeom, 
	renderingProgramAxis, renderingProgramFog, renderingProgramNoise, renderingProgramBump, renderingProgramEnviron;
	private int vao[] = new int[1];
	private int vbo[] = new int[120];
	
	private boolean axisOn = true;
	private boolean mainLightOn = true;
	private boolean rotateOn = false;
	boolean render1 = true;
	
	private Vector3f initialLightLoc = new Vector3f(5.0f, 15.0f, -8.0f);
	private float correction = 0.0f;
	private float inLightX, inLightY, inLightZ;
	private float amt = 0.0f;
	private float BlueAmtX = 0.0f;
	private float BlueAmtY = 0.0f;
	private float BlueAmtZ = 0.0f;
	
	//Camera stuff
	private Camera StartingView = new Camera(-1, -1, -3);
	private Camera3 camera3 = new Camera3();
	private Camera StartingView2 = new Camera(-70.0f, -80.0f, 250.0f);
	private Camera3 camera4 = new Camera3();
	private float shipCamLocX, shipCamLocY, shipCamLocZ;
	
	// white light properties
	private float[] globalAmbient = new float[] { 0.7f, 0.7f, 0.7f, 1.0f };
	private float[] lightAmbient = new float[] { 0.0f, 0.0f, 0.0f, 1.0f };
	private float[] lightDiffuse = new float[] { 1.0f, 1.0f, 1.0f, 1.0f };
	private float[] lightSpecular = new float[] { 1.0f, 1.0f, 1.0f, 1.0f };

	// Materials
	private float[] GmatAmb = Utils.goldAmbient();
	private float[] GmatDif = Utils.goldDiffuse();
	private float[] GmatSpe = Utils.goldSpecular();
	private float GmatShi = Utils.goldShininess();
	
	private float[] BmatAmb = Utils.bronzeAmbient();
	private float[] BmatDif = Utils.bronzeDiffuse();
	private float[] BmatSpe = Utils.bronzeSpecular();
	private float BmatShi = Utils.bronzeShininess();

	private float[] IGambient = Utils.silverAmbient();
	private float[] IGdiffuse = Utils.silverDiffuse();
	private float[] IGspecular = Utils.silverSpecular();
	private float IGshininess = Utils.silverShininess();
	

    private float[] armorambient = {0.0f,0.0f,0.0f,1.0f};
    private float[] armordiffuse = {1.0f,1.0f,1.0f,1.0f};
    private float[] armorspecular =  {1.0f,1.0f,1.0f,1.0f};
    private float armorshininess = 10f;
    
    private float[] moonambient = {1.0f,1.0f,1.0f,1.0f};
    private float[] moondiffuse = {0.1f,0.1f,0.1f,1.0f};
    private float[] moonspecular =  {1.0f,1.0f,1.0f,1.0f};
    private float moonshininess = 10f;
    
    private float[] Droidamb={ 0.05f,0.05f,0.05f,1.0f };
    private float[] Droiddiff={0.78f,0.64f,0.64f,1.0f};
    private float[] Droidspec={0.4f,0.55f,0.37f,1.0f};
    private float Droidshine=70f;
    
    private float[] cloudambient = {0.9f,0.88f,0.95f,1.0f};
    private float[] clouddiffuse = {0.8f,0.9f,1.0f,1.0f};
    private float[] cloudspecular =  {0.2f,0.2f,0.4f,1.0f};
    private float cloudshininess = 50f;
    
    private float[] towerambient = {0.0f,0.0f,0.0f,1.0f};
    private float[] towerdiffuse = {0.1f,0.1f,0.1f,1.0f};
    private float[] towerspecular = {0.2f,0.2f,0.4f,1.0f};
    private float towershininess = 10f;
    
    private float[] neatambient = { 0.05f,0.05f,0.05f,1.0f };
    private float[] neatdiffuse = {0.1f,0.1f,0.1f,1.0f};
    private float[] neatspecular = { 0.7f,0.7f,0.7f,1.0f};
    private float neatshininess = 2f;
    
    private float[] RedTronamb={0.94f,0.16f,0.11f,1.0f};
    private float[] RedTrondiff={0.21f,0.0f,0.07568f,1.0f};
    private float[] RedTronspec={0.633f,0.727811f,0.633f,1.0f};
    private float RedTronshine=70f;
    
    private float[] BlueTronamb={0.51f,0.71f,1.0f,1.0f};
    private float[] BlueTrondiff={0.29f,0.29f,0.07568f,1.0f};
    private float[] BlueTronspec={0.633f,0.727811f,0.633f,1.0f};
    private float BlueTronshine=70f;
    
    private float[] GreenTronamb={0.0215f,0.47f,0.0215f,1.0f};
    private float[] GreenTrondiff={0.07568f,0.69f,0.07568f,1.0f};
    private float[] GreenTronspec={0.24f,0.727811f,0.61f,1.0f};
    private float GreenTronshine=41f;
    
    private float[] WallTronamb={0.28f,0.29f,0.06f,1.0f};
    private float[] WallTrondiff={0.92f,0.41f,0.32f,1.0f};
    private float[] WallTronspec={0.8f,0.64f,0.1f,1.0f};
    private float WallTronshine=30f;
    
    private float[] Blackamb={0.0f,0.0f,0.0f,1.0f};
    private float[] Blackdiff={0.9f,0.0f,0.0f,1.0f};
    private float[] Blackspec={0.0f,0.0f,0.0f,1.0f};
    private float Blackshine=128f;
    
    float[] birdbuil_ambient ={ 0.23125f, 0.23125f, 0.23125f, 1.0f };
    float[] birdbuil_diffuse ={0.2775f, 0.2775f, 0.2775f, 1.0f };
    float[] birdbuil_specular ={0.773911f, 0.773911f, 0.773911f, 1.0f };
    float birdbuilshine =89.6f ;
	
		//Defining locations
	private float objLocX, objLocY, objLocZ;
	private float moonLocX, moonLocY, moonLocZ;
	private float spaceGateLocX, spaceGateLocY, spaceGateLocZ;
	private float ig88LocX, ig88LocY, ig88LocZ;
	private float backHangerLocX, backHangerLocY, backHangerLocZ;
	private float BalconyDroidLocX, BalconyDroidLocY, BalconyDroidLocZ;
	private float bobaFettLocX, bobaFettLocY, bobaFettLocZ;
	private float BirdBuildingLocX, BirdBuildingLocY, BirdBuildingLocZ;
	private float theWallLocX, theWallLocY, theWallLocZ;
	private float BillBoardBuildingLocX, BillBoardBuildingLocY, BillBoardBuildingLocZ;
	private float CloudyLayerLocX, CloudyLayerLocY, CloudyLayerLocZ;
	private int FettmambLoc, FettmdiffLoc, FettmspecLoc, FettmshiLoc; 
	private int BirdBmambLoc, BirdBmdiffLoc, BirdBmspecLoc, BirdBmshiLoc; 
	private float DroidAloneLocX, DroidAloneLocY, DroidAloneLocZ;
	private float blueGhostLocX, blueGhostLocY, blueGhostLocZ;
	private float HoloTableLocX, HoloTableLocY, HoloTableLocZ;
	private float HoloTableLoc2X, HoloTableLoc2Y, HoloTableLoc2Z;
	private float HoloTableLoc3X, HoloTableLoc3Y, HoloTableLoc3Z;
	private float WallArmorLocX, WallArmorLocY, WallArmorLocZ;
	private float MasteChamberLocX, MasteChamberLocY, MasteChamberLocZ;
	private float DroidLightLocX, DroidLightLocY, DroidLightLocZ;
	private float BalconyExtensionLocX, BalconyExtensionLocY, BalconyExtensionLocZ;
	private float BalconyExtensionV3BottomLocX, BalconyExtensionV3BottomLocY, BalconyExtensionV3BottomLocZ;
	private float MirrorGate_FrameLocX, MirrorGate_FrameLocY, MirrorGate_FrameLocZ;
	private float terLocX, terLocY, terLocZ;
	private float flyingStarLocX, flyingStarLocY, flyingStarLocZ;
	private float FurryKnightLocX, FurryKnightLocY, FurryKnightLocZ;
	private float MasterChamberDoorLocX, MasterChamberDoorLocY, MasterChamberDoorLocZ;
	
	private float[] thisAmb, thisDif, thisSpe, matAmb, matDif, matSpe;
	private float thisShi, matShi;
	
	// shadow stuff
	private int scSizeX, scSizeY;
	private int [] shadowTex = new int[50];
	private int [] shadowBuffer = new int[50];
	private Matrix4f lightVmat = new Matrix4f();
	private Matrix4f lightPmat = new Matrix4f();
	private Matrix4f shadowMVP1 = new Matrix4f();
	private Matrix4f shadowMVP2 = new Matrix4f();
	private Matrix4f b = new Matrix4f();

	// allocate variables for display() function
	private FloatBuffer vals = Buffers.newDirectFloatBuffer(1500);
	private Matrix4f pMat = new Matrix4f();  // perspective matrix
	private Matrix4f vMat = new Matrix4f();  // view matrix
	private Matrix4f mMat = new Matrix4f();  // model matrix
	private Matrix4f mvMat = new Matrix4f(); // model-view matrix
	private Matrix4f invTrMat = new Matrix4f(); // inverse-transpose
	private Matrix4f texRotMat = new Matrix4f(); // rotation for noise texture
	private int mvLoc, projLoc, nLoc, sLoc, vLoc, mvLoc2, texRotLoc, alphaLoc, flipLoc;
	private int globalAmbLoc, ambLoc, diffLoc, specLoc, posLoc, mambLoc, mdiffLoc, mspecLoc, mshiLoc;
	private float aspect;
	private double tf;
	
	private Vector3f currentLightPos = new Vector3f();
	private float[] lightPos = new float[3];
	private Vector3f origin = new Vector3f(0.0f, 0.0f, 0.0f);
	private Vector3f up = new Vector3f(0.0f, 1.0f, 0.0f);
	
	//Texture
	private int shuttleTexture;
	private int cyborgTexture;
	private int redTexture;
	private int greenTexture;
	private int blueTexture;
	private int moonTexture;
	private int skyboxTexture;
	private int skybox2Texture;
	private int spaceGateTexture;
	private int igTexture;
	private int backHangerTexture;
	private int BalconyDroidTexture;
	private int bobaFettTexture;
	private int BirdBuildingTexture;
	private int theWallTexture;
	private int BillBoardBuildingTexture;
	private int CloudyLayerTexture;
	private int DroidAloneTexture;
	private int blueGhostTexture;
	private int TronChessTexture;
	private int HoloTableTexture;
	private int RedTronTexture;
	private int BlueTronTexture;
	private int WallArmorTexture;
	private int BalconyExtensionTexture;
	private int BalconyExtensionV3BottomTexture;
	private int BirdBuildingExtensionTexture;
	private int TheWall_BottomTexture;
	private int BillBoardBuilding_BottomTexture;
	private int BirdBuildingExtensionV1_BottomTexture;
	private int BackHangerExtensionTexture;
	private int backgroundFOGTexture;
	private int MasterChamberTexture;
	private int HoloTable_bottomTexture;
	private int MirrorGateTexture;
	private int MirrorGate_FrameTexture;
	private int FurryKnightTexture;
	private int MasterLandingChamber_FrontTexture;
	private int MasterChamberDoorTexture;
	private int MasterChamberDoorBackTexture;
	private int MasterLandingChamberBELOWTexture;

	private int noiseTexture;
	private int noiseHeight= 300;
	private int noiseWidth = 300;
	private int noiseDepth = 300;
	private double[][][] noise = new double[noiseHeight][noiseWidth][noiseDepth];
	private java.util.Random random = new java.util.Random();


	//Imported Model stuff
	private int numObjVertices;
	private ImportedModel myModel;

	private int spaceGateNumObjVertices;
	private ImportedModel spaceGateModel;

	private int igNumObjigVertices;
	private ImportedModel igModel;

	private int backHangerNumObjVertices;
	private ImportedModel backHangerModel;

	private int BalconyDroidNumObjVertices;
	private ImportedModel BalconyDroidModel;

	private int bobaFettNumObjVertices;
	private ImportedModel bobaFettModel;

	private int BirdBuildingNumObjVertices;
	private ImportedModel BirdBuildingModel;

	private int theWallNumObjVertices;
	private ImportedModel theWallModel;

	private int BillBoardBuildingNumObjVertices;
	private ImportedModel BillBoardBuildingModel;

	private int CloudyLayerNumObjVertices;
	private ImportedModel CloudyLayerModel;

	private int DroidAloneNumObjVertices;
	private ImportedModel DroidAloneModel;

	private int blueGhostNumObjVertices;
	private ImportedModel blueGhostModel;

	private int TronChessNumObjVertices;
	private ImportedModel TronChessModel;

	private int HoloTableNumObjVertices;
	private ImportedModel HoloTableModel;

	private int RedTronNumObjVertices;
	private ImportedModel RedTronModel;

	private int BlueTronNumObjVertices;
	private ImportedModel BlueTronModel;

	private int WallArmorNumObjVertices;
	private ImportedModel WallArmorModel;

	private int BalconyExtensionNumObjVertices;
	private ImportedModel BalconyExtensionModel;

	private int BalconyExtensionV3BottomNumObjVertices;
	private ImportedModel BalconyExtensionV3BottomModel;

	private int BirdBuildingExtensionNumObjVertices;
	private ImportedModel BirdBuildingExtensionModel;

	private int TheWall_BottomNumObjVertices;
	private ImportedModel TheWall_BottomModel;

	private int BillBoardBuilding_BottomNumObjVertices;
	private ImportedModel BillBoardBuilding_BottomModel;

	private int BirdBuildingExtensionV1_BottomNumObjVertices;
	private ImportedModel BirdBuildingExtensionV1_BottomModel;

	private int BackHangerExtensionNumObjVertices;
	private ImportedModel BackHangerExtensionModel;

	private int backgroundFOGNumObjVertices;
	private ImportedModel backgroundFOGModel;

	private ImportedModel masterChamber;
	private int nummasterChamberVertices;

	private ImportedModel flyingStar;
	private int numflyingStarVertices;

	private int HoloTable_bottomNumObjVertices;
	private ImportedModel HoloTable_bottomModel;

	private int MirrorGateNumObjVertices;
	private ImportedModel MirrorGateModel;

	private int MirrorGate_FrameNumObjVertices;
	private ImportedModel MirrorGate_FrameModel;

	private int FurryKnightNumObjVertices;
	private ImportedModel FurryKnightModel;

	private int MasterLandingChamber_FrontNumObjVertices;
	private ImportedModel MasterLandingChamber_FrontModel;

	private int MasterChamberDoorNumObjVertices;
	private ImportedModel MasterChamberDoorModel;

	private int MasterChamberDoorBackNumObjVertices;
	private ImportedModel MasterChamberDoorBackModel;

	private int MasterLandingChamberBELOWNumObjVertices;
	private ImportedModel MasterLandingChamberBELOWModel;

	//Fog and Height Map stuff
	private ImportedModel ground;
	private int numGroundVertices;

	private int rockyTexture;
	private int heightMap;

	//Sphere stuff
	private Sphere mySphere;
	private int numSphereVerts;
	
	
	public Starter()
	{	setTitle("Jon Knight: Assignment 4, Taking a Picture in the Game Room");
		setSize(1400, 1000);
		myCanvas = new GLCanvas();
		myCanvas.addGLEventListener(this);
		myCanvas.addMouseWheelListener(this);
		myCanvas.addMouseMotionListener(this);
		this.add(myCanvas);
		this.setVisible(true);

		JPanel contentPane = (JPanel) this.getContentPane();
		AbstractAction displayAxis = new DisplayTheAxis(this);
		AbstractAction changeLight = new LightOn(this);
		AbstractAction rotateOn = new rotateOn(this);
		MoveCamForward.getInstance().setCamera(camera3);
		MoveCamBackward.getInstance().setCamera(camera3);
		MoveCamRight.getInstance().setCamera(camera3);
		MoveCamLeft.getInstance().setCamera(camera3);
		MoveCamDown.getInstance().setCamera(camera3);
		MoveCamUp.getInstance().setCamera(camera3);
		PanCamRight.getInstance().setCamera(camera3);
		PanCamLeft.getInstance().setCamera(camera3);
		PitchCamUp.getInstance().setCamera(camera3);
		PitchCamDown.getInstance().setCamera(camera3);
		camControl1.getInstance().setCamera(camera3, camera4);
		camControl2.getInstance().setCamera(camera3, camera4);
		camControl3.getInstance().setCamera(camera3, camera4);

		InputMap imap = contentPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap amap = contentPane.getActionMap();
		
		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_1, 0), KeyEvent.VK_1);
		amap.put(KeyEvent.VK_1, camControl1.getInstance());
		
		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_2, 0), KeyEvent.VK_2);
		amap.put(KeyEvent.VK_2, camControl2.getInstance());
		
		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_3, 0), KeyEvent.VK_3);
		amap.put(KeyEvent.VK_3, camControl3.getInstance());
		
		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), KeyEvent.VK_SPACE);
		amap.put(KeyEvent.VK_SPACE, displayAxis);
		
		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F, 0), KeyEvent.VK_F);
		amap.put(KeyEvent.VK_F, changeLight);
		
		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_G, 0), KeyEvent.VK_G);
		amap.put(KeyEvent.VK_G, rotateOn);
		
		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), KeyEvent.VK_W);
		amap.put(KeyEvent.VK_W, MoveCamForward.getInstance());
		
		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), KeyEvent.VK_S);
		amap.put(KeyEvent.VK_S, MoveCamBackward.getInstance());
		
		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), KeyEvent.VK_D);
		amap.put(KeyEvent.VK_D, MoveCamRight.getInstance());
		
		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), KeyEvent.VK_A);
		amap.put(KeyEvent.VK_A, MoveCamLeft.getInstance());
		
		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_E, 0), KeyEvent.VK_E);
		amap.put(KeyEvent.VK_E, MoveCamDown.getInstance());
		
		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0), KeyEvent.VK_Q);
		amap.put(KeyEvent.VK_Q, MoveCamUp.getInstance());
		
		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), KeyEvent.VK_RIGHT);
		amap.put(KeyEvent.VK_RIGHT, PanCamRight.getInstance());
		
		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), KeyEvent.VK_LEFT);
		amap.put(KeyEvent.VK_LEFT, PanCamLeft.getInstance());
		
		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), KeyEvent.VK_UP);
		amap.put(KeyEvent.VK_UP, PitchCamUp.getInstance());
		
		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), KeyEvent.VK_DOWN);
		amap.put(KeyEvent.VK_DOWN, PitchCamDown.getInstance());
		
		Animator animator = new Animator(myCanvas);
		animator.start();	
	}
	

	public void display(GLAutoDrawable drawable)
	{	GL4 gl = (GL4) GLContext.getCurrentGL();
		gl.glClear(GL_COLOR_BUFFER_BIT);
		gl.glClear(GL_DEPTH_BUFFER_BIT);
		elapsedTime = System.currentTimeMillis() - startTime;
		
		currentLightPos.set(initialLightLoc);
		
		lightVmat.identity().setLookAt(currentLightPos, origin, up);	// vector from light to origin
		lightPmat.identity().setPerspective((float) Math.toRadians(60.0f), aspect, 0.1f, 1000.0f);

		gl.glBindFramebuffer(GL_FRAMEBUFFER, shadowBuffer[0]);
		gl.glFramebufferTexture(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, shadowTex[0], 0);
	
		gl.glDrawBuffer(GL_NONE);
		gl.glEnable(GL_DEPTH_TEST);
		gl.glEnable(GL_POLYGON_OFFSET_FILL);	//  for reducing
		gl.glPolygonOffset(3.0f, 5.0f);		//  shadow artifacts

		passOne();
		
		gl.glDisable(GL_POLYGON_OFFSET_FILL);	// artifact reduction, continued
		
		gl.glBindFramebuffer(GL_FRAMEBUFFER, 0);
		gl.glActiveTexture(GL_TEXTURE0);
		gl.glBindTexture(GL_TEXTURE_2D, shadowTex[0]);
	
		gl.glDrawBuffer(GL_FRONT);
		
		passTwo();
		
		
		if ((blueGhostLocZ < 1150 && camera3.getCamera() == 2) || camera3.getCamera() == 1 || camera3.getCamera() == 3)
		{
		passThree();
		}
		
		passFour();
		
		if (camera3.getCamera() == 1 || camera3.getCamera() == 3)
		{
		passFive();
		}
		
		if (camera3.getCamera() == 1 || camera3.getCamera() == 3)
		{
		passSix();
		}
		
		if (camera3.getCamera() == 1 || camera3.getCamera() == 3)
		{
		passSeven();
		}
		
		if (camera3.getCamera() == 1 || camera3.getCamera() == 3)
		{
		passEight();
		}
		
	}
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//Eight Passes for various effects and model placement
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	
	public void passOne()
	{	GL4 gl = (GL4) GLContext.getCurrentGL();
	
		gl.glUseProgram(renderingProgram1);

		
		// ---------------------- Cloud Layer
		
		thisAmb = cloudambient;
		thisDif = clouddiffuse;
		thisSpe = cloudspecular;
		thisShi = cloudshininess;

		mMat.identity();
		mMat.translate(CloudyLayerLocX, CloudyLayerLocY+1, CloudyLayerLocZ);
		mMat.scale(5f, 5f, 5f);

		shadowMVP1.identity();
		shadowMVP1.mul(lightPmat);
		shadowMVP1.mul(lightVmat);
		shadowMVP1.mul(mMat);

		gl.glUniformMatrix4fv(sLoc, 1, false, shadowMVP1.get(vals));

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[39]);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);

		gl.glEnable(GL_DEPTH_TEST);
		gl.glFrontFace(GL_LEQUAL);
		gl.glDrawArrays(GL_TRIANGLES, 0, CloudyLayerModel.getNumVertices());
		
		
		// ---------------------- Droid Alone
		
	mMat.identity();
	mMat.translate(DroidAloneLocX, DroidAloneLocY, DroidAloneLocZ);
	mMat.scale(0.7f, 0.7f, 0.7f);

	shadowMVP1.identity();
	shadowMVP1.mul(lightPmat);
	shadowMVP1.mul(lightVmat);
	shadowMVP1.mul(mMat);

	gl.glUniformMatrix4fv(sLoc, 1, false, shadowMVP1.get(vals));

	gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[42]);
	gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
	gl.glEnableVertexAttribArray(0);

	gl.glEnable(GL_DEPTH_TEST);
	gl.glDepthFunc(GL_LEQUAL);
	gl.glDrawArrays(GL_TRIANGLES, 0, DroidAloneModel.getNumVertices());


	// ---------------------- The Real IG


	mMat.identity();
	mMat.translate(ig88LocX, ig88LocY, ig88LocZ);
	mMat.scale(1.0f, 1.0f, 1.0f);
	mMat.rotateY((float)Math.toRadians(-80.0f));

	shadowMVP1.identity();
	shadowMVP1.mul(lightPmat);
	shadowMVP1.mul(lightVmat);
	shadowMVP1.mul(mMat);

	gl.glUniformMatrix4fv(sLoc, 1, false, shadowMVP1.get(vals));

	gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[18]);
	gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
	gl.glEnableVertexAttribArray(0);

	gl.glEnable(GL_DEPTH_TEST);
	gl.glFrontFace(GL_LEQUAL);
	gl.glDrawArrays(GL_TRIANGLES, 0, igModel.getNumVertices());


	// ---------------------- Back Hanger Bay


	mMat.identity();
	mMat.translate(backHangerLocX, backHangerLocY, backHangerLocZ);
	mMat.scale(1.0f, 0.7f, 0.7f);

	shadowMVP1.identity();
	shadowMVP1.mul(lightPmat);
	shadowMVP1.mul(lightVmat);
	shadowMVP1.mul(mMat);

	gl.glUniformMatrix4fv(sLoc, 1, false, shadowMVP1.get(vals));

	gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[21]);
	gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
	gl.glEnableVertexAttribArray(0);

	gl.glEnable(GL_DEPTH_TEST);
	gl.glFrontFace(GL_LEQUAL);
	gl.glDrawArrays(GL_TRIANGLES, 0, backHangerModel.getNumVertices());


	// ---------------------- Boba Fett

	mMat.identity();
	mMat.translate(bobaFettLocX, bobaFettLocY, bobaFettLocZ);
	mMat.scale(1.0f, 1.0f, 1.0f);

	shadowMVP1.identity();
	shadowMVP1.mul(lightPmat);
	shadowMVP1.mul(lightVmat);
	shadowMVP1.mul(mMat);

	gl.glUniformMatrix4fv(sLoc, 1, false, shadowMVP1.get(vals));

	gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[27]);
	gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
	gl.glEnableVertexAttribArray(0);

	gl.glEnable(GL_DEPTH_TEST);
	gl.glFrontFace(GL_LEQUAL);
	gl.glDrawArrays(GL_TRIANGLES, 0, bobaFettModel.getNumVertices());


	// ---------------------- Bird Building and Landing Pad

	mMat.identity();
	mMat.translate(BirdBuildingLocX, BirdBuildingLocY, BirdBuildingLocZ);
	mMat.scale(1.2f, 1.2f, 1.2f);

	shadowMVP1.identity();
	shadowMVP1.mul(lightPmat);
	shadowMVP1.mul(lightVmat);
	shadowMVP1.mul(mMat);

	gl.glUniformMatrix4fv(sLoc, 1, false, shadowMVP1.get(vals));

	gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[30]);
	gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
	gl.glEnableVertexAttribArray(0);

	gl.glEnable(GL_DEPTH_TEST);
	gl.glFrontFace(GL_LEQUAL);
	gl.glDrawArrays(GL_TRIANGLES, 0, BirdBuildingModel.getNumVertices());


	// Blue Ship

	mMat.identity();
	mMat.translate(blueGhostLocX, blueGhostLocY, blueGhostLocZ);
	mMat.scale(1.3f, 1.3f, 1.3f);

	 shadowMVP1.identity();
	 shadowMVP1.mul(lightPmat);
	 shadowMVP1.mul(lightVmat);
	 shadowMVP1.mul(mMat);

	 gl.glUniformMatrix4fv(sLoc, 1, false, shadowMVP1.get(vals));

	gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[45]);
	gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
	gl.glEnableVertexAttribArray(0);

	gl.glEnable(GL_DEPTH_TEST);
	gl.glFrontFace(GL_LEQUAL);
	gl.glDrawArrays(GL_TRIANGLES, 0, blueGhostModel.getNumVertices());


	// Chess board

	mMat.identity();
	mMat.translate(HoloTableLocX, HoloTableLocY, HoloTableLocZ);
	mMat.scale(0.3f, 0.3f, 0.3f);
	mMat.rotateY((float)Math.toRadians(-90.0f));
	mMat.rotateZ((float)Math.toRadians(90.0f));
	mMat.rotateX((float)Math.toRadians(180.0f));

	shadowMVP1.identity();
	shadowMVP1.mul(lightPmat);
	shadowMVP1.mul(lightVmat);
	shadowMVP1.mul(mMat);

	gl.glUniformMatrix4fv(sLoc, 1, false, shadowMVP1.get(vals));

	gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[48]);
	gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
	gl.glEnableVertexAttribArray(0);

	gl.glEnable(GL_DEPTH_TEST);
	gl.glFrontFace(GL_LEQUAL);
	gl.glDrawArrays(GL_TRIANGLES, 0, TronChessModel.getNumVertices());


	// HoloTable

	mvMat.identity();
	mMat.translate(HoloTableLocX, HoloTableLocY, HoloTableLocZ);
	mMat.scale(0.3f, 0.3f, 0.3f);
	mMat.rotateY((float)Math.toRadians(-90.0f));
	mMat.rotateZ((float)Math.toRadians(90.0f));
	mMat.rotateX((float)Math.toRadians(180.0f));

	shadowMVP1.identity();
	shadowMVP1.mul(lightPmat);
	shadowMVP1.mul(lightVmat);
	shadowMVP1.mul(mMat);

	gl.glUniformMatrix4fv(sLoc, 1, false, shadowMVP1.get(vals));

	gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[51]);
	gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
	gl.glEnableVertexAttribArray(0);

	gl.glEnable(GL_DEPTH_TEST);
	gl.glFrontFace(GL_LEQUAL);
	gl.glDrawArrays(GL_TRIANGLES, 0, HoloTableModel.getNumVertices());


	// HoloTable 2

	mvMat.identity();
	mMat.translate(HoloTableLoc2X, HoloTableLoc2Y, HoloTableLoc2Z);
	mMat.scale(0.3f, 0.3f, 0.7f);
	mMat.rotateY((float)Math.toRadians(-90.0f));
	mMat.rotateZ((float)Math.toRadians(90.0f));
	mMat.rotateX((float)Math.toRadians(180.0f));

	shadowMVP1.identity();
	shadowMVP1.mul(lightPmat);
	shadowMVP1.mul(lightVmat);
	shadowMVP1.mul(mMat);

	gl.glUniformMatrix4fv(sLoc, 1, false, shadowMVP1.get(vals));

	gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[51]);
	gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
	gl.glEnableVertexAttribArray(0);

	gl.glEnable(GL_DEPTH_TEST);
	gl.glFrontFace(GL_LEQUAL);
	gl.glDrawArrays(GL_TRIANGLES, 0, HoloTableModel.getNumVertices());


	// HoloTable 3

	mvMat.identity();
	mMat.translate(HoloTableLoc3X, HoloTableLoc3Y, HoloTableLoc3Z);
	mMat.scale(0.3f, 0.3f, 0.3f);
	mMat.rotateY((float)Math.toRadians(-90.0f));
	mMat.rotateZ((float)Math.toRadians(90.0f));
	mMat.rotateX((float)Math.toRadians(180.0f));

	shadowMVP1.identity();
	shadowMVP1.mul(lightPmat);
	shadowMVP1.mul(lightVmat);
	shadowMVP1.mul(mMat);

	gl.glUniformMatrix4fv(sLoc, 1, false, shadowMVP1.get(vals));

	gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[51]);
	gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
	gl.glEnableVertexAttribArray(0);

	gl.glEnable(GL_DEPTH_TEST);
	gl.glFrontFace(GL_LEQUAL);
	gl.glDrawArrays(GL_TRIANGLES, 0, HoloTableModel.getNumVertices());


	// Wall Armor

	mMat.identity();
	mMat.translate(WallArmorLocX, WallArmorLocY, WallArmorLocZ);
	mMat.scale(2.6f, 2.6f, 2.6f);
	mMat.rotateY((float)Math.toRadians(-180.0f));

	shadowMVP1.identity();
	shadowMVP1.mul(lightPmat);
	shadowMVP1.mul(lightVmat);
	shadowMVP1.mul(mMat);

	gl.glUniformMatrix4fv(sLoc, 1, false, shadowMVP1.get(vals));

	gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[60]);
	gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
	gl.glEnableVertexAttribArray(0);

	gl.glEnable(GL_DEPTH_TEST);
	gl.glFrontFace(GL_LEQUAL);
	gl.glDrawArrays(GL_TRIANGLES, 0, WallArmorModel.getNumVertices());


	// ----------------------  Balcony

	mMat.identity();
	mMat.translate(objLocX, objLocY, objLocZ);
	mMat.scale(1.0f, 0.7f, 0.7f);

	shadowMVP1.identity();
	shadowMVP1.mul(lightPmat);
	shadowMVP1.mul(lightVmat);
	shadowMVP1.mul(mMat);

	gl.glUniformMatrix4fv(sLoc, 1, false, shadowMVP1.get(vals));

	gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[24]);
	gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
	gl.glEnableVertexAttribArray(0);

	gl.glEnable(GL_DEPTH_TEST);
	gl.glFrontFace(GL_LEQUAL);
	gl.glDrawArrays(GL_TRIANGLES, 0, BalconyDroidModel.getNumVertices());


	//Master Chamber

	mMat.identity();
	mMat.translate(MasteChamberLocX, MasteChamberLocY, MasteChamberLocZ);
	mMat.scale(8.0f, 8.0f, 8.0f);

	shadowMVP1.identity();
	shadowMVP1.mul(lightPmat);
	shadowMVP1.mul(lightVmat);
	shadowMVP1.mul(mMat);

	gl.glUniformMatrix4fv(sLoc, 1, false, shadowMVP1.get(vals));

	gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[89]);
	gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
	gl.glEnableVertexAttribArray(0);

	gl.glEnable(GL_DEPTH_TEST);
	gl.glFrontFace(GL_LEQUAL);
	gl.glDrawArrays(GL_TRIANGLES, 0, masterChamber.getNumVertices());


	//Mirror Gate

	mMat.identity();
	mMat.translate(MirrorGate_FrameLocX, MirrorGate_FrameLocY, MirrorGate_FrameLocZ);
	mMat.scale(1.0f,1.0f, 1.0f);

	mMat.rotateX((float)Math.toRadians(180.0f));

	shadowMVP1.identity();
	shadowMVP1.mul(lightPmat);
	shadowMVP1.mul(lightVmat);
	shadowMVP1.mul(mMat);

	gl.glUniformMatrix4fv(sLoc, 1, false, shadowMVP1.get(vals));

	gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[101]);
	gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
	gl.glEnableVertexAttribArray(0);

	gl.glEnable(GL_DEPTH_TEST);
	gl.glFrontFace(GL_LEQUAL);
	gl.glDrawArrays(GL_TRIANGLES, 0, MirrorGate_FrameModel.getNumVertices());
		
	}
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


	public void passTwo()
	{	GL4 gl = (GL4) GLContext.getCurrentGL();

	
	if (camera3.getCamera() == 1)
	{
		render1 = true;
	}
	else if  (camera3.getCamera() == 2)
	{
		if (blueGhostLocY > -46)
			render1 = true;
		else 
			render1 = false;
	}
	else if (camera3.getCamera() == 3)
	{
		if (camera3.getCameraY() > -42)
			render1 = true;
		else 
			render1 = false;
	}
		
	if (render1 == true)
	{
		gl.glUseProgram(renderingProgramCubeMap);

		vLoc = gl.glGetUniformLocation(renderingProgramCubeMap, "v_matrix");
		gl.glUniformMatrix4fv(vLoc, 1, false, vMat.get(vals));

		projLoc = gl.glGetUniformLocation(renderingProgramCubeMap, "proj_matrix");
		gl.glUniformMatrix4fv(projLoc, 1, false, pMat.get(vals));
				
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[15]);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);
		
		gl.glActiveTexture(GL_TEXTURE0);
		gl.glBindTexture(GL_TEXTURE_CUBE_MAP, skyboxTexture);

		gl.glEnable(GL_CULL_FACE);
		gl.glFrontFace(GL_CCW);	     // cube is CW, but we are viewing the inside
		gl.glDisable(GL_DEPTH_TEST);
		gl.glDrawArrays(GL_TRIANGLES, 0, 36);
		gl.glEnable(GL_DEPTH_TEST);
	}
	else
	{
	gl.glUseProgram(renderingProgramCubeMap);

	vLoc = gl.glGetUniformLocation(renderingProgramCubeMap, "v_matrix");
	gl.glUniformMatrix4fv(vLoc, 1, false, vMat.get(vals));

	projLoc = gl.glGetUniformLocation(renderingProgramCubeMap, "proj_matrix");
	gl.glUniformMatrix4fv(projLoc, 1, false, pMat.get(vals));
			
	gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[15]);
	gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
	gl.glEnableVertexAttribArray(0);
	
	gl.glActiveTexture(GL_TEXTURE0);
	gl.glBindTexture(GL_TEXTURE_CUBE_MAP, skybox2Texture);

	gl.glEnable(GL_CULL_FACE);
	gl.glFrontFace(GL_CCW);	    
	gl.glDisable(GL_DEPTH_TEST);
	gl.glDrawArrays(GL_TRIANGLES, 0, 36);
	gl.glEnable(GL_DEPTH_TEST);
	}
	
	//Render everything else
	
		gl.glUseProgram(renderingProgram2);
		
		mvLoc = gl.glGetUniformLocation(renderingProgram2, "mv_matrix");
		projLoc = gl.glGetUniformLocation(renderingProgram2, "proj_matrix");
		nLoc = gl.glGetUniformLocation(renderingProgram2, "norm_matrix");
		sLoc = gl.glGetUniformLocation(renderingProgram2, "shadowMVP");
		
		pMat.identity().setPerspective((float) Math.toRadians(90.0f), aspect, 0.1f, 1800.0f);
		
		
	//Setting up Camera Schemas
		if (camera3.getCamera() == 1)
		{
		vMat.identity().set4x3(camera3.getViewMatrix()); 
		vMat.translate(StartingView.getX(), StartingView.getY(), StartingView.getZ());
		}
		else if (camera3.getCamera() == 2)
		{	
		vMat.identity().set4x3(camera3.getViewMatrix()); 
		vMat.rotateY(60);
		vMat.translate(StartingView2.getX(), StartingView2.getY(), StartingView2.getZ());
		StartingView2.setXYZ((-blueGhostLocX+3), (-blueGhostLocY-8), (-blueGhostLocZ+15));
		
		}
		else if (camera3.getCamera() == 3)
		{
		vMat.identity().set4x3(camera3.getViewMatrix()); 
		vMat.translate(StartingView.getX(), StartingView.getY(), StartingView.getZ());
		}
		
		
		currentLightPos.set(initialLightLoc);
		amt += 0.5f;
		if(rotateOn) {
		currentLightPos.rotateAxis((float)Math.toRadians(amt), 0.0f, 0.001f, 0.0f);
		}
		installLights(renderingProgram2, vMat);

		
		// Droid Alone
		
		thisAmb = IGambient;
		thisDif = IGdiffuse;
		thisSpe = IGspecular;
		thisShi = IGshininess;

		mMat.identity();
		mMat.translate(DroidAloneLocX, DroidAloneLocY, DroidAloneLocZ);
		mMat.scale(0.7f, 0.7f, 0.7f);
		
		currentLightPos.set(initialLightLoc);
		installLights(renderingProgram2, vMat);

		mvMat.identity();
		mvMat.mul(vMat);
		mvMat.mul(mMat);
		
		shadowMVP2.identity();
		shadowMVP2.mul(b);
		shadowMVP2.mul(lightPmat);
		shadowMVP2.mul(lightVmat);
		shadowMVP2.mul(mMat);
		
		mvMat.invert(invTrMat);
		invTrMat.transpose(invTrMat);

		gl.glUniformMatrix4fv(mvLoc, 1, false, mvMat.get(vals));
		gl.glUniformMatrix4fv(projLoc, 1, false, pMat.get(vals));
		gl.glUniformMatrix4fv(nLoc, 1, false, invTrMat.get(vals));
		gl.glUniformMatrix4fv(sLoc, 1, false, shadowMVP2.get(vals));

		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[42]);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[44]);
		gl.glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(1);
		
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo[43]);
        gl.glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(2);

		gl.glActiveTexture(GL_TEXTURE0);
		gl.glBindTexture(GL_TEXTURE_2D, DroidAloneTexture);

		gl.glEnable(GL_DEPTH_TEST);
		gl.glFrontFace(GL_LEQUAL);
		gl.glDrawArrays(GL_TRIANGLES, 0, DroidAloneModel.getNumVertices());
		

		
		//  ------That's no Moon... or, maybe it is?
		
		thisAmb = moonambient;
		thisDif = moondiffuse;
		thisSpe = moonspecular;
		thisShi = moonshininess;

		mMat.identity();
		mMat.translate(moonLocX, moonLocY, moonLocZ);
		mMat.scale(60.0f, 60.0f, 60.0f);
		
		currentLightPos.set(initialLightLoc);
		installLights(renderingProgram2, vMat);

		mvMat.identity();
		mvMat.mul(vMat);
		mvMat.mul(mMat);
		
		shadowMVP2.identity();
		shadowMVP2.mul(b);
		shadowMVP2.mul(lightPmat);
		shadowMVP2.mul(lightVmat);
		shadowMVP2.mul(mMat);
		
		mvMat.invert(invTrMat);
		invTrMat.transpose(invTrMat);

		gl.glUniformMatrix4fv(mvLoc, 1, false, mvMat.get(vals));
		gl.glUniformMatrix4fv(projLoc, 1, false, pMat.get(vals));
		gl.glUniformMatrix4fv(nLoc, 1, false, invTrMat.get(vals));
		gl.glUniformMatrix4fv(sLoc, 1, false, shadowMVP2.get(vals));
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[11]);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[13]);
		gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(1);
		
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo[12]);
        gl.glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(2);

		gl.glActiveTexture(GL_TEXTURE0);
		gl.glBindTexture(GL_TEXTURE_2D, moonTexture);

		gl.glEnable(GL_CULL_FACE);
		gl.glFrontFace(GL_CCW);
		gl.glDrawArrays(GL_TRIANGLES, 0, numSphereVerts); 
		
		// ---------------------- The Real IG
		
		thisAmb = IGambient;
		thisDif = IGdiffuse;
		thisSpe = IGspecular;
		thisShi = IGshininess;
		
		mMat.identity();
		mMat.translate(ig88LocX, ig88LocY, ig88LocZ);
		mMat.scale(1.0f, 1.0f, 1.0f);
		mMat.rotateY((float)Math.toRadians(-80.0f));
		
		currentLightPos.set(initialLightLoc);
		installLights(renderingProgram2, vMat);

		mvMat.identity();
		mvMat.mul(vMat);
		mvMat.mul(mMat);
		
		shadowMVP2.identity();
		shadowMVP2.mul(b);
		shadowMVP2.mul(lightPmat);
		shadowMVP2.mul(lightVmat);
		shadowMVP2.mul(mMat);
		
		mvMat.invert(invTrMat);
		invTrMat.transpose(invTrMat);

		gl.glUniformMatrix4fv(mvLoc, 1, false, mvMat.get(vals));
		gl.glUniformMatrix4fv(projLoc, 1, false, pMat.get(vals));
		gl.glUniformMatrix4fv(nLoc, 1, false, invTrMat.get(vals));
		gl.glUniformMatrix4fv(sLoc, 1, false, shadowMVP2.get(vals));
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[18]);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[20]);
		gl.glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(1);
		
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo[19]);
        gl.glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(2);

		gl.glActiveTexture(GL_TEXTURE0);
		gl.glBindTexture(GL_TEXTURE_2D, igTexture);

		gl.glEnable(GL_DEPTH_TEST);
		gl.glFrontFace(GL_LEQUAL);
		gl.glDrawArrays(GL_TRIANGLES, 0, igModel.getNumVertices());
		
		
		// ---------------------- Back Hanger Bay

		thisAmb = towerambient;
		thisDif = towerdiffuse;
		thisSpe = towerspecular;
		thisShi = towershininess;

		mMat.identity();
		mMat.translate(backHangerLocX, backHangerLocY, backHangerLocZ);
		mMat.scale(1.0f, 0.7f, 0.7f);

		currentLightPos.set(initialLightLoc);
		installLights(renderingProgram2, vMat);

		mvMat.identity();
		mvMat.mul(vMat);
		mvMat.mul(mMat);
		
		shadowMVP2.identity();
		shadowMVP2.mul(b);
		shadowMVP2.mul(lightPmat);
		shadowMVP2.mul(lightVmat);
		shadowMVP2.mul(mMat);
		
		mvMat.invert(invTrMat);
		invTrMat.transpose(invTrMat);

		gl.glUniformMatrix4fv(mvLoc, 1, false, mvMat.get(vals));
		gl.glUniformMatrix4fv(projLoc, 1, false, pMat.get(vals));
		gl.glUniformMatrix4fv(nLoc, 1, false, invTrMat.get(vals));
		gl.glUniformMatrix4fv(sLoc, 1, false, shadowMVP2.get(vals));

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[21]);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[23]);
		gl.glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(1);

		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo[22]);
		gl.glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(2);

		gl.glActiveTexture(GL_TEXTURE0);
		gl.glBindTexture(GL_TEXTURE_2D, backHangerTexture);

		gl.glEnable(GL_DEPTH_TEST);
		gl.glFrontFace(GL_LEQUAL);
		gl.glDrawArrays(GL_TRIANGLES, 0, backHangerModel.getNumVertices());
				
				
		// ---------------------- Boba Fett
		
		thisAmb = armorambient;
		thisDif = armordiffuse;
		thisSpe = armorspecular;
		thisShi = armorshininess;

		mMat.identity();
		mMat.translate(bobaFettLocX, bobaFettLocY, bobaFettLocZ);
		mMat.scale(1.0f, 1.0f, 1.0f);
		
		currentLightPos.set(initialLightLoc);
		installLights(renderingProgram2, vMat);
		
		mvMat.identity();
		mvMat.mul(vMat);
		mvMat.mul(mMat);
		
		shadowMVP2.identity();
		shadowMVP2.mul(b);
		shadowMVP2.mul(lightPmat);
		shadowMVP2.mul(lightVmat);
		shadowMVP2.mul(mMat);
		
		mvMat.invert(invTrMat);
		invTrMat.transpose(invTrMat);

		gl.glUniformMatrix4fv(mvLoc, 1, false, mvMat.get(vals));
		gl.glUniformMatrix4fv(projLoc, 1, false, pMat.get(vals));
		gl.glUniformMatrix4fv(nLoc, 1, false, invTrMat.get(vals));
		gl.glUniformMatrix4fv(sLoc, 1, false, shadowMVP2.get(vals));
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[27]);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[29]);
		gl.glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(1);
		
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo[28]);
        gl.glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(2);

		gl.glActiveTexture(GL_TEXTURE0);
		gl.glBindTexture(GL_TEXTURE_2D, bobaFettTexture);

		gl.glEnable(GL_DEPTH_TEST);
		gl.glFrontFace(GL_LEQUAL);
		gl.glDrawArrays(GL_TRIANGLES, 0, bobaFettModel.getNumVertices());
		
		
		// ---------------------- Bird Building and Landing Pad
		
		thisAmb = BmatAmb;
		thisDif = BmatDif;
		thisSpe = BmatSpe;
		thisShi = BmatShi;
		
		mMat.identity();
		mMat.translate(BirdBuildingLocX, BirdBuildingLocY, BirdBuildingLocZ);
		mMat.scale(1.2f, 1.2f, 1.2f);
		
		currentLightPos.set(initialLightLoc);
		installLights(renderingProgram2, vMat);

		mvMat.identity();
		mvMat.mul(vMat);
		mvMat.mul(mMat);
		
		shadowMVP2.identity();
		shadowMVP2.mul(b);
		shadowMVP2.mul(lightPmat);
		shadowMVP2.mul(lightVmat);
		shadowMVP2.mul(mMat);
		
		mvMat.invert(invTrMat);
		invTrMat.transpose(invTrMat);

		gl.glUniformMatrix4fv(mvLoc, 1, false, mvMat.get(vals));
		gl.glUniformMatrix4fv(projLoc, 1, false, pMat.get(vals));
		gl.glUniformMatrix4fv(nLoc, 1, false, invTrMat.get(vals));
		gl.glUniformMatrix4fv(sLoc, 1, false, shadowMVP2.get(vals));

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[30]);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[32]);
		gl.glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(1);
		
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo[31]);
        gl.glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(2);

		gl.glActiveTexture(GL_TEXTURE0);
		gl.glBindTexture(GL_TEXTURE_2D, BirdBuildingTexture);

		gl.glEnable(GL_DEPTH_TEST);
		gl.glFrontFace(GL_LEQUAL);
		gl.glDrawArrays(GL_TRIANGLES, 0, BirdBuildingModel.getNumVertices());
		
		
		// ---------------------- Bird Building Extension
		
		thisAmb = BmatAmb;
		thisDif = BmatDif;
		thisSpe = BmatSpe;
		thisShi = BmatShi;
		
		mMat.identity();
		mMat.translate(BirdBuildingLocX, BirdBuildingLocY, BirdBuildingLocZ);
		mMat.scale(1.2f, 1.2f, 1.2f);
		
		currentLightPos.set(initialLightLoc);
		installLights(renderingProgram2, vMat);

		mvMat.identity();
		mvMat.mul(vMat);
		mvMat.mul(mMat);
		
		shadowMVP2.identity();
		shadowMVP2.mul(b);
		shadowMVP2.mul(lightPmat);
		shadowMVP2.mul(lightVmat);
		shadowMVP2.mul(mMat);
		
		mvMat.invert(invTrMat);
		invTrMat.transpose(invTrMat);

		gl.glUniformMatrix4fv(mvLoc, 1, false, mvMat.get(vals));
		gl.glUniformMatrix4fv(projLoc, 1, false, pMat.get(vals));
		gl.glUniformMatrix4fv(nLoc, 1, false, invTrMat.get(vals));
		gl.glUniformMatrix4fv(sLoc, 1, false, shadowMVP2.get(vals));

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[65]);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[67]);
		gl.glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(1);
		
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo[66]);
        gl.glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(2);

		gl.glActiveTexture(GL_TEXTURE0);
		gl.glBindTexture(GL_TEXTURE_2D, BirdBuildingExtensionTexture);

		gl.glEnable(GL_DEPTH_TEST);
		gl.glFrontFace(GL_LEQUAL);
		gl.glDrawArrays(GL_TRIANGLES, 0, BirdBuildingExtensionModel.getNumVertices());
		
		
		// ---------------------- The Wall
		
		thisAmb = BmatAmb;
		thisDif = BmatDif;
		thisSpe = BmatSpe;
		thisShi = BmatShi;

		mMat.identity();
		mMat.translate(theWallLocX, theWallLocY, theWallLocZ);
		mMat.scale(5f, 5f, 5f);
		mMat.rotateY((float)Math.toRadians(-180.0f));
		
		currentLightPos.set(initialLightLoc);
		installLights(renderingProgram2, vMat);

		mvMat.identity();
		mvMat.mul(vMat);
		mvMat.mul(mMat);
		
		shadowMVP2.identity();
		shadowMVP2.mul(b);
		shadowMVP2.mul(lightPmat);
		shadowMVP2.mul(lightVmat);
		shadowMVP2.mul(mMat);
		
		mvMat.invert(invTrMat);
		invTrMat.transpose(invTrMat);

		gl.glUniformMatrix4fv(mvLoc, 1, false, mvMat.get(vals));
		gl.glUniformMatrix4fv(projLoc, 1, false, pMat.get(vals));
		gl.glUniformMatrix4fv(nLoc, 1, false, invTrMat.get(vals));
		gl.glUniformMatrix4fv(sLoc, 1, false, shadowMVP2.get(vals));

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[33]);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[35]);
		gl.glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(1);
		
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo[34]);
        gl.glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(2);

		gl.glActiveTexture(GL_TEXTURE0);
		gl.glBindTexture(GL_TEXTURE_2D, theWallTexture);

		gl.glEnable(GL_DEPTH_TEST);
		gl.glFrontFace(GL_LEQUAL);
		gl.glDrawArrays(GL_TRIANGLES, 0, theWallModel.getNumVertices());
		
		
		// ---------------------- Tower Building	
		
		thisAmb = towerambient;
		thisDif = towerdiffuse;
		thisSpe = towerspecular;
		thisShi = towershininess;
		
		mMat.identity();
		mMat.translate(BillBoardBuildingLocX, BillBoardBuildingLocY, BillBoardBuildingLocZ);
		mMat.scale(5f, 5f, 5f);
		mMat.rotateY((float)Math.toRadians(-180.0f));
		
		currentLightPos.set(initialLightLoc);
		installLights(renderingProgram2, vMat);

		mvMat.identity();
		mvMat.mul(vMat);
		mvMat.mul(mMat);
		
		shadowMVP2.identity();
		shadowMVP2.mul(b);
		shadowMVP2.mul(lightPmat);
		shadowMVP2.mul(lightVmat);
		shadowMVP2.mul(mMat);
		
		mvMat.invert(invTrMat);
		invTrMat.transpose(invTrMat);

		gl.glUniformMatrix4fv(mvLoc, 1, false, mvMat.get(vals));
		gl.glUniformMatrix4fv(projLoc, 1, false, pMat.get(vals));
		gl.glUniformMatrix4fv(nLoc, 1, false, invTrMat.get(vals));
		gl.glUniformMatrix4fv(sLoc, 1, false, shadowMVP2.get(vals));

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[36]);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[38]);
		gl.glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(1);
		
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo[37]);
        gl.glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(2);

		gl.glActiveTexture(GL_TEXTURE0);
		gl.glBindTexture(GL_TEXTURE_2D, BillBoardBuildingTexture);

		gl.glEnable(GL_DEPTH_TEST);
		gl.glFrontFace(GL_LEQUAL);
		gl.glDrawArrays(GL_TRIANGLES, 0, BillBoardBuildingModel.getNumVertices());
		
		
		// Blue Ship

		thisAmb = IGambient;
		thisDif = IGdiffuse;
		thisSpe = IGspecular;
		thisShi = IGshininess;

		mMat.identity();
		mMat.translate(blueGhostLocX, blueGhostLocY, blueGhostLocZ);
		mMat.scale(1.3f, 1.3f, 1.3f);
		mMat.rotateXYZ(BlueAmtX, BlueAmtY, BlueAmtZ);
		
		
		//Setting Ship Movements based on positioning. 
		
		 if ( blueGhostLocX > 15)
		 {
		 	
		 	blueGhostLocY = blueGhostLocY -0.14f;
		 	 blueGhostLocZ =  blueGhostLocZ +0.2f;
		 	 blueGhostLocX =  blueGhostLocX -0.1f;	
		 	 if (correction > -.45f)
		 	 {
		 		BlueAmtY = BlueAmtY -.002f;
		 		BlueAmtX = BlueAmtX +.001f;
		 		correction = correction -.002f;
		 	 }
		 }
		 else if (blueGhostLocY > -23)
		 {
		 	blueGhostLocY = blueGhostLocY -0.14f;
		 	 blueGhostLocZ =  blueGhostLocZ +0.2f;
		 	 if (correction > -0.88)
		 	 {
		 		BlueAmtY = BlueAmtY +.004f;
		 		BlueAmtX = BlueAmtX -.002f;
		 		correction = correction -.004f;
		 	 }
		 }
		 else if (blueGhostLocZ < 1200)	
		 {
			 blueGhostLocY = blueGhostLocY -0.07f;
			 blueGhostLocZ =  blueGhostLocZ +0.1f;
		 	 blueGhostLocZ =  blueGhostLocZ +0.5f;
			 
		 }
		 else if (blueGhostLocZ > 1200 && blueGhostLocZ < 1500)
		 {
			 blueGhostLocZ =  blueGhostLocZ +1.0f;
		 }
		 
		 if (blueGhostLocZ > 750 && blueGhostLocZ < 900)
		 {
			 MasterChamberDoorLocY = MasterChamberDoorLocY +0.3f;
		 }
		 
		 if (blueGhostLocZ > 950 && MasterChamberDoorLocY > -152.05f)
		 {
			 MasterChamberDoorLocY = MasterChamberDoorLocY -0.2f;
		 }
		 else if (blueGhostLocZ > 1200 && blueGhostLocZ < 1300)
		 {
			 MasterChamberDoorLocZ = MasterChamberDoorLocZ -0.3f;
		 }
		 
		 if (blueGhostLocZ > 1500 && blueGhostLocZ < 1750)
		 {
			 blueGhostLocZ =  blueGhostLocZ +0.9f;
			
		 }
		 else if (blueGhostLocZ > 1750 && BlueAmtY > -1.2779907)
		 {
			 BlueAmtY = BlueAmtY - .006f;
		 }
		 else if (blueGhostLocX > -500 && blueGhostLocZ > 1800)
		 {
			 blueGhostLocX =  blueGhostLocX -2.8f;	
		 }
		 else if (blueGhostLocX > -550 && blueGhostLocZ > 1750)
		 {
			 blueGhostLocX =  blueGhostLocX -1.8f;
		 }
		 else if (blueGhostLocX > -600 && blueGhostLocZ > 1750)
		 {
			 blueGhostLocX =  blueGhostLocX -0.4f;
		 }
			
			currentLightPos.set(initialLightLoc);
			installLights(renderingProgram2, vMat);

			mvMat.identity();
			mvMat.mul(vMat);
			mvMat.mul(mMat);
			
			shadowMVP2.identity();
			shadowMVP2.mul(b);
			shadowMVP2.mul(lightPmat);
			shadowMVP2.mul(lightVmat);
			shadowMVP2.mul(mMat);
			
			mvMat.invert(invTrMat);
			invTrMat.transpose(invTrMat);

			gl.glUniformMatrix4fv(mvLoc, 1, false, mvMat.get(vals));
			gl.glUniformMatrix4fv(projLoc, 1, false, pMat.get(vals));
			gl.glUniformMatrix4fv(nLoc, 1, false, invTrMat.get(vals));
			gl.glUniformMatrix4fv(sLoc, 1, false, shadowMVP2.get(vals));
			
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[45]);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[47]);
		gl.glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(1);

		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo[46]);
		gl.glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(2);

		gl.glActiveTexture(GL_TEXTURE0);
		gl.glBindTexture(GL_TEXTURE_2D, blueGhostTexture);

		gl.glEnable(GL_DEPTH_TEST);
		gl.glFrontFace(GL_LEQUAL);
		gl.glDrawArrays(GL_TRIANGLES, 0, blueGhostModel.getNumVertices());
		
		
		// Chess board
		
		thisAmb = IGambient;
		thisDif = IGdiffuse;
		thisSpe = IGspecular;
		thisShi = IGshininess;

		mMat.identity();
		mMat.translate(HoloTableLocX, HoloTableLocY, HoloTableLocZ);
		mMat.scale(0.3f, 0.3f, 0.3f);
		mMat.rotateY((float)Math.toRadians(-90.0f));
		mMat.rotateZ((float)Math.toRadians(90.0f));
		mMat.rotateX((float)Math.toRadians(180.0f));
		
		currentLightPos.set(initialLightLoc);
		installLights(renderingProgram2, vMat);

		mvMat.identity();
		mvMat.mul(vMat);
		mvMat.mul(mMat);
		
		shadowMVP2.identity();
		shadowMVP2.mul(b);
		shadowMVP2.mul(lightPmat);
		shadowMVP2.mul(lightVmat);
		shadowMVP2.mul(mMat);
		
		mvMat.invert(invTrMat);
		invTrMat.transpose(invTrMat);
		
		gl.glUniformMatrix4fv(mvLoc, 1, false, mvMat.get(vals));
		gl.glUniformMatrix4fv(projLoc, 1, false, pMat.get(vals));
		gl.glUniformMatrix4fv(nLoc, 1, false, invTrMat.get(vals));
		gl.glUniformMatrix4fv(sLoc, 1, false, shadowMVP2.get(vals));
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[48]);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[50]);
		gl.glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(1);
		
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo[49]);
        gl.glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(2);

		gl.glActiveTexture(GL_TEXTURE0);
		gl.glBindTexture(GL_TEXTURE_2D, TronChessTexture);

		gl.glEnable(GL_DEPTH_TEST);
		gl.glFrontFace(GL_LEQUAL);
		gl.glDrawArrays(GL_TRIANGLES, 0, TronChessModel.getNumVertices());
		
		
		// HoloTable
		
		thisAmb = IGambient;
		thisDif = IGdiffuse;
		thisSpe = IGspecular;
		thisShi = IGshininess;
		
		mMat.identity();
		mMat.translate(HoloTableLocX, HoloTableLocY, HoloTableLocZ);
		mMat.scale(0.3f, 0.3f, 0.3f);
		mMat.rotateY((float)Math.toRadians(-90.0f));
		mMat.rotateZ((float)Math.toRadians(90.0f));
		mMat.rotateX((float)Math.toRadians(180.0f));

		currentLightPos.set(initialLightLoc);
		installLights(renderingProgram2, vMat);

		mvMat.identity();
		mvMat.mul(vMat);
		mvMat.mul(mMat);
		
		shadowMVP2.identity();
		shadowMVP2.mul(b);
		shadowMVP2.mul(lightPmat);
		shadowMVP2.mul(lightVmat);
		shadowMVP2.mul(mMat);
		
		mvMat.invert(invTrMat);
		invTrMat.transpose(invTrMat);
		
		gl.glUniformMatrix4fv(mvLoc, 1, false, mvMat.get(vals));
		gl.glUniformMatrix4fv(projLoc, 1, false, pMat.get(vals));
		gl.glUniformMatrix4fv(nLoc, 1, false, invTrMat.get(vals));
		gl.glUniformMatrix4fv(sLoc, 1, false, shadowMVP2.get(vals));
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[51]);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[53]);
		gl.glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(1);
		
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo[52]);
        gl.glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(2);

		gl.glActiveTexture(GL_TEXTURE0);
		gl.glBindTexture(GL_TEXTURE_2D, HoloTableTexture);

		gl.glEnable(GL_DEPTH_TEST);
		gl.glFrontFace(GL_LEQUAL);
		gl.glDrawArrays(GL_TRIANGLES, 0, HoloTableModel.getNumVertices());
		
		
		// HoloTable 2
		
		thisAmb = IGambient;
		thisDif = IGdiffuse;
		thisSpe = IGspecular;
		thisShi = IGshininess;
		
		mMat.identity();
		mMat.translate(HoloTableLoc2X, HoloTableLoc2Y, HoloTableLoc2Z);
		mMat.scale(0.3f, 0.3f, 0.7f);
		mMat.rotateY((float)Math.toRadians(-90.0f));
		mMat.rotateZ((float)Math.toRadians(90.0f));
		mMat.rotateX((float)Math.toRadians(180.0f));

		currentLightPos.set(initialLightLoc);
		installLights(renderingProgram2, vMat);

		mvMat.identity();
		mvMat.mul(vMat);
		mvMat.mul(mMat);
		
		shadowMVP2.identity();
		shadowMVP2.mul(b);
		shadowMVP2.mul(lightPmat);
		shadowMVP2.mul(lightVmat);
		shadowMVP2.mul(mMat);
		
		mvMat.invert(invTrMat);
		invTrMat.transpose(invTrMat);
		
		gl.glUniformMatrix4fv(mvLoc, 1, false, mvMat.get(vals));
		gl.glUniformMatrix4fv(projLoc, 1, false, pMat.get(vals));
		gl.glUniformMatrix4fv(nLoc, 1, false, invTrMat.get(vals));
		gl.glUniformMatrix4fv(sLoc, 1, false, shadowMVP2.get(vals));
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[51]);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[53]);
		gl.glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(1);
		
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo[52]);
        gl.glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(2);

		gl.glActiveTexture(GL_TEXTURE0);
		gl.glBindTexture(GL_TEXTURE_2D, HoloTableTexture);

		gl.glEnable(GL_DEPTH_TEST);
		gl.glFrontFace(GL_LEQUAL);
		gl.glDrawArrays(GL_TRIANGLES, 0, HoloTableModel.getNumVertices());
		
		
		// HoloTable 3
		
		thisAmb = IGambient;
		thisDif = IGdiffuse;
		thisSpe = IGspecular;
		thisShi = IGshininess;
		
		mMat.identity();
		mMat.translate(HoloTableLoc3X, HoloTableLoc3Y, HoloTableLoc3Z);
		mMat.scale(0.3f, 0.3f, 0.3f);
		mMat.rotateY((float)Math.toRadians(-90.0f));
		mMat.rotateZ((float)Math.toRadians(90.0f));
		mMat.rotateX((float)Math.toRadians(180.0f));

		currentLightPos.set(initialLightLoc);
		installLights(renderingProgram2, vMat);

		mvMat.identity();
		mvMat.mul(vMat);
		mvMat.mul(mMat);
		
		shadowMVP2.identity();
		shadowMVP2.mul(b);
		shadowMVP2.mul(lightPmat);
		shadowMVP2.mul(lightVmat);
		shadowMVP2.mul(mMat);
		
		mvMat.invert(invTrMat);
		invTrMat.transpose(invTrMat);
		
		gl.glUniformMatrix4fv(mvLoc, 1, false, mvMat.get(vals));
		gl.glUniformMatrix4fv(projLoc, 1, false, pMat.get(vals));
		gl.glUniformMatrix4fv(nLoc, 1, false, invTrMat.get(vals));
		gl.glUniformMatrix4fv(sLoc, 1, false, shadowMVP2.get(vals));
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[51]);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[53]);
		gl.glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(1);
		
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo[52]);
        gl.glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(2);

		gl.glActiveTexture(GL_TEXTURE0);
		gl.glBindTexture(GL_TEXTURE_2D, HoloTableTexture);

		gl.glEnable(GL_DEPTH_TEST);
		gl.glFrontFace(GL_LEQUAL);
		gl.glDrawArrays(GL_TRIANGLES, 0, HoloTableModel.getNumVertices());

		
		// Wall Armor
		
		thisAmb = WallTronamb;
		thisDif = WallTrondiff;
		thisSpe = WallTronspec;
		thisShi = WallTronshine;
		
		mMat.identity();
		mMat.translate(WallArmorLocX, WallArmorLocY, WallArmorLocZ);
		mMat.scale(2.6f, 2.6f, 2.6f);
		mMat.rotateY((float)Math.toRadians(-180.0f));
		
		currentLightPos.set(initialLightLoc);
		installLights(renderingProgram2, vMat);

		mvMat.identity();
		mvMat.mul(vMat);
		mvMat.mul(mMat);
		
		shadowMVP2.identity();
		shadowMVP2.mul(b);
		shadowMVP2.mul(lightPmat);
		shadowMVP2.mul(lightVmat);
		shadowMVP2.mul(mMat);
		
		mvMat.invert(invTrMat);
		invTrMat.transpose(invTrMat);
		
		gl.glUniformMatrix4fv(mvLoc, 1, false, mvMat.get(vals));
		gl.glUniformMatrix4fv(projLoc, 1, false, pMat.get(vals));
		gl.glUniformMatrix4fv(nLoc, 1, false, invTrMat.get(vals));
		gl.glUniformMatrix4fv(sLoc, 1, false, shadowMVP2.get(vals));
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[60]);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[62]);
		gl.glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(1);
		
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo[61]);
        gl.glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(2);

		gl.glActiveTexture(GL_TEXTURE0);
		gl.glBindTexture(GL_TEXTURE_2D, WallArmorTexture);

		gl.glEnable(GL_DEPTH_TEST);
		gl.glFrontFace(GL_LEQUAL);
		gl.glDrawArrays(GL_TRIANGLES, 0, WallArmorModel.getNumVertices());
		
		
		// ----------------------  Balcony

		thisAmb = Droidamb;
		thisDif = Droiddiff;
		thisSpe = Droidspec;
		thisShi = Droidshine;

		mMat.identity();
		mMat.translate(objLocX, objLocY, objLocZ);
		mMat.scale(1.0f, 0.7f, 0.7f);
		
		currentLightPos.set(initialLightLoc);
		installLights(renderingProgram2, vMat);

		mvMat.identity();
		mvMat.mul(vMat);
		mvMat.mul(mMat);
		
		shadowMVP2.identity();
		shadowMVP2.mul(b);
		shadowMVP2.mul(lightPmat);
		shadowMVP2.mul(lightVmat);
		shadowMVP2.mul(mMat);
		
		mvMat.invert(invTrMat);
		invTrMat.transpose(invTrMat);
		
		gl.glUniformMatrix4fv(mvLoc, 1, false, mvMat.get(vals));
		gl.glUniformMatrix4fv(projLoc, 1, false, pMat.get(vals));
		gl.glUniformMatrix4fv(nLoc, 1, false, invTrMat.get(vals));
		gl.glUniformMatrix4fv(sLoc, 1, false, shadowMVP2.get(vals));

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[24]);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[26]);
		gl.glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(1);
		
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo[25]);
        gl.glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(2);

		gl.glActiveTexture(GL_TEXTURE0);
		gl.glBindTexture(GL_TEXTURE_2D, BalconyDroidTexture);

		gl.glEnable(GL_DEPTH_TEST);
		gl.glFrontFace(GL_LEQUAL);
		gl.glDrawArrays(GL_TRIANGLES, 0, BalconyDroidModel.getNumVertices());
		
		
		// ----------------------  Balcony Extension

		thisAmb = Droidamb;
		thisDif = Droiddiff;
		thisSpe = Droidspec;
		thisShi = Droidshine;

		mMat.identity();
		mMat.translate(objLocX, objLocY, objLocZ);
		mMat.scale(1.0f, 0.7f, 0.7f);
		
		currentLightPos.set(initialLightLoc);
		installLights(renderingProgram2, vMat);

		mvMat.identity();
		mvMat.mul(vMat);
		mvMat.mul(mMat);
		
		shadowMVP2.identity();
		shadowMVP2.mul(b);
		shadowMVP2.mul(lightPmat);
		shadowMVP2.mul(lightVmat);
		shadowMVP2.mul(mMat);
		
		mvMat.invert(invTrMat);
		invTrMat.transpose(invTrMat);
		
		gl.glUniformMatrix4fv(mvLoc, 1, false, mvMat.get(vals));
		gl.glUniformMatrix4fv(projLoc, 1, false, pMat.get(vals));
		gl.glUniformMatrix4fv(nLoc, 1, false, invTrMat.get(vals));
		gl.glUniformMatrix4fv(sLoc, 1, false, shadowMVP2.get(vals));

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[63]);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[65]);
		gl.glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(1);
		
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo[64]);
        gl.glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(2);

		gl.glActiveTexture(GL_TEXTURE0);
		gl.glBindTexture(GL_TEXTURE_2D, BalconyExtensionTexture);

		gl.glEnable(GL_DEPTH_TEST);
		gl.glFrontFace(GL_LEQUAL);
		gl.glDrawArrays(GL_TRIANGLES, 0, BalconyExtensionModel.getNumVertices());
		
		
		//Master Chamber
		
	thisAmb = IGambient;
	thisDif = IGdiffuse;
	thisSpe = IGspecular;
	thisShi = IGshininess;

	mMat.identity();
	mMat.translate(MasteChamberLocX, MasteChamberLocY, MasteChamberLocZ);
	mMat.scale(8.0f, 8.0f, 8.0f);

	currentLightPos.set(initialLightLoc);
	installLights(renderingProgram2, vMat);

	mvMat.identity();
	mvMat.mul(vMat);
	mvMat.mul(mMat);
	
	shadowMVP2.identity();
	shadowMVP2.mul(b);
	shadowMVP2.mul(lightPmat);
	shadowMVP2.mul(lightVmat);
	shadowMVP2.mul(mMat);
	
	mvMat.invert(invTrMat);
	invTrMat.transpose(invTrMat);

	gl.glUniformMatrix4fv(mvLoc, 1, false, mvMat.get(vals));
	gl.glUniformMatrix4fv(projLoc, 1, false, pMat.get(vals));
	gl.glUniformMatrix4fv(nLoc, 1, false, invTrMat.get(vals));
	gl.glUniformMatrix4fv(sLoc, 1, false, shadowMVP2.get(vals));
	
	gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[89]);
	gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
	gl.glEnableVertexAttribArray(0);
	
	gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[91]);
	gl.glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
	gl.glEnableVertexAttribArray(1);
	
	gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo[90]);
    gl.glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
    gl.glEnableVertexAttribArray(2);
    
	gl.glActiveTexture(GL_TEXTURE0);
	gl.glBindTexture(GL_TEXTURE_2D, MasterChamberTexture);
	
	gl.glEnable(GL_CULL_FACE);
	gl.glFrontFace(GL_CCW);
	gl.glEnable(GL_DEPTH_TEST);
	gl.glDepthFunc(GL_LEQUAL);

	gl.glDrawArrays(GL_TRIANGLES, 0, masterChamber.getNumVertices());
	
	
	//Master Chamber Back Door
	
	mMat.identity();
	mMat.translate(MasterChamberDoorLocX, MasterChamberDoorLocY, MasterChamberDoorLocZ);
	mMat.scale(16.0f, 16.0f, 16.0f);

	mvMat.identity();
	mvMat.mul(vMat);
	mvMat.mul(mMat);

	gl.glUniformMatrix4fv(mvLoc, 1, false, mvMat.get(vals));
	gl.glUniformMatrix4fv(projLoc, 1, false, pMat.get(vals));


	gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[113]);
	gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
	gl.glEnableVertexAttribArray(0);

	gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[115]);
	gl.glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
	gl.glEnableVertexAttribArray(1);
	
	gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo[114]);
    gl.glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
    gl.glEnableVertexAttribArray(2);

	gl.glActiveTexture(GL_TEXTURE0);
	gl.glBindTexture(GL_TEXTURE_2D, MasterChamberDoorBackTexture);

	gl.glEnable(GL_DEPTH_TEST);
	gl.glFrontFace(GL_LEQUAL);
	gl.glDrawArrays(GL_TRIANGLES, 0, MasterChamberDoorBackModel.getNumVertices());

	
	//Mirror Gate
	
	thisAmb = Droidamb;
	thisDif = Droiddiff;
	thisSpe = Droidspec;
	thisShi = Droidshine;

	mMat.identity();
	mMat.translate(MirrorGate_FrameLocX, MirrorGate_FrameLocY, MirrorGate_FrameLocZ);
	mMat.scale(1.0f,1.0f, 1.0f);
	mMat.rotateX((float)Math.toRadians(180.0f));

	currentLightPos.set(initialLightLoc);
	installLights(renderingProgram2, vMat);

	mvMat.identity();
	mvMat.mul(vMat);
	mvMat.mul(mMat);

	shadowMVP2.identity();
	shadowMVP2.mul(b);
	shadowMVP2.mul(lightPmat);
	shadowMVP2.mul(lightVmat);
	shadowMVP2.mul(mMat);

	mvMat.invert(invTrMat);
	invTrMat.transpose(invTrMat);

	gl.glUniformMatrix4fv(mvLoc, 1, false, mvMat.get(vals));
	gl.glUniformMatrix4fv(projLoc, 1, false, pMat.get(vals));
	gl.glUniformMatrix4fv(nLoc, 1, false, invTrMat.get(vals));
	gl.glUniformMatrix4fv(sLoc, 1, false, shadowMVP2.get(vals));

	gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[101]);
	gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
	gl.glEnableVertexAttribArray(0);

	gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[103]);
	gl.glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
	gl.glEnableVertexAttribArray(1);

	gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo[102]);
	gl.glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
	gl.glEnableVertexAttribArray(2);

	gl.glActiveTexture(GL_TEXTURE0);
	gl.glBindTexture(GL_TEXTURE_2D, MirrorGate_FrameTexture);

	gl.glEnable(GL_CULL_FACE);
	gl.glFrontFace(GL_CCW);
	gl.glEnable(GL_DEPTH_TEST);
	gl.glDepthFunc(GL_LEQUAL);

	gl.glDrawArrays(GL_TRIANGLES, 0, MirrorGate_FrameModel.getNumVertices());
			
		
		// ---------------------- Cloud Layer
		
		thisAmb = cloudambient;
		thisDif = clouddiffuse;
		thisSpe = cloudspecular;
		thisShi = cloudshininess;

		mMat.identity();
		mMat.translate(CloudyLayerLocX, CloudyLayerLocY+1, CloudyLayerLocZ);
		mMat.scale(5f, 5f, 5f);

		currentLightPos.set(initialLightLoc);
		installLights(renderingProgram2, vMat);

		mvMat.identity();
		mvMat.mul(vMat);
		mvMat.mul(mMat);
		
		shadowMVP2.identity();
		shadowMVP2.mul(b);
		shadowMVP2.mul(lightPmat);
		shadowMVP2.mul(lightVmat);
		shadowMVP2.mul(mMat);
		
		mvMat.invert(invTrMat);
		invTrMat.transpose(invTrMat);

		gl.glUniformMatrix4fv(mvLoc, 1, false, mvMat.get(vals));
		gl.glUniformMatrix4fv(projLoc, 1, false, pMat.get(vals));
		gl.glUniformMatrix4fv(nLoc, 1, false, invTrMat.get(vals));
		gl.glUniformMatrix4fv(sLoc, 1, false, shadowMVP2.get(vals));
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[39]);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[41]);
		gl.glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(1);
		
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo[40]);
        gl.glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(2);

		gl.glActiveTexture(GL_TEXTURE0);
		gl.glBindTexture(GL_TEXTURE_2D, CloudyLayerTexture);

		gl.glEnable(GL_DEPTH_TEST);
		gl.glFrontFace(GL_LEQUAL);
		gl.glDrawArrays(GL_TRIANGLES, 0, CloudyLayerModel.getNumVertices());
		
		
		//Draw the Axis
		
		if(axisOn) {
			//************ X Axis (Red) **************
			thisAmb = RedTronamb;
			thisDif = RedTrondiff;
			thisSpe = RedTronspec;
			thisShi = RedTronshine;
			currentLightPos.set(initialLightLoc);
			installLights(renderingProgram2, vMat);

			mvMat.identity();
			mvMat.mul(vMat);
			mvMat.mul(mMat);
			
			mvMat.invert(invTrMat);
			invTrMat.transpose(invTrMat);
			gl.glUniformMatrix4fv(mvLoc2, 1, false, vMat.get(vals));
			gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[5]);
			gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
			gl.glEnableVertexAttribArray(0);
		
			gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[6]);
			gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
			gl.glEnableVertexAttribArray(1);
		
			gl.glActiveTexture(GL_TEXTURE0);
			gl.glBindTexture(GL_TEXTURE_2D, redTexture);
		
			gl.glDrawArrays(GL_TRIANGLES, 0, 9);
			
			//************ y Axis (Green) **************
			thisAmb = GreenTronamb;
			thisDif = GreenTrondiff;
			thisSpe = GreenTronspec;
			thisShi = GreenTronshine;
			currentLightPos.set(initialLightLoc);
			installLights(renderingProgram2, vMat);

			mvMat.identity();
			mvMat.mul(vMat);
			mvMat.mul(mMat);
			
			mvMat.invert(invTrMat);
			invTrMat.transpose(invTrMat);
			gl.glUniformMatrix4fv(mvLoc2, 1, false, vMat.get(vals));
			gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[7]);
			gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
			gl.glEnableVertexAttribArray(0);
		
			gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[8]);
			gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
			gl.glEnableVertexAttribArray(1);
		
			gl.glActiveTexture(GL_TEXTURE0);
			gl.glBindTexture(GL_TEXTURE_2D, greenTexture);
		
			gl.glDrawArrays(GL_TRIANGLES, 0, 9);
			
			//************ Z Axis (Blue) **************
			thisAmb = BlueTronamb;
			thisDif = BlueTrondiff;
			thisSpe = BlueTronspec;
			thisShi = BlueTronshine;
			currentLightPos.set(initialLightLoc);
			installLights(renderingProgram2, vMat);

			mvMat.identity();
			mvMat.mul(vMat);
			mvMat.mul(mMat);
			
			mvMat.invert(invTrMat);
			invTrMat.transpose(invTrMat);
			gl.glUniformMatrix4fv(mvLoc2, 1, false, vMat.get(vals));
			gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[9]);
			gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
			gl.glEnableVertexAttribArray(0);
		
			gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[10]);
			gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
			gl.glEnableVertexAttribArray(1);
		
			gl.glActiveTexture(GL_TEXTURE0);
			gl.glBindTexture(GL_TEXTURE_2D, blueTexture);
		
			gl.glDrawArrays(GL_TRIANGLES, 0, 9);
		}
		
	}
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	
	public void passThree()
	{
		GL4 gl = (GL4) GLContext.getCurrentGL();
		
		// Fog and stuff
		
		gl.glUseProgram(renderingProgramFog);
		
		mvLoc = gl.glGetUniformLocation(renderingProgramFog, "mv_matrix");
		projLoc = gl.glGetUniformLocation(renderingProgramFog, "proj_matrix");
		
		
		// ----------------------  Balcony Extension

		mMat.identity();
		mMat.translate(BalconyExtensionV3BottomLocX, BalconyExtensionV3BottomLocY, BalconyExtensionV3BottomLocZ);
		mMat.scale(2.0f, 1.4f, 1.4f);

		mvMat.identity();
		mvMat.mul(vMat);
		mvMat.mul(mMat);

		gl.glUniformMatrix4fv(mvLoc, 1, false, mvMat.get(vals));
		gl.glUniformMatrix4fv(projLoc, 1, false, pMat.get(vals));

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[71]);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[73]);
		gl.glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(1);
		
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo[72]);
        gl.glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(2);

		gl.glActiveTexture(GL_TEXTURE0);
		gl.glBindTexture(GL_TEXTURE_2D, BalconyExtensionV3BottomTexture);

		gl.glEnable(GL_DEPTH_TEST);
		gl.glFrontFace(GL_LEQUAL);
		gl.glDrawArrays(GL_TRIANGLES, 0, BalconyExtensionV3BottomModel.getNumVertices());

		
		// ---------------------- The Wall Extension

		mMat.identity();
		mMat.translate(theWallLocX, theWallLocY, theWallLocZ);
		mMat.scale(10f, 10f, 10f);
		mMat.rotateY((float)Math.toRadians(-180.0f));
		
		mvMat.identity();
		mvMat.mul(vMat);
		mvMat.mul(mMat);

		gl.glUniformMatrix4fv(mvLoc, 1, false, mvMat.get(vals));
		gl.glUniformMatrix4fv(projLoc, 1, false, pMat.get(vals));

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[74]);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[76]);
		gl.glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(1);
		
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo[75]);
        gl.glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(2);

		gl.glActiveTexture(GL_TEXTURE0);
		gl.glBindTexture(GL_TEXTURE_2D, TheWall_BottomTexture);

		gl.glEnable(GL_DEPTH_TEST);
		gl.glFrontFace(GL_LEQUAL);
		gl.glDrawArrays(GL_TRIANGLES, 0, TheWall_BottomModel.getNumVertices());
		
		
		// ---------------------- Tower Building	
		
		mMat.identity();
		mMat.translate(BillBoardBuildingLocX, BillBoardBuildingLocY, BillBoardBuildingLocZ);
		mMat.scale(10f, 10f, 10f);
		mMat.rotateY((float)Math.toRadians(-180.0f));
		

		mvMat.identity();
		mvMat.mul(vMat);
		mvMat.mul(mMat);
		
		gl.glUniformMatrix4fv(mvLoc, 1, false, mvMat.get(vals));
		gl.glUniformMatrix4fv(projLoc, 1, false, pMat.get(vals));


		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[77]);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[79]);
		gl.glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(1);
		
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo[78]);
        gl.glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(2);

		gl.glActiveTexture(GL_TEXTURE0);
		gl.glBindTexture(GL_TEXTURE_2D, BillBoardBuilding_BottomTexture);

		gl.glEnable(GL_DEPTH_TEST);
		gl.glFrontFace(GL_LEQUAL);
		gl.glDrawArrays(GL_TRIANGLES, 0, BillBoardBuilding_BottomModel.getNumVertices());
		
		
		// ---------------------- Bird Building Extension

		mMat.identity();
		mMat.translate(BirdBuildingLocX, BirdBuildingLocY, BirdBuildingLocZ);
		mMat.scale(2.4f, 2.4f, 2.4f);


		mvMat.identity();
		mvMat.mul(vMat);
		mvMat.mul(mMat);
		

		gl.glUniformMatrix4fv(mvLoc, 1, false, mvMat.get(vals));
		gl.glUniformMatrix4fv(projLoc, 1, false, pMat.get(vals));

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[80]);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[82]);
		gl.glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(1);
		
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo[81]);
        gl.glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(2);

		gl.glActiveTexture(GL_TEXTURE0);
		gl.glBindTexture(GL_TEXTURE_2D, BirdBuildingExtensionV1_BottomTexture);

		gl.glEnable(GL_DEPTH_TEST);
		gl.glFrontFace(GL_LEQUAL);
		gl.glDrawArrays(GL_TRIANGLES, 0, BirdBuildingExtensionV1_BottomModel.getNumVertices());
		
		
		// ---------------------- Back Hanger Bay Extension

		mMat.identity();
		mMat.translate(backHangerLocX, backHangerLocY, backHangerLocZ);
		mMat.scale(2.0f, 1.4f, 1.4f);

		mvMat.identity();
		mvMat.mul(vMat);
		mvMat.mul(mMat);

		gl.glUniformMatrix4fv(mvLoc, 1, false, mvMat.get(vals));
		gl.glUniformMatrix4fv(projLoc, 1, false, pMat.get(vals));

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[83]);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[85]);
		gl.glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(1);
		
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo[84]);
        gl.glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(2);

		gl.glActiveTexture(GL_TEXTURE0);
		gl.glBindTexture(GL_TEXTURE_2D, BackHangerExtensionTexture);

		gl.glEnable(GL_DEPTH_TEST);
		gl.glFrontFace(GL_LEQUAL);
		gl.glDrawArrays(GL_TRIANGLES, 0, BackHangerExtensionModel.getNumVertices());
	
	
		//Master Chamber Front
		
		mMat.identity();
		mMat.translate(MasteChamberLocX, MasteChamberLocY, MasteChamberLocZ-25);
		mMat.scale(16.0f, 16.0f, 16.0f);

		mvMat.identity();
		mvMat.mul(vMat);
		mvMat.mul(mMat);

		gl.glUniformMatrix4fv(mvLoc, 1, false, mvMat.get(vals));
		gl.glUniformMatrix4fv(projLoc, 1, false, pMat.get(vals));

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[107]);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[109]);
		gl.glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(1);
		
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo[108]);
        gl.glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(2);

		gl.glActiveTexture(GL_TEXTURE0);
		gl.glBindTexture(GL_TEXTURE_2D, MasterLandingChamber_FrontTexture);

		gl.glEnable(GL_DEPTH_TEST);
		gl.glFrontFace(GL_LEQUAL);
		gl.glDrawArrays(GL_TRIANGLES, 0, MasterLandingChamber_FrontModel.getNumVertices());
		
		
		//Master Chamber Front Door
		
		mMat.identity();
		mMat.translate(MasterChamberDoorLocX, MasterChamberDoorLocY, MasterChamberDoorLocZ);
		mMat.scale(16.0f, 16.0f, 16.0f);

		mvMat.identity();
		mvMat.mul(vMat);
		mvMat.mul(mMat);

		gl.glUniformMatrix4fv(mvLoc, 1, false, mvMat.get(vals));
		gl.glUniformMatrix4fv(projLoc, 1, false, pMat.get(vals));

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[110]);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[112]);
		gl.glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(1);
		
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo[111]);
        gl.glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(2);

		gl.glActiveTexture(GL_TEXTURE0);
		gl.glBindTexture(GL_TEXTURE_2D, MasterChamberDoorTexture);

		gl.glEnable(GL_DEPTH_TEST);
		gl.glFrontFace(GL_LEQUAL);
		gl.glDrawArrays(GL_TRIANGLES, 0, MasterChamberDoorModel.getNumVertices());
		
		
		// ---------------------- FOG Walls

		mMat.identity();
		mMat.translate(0, -50, -250);
		mMat.scale(70.0f, 90.0f, 35.0f);

		mvMat.identity();
		mvMat.mul(vMat);
		mvMat.mul(mMat);

		gl.glUniformMatrix4fv(mvLoc, 1, false, mvMat.get(vals));
		gl.glUniformMatrix4fv(projLoc, 1, false, pMat.get(vals));

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[86]);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[88]);
		gl.glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(1);
		
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo[87]);
        gl.glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(2);

		gl.glActiveTexture(GL_TEXTURE0);
		gl.glBindTexture(GL_TEXTURE_2D, backgroundFOGTexture);

		gl.glEnable(GL_DEPTH_TEST);
		gl.glFrontFace(GL_LEQUAL);
		gl.glDrawArrays(GL_TRIANGLES, 0, backgroundFOGModel.getNumVertices());
		
		
		//Beneath the Clouds

		mMat.identity();
		mMat.translate(terLocX, terLocY, terLocZ);
		mMat.scale(800.0f, 700.0f, 1000.0f);
		mMat.rotateX((float)Math.toRadians(10.0f));

		mvMat.identity();
		mvMat.mul(vMat);
		mvMat.mul(mMat);

		gl.glUniformMatrix4fv(mvLoc, 1, false, mvMat.get(vals));
		gl.glUniformMatrix4fv(projLoc, 1, false, pMat.get(vals));

		// vertices buffer
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[68]);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);
		
		// texture coordinate buffer
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[69]);
		gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(1);

		// normals buffer
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[70]);
		gl.glVertexAttribPointer(2, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(2);

		// texture
		gl.glActiveTexture(GL_TEXTURE0);
		gl.glBindTexture(GL_TEXTURE_2D, rockyTexture);

		// height map
		gl.glActiveTexture(GL_TEXTURE1);
		gl.glBindTexture(GL_TEXTURE_2D, heightMap);
		
		gl.glEnable(GL_CULL_FACE);
		gl.glFrontFace(GL_CCW);
		gl.glEnable(GL_DEPTH_TEST);
		gl.glDepthFunc(GL_LEQUAL);
		
		gl.glDrawArrays(GL_TRIANGLES, 0, numGroundVertices);

		
		// ---------------------- Cloud Layer
		
		thisAmb = cloudambient;
		thisDif = clouddiffuse;
		thisSpe = cloudspecular;
		thisShi = cloudshininess;

		mMat.identity();
		mMat.translate(CloudyLayerLocX, CloudyLayerLocY-1, CloudyLayerLocZ+300);
		mMat.scale(5f, 5f, 5f);

		currentLightPos.set(initialLightLoc);
		installLights(renderingProgram2, vMat);

		mvMat.identity();
		mvMat.mul(vMat);
		mvMat.mul(mMat);
		
		shadowMVP2.identity();
		shadowMVP2.mul(b);
		shadowMVP2.mul(lightPmat);
		shadowMVP2.mul(lightVmat);
		shadowMVP2.mul(mMat);
		
		mvMat.invert(invTrMat);
		invTrMat.transpose(invTrMat);

		gl.glUniformMatrix4fv(mvLoc, 1, false, mvMat.get(vals));
		gl.glUniformMatrix4fv(projLoc, 1, false, pMat.get(vals));
		gl.glUniformMatrix4fv(nLoc, 1, false, invTrMat.get(vals));
		gl.glUniformMatrix4fv(sLoc, 1, false, shadowMVP2.get(vals));
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[39]);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[41]);
		gl.glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(1);
		
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo[40]);
        gl.glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(2);

		gl.glActiveTexture(GL_TEXTURE0);
		gl.glBindTexture(GL_TEXTURE_2D, CloudyLayerTexture);

		gl.glEnable(GL_DEPTH_TEST);
		gl.glFrontFace(GL_CW);
		gl.glDrawArrays(GL_TRIANGLES, 0, CloudyLayerModel.getNumVertices());
		
	}
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	
	public void passFour()
	{
		GL4 gl = (GL4) GLContext.getCurrentGL();
		
		gl.glUseProgram(renderingProgramNoise);
		
		mvLoc = gl.glGetUniformLocation(renderingProgramNoise, "mv_matrix");
		projLoc = gl.glGetUniformLocation(renderingProgramNoise, "proj_matrix");
		nLoc = gl.glGetUniformLocation(renderingProgramNoise, "norm_matrix");
		texRotLoc = gl.glGetUniformLocation(renderingProgramNoise, "texRot_matrix");
		
		thisAmb = Droidamb;
	thisDif = Droiddiff;
	thisSpe = Droidspec;
	thisShi = Droidshine;

	mMat.identity();
	mMat.translate(flyingStarLocX, flyingStarLocY, flyingStarLocZ);
	mMat.scale(.5f, .5f, .5f);
	mMat.rotateY((float)Math.toRadians(55.0f));
	mMat.rotateX((float)Math.toRadians(amt));
	
	currentLightPos.set(initialLightLoc);
	installLights(renderingProgramNoise, vMat);

	mvMat.identity();
	mvMat.mul(vMat);
	mvMat.mul(mMat);
	
	mvMat.invert(invTrMat);
	invTrMat.transpose(invTrMat);

	gl.glUniformMatrix4fv(mvLoc, 1, false, mvMat.get(vals));
	gl.glUniformMatrix4fv(projLoc, 1, false, pMat.get(vals));
	gl.glUniformMatrix4fv(nLoc, 1, false, invTrMat.get(vals));
	gl.glUniformMatrix4fv(texRotLoc, 1, false, texRotMat.get(vals));
	
	gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[92]);
	gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
	gl.glEnableVertexAttribArray(0);
	
	gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[94]);
	gl.glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
	gl.glEnableVertexAttribArray(1);
	
	gl.glActiveTexture(GL_TEXTURE4);
	gl.glBindTexture(GL_TEXTURE_3D, noiseTexture);
	
	gl.glEnable(GL_CULL_FACE);
	gl.glFrontFace(GL_CCW);
	gl.glEnable(GL_DEPTH_TEST);
	gl.glDepthFunc(GL_LEQUAL);

	gl.glDrawArrays(GL_TRIANGLES, 0, numflyingStarVertices);
	
	}
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	
	public void passFive() //Bump Mapping Pass
	{
		GL4 gl = (GL4) GLContext.getCurrentGL();
		
		gl.glUseProgram(renderingProgramBump);
	
		mvLoc = gl.glGetUniformLocation(renderingProgramBump, "mv_matrix");
		projLoc = gl.glGetUniformLocation(renderingProgramBump, "proj_matrix");
		nLoc = gl.glGetUniformLocation(renderingProgramBump, "norm_matrix");
		
		
		//Holotable 1
		
		mMat.identity();
		mMat.translate(HoloTableLocX, HoloTableLocY, HoloTableLocZ);
		mMat.scale(0.3f, 0.3f, 0.3f);
		mMat.rotateY((float)Math.toRadians(-90.0f));
		mMat.rotateZ((float)Math.toRadians(90.0f));
		mMat.rotateX((float)Math.toRadians(180.0f));
		
		currentLightPos.set(initialLightLoc);		
		installLights(renderingProgramBump, vMat);

		mvMat.identity();
		mvMat.mul(vMat);
		mvMat.mul(mMat);
		
		shadowMVP2.identity();
		shadowMVP2.mul(b);
		shadowMVP2.mul(lightPmat);
		shadowMVP2.mul(lightVmat);
		shadowMVP2.mul(mMat);

		mvMat.invert(invTrMat);
		invTrMat.transpose(invTrMat);

		gl.glUniformMatrix4fv(mvLoc, 1, false, mvMat.get(vals));
		gl.glUniformMatrix4fv(projLoc, 1, false, pMat.get(vals));
		gl.glUniformMatrix4fv(nLoc, 1, false, invTrMat.get(vals));
		gl.glUniformMatrix4fv(sLoc, 1, false, shadowMVP2.get(vals));

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[95]);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[97]);
		gl.glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(1);
		
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo[96]);
        gl.glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(2);

		gl.glActiveTexture(GL_TEXTURE0);
		gl.glBindTexture(GL_TEXTURE_2D, HoloTable_bottomTexture);

		gl.glEnable(GL_DEPTH_TEST);
		gl.glFrontFace(GL_LEQUAL);
		gl.glDrawArrays(GL_TRIANGLES, 0, HoloTable_bottomModel.getNumVertices());
		
		
		// HoloTable 2
		
		thisAmb = IGambient;
		thisDif = IGdiffuse;
		thisSpe = IGspecular;
		thisShi = IGshininess;
		
		mMat.identity();
		mMat.translate(HoloTableLoc2X, HoloTableLoc2Y, HoloTableLoc2Z);
		mMat.scale(0.3f, 0.3f, 0.7f);
		mMat.rotateY((float)Math.toRadians(-90.0f));
		mMat.rotateZ((float)Math.toRadians(90.0f));
		mMat.rotateX((float)Math.toRadians(180.0f));

		currentLightPos.set(initialLightLoc);
		installLights(renderingProgramBump, vMat);

		mvMat.identity();
		mvMat.mul(vMat);
		mvMat.mul(mMat);
		
		shadowMVP2.identity();
		shadowMVP2.mul(b);
		shadowMVP2.mul(lightPmat);
		shadowMVP2.mul(lightVmat);
		shadowMVP2.mul(mMat);
		
		mvMat.invert(invTrMat);
		invTrMat.transpose(invTrMat);
		
		gl.glUniformMatrix4fv(mvLoc, 1, false, mvMat.get(vals));
		gl.glUniformMatrix4fv(projLoc, 1, false, pMat.get(vals));
		gl.glUniformMatrix4fv(nLoc, 1, false, invTrMat.get(vals));
		gl.glUniformMatrix4fv(sLoc, 1, false, shadowMVP2.get(vals));
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[95]);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[97]);
		gl.glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(1);
		
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo[96]);
        gl.glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(2);

		gl.glActiveTexture(GL_TEXTURE0);
		gl.glBindTexture(GL_TEXTURE_2D, HoloTable_bottomTexture);

		gl.glEnable(GL_DEPTH_TEST);
		gl.glFrontFace(GL_LEQUAL);
		gl.glDrawArrays(GL_TRIANGLES, 0, HoloTable_bottomModel.getNumVertices());
		
		
		// HoloTable 3
		
		thisAmb = IGambient;
		thisDif = IGdiffuse;
		thisSpe = IGspecular;
		thisShi = IGshininess;
		
		mMat.identity();
		mMat.translate(HoloTableLoc3X, HoloTableLoc3Y, HoloTableLoc3Z);
		mMat.scale(0.3f, 0.3f, 0.3f);
		mMat.rotateY((float)Math.toRadians(-90.0f));
		mMat.rotateZ((float)Math.toRadians(90.0f));
		mMat.rotateX((float)Math.toRadians(180.0f));

		currentLightPos.set(initialLightLoc);
		installLights(renderingProgramBump, vMat);

		mvMat.identity();
		mvMat.mul(vMat);
		mvMat.mul(mMat);
		
		shadowMVP2.identity();
		shadowMVP2.mul(b);
		shadowMVP2.mul(lightPmat);
		shadowMVP2.mul(lightVmat);
		shadowMVP2.mul(mMat);
		
		mvMat.invert(invTrMat);
		invTrMat.transpose(invTrMat);
		
		gl.glUniformMatrix4fv(mvLoc, 1, false, mvMat.get(vals));
		gl.glUniformMatrix4fv(projLoc, 1, false, pMat.get(vals));
		gl.glUniformMatrix4fv(nLoc, 1, false, invTrMat.get(vals));
		gl.glUniformMatrix4fv(sLoc, 1, false, shadowMVP2.get(vals));
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[95]);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[97]);
		gl.glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(1);
		
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo[96]);
        gl.glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(2);

		gl.glActiveTexture(GL_TEXTURE0);
		gl.glBindTexture(GL_TEXTURE_2D, HoloTable_bottomTexture);

		gl.glEnable(GL_DEPTH_TEST);
		gl.glFrontFace(GL_LEQUAL);
		gl.glDrawArrays(GL_TRIANGLES, 0, HoloTable_bottomModel.getNumVertices());
		
	}
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	
	public void passSix()
	{
		GL4 gl = (GL4) GLContext.getCurrentGL();
		
	gl.glUseProgram(renderingProgramEnviron);
		
		mvLoc = gl.glGetUniformLocation(renderingProgramEnviron, "mv_matrix");
		projLoc = gl.glGetUniformLocation(renderingProgramEnviron, "proj_matrix");
		nLoc = gl.glGetUniformLocation(renderingProgramEnviron, "norm_matrix");

		mMat.identity();
		mMat.translate(MirrorGate_FrameLocX, MirrorGate_FrameLocY, MirrorGate_FrameLocZ);
		mMat.scale(1.0f,1.0f, 1.0f);
		mMat.rotateX((float)Math.toRadians(180.0f));

		mvMat.identity();
		mvMat.mul(vMat);
		mvMat.mul(mMat);

		gl.glUniformMatrix4fv(mvLoc, 1, false, mvMat.get(vals));
		gl.glUniformMatrix4fv(projLoc, 1, false, pMat.get(vals));
		gl.glUniformMatrix4fv(nLoc, 1, false, invTrMat.get(vals));

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[98]);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[100]);
		gl.glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(1);
		
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo[99]);
        gl.glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(2);

		gl.glActiveTexture(GL_TEXTURE0);
		gl.glBindTexture(GL_TEXTURE_CUBE_MAP, skyboxTexture);

		gl.glEnable(GL_CULL_FACE);
		gl.glFrontFace(GL_CCW);
		gl.glDepthFunc(GL_LEQUAL);

		gl.glDrawArrays(GL_TRIANGLES, 0, MirrorGateModel.getNumVertices());
	}
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


	public void passSeven() //Transparency Effect
	{
		GL4 gl = (GL4) GLContext.getCurrentGL();
		
	gl.glUseProgram(renderingProgramBlend);
	
	mvLoc = gl.glGetUniformLocation(renderingProgramBlend, "mv_matrix");
	projLoc = gl.glGetUniformLocation(renderingProgramBlend, "proj_matrix");
	nLoc = gl.glGetUniformLocation(renderingProgramBlend, "norm_matrix");
	alphaLoc = gl.glGetUniformLocation(renderingProgramBlend, "alpha");
	flipLoc = gl.glGetUniformLocation(renderingProgramBlend, "flipNormal");

	// Red Tron
	
	thisAmb = RedTronamb;
	thisDif = RedTrondiff;
	thisSpe = RedTronspec;
	thisShi = RedTronshine;

	mMat.identity();
	mMat.translate(HoloTableLocX, HoloTableLocY, HoloTableLocZ);
	mMat.scale(0.3f, 0.3f, 0.3f);
	mMat.rotateY((float)Math.toRadians(-90.0f));
	mMat.rotateZ((float)Math.toRadians(90.0f));
	mMat.rotateX((float)Math.toRadians(180.0f));

	currentLightPos.set(initialLightLoc);
	installLights(renderingProgramBlend, vMat);

	mvMat.identity();
	mvMat.mul(vMat);
	mvMat.mul(mMat);

	mvMat.invert(invTrMat);
	invTrMat.transpose(invTrMat);
	
	gl.glUniformMatrix4fv(mvLoc, 1, false, mvMat.get(vals));
	gl.glUniformMatrix4fv(projLoc, 1, false, pMat.get(vals));
	gl.glUniformMatrix4fv(nLoc, 1, false, invTrMat.get(vals));
	gl.glProgramUniform1f(renderingProgramBlend, alphaLoc, 1.0f);
	gl.glProgramUniform1f(renderingProgramBlend, flipLoc, 1.0f);
	
	gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[54]);
	gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
	gl.glEnableVertexAttribArray(0);

	gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[56]);
	gl.glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
	gl.glEnableVertexAttribArray(1);
	
	gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo[55]);
    gl.glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
    gl.glEnableVertexAttribArray(2);
	
	gl.glActiveTexture(GL_TEXTURE0);
	gl.glBindTexture(GL_TEXTURE_2D, RedTronTexture);
	
	// 2-pass rendering a transparent version

	gl.glEnable(GL_BLEND);
	gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	gl.glBlendEquation(GL_FUNC_ADD);

	gl.glEnable(GL_CULL_FACE);
	
	gl.glCullFace(GL_FRONT);
	gl.glProgramUniform1f(renderingProgramBlend, alphaLoc, 0.3f);
	gl.glProgramUniform1f(renderingProgramBlend, flipLoc, -1.0f);
	gl.glDrawArrays(GL_TRIANGLES, 0, RedTronModel.getNumVertices());
	
	gl.glCullFace(GL_BACK);
	gl.glProgramUniform1f(renderingProgramBlend, alphaLoc, 0.7f);
	gl.glProgramUniform1f(renderingProgramBlend, flipLoc, 1.0f);
	gl.glDrawArrays(GL_TRIANGLES, 0, RedTronModel.getNumVertices());

	gl.glDisable(GL_BLEND);
	
	// end transparency section
	
	
	// Blue Tron
	
	thisAmb = BlueTronamb;
	thisDif = BlueTrondiff;
	thisSpe = BlueTronspec;
	thisShi = BlueTronshine;

	mMat.identity();
	mMat.translate(HoloTableLocX, HoloTableLocY, HoloTableLocZ);
	mMat.scale(0.3f, 0.3f, 0.3f);
	mMat.rotateY((float)Math.toRadians(-90.0f));
	mMat.rotateZ((float)Math.toRadians(90.0f));
	mMat.rotateX((float)Math.toRadians(180.0f));

	currentLightPos.set(initialLightLoc);
	installLights(renderingProgramBlend, vMat);

	mvMat.identity();
	mvMat.mul(vMat);
	mvMat.mul(mMat);

	mvMat.invert(invTrMat);
	invTrMat.transpose(invTrMat);

	gl.glUniformMatrix4fv(mvLoc, 1, false, mvMat.get(vals));
	gl.glUniformMatrix4fv(projLoc, 1, false, pMat.get(vals));
	gl.glUniformMatrix4fv(nLoc, 1, false, invTrMat.get(vals));
	gl.glProgramUniform1f(renderingProgramBlend, alphaLoc, 1.0f);
	gl.glProgramUniform1f(renderingProgramBlend, flipLoc, 1.0f);
	
	gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[57]);
	gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
	gl.glEnableVertexAttribArray(0);

	gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[59]);
	gl.glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
	gl.glEnableVertexAttribArray(1);
	
	gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo[58]);
    gl.glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
    gl.glEnableVertexAttribArray(2);
	
	gl.glActiveTexture(GL_TEXTURE0);
	gl.glBindTexture(GL_TEXTURE_2D, BlueTronTexture);

	// 2-pass rendering a transparent version

	gl.glEnable(GL_BLEND);
	gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	gl.glBlendEquation(GL_FUNC_ADD);

	gl.glEnable(GL_CULL_FACE);
	
	gl.glCullFace(GL_FRONT);
	gl.glProgramUniform1f(renderingProgramBlend, alphaLoc, 0.3f);
	gl.glProgramUniform1f(renderingProgramBlend, flipLoc, -1.0f);
	gl.glDrawArrays(GL_TRIANGLES, 0, BlueTronModel.getNumVertices());
	
	gl.glCullFace(GL_BACK);
	gl.glProgramUniform1f(renderingProgramBlend, alphaLoc, 0.7f);
	gl.glProgramUniform1f(renderingProgramBlend, flipLoc, 1.0f);
	gl.glDrawArrays(GL_TRIANGLES, 0, BlueTronModel.getNumVertices());

	gl.glDisable(GL_BLEND);
	
	// end transparency section
		
	}
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	
	public void passEight() // GEOM Mod
	{
		GL4 gl = (GL4) GLContext.getCurrentGL();
		
	gl.glUseProgram(renderingProgramGeom);
	
	mvLoc = gl.glGetUniformLocation(renderingProgramGeom, "mv_matrix");
	projLoc = gl.glGetUniformLocation(renderingProgramGeom, "proj_matrix");
	nLoc = gl.glGetUniformLocation(renderingProgramGeom, "norm_matrix");
	
	
	thisAmb = GmatAmb; 
	thisDif = GmatDif;
	thisSpe = GmatSpe;
	thisShi = GmatShi;
	
	mMat.identity();
	mMat.translate(FurryKnightLocX, FurryKnightLocY, FurryKnightLocZ);
	mMat.scale(1.5f, 1.5f, 1.5f);

	currentLightPos.set(initialLightLoc);
	installLights(renderingProgramGeom, vMat);

	mvMat.identity();
	mvMat.mul(vMat);
	mvMat.mul(mMat);
	
	mvMat.invert(invTrMat);
	invTrMat.transpose(invTrMat);

	gl.glUniformMatrix4fv(mvLoc, 1, false, mvMat.get(vals));
	gl.glUniformMatrix4fv(projLoc, 1, false, pMat.get(vals));
	gl.glUniformMatrix4fv(nLoc, 1, false, invTrMat.get(vals));
	gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[104]);
	gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
	gl.glEnableVertexAttribArray(0);

	gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[106]);
	gl.glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
	gl.glEnableVertexAttribArray(1);
	
	gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo[105]);
    gl.glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
    gl.glEnableVertexAttribArray(2);

	gl.glActiveTexture(GL_TEXTURE0);
	gl.glBindTexture(GL_TEXTURE_2D, FurryKnightTexture);
	
	gl.glEnable(GL_DEPTH_TEST);
	gl.glFrontFace(GL_LEQUAL);

	gl.glDrawArrays(GL_TRIANGLES, 0, FurryKnightModel.getNumVertices());
	
	}
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//End Passes
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


	public void init(GLAutoDrawable drawable)
	{	
	
	GL4 gl = (GL4) GLContext.getCurrentGL();
	
	
	//Importing Models
	
	myModel = new ImportedModel("SpaceStationAlpha-b.obj");
	spaceGateModel = new ImportedModel("SpaceGate.obj");
	igModel = new ImportedModel("IG-88V4.obj");
	backHangerModel = new ImportedModel("BackHangerwithFalconV2.obj");
	BalconyDroidModel = new ImportedModel("BalconyAloneV4.obj");
	bobaFettModel = new ImportedModel("BobaFettV8.obj");
	BirdBuildingModel = new ImportedModel("BirdBuildingV6.obj");
	theWallModel = new ImportedModel("TheWall_Top.obj");
	BillBoardBuildingModel = new ImportedModel("BillBoardBuilding_Top.obj");
	CloudyLayerModel = new ImportedModel("CloudyBelow.obj");
	DroidAloneModel = new ImportedModel("DroindAloneV3.obj");
	blueGhostModel = new ImportedModel("BlueGhostV2b.obj");
	TronChessModel = new ImportedModel("Chessboard.obj");
	HoloTableModel = new ImportedModel("HoloTableV2_top.obj");
	RedTronModel = new ImportedModel("RedTron.obj");
	BlueTronModel = new ImportedModel("BlueTron.obj");
	WallArmorModel = new ImportedModel("WallArmor.obj");
	BalconyExtensionModel = new ImportedModel("BalconyExtensionV3Top.obj");
	BalconyExtensionV3BottomModel = new ImportedModel("BalconyExtensionV3Bottom.obj");
	BirdBuildingExtensionModel = new ImportedModel("BirdBuildingExtensionV1_TopV3.obj");
	ground = new ImportedModel("grid.obj");
	BillBoardBuilding_BottomModel = new ImportedModel("BillBoardBuilding_Bottom.obj");
	BirdBuildingExtensionV1_BottomModel = new ImportedModel("BirdBuildingExtensionV1_BottomV4.obj");
	BackHangerExtensionModel = new ImportedModel("BackHangerExtension.obj");
	backgroundFOGModel = new ImportedModel("FOGWall2.obj");
	TheWall_BottomModel = new ImportedModel("TheWall_Bottom.obj");
	masterChamber = new ImportedModel("MasterLandingChamberV5.obj");
	flyingStar = new ImportedModel("FloatingStar.obj");
	HoloTable_bottomModel = new ImportedModel("HoloTableV2_bottom.obj");
	MirrorGateModel = new ImportedModel("MirrorGate_Inner.obj");
	MirrorGate_FrameModel = new ImportedModel("MirrorGate_Frame.obj");
	FurryKnightModel = new ImportedModel("FurryKnight.obj");
	MasterLandingChamber_FrontModel = new ImportedModel("MasterLandingChamber_FrontV2.obj");
	MasterChamberDoorModel = new ImportedModel("MasterChamberDoorV2_Back.obj");
	MasterChamberDoorBackModel = new ImportedModel("MasterChamberDoorV2_Front.obj");
	MasterLandingChamberBELOWModel = new ImportedModel("MasterLandingChamberBELOW.obj");
	
	
	//Setting Rendering Programs for each section
	
	startTime = System.currentTimeMillis();
	renderingProgramCubeMap = Utils.createShaderProgram("a4/vertCShader.glsl", "a4/fragCShader.glsl");
	renderingProgram1 = Utils.createShaderProgram("a4/vert1shader.glsl", "a4/frag1shader.glsl");
	renderingProgram2 = Utils.createShaderProgram("a4/vertLightShader.glsl", "a4/fragLightShader.glsl");
	renderingProgramAxis = Utils.createShaderProgram("a4/vert.shader", "a4/frag.shader");
	renderingProgramFog = Utils.createShaderProgram("a4/vertFogShader.glsl", "a4/fragFogShader.glsl");
	renderingProgramNoise = Utils.createShaderProgram("a4/vertNoiseShader.glsl", "a4/fragNoiseShader.glsl");
	renderingProgramBump = Utils.createShaderProgram("a4/vertBumpShader.glsl", "a4/fragBumpShader.glsl");
	renderingProgramEnviron = Utils.createShaderProgram("a4/vertEnvironShader.glsl", "a4/fragEnvironShader.glsl");
	renderingProgramBlend = Utils.createShaderProgram("a4/vertBlendShader.glsl", "a4/fragBlendShader.glsl");
	renderingProgramGeom = Utils.createShaderProgram("a4/vertGeomShader.glsl", "a4/geomShader.glsl", "a4/fragGeomShader.glsl");
		
	aspect = (float) myCanvas.getWidth() / (float) myCanvas.getHeight();
	pMat.identity().setPerspective((float) Math.toRadians(60.0f), aspect, 0.1f, 1000.0f);

	inLightX = initialLightLoc.x;
	inLightY = initialLightLoc.y;
	inLightZ = initialLightLoc.z;
		
	setupVertices();
	setupShadowBuffers();
		
		
	//Setting initial positions of objects	
	
	objLocX = 0.5f; objLocY = 0.0f; objLocZ = 0.0f;
	BalconyExtensionV3BottomLocX = 0.5f; BalconyExtensionV3BottomLocY = 0.0f; BalconyExtensionV3BottomLocZ= 0.0f;
	moonLocX = 100.0f; moonLocY = 250.0f; moonLocZ = -700.0f;
	spaceGateLocX = 0.0f; spaceGateLocY = 2.0f; spaceGateLocZ = -5.0f;
	ig88LocX = -2.5f; ig88LocY = -3.0f; ig88LocZ = -8.0f;
	bobaFettLocX = 1.5f; bobaFettLocY = 2.0f; bobaFettLocZ = -11.0f;
	backHangerLocX = -10.0f; backHangerLocY = -7.5f; backHangerLocZ = -40.0f;
	BalconyDroidLocX = 0.0f; BalconyDroidLocY = 0.0f; BalconyDroidLocZ = 0.0f;
	BirdBuildingLocX = -4.0f; BirdBuildingLocY = 6.0f; BirdBuildingLocZ = -40.0f;
	theWallLocX = 150.0f; theWallLocY = 72.0f; theWallLocZ = -360.0f;
	BillBoardBuildingLocX = 50.0f; BillBoardBuildingLocY = -34.0f; BillBoardBuildingLocZ = -400.0f;
	CloudyLayerLocX = -10.0f; CloudyLayerLocY = -40.0f; CloudyLayerLocZ = 0.0f;
	DroidAloneLocX = 0.7f; DroidAloneLocY = 0.2f;	DroidAloneLocZ = 2.0f;
	blueGhostLocX = 70.0f; blueGhostLocY = 80.0f; blueGhostLocZ = -250.0f;
	shipCamLocX = 70.0f; shipCamLocY = 90.0f; shipCamLocZ = -blueGhostLocZ - -270.0f;
	HoloTableLocX = 1.0f; HoloTableLocY = -6.0f; HoloTableLocZ = 20.0f;
	HoloTableLoc2X = -20.0f; HoloTableLoc2Y = -6.0f; HoloTableLoc2Z = 3.0f;
	HoloTableLoc3X = 18.0f; HoloTableLoc3Y = -6.0f; HoloTableLoc3Z = 3.0f;
	WallArmorLocX = -23.0f; WallArmorLocY = -5.0f; WallArmorLocZ = 20.0f;
	terLocX = 0.0f; terLocY = -200.05f; terLocZ = 400.0f;
	MasteChamberLocX= 10.0f; MasteChamberLocY = -153.05f; MasteChamberLocZ = 930.0f;
	MasterChamberDoorLocX = 10;  MasterChamberDoorLocY = -153.05f; MasterChamberDoorLocZ = 914.0f;
	flyingStarLocX = 6.5f; flyingStarLocY = 3.0f; flyingStarLocZ = -13.0f;
	MirrorGate_FrameLocX = 10.0f;	MirrorGate_FrameLocY = 5.0f;	MirrorGate_FrameLocZ = 5.5f;
	FurryKnightLocX = 10.0f; FurryKnightLocY = -10.0f; FurryKnightLocZ = 0.0f;
	BlueAmtX = 0.0f; BlueAmtY = 0.0f; BlueAmtZ = 0.0f;
		
		
	//Set Textures
		
	BalconyDroidTexture = Utils.loadTexture("BalconyAloneV4.jpg");
	igTexture = Utils.loadTexture("IG-88V3.jpg");
	shuttleTexture = Utils.loadTexture("SpaceStationAlpha-b.png");
	redTexture = Utils.loadTexture("red.png");
	greenTexture  = Utils.loadTexture("green.png");	
	blueTexture = Utils.loadTexture("blue.png");	
	cyborgTexture = Utils.loadTexture("cyborg.jpg");
	moonTexture = Utils.loadTexture("squareMoonMap.jpg");
	spaceGateTexture = Utils.loadTexture("SpaceGate.png");
	backHangerTexture = Utils.loadTexture("BackhangerV2.jpg");
	bobaFettTexture = Utils.loadTexture("BobaFettV8.jpg");
	BirdBuildingTexture = Utils.loadTexture("BirdBuildingV6.jpg");
	theWallTexture = Utils.loadTexture("TheWall_Top.jpg");
	BillBoardBuildingTexture = Utils.loadTexture("BillBoardBuilding_Top.jpg");
	CloudyLayerTexture = Utils.loadTexture("CloudyBelow.jpg");
	DroidAloneTexture = Utils.loadTexture("DroindAloneV3.jpg");
	blueGhostTexture = Utils.loadTexture("blueGhost.jpg");
	TronChessTexture = Utils.loadTexture("Chessboard.jpg");
	HoloTableTexture = Utils.loadTexture("HoloTableV2_top.jpg");
	RedTronTexture = Utils.loadTexture("RedTron.jpg");	
	BlueTronTexture = Utils.loadTexture("BlueTron.jpg");	
	WallArmorTexture = Utils.loadTexture("WallArmor.jpg");	
	BalconyExtensionTexture = Utils.loadTexture("BalconyExtensionV3Top.jpg");	
	BalconyExtensionV3BottomTexture = Utils.loadTexture("BalconyExtensionV3Bottom.jpg");	
	BirdBuildingExtensionTexture = Utils.loadTexture("BirdBuildingExtensionV1_TopV3.jpg");	
	TheWall_BottomTexture = Utils.loadTexture("TheWall_Bottom.jpg");	
	BillBoardBuilding_BottomTexture = Utils.loadTexture("BillBoardBuilding_Bottom.jpg");	
	BirdBuildingExtensionV1_BottomTexture = Utils.loadTexture("BirdBuildingExtensionV1_BottomV4.jpg");	
	BackHangerExtensionTexture = Utils.loadTexture("BackHangerExtension.jpg");	
	backgroundFOGTexture = Utils.loadTexture("CloudyBelow.jpg");	
	MasterChamberTexture = Utils.loadTexture("MasterLandingChamberV5.jpg");	
	HoloTable_bottomTexture = Utils.loadTexture("HoloTableV2_bottom.jpg");	
	MirrorGate_FrameTexture = Utils.loadTexture("MirrorGate_Frame.jpg");	
	MasterLandingChamber_FrontTexture = Utils.loadTexture("MasterLandingChamber_FrontV2.jpg");	
	MasterChamberDoorTexture = Utils.loadTexture("MasterChamberDoorV2_Back.jpg");	
	MasterChamberDoorBackTexture = Utils.loadTexture("MasterChamberDoorV2_Front.jpg");	
	MasterLandingChamberBELOWTexture = Utils.loadTexture("MasterLandingChamberBELOW.jpg");
		
		
	//Height Map, Fog, Noise, Skyboxes
	
	rockyTexture = Utils.loadTexture("bkgd2.jpg");
	heightMap = Utils.loadTexture("heightmap2.jpg");
		
	generateNoise();	
	noiseTexture = buildNoiseTexture();
		
	texRotMat.rotateY((float)Math.toRadians(30.0f));	
	texRotMat.rotateX((float)Math.toRadians(30.0f));
	texRotMat.rotateZ((float)Math.toRadians(30.0f));
		
	skyboxTexture = Utils.loadCubeMap("cubeMap");
	gl.glEnable(GL_TEXTURE_CUBE_MAP_SEAMLESS);
	skybox2Texture = Utils.loadCubeMap("cubeMap2");
	gl.glEnable(GL_TEXTURE_CUBE_MAP_SEAMLESS);
	
		//Setting for Shadows
	b.set(
			0.5f, 0.0f, 0.0f, 0.0f,
			0.0f, 0.5f, 0.0f, 0.0f,
			0.0f, 0.0f, 0.5f, 0.0f,
			0.5f, 0.5f, 0.5f, 1.0f);
	}
	
	
	private void setupShadowBuffers()
	{	GL4 gl = (GL4) GLContext.getCurrentGL();
		scSizeX = myCanvas.getWidth();
		scSizeY = myCanvas.getHeight();
	
		gl.glGenFramebuffers(1, shadowBuffer, 0);
	
		gl.glGenTextures(1, shadowTex, 0);
		gl.glBindTexture(GL_TEXTURE_2D, shadowTex[0]);
		gl.glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT32,
						scSizeX, scSizeY, 0, GL_DEPTH_COMPONENT, GL_FLOAT, null);
		gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_COMPARE_MODE, GL_COMPARE_REF_TO_TEXTURE);
		gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_COMPARE_FUNC, GL_LEQUAL);
		
		// may reduce shadow border artifacts
		gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
	}
	

	private void setupVertices()
	{	GL4 gl = (GL4) GLContext.getCurrentGL();
	
	
	// skybox
	
	float[] cubeVertexPositions =
	{	-1.0f,  1.0f, -1.0f, -1.0f, -1.0f, -1.0f, 1.0f, -1.0f, -1.0f,
		1.0f, -1.0f, -1.0f, 1.0f,  1.0f, -1.0f, -1.0f,  1.0f, -1.0f,
		1.0f, -1.0f, -1.0f, 1.0f, -1.0f,  1.0f, 1.0f,  1.0f, -1.0f,
		1.0f, -1.0f,  1.0f, 1.0f,  1.0f,  1.0f, 1.0f,  1.0f, -1.0f,
		1.0f, -1.0f,  1.0f, -1.0f, -1.0f,  1.0f, 1.0f,  1.0f,  1.0f,
		-1.0f, -1.0f,  1.0f, -1.0f,  1.0f,  1.0f, 1.0f,  1.0f,  1.0f,
		-1.0f, -1.0f,  1.0f, -1.0f, -1.0f, -1.0f, -1.0f,  1.0f,  1.0f,
		-1.0f, -1.0f, -1.0f, -1.0f,  1.0f, -1.0f, -1.0f,  1.0f,  1.0f,
		-1.0f, -1.0f,  1.0f,  1.0f, -1.0f,  1.0f,  1.0f, -1.0f, -1.0f,
		1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f,  1.0f,
		-1.0f,  1.0f, -1.0f, 1.0f,  1.0f, -1.0f, 1.0f,  1.0f,  1.0f,
		1.0f,  1.0f,  1.0f, -1.0f,  1.0f,  1.0f, -1.0f,  1.0f, -1.0f
	};
	
	
	//Axis Lines Stuff
	
	float[] xPositions = {
		0.0f, 0.0f, 0.0f, 
		100.0f, 0.0f, 0.0f, 
		0.0f, 0.05f, 0.0f	
	};

	float[] xTextures =	{
		0.0f, 0.0f, 1.0f, 
		0.0f, 1.0f, 1.0f	
	};

	float[] yPositions = {
		0.0f, 0.0f, 0.0f, 
		0.05f, 0.0f, 0.0f, 
		0.0f, 100.0f, 0.0f
	};

	float[] yTextures = {
		0.0f, 0.0f, 1.0f, 
		0.0f, 1.0f, 1.0f	
	};

	float[] zPositions = {
		0.0f, 0.0f, 0.0f, 
		0.0f, 0.0f, 100.0f, 
		0.0f, -0.05f, 0.0f
	};

	float[] zTextures =	{
		0.0f, 0.0f, 1.0f, 
		0.0f, 1.0f, 1.0f	
	};

	
		// Droid Alone
		
		DroidAloneNumObjVertices = DroidAloneModel.getNumVertices();
		Vector3f[] DroidAloneVertices = DroidAloneModel.getVertices();
		Vector2f[] DroidAloneTexCoords = DroidAloneModel.getTexCoords();
		Vector3f[] DroidAloneNormals = DroidAloneModel.getNormals();

		float[] DroidAlonePvalues = new float[DroidAloneNumObjVertices * 3];
		float[] DroidAloneTvalues = new float[DroidAloneNumObjVertices * 2];
		float[] DroidAloneNvalues = new float[DroidAloneNumObjVertices * 3];

		for (int i = 0; i < DroidAloneNumObjVertices; i++) {
			DroidAlonePvalues[i * 3] = (float) (DroidAloneVertices[i]).x();
			DroidAlonePvalues[i * 3 + 1] = (float) (DroidAloneVertices[i]).y();
			DroidAlonePvalues[i * 3 + 2] = (float) (DroidAloneVertices[i]).z();
			DroidAloneTvalues[i * 2] = (float) (DroidAloneTexCoords[i]).x();
			DroidAloneTvalues[i * 2 + 1] = (float) (DroidAloneTexCoords[i]).y();
			DroidAloneNvalues[i * 3] = (float) (DroidAloneNormals[i]).x();
			DroidAloneNvalues[i * 3 + 1] = (float) (DroidAloneNormals[i]).y();
			DroidAloneNvalues[i * 3 + 2] = (float) (DroidAloneNormals[i]).z();
		}
		
		
		//Cloud Layer
		
		CloudyLayerNumObjVertices = CloudyLayerModel.getNumVertices();
		Vector3f[] CloudyLayerVertices = CloudyLayerModel.getVertices();
		Vector2f[] CloudyLayerTexCoords = CloudyLayerModel.getTexCoords();
		Vector3f[] CloudyLayerNormals = CloudyLayerModel.getNormals();
		
		float[] CloudyLayerPvalues = new float[CloudyLayerNumObjVertices*3];
		float[] CloudyLayerTvalues = new float[CloudyLayerNumObjVertices*2];
		float[] CloudyLayerNvalues = new float[CloudyLayerNumObjVertices*3];
		
		for (int i=0; i<CloudyLayerNumObjVertices; i++)
		{	CloudyLayerPvalues[i*3]   = (float) (CloudyLayerVertices[i]).x();
			CloudyLayerPvalues[i*3+1] = (float) (CloudyLayerVertices[i]).y();
			CloudyLayerPvalues[i*3+2] = (float) (CloudyLayerVertices[i]).z();
			CloudyLayerTvalues[i*2]   = (float) (CloudyLayerTexCoords[i]).x();
			CloudyLayerTvalues[i*2+1] = (float) (CloudyLayerTexCoords[i]).y();
			CloudyLayerNvalues[i*3]   = (float) (CloudyLayerNormals[i]).x();
			CloudyLayerNvalues[i*3+1] = (float) (CloudyLayerNormals[i]).y();
			CloudyLayerNvalues[i*3+2] = (float) (CloudyLayerNormals[i]).z();
		}
		
		mySphere = new Sphere(96);
		numSphereVerts = mySphere.getIndices().length;
		
		
		//Sphere stuff
		
	int[] indices2 = mySphere.getIndices();
	Vector3f[] vert = mySphere.getVertices();
	Vector2f[] tex  = mySphere.getTexCoords();
	Vector3f[] norm = mySphere.getNormals();
	
	float[] spherepvalues = new float[indices2.length*3];
	float[] spheretvalues = new float[indices2.length*2];
	float[] spherenvalues = new float[indices2.length*3];
	
	for (int i=0; i<indices2.length; i++)
	{	spherepvalues[i*3] = (float) (vert[indices2[i]]).x;
	spherepvalues[i*3+1] = (float) (vert[indices2[i]]).y;
	spherepvalues[i*3+2] = (float) (vert[indices2[i]]).z;
	spheretvalues[i*2] = (float) (tex[indices2[i]]).x;
	spheretvalues[i*2+1] = (float) (tex[indices2[i]]).y;
	spherenvalues[i*3] = (float) (norm[indices2[i]]).x;
	spherenvalues[i*3+1]= (float)(norm[indices2[i]]).y;
	spherenvalues[i*3+2]=(float) (norm[indices2[i]]).z;
	}
	
	
	//IG Stuff
	
	igNumObjigVertices = igModel.getNumVertices();
	Vector3f[] igVertices = igModel.getVertices();
	Vector2f[] igTexCoords = igModel.getTexCoords();
	Vector3f[] igNormals = igModel.getNormals();
	
	float[] igPvalues = new float[igNumObjigVertices*3];
	float[] igTvalues = new float[igNumObjigVertices*2];
	float[] igNvalues = new float[igNumObjigVertices*3];
	
	for (int i=0; i<igNumObjigVertices; i++)
	{	igPvalues[i*3]   = (float) (igVertices[i]).x();
		igPvalues[i*3+1] = (float) (igVertices[i]).y();
		igPvalues[i*3+2] = (float) (igVertices[i]).z();
		igTvalues[i*2]   = (float) (igTexCoords[i]).x();
		igTvalues[i*2+1] = (float) (igTexCoords[i]).y();
		igNvalues[i*3]   = (float) (igNormals[i]).x();
		igNvalues[i*3+1] = (float) (igNormals[i]).y();
		igNvalues[i*3+2] = (float) (igNormals[i]).z();
	}
	
	
	//Back Landing Pad
	
	backHangerNumObjVertices = backHangerModel.getNumVertices();
	Vector3f[] backHangerVertices = backHangerModel.getVertices();
	Vector2f[] backHangerTexCoords = backHangerModel.getTexCoords();
	Vector3f[] backHangerNormals = backHangerModel.getNormals();
	
	float[] backHangerPvalues = new float[backHangerNumObjVertices*3];
	float[] backHangerTvalues = new float[backHangerNumObjVertices*2];
	float[] backHangerNvalues = new float[backHangerNumObjVertices*3];
	
	for (int i=0; i<backHangerNumObjVertices; i++)
	{	backHangerPvalues[i*3]   = (float) (backHangerVertices[i]).x();
		backHangerPvalues[i*3+1] = (float) (backHangerVertices[i]).y();
		backHangerPvalues[i*3+2] = (float) (backHangerVertices[i]).z();
		backHangerTvalues[i*2]   = (float) (backHangerTexCoords[i]).x();
		backHangerTvalues[i*2+1] = (float) (backHangerTexCoords[i]).y();
		backHangerNvalues[i*3]   = (float) (backHangerNormals[i]).x();
		backHangerNvalues[i*3+1] = (float) (backHangerNormals[i]).y();
		backHangerNvalues[i*3+2] = (float) (backHangerNormals[i]).z();
	}
	
	
	//Balcony with droid
	
	BalconyDroidNumObjVertices = BalconyDroidModel.getNumVertices();
	Vector3f[] BalconyDroidVertices = BalconyDroidModel.getVertices();
	Vector2f[] BalconyDroidTexCoords = BalconyDroidModel.getTexCoords();
	Vector3f[] BalconyDroidNormals = BalconyDroidModel.getNormals();
	
	float[] BalconyDroidPvalues = new float[BalconyDroidNumObjVertices*3];
	float[] BalconyDroidTvalues = new float[BalconyDroidNumObjVertices*2];
	float[] BalconyDroidNvalues = new float[BalconyDroidNumObjVertices*3];
	
	for (int i=0; i<BalconyDroidNumObjVertices; i++)
	{	BalconyDroidPvalues[i*3]   = (float) (BalconyDroidVertices[i]).x();
		BalconyDroidPvalues[i*3+1] = (float) (BalconyDroidVertices[i]).y();
		BalconyDroidPvalues[i*3+2] = (float) (BalconyDroidVertices[i]).z();
		BalconyDroidTvalues[i*2]   = (float) (BalconyDroidTexCoords[i]).x();
		BalconyDroidTvalues[i*2+1] = (float) (BalconyDroidTexCoords[i]).y();
		BalconyDroidNvalues[i*3]   = (float) (BalconyDroidNormals[i]).x();
		BalconyDroidNvalues[i*3+1] = (float) (BalconyDroidNormals[i]).y();
		BalconyDroidNvalues[i*3+2] = (float) (BalconyDroidNormals[i]).z();
	}
	
	
	// Boba Fett
	
	bobaFettNumObjVertices = bobaFettModel.getNumVertices();
	Vector3f[] bobaFettVertices = bobaFettModel.getVertices();
	Vector2f[] bobaFettTexCoords = bobaFettModel.getTexCoords();
	Vector3f[] bobaFettNormals = bobaFettModel.getNormals();
	
	float[] bobaFettPvalues = new float[bobaFettNumObjVertices*3];
	float[] bobaFettTvalues = new float[bobaFettNumObjVertices*2];
	float[] bobaFettNvalues = new float[bobaFettNumObjVertices*3];
	
	for (int i=0; i<bobaFettNumObjVertices; i++)
	{	bobaFettPvalues[i*3]   = (float) (bobaFettVertices[i]).x();
		bobaFettPvalues[i*3+1] = (float) (bobaFettVertices[i]).y();
		bobaFettPvalues[i*3+2] = (float) (bobaFettVertices[i]).z();
		bobaFettTvalues[i*2]   = (float) (bobaFettTexCoords[i]).x();
		bobaFettTvalues[i*2+1] = (float) (bobaFettTexCoords[i]).y();
		bobaFettNvalues[i*3]   = (float) (bobaFettNormals[i]).x();
		bobaFettNvalues[i*3+1] = (float) (bobaFettNormals[i]).y();
		bobaFettNvalues[i*3+2] = (float) (bobaFettNormals[i]).z();
	}
	
	
	//Bird building and other landing pad
	
	BirdBuildingNumObjVertices = BirdBuildingModel.getNumVertices();
	Vector3f[] BirdBuildingVertices = BirdBuildingModel.getVertices();
	Vector2f[] BirdBuildingTexCoords = BirdBuildingModel.getTexCoords();
	Vector3f[] BirdBuildingNormals = BirdBuildingModel.getNormals();
	
	float[] BirdBuildingPvalues = new float[BirdBuildingNumObjVertices*3];
	float[] BirdBuildingTvalues = new float[BirdBuildingNumObjVertices*2];
	float[] BirdBuildingNvalues = new float[BirdBuildingNumObjVertices*3];
	
	for (int i=0; i<BirdBuildingNumObjVertices; i++)
	{	BirdBuildingPvalues[i*3]   = (float) (BirdBuildingVertices[i]).x();
		BirdBuildingPvalues[i*3+1] = (float) (BirdBuildingVertices[i]).y();
		BirdBuildingPvalues[i*3+2] = (float) (BirdBuildingVertices[i]).z();
		BirdBuildingTvalues[i*2]   = (float) (BirdBuildingTexCoords[i]).x();
		BirdBuildingTvalues[i*2+1] = (float) (BirdBuildingTexCoords[i]).y();
		BirdBuildingNvalues[i*3]   = (float) (BirdBuildingNormals[i]).x();
		BirdBuildingNvalues[i*3+1] = (float) (BirdBuildingNormals[i]).y();
		BirdBuildingNvalues[i*3+2] = (float) (BirdBuildingNormals[i]).z();
	}
	
	
	//The Wall
	
	theWallNumObjVertices = theWallModel.getNumVertices();
	Vector3f[] theWallVertices = theWallModel.getVertices();
	Vector2f[] theWallTexCoords = theWallModel.getTexCoords();
	Vector3f[] theWallNormals = theWallModel.getNormals();
	
	float[] theWallPvalues = new float[theWallNumObjVertices*3];
	float[] theWallTvalues = new float[theWallNumObjVertices*2];
	float[] theWallNvalues = new float[theWallNumObjVertices*3];
	
	for (int i=0; i<theWallNumObjVertices; i++)
	{	theWallPvalues[i*3]   = (float) (theWallVertices[i]).x();
		theWallPvalues[i*3+1] = (float) (theWallVertices[i]).y();
		theWallPvalues[i*3+2] = (float) (theWallVertices[i]).z();
		theWallTvalues[i*2]   = (float) (theWallTexCoords[i]).x();
		theWallTvalues[i*2+1] = (float) (theWallTexCoords[i]).y();
		theWallNvalues[i*3]   = (float) (theWallNormals[i]).x();
		theWallNvalues[i*3+1] = (float) (theWallNormals[i]).y();
		theWallNvalues[i*3+2] = (float) (theWallNormals[i]).z();
	}
	
	
	//Tower Building
	
	BillBoardBuildingNumObjVertices = BillBoardBuildingModel.getNumVertices();
	Vector3f[] BillBoardBuildingVertices = BillBoardBuildingModel.getVertices();
	Vector2f[] BillBoardBuildingTexCoords = BillBoardBuildingModel.getTexCoords();
	Vector3f[] BillBoardBuildingNormals = BillBoardBuildingModel.getNormals();
	
	float[] BillBoardBuildingPvalues = new float[BillBoardBuildingNumObjVertices*3];
	float[] BillBoardBuildingTvalues = new float[BillBoardBuildingNumObjVertices*2];
	float[] BillBoardBuildingNvalues = new float[BillBoardBuildingNumObjVertices*3];
	
	for (int i=0; i<BillBoardBuildingNumObjVertices; i++)
	{	BillBoardBuildingPvalues[i*3]   = (float) (BillBoardBuildingVertices[i]).x();
		BillBoardBuildingPvalues[i*3+1] = (float) (BillBoardBuildingVertices[i]).y();
		BillBoardBuildingPvalues[i*3+2] = (float) (BillBoardBuildingVertices[i]).z();
		BillBoardBuildingTvalues[i*2]   = (float) (BillBoardBuildingTexCoords[i]).x();
		BillBoardBuildingTvalues[i*2+1] = (float) (BillBoardBuildingTexCoords[i]).y();
		BillBoardBuildingNvalues[i*3]   = (float) (BillBoardBuildingNormals[i]).x();
		BillBoardBuildingNvalues[i*3+1] = (float) (BillBoardBuildingNormals[i]).y();
		BillBoardBuildingNvalues[i*3+2] = (float) (BillBoardBuildingNormals[i]).z();
	}
	
	
	//Blue Ghost
	
	blueGhostNumObjVertices = blueGhostModel.getNumVertices();
	Vector3f[] blueGhostVertices = blueGhostModel.getVertices();
	Vector2f[] blueGhostTexCoords = blueGhostModel.getTexCoords();
	Vector3f[] blueGhostNormals = blueGhostModel.getNormals();

	float[] blueGhostPvalues = new float[blueGhostNumObjVertices * 3];
	float[] blueGhostTvalues = new float[blueGhostNumObjVertices * 2];
	float[] blueGhostNvalues = new float[blueGhostNumObjVertices * 3];

	for (int i = 0; i < blueGhostNumObjVertices; i++) {
		blueGhostPvalues[i * 3] = (float) (blueGhostVertices[i]).x();
		blueGhostPvalues[i * 3 + 1] = (float) (blueGhostVertices[i]).y();
		blueGhostPvalues[i * 3 + 2] = (float) (blueGhostVertices[i]).z();
		blueGhostTvalues[i * 2] = (float) (blueGhostTexCoords[i]).x();
		blueGhostTvalues[i * 2 + 1] = (float) (blueGhostTexCoords[i]).y();
		blueGhostNvalues[i * 3] = (float) (blueGhostNormals[i]).x();
		blueGhostNvalues[i * 3 + 1] = (float) (blueGhostNormals[i]).y();
		blueGhostNvalues[i * 3 + 2] = (float) (blueGhostNormals[i]).z();
	}
	
	
	//Tron Chess Set
	
	TronChessNumObjVertices = TronChessModel.getNumVertices();
	Vector3f[] TronChessVertices = TronChessModel.getVertices();
	Vector2f[] TronChessTexCoords = TronChessModel.getTexCoords();
	Vector3f[] TronChessNormals = TronChessModel.getNormals();

	float[] TronChessPvalues = new float[TronChessNumObjVertices * 3];
	float[] TronChessTvalues = new float[TronChessNumObjVertices * 2];
	float[] TronChessNvalues = new float[TronChessNumObjVertices * 3];

	for (int i = 0; i < TronChessNumObjVertices; i++) {
		TronChessPvalues[i * 3] = (float) (TronChessVertices[i]).x();
		TronChessPvalues[i * 3 + 1] = (float) (TronChessVertices[i]).y();
		TronChessPvalues[i * 3 + 2] = (float) (TronChessVertices[i]).z();
		TronChessTvalues[i * 2] = (float) (TronChessTexCoords[i]).x();
		TronChessTvalues[i * 2 + 1] = (float) (TronChessTexCoords[i]).y();
		TronChessNvalues[i * 3] = (float) (TronChessNormals[i]).x();
		TronChessNvalues[i * 3 + 1] = (float) (TronChessNormals[i]).y();
		TronChessNvalues[i * 3 + 2] = (float) (TronChessNormals[i]).z();
	}
	
	
	// Holotable
	
	HoloTableNumObjVertices = HoloTableModel.getNumVertices();
	Vector3f[] HoloTableVertices = HoloTableModel.getVertices();
	Vector2f[] HoloTableTexCoords = HoloTableModel.getTexCoords();
	Vector3f[] HoloTableNormals = HoloTableModel.getNormals();

	float[] HoloTablePvalues = new float[HoloTableNumObjVertices * 3];
	float[] HoloTableTvalues = new float[HoloTableNumObjVertices * 2];
	float[] HoloTableNvalues = new float[HoloTableNumObjVertices * 3];

	for (int i = 0; i < HoloTableNumObjVertices; i++) {
		HoloTablePvalues[i * 3] = (float) (HoloTableVertices[i]).x();
		HoloTablePvalues[i * 3 + 1] = (float) (HoloTableVertices[i]).y();
		HoloTablePvalues[i * 3 + 2] = (float) (HoloTableVertices[i]).z();
		HoloTableTvalues[i * 2] = (float) (HoloTableTexCoords[i]).x();
		HoloTableTvalues[i * 2 + 1] = (float) (HoloTableTexCoords[i]).y();
		HoloTableNvalues[i * 3] = (float) (HoloTableNormals[i]).x();
		HoloTableNvalues[i * 3 + 1] = (float) (HoloTableNormals[i]).y();
		HoloTableNvalues[i * 3 + 2] = (float) (HoloTableNormals[i]).z();
	}
	
	
	//Red Tron
	
	RedTronNumObjVertices = RedTronModel.getNumVertices();
	Vector3f[] RedTronVertices = RedTronModel.getVertices();
	Vector2f[] RedTronTexCoords = RedTronModel.getTexCoords();
	Vector3f[] RedTronNormals = RedTronModel.getNormals();

	float[] RedTronPvalues = new float[RedTronNumObjVertices * 3];
	float[] RedTronTvalues = new float[RedTronNumObjVertices * 2];
	float[] RedTronNvalues = new float[RedTronNumObjVertices * 3];

	for (int i = 0; i < RedTronNumObjVertices; i++) {
		RedTronPvalues[i * 3] = (float) (RedTronVertices[i]).x();
		RedTronPvalues[i * 3 + 1] = (float) (RedTronVertices[i]).y();
		RedTronPvalues[i * 3 + 2] = (float) (RedTronVertices[i]).z();
		RedTronTvalues[i * 2] = (float) (RedTronTexCoords[i]).x();
		RedTronTvalues[i * 2 + 1] = (float) (RedTronTexCoords[i]).y();
		RedTronNvalues[i * 3] = (float) (RedTronNormals[i]).x();
		RedTronNvalues[i * 3 + 1] = (float) (RedTronNormals[i]).y();
		RedTronNvalues[i * 3 + 2] = (float) (RedTronNormals[i]).z();
	}

	
	//Blue Tron
	
	BlueTronNumObjVertices = BlueTronModel.getNumVertices();
	Vector3f[] BlueTronVertices = BlueTronModel.getVertices();
	Vector2f[] BlueTronTexCoords = BlueTronModel.getTexCoords();
	Vector3f[] BlueTronNormals = BlueTronModel.getNormals();

	float[] BlueTronPvalues = new float[BlueTronNumObjVertices * 3];
	float[] BlueTronTvalues = new float[BlueTronNumObjVertices * 2];
	float[] BlueTronNvalues = new float[BlueTronNumObjVertices * 3];

	for (int i = 0; i < BlueTronNumObjVertices; i++) {
		BlueTronPvalues[i * 3] = (float) (BlueTronVertices[i]).x();
		BlueTronPvalues[i * 3 + 1] = (float) (BlueTronVertices[i]).y();
		BlueTronPvalues[i * 3 + 2] = (float) (BlueTronVertices[i]).z();
		BlueTronTvalues[i * 2] = (float) (BlueTronTexCoords[i]).x();
		BlueTronTvalues[i * 2 + 1] = (float) (BlueTronTexCoords[i]).y();
		BlueTronNvalues[i * 3] = (float) (BlueTronNormals[i]).x();
		BlueTronNvalues[i * 3 + 1] = (float) (BlueTronNormals[i]).y();
		BlueTronNvalues[i * 3 + 2] = (float) (BlueTronNormals[i]).z();
	}

	
	//Wall Armor
	
	WallArmorNumObjVertices = WallArmorModel.getNumVertices();
	Vector3f[] WallArmorVertices = WallArmorModel.getVertices();
	Vector2f[] WallArmorTexCoords = WallArmorModel.getTexCoords();
	Vector3f[] WallArmorNormals = WallArmorModel.getNormals();

	float[] WallArmorPvalues = new float[WallArmorNumObjVertices * 3];
	float[] WallArmorTvalues = new float[WallArmorNumObjVertices * 2];
	float[] WallArmorNvalues = new float[WallArmorNumObjVertices * 3];

	for (int i = 0; i < WallArmorNumObjVertices; i++) {
		WallArmorPvalues[i * 3] = (float) (WallArmorVertices[i]).x();
		WallArmorPvalues[i * 3 + 1] = (float) (WallArmorVertices[i]).y();
		WallArmorPvalues[i * 3 + 2] = (float) (WallArmorVertices[i]).z();
		WallArmorTvalues[i * 2] = (float) (WallArmorTexCoords[i]).x();
		WallArmorTvalues[i * 2 + 1] = (float) (WallArmorTexCoords[i]).y();
		WallArmorNvalues[i * 3] = (float) (WallArmorNormals[i]).x();
		WallArmorNvalues[i * 3 + 1] = (float) (WallArmorNormals[i]).y();
		WallArmorNvalues[i * 3 + 2] = (float) (WallArmorNormals[i]).z();
	}
	
	
	//Balcony Extension
	
	BalconyExtensionNumObjVertices = BalconyExtensionModel.getNumVertices();
	Vector3f[] BalconyExtensionVertices = BalconyExtensionModel.getVertices();
	Vector2f[] BalconyExtensionTexCoords = BalconyExtensionModel.getTexCoords();
	Vector3f[] BalconyExtensionNormals = BalconyExtensionModel.getNormals();

	float[] BalconyExtensionPvalues = new float[BalconyExtensionNumObjVertices * 3];
	float[] BalconyExtensionTvalues = new float[BalconyExtensionNumObjVertices * 2];
	float[] BalconyExtensionNvalues = new float[BalconyExtensionNumObjVertices * 3];

	for (int i = 0; i < BalconyExtensionNumObjVertices; i++) {
		BalconyExtensionPvalues[i * 3] = (float) (BalconyExtensionVertices[i]).x();
		BalconyExtensionPvalues[i * 3 + 1] = (float) (BalconyExtensionVertices[i]).y();
		BalconyExtensionPvalues[i * 3 + 2] = (float) (BalconyExtensionVertices[i]).z();
		BalconyExtensionTvalues[i * 2] = (float) (BalconyExtensionTexCoords[i]).x();
		BalconyExtensionTvalues[i * 2 + 1] = (float) (BalconyExtensionTexCoords[i]).y();
		BalconyExtensionNvalues[i * 3] = (float) (BalconyExtensionNormals[i]).x();
		BalconyExtensionNvalues[i * 3 + 1] = (float) (BalconyExtensionNormals[i]).y();
		BalconyExtensionNvalues[i * 3 + 2] = (float) (BalconyExtensionNormals[i]).z();
	}
	
	
	//Balcony Bottom Extension
	
	BalconyExtensionV3BottomNumObjVertices = BalconyExtensionV3BottomModel.getNumVertices();
	Vector3f[] BalconyExtensionV3BottomVertices = BalconyExtensionV3BottomModel.getVertices();
	Vector2f[] BalconyExtensionV3BottomTexCoords = BalconyExtensionV3BottomModel.getTexCoords();
	Vector3f[] BalconyExtensionV3BottomNormals = BalconyExtensionV3BottomModel.getNormals();

	float[] BalconyExtensionV3BottomPvalues = new float[BalconyExtensionV3BottomNumObjVertices * 3];
	float[] BalconyExtensionV3BottomTvalues = new float[BalconyExtensionV3BottomNumObjVertices * 2];
	float[] BalconyExtensionV3BottomNvalues = new float[BalconyExtensionV3BottomNumObjVertices * 3];

	for (int i = 0; i < BalconyExtensionV3BottomNumObjVertices; i++) {
		BalconyExtensionV3BottomPvalues[i * 3] = (float) (BalconyExtensionV3BottomVertices[i]).x();
		BalconyExtensionV3BottomPvalues[i * 3 + 1] = (float) (BalconyExtensionV3BottomVertices[i]).y();
		BalconyExtensionV3BottomPvalues[i * 3 + 2] = (float) (BalconyExtensionV3BottomVertices[i]).z();
		BalconyExtensionV3BottomTvalues[i * 2] = (float) (BalconyExtensionV3BottomTexCoords[i]).x();
		BalconyExtensionV3BottomTvalues[i * 2 + 1] = (float) (BalconyExtensionV3BottomTexCoords[i]).y();
		BalconyExtensionV3BottomNvalues[i * 3] = (float) (BalconyExtensionV3BottomNormals[i]).x();
		BalconyExtensionV3BottomNvalues[i * 3 + 1] = (float) (BalconyExtensionV3BottomNormals[i]).y();
		BalconyExtensionV3BottomNvalues[i * 3 + 2] = (float) (BalconyExtensionV3BottomNormals[i]).z();
	}
	
	
	//Bird Building Extension
	
	BirdBuildingExtensionNumObjVertices = BirdBuildingExtensionModel.getNumVertices();
	Vector3f[] BirdBuildingExtensionVertices = BirdBuildingExtensionModel.getVertices();
	Vector2f[] BirdBuildingExtensionTexCoords = BirdBuildingExtensionModel.getTexCoords();
	Vector3f[] BirdBuildingExtensionNormals = BirdBuildingExtensionModel.getNormals();

	float[] BirdBuildingExtensionPvalues = new float[BirdBuildingExtensionNumObjVertices * 3];
	float[] BirdBuildingExtensionTvalues = new float[BirdBuildingExtensionNumObjVertices * 2];
	float[] BirdBuildingExtensionNvalues = new float[BirdBuildingExtensionNumObjVertices * 3];

	for (int i = 0; i < BirdBuildingExtensionNumObjVertices; i++) {
		BirdBuildingExtensionPvalues[i * 3] = (float) (BirdBuildingExtensionVertices[i]).x();
		BirdBuildingExtensionPvalues[i * 3 + 1] = (float) (BirdBuildingExtensionVertices[i]).y();
		BirdBuildingExtensionPvalues[i * 3 + 2] = (float) (BirdBuildingExtensionVertices[i]).z();
		BirdBuildingExtensionTvalues[i * 2] = (float) (BirdBuildingExtensionTexCoords[i]).x();
		BirdBuildingExtensionTvalues[i * 2 + 1] = (float) (BirdBuildingExtensionTexCoords[i]).y();
		BirdBuildingExtensionNvalues[i * 3] = (float) (BirdBuildingExtensionNormals[i]).x();
		BirdBuildingExtensionNvalues[i * 3 + 1] = (float) (BirdBuildingExtensionNormals[i]).y();
		BirdBuildingExtensionNvalues[i * 3 + 2] = (float) (BirdBuildingExtensionNormals[i]).z();
	}
	
	
	//Height Map and Fog
	
	numGroundVertices = ground.getNumVertices();
	Vector3f[] verticesGround = ground.getVertices();
	Vector2f[] texCoordsGround = ground.getTexCoords();
	Vector3f[] normalsGround = ground.getNormals();

	float[] pvaluesGround = new float[numGroundVertices*3];
	float[] tvaluesGround = new float[numGroundVertices*2];
	float[] nvaluesGround = new float[numGroundVertices*3];
	
	for (int i=0; i<numGroundVertices; i++)
	{	pvaluesGround[i*3]   = (float) (verticesGround[i]).x();
		pvaluesGround[i*3+1] = (float) (verticesGround[i]).y();
		pvaluesGround[i*3+2] = (float) (verticesGround[i]).z();
		tvaluesGround[i*2]   = (float) (texCoordsGround[i]).x();
		tvaluesGround[i*2+1] = (float) (texCoordsGround[i]).y();
		nvaluesGround[i*3]   = (float) (normalsGround[i]).x();
		nvaluesGround[i*3+1] = (float) (normalsGround[i]).y();
		nvaluesGround[i*3+2] = (float) (normalsGround[i]).z();
	}	
	
	
	//The Wall Extension
	
	TheWall_BottomNumObjVertices = TheWall_BottomModel.getNumVertices();
	Vector3f[] TheWall_BottomVertices = TheWall_BottomModel.getVertices();
	Vector2f[] TheWall_BottomTexCoords = TheWall_BottomModel.getTexCoords();
	Vector3f[] TheWall_BottomNormals = TheWall_BottomModel.getNormals();

	float[] TheWall_BottomPvalues = new float[TheWall_BottomNumObjVertices * 3];
	float[] TheWall_BottomTvalues = new float[TheWall_BottomNumObjVertices * 2];
	float[] TheWall_BottomNvalues = new float[TheWall_BottomNumObjVertices * 3];

	for (int i = 0; i < TheWall_BottomNumObjVertices; i++) {
		TheWall_BottomPvalues[i * 3] = (float) (TheWall_BottomVertices[i]).x();
		TheWall_BottomPvalues[i * 3 + 1] = (float) (TheWall_BottomVertices[i]).y();
		TheWall_BottomPvalues[i * 3 + 2] = (float) (TheWall_BottomVertices[i]).z();
		TheWall_BottomTvalues[i * 2] = (float) (TheWall_BottomTexCoords[i]).x();
		TheWall_BottomTvalues[i * 2 + 1] = (float) (TheWall_BottomTexCoords[i]).y();
		TheWall_BottomNvalues[i * 3] = (float) (TheWall_BottomNormals[i]).x();
		TheWall_BottomNvalues[i * 3 + 1] = (float) (TheWall_BottomNormals[i]).y();
		TheWall_BottomNvalues[i * 3 + 2] = (float) (TheWall_BottomNormals[i]).z();
	}
	
	
	//Tower Building Extension
	
	BillBoardBuilding_BottomNumObjVertices = BillBoardBuilding_BottomModel.getNumVertices();
	Vector3f[] BillBoardBuilding_BottomVertices = BillBoardBuilding_BottomModel.getVertices();
	Vector2f[] BillBoardBuilding_BottomTexCoords = BillBoardBuilding_BottomModel.getTexCoords();
	Vector3f[] BillBoardBuilding_BottomNormals = BillBoardBuilding_BottomModel.getNormals();

	float[] BillBoardBuilding_BottomPvalues = new float[BillBoardBuilding_BottomNumObjVertices * 3];
	float[] BillBoardBuilding_BottomTvalues = new float[BillBoardBuilding_BottomNumObjVertices * 2];
	float[] BillBoardBuilding_BottomNvalues = new float[BillBoardBuilding_BottomNumObjVertices * 3];

	for (int i = 0; i < BillBoardBuilding_BottomNumObjVertices; i++) {
		BillBoardBuilding_BottomPvalues[i * 3] = (float) (BillBoardBuilding_BottomVertices[i]).x();
		BillBoardBuilding_BottomPvalues[i * 3 + 1] = (float) (BillBoardBuilding_BottomVertices[i]).y();
		BillBoardBuilding_BottomPvalues[i * 3 + 2] = (float) (BillBoardBuilding_BottomVertices[i]).z();
		BillBoardBuilding_BottomTvalues[i * 2] = (float) (BillBoardBuilding_BottomTexCoords[i]).x();
		BillBoardBuilding_BottomTvalues[i * 2 + 1] = (float) (BillBoardBuilding_BottomTexCoords[i]).y();
		BillBoardBuilding_BottomNvalues[i * 3] = (float) (BillBoardBuilding_BottomNormals[i]).x();
		BillBoardBuilding_BottomNvalues[i * 3 + 1] = (float) (BillBoardBuilding_BottomNormals[i]).y();
		BillBoardBuilding_BottomNvalues[i * 3 + 2] = (float) (BillBoardBuilding_BottomNormals[i]).z();
	}
	
	
	//Bird Building Below
	
	BirdBuildingExtensionV1_BottomNumObjVertices = BirdBuildingExtensionV1_BottomModel.getNumVertices();
	Vector3f[] BirdBuildingExtensionV1_BottomVertices = BirdBuildingExtensionV1_BottomModel.getVertices();
	Vector2f[] BirdBuildingExtensionV1_BottomTexCoords = BirdBuildingExtensionV1_BottomModel.getTexCoords();
	Vector3f[] BirdBuildingExtensionV1_BottomNormals = BirdBuildingExtensionV1_BottomModel.getNormals();

	float[] BirdBuildingExtensionV1_BottomPvalues = new float[BirdBuildingExtensionV1_BottomNumObjVertices * 3];
	float[] BirdBuildingExtensionV1_BottomTvalues = new float[BirdBuildingExtensionV1_BottomNumObjVertices * 2];
	float[] BirdBuildingExtensionV1_BottomNvalues = new float[BirdBuildingExtensionV1_BottomNumObjVertices * 3];

	for (int i = 0; i < BirdBuildingExtensionV1_BottomNumObjVertices; i++) {
		BirdBuildingExtensionV1_BottomPvalues[i * 3] = (float) (BirdBuildingExtensionV1_BottomVertices[i]).x();
		BirdBuildingExtensionV1_BottomPvalues[i * 3 + 1] = (float) (BirdBuildingExtensionV1_BottomVertices[i]).y();
		BirdBuildingExtensionV1_BottomPvalues[i * 3 + 2] = (float) (BirdBuildingExtensionV1_BottomVertices[i]).z();
		BirdBuildingExtensionV1_BottomTvalues[i * 2] = (float) (BirdBuildingExtensionV1_BottomTexCoords[i]).x();
		BirdBuildingExtensionV1_BottomTvalues[i * 2 + 1] = (float) (BirdBuildingExtensionV1_BottomTexCoords[i]).y();
		BirdBuildingExtensionV1_BottomNvalues[i * 3] = (float) (BirdBuildingExtensionV1_BottomNormals[i]).x();
		BirdBuildingExtensionV1_BottomNvalues[i * 3 + 1] = (float) (BirdBuildingExtensionV1_BottomNormals[i]).y();
		BirdBuildingExtensionV1_BottomNvalues[i * 3 + 2] = (float) (BirdBuildingExtensionV1_BottomNormals[i]).z();
	}
	
	
	//Back Hanger Extension
	
	BackHangerExtensionNumObjVertices = BackHangerExtensionModel.getNumVertices();
	Vector3f[] BackHangerExtensionVertices = BackHangerExtensionModel.getVertices();
	Vector2f[] BackHangerExtensionTexCoords = BackHangerExtensionModel.getTexCoords();
	Vector3f[] BackHangerExtensionNormals = BackHangerExtensionModel.getNormals();

	float[] BackHangerExtensionPvalues = new float[BackHangerExtensionNumObjVertices * 3];
	float[] BackHangerExtensionTvalues = new float[BackHangerExtensionNumObjVertices * 2];
	float[] BackHangerExtensionNvalues = new float[BackHangerExtensionNumObjVertices * 3];

	for (int i = 0; i < BackHangerExtensionNumObjVertices; i++) {
		BackHangerExtensionPvalues[i * 3] = (float) (BackHangerExtensionVertices[i]).x();
		BackHangerExtensionPvalues[i * 3 + 1] = (float) (BackHangerExtensionVertices[i]).y();
		BackHangerExtensionPvalues[i * 3 + 2] = (float) (BackHangerExtensionVertices[i]).z();
		BackHangerExtensionTvalues[i * 2] = (float) (BackHangerExtensionTexCoords[i]).x();
		BackHangerExtensionTvalues[i * 2 + 1] = (float) (BackHangerExtensionTexCoords[i]).y();
		BackHangerExtensionNvalues[i * 3] = (float) (BackHangerExtensionNormals[i]).x();
		BackHangerExtensionNvalues[i * 3 + 1] = (float) (BackHangerExtensionNormals[i]).y();
		BackHangerExtensionNvalues[i * 3 + 2] = (float) (BackHangerExtensionNormals[i]).z();
	}
	
	
	//Cloud Back
	
	backgroundFOGNumObjVertices = backgroundFOGModel.getNumVertices();
	Vector3f[] backgroundFOGVertices = backgroundFOGModel.getVertices();
	Vector2f[] backgroundFOGTexCoords = backgroundFOGModel.getTexCoords();
	Vector3f[] backgroundFOGNormals = backgroundFOGModel.getNormals();

	float[] backgroundFOGPvalues = new float[backgroundFOGNumObjVertices * 3];
	float[] backgroundFOGTvalues = new float[backgroundFOGNumObjVertices * 2];
	float[] backgroundFOGNvalues = new float[backgroundFOGNumObjVertices * 3];

	for (int i = 0; i < backgroundFOGNumObjVertices; i++) {
		backgroundFOGPvalues[i * 3] = (float) (backgroundFOGVertices[i]).x();
		backgroundFOGPvalues[i * 3 + 1] = (float) (backgroundFOGVertices[i]).y();
		backgroundFOGPvalues[i * 3 + 2] = (float) (backgroundFOGVertices[i]).z();
		backgroundFOGTvalues[i * 2] = (float) (backgroundFOGTexCoords[i]).x();
		backgroundFOGTvalues[i * 2 + 1] = (float) (backgroundFOGTexCoords[i]).y();
		backgroundFOGNvalues[i * 3] = (float) (backgroundFOGNormals[i]).x();
		backgroundFOGNvalues[i * 3 + 1] = (float) (backgroundFOGNormals[i]).y();
		backgroundFOGNvalues[i * 3 + 2] = (float) (backgroundFOGNormals[i]).z();
	}
	
	
	//Master Chamber
	
	nummasterChamberVertices = masterChamber.getNumVertices();
	Vector3f[] masterChambervertices = masterChamber.getVertices();
	Vector2f[] masterChambertexCoords = masterChamber.getTexCoords();
	Vector3f[] masterChambernormals = masterChamber.getNormals();
	
	float[] masterChamberpvalues = new float[nummasterChamberVertices*3];
	float[] masterChambertvalues = new float[nummasterChamberVertices*2];
	float[] masterChambernvalues = new float[nummasterChamberVertices*3];
	
	for (int i=0; i<nummasterChamberVertices; i++)
	{	masterChamberpvalues[i*3]   = (float) (masterChambervertices[i]).x();
		masterChamberpvalues[i*3+1] = (float) (masterChambervertices[i]).y();
		masterChamberpvalues[i*3+2] = (float) (masterChambervertices[i]).z();
		masterChambertvalues[i*2]   = (float) (masterChambertexCoords[i]).x();
		masterChambertvalues[i*2+1] = (float) (masterChambertexCoords[i]).y();
		masterChambernvalues[i*3]   = (float) (masterChambernormals[i]).x();
		masterChambernvalues[i*3+1] = (float) (masterChambernormals[i]).y();
		masterChambernvalues[i*3+2] = (float) (masterChambernormals[i]).z();
	}
	
	
	//Master Chamber Front
	
	MasterLandingChamber_FrontNumObjVertices = MasterLandingChamber_FrontModel.getNumVertices();
	Vector3f[] MasterLandingChamber_FrontVertices = MasterLandingChamber_FrontModel.getVertices();
	Vector2f[] MasterLandingChamber_FrontTexCoords = MasterLandingChamber_FrontModel.getTexCoords();
	Vector3f[] MasterLandingChamber_FrontNormals = MasterLandingChamber_FrontModel.getNormals();

	float[] MasterLandingChamber_FrontPvalues = new float[MasterLandingChamber_FrontNumObjVertices * 3];
	float[] MasterLandingChamber_FrontTvalues = new float[MasterLandingChamber_FrontNumObjVertices * 2];
	float[] MasterLandingChamber_FrontNvalues = new float[MasterLandingChamber_FrontNumObjVertices * 3];

	for (int i = 0; i < MasterLandingChamber_FrontNumObjVertices; i++) {
		MasterLandingChamber_FrontPvalues[i * 3] = (float) (MasterLandingChamber_FrontVertices[i]).x();
		MasterLandingChamber_FrontPvalues[i * 3 + 1] = (float) (MasterLandingChamber_FrontVertices[i]).y();
		MasterLandingChamber_FrontPvalues[i * 3 + 2] = (float) (MasterLandingChamber_FrontVertices[i]).z();
		MasterLandingChamber_FrontTvalues[i * 2] = (float) (MasterLandingChamber_FrontTexCoords[i]).x();
		MasterLandingChamber_FrontTvalues[i * 2 + 1] = (float) (MasterLandingChamber_FrontTexCoords[i]).y();
		MasterLandingChamber_FrontNvalues[i * 3] = (float) (MasterLandingChamber_FrontNormals[i]).x();
		MasterLandingChamber_FrontNvalues[i * 3 + 1] = (float) (MasterLandingChamber_FrontNormals[i]).y();
		MasterLandingChamber_FrontNvalues[i * 3 + 2] = (float) (MasterLandingChamber_FrontNormals[i]).z();
	}
	
	
	//Master Chamber Door 
	
	MasterChamberDoorNumObjVertices = MasterChamberDoorModel.getNumVertices();
	Vector3f[] MasterChamberDoorVertices = MasterChamberDoorModel.getVertices();
	Vector2f[] MasterChamberDoorTexCoords = MasterChamberDoorModel.getTexCoords();
	Vector3f[] MasterChamberDoorNormals = MasterChamberDoorModel.getNormals();

	float[] MasterChamberDoorPvalues = new float[MasterChamberDoorNumObjVertices * 3];
	float[] MasterChamberDoorTvalues = new float[MasterChamberDoorNumObjVertices * 2];
	float[] MasterChamberDoorNvalues = new float[MasterChamberDoorNumObjVertices * 3];

	for (int i = 0; i < MasterChamberDoorNumObjVertices; i++) {
		MasterChamberDoorPvalues[i * 3] = (float) (MasterChamberDoorVertices[i]).x();
		MasterChamberDoorPvalues[i * 3 + 1] = (float) (MasterChamberDoorVertices[i]).y();
		MasterChamberDoorPvalues[i * 3 + 2] = (float) (MasterChamberDoorVertices[i]).z();
		MasterChamberDoorTvalues[i * 2] = (float) (MasterChamberDoorTexCoords[i]).x();
		MasterChamberDoorTvalues[i * 2 + 1] = (float) (MasterChamberDoorTexCoords[i]).y();
		MasterChamberDoorNvalues[i * 3] = (float) (MasterChamberDoorNormals[i]).x();
		MasterChamberDoorNvalues[i * 3 + 1] = (float) (MasterChamberDoorNormals[i]).y();
		MasterChamberDoorNvalues[i * 3 + 2] = (float) (MasterChamberDoorNormals[i]).z();
	}
	
	
	//Master Chamber Back Door
	
	MasterChamberDoorBackNumObjVertices = MasterChamberDoorBackModel.getNumVertices();
	Vector3f[] MasterChamberDoorBackVertices = MasterChamberDoorBackModel.getVertices();
	Vector2f[] MasterChamberDoorBackTexCoords = MasterChamberDoorBackModel.getTexCoords();
	Vector3f[] MasterChamberDoorBackNormals = MasterChamberDoorBackModel.getNormals();

	float[] MasterChamberDoorBackPvalues = new float[MasterChamberDoorBackNumObjVertices * 3];
	float[] MasterChamberDoorBackTvalues = new float[MasterChamberDoorBackNumObjVertices * 2];
	float[] MasterChamberDoorBackNvalues = new float[MasterChamberDoorBackNumObjVertices * 3];

	for (int i = 0; i < MasterChamberDoorBackNumObjVertices; i++) {
		MasterChamberDoorBackPvalues[i * 3] = (float) (MasterChamberDoorBackVertices[i]).x();
		MasterChamberDoorBackPvalues[i * 3 + 1] = (float) (MasterChamberDoorBackVertices[i]).y();
		MasterChamberDoorBackPvalues[i * 3 + 2] = (float) (MasterChamberDoorBackVertices[i]).z();
		MasterChamberDoorBackTvalues[i * 2] = (float) (MasterChamberDoorBackTexCoords[i]).x();
		MasterChamberDoorBackTvalues[i * 2 + 1] = (float) (MasterChamberDoorBackTexCoords[i]).y();
		MasterChamberDoorBackNvalues[i * 3] = (float) (MasterChamberDoorBackNormals[i]).x();
		MasterChamberDoorBackNvalues[i * 3 + 1] = (float) (MasterChamberDoorBackNormals[i]).y();
		MasterChamberDoorBackNvalues[i * 3 + 2] = (float) (MasterChamberDoorBackNormals[i]).z();
	}
	
	
	// Master Chamber Below
	
	MasterLandingChamberBELOWNumObjVertices = MasterLandingChamberBELOWModel.getNumVertices();
	Vector3f[] MasterLandingChamberBELOWVertices = MasterLandingChamberBELOWModel.getVertices();
	Vector2f[] MasterLandingChamberBELOWTexCoords = MasterLandingChamberBELOWModel.getTexCoords();
	Vector3f[] MasterLandingChamberBELOWNormals = MasterLandingChamberBELOWModel.getNormals();

	float[] MasterLandingChamberBELOWPvalues = new float[MasterLandingChamberBELOWNumObjVertices * 3];
	float[] MasterLandingChamberBELOWTvalues = new float[MasterLandingChamberBELOWNumObjVertices * 2];
	float[] MasterLandingChamberBELOWNvalues = new float[MasterLandingChamberBELOWNumObjVertices * 3];

	for (int i = 0; i < MasterLandingChamberBELOWNumObjVertices; i++) {
		MasterLandingChamberBELOWPvalues[i * 3] = (float) (MasterLandingChamberBELOWVertices[i]).x();
		MasterLandingChamberBELOWPvalues[i * 3 + 1] = (float) (MasterLandingChamberBELOWVertices[i]).y();
		MasterLandingChamberBELOWPvalues[i * 3 + 2] = (float) (MasterLandingChamberBELOWVertices[i]).z();
		MasterLandingChamberBELOWTvalues[i * 2] = (float) (MasterLandingChamberBELOWTexCoords[i]).x();
		MasterLandingChamberBELOWTvalues[i * 2 + 1] = (float) (MasterLandingChamberBELOWTexCoords[i]).y();
		MasterLandingChamberBELOWNvalues[i * 3] = (float) (MasterLandingChamberBELOWNormals[i]).x();
		MasterLandingChamberBELOWNvalues[i * 3 + 1] = (float) (MasterLandingChamberBELOWNormals[i]).y();
		MasterLandingChamberBELOWNvalues[i * 3 + 2] = (float) (MasterLandingChamberBELOWNormals[i]).z();
	}

	
	//Flying Star
	
	numflyingStarVertices = flyingStar.getNumVertices();
	Vector3f[] flyingStarvertices = flyingStar.getVertices();
	Vector2f[] flyingStartexCoords = flyingStar.getTexCoords();
	Vector3f[] flyingStarnormals = flyingStar.getNormals();
	
	float[] flyingStarpvalues = new float[numflyingStarVertices*3];
	float[] flyingStartvalues = new float[numflyingStarVertices*2];
	float[] flyingStarnvalues = new float[numflyingStarVertices*3];
	
	for (int i=0; i<numflyingStarVertices; i++)
	{	flyingStarpvalues[i*3]   = (float) (flyingStarvertices[i]).x();
		flyingStarpvalues[i*3+1] = (float) (flyingStarvertices[i]).y();
		flyingStarpvalues[i*3+2] = (float) (flyingStarvertices[i]).z();
		flyingStartvalues[i*2]   = (float) (flyingStartexCoords[i]).x();
		flyingStartvalues[i*2+1] = (float) (flyingStartexCoords[i]).y();
		flyingStarnvalues[i*3]   = (float) (flyingStarnormals[i]).x();
		flyingStarnvalues[i*3+1] = (float) (flyingStarnormals[i]).y();
		flyingStarnvalues[i*3+2] = (float) (flyingStarnormals[i]).z();
	}
	
	
	// HoloTable_bottom
	
	HoloTable_bottomNumObjVertices = HoloTable_bottomModel.getNumVertices();
	Vector3f[] HoloTable_bottomVertices = HoloTable_bottomModel.getVertices();
	Vector2f[] HoloTable_bottomTexCoords = HoloTable_bottomModel.getTexCoords();
	Vector3f[] HoloTable_bottomNormals = HoloTable_bottomModel.getNormals();

	float[] HoloTable_bottomPvalues = new float[HoloTable_bottomNumObjVertices * 3];
	float[] HoloTable_bottomTvalues = new float[HoloTable_bottomNumObjVertices * 2];
	float[] HoloTable_bottomNvalues = new float[HoloTable_bottomNumObjVertices * 3];

	for (int i = 0; i < HoloTable_bottomNumObjVertices; i++) {
		HoloTable_bottomPvalues[i * 3] = (float) (HoloTable_bottomVertices[i]).x();
		HoloTable_bottomPvalues[i * 3 + 1] = (float) (HoloTable_bottomVertices[i]).y();
		HoloTable_bottomPvalues[i * 3 + 2] = (float) (HoloTable_bottomVertices[i]).z();
		HoloTable_bottomTvalues[i * 2] = (float) (HoloTable_bottomTexCoords[i]).x();
		HoloTable_bottomTvalues[i * 2 + 1] = (float) (HoloTable_bottomTexCoords[i]).y();
		HoloTable_bottomNvalues[i * 3] = (float) (HoloTable_bottomNormals[i]).x();
		HoloTable_bottomNvalues[i * 3 + 1] = (float) (HoloTable_bottomNormals[i]).y();
		HoloTable_bottomNvalues[i * 3 + 2] = (float) (HoloTable_bottomNormals[i]).z();
	}


	// MirrorGate

	MirrorGateNumObjVertices = MirrorGateModel.getNumVertices();
	Vector3f[] MirrorGateVertices = MirrorGateModel.getVertices();
	Vector2f[] MirrorGateTexCoords = MirrorGateModel.getTexCoords();
	Vector3f[] MirrorGateNormals = MirrorGateModel.getNormals();

	float[] MirrorGatePvalues = new float[MirrorGateNumObjVertices * 3];
	float[] MirrorGateTvalues = new float[MirrorGateNumObjVertices * 2];
	float[] MirrorGateNvalues = new float[MirrorGateNumObjVertices * 3];

	for (int i = 0; i < MirrorGateNumObjVertices; i++) {
	MirrorGatePvalues[i * 3] = (float) (MirrorGateVertices[i]).x();
	MirrorGatePvalues[i * 3 + 1] = (float) (MirrorGateVertices[i]).y();
	MirrorGatePvalues[i * 3 + 2] = (float) (MirrorGateVertices[i]).z();
	MirrorGateTvalues[i * 2] = (float) (MirrorGateTexCoords[i]).x();
	MirrorGateTvalues[i * 2 + 1] = (float) (MirrorGateTexCoords[i]).y();
	MirrorGateNvalues[i * 3] = (float) (MirrorGateNormals[i]).x();
	MirrorGateNvalues[i * 3 + 1] = (float) (MirrorGateNormals[i]).y();
	MirrorGateNvalues[i * 3 + 2] = (float) (MirrorGateNormals[i]).z();
	}


	// Mirror Gate Frame

	MirrorGate_FrameNumObjVertices = MirrorGate_FrameModel.getNumVertices();
	Vector3f[] MirrorGate_FrameVertices = MirrorGate_FrameModel.getVertices();
	Vector2f[] MirrorGate_FrameTexCoords = MirrorGate_FrameModel.getTexCoords();
	Vector3f[] MirrorGate_FrameNormals = MirrorGate_FrameModel.getNormals();

	float[] MirrorGate_FramePvalues = new float[MirrorGate_FrameNumObjVertices * 3];
	float[] MirrorGate_FrameTvalues = new float[MirrorGate_FrameNumObjVertices * 2];
	float[] MirrorGate_FrameNvalues = new float[MirrorGate_FrameNumObjVertices * 3];

	for (int i = 0; i < MirrorGate_FrameNumObjVertices; i++) {
		MirrorGate_FramePvalues[i * 3] = (float) (MirrorGate_FrameVertices[i]).x();
		MirrorGate_FramePvalues[i * 3 + 1] = (float) (MirrorGate_FrameVertices[i]).y();
		MirrorGate_FramePvalues[i * 3 + 2] = (float) (MirrorGate_FrameVertices[i]).z();
		MirrorGate_FrameTvalues[i * 2] = (float) (MirrorGate_FrameTexCoords[i]).x();
		MirrorGate_FrameTvalues[i * 2 + 1] = (float) (MirrorGate_FrameTexCoords[i]).y();
		MirrorGate_FrameNvalues[i * 3] = (float) (MirrorGate_FrameNormals[i]).x();
		MirrorGate_FrameNvalues[i * 3 + 1] = (float) (MirrorGate_FrameNormals[i]).y();
		MirrorGate_FrameNvalues[i * 3 + 2] = (float) (MirrorGate_FrameNormals[i]).z();
	}


	//Furry Knight

	FurryKnightNumObjVertices = FurryKnightModel.getNumVertices();
	Vector3f[] FurryKnightVertices = FurryKnightModel.getVertices();
	Vector2f[] FurryKnightTexCoords = FurryKnightModel.getTexCoords();
	Vector3f[] FurryKnightNormals = FurryKnightModel.getNormals();

	float[] FurryKnightPvalues = new float[FurryKnightNumObjVertices * 3];
	float[] FurryKnightTvalues = new float[FurryKnightNumObjVertices * 2];
	float[] FurryKnightNvalues = new float[FurryKnightNumObjVertices * 3];

	for (int i = 0; i < FurryKnightNumObjVertices; i++) {
	FurryKnightPvalues[i * 3] = (float) (FurryKnightVertices[i]).x();
	FurryKnightPvalues[i * 3 + 1] = (float) (FurryKnightVertices[i]).y();
	FurryKnightPvalues[i * 3 + 2] = (float) (FurryKnightVertices[i]).z();
	FurryKnightTvalues[i * 2] = (float) (FurryKnightTexCoords[i]).x();
	FurryKnightTvalues[i * 2 + 1] = (float) (FurryKnightTexCoords[i]).y();
	FurryKnightNvalues[i * 3] = (float) (FurryKnightNormals[i]).x();
	FurryKnightNvalues[i * 3 + 1] = (float) (FurryKnightNormals[i]).y();
	FurryKnightNvalues[i * 3 + 2] = (float) (FurryKnightNormals[i]).z();
	}



		// buffers definition
		gl.glGenVertexArrays(vao.length, vao, 0);
		gl.glBindVertexArray(vao[0]);

		gl.glGenBuffers(vbo.length, vbo, 0);

		
		//Skybox
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[15]);
		FloatBuffer cvertBuf = Buffers.newDirectFloatBuffer(cubeVertexPositions);
		gl.glBufferData(GL_ARRAY_BUFFER, cvertBuf.limit()*4, cvertBuf, GL_STATIC_DRAW);
		

		// X Line
		
		gl.glBindBuffer(gl.GL_ARRAY_BUFFER, vbo[5]);
		FloatBuffer xvertBuf = Buffers.newDirectFloatBuffer(xPositions);
		gl.glBufferData(gl.GL_ARRAY_BUFFER, xvertBuf.limit()*4, xvertBuf, gl.GL_STATIC_DRAW);

		gl.glBindBuffer(gl.GL_ARRAY_BUFFER, vbo[6]);
		FloatBuffer xtexBuf = Buffers.newDirectFloatBuffer(xTextures);
		gl.glBufferData(gl.GL_ARRAY_BUFFER, xtexBuf.limit()*4, xtexBuf, gl.GL_STATIC_DRAW);
		
		
		// Y Line
		
		gl.glBindBuffer(gl.GL_ARRAY_BUFFER, vbo[7]);
		FloatBuffer yvertBuf = Buffers.newDirectFloatBuffer(yPositions);
		gl.glBufferData(gl.GL_ARRAY_BUFFER, yvertBuf.limit()*4, yvertBuf, gl.GL_STATIC_DRAW);

		gl.glBindBuffer(gl.GL_ARRAY_BUFFER, vbo[8]);
		FloatBuffer ytexBuf = Buffers.newDirectFloatBuffer(yTextures);
		gl.glBufferData(gl.GL_ARRAY_BUFFER, ytexBuf.limit()*4, ytexBuf, gl.GL_STATIC_DRAW);
		
		
		// Z Line
		
		gl.glBindBuffer(gl.GL_ARRAY_BUFFER, vbo[9]);
		FloatBuffer zvertBuf = Buffers.newDirectFloatBuffer(zPositions);
		gl.glBufferData(gl.GL_ARRAY_BUFFER, zvertBuf.limit()*4, zvertBuf, gl.GL_STATIC_DRAW);

		gl.glBindBuffer(gl.GL_ARRAY_BUFFER, vbo[10]);
		FloatBuffer ztexBuf = Buffers.newDirectFloatBuffer(zTextures);
		gl.glBufferData(gl.GL_ARRAY_BUFFER, ztexBuf.limit()*4, ztexBuf, gl.GL_STATIC_DRAW);
		
		
		//Droid Alone
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[42]);
		FloatBuffer DroidAloneVertBuf = Buffers.newDirectFloatBuffer(DroidAlonePvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, DroidAloneVertBuf.limit() * 4, DroidAloneVertBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[44]);
		FloatBuffer DroidAloneTexBuf = Buffers.newDirectFloatBuffer(DroidAloneTvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, DroidAloneTexBuf.limit() * 4, DroidAloneTexBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[43]);
		FloatBuffer DroidAloneNorBuf = Buffers.newDirectFloatBuffer(DroidAloneNvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, DroidAloneNorBuf.limit() * 4, DroidAloneNorBuf, GL_STATIC_DRAW);
		
		
		//Cloud Layer
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[39]);
		FloatBuffer CloudyLayerVertBuf = Buffers.newDirectFloatBuffer(CloudyLayerPvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, CloudyLayerVertBuf.limit()*4, CloudyLayerVertBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[40]);
		FloatBuffer CloudyLayerTexBuf = Buffers.newDirectFloatBuffer(CloudyLayerTvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, CloudyLayerTexBuf.limit()*4, CloudyLayerTexBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[41]);
		FloatBuffer CloudyLayerNorBuf = Buffers.newDirectFloatBuffer(CloudyLayerNvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, CloudyLayerNorBuf.limit()*4,CloudyLayerNorBuf, GL_STATIC_DRAW);
		
		
		//Sphere
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[11]);
		FloatBuffer spherevertBuf = Buffers.newDirectFloatBuffer(spherepvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, spherevertBuf.limit()*4, spherevertBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[12]);
		FloatBuffer spheretexBuf = Buffers.newDirectFloatBuffer(spheretvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, spheretexBuf.limit()*4, spheretexBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[13]);
		FloatBuffer spherenorBuf = Buffers.newDirectFloatBuffer(spherenvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, spherenorBuf.limit()*4,spherenorBuf, GL_STATIC_DRAW);
		
		gl.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vbo[14]);
		IntBuffer SphereidxBuf = Buffers.newDirectIntBuffer(indices2);
		gl.glBufferData(GL_ELEMENT_ARRAY_BUFFER, SphereidxBuf.limit()*4, SphereidxBuf, GL_STATIC_DRAW);
		
		
		//IG Stuff
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[18]);
		FloatBuffer igVertBuf = Buffers.newDirectFloatBuffer(igPvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, igVertBuf.limit()*4, igVertBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[19]);
		FloatBuffer igTexBuf = Buffers.newDirectFloatBuffer(igTvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, igTexBuf.limit()*4, igTexBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[20]);
		FloatBuffer igNorBuf = Buffers.newDirectFloatBuffer(igNvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, igNorBuf.limit()*4,igNorBuf, GL_STATIC_DRAW);
		
		
		//Back Hanger Bay
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[21]);
		FloatBuffer backHangerVertBuf = Buffers.newDirectFloatBuffer(backHangerPvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, backHangerVertBuf.limit()*4, backHangerVertBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[22]);
		FloatBuffer backHangerTexBuf = Buffers.newDirectFloatBuffer(backHangerTvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, backHangerTexBuf.limit()*4, backHangerTexBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[23]);
		FloatBuffer backHangerNorBuf = Buffers.newDirectFloatBuffer(backHangerNvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, backHangerNorBuf.limit()*4,backHangerNorBuf, GL_STATIC_DRAW);
		
		
		//Balcony with Droid
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[24]);
		FloatBuffer BalconyDroidVertBuf = Buffers.newDirectFloatBuffer(BalconyDroidPvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, BalconyDroidVertBuf.limit()*4, BalconyDroidVertBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[25]);
		FloatBuffer BalconyDroidTexBuf = Buffers.newDirectFloatBuffer(BalconyDroidTvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, BalconyDroidTexBuf.limit()*4, BalconyDroidTexBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[26]);
		FloatBuffer BalconyDroidNorBuf = Buffers.newDirectFloatBuffer(BalconyDroidNvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, BalconyDroidNorBuf.limit()*4,BalconyDroidNorBuf, GL_STATIC_DRAW);
		
		
		// Boba Fett
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[27]);
		FloatBuffer bobaFettVertBuf = Buffers.newDirectFloatBuffer(bobaFettPvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, bobaFettVertBuf.limit()*4, bobaFettVertBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[29]);
		FloatBuffer bobaFettNorBuf = Buffers.newDirectFloatBuffer(bobaFettNvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, bobaFettNorBuf.limit()*4,bobaFettNorBuf, GL_STATIC_DRAW);
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[28]);
		FloatBuffer bobaFettTexBuf = Buffers.newDirectFloatBuffer(bobaFettTvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, bobaFettTexBuf.limit()*4, bobaFettTexBuf, GL_STATIC_DRAW);
		
		
		//Bird building and other landing pad
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[30]);
		FloatBuffer BirdBuildingVertBuf = Buffers.newDirectFloatBuffer(BirdBuildingPvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, BirdBuildingVertBuf.limit()*4, BirdBuildingVertBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[31]);
		FloatBuffer BirdBuildingTexBuf = Buffers.newDirectFloatBuffer(BirdBuildingTvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, BirdBuildingTexBuf.limit()*4, BirdBuildingTexBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[32]);
		FloatBuffer BirdBuildingNorBuf = Buffers.newDirectFloatBuffer(BirdBuildingNvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, BirdBuildingNorBuf.limit()*4,BirdBuildingNorBuf, GL_STATIC_DRAW);
		
		
		//The Wall
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[33]);
		FloatBuffer theWallVertBuf = Buffers.newDirectFloatBuffer(theWallPvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, theWallVertBuf.limit()*4, theWallVertBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[34]);
		FloatBuffer theWallTexBuf = Buffers.newDirectFloatBuffer(theWallTvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, theWallTexBuf.limit()*4, theWallTexBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[35]);
		FloatBuffer theWallNorBuf = Buffers.newDirectFloatBuffer(theWallNvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, theWallNorBuf.limit()*4,theWallNorBuf, GL_STATIC_DRAW);
		
		
		//Tower Building
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[36]);
		FloatBuffer BillBoardBuildingVertBuf = Buffers.newDirectFloatBuffer(BillBoardBuildingPvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, BillBoardBuildingVertBuf.limit()*4, BillBoardBuildingVertBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[37]);
		FloatBuffer BillBoardBuildingTexBuf = Buffers.newDirectFloatBuffer(BillBoardBuildingTvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, BillBoardBuildingTexBuf.limit()*4, BillBoardBuildingTexBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[38]);
		FloatBuffer BillBoardBuildingNorBuf = Buffers.newDirectFloatBuffer(BillBoardBuildingNvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, BillBoardBuildingNorBuf.limit()*4,BillBoardBuildingNorBuf, GL_STATIC_DRAW);
		
		
		//Blue Ghost
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[45]);
		FloatBuffer blueGhostVertBuf = Buffers.newDirectFloatBuffer(blueGhostPvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, blueGhostVertBuf.limit() * 4, blueGhostVertBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[46]);
		FloatBuffer blueGhostTexBuf = Buffers.newDirectFloatBuffer(blueGhostTvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, blueGhostTexBuf.limit() * 4, blueGhostTexBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[47]);
		FloatBuffer blueGhostNorBuf = Buffers.newDirectFloatBuffer(blueGhostNvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, blueGhostNorBuf.limit() * 4, blueGhostNorBuf, GL_STATIC_DRAW);
		
		
		//Tron Chess Set
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[48]);
		FloatBuffer TronChessVertBuf = Buffers.newDirectFloatBuffer(TronChessPvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, TronChessVertBuf.limit() * 4, TronChessVertBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[49]);
		FloatBuffer TronChessTexBuf = Buffers.newDirectFloatBuffer(TronChessTvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, TronChessTexBuf.limit() * 4, TronChessTexBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[50]);
		FloatBuffer TronChessNorBuf = Buffers.newDirectFloatBuffer(TronChessNvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, TronChessNorBuf.limit() * 4, TronChessNorBuf, GL_STATIC_DRAW);
		
		
		//Holotable
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[51]);
		FloatBuffer HoloTableVertBuf = Buffers.newDirectFloatBuffer(HoloTablePvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, HoloTableVertBuf.limit() * 4, HoloTableVertBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[52]);
		FloatBuffer HoloTableTexBuf = Buffers.newDirectFloatBuffer(HoloTableTvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, HoloTableTexBuf.limit() * 4, HoloTableTexBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[53]);
		FloatBuffer HoloTableNorBuf = Buffers.newDirectFloatBuffer(HoloTableNvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, HoloTableNorBuf.limit() * 4, HoloTableNorBuf, GL_STATIC_DRAW);
		
		
		//Red Tron
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[54]);
		FloatBuffer RedTronVertBuf = Buffers.newDirectFloatBuffer(RedTronPvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, RedTronVertBuf.limit() * 4, RedTronVertBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[55]);
		FloatBuffer RedTronTexBuf = Buffers.newDirectFloatBuffer(RedTronTvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, RedTronTexBuf.limit() * 4, RedTronTexBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[56]);
		FloatBuffer RedTronNorBuf = Buffers.newDirectFloatBuffer(RedTronNvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, RedTronNorBuf.limit() * 4, RedTronNorBuf, GL_STATIC_DRAW);
		
		
		//Blue Tron
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[57]);
		FloatBuffer BlueTronVertBuf = Buffers.newDirectFloatBuffer(BlueTronPvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, BlueTronVertBuf.limit() * 4, BlueTronVertBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[58]);
		FloatBuffer BlueTronTexBuf = Buffers.newDirectFloatBuffer(BlueTronTvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, BlueTronTexBuf.limit() * 4, BlueTronTexBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[59]);
		FloatBuffer BlueTronNorBuf = Buffers.newDirectFloatBuffer(BlueTronNvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, BlueTronNorBuf.limit() * 4, BlueTronNorBuf, GL_STATIC_DRAW);
		
		
		//Wall Armor
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[60]);
		FloatBuffer WallArmorVertBuf = Buffers.newDirectFloatBuffer(WallArmorPvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, WallArmorVertBuf.limit() * 4, WallArmorVertBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[61]);
		FloatBuffer WallArmorTexBuf = Buffers.newDirectFloatBuffer(WallArmorTvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, WallArmorTexBuf.limit() * 4, WallArmorTexBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[62]);
		FloatBuffer WallArmorNorBuf = Buffers.newDirectFloatBuffer(WallArmorNvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, WallArmorNorBuf.limit() * 4, WallArmorNorBuf, GL_STATIC_DRAW);
		
		
		//Balcony Extension
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[63]);
		FloatBuffer BalconyExtensionVertBuf = Buffers.newDirectFloatBuffer(BalconyExtensionPvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, BalconyExtensionVertBuf.limit() * 4, BalconyExtensionVertBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[64]);
		FloatBuffer BalconyExtensionTexBuf = Buffers.newDirectFloatBuffer(BalconyExtensionTvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, BalconyExtensionTexBuf.limit() * 4, BalconyExtensionTexBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[65]);
		FloatBuffer BalconyExtensionNorBuf = Buffers.newDirectFloatBuffer(BalconyExtensionNvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, BalconyExtensionNorBuf.limit() * 4, BalconyExtensionNorBuf, GL_STATIC_DRAW);
		
		
		//Balcony Bottom Extension
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[71]);
		FloatBuffer BalconyExtensionV3BottomVertBuf = Buffers.newDirectFloatBuffer(BalconyExtensionV3BottomPvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, BalconyExtensionV3BottomVertBuf.limit() * 4, BalconyExtensionV3BottomVertBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[72]);
		FloatBuffer BalconyExtensionV3BottomTexBuf = Buffers.newDirectFloatBuffer(BalconyExtensionV3BottomTvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, BalconyExtensionV3BottomTexBuf.limit() * 4, BalconyExtensionV3BottomTexBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[73]);
		FloatBuffer BalconyExtensionV3BottomNorBuf = Buffers.newDirectFloatBuffer(BalconyExtensionV3BottomNvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, BalconyExtensionV3BottomNorBuf.limit() * 4, BalconyExtensionV3BottomNorBuf, GL_STATIC_DRAW);
		
		
		//Bird Building Extension
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[65]);
		FloatBuffer BirdBuildingExtensionVertBuf = Buffers.newDirectFloatBuffer(BirdBuildingExtensionPvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, BirdBuildingExtensionVertBuf.limit() * 4, BirdBuildingExtensionVertBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[66]);
		FloatBuffer BirdBuildingExtensionTexBuf = Buffers.newDirectFloatBuffer(BirdBuildingExtensionTvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, BirdBuildingExtensionTexBuf.limit() * 4, BirdBuildingExtensionTexBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[67]);
		FloatBuffer BirdBuildingExtensionNorBuf = Buffers.newDirectFloatBuffer(BirdBuildingExtensionNvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, BirdBuildingExtensionNorBuf.limit() * 4, BirdBuildingExtensionNorBuf, GL_STATIC_DRAW);
		
		
		//  ground vertices
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[68]);
		FloatBuffer groundvertBuf = Buffers.newDirectFloatBuffer(pvaluesGround);
		gl.glBufferData(GL_ARRAY_BUFFER, groundvertBuf.limit()*4, groundvertBuf, GL_STATIC_DRAW);

		
		//  ground texture coordinates
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[69]);
		FloatBuffer groundTexBuf = Buffers.newDirectFloatBuffer(tvaluesGround);
		gl.glBufferData(GL_ARRAY_BUFFER, groundTexBuf.limit()*4, groundTexBuf, GL_STATIC_DRAW);

		
		// ground normals
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[70]);
		FloatBuffer groundNorBuf = Buffers.newDirectFloatBuffer(nvaluesGround);
		gl.glBufferData(GL_ARRAY_BUFFER, groundNorBuf.limit()*4, groundNorBuf, GL_STATIC_DRAW);
		
		
		//The Wall Extension
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[74]);
		FloatBuffer TheWall_BottomVertBuf = Buffers.newDirectFloatBuffer(TheWall_BottomPvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, TheWall_BottomVertBuf.limit() * 4, TheWall_BottomVertBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[75]);
		FloatBuffer TheWall_BottomTexBuf = Buffers.newDirectFloatBuffer(TheWall_BottomTvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, TheWall_BottomTexBuf.limit() * 4, TheWall_BottomTexBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[76]);
		FloatBuffer TheWall_BottomNorBuf = Buffers.newDirectFloatBuffer(TheWall_BottomNvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, TheWall_BottomNorBuf.limit() * 4, TheWall_BottomNorBuf, GL_STATIC_DRAW);
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[77]);
		FloatBuffer BillBoardBuilding_BottomVertBuf = Buffers.newDirectFloatBuffer(BillBoardBuilding_BottomPvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, BillBoardBuilding_BottomVertBuf.limit() * 4, BillBoardBuilding_BottomVertBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[78]);
		FloatBuffer BillBoardBuilding_BottomTexBuf = Buffers.newDirectFloatBuffer(BillBoardBuilding_BottomTvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, BillBoardBuilding_BottomTexBuf.limit() * 4, BillBoardBuilding_BottomTexBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[79]);
		FloatBuffer BillBoardBuilding_BottomNorBuf = Buffers.newDirectFloatBuffer(BillBoardBuilding_BottomNvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, BillBoardBuilding_BottomNorBuf.limit() * 4, BillBoardBuilding_BottomNorBuf, GL_STATIC_DRAW);
		
		
		//Bird Building Below
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[80]);
		FloatBuffer BirdBuildingExtensionV1_BottomVertBuf = Buffers.newDirectFloatBuffer(BirdBuildingExtensionV1_BottomPvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, BirdBuildingExtensionV1_BottomVertBuf.limit() * 4, BirdBuildingExtensionV1_BottomVertBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[81]);
		FloatBuffer BirdBuildingExtensionV1_BottomTexBuf = Buffers.newDirectFloatBuffer(BirdBuildingExtensionV1_BottomTvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, BirdBuildingExtensionV1_BottomTexBuf.limit() * 4, BirdBuildingExtensionV1_BottomTexBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[82]);
		FloatBuffer BirdBuildingExtensionV1_BottomNorBuf = Buffers.newDirectFloatBuffer(BirdBuildingExtensionV1_BottomNvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, BirdBuildingExtensionV1_BottomNorBuf.limit() * 4, BirdBuildingExtensionV1_BottomNorBuf, GL_STATIC_DRAW);
		
		
		//Back Hanger extension
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[83]);
		FloatBuffer BackHangerExtensionVertBuf = Buffers.newDirectFloatBuffer(BackHangerExtensionPvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, BackHangerExtensionVertBuf.limit() * 4, BackHangerExtensionVertBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[84]);
		FloatBuffer BackHangerExtensionTexBuf = Buffers.newDirectFloatBuffer(BackHangerExtensionTvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, BackHangerExtensionTexBuf.limit() * 4, BackHangerExtensionTexBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[85]);
		FloatBuffer BackHangerExtensionNorBuf = Buffers.newDirectFloatBuffer(BackHangerExtensionNvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, BackHangerExtensionNorBuf.limit() * 4, BackHangerExtensionNorBuf, GL_STATIC_DRAW);
		
		
		//Background Fog
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[86]);
		FloatBuffer backgroundFOGVertBuf = Buffers.newDirectFloatBuffer(backgroundFOGPvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, backgroundFOGVertBuf.limit() * 4, backgroundFOGVertBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[87]);
		FloatBuffer backgroundFOGTexBuf = Buffers.newDirectFloatBuffer(backgroundFOGTvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, backgroundFOGTexBuf.limit() * 4, backgroundFOGTexBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[88]);
		FloatBuffer backgroundFOGNorBuf = Buffers.newDirectFloatBuffer(backgroundFOGNvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, backgroundFOGNorBuf.limit() * 4, backgroundFOGNorBuf, GL_STATIC_DRAW);
	
	
		//Master Chamber
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[89]);
		FloatBuffer masterChambervertBuf = Buffers.newDirectFloatBuffer(masterChamberpvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, masterChambervertBuf.limit()*4, masterChambervertBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[90]);
		FloatBuffer masterChambertexBuf = Buffers.newDirectFloatBuffer(masterChambertvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, masterChambertexBuf.limit()*4, masterChambertexBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[91]);
		FloatBuffer masterChambernorBuf = Buffers.newDirectFloatBuffer(masterChambernvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, masterChambernorBuf.limit()*4,masterChambernorBuf, GL_STATIC_DRAW);
		
		
		//Master Chamber Back Door
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[113]);
		FloatBuffer MasterChamberDoorBackVertBuf = Buffers.newDirectFloatBuffer(MasterChamberDoorBackPvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, MasterChamberDoorBackVertBuf.limit() * 4, MasterChamberDoorBackVertBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[114]);
		FloatBuffer MasterChamberDoorBackTexBuf = Buffers.newDirectFloatBuffer(MasterChamberDoorBackTvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, MasterChamberDoorBackTexBuf.limit() * 4, MasterChamberDoorBackTexBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[115]);
		FloatBuffer MasterChamberDoorBackNorBuf = Buffers.newDirectFloatBuffer(MasterChamberDoorBackNvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, MasterChamberDoorBackNorBuf.limit() * 4, MasterChamberDoorBackNorBuf, GL_STATIC_DRAW);
		
		
		//Flying Star
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[92]);
		FloatBuffer flyingStarvertBuf = Buffers.newDirectFloatBuffer(flyingStarpvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, flyingStarvertBuf.limit()*4, flyingStarvertBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[93]);
		FloatBuffer flyingStartexBuf = Buffers.newDirectFloatBuffer(flyingStartvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, flyingStartexBuf.limit()*4, flyingStartexBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[94]);
		FloatBuffer flyingStarnorBuf = Buffers.newDirectFloatBuffer(flyingStarnvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, flyingStarnorBuf.limit()*4,flyingStarnorBuf, GL_STATIC_DRAW);
		
		
		//Master Chamber
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[107]);
		FloatBuffer MasterLandingChamber_FrontVertBuf = Buffers.newDirectFloatBuffer(MasterLandingChamber_FrontPvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, MasterLandingChamber_FrontVertBuf.limit() * 4, MasterLandingChamber_FrontVertBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[108]);
		FloatBuffer MasterLandingChamber_FrontTexBuf = Buffers.newDirectFloatBuffer(MasterLandingChamber_FrontTvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, MasterLandingChamber_FrontTexBuf.limit() * 4, MasterLandingChamber_FrontTexBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[109]);
		FloatBuffer MasterLandingChamber_FrontNorBuf = Buffers.newDirectFloatBuffer(MasterLandingChamber_FrontNvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, MasterLandingChamber_FrontNorBuf.limit() * 4, MasterLandingChamber_FrontNorBuf, GL_STATIC_DRAW);
	
	
		//Master Chamber Door
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[110]);
		FloatBuffer MasterChamberDoorVertBuf = Buffers.newDirectFloatBuffer(MasterChamberDoorPvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, MasterChamberDoorVertBuf.limit() * 4, MasterChamberDoorVertBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[111]);
		FloatBuffer MasterChamberDoorTexBuf = Buffers.newDirectFloatBuffer(MasterChamberDoorTvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, MasterChamberDoorTexBuf.limit() * 4, MasterChamberDoorTexBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[112]);
		FloatBuffer MasterChamberDoorNorBuf = Buffers.newDirectFloatBuffer(MasterChamberDoorNvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, MasterChamberDoorNorBuf.limit() * 4, MasterChamberDoorNorBuf, GL_STATIC_DRAW);
	
	
		//Master Chamber Below
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[115]);
		FloatBuffer MasterLandingChamberBELOWVertBuf = Buffers.newDirectFloatBuffer(MasterLandingChamberBELOWPvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, MasterLandingChamberBELOWVertBuf.limit() * 4, MasterLandingChamberBELOWVertBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[116]);
		FloatBuffer MasterLandingChamberBELOWTexBuf = Buffers.newDirectFloatBuffer(MasterLandingChamberBELOWTvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, MasterLandingChamberBELOWTexBuf.limit() * 4, MasterLandingChamberBELOWTexBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[117]);
		FloatBuffer MasterLandingChamberBELOWNorBuf = Buffers.newDirectFloatBuffer(MasterLandingChamberBELOWNvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, MasterLandingChamberBELOWNorBuf.limit() * 4, MasterLandingChamberBELOWNorBuf, GL_STATIC_DRAW);
		
		
		//HoloTable_bottom
		
	gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[95]);
	FloatBuffer HoloTable_bottomVertBuf = Buffers.newDirectFloatBuffer(HoloTable_bottomPvalues);
	gl.glBufferData(GL_ARRAY_BUFFER, HoloTable_bottomVertBuf.limit() * 4, HoloTable_bottomVertBuf, GL_STATIC_DRAW);

	gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[96]);
	FloatBuffer HoloTable_bottomTexBuf = Buffers.newDirectFloatBuffer(HoloTable_bottomTvalues);
	gl.glBufferData(GL_ARRAY_BUFFER, HoloTable_bottomTexBuf.limit() * 4, HoloTable_bottomTexBuf, GL_STATIC_DRAW);

	gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[97]);
	FloatBuffer HoloTable_bottomNorBuf = Buffers.newDirectFloatBuffer(HoloTable_bottomNvalues);
	gl.glBufferData(GL_ARRAY_BUFFER, HoloTable_bottomNorBuf.limit() * 4, HoloTable_bottomNorBuf, GL_STATIC_DRAW);


	//MirrorGate

	gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[98]);
	FloatBuffer MirrorGateVertBuf = Buffers.newDirectFloatBuffer(MirrorGatePvalues);
	gl.glBufferData(GL_ARRAY_BUFFER, MirrorGateVertBuf.limit() * 4, MirrorGateVertBuf, GL_STATIC_DRAW);

	gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[99]);
	FloatBuffer MirrorGateTexBuf = Buffers.newDirectFloatBuffer(MirrorGateTvalues);
	gl.glBufferData(GL_ARRAY_BUFFER, MirrorGateTexBuf.limit() * 4, MirrorGateTexBuf, GL_STATIC_DRAW);

	gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[100]);
	FloatBuffer MirrorGateNorBuf = Buffers.newDirectFloatBuffer(MirrorGateNvalues);
	gl.glBufferData(GL_ARRAY_BUFFER, MirrorGateNorBuf.limit() * 4, MirrorGateNorBuf, GL_STATIC_DRAW);


	//Mirror Gate Frame

	gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[101]);
	FloatBuffer MirrorGate_FrameVertBuf = Buffers.newDirectFloatBuffer(MirrorGate_FramePvalues);
	gl.glBufferData(GL_ARRAY_BUFFER, MirrorGate_FrameVertBuf.limit() * 4, MirrorGate_FrameVertBuf, GL_STATIC_DRAW);

	gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[102]);
	FloatBuffer MirrorGate_FrameTexBuf = Buffers.newDirectFloatBuffer(MirrorGate_FrameTvalues);
	gl.glBufferData(GL_ARRAY_BUFFER, MirrorGate_FrameTexBuf.limit() * 4, MirrorGate_FrameTexBuf, GL_STATIC_DRAW);

	gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[103]);
	FloatBuffer MirrorGate_FrameNorBuf = Buffers.newDirectFloatBuffer(MirrorGate_FrameNvalues);
	gl.glBufferData(GL_ARRAY_BUFFER, MirrorGate_FrameNorBuf.limit() * 4, MirrorGate_FrameNorBuf, GL_STATIC_DRAW);


	//Furry Knight 

	gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[104]);
	FloatBuffer FurryKnightVertBuf = Buffers.newDirectFloatBuffer(FurryKnightPvalues);
	gl.glBufferData(GL_ARRAY_BUFFER, FurryKnightVertBuf.limit() * 4, FurryKnightVertBuf, GL_STATIC_DRAW);

	gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[105]);
	FloatBuffer FurryKnightTexBuf = Buffers.newDirectFloatBuffer(FurryKnightTvalues);
	gl.glBufferData(GL_ARRAY_BUFFER, FurryKnightTexBuf.limit() * 4, FurryKnightTexBuf, GL_STATIC_DRAW);

	gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[106]);
	FloatBuffer FurryKnightNorBuf = Buffers.newDirectFloatBuffer(FurryKnightNvalues);
	gl.glBufferData(GL_ARRAY_BUFFER, FurryKnightNorBuf.limit() * 4, FurryKnightNorBuf, GL_STATIC_DRAW);
	
	}
	
	
	private void installLights(int renderingProgram, Matrix4f vMatrix)
	{	GL4 gl = (GL4) GLContext.getCurrentGL();
	
		currentLightPos.mulPosition(vMatrix);
		lightPos[0]=currentLightPos.x(); lightPos[1]=currentLightPos.y(); lightPos[2]=currentLightPos.z();
		
		// set current material values
		matAmb = thisAmb;
		matDif = thisDif;
		matSpe = thisSpe;
		matShi = thisShi;
		
		// get the locations of the light and material fields in the shader
		globalAmbLoc = gl.glGetUniformLocation(renderingProgram, "globalAmbient");
		ambLoc = gl.glGetUniformLocation(renderingProgram, "light.ambient");
		diffLoc = gl.glGetUniformLocation(renderingProgram, "light.diffuse");
		specLoc = gl.glGetUniformLocation(renderingProgram, "light.specular");
		posLoc = gl.glGetUniformLocation(renderingProgram, "light.position");
		mambLoc = gl.glGetUniformLocation(renderingProgram, "material.ambient");
		mdiffLoc = gl.glGetUniformLocation(renderingProgram, "material.diffuse");
		mspecLoc = gl.glGetUniformLocation(renderingProgram, "material.specular");
		mshiLoc = gl.glGetUniformLocation(renderingProgram, "material.shininess");
	
		//  set the uniform light and material values in the shader
		gl.glProgramUniform4fv(renderingProgram, globalAmbLoc, 1, globalAmbient, 0);
		gl.glProgramUniform4fv(renderingProgram, ambLoc, 1, lightAmbient, 0);
		gl.glProgramUniform4fv(renderingProgram, diffLoc, 1, lightDiffuse, 0);
		gl.glProgramUniform4fv(renderingProgram, specLoc, 1, lightSpecular, 0);
		gl.glProgramUniform3fv(renderingProgram, posLoc, 1, lightPos, 0);
		gl.glProgramUniform4fv(renderingProgram, mambLoc, 1, matAmb, 0);
		gl.glProgramUniform4fv(renderingProgram, mdiffLoc, 1, matDif, 0);
		gl.glProgramUniform4fv(renderingProgram, mspecLoc, 1, matSpe, 0);
		gl.glProgramUniform1f(renderingProgram, mshiLoc, matShi);
	}
	

	public static void main(String[] args) { new Starter(); }
	public void dispose(GLAutoDrawable drawable) {}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height)
	{	GL4 gl = (GL4) GLContext.getCurrentGL();

		aspect = (float) myCanvas.getWidth() / (float) myCanvas.getHeight();
		pMat.identity().setPerspective((float) Math.toRadians(60.0f), aspect, 0.1f, 1000.0f);

		setupShadowBuffers();
	}
	
	
	private void fillDataArray(byte data[])
	{ double xyPeriod = 30.0;
	  double turbPower = 0.15;
	  double turbSize =  80.0;
	  
	  for (int i=0; i<noiseWidth; i++)
	  { for (int j=0; j<noiseHeight; j++)
	    { for (int k=0; k<noiseDepth; k++)
	      {	double xValue = (i + (double)noiseWidth/2.0) / (double)noiseWidth;
		double yValue = (j - (double)noiseHeight/2.0) / (double)noiseHeight;
		double distValue = Math.sqrt(xValue * xValue + yValue * yValue)
						+ turbPower * turbulence(i, j, k, turbSize) / 256.0;
		double sineValue = 128.0 * Math.abs(Math.sin(2.0 * xyPeriod * distValue * Math.PI));

		Color c = new Color((int)(60+(int)sineValue), (int)(10+(int)sineValue), 0);

	        data[i*(noiseWidth*noiseHeight*4)+j*(noiseHeight*4)+k*4+1] = (byte) c.getGreen();
	        data[i*(noiseWidth*noiseHeight*4)+j*(noiseHeight*4)+k*4+2] = (byte) c.getBlue();
	        data[i*(noiseWidth*noiseHeight*4)+j*(noiseHeight*4)+k*4+3] = (byte) 255;
	} } } }
	

	private int buildNoiseTexture()
	{	GL4 gl = (GL4) GLContext.getCurrentGL();

		byte[] data = new byte[noiseHeight*noiseWidth*noiseDepth*4];
		
		fillDataArray(data);

		ByteBuffer bb = Buffers.newDirectByteBuffer(data);

		int[] textureIDs = new int[1];
		gl.glGenTextures(1, textureIDs, 0);
		int textureID = textureIDs[0];

		gl.glBindTexture(GL_TEXTURE_3D, textureID);

		gl.glTexStorage3D(GL_TEXTURE_3D, 1, GL_RGBA8, noiseWidth, noiseHeight, noiseDepth);
		gl.glTexSubImage3D(GL_TEXTURE_3D, 0, 0, 0, 0,
				noiseWidth, noiseHeight, noiseDepth, GL_RGBA, GL_UNSIGNED_INT_8_8_8_8_REV, bb);
		
		gl.glTexParameteri(GL_TEXTURE_3D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

		return textureID;
	}
	

	void generateNoise()
	{	for (int x=0; x<noiseHeight; x++)
		{	for (int y=0; y<noiseWidth; y++)
			{	for (int z=0; z<noiseDepth; z++)
				{	noise[x][y][z] = random.nextDouble();
	}	}	}	}
	
	
	double smoothNoise(double x1, double y1, double z1)
	{	//get fractional part of x, y, and z
		double fractX = x1 - (int) x1;
		double fractY = y1 - (int) y1;
		double fractZ = z1 - (int) z1;

		//neighbor values
		int x2 = ((int)x1 + noiseWidth + 1) % noiseWidth;
		int y2 = ((int)y1 + noiseHeight+ 1) % noiseHeight;
		int z2 = ((int)z1 + noiseDepth + 1) % noiseDepth;

		//smooth the noise by interpolating
		double value = 0.0;
		value += (1-fractX) * (1-fractY) * (1-fractZ) * noise[(int)x1][(int)y1][(int)z1];
		value += (1-fractX) * fractY     * (1-fractZ) * noise[(int)x1][(int)y2][(int)z1];
		value += fractX     * (1-fractY) * (1-fractZ) * noise[(int)x2][(int)y1][(int)z1];
		value += fractX     * fractY     * (1-fractZ) * noise[(int)x2][(int)y2][(int)z1];

		value += (1-fractX) * (1-fractY) * fractZ     * noise[(int)x1][(int)y1][(int)z2];
		value += (1-fractX) * fractY     * fractZ     * noise[(int)x1][(int)y2][(int)z2];
		value += fractX     * (1-fractY) * fractZ     * noise[(int)x2][(int)y1][(int)z2];
		value += fractX     * fractY     * fractZ     * noise[(int)x2][(int)y2][(int)z2];
		
		return value;
	}
	

	private double turbulence(double x, double y, double z, double size)
	{	double value = 5.0, initialSize = size;
		while(size >= 10.0)
		{	value = value + smoothNoise(x/size, y/size, z/size) * size;
			size = size / 2.0;
		}
		value = 128.0 * value / initialSize;
		return value;
	}
	
	
	public void displayAxis() {
		if (axisOn)
			axisOn = false;
		else 
			axisOn = true;
	}
	
	
	public void rotateLight() {
		if (rotateOn)
			rotateOn = false;
		else 
			rotateOn = true;
	}
	
	
	public void changeLight() {

		if (mainLightOn)
		{
			mainLightOn = false;
			inLightX = initialLightLoc.x;
			inLightY = initialLightLoc.y;
			inLightZ = initialLightLoc.z;
			initialLightLoc.x = 1000; 	initialLightLoc.y = 1000; 	initialLightLoc.z = 1000; 
		}
		else 
		{
			mainLightOn = true;
			initialLightLoc.x = inLightX;
			initialLightLoc.y = inLightY;
			initialLightLoc.z = inLightZ;
		}
}


	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		if (mainLightOn)
		{
        if (!arg0.isControlDown()) {
            if (arg0.getWheelRotation() < 0) initialLightLoc.y = (initialLightLoc.y() + 0.5f);
            else initialLightLoc.y = (initialLightLoc.y() - 0.5f);
        } else {
            if (arg0.getWheelRotation() < 0) initialLightLoc.z = (initialLightLoc.z() + 0.5f);
            else initialLightLoc.z = (initialLightLoc.z() - 0.5f);
        }
     //   DroidLightLocX = initialLightLoc.x; DroidLightLocY = initialLightLoc.y; DroidLightLocZ = initialLightLoc.z;
        myCanvas.display();
		}
		
	}
	

	@Override
	public void mouseDragged(MouseEvent arg0) {
		if (mainLightOn)
		{
        float z = (float) initialLightLoc.z();

        if (arg0.getX() > this.getWidth() / 2 && arg0.getX() < this.getWidth())
        	initialLightLoc.x = (this.getWidth() / 2 + (arg0.getX() - this.getWidth()));
        else if (arg0.getX() > this.getWidth()) initialLightLoc.x = (this.getWidth());
        else if (arg0.getX() < this.getWidth() / 2) initialLightLoc.x = (arg0.getX() - this.getWidth() / 2);
        else initialLightLoc.x = 0;

        if (arg0.getY() > this.getHeight() / 2 && arg0.getY() < this.getHeight())
        	initialLightLoc.y = (-(this.getHeight() / 2 + (arg0.getY() - this.getHeight())));
        else if (arg0.getY() > this.getHeight()) initialLightLoc.y = (this.getHeight());
        else if (arg0.getY() < this.getHeight() / 2) initialLightLoc.y = (-(arg0.getY() - this.getHeight() / 2));
        else initialLightLoc.y = 0;

        initialLightLoc.z = (z);
     //   DroidLightLocX = initialLightLoc.x; DroidLightLocY = initialLightLoc.y; DroidLightLocZ = initialLightLoc.z;
        myCanvas.display();
		}
		
	}
	

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}