package com.boomchess.game.frontend.stage;

import static com.boomchess.game.BoomChess.actionSequence;
import static com.boomchess.game.BoomChess.batch;
import static com.boomchess.game.BoomChess.botMovingStage;
import static com.boomchess.game.BoomChess.calculateTileByPX;
import static com.boomchess.game.BoomChess.calculateTileByPXNonGDX;
import static com.boomchess.game.BoomChess.clearAllowedTiles;
import static com.boomchess.game.BoomChess.createInGameOptionStages;
import static com.boomchess.game.BoomChess.createMainMenuStage;
import static com.boomchess.game.BoomChess.crossOfDeathStage;
import static com.boomchess.game.BoomChess.currentState;
import static com.boomchess.game.BoomChess.damageNumberStage;
import static com.boomchess.game.BoomChess.deathExplosionStage;
import static com.boomchess.game.BoomChess.dottedLineStage;
import static com.boomchess.game.BoomChess.empty;
import static com.boomchess.game.BoomChess.gameEndStage;
import static com.boomchess.game.BoomChess.inGame;
import static com.boomchess.game.BoomChess.inTutorial;
import static com.boomchess.game.BoomChess.isColourChanged;
import static com.boomchess.game.BoomChess.legitTurn;
import static com.boomchess.game.BoomChess.reRenderGame;
import static com.boomchess.game.BoomChess.setAllowedTiles;
import static com.boomchess.game.BoomChess.showHelp;
import static com.boomchess.game.BoomChess.showInGameOptions;
import static com.boomchess.game.BoomChess.skin;
import static com.boomchess.game.BoomChess.speechBubbleStage;
import static com.boomchess.game.BoomChess.tileSize;
import static com.boomchess.game.BoomChess.wrongMoveStage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.boomchess.game.BoomChess;
import com.boomchess.game.backend.Board;
import com.boomchess.game.backend.Coordinates;
import com.boomchess.game.backend.Soldier;
import com.boomchess.game.backend.subsoldier.Artillery;
import com.boomchess.game.backend.subsoldier.Empty;
import com.boomchess.game.backend.subsoldier.Hill;
import com.boomchess.game.frontend.actor.AttackSequence;
import com.boomchess.game.frontend.actor.HealthNumber;
import com.boomchess.game.frontend.actor.SpecialDamageIndicator;
import com.boomchess.game.frontend.actor.WrongMoveIndicator;
import com.boomchess.game.frontend.animations.soldierAnimation;
import com.boomchess.game.frontend.interfaces.takeIntervalSelfie;
import com.boomchess.game.frontend.interfaces.takeSelfieInterface;

import java.util.ArrayList;
import java.util.LinkedList;

public class GameStage {

    private static boolean  showHealth = false;

    private final Stage gameStage;

    public GameStage(boolean isBotMatch) {
        this.gameStage = createGameStage(isBotMatch);
    }

    public static LinkedList<soldierAnimation> allActiveSoldierAnimations = new LinkedList<>();

