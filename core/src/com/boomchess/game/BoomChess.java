package com.boomchess.game;

import static com.boomchess.game.frontend.stage.GameStage.createGameStage;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.boomchess.game.backend.BOT;
import com.boomchess.game.backend.Board;
import com.boomchess.game.backend.Coordinates;
import com.boomchess.game.backend.Damage;
import com.boomchess.game.backend.Soldier;
import com.boomchess.game.backend.subsoldier.Artillery;
import com.boomchess.game.backend.subsoldier.Commando;
import com.boomchess.game.backend.subsoldier.General;
import com.boomchess.game.backend.subsoldier.Helicopter;
import com.boomchess.game.backend.subsoldier.Infantry;
import com.boomchess.game.backend.subsoldier.Tank;
import com.boomchess.game.backend.subsoldier.Wardog;
import com.boomchess.game.frontend.actor.AttackSequence;
import com.boomchess.game.frontend.actor.DamageNumber;
import com.boomchess.game.frontend.actor.DeathExplosionActor;
import com.boomchess.game.frontend.actor.DottedLineActor;
import com.boomchess.game.frontend.actor.HitMarkerActor;
import com.boomchess.game.frontend.actor.moveBotTile;
import com.boomchess.game.frontend.animations.soldierAnimation;
import com.boomchess.game.frontend.picture.RandomImage;
import com.boomchess.game.frontend.picture.SpeechBubbles;
import com.boomchess.game.frontend.screen.RelativeResizer;
import com.boomchess.game.frontend.sound.MusicPlaylist;
import com.boomchess.game.frontend.sound.RandomSound;
import com.boomchess.game.frontend.stage.ChallengeStage;
import com.boomchess.game.frontend.stage.CreditsStage;
import com.boomchess.game.frontend.stage.GameEndStage;
import com.boomchess.game.frontend.stage.GameStage;
import com.boomchess.game.frontend.stage.LoadingScreenStage;
import com.boomchess.game.frontend.stage.MapStage;
import com.boomchess.game.frontend.stage.MenuStage;
import com.boomchess.game.frontend.stage.OptionsStage;

import java.util.ArrayList;

public class BoomChess extends ApplicationAdapter {

	// used for essential resolution and drawing matters -------------------------------------------------------
	public static SpriteBatch batch;
	// usage for Scene2DUI-skins and stages -------------------------------------------------------
	// size of tiles on the board
	public static float tileSize;
	public static Skin skin;
	public static Skin progressBarSkin;
	public static int numberObstacle; // number of obstacles in the default game mode
	public static Stage currentStage;

	// for the Move Overlay ------------------------------------------------------
	public static boolean showMove = false;
	private static Stage  moveLogoStage;

	// for the  map -----------------------------------------

	public static Stage mapStage;

	public static RandomImage medievalMaps;
	public static RandomImage modernMaps;

	// for setting the current "Mover" of the game / if a Move has been valid ------------------------------
	public static boolean legitTurn = false;

	// for the x-marker overlay over the game-field -----------------------------------------------
	public static boolean renderOverlay = false;
	public static Stage possibleMoveOverlay;

	// for bot matching -----------------------------------------------
	public static boolean isBotMatch;

	// used for the dotted line when damage occurs -----------------------------------------------
	// Shape Renderer for easy drawing of lines
	private static ShapeRenderer shapeRenderer;
	// stage we render the shapes on
	public static Stage dottedLineStage;
	// used for the deathExplosion ---------------------------------------------
	public static Stage deathExplosionStage;

	// -------------------------------------------------------------------------------------------
	// ---------------------------- Asset Manager -----------------------------------------------
	// -------------------------------------------------------------------------------------------

	// all assets that can appear multiple times on the screen at once get called a Texture, for creating new Image
	// objects down the line
	public static Texture redTank;
	public static Texture redHelicopter;
	public static Texture redWardog;
	public static Texture redGeneral;
	public static Texture redCommando;
	public static Texture redInfantry;
	public static Texture greenTank;
	public static Texture greenHelicopter;
	public static Texture greenWardog;
	public static Texture greenGeneral;
	public static Texture greenCommando;
	public static Texture greenInfantry;
	public static Texture greenArtillery;
	public static Texture greenJeep;
	public static Texture redArtillery;
	public static Texture redJeep;
	public static Texture blueArtillery;
	public static Texture blueInfantry;
	public static Texture blueCommando;
	public static Texture blueGeneral;
	public static Texture blueWardog;
	public static Texture blueHelicopter;
	public static Texture blueTank;
	public static Texture blueJeep;

	private static Image redMove;
	private static Image greenMove;
	private static Image blueMove;

	private static Image actionOngoing;

	private static Texture xMarker;
	public static Image boomLogo;

	public static Texture empty;
	public static Texture hill;

	// music
	public static MusicPlaylist background_music;
	public static MusicPlaylist menu_music;

	// universal Buttons -- here for music and sound control

	public static Button muteButton;

	public static Button skipButton;

	// volume of Sounds

	public static float volume = 0.2f;  // variable to store the current MUSIC volume level

	public static float soundVolume = 0.20f;  // variable to store the current SOUND volume level

	// for the labels

	public static TextButton volumeLabel;

	public static TextButton soundVolumeLabel;

	// audio table
	public static Table audioTable;

	// RandomSound Objects of Sound Groups to be played by the Pieces if they deal Damage

	public static RandomSound smallArmsSound;
	public static RandomSound bigArmsSound;
	public static RandomSound dogSound;
	public static RandomSound helicopterSound;
	public static RandomSound tankSound;
	public static RandomSound smallExplosionSound;
	public static RandomSound bigExplosionSound;

	// for the medieval mode

	public static RandomSound archerSound;
	public static RandomSound catapultSound;
	public static RandomSound magicSound;
	public static RandomSound knightSound;
	public static RandomSound queenSound;
	public static RandomSound kingSound;

	public static Texture redArcher;
	public static Texture redCatapult;
	public static Texture redMagician;
	public static Texture redKnight;
	public static Texture redQueen;
	public static Texture redKing;
	public static Texture greenArcher;
	public static Texture greenCatapult;
	public static Texture greenMagician;
	public static Texture greenKnight;
	public static Texture greenQueen;
	public static Texture greenKing;
	public static Texture greenFea;
	public static Texture redFea;

	// random Texture Objects for the obstacles as well as the obstacle Texture loaded into it
	// for use in the hill constructor

	public static RandomImage obstacleTextures;

	// for the credits
	public static MusicPlaylist creditsMusic;

	// for the boolean value if the game is in medieval mode

	public static boolean isMedievalMode = false;

	public static boolean isColourChanged;

	public static Sound loadingSound;

	public static String botDifficulty = "medium";

	// stage for gameEnd
	public static Stage gameEndStage;

	// botMove class
	public static moveBotTile botMove;

	// variables for empty coordinate for botmove

	public static boolean useEmpty;
	public static int emptyX;
	public static int emptyY;

	// stage used for the moving bot soldier pictures
	public static Stage botMovingStage;

	public static AttackSequence actionSequence; // for when the Sequence is running

	// for the textures of the loadingScreen

	public static RandomImage loadingScreenTextures;
	private static Stage loadingStage;
	private static boolean loadingScreenIsRunning = true;
	
	// for the return to menu button, we have to have a boolean keeping processTurn from running if not true
	public static boolean inGame = false;

	// public Texture for the speech bubble general "Attack incoming!"
	public static Texture alarmTexture;

	// random texture object of kill speech bubbles
	public static RandomImage killSpeeches;

	// for the speech bubbles in general, we need to build a stage especially for them
	public static Stage speechBubbleStage;

	// for the sounds of the speechBubbles
	public static RandomSound speechSounds;
	public static RandomSound radioChatter;

	// boolean if beep (speech) used
	public static boolean isBeepMode = false;

	public static Stage crossOfDeathStage;

	public static Texture crossOfDeathTexture;

	private static boolean sequenceRunning;

	private static float loadingElapsed = 0;
	private static boolean assetsLoaded = false;
	public static Stage wrongMoveStage;
	public static Texture wrongMoveLogo;
	public static Sound brick;

	// for the help stage and the help button
	public static Stage helpStage;
	public static Texture helpTexture;
	public static boolean showHelp;

	// bot arm texture for botMoveTile

	public static Texture botArm;
	public static boolean showArm = false;

	// for the option stages ( bot and non bot )
	public static boolean showInGameOptions;
	public static Stage inGamOptStage;
	public static Texture clipBoard;

	// -----------------------------------------------------------------------------------------

	// android relative sizing capability

	public static Stage backgroundStage;

	public static float screenWidth;
	public static float screenHeigth;

	public static float buttonWidth;
	public static float buttonHeight;


	// -----------------------------------------------------------------------------------------

	// credits

	public static Texture credits;
	public static Texture extendedCredits;

	// --------------------------------------------

	public static Sound katIncluded;

	public static Texture tutorialTexture;
	public static boolean inTutorial = false;

	// --------------------------------------------

	public static int botMovingSpeed = 1;

	// --------------------------------------------

	public static boolean showAttackCircle = true;
	public static Texture threeTOthreeCircle;
	public static Texture fiveTOfiveCircle;

	// --------------------------------------------

	public static boolean showPossibleDamage = true;

	public static Stage damageNumberStage;

	public static Texture plusFive;
	public static Texture plusTen;
	public static Texture minusFive;
	public static Texture minusTen;

	// -------------------------------------------

	// textures for damage intervals
	public static boolean showIntervals = false;
	public static Texture tenTwenty;
	public static Texture oneFive;
	public static Texture oneTwenty;
	public static Texture fiveThirty;
	public static Texture fiveTen;
	public static Texture fiveTwenty;

	// -------------------------------------------

	// numbers
	public static Texture zero;
	public static Texture one;
	public static Texture two;
	public static Texture three;
	public static Texture four;
	public static Texture five;
	public static Texture six;
	public static Texture seven;
	public static Texture eight;
	public static Texture nine;

	// --------------------------------------------------------------------------------------------------

	// for the possible colors of textures, loaded so we don't have to change each pixel of the
	// standard numbers, this way costs less processing power

