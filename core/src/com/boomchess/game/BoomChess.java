package com.boomchess.game;

import static com.boomchess.game.frontend.stage.GameStage.createGameStage;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
import com.boomchess.game.backend.subsoldier.General;
import com.boomchess.game.frontend.actor.AttackSequence;
import com.boomchess.game.frontend.actor.DeathExplosionActor;
import com.boomchess.game.frontend.actor.DottedLineActor;
import com.boomchess.game.frontend.actor.HitMarkerActor;
import com.boomchess.game.frontend.actor.moveBotTile;
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
	public static Texture redArtillery;
	public static Texture blueArtillery;
	public static Texture blueInfantry;
	public static Texture blueCommando;
	public static Texture blueGeneral;
	public static Texture blueWardog;
	public static Texture blueHelicopter;
	public static Texture blueTank;

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
	public static Music menu_music;

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
	public MusicPlaylist creditsMusic;

	// for the boolean value if the game is in medieval mode

	public static boolean isMedievalMode = false;

	public static boolean isColourChanged;

	public static Sound loadingSound;

	public static String botDifficulty = "easy";

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
	private static Sound boomSoftwares;
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

	public static float sliderSize;

	// --------------------------------------------

	public static Sound katIncluded;
	public static Music tutorialSound;

	public static Texture tutorialTexture;
	public static boolean inTutorial = false;

	@Override
	public void create() {
		// creation of the batch for drawing the images
		batch = new SpriteBatch();

		// loading Screen is going till loading complete and main menu starts ----------------------------

		loadingScreenTextures = new RandomImage();
		loadingScreenTextures.addTexture("loadingScreen/KatLoading.png");
		loadingSound = Gdx.audio.newSound(Gdx.files.internal("sounds/countdown.mp3"));
		tutorialSound = Gdx.audio.newMusic(Gdx.files.internal("Misc/tutorialsound.mp3"));
		tutorialTexture = new Texture(Gdx.files.internal("Misc/tutorial.png"));
		loadingStage = LoadingScreenStage.initalizeUI();

		// creating all stage objects
		deathExplosionStage = new Stage(new ScreenViewport());
		mapStage  = new Stage(new ScreenViewport());
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

		// initialises the tile size for relative positioning of stages
		RelativeResizer.init(); // sets public tilesize variable

		// resize all stages for the beginning
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		// background is drawn in a batch, hence Texture
		Texture background = new Texture(Gdx.files.internal("backgrounds/background_4.png"));

		screenWidth = Gdx.graphics.getWidth();
		screenHeigth = Gdx.graphics.getHeight();

		Image backgroundImage = new Image(background);
		backgroundImage.setSize(screenWidth, screenHeigth);
		backgroundStage.addActor(backgroundImage);

		buttonWidth = tileSize*2;
		buttonHeight = tileSize/2;

		boomSoftwares = Gdx.audio.newSound(Gdx.files.internal("Misc/BoomSoftwares.mp3"));
		katIncluded = Gdx.audio.newSound(Gdx.files.internal("Misc/katIncluded.mp3"));

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

		if (loadingScreenIsRunning){
			loadingStage.act();
			loadingStage.draw();
			// run loading screen for 3 seconds atleast
			if(loadingElapsed < 4){
				loadingElapsed += Gdx.graphics.getDeltaTime();
			} else {
				loadingScreenIsRunning = false;
				// ensures game starts in menu
				createMainMenuStage();
				loadingStage.clear();
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
		currentStage.act();
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
		}

		if(showInGameOptions){
			Gdx.input.setInputProcessor(inGamOptStage);
			inGamOptStage.act();
			inGamOptStage.draw();
		}

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

	private static void loadAllAssets() {
		/*
		This method gets called during the main loading Stage runs
		 */
		// loading all assets -----------------------------------------------------------------------------------

		// for defaulting colour change
		isColourChanged = false;

		// skin of the UI --------------------
		// skin (look) of the buttons via the prearranged json file
		skin = new Skin(Gdx.files.internal("menu.commodore64/uiskin.json"));

		// Retrieving the font used in the skin
		BitmapFont font = skin.getFont("commodore-64");

		// Scaling the font depending on the relativresizer calculated tile size

		if (tileSize > 140) {
			font.getData().setScale(3.5f);
			sliderSize = 4f;
		} else if (tileSize > 100) {
			font.getData().setScale(2.8f);
			sliderSize = 2.5f;
		} else if (tileSize > 50){
			font.getData().setScale(2);
			sliderSize = 1.5f;
		} else {
			font.getData().setScale(1.5f);
			sliderSize = 1f;
		}

		// Optionally, update the skin with the scaled font if needed
		skin.add("commodore-64", font, BitmapFont.class);


		// assets

		greenMove = new Image(new Texture(Gdx.files.internal("moveLogos/green_Move.png")));
		redMove = new Image(new Texture(Gdx.files.internal("moveLogos/red_Move.png")));
		blueMove = new Image(new Texture(Gdx.files.internal("moveLogos/blue_Move.png")));

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

		// bot arm
		botArm = new Texture(Gdx.files.internal("Misc/botArm.png"));


		blueArtillery = new Texture(Gdx.files.internal("blueTeam/artillery_blue_right.png"));
		blueInfantry = new Texture(Gdx.files.internal("blueTeam/infantry_blue_right.png"));
		blueCommando = new Texture(Gdx.files.internal("blueTeam/commando_blue_right.png"));
		blueGeneral = new Texture(Gdx.files.internal("blueTeam/general_blue_right.png"));
		blueWardog = new Texture(Gdx.files.internal("blueTeam/war_dog_blue_right.png"));
		blueHelicopter = new Texture(Gdx.files.internal("blueTeam/helicopter_blue_right.png"));
		blueTank = new Texture(Gdx.files.internal("blueTeam/tank_blue_right.png"));

		xMarker = new Texture(Gdx.files.internal("Misc/xMarker.png"));

		boomLogo = new Image(new Texture(Gdx.files.internal("logo/Logo3.png")));

		empty = new Texture(Gdx.files.internal("Misc/empty.png"));

		// help texture
		helpTexture = new Texture(Gdx.files.internal("Misc/rules.png"));

		// Loading Texture of the map

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

		// texture for the action Running logo
		actionOngoing = new Image(new Texture(Gdx.files.internal("Misc/actionOngoing.png")));
		actionOngoing.setSize(tileSize*2.5f, tileSize*1.2f);

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

		// load the sound effects into respective Objects --------------------------------------

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

		// load the background music into MusicPlaylist object --------------------------------------
		background_music = new MusicPlaylist();
		background_music.addSong("music/Breakdown.mp3"); // song added by Artist Wumbatz
		background_music.addSong("music/A Little R & R.mp3");
		background_music.addSong("music/24 Stray cat.mp3");
		background_music.addSong("music/05 Thought Soup.mp3");
		background_music.addSong("music/06 Tonal Dissonance.mp3");
		background_music.addSong("music/27 Coffee Break.mp3");
		background_music.addSong("music/36 Tonal Resonance.mp3");
		background_music.addSong("music/epic-battle.mp3");
		background_music.addSong("music/Outside the Colosseum.mp3");
		background_music.addSong("music/Song Idee Chess.mp3"); // song added by Artist Wumbatz
		background_music.addSong("music/Song 2.mp3"); // song added by Artist Wumbatz

		wrongMoveLogo = new Texture("Misc/WrongMove.png");

		// load the menu music

		menu_music = Gdx.audio.newMusic(Gdx.files.internal
				("music/(LOOP-READY) Track 1 - Safe Zone No Intro.mp3"));

		// ---------------------------- universal Buttons for adding to stages

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
					if(inTutorial){
						tutorialSound.setVolume(0.5f);
					}

					soundVolume = 0.5f;
					volume = 0.5f;

					background_music.setVolume(volume);
					menu_music.setVolume(volume);
				} else {
					if(inTutorial){
						tutorialSound.setVolume(0);
					}
					soundVolume = 0;
					volume = 0;
					background_music.setVolume(volume);
					menu_music.setVolume(volume);
				}
			}
		});

		skipButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (currentState != GameState.NOT_IN_GAME) {
					background_music.nextSong();
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

		loadingSound.stop();

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

		// leaves the loading screen
		assetsLoaded = true;
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
		table.add(botDifficultyText).padBottom(tileSize/4).row();

		// Button to change 1.Player Colour to blue
		TextButton changeColourButton = new TextButton("Switch 1P Skin", skin);
		changeColourButton.align(Align.bottomRight);
		changeColourButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				isColourChanged = !isColourChanged;
				currentStage = GameStage.createGameStage(isBotMatch);
				createInGameOptionStages();
			}
		});
		table.add(changeColourButton).padBottom(tileSize/4).row();

		// button for turning the arm on and off
		TextButton armButton = new TextButton("BotArm: " + showArm, skin);
		armButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				showArm = !showArm;
				createInGameOptionStages();
			}
		});
		table.add(armButton).padBottom(tileSize/4).row();

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
		table.add(beepModeButton).padBottom(tileSize/4).row();

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
		table.row().padBottom(tileSize/4);

		table.add(changeMapButton);
		table.row().padBottom(tileSize/4);

		table.add(volumeLabel);
		table.row().padBottom(tileSize/4);

		table.add(soundVolumeLabel);
		table.row().padBottom(tileSize/4);

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
		table.add(menuButton).padBottom(tileSize/4).row();

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
		table.add(closeButton).padBottom(tileSize/4).row();


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
		float yPosition = Gdx.graphics.getHeight() - height;// Subtract height of the mover, positioning it at the top
		currentMover.setPosition(xPosition, yPosition);


		boolean addActor = true;
		if(sequenceRunning){
			// if the sequence is running, add an actor that says action is running
			currentMover.addActor(actionOngoing);
			currentMover.setPosition(xPosition, yPosition-tileSize/4);
		}
		else {
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
		switchToStage(createGameStage(isBotMatch));
	}

	public static void createMainMenuStage() {
		/*
		* method for creating the stage for the main menu
		 */
		inTutorial = false;
		switchToStage(MenuStage.initializeUI());
		gameEndStage.clear();
	}

	public static void createOptionsStage() {
		/*
		* method for creating the stage for the options display
		 */
		switchToStage(OptionsStage.initalizeUI());
	}

	public static void createHelpStage() {
		/*
		* method for creating the stage for the help display
		 */
		Stage funcStage = new Stage();

		Table funcTable = new Table();

		Image helpImage = new Image(helpTexture);
		helpImage.setSize(tileSize*12.5f, tileSize*8);
		helpImage.setPosition(tileSize*4.1f, tileSize*1.9f);
		funcTable.addActor(helpImage);

		funcTable.row();

		// button to go back to the menu
		TextButton backButton = new TextButton("Back", skin);
		backButton.setPosition((float) Gdx.graphics.getWidth() /2,
				(float) Gdx.graphics.getHeight() /8);
		backButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				createMainMenuStage();
			}
		});
		funcTable.addActor(backButton);

		funcStage.addActor(funcTable);

		switchToStage(funcStage);
	}

	public static void createCreditsStage() {
		/*
		* method for creating the stage for the credits display
		 */
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

		Coordinates generalCoordinates = new Coordinates();
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
        cross.setPosition((float) coordinates.getX() - (tileSize / 2),
				(float) coordinates.getY() - (tileSize / 2));
		cross.setSize(tileSize, tileSize);
		BoomChess.crossOfDeathStage.addActor(cross);
	}
}