    public static Stage createGameStage(final boolean isBotMatch) {

        // clears the crossOfDeathStage
        crossOfDeathStage.clear();

        // allows the game have a MoveLogo
        BoomChess.showMove = true;

        // add the audio table to gameStage as Actor and position on the far right of the Screen
        Stage gameStage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        // xMarkerOverlay
        BoomChess.possibleMoveOverlay = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        // for the size of the tiles
        int numRows = 8;
        int numColumns = 9;

        batch.begin();

        Table root = new Table();

        // root of size 9 to 8 and center of screen
        root.setSize(numColumns * tileSize, numRows * tileSize);
        root.setPosition((float) Gdx.graphics.getWidth() / 2 - root.getWidth() / 2,
                (float) Gdx.graphics.getHeight() / 2 - root.getHeight() / 2);

        Soldier[][] gameBoard = Board.getGameBoard();

        for (int j = 0; j < numRows; j++) {
            root.row();
            for (int i = 0; i < numColumns; i++) {

                final Stack tileWidget = new Stack();

                final Soldier soldier = gameBoard[i][j];

                // if the current coordinate is the empty Variable coordinates and its
                // useEmpty = true, the solPiece has an Image of Empty, if not continue to rest

                Stack solPiece = new Stack();

                // if BoomChess.isAnimated, make solPiece a soldierAnimation Object and if not, an Image Object
                if (BoomChess.isAnimated) {
                    if (soldier instanceof Empty) {
                        Image emptyImage = new Image(empty);
                        emptyImage.setSize(tileSize, tileSize);
                        solPiece.add(emptyImage);
                    } else if (soldier instanceof Hill){
                        Image soldierImage = new Image(((takeSelfieInterface) soldier).takeSelfie());
                        soldierImage.setSize(tileSize, tileSize);
                        solPiece.add(soldierImage);
                    }else {
                        if(BoomChess.isMedievalMode){
                            // standard image load
                            Image soldierImage = new Image(((takeSelfieInterface) soldier).takeSelfie());
                            soldierImage.setSize(tileSize, tileSize);
                            solPiece.add(soldierImage);
                        }
                        else {
                            // switch statement for deciding which
                            // soldier animation to take, each case has a green or red and a
                            // iscolourreversed if statement between green and blue
                            switch (soldier.getPieceType()) {

                                case "general":
                                    if (soldier.getTeamColor().equals("green")) {
                                        if (isColourChanged) {
                                            soldierAnimation blueGeneralAnimation =
                                                    BoomChess.blueGeneralAnimation.clone();
                                            solPiece.add(blueGeneralAnimation);
                                            allActiveSoldierAnimations.add(blueGeneralAnimation);
                                        } else {
                                            soldierAnimation greenGeneralAnimation =
                                                    BoomChess.greenGeneralAnimation.clone();
                                            solPiece.add(greenGeneralAnimation);
                                            allActiveSoldierAnimations.add(greenGeneralAnimation);
                                        }
                                    } else {
                                        soldierAnimation redGeneralAnimation =
                                                BoomChess.redGeneralAnimation.clone();
                                        solPiece.add(redGeneralAnimation);
                                        allActiveSoldierAnimations.add(redGeneralAnimation);
                                    }
                                    break;

                                case "tank":
                                    if (soldier.getTeamColor().equals("green")) {
                                        if (isColourChanged) {
                                            soldierAnimation blueTankAnimation =
                                                    BoomChess.blueTankAnimation.clone();
                                            solPiece.add(blueTankAnimation);
                                            allActiveSoldierAnimations.add(blueTankAnimation);
                                        } else {
                                            soldierAnimation greenTankAnimation =
                                                    BoomChess.greenTankAnimation.clone();
                                            solPiece.add(greenTankAnimation);
                                            allActiveSoldierAnimations.add(greenTankAnimation);
                                        }
                                    } else {
                                        soldierAnimation redTankAnimation =
                                                BoomChess.redTankAnimation.clone();
                                        solPiece.add(redTankAnimation);
                                        allActiveSoldierAnimations.add(redTankAnimation);
                                    }
                                    break;

                                case "artillery":
                                    if (soldier.getTeamColor().equals("green")) {
                                        if (isColourChanged) {
                                            soldierAnimation blueArtilleryAnimation =
                                                    BoomChess.blueArtilleryAnimation.clone();
                                            solPiece.add(blueArtilleryAnimation);
                                            allActiveSoldierAnimations.add(blueArtilleryAnimation);
                                        } else {
                                            soldierAnimation greenArtilleryAnimation =
                                                    BoomChess.greenArtilleryAnimation.clone();
                                            solPiece.add(greenArtilleryAnimation);
                                            allActiveSoldierAnimations.add(greenArtilleryAnimation);
                                        }
                                    } else {
                                        soldierAnimation redArtilleryAnimation =
                                                BoomChess.redArtilleryAnimation.clone();
                                        solPiece.add(redArtilleryAnimation);
                                        allActiveSoldierAnimations.add(redArtilleryAnimation);
                                    }
                                    break;

                                case "infantry":
                                    if (soldier.getTeamColor().equals("green")) {
                                        if (isColourChanged) {
                                            soldierAnimation blueInfantryAnimation =
                                                    BoomChess.blueInfantryAnimation.clone();
                                            solPiece.add(blueInfantryAnimation);
                                            allActiveSoldierAnimations.add(blueInfantryAnimation);
                                        } else {
                                            soldierAnimation greenInfantryAnimation =
                                                    BoomChess.greenInfantryAnimation.clone();
                                            solPiece.add(greenInfantryAnimation);
                                            allActiveSoldierAnimations.add(greenInfantryAnimation);
                                        }
                                    } else {
                                        soldierAnimation redInfantryAnimation =
                                                BoomChess.redInfantryAnimation.clone();
                                        solPiece.add(redInfantryAnimation);
                                        allActiveSoldierAnimations.add(redInfantryAnimation);
                                    }
                                    break;

                                case "helicopter":
                                    if (soldier.getTeamColor().equals("green")) {
                                        if (isColourChanged) {
                                            soldierAnimation blueHelicopterAnimation =
                                                    BoomChess.blueHelicopterAnimation.clone();
                                            solPiece.add(blueHelicopterAnimation);
                                            allActiveSoldierAnimations.add(blueHelicopterAnimation);
                                        } else {
                                            soldierAnimation greenHelicopterAnimation =
                                                    BoomChess.greenHelicopterAnimation.clone();
                                            solPiece.add(greenHelicopterAnimation);
                                            allActiveSoldierAnimations.add(greenHelicopterAnimation);
                                        }
                                    } else {
                                        soldierAnimation redHelicopterAnimation =
                                                BoomChess.redHelicopterAnimation.clone();
                                        solPiece.add(redHelicopterAnimation);
                                        allActiveSoldierAnimations.add(redHelicopterAnimation);
                                    }
                                    break;

                                case "wardog":
                                    if (soldier.getTeamColor().equals("green")) {
                                        if (isColourChanged) {
                                            soldierAnimation blueWardogAnimation =
                                                    BoomChess.blueWardogAnimation.clone();
                                            solPiece.add(blueWardogAnimation);
                                            allActiveSoldierAnimations.add(blueWardogAnimation);
                                        } else {
                                            soldierAnimation greenWardogAnimation =
                                                    BoomChess.greenWardogAnimation.clone();
                                            solPiece.add(greenWardogAnimation);
                                            allActiveSoldierAnimations.add(greenWardogAnimation);
                                        }
                                    } else {
                                        soldierAnimation redWardogAnimation =
                                                BoomChess.redWardogAnimation.clone();
                                        solPiece.add(redWardogAnimation);
                                        allActiveSoldierAnimations.add(redWardogAnimation);
                                    }
                                    break;
                                case "commando":
                                    if (soldier.getTeamColor().equals("green")) {
                                        if (isColourChanged) {
                                            soldierAnimation blueCommandoAnimation =
                                                    BoomChess.blueCommandoAnimation.clone();
                                            solPiece.add(blueCommandoAnimation);
                                            allActiveSoldierAnimations.add(blueCommandoAnimation);
                                        } else {
                                            soldierAnimation greenCommandoAnimation =
                                                    BoomChess.greenCommandoAnimation.clone();
                                            solPiece.add(greenCommandoAnimation);
                                            allActiveSoldierAnimations.add(greenCommandoAnimation);
                                        }
                                    } else {
                                        soldierAnimation redCommandoAnimation =
                                                BoomChess.redCommandoAnimation.clone();
                                        solPiece.add(redCommandoAnimation);
                                        allActiveSoldierAnimations.add(redCommandoAnimation);
                                    }
                                    break;

                            }
                        }
                    }
                } else {
                    // load the corresponding image through the Soldier Take Selfie Method
                    if (soldier instanceof takeSelfieInterface) {
                        Image soldierImage = new Image(((takeSelfieInterface) soldier).takeSelfie());
                        soldierImage.setSize(tileSize, tileSize);
                        solPiece.add(soldierImage);
                    }
                }

                tileWidget.add(solPiece);

                // draw the image at the correct position
                solPiece.setSize(tileSize, tileSize);

                // try getHealth, is null, make health to -1
                int health;
                if (!(soldier == null)) {
                    health = soldier.getHealth();
                    // use the 'soldier' object since it's the same as gameBoard[X][Y]
                } else {
                    health = -1;
                }

                if (health < 15 && health > 0) {
                    // Apply a light red hue effect to the tileWidget's image
                    Color lightRed = new Color(1.0f, 0.5f, 0.5f, 1.0f);
                    solPiece.setColor(lightRed);
                }

                if (!(health == -1)) {
                    // tileWidget is only move able if a Piece is on it, meaning it has health
                    tileWidget.setTouchable(Touchable.enabled);
                    // draw the health number if showHealth is true
                    if(showHealth) {
                        Image blackCircle = new Image(BoomChess.blackCircle);
                        blackCircle.setSize(tileSize, tileSize);
                        tileWidget.add(blackCircle);
                        Container<Table> healthContainer =
                                HealthNumber.giveContainer(soldier.getStandardHealth(), health);
                        tileWidget.add(healthContainer);
                    }
                }

                // if the current soldier is instance of artillery, add the attack radius
                // of 5to5, if not, 3to3. safe it in a container and place its middle on
                // on the middle of the tileWidget

                // load both attack radius (3to3 and 5to5) images
                Image attackRadius3to3 = new Image(BoomChess.threeTOthreeCircle);
                attackRadius3to3.setSize(tileSize*3, tileSize*3);
                Image attackRadius5to5 = new Image(BoomChess.fiveTOfiveCircle);
                attackRadius5to5.setSize(tileSize*5, tileSize*5);

                // create container for the attack radius
                final Stack attackRadiusContainer = new Stack();

                // set the middle of the attack radius container to the middle of the tileWidget
                Coordinates coord = BoomChess.calculatePXbyTile(i, j);

                if(soldier instanceof Artillery){
                    attackRadiusContainer.add(attackRadius5to5);
                    attackRadiusContainer.setSize(tileSize*5, tileSize*5);
                } else {
                    attackRadiusContainer.add(attackRadius3to3);
                    attackRadiusContainer.setSize(tileSize*3, tileSize*3);
                }

                attackRadiusContainer.setPosition(
                        coord.getX()-attackRadiusContainer.getWidth()/2,
                        coord.getY()-attackRadiusContainer.getHeight()/2);

                attackRadiusContainer.setVisible(false);

                attackRadiusContainer.toFront();

                // add a damageInterval if showPossibleDamage is true
                if(BoomChess.showIntervals){
                    // takeSelfieInterface with showInterval method
                    if(soldier instanceof takeIntervalSelfie){
                        // Create an intermediary container (Table or Stack) for layout purposes
                        Table intervalContainer = new Table();

                        // Create the damageInterval image
                        Image damageInterval = new Image(((takeIntervalSelfie) soldier).showInterval());
                        damageInterval.setFillParent(false);

                        // Set the size of the damageInterval relative to the tileSize
                        damageInterval.setSize(tileSize/4, tileSize/8);

                        intervalContainer.add(damageInterval);

                        // Add the intervalContainer to the tileWidget Stack
                        // This ensures that damageInterval maintains its size and position
                        // within intervalContainer
                        tileWidget.add(intervalContainer);

                        intervalContainer.setSize(tileWidget.getWidth(), tileWidget.getHeight());
                    }
                }

                // add a Listener only if (!isBotMatch) || (isBotMatch && (state == GameState.GREEN_TURN))
                // since we do not want Red to have Drag if it's a bot-match, since that's the bot team
                if ((!isBotMatch) || (isBotMatch && (currentState == BoomChess.GameState.GREEN_TURN))) {
                    final int finalI = i;
                    final int finalJ = j;

                    tileWidget.addListener(new DragListener() {
                        @Override
                        public void dragStart(InputEvent event, float x, float y, int pointer) {
                            // Code runs when dragging starts:

                            // Get the team color of the current tile
                            Soldier[][] gameBoard = Board.getGameBoard();
                            String tileTeamColor = gameBoard[finalI][finalJ].getTeamColor();

                            // If it's not the current team's turn, cancel the drag and return
                            if ((currentState == BoomChess.GameState.RED_TURN && !tileTeamColor.equals("red")) ||
                                    (currentState == BoomChess.GameState.GREEN_TURN && !tileTeamColor.equals("green"))) {
                                event.cancel();

                                // adds a logo on screen that says that the movement was not allowed yet
                                WrongMoveIndicator indi = new WrongMoveIndicator();
                                wrongMoveStage.addActor(indi);
                                //plays a brick sound
                                indi.makeSound();

                                BoomChess.reRenderGame();
                                return;
                            }

                            if(BoomChess.showAttackCircle){
                                // attack radius
                                attackRadiusContainer.setVisible(true);
                            }

                            tileWidget.toFront(); // Bring the actor to the front, so it appears above other actors
                            // as long as the mouse is pressed down, the actor is moved to the mouse position
                            // we calculate the tiles it can move to and highlight these tiles with a slightly red hue
                            // the calculated tiles are part of a ArrayList variable that is created at create
                            // of the whole programm
                            // and gets cleared once we touchDragged the actor to a new position

                            // switch statement for deciding which
                            // Chess Pieces Class mathMove is used to assign the ArrayList validMoveTiles

                            assert soldier != null;
                            setAllowedTiles(soldier.mathMove(finalI, finalJ));
                        }

                        @Override
                        public void drag(InputEvent event, final float x, final float y, int pointer) {

                            // Code here will run during the dragging
                            tileWidget.moveBy(x - tileWidget.getWidth() / 2, y - tileWidget.getHeight() / 2);
                            attackRadiusContainer.moveBy(x - tileWidget.getWidth() / 2, y - tileWidget.getHeight() / 2);

                            // if the user has showPossibleDamage activated, show the possible damage
                            // of all attackable enemies
                            // from the current tile the drag is hovering over
                            Gdx.app.log("DragMethod", "About to post runnable");
                            Gdx.app.postRunnable(new Runnable() {
                                @Override
                                public void run() {

                                    Gdx.app.log("Runnable", "Inside the runnable");

                                    // for thread safety in OpenGL, which libGDX used, we
                                    // add a runnable for stage manipulation in the ereignis-thread
                                    // to main thread

                                    // Modify your UI components here
                                    damageNumberStage.clear();

                                    if(BoomChess.showPossibleDamage) {

                                        // we determine if the current tileWidget is of the soldier
                                        // artillery or not

                                        int distance = 1;
                                        if(soldier instanceof Artillery){
                                            distance = 2;
                                        }

                                        // go through the gameboard starting at the current tile and
                                        // check each tile nearby if there is an enemy soldier

                                        Soldier[][] gameBoard = Board.getGameBoard();

                                        // current calculation position from local
                                        // to screen coordinates

                                        Vector2 screenCoords =
                                                tileWidget.localToScreenCoordinates(new Vector2(x, y));
                                        Coordinates currentCords =
                                                calculateTileByPXNonGDX((int) screenCoords.x, (int) screenCoords.y);

                                        int currentX = currentCords.getX();
                                        int currentY = currentCords.getY();

                                        int startX = Math.max(0, currentX - distance);
                                        int endX = Math.min(8, currentX + distance);

                                        int startY = Math.max(0, currentY - distance);
                                        int endY = Math.min(7, currentY + distance);

                                        // get the color of the soldier that is currently being dragged
                                        String attackColor = gameBoard[finalI][finalJ].getTeamColor();

                                        // get the soldier type that the currently dragged soldier
                                        // can inflict special damage to

                                        int boniValue = 0;
                                        int malusValue = 0;
                                        Class<? extends Soldier> boniGI = null;
                                        Class<? extends Soldier> malusGI = null;

                                        Soldier boniSoldier = BoomChess.getSpecialBoniSoldier(soldier);
                                        if(boniSoldier != null){
                                            boniGI = boniSoldier.getClass();
                                            boniValue = BoomChess.getSpecialBoniValue(soldier);
                                        }

                                        Soldier malusSoldier = BoomChess.getSpecialMalusSoldier(soldier);
                                        if(malusSoldier != null){
                                            malusGI = malusSoldier.getClass();
                                            malusValue = BoomChess.getSpecialMalusValue(soldier);
                                        }



                                        for (int i = startX; i <= endX; i++) {
                                            for (int j = startY; j <= endY; j++) {
                                                if (i == finalI && j == finalJ) continue;

                                                Soldier currentSoldier = gameBoard[i][j];

                                                if (currentSoldier != null &&
                                                        !(currentSoldier instanceof Empty) &&
                                                        !(currentSoldier instanceof Hill)) {

                                                    String hurtColor = currentSoldier.getTeamColor();
                                                    if (!hurtColor.equals(attackColor)) {

                                                        Gdx.app.log("Debug", "Checking tile [" + i + "," + j + "], " +
                                                                "Enemy Color: " + hurtColor + ", Attacker Color: " + attackColor +
                                                                ", BoniGI: " + boniGI + ", MalusGI: " + malusGI);

                                                        if (boniSoldier != null &&
                                                                boniGI.isInstance(currentSoldier)) {
                                                            damageNumberStage.addActor(
                                                                    new SpecialDamageIndicator(boniValue, i, j, true));
                                                            Gdx.app.log("Runnable", "Added Boni");
                                                        }

                                                        if (malusSoldier != null &&
                                                                malusGI.isInstance(currentSoldier)) {
                                                            damageNumberStage.addActor(
                                                                    new SpecialDamageIndicator(malusValue, i, j, false));
                                                            Gdx.app.log("Runnable", "Added Malus");
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            });
                        }

                        @Override
                        public void dragStop(InputEvent event, float x, float y, int pointer) {

                            damageNumberStage.dispose();
                            damageNumberStage = new Stage();

                            // Code here will run when the player lets go of the actor

                            // Get the position of the tileWidget relative to the parent actor (the gameBoard)
                            Vector2 localCoords = new Vector2(x, y);
                            // Convert the position to stage (screen) coordinates
                            Vector2 screenCoords = tileWidget.localToStageCoordinates(localCoords);


                            Coordinates currentCoord = calculateTileByPX((int) screenCoords.x, (int) screenCoords.y);

                            // for loop through validMoveTiles, at each tile we check for equality of currentCoord
                            // with the Coordinate
                            // in the ArrayList by using currentCoord.checkEqual(validMoveTiles[i]) and if true,
                            // we set the
                            // validMove Variable to true, call on the update method of the Board class and break
                            // the for loop
                            // then clear the Board.


                            ArrayList<Coordinates> validMoveTiles = Board.getValidMoveTiles();
                            for (Coordinates validMoveTile : validMoveTiles) {
                                if (currentCoord.checkEqual(validMoveTile)) {
                                    // Board.update with oldX, oldY, newX, newY
                                    Board.update(finalI, finalJ, currentCoord.getX(), currentCoord.getY());
                                    legitTurn = true;
                                    break;
                                }
                            }

                            // and the validMoveTiles are cleared
                            clearAllowedTiles(); // for turning off the Overlay
                            Board.emptyValidMoveTiles();

                            attackRadiusContainer.setVisible(false);

                            BoomChess.reRenderGame();

                        }
                    });
                }
                gameStage.addActor(attackRadiusContainer);
                root.add(tileWidget).size(tileSize);
            }
        }

        gameStage.addActor(root);
        batch.end();

        if(inTutorial){
            // add tutorialtexture to the upper right corner
            Image tutorialTexture = new Image(BoomChess.tutorialTexture);
            tutorialTexture.setSize(tileSize*6, tileSize*7);
            tutorialTexture.setPosition(Gdx.graphics.getWidth() - tutorialTexture.getWidth(),
                    Gdx.graphics.getHeight() - tutorialTexture.getHeight());
            gameStage.addActor(tutorialTexture);
        }



        // create another table for the option buttons

        Table backTable = new Table();
        backTable.setSize(tileSize*5, tileSize*2f);
        // bottom right the table in the parent container
        backTable.setPosition(Gdx.graphics.getWidth() - backTable.getWidth(), tileSize);
        gameStage.addActor(backTable); // Add the table to the stage


        // help button
        backTable.row().padBottom(tileSize/4);
        TextButton helpButton = new TextButton("Help", skin);
        helpButton.align(Align.bottomRight);
        backTable.add(helpButton);
        helpButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showHelp = !showHelp;
            }
        });

        backTable.row().padBottom(tileSize/4);

        // show intervals button
        TextButton intervalsButton = new TextButton("Damage", skin);
        intervalsButton.align(Align.bottomRight);
        backTable.add(intervalsButton);
        intervalsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                BoomChess.showIntervals = !BoomChess.showIntervals;
                reRenderGame();
            }
        });

        backTable.row().padBottom(tileSize/4);

        // show health button
        backTable.row().padBottom(tileSize/4);
        TextButton healthButton = new TextButton("Health", skin);
        healthButton.align(Align.bottomRight);
        backTable.add(healthButton);
        healthButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showHealth = !showHealth;
                reRenderGame();
            }
        });

        backTable.row().padBottom(tileSize/4);

        // turn on options
        TextButton optionsButton = new TextButton("Options", skin);
        optionsButton.align(Align.bottomRight);
        backTable.add(optionsButton);
        optionsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                createInGameOptionStages();
                showInGameOptions = !showInGameOptions;
            }
        });
        backTable.row().padBottom(tileSize/4);

        // Exit to Main Menu button to return to the main menu
        TextButton menuButton = new TextButton("Main Menu", skin);
        menuButton.align(Align.bottomRight);
        backTable.add(menuButton);
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
                
                createMainMenuStage();
            }
        });

        return gameStage;
    }

    public Stage getStage() {
        return gameStage;
    }

    public static void clearAllActiveSoldierAnimations(){
        if(allActiveSoldierAnimations == null){
            return;
        }
        for(soldierAnimation soldierAnimation : allActiveSoldierAnimations){
            soldierAnimation.remove();
        }
        allActiveSoldierAnimations.clear();
    }
}