	public static Texture redOne;
	public static Texture greenOne;
	public static Texture yellowOne;
	public static Texture orangeOne;
	public static Texture redTwo;
	public static Texture greenTwo;
	public static Texture yellowTwo;
	public static Texture orangeTwo;
	public static Texture redThree;
	public static Texture greenThree;
	public static Texture yellowThree;
	public static Texture orangeThree;
	public static Texture redFour;
	public static Texture greenFour;
	public static Texture yellowFour;
	public static Texture orangeFour;
	public static Texture redFive;
	public static Texture greenFive;
	public static Texture yellowFive;
	public static Texture orangeFive;
	public static Texture redSix;
	public static Texture greenSix;
	public static Texture yellowSix;
	public static Texture orangeSix;
	public static Texture redSeven;
	public static Texture greenSeven;
	public static Texture yellowSeven;
	public static Texture orangeSeven;
	public static Texture redEight;
	public static Texture greenEight;
	public static Texture yellowEight;
	public static Texture orangeEight;
	public static Texture redNine;
	public static Texture greenNine;
	public static Texture yellowNine;
	public static Texture orangeNine;
	public static Texture redZero;
	public static Texture greenZero;
	public static Texture yellowZero;
	public static Texture orangeZero;

	// ----------------------------

	// black circle behind the health number

	public static Texture blackCircle;

	// -----------------------------

	// stage for the current song name that is playing

	public static Stage songNameStage;
	public static Label musicLabel;
	public static float musicLabelScale;

	// -----------------------------

	// for the animation or texture setting

	public static boolean isAnimated = true;

	// -----------------------------

	// soldierAnimations for all pieceTypes

	public static soldierAnimation redInfantryAnimation;
	public static soldierAnimation redCommandoAnimation;
	public static soldierAnimation redGeneralAnimation;
	public static soldierAnimation redWardogAnimation;
	public static soldierAnimation redHelicopterAnimation;
	public static soldierAnimation redTankAnimation;
	public static soldierAnimation redArtilleryAnimation;
	public static soldierAnimation greenInfantryAnimation;
	public static soldierAnimation greenCommandoAnimation;
	public static soldierAnimation greenGeneralAnimation;
	public static soldierAnimation greenWardogAnimation;
	public static soldierAnimation greenHelicopterAnimation;
	public static soldierAnimation greenTankAnimation;
	public static soldierAnimation greenArtilleryAnimation;
	public static soldierAnimation blueInfantryAnimation;
	public static soldierAnimation blueCommandoAnimation;
	public static soldierAnimation blueGeneralAnimation;
	public static soldierAnimation blueWardogAnimation;
	public static soldierAnimation blueHelicopterAnimation;
	public static soldierAnimation blueTankAnimation;
	public static soldierAnimation blueArtilleryAnimation;
	public static soldierAnimation blueJeepAnimation;
	public static soldierAnimation redJeepAnimation;
	public static soldierAnimation greenJeepAnimation;

	// ------------------------------------------------------

	// if on desktop, false, android if true, sets the resizing by tileSize and UI size
	public static boolean publisher = true;

	// ------------------------------------------------------

	public static boolean dogIsJeep = true;
	public static boolean nonInvasiveReRender = false;

	@Override
	public void create() {
		// creation of the batch for drawing the images
		batch = new SpriteBatch();

		// loading Screen is going till loading complete and main menu starts -------------

		// skin of the UI --------------------
		// skin (look) of the buttons via the prearranged json file
		skin = new Skin(Gdx.files.internal("menu.commodore64/uiskin.json"));

		loadingScreenTextures = new RandomImage();

		// initialises the tile size for relative positioning of stages
		RelativeResizer.init();
		// sets public tilesize variable and skin bitmap size  for button scale

		loadingSound = Gdx.audio.newSound(Gdx.files.internal("sounds/countdown.mp3"));
		tutorialTexture = new Texture(Gdx.files.internal("Misc/tutorial.png"));
		loadingStage = LoadingScreenStage.initalizeUI();

		// creating all stage objects
		deathExplosionStage = new Stage(new ScreenViewport());
		mapStage = new Stage(new ScreenViewport());
		dottedLineStage = new Stage(new ScreenViewport());
		botMovingStage = new Stage(new ScreenViewport());
		speechBubbleStage = new Stage(new ScreenViewport());
		crossOfDeathStage = new Stage(new ScreenViewport());
		gameEndStage = new Stage(new ScreenViewport());
		currentStage = new Stage(new ScreenViewport());
		moveLogoStage = new Stage(new ScreenViewport());
		possibleMoveOverlay = new Stage(new ScreenViewport());
		wrongMoveStage = new Stage(new ScreenViewport());
		helpStage = new Stage(new ScreenViewport());
		inGamOptStage = new Stage(new ScreenViewport());
		backgroundStage = new Stage(new ScreenViewport());
		damageNumberStage = new Stage(new ScreenViewport());
		songNameStage = new Stage(new ScreenViewport());

		// resize all stages for the beginning
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		// background is drawn in a batch, hence Texture
		Texture background = new Texture(Gdx.files.internal("backgrounds/background_4.png"));

		screenWidth = Gdx.graphics.getWidth();
		screenHeigth = Gdx.graphics.getHeight();

		Image backgroundImage = new Image(background);
		backgroundImage.setSize(screenWidth, screenHeigth);
		backgroundStage.addActor(backgroundImage);

		buttonWidth = tileSize * 2;
		buttonHeight = tileSize / 2;

		loadingScreenIsRunning = true;
	}

	public enum GameState {
		// for determining the current state of the game
		/*
		* The game has 3 states: RED_TURN, GREEN_TURN, NOT_IN_GAME
		* Using these states allows for smooth switching between game assets
		* RED_TURN: the red player has their turn
		* GREEN_TURN: the green player has their turn
		* NOT_IN_GAME: the game is not in progress, in any menuStage
		 */

		RED_TURN, GREEN_TURN, NOT_IN_GAME
	}

	// the first state at game Start is NOT_IN_GAME
	public static GameState currentState = GameState.NOT_IN_GAME;
	
	@Override
	public void render() {
		/*
		* render is called every frame, main-game loop of the game, holds all stages in nested ifs and the processTurn
		 */

		Gdx.gl.glClearColor(1, 1, 1, 1);

		// checks if the screen has been resized and if it has, it changes tileSize for relative
		// stage creation
		RelativeResizer.ensure();

		if (loadingScreenIsRunning){
			loadingStage.act();
			loadingStage.draw();
			// run loading screen for 4 seconds atleast
			if(loadingElapsed < 4){
				loadingElapsed += Gdx.graphics.getDeltaTime();
			} else {
				loadingScreenIsRunning = false;
				// ensures game starts in menu
				createMainMenuStage();
				loadingStage.clear();
				Gdx.app.log("LoadingScreen", "LoadingScreen finished");
			}
			// load the assets first time
			if(!(assetsLoaded)){
				loadAllAssets();
				katIncluded.play(volume);
			}
			return;
		}

		backgroundStage.act();
		backgroundStage.draw();

		// map underneath the currentStage if the game is ongoing
		if (inGame){
			// for the map
			mapStage.act();
			mapStage.draw();
		}

		// for the overlay of possible moves
		if (renderOverlay) {
			possibleMoveOverlay.act();
			possibleMoveOverlay.draw();
		}

		// for the stages, displays only stage assigned as currentStage, see method switchToStage
		currentStage.act(Gdx.graphics.getDeltaTime());
		currentStage.draw();

		// for the crossOfDeathStage
		crossOfDeathStage.act();
		crossOfDeathStage.draw();

		// for the dotted line when damage occurs -----------------------------------------------
		// Render the dottedLineStage
		dottedLineStage.act(Gdx.graphics.getDeltaTime());
		dottedLineStage.draw();

		// for the deathExplosion --------------------------------------------------------------
		// Render the deathExplosionStage
		deathExplosionStage.act(Gdx.graphics.getDeltaTime());
		deathExplosionStage.draw();

		// stage for the moving bot soldiers
		botMovingStage.act(Gdx.graphics.getDeltaTime());
		botMovingStage.draw();

		// stage for the damage numbers and its Runnables
		damageNumberStage.act(Gdx.graphics.getDeltaTime());
		damageNumberStage.draw();

		// stage for the speech bubbles
		speechBubbleStage.act(Gdx.graphics.getDeltaTime());
		speechBubbleStage.draw();

		// render the gameEndStage
		gameEndStage.act();
		gameEndStage.draw();

		// for the move Logo, clear the stage, change logo
		moveLogoStage.clear(); // to ensure no double overlay
		updateMoveLogo();

		if(inGame){
			// draw the movelogo
			moveLogoStage.act();
			moveLogoStage.draw();

			wrongMoveStage.act();
			wrongMoveStage.draw();
		}


		if(showHelp){
			showInGameOptions = false;
			Gdx.input.setInputProcessor(currentStage);
			helpStage.act();
			helpStage.draw();
			Gdx.app.log("Help", "Help is shown");
		}

		if(showInGameOptions){
			Gdx.input.setInputProcessor(inGamOptStage);
			inGamOptStage.act();
			inGamOptStage.draw();
		}

		// for the song name stage
		songNameStage.act();
		songNameStage.draw();

		if (actionSequence.getDamageSequenceRunning()){
			// update the method playNext
			sequenceRunning = true;
			actionSequence.playNext(Gdx.graphics.getDeltaTime());
			return;
		}
		sequenceRunning = false;

		if(inGame){
			// make a turn check if the game is in progress
			processTurn();
		}
	}

	private void processTurn() {
		/*
		* ProcessTurn is called at end of every Frame and triggers game progression if a Drag&Drop turn is legit /
		* triggers the bot if isBotMatch and RED_MOVE
		 */
		if (currentState == GameState.RED_TURN) {
			if (!isBotMatch){
				if (legitTurn) {
					calculateDamage("red");
					legitTurn = false;
				}
			} else {
				// switch case to make a bot decision for red team
				if (!(botMove.getIsMoving())) {
					switch (botDifficulty) {
						case ("easy"):
							BOT.easyBotMove();
							break;
						case ("medium"):
							BOT.mediumBotMove();
							break;
						case ("hard"):
							BOT.hardBotMove();
							break;
					}
					legitTurn = false;
				} else {
					// add delta float time to BotMove.update
					botMove.update(Gdx.graphics.getDeltaTime()); // updates till moving has finished
				}

				if (botMove.movingFinished) { // if the bot moving has finished, render and attack

					// update the gameBoard officially, not with botMove Trick
					Board.update(botMove.startX, botMove.startY, botMove.endX, botMove.endY);
					botMovingStage.clear(); // clear the Stage so that moveSoldier is gone
					reRenderGame();

					// calculate damage, starts consequence
					calculateDamage("red");
				}
			}
		} else if (currentState == GameState.GREEN_TURN) {
			if (legitTurn) {
				calculateDamage("green");
				legitTurn = false;
			}
		}
	}

