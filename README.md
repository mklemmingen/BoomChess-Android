Idiocracied chess, with health, guns, obstacles, boom and stuff.

Featuring complex health, damage and movement systems to promote both tactical logic and the inclusion of randomness into decision making.

Makes lots of cool boom sounds. 

non-deterministic chess.

![Gif of BoomChess Gameplay](https://github.com/mklemmingen/mklemmingen/blob/3556596be154afc9ec85af4a719772fc1042eaa4/boomchessandroid.gif?raw=true)

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Quick intro:

The game will be modernized using a different approach to chess. Each piece is a new piece resembling it only slightly in the way it can move. The King will be a General. The Towers will be tanks. The Pawns Infantry. The Runners will be Dogs. The Horses will be Helicopters. The Queen will be a Commando. 

The whole board will be 9x8. Each piece has a healtvalue and a damagevalue that is randomised and subject to dis- and advantages  
 
At the end of each chess-like turn, the current players pieces will all attack anyone they can. 

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Damage is scaled by the health of the piece. The less health the piece has, the less it will deal damage.
Its in the format of damage * standardHealth/currentHealth

A Pieces Damage to an enemy is a fraction by single-attack-damage/(numbers of enemies it is attacking this turn overall)

A Piece gets a damage boost by damage*numbers of allies surrounding it

General(King) / health: int: 50   / damage: 1-5  

   The most critical piece on the board. It can move one square in any direction (horizontally, vertically, or diagonally).
   if killed team loses
   takes only half the possible damage

Commando(Queen)n / health: int: 50  / damage: 1-30  / advantages: +10 to attacking tanks 

   The most powerful piece. It can move horizontally, vertically, diagonally, and in any direction for any number of squares.
   on a randomized 1-5 scale, he takes (<random number>/5)*100 percent less damage
   
Tank(Rook) / health: int: 60  / damage: 10-20 / advantages: +5 to attacking infantry / disadvantages: deals -5 to wardogs

   These are often represented as towers. They can move horizontally or vertically for any number of squares.
   high health. hard on other armour.
   easily killed by helicopters. acts like towers

War Dogs(Bishop) / health: int: 40   / damage: 5-20  / advantages: +5 to attacking infantry 

   They move diagonally for any number of squares.
   is the end of faith for all infantry. easy target for helicopters
   the war dogs fear them because they go broom. 
 
Helicopter(Knight)/ health: int : 50  / damage: 10-20 / advantages: +5 to attacking tanks

   Knights move in an L-shape: two squares in one direction (either horizontally or vertically) and then one square in a perpendicular direction. Knights can jump over other pieces.
   High movement. doesn't care about obstacles.
   high effectiveness against armour. 

Infantry(Pawn) / health: int: 40  / damage: 01-20 / advantages: +5 to attacking helicopters / disadvantages: -5 to tank

   Pawns move forward one square but capture diagonally. On their first move, they have the option to move forward two squares. When a pawn reaches the opponent's back rank, it can be promoted to any other piece (typically a queen).
   the simple pawn
   Has a bonus on attacking helicopters. an easy target for war dogs.

Artillery / damage 01-10 

 Artillery can move in any direction one tile. It can hit targets 2 tiles away

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Board Size: 9x8

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Starting Menu Layout:

Boom-Chess

1. Tutorial
2. Start a 2-player-game
3. Start a game against a bot
4. Options
5. Credits
6. EXIT

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

main-menu title:

Victory Sound by Lesiakower https://pixabay.com/music/video-games-victory-screen-150573/

Background-Music ingame:

"Retro Wave" from "https://pixabay.com/sound-effects/retro-wave-melodie-128-bpm-8970/" at date: 11.10.23

"epic-battle" by Lesiakower https://pixabay.com/music/video-games-epic-battle-153400/

r and r, outside the colloseum https://bit-by-bit-sound.itch.io/16-bit-starter-pack

safe zone https://hzsmith.itch.io/vol2

downfall, thought soup, total dissonance, stray cat, coffee break, tonal resonance: https://garoslaw.itch.io/monolith-ost

------------------------------------------------------------------------------------------------------------------------------------------------------------------

Sources for Pictures

the background has been created by Marty Lauterbach (mklemmingen) using LibreSprite
All Characters have been created by Marty Lauterbach (mklemmingen) using LibreSprite and Dall-E3

source for hitmarker

https://www.freepik.com/free-vector/animation-sprite-sheet-bomb-explosion-sequence_29084609.htm#query=sprite%20sheets&position=12&from_view=keyword&track=ais

-------------------------------------------------------------------------------------------------------------------------------------------------------------------

Tutorial and Wiki Sources:

For Scene2D
https://www.youtube.com/watch?v=YbeDMmajH9s

For General libGDX and starter Project
https://libgdx.com/wiki/

For Usage of Tiled for tmx map and tileset conversion:
https://www.youtube.com/watch?v=N6xqCwblyiw

-----------
source skins for Scene2DUI
https://github.com/czyzby/gdx-skins//master/flat
https://github.com/czyzby/gdx-skins/tree/master/commodore64

-----------
source for animation (Sprite Sheets)
Explosion
https://www.seekpng.com/idown/u2q8t4i1o0t4a9u2_drawn-explosions-sprite-explosion-sprite-sheet-doom/

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Documentation:

OLD-Schematics for the Program

![Schematics for the Program. frontend on top, backend on bottom. the frontend is libGDX based and the backend is a 2D Array of a Soldier class, a Damage class, a Board Class and a couple of Classes for pieces. It is not a much-more-indepth diagram](https://github.com/mklemmingen/boom-chess/blob/master/readme_assets/Schematics_ProgrammierprojectHD.png?raw=true)

Creation of the Background in LibreSprite https://github.com/LibreSprite/LibreSprite

![Background Creation](https://github.com/mklemmingen/The-Boom-Chess/blob/master/readme_assets/backgroundCreation.png?raw=true)

creating better PixelArt Icons for the Soldier Pieces using bings AI creation powered by Dall-E 3

![Icon Creation using DallE3](https://github.com/mklemmingen/The-Boom-Chess/blob/master/readme_assets/creatingBetterPixelArtIconsWithDallE3.png?raw=true)

Creation of mixed Icons for making the Pieces more diverse in their type USING inkscape https://inkscape.org

![Mixed Icons Creation in inkscape](https://github.com/mklemmingen/The-Boom-Chess/blob/master/readme_assets/combiningAndRefining.png?raw=true)

Creation of the Boom Logo pixel by pixel in LibreSprite https://github.com/LibreSprite/LibreSprite

![Creation of the Boom Logo pixel by pixel in LibreSprite https://github.com/LibreSprite/LibreSprite](https://github.com/mklemmingen/The-Boom-Chess/blob/master/readme_assets/How_to_Boom-Logo.png?raw=true)

Creation of the Move Logo pixel by pixel in LibreSprite https://github.com/LibreSprite/LibreSprite

![Creation of the Move Logo pixel by pixel in LibreSprite https://github.com/LibreSprite/LibreSprite](https://github.com/mklemmingen/The-Boom-Chess/blob/master/readme_assets/Move_Logo_Creation.png?raw=true)

Creation of the Schematics USING inkscape https://inkscape.org

![Schematics Creation in inkscape](https://github.com/mklemmingen/The-Boom-Chess/blob/master/readme_assets/schematicsCreation.png?raw=true)

Creation of the underlying .tmx tiled map using TILED https://www.mapeditor.org/

![Creation of the underlying .tmx tiled map using TILED](https://github.com/mklemmingen/The-Boom-Chess/blob/master/readme_assets/TiledUsage.png?raw=true)

Using Inkscape for refining Icons

![Using Inkscape for refining Icons](https://github.com/mklemmingen/The-Boom-Chess/blob/master/readme_assets/UsingInkScapeForRefininigPNGs.png?raw=true)


// sources for the sounds used for the pieces

all sounds were converted and cut with audacity to make them
smaller, compact and theme fitting

https://freesound.org/people/TobiasKosmos/sounds/163277/

https://freesound.org/people/nuncaconoci/sounds/487535/

https://freesound.org/people/EminYILDIRIM/sounds/547369/

https://freesound.org/people/praesius/sounds/107190/

https://freesound.org/people/D001447733/sounds/464596/

https://freesound.org/people/8bitmyketison/sounds/699822/

https://freesound.org/people/wesleyextreme_gamer/sounds/574820/

https://freesound.org/people/MTJohnson/sounds/426326/

https://freesound.org/people/Streety/sounds/30245/

https://freesound.org/people/JohnBuhr/sounds/326803/

https://freesound.org/people/JoelAudio/sounds/77611/

https://freesound.org/people/Angrycrazii/sounds/277322/

https://freesound.org/people/CaptainYulef/sounds/638696/

https://freesound.org/people/maugusto_sfx/sounds/468030/

https://freesound.org/people/DrinkingWindGames/sounds/439670/

https://freesound.org/people/Gingerhoney/sounds/655070/

https://freesound.org/people/o_ciz/sounds/475479/

https://freesound.org/people/qubodup/sounds/442827/

https://freesound.org/people/HighPixel/sounds/431174/

https://freesound.org/people/cetsoundcrew/sounds/521321/

https://freesound.org/people/Robinhood76/sounds/253203/

https://freesound.org/people/copyc4t/sounds/222608/

https://freesound.org/people/SonoFxAudio/sounds/649335/

https://freesound.org/people/Alxy/sounds/190469/

https://freesound.org/people/ainst/sounds/442668/

https://freesound.org/people/unikumpu/sounds/609460/

https://freesound.org/people/AntumDeluge/sounds/584326/

https://freesound.org/people/UnderlinedDesigns/sounds/172667/

https://freesound.org/people/Tissman/sounds/444675/

https://freesound.org/people/karisigurd4/sounds/564887/

https://freesound.org/people/qubodup/sounds/442958/

https://freesound.org/people/Willlewis/sounds/244345/

https://freesound.org/people/harpoyume/sounds/86031/

https://freesound.org/people/harpoyume/sounds/86032/

https://freesound.org/people/Rudmer_Rotteveel/sounds/336010/

https://freesound.org/people/EFlexMusic/sounds/387229/

https://freesound.org/people/cydon/sounds/268557/

https://freesound.org/people/unfa/sounds/609588/

https://freesound.org/people/deleted_user_5405837/sounds/399303/

https://freesound.org/people/cabled_mess/sounds/350876/

https://freesound.org/people/SoundDesignForYou/sounds/646672/

https://freesound.org/people/MATRIXXX_/sounds/402767/

https://freesound.org/people/MATRIXXX_/sounds/443258/

https://freesound.org/people/MATRIXXX_/sounds/402067/

https://freesound.org/people/ProjectsU012/sounds/341695/

https://freesound.org/people/MATRIXXX_/sounds/658266/

https://freesound.org/people/MiscPractice/sounds/676958/

https://freesound.org/people/3questionmarks/sounds/612722/

https://freesound.org/people/VincentM400/sounds/249618/

https://pixabay.com/sound-effects/autocannon-20mm-143113/

https://pixabay.com/sound-effects/sniper-rifle-129927/

https://pixabay.com/sound-effects/helicopter-rotor-loop-105796/

https://pixabay.com/sound-effects/cannonball-89596/

https://pixabay.com/sound-effects/dog-barking-6296/

https://pixabay.com/sound-effects/desert-eagle-gunshot-14622/

https://pixabay.com/sound-effects/driving-tank-engine-88503/