	private void loadAllAssets() {
		/*
		This method gets called during the main loading Stage runs
		 */

		// for defaulting colour change
		isColourChanged = true;

		// music label
		// load empty musicLabel and put its position to the lower left of the screen
		musicLabel = new Label("", skin);
		musicLabel.setPosition(tileSize, tileSize*1.25f);
		musicLabel.setColor(Color.BLUE);
		// scale down by 0.75 to 0.25 of current size
		musicLabel.setFontScale(musicLabelScale);
		songNameStage.addActor(musicLabel);

		// assets ------------------------------------------------------------------------

		greenMove = new Image(new Texture(Gdx.files.internal("moveLogos/green_Move.png")));
		redMove = new Image(new Texture(Gdx.files.internal("moveLogos/red_Move.png")));
		blueMove = new Image(new Texture(Gdx.files.internal("moveLogos/blue_Move.png")));

		// for the intervals

		tenTwenty = new Texture(Gdx.files.internal("Misc/tenTwenty.png"));
		oneFive = new Texture(Gdx.files.internal("Misc/oneFive.png"));
		oneTwenty = new Texture(Gdx.files.internal("Misc/oneTwenty.png"));
		fiveThirty = new Texture(Gdx.files.internal("Misc/fiveThirty.png"));
		fiveTen = new Texture(Gdx.files.internal("Misc/fiveTen.png"));
		fiveTwenty = new Texture(Gdx.files.internal("Misc/fiveTwenty.png"));

		// for the damage numbers

		zero = new Texture(Gdx.files.internal("Misc/zero.png"));
		one = new Texture(Gdx.files.internal("Misc/one.png"));
		two = new Texture(Gdx.files.internal("Misc/two.png"));
		three = new Texture(Gdx.files.internal("Misc/three.png"));
		four = new Texture(Gdx.files.internal("Misc/four.png"));
		five = new Texture(Gdx.files.internal("Misc/five.png"));
		six = new Texture(Gdx.files.internal("Misc/six.png"));
		seven = new Texture(Gdx.files.internal("Misc/seven.png"));
		eight = new Texture(Gdx.files.internal("Misc/eight.png"));
		nine = new Texture(Gdx.files.internal("Misc/nine.png"));

		// damage numbers

		plusFive = new Texture(Gdx.files.internal("Misc/plusFive.png"));
		plusTen = new Texture(Gdx.files.internal("Misc/plusTen.png"));
		minusFive = new Texture(Gdx.files.internal("Misc/minusFive.png"));
		minusTen = new Texture(Gdx.files.internal("Misc/minusTen.png"));

		// set the size of all move logos

		greenMove.setSize(tileSize*2.5f, tileSize*1.2f);
		redMove.setSize(tileSize*2.5f, tileSize*1.2f);
		blueMove.setSize(tileSize*2.5f, tileSize*1.2f);

		//------------------------------------------------

		greenInfantry = new Texture(Gdx.files.internal("greenTeam/infantry_green_right.png"));
		redInfantry = new Texture(Gdx.files.internal("redTeam/infantry_red_left.png"));
		greenCommando = new Texture(Gdx.files.internal("greenTeam/commando_green_right.png"));
		redCommando = new Texture(Gdx.files.internal("redTeam/commando_red_left.png"));
		greenGeneral = new Texture(Gdx.files.internal("greenTeam/general_green_right.png"));
		redGeneral = new Texture(Gdx.files.internal("redTeam/general_red_left.png"));
		greenWardog = new Texture(Gdx.files.internal("greenTeam/war_dog_green_right.png"));
		redWardog = new Texture(Gdx.files.internal("redTeam/war_dog_red_left.png"));
		greenHelicopter = new Texture(Gdx.files.internal("greenTeam/helicopter_green_right.png"));
		redHelicopter = new Texture(Gdx.files.internal("redTeam/helicopter_red_left.png"));
		greenTank = new Texture(Gdx.files.internal("greenTeam/tank_green_right.png"));
		redTank = new Texture(Gdx.files.internal("redTeam/tank_red_left.png"));
		greenArtillery = new Texture(Gdx.files.internal("greenTeam/artillery_green_right.png"));
		redArtillery = new Texture(Gdx.files.internal("redTeam/artillery_red_left.png"));
		greenJeep = new Texture(Gdx.files.internal("greenTeam/jeep_green_right.png"));
		redJeep = new Texture(Gdx.files.internal("redTeam/jeep_red_left.png"));

		// bot arm
		botArm = new Texture(Gdx.files.internal("Misc/botArm.png"));


		blueArtillery = new Texture(Gdx.files.internal("blueTeam/artillery_blue_right.png"));
		blueInfantry = new Texture(Gdx.files.internal("blueTeam/infantry_blue_right.png"));
		blueCommando = new Texture(Gdx.files.internal("blueTeam/commando_blue_right.png"));
		blueGeneral = new Texture(Gdx.files.internal("blueTeam/general_blue_right.png"));
		blueWardog = new Texture(Gdx.files.internal("blueTeam/war_dog_blue_right.png"));
		blueHelicopter = new Texture(Gdx.files.internal("blueTeam/helicopter_blue_right.png"));
		blueTank = new Texture(Gdx.files.internal("blueTeam/tank_blue_right.png"));
		blueJeep = new Texture(Gdx.files.internal("blueTeam/jeep_blue_right.png"));

		xMarker = new Texture(Gdx.files.internal("Misc/xMarker.png"));

		boomLogo = new Image(new Texture(Gdx.files.internal("logo/Logo3.png")));

		empty = new Texture(Gdx.files.internal("Misc/empty.png"));

		// help texture
		helpTexture = new Texture(Gdx.files.internal("Misc/rules.png"));

		// attack circles

		threeTOthreeCircle = new Texture(Gdx.files.internal("Misc/threeTothreeCircle.png"));
		fiveTOfiveCircle = new Texture(Gdx.files.internal("Misc/fiveTofiveCircle.png"));

		// loading coloured numbers
		redOne = new Texture(Gdx.files.internal("numbers/redOne.png"));
		greenOne = new Texture(Gdx.files.internal("numbers/greenOne.png"));
		yellowOne = new Texture(Gdx.files.internal("numbers/yellowOne.png"));
		orangeOne = new Texture(Gdx.files.internal("numbers/orangeOne.png"));

		redTwo = new Texture(Gdx.files.internal("numbers/redTwo.png"));
		greenTwo = new Texture(Gdx.files.internal("numbers/greenTwo.png"));
		yellowTwo = new Texture(Gdx.files.internal("numbers/yellowTwo.png"));
		orangeTwo = new Texture(Gdx.files.internal("numbers/orangeTwo.png"));

		redThree = new Texture(Gdx.files.internal("numbers/redThree.png"));
		greenThree = new Texture(Gdx.files.internal("numbers/greenThree.png"));
		yellowThree = new Texture(Gdx.files.internal("numbers/yellowThree.png"));
		orangeThree = new Texture(Gdx.files.internal("numbers/orangeThree.png"));

		redFour = new Texture(Gdx.files.internal("numbers/redFour.png"));
		greenFour = new Texture(Gdx.files.internal("numbers/greenFour.png"));
		yellowFour = new Texture(Gdx.files.internal("numbers/yellowFour.png"));
		orangeFour = new Texture(Gdx.files.internal("numbers/orangeFour.png"));

		redFive = new Texture(Gdx.files.internal("numbers/redFive.png"));
		greenFive = new Texture(Gdx.files.internal("numbers/greenFive.png"));
		yellowFive = new Texture(Gdx.files.internal("numbers/yellowFive.png"));
		orangeFive = new Texture(Gdx.files.internal("numbers/orangeFive.png"));

		redSix = new Texture(Gdx.files.internal("numbers/redSix.png"));
		greenSix = new Texture(Gdx.files.internal("numbers/greenSix.png"));
		yellowSix = new Texture(Gdx.files.internal("numbers/yellowSix.png"));
		orangeSix = new Texture(Gdx.files.internal("numbers/orangeSix.png"));

		redSeven = new Texture(Gdx.files.internal("numbers/redSeven.png"));
		greenSeven = new Texture(Gdx.files.internal("numbers/greenSeven.png"));
		yellowSeven = new Texture(Gdx.files.internal("numbers/yellowSeven.png"));
		orangeSeven = new Texture(Gdx.files.internal("numbers/orangeSeven.png"));

		redEight = new Texture(Gdx.files.internal("numbers/redEight.png"));
		greenEight = new Texture(Gdx.files.internal("numbers/greenEight.png"));
		yellowEight = new Texture(Gdx.files.internal("numbers/yellowEight.png"));
		orangeEight = new Texture(Gdx.files.internal("numbers/orangeEight.png"));

		redNine = new Texture(Gdx.files.internal("numbers/redNine.png"));
		greenNine = new Texture(Gdx.files.internal("numbers/greenNine.png"));
		yellowNine = new Texture(Gdx.files.internal("numbers/yellowNine.png"));
		orangeNine = new Texture(Gdx.files.internal("numbers/orangeNine.png"));

		redZero = new Texture(Gdx.files.internal("numbers/redZero.png"));
		greenZero = new Texture(Gdx.files.internal("numbers/greenZero.png"));
		yellowZero = new Texture(Gdx.files.internal("numbers/yellowZero.png"));
		orangeZero = new Texture(Gdx.files.internal("numbers/orangeZero.png"));

		Gdx.app.log("BoomChess", "Loading Assets: HealthNumber assets finished");

		// Loading Texture of the map

		Gdx.app.log("BoomChess", "Loading Assets: Maps");

		medievalMaps = new RandomImage();
		modernMaps = new RandomImage();

		medievalMaps.addTexture("map/map2/game_map.png"); // colourful medieval map
		modernMaps.addTexture("map/map2/game_map2.png"); // city map
		modernMaps.addTexture("map/map2/game_map3.png"); // city map
		medievalMaps.addTexture("map/map2/game_map4.png"); // colourful village map
		medievalMaps.addTexture("map/map2/game_map5.png"); // colourful village map
		modernMaps.addTexture("map/map2/game_map6.png"); // desert City map
		modernMaps.addTexture("map/map2/game_map7.png"); // desert City map

		modernMaps.addTexture("map/map3/map1.png"); // cool black and white map
		modernMaps.addTexture("map/map3/map2.png"); // cool black and white map
		modernMaps.addTexture("map/map3/map3.png"); // cool black and white map
		modernMaps.addTexture("map/map3/map4.png"); // cool black and white map
		modernMaps.addTexture("map/map3/map5.png"); // cool black and white map
		modernMaps.addTexture("map/map3/map6.png"); // cool black and white map

		Gdx.app.log("BoomChess", "Loading Assets: HealthNumber assets");
		// black circle

		blackCircle = new Texture(Gdx.files.internal("Misc/blackCircle.png"));

		// texture for the action Running logo
		actionOngoing = new Image(new Texture(Gdx.files.internal("Misc/actionOngoing.png")));
		actionOngoing.setSize(tileSize*2.5f, tileSize*1.2f);

		Gdx.app.log("BoomChess", "Loading Assets: Maps done");

		Gdx.app.log("BoomChess", "Loading Assets: Medieval Assets");

		// load the Textures of the medieval game mode

		redArcher = new Texture(Gdx.files.internal("medieval/red_archer.png"));
		redCatapult = new Texture(Gdx.files.internal("medieval/red_catapult.png"));
		redMagician = new Texture(Gdx.files.internal("medieval/red_magician.png"));
		redKnight = new Texture(Gdx.files.internal("medieval/red_knight.png"));
		redQueen = new Texture(Gdx.files.internal("medieval/red_queen.png"));
		redKing = new Texture(Gdx.files.internal("medieval/red_king.png"));
		greenArcher = new Texture(Gdx.files.internal("medieval/green_archer.png"));
		greenCatapult = new Texture(Gdx.files.internal("medieval/green_catapult.png"));
		greenMagician = new Texture(Gdx.files.internal("medieval/green_magician.png"));
		greenKnight = new Texture(Gdx.files.internal("medieval/green_knight.png"));
		greenQueen = new Texture(Gdx.files.internal("medieval/green_queen.png"));
		greenKing = new Texture(Gdx.files.internal("medieval/green_king.png"));
		redFea = new Texture(Gdx.files.internal("medieval/red_fea.png"));
		greenFea = new Texture(Gdx.files.internal("medieval/green_fea.png"));

		Gdx.app.log("BoomChess", "Loading Assets: Medieval Assets done");

		Gdx.app.log("BoomChess", "Loading Assets: Speech Bubbles & Obstacles");

		// textures of the obstacles

		obstacleTextures = new RandomImage();
		obstacleTextures.addTexture("obstacles/obstacle1.png");
		obstacleTextures.addTexture("obstacles/obstacle2.png");
		obstacleTextures.addTexture("obstacles/obstacle3.png");
		obstacleTextures.addTexture("obstacles/obstacle4.png");
		obstacleTextures.addTexture("obstacles/obstacle5.png");
		obstacleTextures.addTexture("obstacles/obstacle6.png");
		obstacleTextures.addTexture("obstacles/obstacle8.png");
		obstacleTextures.addTexture("obstacles/obstacle9.png");

		// adding alarm texture

		alarmTexture = new Texture(Gdx.files.internal("speechbubbles/alarmattack.png"));

		// adding speech bubbles that show a kill having happened
		killSpeeches = new RandomImage();
		killSpeeches.addTexture("speechbubbles/kill1.png");
		killSpeeches.addTexture("speechbubbles/kill2.png");
		killSpeeches.addTexture("speechbubbles/kill3.png");
		killSpeeches.addTexture("speechbubbles/kill4.png");
		killSpeeches.addTexture("speechbubbles/kill5.png");

		// Texture of the red cross of death
		crossOfDeathTexture = new Texture(Gdx.files.internal("Misc/crossOfDeath.png"));

		Gdx.app.log("BoomChess", "Loading Assets: Speech Bubbles & Obstacles done");

		// load the sound effects into respective Objects --------------------------------------

		Gdx.app.log("BoomChess", "Loading Assets: Sound Effects");

		smallArmsSound = new RandomSound();
		smallArmsSound.addSound("sounds/desert-eagle-gunshot.mp3");
		smallArmsSound.addSound("sounds/Gunshot/autocannon-20mm.mp3");
		smallArmsSound.addSound("sounds/Gunshot/High/Long/A.mp3");
		smallArmsSound.addSound("sounds/Gunshot/High/Long/D.mp3");
		smallArmsSound.addSound("sounds/Gunshot/High/Long/C.mp3");
		smallArmsSound.addSound("sounds/Gunshot/High/Long/E.mp3");
		smallArmsSound.addSound("sounds/Gunshot/High/Long/F.mp3");
		smallArmsSound.addSound("sounds/Gunshot/High/Long/G.mp3");
		smallArmsSound.addSound("sounds/Gunshot/High/Long/H.mp3");
		smallArmsSound.addSound("sounds/Gunshot/High/Short/A.mp3");
		smallArmsSound.addSound("sounds/Gunshot/High/Short/C.mp3");
		smallArmsSound.addSound("sounds/Gunshot/High/Short/D.mp3");
		smallArmsSound.addSound("sounds/Gunshot/High/Short/E.mp3");
		smallArmsSound.addSound("sounds/Gunshot/High/Short/F.mp3");
		smallArmsSound.addSound("sounds/Gunshot/High/Short/G.mp3");
		smallArmsSound.addSound("sounds/Gunshot/High/Short/H.mp3");
		smallArmsSound.addSound("sounds/Gunshot/Low/Short/A.mp3");
		smallArmsSound.addSound("sounds/Gunshot/Low/Short/C.mp3");
		smallArmsSound.addSound("sounds/Gunshot/Low/Short/D.mp3");
		smallArmsSound.addSound("sounds/Gunshot/Low/Short/E.mp3");
		smallArmsSound.addSound("sounds/Gunshot/Low/Short/F.mp3");
		smallArmsSound.addSound("sounds/Gunshot/Low/Short/G.mp3");
		smallArmsSound.addSound("sounds/Gunshot/Low/Short/H.mp3");
		smallArmsSound.addSound("sounds/Gunshot/Low/Long/A.mp3");
		smallArmsSound.addSound("sounds/Gunshot/Low/Long/C.mp3");
		smallArmsSound.addSound("sounds/Gunshot/Low/Long/D.mp3");
		smallArmsSound.addSound("sounds/Gunshot/Low/Long/E.mp3");
		smallArmsSound.addSound("sounds/Gunshot/Low/Long/F.mp3");
		smallArmsSound.addSound("sounds/Gunshot/Low/Long/G.mp3");
		smallArmsSound.addSound("sounds/Gunshot/Low/Long/H.mp3");

		brick = Gdx.audio.newSound(Gdx.files.internal("Misc/brick.mp3"));

		bigArmsSound = new RandomSound();
		bigArmsSound.addSound("sounds/cannonball.mp3");
		bigArmsSound.addSound("sounds/big/big7.mp3");


		dogSound = new RandomSound();
		dogSound.addSound("sounds/Dogs/dog_barking.mp3");
		dogSound.addSound("sounds/Dogs/dog1.mp3");
		dogSound.addSound("sounds/Dogs/dog2.mp3");
		dogSound.addSound("sounds/Dogs/dog3.mp3");
		dogSound.addSound("sounds/Dogs/dog4.mp3");
		dogSound.addSound("sounds/Dogs/dog5.mp3");
		dogSound.addSound("sounds/Dogs/dog6.mp3");

		helicopterSound = new RandomSound();
		helicopterSound.addSound("sounds/Gunshot/autocannon-20mm.mp3");
		helicopterSound.addSound("sounds/helicopter/helicopter1.mp3");
		helicopterSound.addSound("sounds/helicopter/helicopter2.mp3");
		helicopterSound.addSound("sounds/helicopter/helicopter3.mp3");
		helicopterSound.addSound("sounds/helicopter/helicopter4.mp3");
		helicopterSound.addSound("sounds/helicopter/helicopter5.mp3");

		tankSound = bigArmsSound;

		smallExplosionSound = new RandomSound();
		smallExplosionSound.addSound("sounds/small/small1.mp3");
		smallExplosionSound.addSound("sounds/small/small2.mp3");
		smallExplosionSound.addSound("sounds/small/small3.mp3");

		bigExplosionSound = new RandomSound();
		bigExplosionSound.addSound("sounds/boom.mp3");
		bigExplosionSound.addSound("sounds/big/big7.mp3");
		bigExplosionSound.addSound("sounds/big/big6.mp3");
		bigExplosionSound.addSound("sounds/big/big5.mp3");
		bigExplosionSound.addSound("sounds/big/big4.mp3");
		bigExplosionSound.addSound("sounds/big/big3.mp3");
		bigExplosionSound.addSound("sounds/big/big2.mp3");
		bigExplosionSound.addSound("sounds/big/big1.mp3");


		// speech bubble pop up sounds
		speechSounds = new RandomSound();
		speechSounds.addSound("sounds/coin/arcadecoin.mp3");
		speechSounds.addSound("sounds/coin/coin2.mp3");
		speechSounds.addSound("sounds/coin/coin6.mp3");



		radioChatter = new RandomSound();
		radioChatter.addSound("sounds/radio/speech1.mp3");
		radioChatter.addSound("sounds/radio/speech3.mp3");
		radioChatter.addSound("sounds/radio/speech7.mp3");

		// credits
		credits = new Texture("Misc/credits.png");
		extendedCredits = new Texture("Misc/extendedCredits.png");


		// load the sounds for the medieval mode

		archerSound = new RandomSound();
		archerSound.addSound("sounds/archer/archer1.mp3");
		archerSound.addSound("sounds/archer/archer2.mp3");
		catapultSound = new RandomSound();
		catapultSound.addSound("sounds/catapult/catapult1.mp3");
		catapultSound.addSound("sounds/catapult/catapult2.mp3");
		knightSound = new RandomSound();
		knightSound.addSound("sounds/knight/knight1.mp3");
		knightSound.addSound("sounds/knight/knight2.mp3");
		knightSound.addSound("sounds/knight/knight3.mp3");
		knightSound.addSound("sounds/knight/knight4.mp3");
		magicSound = new RandomSound();
		magicSound.addSound("sounds/magic/magic1.mp3");
		magicSound.addSound("sounds/magic/magic2.mp3");
		queenSound = new RandomSound();
		queenSound.addSound("sounds/sword/sword1.mp3");
		queenSound.addSound("sounds/sword/sword2.mp3");
		queenSound.addSound("sounds/sword/sword3.mp3");
		queenSound.addSound("sounds/sword/sword4.mp3");
		queenSound.addSound("sounds/sword/sword5.mp3");
		queenSound.addSound("sounds/sword/sword6.mp3");
		queenSound.addSound("sounds/sword/sword7.mp3");
		kingSound = queenSound;

		Gdx.app.log("BoomChess", "Loading Assets: Sounds finished");

		// load the background music into MusicPlaylist object --------------------------------------
		background_music = new MusicPlaylist();
		background_music.addSong("music/A Little R & R.mp3",
				"A Little R & R", "Bert Cole");
		background_music.addSong("music/SeeingDouble.wav", "Seeing Double",
				"Not Jam");
		background_music.addSong("music/First.wav", "First",
				"Steek Stook");
		background_music.addSong("music/SnesPenultimateLevel.wav", "SnesPenultimateLevel",
				"Steek Stook");
		background_music.addSong("music/03 - Beginnings.mp3",
				"Beginnings", "Oak Thielbar");
		background_music.addSong("music/KleptoLindaCavernsB.wav",
				"CavernsB", "not jam");

		/*
	       TODO not vibing with it anywhere
		background_music.addSong("music/24 Stray cat.mp3",
				"Stray cat", "Garo");
		   TODO not rocking enough
		background_music.addSong("music/06 Tonal Dissonance.mp3",
				"Tonal Dissonance", "Garo");
		background_music.addSong("music/36 Tonal Resonance.mp3",
				"Tonal Resonance", "Garo");
		   TODO not vibing
		background_music.addSong("music/Outside the Colosseum.mp3",
				"Outside the\n Colosseum", "Bert Cole\nbitbybitsound.com");
		background_music.addSong("music/Song Idee Chess.mp3",
				"Song Idee Chess", "Wambutz");
		background_music.addSong("music/Song 2.mp3", "Song 2",
				"Wambutz");
		   TODO too loud
		background_music.addSong("music/Boss Battle.mp3", "Boss Battle",
				"nostalgiac");
		   TODO eh
		background_music.addSong("music/TypeCastTheme.wav", "TYPE:CAST",
				"Not Jam");
		 */

		// load the menu music

		menu_music = new MusicPlaylist();
		menu_music.addSong("music/(LOOP-READY) Track 1 - Safe Zone No Intro.mp3",
				"Safe Zone", "HZSMITH");
		menu_music.addSong("music/03 Warm Breeze.mp3",
				"Warm Breeze", "rachel wang");
		menu_music.addSong("music/27 Coffee Break.mp3",
				"Coffee Break", "Garo");
		menu_music.addSong("music/Breakdown.mp3",
				"Breakdown", "Wambutz");
		menu_music.addSong("music/05 Thought Soup.mp3",
				"Thought Soup", "Garo");
		menu_music.addSong("music/epic-battle.mp3",
				"Epic Battle", "Bert Cole");
		/* TODO too pixelated
		menu_music.addSong("music/Start.mp3", "Start", "Smody");
		*/

		creditsMusic = new MusicPlaylist();
		creditsMusic.addSong("music/TouhouEuropeanQuartet.wav",
				"TouhouEuropeanQuartet", "Steek Stook");


		/*
			TODO vibes
		creditsMusic.addSong("music/credits/Hadokowa - Loading.. - 03 囡囡 (NanNan).mp3",
				"NanNan", "Hadokowa");
		   TODO elaborate on this Song cause wow
		creditsMusic.addSong("music/credits/Hadokowa - Loading.. - 04 買狗養狗 (Get a Dog,\n Pet a Dog).mp3",
		     	"Get a Dog, Pet a Dog", "Hadokowa");
		 */

		wrongMoveLogo = new Texture("Misc/WrongMove.png");

		Gdx.app.log("BoomChess", "Loading Assets: Music finished");

		// ---------------------------- universal Buttons for adding to stages

		Gdx.app.log("BoomChess", "Loading Assets: Audio Table and Buttons");

		muteButton = new TextButton("Mute", skin);

		skipButton = new TextButton("Skip", skin);

		// for the style out of the Commodore64 json file - REFERENCE:

		// com.badlogic.gdx.scenes.scene2d.ui.Button$ButtonStyle: {
		//	default: { up: button, down: button-down },
		//	music: { up: music-off, down: music, checked: music },
		//	sound: { up: sound-off, down: sound, checked: sound },
		//	tmp3le: { up: button, down: button-down, checked: button-down }

		// com.badlogic.gdx.scenes.scene2d.ui.Slider$SliderStyle: {
		//	default-vertical: { background: slider, knob: slider-knob },
		//	default-horizontal: { background: slider, knob: slider-knob }

		muteButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(soundVolume == 0){

					soundVolume = 0.25f;
					volume = 0.25f;

				} else {

					soundVolume = 0;
					volume = 0;
				}
				background_music.setVolume(volume);
				menu_music.setVolume(volume);
			}
		});

		skipButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (currentState != GameState.NOT_IN_GAME) {
					background_music.nextSong();
				} else {
					if(menu_music.isPlaying()) {
						menu_music.nextSong();
					} else {
						creditsMusic.nextSong();
					}
				}
			}
		});

		// their labels

		volumeLabel = new TextButton("Music-Volume: " + volume+"%", skin);
		volumeLabel.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(volume == 0){
					volume = 0.25f;
				} else if (volume <= 0.25f){
					volume = 0.5f;
				} else if (volume  <= 0.5f){
					volume = 0.75f;
				} else if (volume <= 0.75f){
					volume = 1f;
				} else if (volume <= 1f){
					volume = 0f;
				}

				if (currentState != GameState.NOT_IN_GAME) {
					background_music.setVolume(volume);
				} else {
					menu_music.setVolume(volume);
				}

				volumeLabel.setText("Music-Volume: " + volume+"%");

			}
		});

		soundVolumeLabel = new TextButton("Sound-Volume:" + soundVolume +"%", skin);
		soundVolumeLabel.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(soundVolume == 0){
					soundVolume = 0.25f;
				} else if (soundVolume <= 0.25f){
					soundVolume = 0.5f;
				} else if (soundVolume  <= 0.5f){
					soundVolume = 0.75f;
				} else if (soundVolume <= 0.75f){
					soundVolume = 1f;
				} else if (soundVolume <= 1f){
					soundVolume = 0f;
				}

				smallArmsSound.setVolume(soundVolume);
				bigArmsSound.setVolume(soundVolume);
				dogSound.setVolume(soundVolume);
				helicopterSound.setVolume(soundVolume);
				tankSound.setVolume(soundVolume);
				smallExplosionSound.setVolume(soundVolume);
				bigExplosionSound.setVolume(soundVolume);
				archerSound.setVolume(soundVolume);
				catapultSound.setVolume(soundVolume);
				knightSound.setVolume(soundVolume);
				magicSound.setVolume(soundVolume);
				queenSound.setVolume(soundVolume);
				kingSound.setVolume(soundVolume);
				speechSounds.setVolume(soundVolume);
				radioChatter.setVolume(soundVolume);

				soundVolumeLabel.setText("Sound-Volume:" + soundVolume + "%");
			}
		});

		Gdx.app.log("BoomChess", "Loading Assets: Audio Table and Buttons finished");

		// for the dotted Line when damage occurs -----------------------------------------------

		shapeRenderer = new ShapeRenderer();

		// -----------------------------------------------------------------------------
		/*
		 * creation of the stages for the menu - this allows the Scene2D.ui to be used for quick swapping of screens
		 * and the usage of the buttons/ui-elements/so-called actors and child actors to be used
		 * stages will be the way we display all menus and the game itself
		 */

		// skin (look) of the progress bar via a prearranged json file
		progressBarSkin = new Skin(Gdx.files.internal("progressBarSkin/neon-ui.json"));


		// creation of empty Board.validMoveTiles for null-pointer exception avoidance
		Board.validMoveTiles = new ArrayList<>();

		// create a new MapStage Object for the variable mapStage

		createMapStage();

		// set number obstacle for initialization to 3
		numberObstacle = 3;

		// intialise the possibleMoveOverlay
		possibleMoveOverlay = new Stage();

		// sets the InputProcessor, a Gdx Tool for handling userinput, to the currentStage, since its the only
		// stage that needs input. Use multiplexer if multiple needed, then change back to single InputProcess.
		Gdx.input.setInputProcessor(currentStage);

		// initialise the botMove, a simulator for the schein-animation of the bot soldier along the white line
		botMove = new moveBotTile();

		// our damage dealing scenarios
		actionSequence = new AttackSequence();

		sequenceRunning = false;

		// help Stage

		Image helpImage = new Image(helpTexture);
		helpImage.setSize(tileSize*12.5f, tileSize*8);
		helpImage.setPosition(tileSize*4.1f, tileSize*1.9f);
		helpStage.addActor(helpImage);

		clipBoard = new Texture(Gdx.files.internal("Misc/clipboard.png"));
		createInGameOptionStages();

		// loading all animation
		loadAllAnimation();

		Gdx.app.log("BoomChess", "Loading Assets: Finished");
		// leaves the loading screen
		assetsLoaded = true;
	}

	private void loadAllAnimation() {

		Gdx.app.log("BoomChess", "Loading Assets: Animations");

		Soldier[] allSoldiers = new Soldier[14];

		// load all possible soldiers into the array
		allSoldiers[0] = new Infantry("red");
		allSoldiers[1] = new Commando("red");
		allSoldiers[2] = new General("red");
		allSoldiers[3] = new Wardog("red");
		allSoldiers[4] = new Helicopter("red");
		allSoldiers[5] = new Tank("red");
		allSoldiers[6] = new Artillery("red");
		allSoldiers[7] = new Infantry("green");

		allSoldiers[8] = new Commando("green");
		allSoldiers[9] = new General("green");

		allSoldiers[10] = new Wardog("green");
		allSoldiers[11] = new Helicopter("green");
		allSoldiers[12] = new Tank("green");
		allSoldiers[13] = new Artillery("green");

		boolean tmp = isColourChanged;

		boolean tmpJeep = dogIsJeep;

		// load all soldierAnimations with the soldier objects
		redInfantryAnimation = new soldierAnimation(allSoldiers[0]);
		redInfantryAnimation.setSize(tileSize, tileSize);
		redCommandoAnimation = new soldierAnimation(allSoldiers[1]);
		redCommandoAnimation.setSize(tileSize, tileSize);
		redGeneralAnimation = new soldierAnimation(allSoldiers[2]);
		redGeneralAnimation.setSize(tileSize, tileSize);

		dogIsJeep = false;
		redWardogAnimation = new soldierAnimation(allSoldiers[3]);
		redWardogAnimation.setSize(tileSize, tileSize);

		dogIsJeep = true;
		redJeepAnimation = new soldierAnimation(allSoldiers[3]);
		redJeepAnimation.setSize(tileSize, tileSize);

		redHelicopterAnimation = new soldierAnimation(allSoldiers[4]);
		redHelicopterAnimation.setSize(tileSize, tileSize);
		redTankAnimation = new soldierAnimation(allSoldiers[5]);
		redTankAnimation.setSize(tileSize, tileSize);
		redArtilleryAnimation = new soldierAnimation(allSoldiers[6]);
		redArtilleryAnimation.setSize(tileSize, tileSize);

		isColourChanged = false;
		greenInfantryAnimation = new soldierAnimation(allSoldiers[7]);
		greenInfantryAnimation.setSize(tileSize, tileSize);
		greenCommandoAnimation = new soldierAnimation(allSoldiers[8]);
		greenCommandoAnimation.setSize(tileSize, tileSize);
		greenGeneralAnimation = new soldierAnimation(allSoldiers[9]);
		greenGeneralAnimation.setSize(tileSize, tileSize);

		dogIsJeep = false;
		greenWardogAnimation = new soldierAnimation(allSoldiers[10]);
		greenWardogAnimation.setSize(tileSize, tileSize);

		dogIsJeep = true;
		greenJeepAnimation = new soldierAnimation(allSoldiers[10]);
		greenJeepAnimation.setSize(tileSize, tileSize);

		greenHelicopterAnimation = new soldierAnimation(allSoldiers[11]);
		greenHelicopterAnimation.setSize(tileSize, tileSize);
		greenTankAnimation = new soldierAnimation(allSoldiers[12]);
		greenTankAnimation.setSize(tileSize, tileSize);
		greenArtilleryAnimation = new soldierAnimation(allSoldiers[13]);
		greenArtilleryAnimation.setSize(tileSize, tileSize);

		isColourChanged = true;
		blueInfantryAnimation = new soldierAnimation(allSoldiers[7]);
		blueInfantryAnimation.setSize(tileSize, tileSize);

		blueGeneralAnimation = new soldierAnimation(allSoldiers[9]);
		blueGeneralAnimation.setSize(tileSize, tileSize);
		blueCommandoAnimation = new soldierAnimation(allSoldiers[8]);
		blueCommandoAnimation.setSize(tileSize, tileSize);

		dogIsJeep = false;
		blueWardogAnimation = new soldierAnimation(allSoldiers[10]);
		blueWardogAnimation.setSize(tileSize, tileSize);

		dogIsJeep = true;
		blueJeepAnimation = new soldierAnimation(allSoldiers[10]);
		blueJeepAnimation.setSize(tileSize, tileSize);

		blueHelicopterAnimation = new soldierAnimation(allSoldiers[11]);
		blueHelicopterAnimation.setSize(tileSize, tileSize);
		blueTankAnimation = new soldierAnimation(allSoldiers[12]);
		blueTankAnimation.setSize(tileSize, tileSize);
		blueArtilleryAnimation = new soldierAnimation(allSoldiers[13]);
		blueArtilleryAnimation.setSize(tileSize, tileSize);

		isColourChanged = tmp;

		Gdx.app.log("BoomChess", "Loading Assets: Animations finished");
	}

	public static void createInGameOptionStages() {
		// clear the stages
		inGamOptStage.clear();

		Table table = new Table();

		table.setSize(tileSize*3, tileSize*5);
		// bottom right the table in the parent container

		// button to change bot difficulty
		// text that displays a text saying "Bot Difficulty"
		final TextButton botDifficultyText = new TextButton("Bot: " + botDifficulty, skin);
		botDifficultyText.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				switch (botDifficulty) {
					case "easy":
						botDifficulty = "medium";
						break;
					case "medium":
						botDifficulty = "hard";
						break;
					case "hard":
						botDifficulty = "easy";
						break;
				}
				createInGameOptionStages();
			}
		});
		table.add(botDifficultyText).padBottom(tileSize/8).row();

		// Button to change 1.Player Colour to blue
		TextButton changeColourButton = new TextButton("Switch 1P Skin", skin);
		changeColourButton.align(Align.bottomRight);
		changeColourButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				isColourChanged = !isColourChanged;
				nonInvasiveReRender = true;
				currentStage = GameStage.createGameStage(isBotMatch);
				addAudioTable();
				createInGameOptionStages();
			}
		});
		table.add(changeColourButton).padBottom(tileSize/8).row();

		// button for turning the arm on and off
		TextButton armButton = new TextButton("BotArm: " + showArm, skin);
		armButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				showArm = !showArm;
				createInGameOptionStages();
			}
		});
		table.add(armButton).padBottom(tileSize/8).row();

		// bot moving speed
		// button for changing the botMovingSpeed
		// 0 is very fast
		// 1 is fast
		// 2 is normal
		String currentSpeed;
		switch (BoomChess.botMovingSpeed) {
			case 0:
				currentSpeed = "Very Fast";
				break;
			case 1:
				currentSpeed = "Fast";
				break;
			default:
				currentSpeed = "Normal";
				break;
		}
		TextButton speedButton = new TextButton("Bot-Speed: " + currentSpeed, skin);
		speedButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				switch (BoomChess.botMovingSpeed) {
					case 0:
						BoomChess.botMovingSpeed = 1;
						break;
					case 1:
						BoomChess.botMovingSpeed = 2;
						break;
					case 2:
						BoomChess.botMovingSpeed = 0;
						break;
				}
				BoomChess.createInGameOptionStages();
			}
		});
		table.add(speedButton).padBottom(tileSize/8).row();

		// attack circle show
		// button for turning the attack circles on and off
		TextButton attackCircleButton = new TextButton("Attack Circles: " + showAttackCircle, skin);
		attackCircleButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				nonInvasiveReRender = true;
				showAttackCircle = !showAttackCircle;
				createInGameOptionStages();
			}
		});
		table.add(attackCircleButton).padBottom(tileSize/8).row();

		// button for the show damage specials
		TextButton showDamageButton = new TextButton("Show Special-DMG: "
				+ showPossibleDamage, skin);
		showDamageButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				showPossibleDamage = !showPossibleDamage;
				createInGameOptionStages();
			}
		});
		table.add(showDamageButton).padBottom(tileSize/8).row();


		// button to change the beep mode of the speech bubbles isBeepMode true or false
		String currentBeepMode;
		if(isBeepMode){
			currentBeepMode = "Arcade";
		}
		else{
			currentBeepMode = "Radio";
		}
		TextButton beepModeButton = new TextButton("Exclamations: " + currentBeepMode, skin);
		beepModeButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeListener.ChangeEvent event, Actor actor) {
				isBeepMode = !isBeepMode;
				createInGameOptionStages();
			}
		});
		table.add(beepModeButton).padBottom(tileSize/8).row();

		// dog is car button
		TextButton dogIsCarButton = new TextButton("Bishop is a Jeep: " + dogIsJeep, skin);
		dogIsCarButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				nonInvasiveReRender = true;
				dogIsJeep = !dogIsJeep;
				createInGameOptionStages();
			}
		});
		table.add(dogIsCarButton).padBottom(tileSize/8).row();

		// change Map
		TextButton changeMapButton = new TextButton("Change Map", skin);
		changeMapButton.align(Align.bottomRight);
		changeMapButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				BoomChess.createMapStage();
				createInGameOptionStages();
			}
		});
		table.row().padBottom(tileSize/8);

		// button for changing if animations are used
		TextButton animationButton = new TextButton("Animations: " + isAnimated, skin);
		animationButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				nonInvasiveReRender = true;
				isAnimated = !isAnimated;
				createInGameOptionStages();
			}
		});

		table.add(changeMapButton);
		table.row().padBottom(tileSize/8);

		table.add(volumeLabel);
		table.row().padBottom(tileSize/8);

		table.add(soundVolumeLabel);
		table.row().padBottom(tileSize/8);

		// Exit to Main Menu button to return to the main menu
		TextButton menuButton = new TextButton("Return to Main Menu", skin);
		menuButton.align(Align.bottomRight);
		menuButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				deathExplosionStage.clear();
				botMovingStage.clear();
				dottedLineStage.clear();
				gameEndStage.clear();
				speechBubbleStage.clear();
				crossOfDeathStage.clear();



				actionSequence = new AttackSequence();

				BoomChess.currentState = BoomChess.GameState.NOT_IN_GAME;

				inGame = false;
				showHelp = false;

				showInGameOptions = false;

				createMainMenuStage();
			}
		});
		table.add(menuButton).padBottom(tileSize/8).row();

		// button to turn off boolean showInGameOptions and to set the input processor to currentStage
		TextButton closeButton = new TextButton("Close Options", skin);
		closeButton.align(Align.bottomRight);
		closeButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				showInGameOptions = false;
				Gdx.input.setInputProcessor(currentStage);
			}
		});
		table.add(closeButton).padBottom(tileSize/8).row();


		// add a clipboard Image centralised on the screen
		Image clipBoardImage = new Image(clipBoard);
		clipBoardImage.setSize(tileSize*10, tileSize*14);
		// set size
		clipBoardImage.setZIndex(1);
		inGamOptStage.addActor(clipBoardImage);

		clipBoardImage.setPosition((float) Gdx.graphics.getWidth() - clipBoardImage.getWidth(),
				(float) Gdx.graphics.getHeight() - clipBoardImage.getHeight()+tileSize*2);

		// set position to middle of the screen
		table.setPosition((float) Gdx.graphics.getWidth() - clipBoardImage.getWidth()
						+ clipBoardImage.getWidth()/3,
				(float) Gdx.graphics.getHeight() - clipBoardImage.getHeight()
						+ clipBoardImage.getHeight()/2);

		// add the tables to their stages
		table.setZIndex(2);
		inGamOptStage.addActor(table);

	}

	@Override
	public void resize(int width, int height) {
		/*
		 * fits the needed values when a resize has happened, when a resize has happened
		 */
		mapStage.getViewport().update(width, height, true);
		moveLogoStage.getViewport().update(width, height, true);
		possibleMoveOverlay.getViewport().update(width, height, true);
		currentStage.getViewport().update(width, height, true);
		dottedLineStage.getViewport().update(width, height, true);
		deathExplosionStage.getViewport().update(width, height, true);
		gameEndStage.getViewport().update(width, height, true);
		botMovingStage.getViewport().update(width, height, true);
	}

	private void calculateDamage(String teamColor) {
		/*
		* method that goes through each tile of the board and if SoldierTeam is teamColor,
		*  lets it attack the surrounding tiles
		 */

		Soldier[][] gameBoard = Board.getGameBoard();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 8; j++) {
				Soldier soldier = gameBoard[i][j];
				// for checking if the piece is an artillery -> call checkSurroundingsArtillery
				if (soldier != null && soldier.getTeamColor().equals(teamColor)) {
					Damage.checkSurroundings(i, j);
				}
			}
		}
		// add a attack incoming bubble from SpeechBubbles
		SpeechBubbles.createSpeechAttackIncoming();
		// start the actionSequence, since Damage has checked all of the board
		actionSequence.startSequences();
	}

	public static void switchTurn(GameState state) {
		/*
		* switchTurn switches the public GameStage enum between RED and GREEN
		 */
		if (state == GameState.RED_TURN) {
			currentState = GameState.GREEN_TURN;
		} else {
			currentState = GameState.RED_TURN;
		}
		reRenderGame();
	}

	@Override
	public void dispose() {
		/*
		* dispose is used when the game is exited, disposes of all assets
		 */
		batch.dispose();
		skin.dispose();
		currentStage.dispose();
		possibleMoveOverlay.dispose();
		moveLogoStage.dispose();
		shapeRenderer.dispose();
		dottedLineStage.dispose();
		deathExplosionStage.dispose();
		gameEndStage.dispose();
		speechBubbleStage.dispose();
		botMovingStage.dispose();
		loadingStage.dispose();

		// dispose of all assets --- Textures do not natively get rubbish canned by Javas inbuilt collector
		// Images do tho!
		 redTank.dispose();
		 redHelicopter.dispose();
		 redWardog.dispose();
		 redGeneral.dispose();
		 redCommando.dispose();
		 redInfantry.dispose();
		 greenTank.dispose();
		 greenHelicopter.dispose();
		 greenWardog.dispose();
		 greenGeneral.dispose();
		 greenCommando.dispose();
		 greenInfantry.dispose();
		 xMarker.dispose();
		 empty.dispose();
		 hill.dispose();

		 // dispose of all sound objects
		 smallArmsSound.dispose();
		 bigArmsSound.dispose();
		 dogSound.dispose();
		 helicopterSound.dispose();
		 tankSound.dispose();
		 smallExplosionSound.dispose();
		 bigExplosionSound.dispose();
		 archerSound.dispose();
		 catapultSound.dispose();
		 knightSound.dispose();
		 magicSound.dispose();
		 queenSound.dispose();
		 kingSound.dispose();

		 // dispose of all music
		 background_music.dispose();
		 menu_music.dispose();
		 creditsMusic.dispose();

		// dispose of all numbers
		redOne.dispose();
		greenOne.dispose();
		yellowOne.dispose();
		orangeOne.dispose();

		redTwo.dispose();
		greenTwo.dispose();
		yellowTwo.dispose();
		orangeTwo.dispose();

		redThree.dispose();
		greenThree.dispose();
		yellowThree.dispose();
		orangeThree.dispose();

		redFour.dispose();
		greenFour.dispose();
		yellowFour.dispose();
		orangeFour.dispose();

		redFive.dispose();
		greenFive.dispose();
		yellowFive.dispose();
		orangeFive.dispose();

		redSix.dispose();
		greenSix.dispose();
		yellowSix.dispose();
		orangeSix.dispose();

		redSeven.dispose();
		greenSeven.dispose();
		yellowSeven.dispose();
		orangeSeven.dispose();

		redEight.dispose();
		greenEight.dispose();
		yellowEight.dispose();
		orangeEight.dispose();

		redNine.dispose();
		greenNine.dispose();
		yellowNine.dispose();
		orangeNine.dispose();

		redZero.dispose();
		greenZero.dispose();
		yellowZero.dispose();
		orangeZero.dispose();

		// animations
		redInfantryAnimation.dispose();
		redCommandoAnimation.dispose();
		redGeneralAnimation.dispose();
		redWardogAnimation.dispose();
		redHelicopterAnimation.dispose();
		redTankAnimation.dispose();
		redArtilleryAnimation.dispose();

		blueInfantryAnimation.dispose();
		blueCommandoAnimation.dispose();
		blueGeneralAnimation.dispose();
		blueWardogAnimation.dispose();
		blueHelicopterAnimation.dispose();
		blueTankAnimation.dispose();
		blueArtilleryAnimation.dispose();

		greenInfantryAnimation.dispose();
		greenCommandoAnimation.dispose();
		greenGeneralAnimation.dispose();
		greenWardogAnimation.dispose();
		greenHelicopterAnimation.dispose();
		greenTankAnimation.dispose();
		greenArtilleryAnimation.dispose();

	}

	public static void switchToStage(Stage newStage) {
		/*
		* this method removes the currentStage and loads a new one
		* used generally in the Stage classes at the end to load the created Stages
		* or combined with a return Stage createStage method
		 */

		if (currentStage != null){
			currentStage.clear();}
		currentStage = newStage;
		addAudioTable();
		Gdx.input.setInputProcessor(currentStage);
	}

	private static void addAudioTable(){
		/*
		* method for adding the audio table to the currentStage
		 */
		// --------- Audio Table ------------
		audioTable = new Table();
		audioTable.setPosition(tileSize*2, tileSize*2);

		// Buttons
		audioTable.row().padBottom(tileSize/4);
		audioTable.add(muteButton);
		audioTable.row().padBottom(tileSize/4);
		audioTable.add(skipButton);
		audioTable.row().padBottom(tileSize/4);

		currentStage.addActor(audioTable);
	}

	private void updateMoveLogo() {
		/*
		* method for updating the moveLogoStage with the correct logo
		 */

		// if not in game, add an actor depending on the current state

		Table currentMover = new Table();

		float width = tileSize * 3;
		float height = tileSize * 2;
		currentMover.setSize(width, height);

		// Position at upper left corner
		float xPosition = tileSize / 3; // Left edge of the screen
		float yPosition = Gdx.graphics.getHeight() - height;
		// Subtract height of the mover, positioning it at the top
		currentMover.setPosition(xPosition, yPosition);


		boolean addActor = true;
		boolean goIntoTeamLogos = false;
		if(sequenceRunning){
			if(actionSequence.isListEmpty()){
				goIntoTeamLogos = true;
			} else {
				// if the sequence is running and there is stuff in the sequence,
				// add an actor that says action is running
				currentMover.addActor(actionOngoing);
			}
		} else { goIntoTeamLogos = true; }

		if(goIntoTeamLogos){
			if (currentState == GameState.RED_TURN) {
				currentMover.addActor(redMove);
			} else if (currentState == GameState.GREEN_TURN) {
				if (!isColourChanged) {
					currentMover.addActor(greenMove);
				} else {
					currentMover.addActor(blueMove);
				}
			} else {
				// if the currentState is not in game, remove the currentMover
				currentMover.remove();
				addActor = false;
			}
		}

		if (addActor) {
			moveLogoStage.addActor(currentMover);
		}

	}

	public static void reRenderGame(){
		/*
		* used to refresh the gameStage if a action has happened that edited the gameBoard.
		 */
		if(currentState == GameState.NOT_IN_GAME || !(inGame)) {
			return;
		}

		// remove all active soldierAnimations
		GameStage.clearAllActiveSoldierAnimations();

		// disposing all inhabitants of the currentStage
		currentStage.clear();

		switchToStage(createGameStage(isBotMatch));
	}

	public static void createMainMenuStage() {
		/*
		* method for creating the stage for the main menu
		 */

		//stop background music
		if(background_music.isPlaying()) {
			background_music.stop();
		}
		if(creditsMusic.isPlaying()) {
			creditsMusic.stop();
		}

		inTutorial = false;
		switchToStage(MenuStage.initializeUI());
		gameEndStage.clear();

		if(!(menu_music.isPlaying())) {
			// start menu music
			menu_music.play();
		}
	}

	public static void createOptionsStage() {
		/*
		* method for creating the stage for the options display
		 */
		switchToStage(OptionsStage.initalizeUI());
	}

	public static void createCreditsStage() {
		/*
		* method for creating the stage for the credits display
		 */
		// stop menu music
		menu_music.stop();
		// start credits music
		if(!(creditsMusic.isPlaying())) {
			creditsMusic.play();
		}
		switchToStage(CreditsStage.initializeUI());
	}

	public static void createGameEndStage (String winnerTeamColour){
		/*
		* method for creating the stage for the game end ON TOP of the gameStage
		* (changing InputProcessor to stop Game Progress)
		 */
		gameEndStage = GameEndStage.initializeUI(winnerTeamColour);

	}

	public static void createChallengeStage() {
		/*
		* method for creating the stage for the challenge display
		 */
		switchToStage(ChallengeStage.initializeUI());
	}

	public static void createMapStage() {
		/*
		* method for creating the stage for the map that is rendered in variable mapStage
		 */
		// boolean false, since we are creating a fully new instance and are not resizing
		mapStage = MapStage.initializeUI(false);
	}

	public static void setAllowedTiles (ArrayList<Coordinates> validMoveTiles) {
		/*
		* method for setting the ArrayList of allowed tiles to move to and a method for clearing it to nothing
		 */

		Board.setValidMoveTiles(validMoveTiles);
		// renew the whole Stage inside possibleMovesOverlay to clear the old tiles
		possibleMoveOverlay.clear();	// clear the old tiles

		// start of overlay creation, works similar to the tileWidget creation

		Table root = new Table();

		root.setSize(tileSize*9, tileSize*8);
		root.center(); // Center the Overlay exactly above the gameBoard in the parent container (stage)
		// refine the position of the root Table, since the orthoCamera is centered on a screen that may change size
		root.setPosition((Gdx.graphics.getWidth() - root.getWidth()) / 2f,
				(Gdx.graphics.getHeight() - root.getHeight()) / 2f);

		// for the size of the tiles
		int numRows = 8;
		int numColumns = 9;

		for (int i = 0; i < numRows; i++) {
			// add a new stage Table row after each row of the gameBoard
			root.row();
			for (int j = 0; j < numColumns; j++) {
				// create a new box like widget at each position of the board and add it to the root table
				// it is 80x80 pixels, holds the image of the piece at that position and is movable to other positions
				// switch statement to check which type of piece it is

				// Empty box (no image) or xMarker (red X) if it is a valid move
				// as coordinates generation in touch.dragged-Event
				boolean xMarkerAdded = false;
				for (Coordinates validMoveTile : validMoveTiles) {
					if (validMoveTile.getX() == j && validMoveTile.getY() == i) {
						// Apply a red X
						root.add(new Image(xMarker)).size(tileSize);
						xMarkerAdded = true;
						break;
					}
				}
				if (!xMarkerAdded) {
					root.add(new Image(empty)).size(tileSize);
				}
			}
		}
		// add the root table to the stage
		possibleMoveOverlay.addActor(root);
		renderOverlay = true;
	}

	public static void clearAllowedTiles () {
		/*
		* method for turning off the renderOverlay (boolean value changed and not display next render
		 */
		renderOverlay = false;
	}

	public static Coordinates calculateTileByPX(int pxCoordinateX, int pxCoordinateY) {
		/*
		* method for calculating the tile coordinates by pixel coordinates
		 */

		// BUGFIX! In LibGDX, the origin of the screen is the top left! i traditional, its bottom left!

		// method for checking which tile a pxCoordinateX and pxCoordinateY is in, creating the coordinates object
		// of the respective tile and returning it
		Coordinates iconTileCoordinate = new Coordinates();

		// we calculate this by first getting the screen width and height
		int screenWidth = Gdx.graphics.getWidth();
		int screenHeight = Gdx.graphics.getHeight();

		// we then calculate the upper left corner of the gameBoard by subtracting the screenWidth and screenHeight by
		// the gameBoard width and height and then dividing it by 2a
		int upperLeftCornerX = (screenWidth - (int) tileSize*9) / 2;
		int upperLeftCornerY = (screenHeight - (int) tileSize*8) / 2;

		// Adjust the pxCoordinate by adding 1 to ensure boundary pixels are correctly classified.
		int adjustedPxX = pxCoordinateX + 1;
		int adjustedPxY = pxCoordinateY + 1;

		// Adjust the Y-coordinate to reflect the difference in coordinate systems ( see BUGFIX )
		adjustedPxY = screenHeight - adjustedPxY;

		// we then calculate the tileX and tileY by subtracting the upperLeftCornerX and upperLeftCornerY from the
		// adjustedPxX and adjustedPxY and dividing it by the tile size
		int tileX = (adjustedPxX - upperLeftCornerX) / (int) tileSize;
		int tileY = (adjustedPxY - upperLeftCornerY) / (int) tileSize;

		// we then set the tileX and tileY in the iconTileCoordinate object
		iconTileCoordinate.setX(tileX);
		iconTileCoordinate.setY(tileY);

		return iconTileCoordinate;
	}

	public static Coordinates calculateTileByPXNonGDX(int pxCoordinateX, int pxCoordinateY) {
		/*
		 * method for calculating the tile coordinates by pixel coordinates,
		 * literally mirrored tile to the calculateTileByPX method
		 */

		// method for checking which tile a pxCoordinateX and pxCoordinateY is in, creating the coordinates object
		// of the respective tile and returning it
		new Coordinates();
		Coordinates iconTileCoordinate;

		iconTileCoordinate = calculateTileByPX(pxCoordinateX, pxCoordinateY);

		// Invert the tilePositionY for libGDX coordinate System compliance
		int invertedTilePositionY = 7 - iconTileCoordinate.getY();

		Coordinates returnCoords = new Coordinates();
		returnCoords.setCoordinates(iconTileCoordinate.getX(), invertedTilePositionY);

		return returnCoords;
	}

	// --------------------------------------------------------------------------------------------------------------
	// ------------------------------------------- DAMAGE ANIMATION METHODS -----------------------------------------
	// --------------------------------------------------------------------------------------------------------------

	public static Coordinates calculatePXbyTile(int tilePositionX, int tilePositionY){
		/*
		* in this first method, the pixel coordinates of the center of a tile is calculated
		* this will be used afterwards to create a dotted Line Animation from an attacker to a defender tile
		 */

		Coordinates pxCoords = new Coordinates();

		// we calculate the board dimensions based on tile dimensions and the number of tiles
		float boardWidth = 9 * tileSize;
		float boardHeight = 8 * tileSize;

		// we use the same way as in calculateTileByPX to get the Screen Parameters
		int screenWidth = Gdx.graphics.getWidth();
		int screenHeight = Gdx.graphics.getHeight();

		// we calculate the top left corner pixel coordinate of the board, which we use for the calculation
		float boardStartX = (screenWidth - boardWidth) / 2;
		float boardStartY = (screenHeight - boardHeight) / 2;

		// Invert the tilePositionY for libGDX coordinate System compliance
		int invertedTilePositionY = 7 - tilePositionY;

		// we calculate the pixel coordinates of any tile
		float tilePixelX = boardStartX + tilePositionX * tileSize + (tileSize / 2);
		float tilePixelY = boardStartY + invertedTilePositionY * tileSize + ( tileSize / 2);



		// we set the calculated px coords into a Coordinates object
		pxCoords.setCoordinates((int) tilePixelX, (int) tilePixelY);

		return pxCoords;
	}

	// for adding a DottedLine to the dottedLineStage
	public static void addDottedLine(int x1, int y1, int x2, int y2, boolean isDamage){
		/*
		* uses a beginning coordinate and a end coordinate to create an Actor and add it to the LineStage
		 */
		DottedLineActor lineActor = new DottedLineActor(x1, y1, x2, y2, shapeRenderer, isDamage,
				currentState, isColourChanged);
		lineActor.setZIndex(1);

		if(isDamage) { // if it is a damage dotted line and not a white bot move line
			actionSequence.addSequence(lineActor);
		} else {
			dottedLineStage.addActor(lineActor);
		}
	}

	// --------------------------------------------------------------------------------------------------------------
	// ------------------------------------------- deathAnimation METHODS -------------------------------------------
	// --------------------------------------------------------------------------------------------------------------
	public static Coordinates calculatePXbyTileNonGDX(int tilePositionX, int tilePositionY){
		/*
		* calculates the most middle pixel of a tile of the chessBoard
		 */
		Coordinates pxCoords = new Coordinates();

		// we calculate the board dimensions based on tile dimensions and the number of tiles
		float boardWidth = 9 * tileSize;
		float boardHeight = 8 * tileSize;

		// we use the same way as in calculateTileByPX to get the Screen Parameters
		int screenWidth = Gdx.graphics.getWidth();
		int screenHeight = Gdx.graphics.getHeight();

		// we calculate the top left corner pixel coordinate of the board, which we use for the calculation
		float boardStartX = (screenWidth - boardWidth) / 2;
		float boardStartY = (screenHeight - boardHeight) / 2;

		// Invert the tilePositionY for libGDX coordinate System compliance
		int invertedTilePositionY = 7 - tilePositionY;

		// we calculate the pixel coordinates of any tile
		float tilePixelX = boardStartX + tilePositionX * tileSize;
		float tilePixelY = boardStartY + invertedTilePositionY * tileSize;



		// we set the calculated px coords into a Coordinates object
		pxCoords.setCoordinates((int) tilePixelX, (int) tilePixelY);

		return pxCoords;
	}
	public static void addDeathAnimation(int x, int y) {
		/*
		* adds the DeathAnimation to the deathExplosionStage, adds this action to log
		 */
		DeathExplosionActor deathActor = new DeathExplosionActor(x, y);
		deathActor.setZIndex(1);
		actionSequence.addSequence(deathActor);
	}

	public static void addDamageNumber(int x, int y, int damage) {
		/*
		 * adds DamageNumber to actionSequence
		 */
		DamageNumber damageNumbActor = new DamageNumber(x, y, damage);
		damageNumbActor.setZIndex(1);
		actionSequence.addSequence(damageNumbActor);
	}

	public static void addHitMarker(int x, int y){
		/*
		 * adds the DeathAnimation to the deathExplosionStage, adds this action to log
		 */
		HitMarkerActor hitActor = new HitMarkerActor(x, y);
		hitActor.setZIndex(1);
		actionSequence.addSequence(hitActor);
	}

	// ------------------- methods for setting a to be displayed as empty variable
	public static void resetEmptyCoordinate() {
		/*

		 */
		useEmpty = false;
	}

	public static void setEmptyCoordinate(int startX, int startY) {
		useEmpty = true;
		emptyX = startX;
		emptyY = startY;
	}

	// finds the coordinates of a general and gives out as px coordinate object
	public static Coordinates findGeneral(boolean isRedGeneral) {
		/*
		* method for finding the general of a team and returning its coordinates
		 */

		new Coordinates();
		Coordinates generalCoordinates;
		Soldier[][] gameBoard = Board.getGameBoard();

		String teamColor;
		if (isRedGeneral) {
			teamColor = "red";
		} else {
			teamColor = "green";
		}

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 8; j++) {

				Soldier soldier = gameBoard[i][j];
				if (soldier instanceof General && soldier.getTeamColor().equals(teamColor)) {
					generalCoordinates = calculatePXbyTile(i, j);
					return generalCoordinates;
				}
			}
		}
		return null;
	}

	//------------------------------------------------------------------
	// ------------------- methods for adding a crossOfDeath to the crossOfDeathStage
	//------------------------------------------------------------------

	public static void addCrossOfDeath(int x, int y) {
		Image cross = new Image(BoomChess.crossOfDeathTexture);
		Coordinates coordinates = BoomChess.calculatePXbyTile(x, y);
		cross.setPosition((float) coordinates.getX() - (tileSize / 2), (float) coordinates.getY() - (tileSize / 2));
		cross.setSize(tileSize, tileSize);
		BoomChess.crossOfDeathStage.addActor(cross);
	}

	// --------------------------------------------------------------------
	// ----------------- methods for getting the special relations of a piece
	// --------------------------------------------------------------------

	public static Soldier getSpecialBoniSoldier(Soldier soldier) {
		Soldier specialBoy;

		if(soldier instanceof Tank){
			specialBoy = new Infantry("invalid");
		} else if(soldier instanceof Helicopter){
			specialBoy = new Tank("invalid");
		} else if(soldier instanceof Wardog){
			specialBoy = new Infantry("invalid");
		} else if(soldier instanceof Commando){
			specialBoy = new Tank("invalid");
		} else if(soldier instanceof Infantry){
			specialBoy = new Helicopter("invalid");
		} else if(soldier instanceof Artillery){
			specialBoy = new Infantry("invalid");
		} else {
			specialBoy = null;
		}

		return specialBoy;
	}

	public static int getSpecialBoniValue(Soldier soldier) {
		int specialValue;

		if(soldier instanceof Tank){
			specialValue = 5;
		} else if(soldier instanceof Helicopter){
			specialValue = 5;
		} else if(soldier instanceof Wardog){
			specialValue = 5;
		} else if(soldier instanceof Commando){
			specialValue = 10;
		} else if(soldier instanceof Infantry){
			specialValue = 5;
		} else if(soldier instanceof Artillery){
			specialValue = 5;
		} else {
			specialValue = 0;
		}

		return specialValue;
	}

	public static Soldier getSpecialMalusSoldier(Soldier soldier){
		Soldier specialBoy = null;

		if(soldier instanceof Tank){
			specialBoy = new Wardog("invalid");
		} else if(soldier instanceof Wardog){
			specialBoy = new Tank("invalid");
		}

		return specialBoy;
	}

	public static int getSpecialMalusValue(Soldier soldier){
		int specialValue;

		if(soldier instanceof Tank){
			specialValue = 5;
		} else if(soldier instanceof Wardog){
			specialValue = 5;
		} else {
			specialValue = 0;
		}

		return specialValue;
	}
}