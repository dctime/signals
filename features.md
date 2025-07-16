### 2025.4.22

Ported from 1.21.4 to 1.21.1 because JEI is availabled only in 1.21.1

Fixed issue 2025.4.22 (1)

Added Energy to the FlawlessExchanger

Generates Dirt Every 200 ticks. The amount of ticks passed is shown in the gui.

### 2025.4.23
Flawless Exchanger Using Redstone to generate power

Signal Wire Block Init

Signal Wire Block Using Multipart Builder Controller by NORTH SOUTH EAST WEST UP DOWN blockstates
(Not Tested)

### 2025.4.24
Fix Issue 2025.4.23 (2) Now the collision box is the unconnected wire

(Signal Wire Block) Right Click the face to extend to the face, Sneak right click to extend to the opposite clicked face.
Visuals only.

Signal Wire Block Got Value and Strength. 
Right Click with a stick to show value and strength.

Placing a redstone block by or placing a signal wire block by a redstone block will set the value to 30. 
Removing the redstone block will update the signal wire.
(Redstone block's onPlace onDestroy method calls level's updateNeightbor. After that neighbor blocks would execute neighborChanged.)

### 2025.4.25

Init SignalWireInformation to SignalWireBlock

SignalWireBlock now behave like eletrical wires put redstone by the wire to make the net of the wire got 30V. 
Support connections and disconnections and loops of wires. Doesnt support multiple voltages applied to one net of wire.

Fixed Issue 2025.4.25 (1)

### 2025.4.27

Redstone block will not power SignalWireBlock anymore.

ConstSignalBlock Init and ready for applying signal to the SignalWireBlock if connected.

Fixed 2025.4.27 Issue (4)

### 2025.4.28

Solved problem that when wire configuration changed while source is near. Issue 2025.4.28 (1)

Signal to Redstone converter init. Use redstone dust to turn one side of a wire to redstone output.

### 2025.4.29

Now redstone converter sends out 15 if signal value exceeds 15 and the same value as the signal value if between 0 and 15

Fixed Issue 2025.4.28 (2)

### 2025.4.30

ConstSignalBlock Screen Init

Fixed Issue 2025.4.30 (1) Container Menu overrides the keyPressed method

### 2025.5.1

Fixed Issue 2025.4.30 (2)

### 2025.5.2

Fixed Issue 2025.5.1 (1) (2)

Fixed Issue 2025.5.2 (2)

### 2025.5.5

Operation Block Init 2025.5.2 (3)

### 2025.5.7

Input and output model of operation block created. 2025.5.5 (1)

Clicking the sides of the operation block will switch side mode. Crouching will instead switch opposite side's side mode. 2025.5.5 (2)

Rendering input and output side models 2025.5.7 (1)

Adding functionalities to the operation block 2025.5.7 (2)

input 1 and input 2 models added 2025.5.7 (4)

Fixed issue 2025.5.7 (5) (7) (8)

### 2025.5.8

Fixed Issue 2025.5.7 (10)

Fixed Issue 2025.5.8 (1) (3) (4) (5)

Fixed Issue 2025.5.2 (1)

Fixed Issue 2025.5.7 (3)

Fixed Issue 2025.5.5 (4)

### 2025.5.9

Init Signal Detector Item to replace STICK

Add functionality to signal detector item

Fixed Issue 2025.5.9 (3) (4) (6)

### 2025.5.13

Fixed Issue 2025.5.10 (2)

Fixed Issue 2025.5.9 (5)

### 2025.5.16

Rotate Const Signal Block when placement

Fixed issue 2025.5.16 (1)

### 2025.5.21

Init Ponder of signal wire block

Fixed Issue 2025.5.21 (1)

### 2025.5.22

Fixed Issue 2025.5.21 (2)

SignalToRedstone Converter now fixed as signal wire block does

### 2025.5.23

Fixed 2025.5.23 (1) Now wires can be waterlogged

Fixed Issue Ponder not checked if create mod installed 2025.5.23 (5)

### 2025.5.24

Fixed Issue 2025.5.24 (1)

### 2025.5.26

Fixed Issue 2025.5.23 (4)

Fixed Issue 2025.5.9 (8)

### 2025.5.27

Fixed Issue 2025.5.23 (3)

Fixed Issue 2025.5.27 (3) (4)

### 2025.5.28

Fixed Issue 2025.5.8 (6) 2025.5.23 (2)

Fixed Issue 2025.5.28 (1)

Ponder Text register integration

### 2025.6.1

2025.5.27 (1) Add ponder to signal wire and detector and const signal

2025.6.1 (1) Add setChanged() to all the blocks that store nbt data (doc BlockEntities)

### 2025.6.5

2025.5.9 (7) Added Configurator Item

2025.5.27 (5) Display signal wire value in Jade

2025.6.1 (5) Jade show operation block output value

Change Mod ID to dctimesignals and MOD Name to Signals

2025.6.1 (2) Add ponder tutorial to Signal Operation Block

### 2025.6.6

2025.6.1 (3) (4) Add ponder tutorial to Redstone Converter wire and operation cards

2025.6.6 (1) Add ponder tutorial to Const Signal Block

2025.6.1 (6) Update signal wire ponder cuz configurator added

### 2025.6.12

Implement RedstoneToSignalConverterBlock to convert redstone signals to custom signal wire output

Add Redstone to Signal Converter block and update related models and language entries

### 2025.6.13

Add redstone to signal converter tutorial and improve existing scene texts

Add RedstoneToSignalConverterBlockEntity to load value when startup

2025.6.13 (1) RedstoneToSignalConverterBlock Jade Implementation 

### 2025.6.14

Dimension Type and Dimension Datagen

### 2025.6.19

Biome Datagen

### 2025.6.23

Add signal dripstone cluster feature and biome generation settings

### 2025.6.28

Add signal world biome and configured features for dripstone and redstone ore

### 2025.6.28

Add signal blocking material chunk feature and its configuration

### 2025.6.29

Add signal blocking material block and its associated features

Add DCtimeRecipeProvider for signal world crafting recipes

Add Signal World Portal block and associated functionality

Enhance Signal World Portal block to support bi-directional travel and update portal creation logic

### 2025.6.30

portal block model and texture

Improve portal detection logic in Signal World Portal block for enhanced functionality

Add structure template pool for Signal World Portal and update data generation

placeable structure without generation

### 2025.7.1

Refactor Signal World Portal logic and improve data generation settings

Updated to 0.0.3

Init Signal Research Station blocks

Add Signal Research Station block and related entities with rendering support

Add Signal Research Menu and Screen with block entity integration

### 2025.7.2

Showing input output signal values on gui

Signal screen got graph

Added plus card

If inputs/outputs greater than 3, disable assembling

Pos uses list instead of set

GUI auto scale in different window size

render outline even not looking at the station block

auto assemble when enter gui

init research item chamber

### 2025.7.3

init research item chamber gui

### 2025.7.5

light bulb progress ready

added recipes for research station uncomplete

### 2025.7.6

Signal Research Station now supports 3 input slots to 1 output slot recipes with item count

output slot should not able to modify

### 2025.7.7

research station should check input signal to add progress

recipe needs a formula input of signal from output to input

research station item chamber data needs to be stored and
research station not synced and stored
fixed

### 2025.7.8

Debug outlines when reassembles

item chamber researching item shown

signal Required reset when process finished

### 2025.7.9

research station related recipes done

aetherite ceramic for making research station related blocks

Multiblock valid flag added

jei integration for research station recipes 

### 2025.7.10

research station invalid flag is true when placed initially

jei quick swap and click to view recipes and related blocks added

research station tutorial ponder added

add ponder to explain signal world resources

eaten multiblock break then replace and place redstone in it will cause it not comsume item but can research (4) fixed

quick swap will swap into the buffer (5) fixed

gui items no tooltip (6) fixed

needs tips for signal recipes (7) fixed

add a tutorial about integer and binary (8) fixed

### 2025.7.11

infinite research when station is broken (1) fixed

0.0.4 beta released

### 2025.7.12

Signal Pickaxe init and turns nearby ores into screen coords ready for putting to screen

### 2025.7.13

Signal Pickaxe screen coords put onto the screen

Added Ore name and max range and range 

ground penerating signal emitter for immersion? (2) init

### 2025.7.14

Added filter functionality to ground penerating signal emitter slot 2

getArea fixed

the pickaxe should have two modes switchable by right clicking the pickaxe in hand. (1)

send jei dragged item to the server (2)

Added data component so that every pickaxe stores its mode and data but hud is still per player shall switch data when switch pickaxe

hud info not stored by pickaxe but player (3) fixed by adding Signal Pickaxe Hud Data Component

### 2025.7.15

limit pickaxe input output slot functionality (fixed)

Signal Pickaxe and Ground Penerating Signal Emitter data not stored. (x)(data component stores itself) (emitter fixed)

Make a cool model animated (added)

Add particle effects when the block is scanning ores (added)

scan ticks stored

signal required 2 fixed stuck at 0 

fix jei showing barrier workaround

show pickaxe info in tooltip (added)

added translations for G.P.S. Emitter and Signal Pickaxe

### 2025.7.16

added signal pickaxe ponder

fixed filter can pass in non block item by jei

gps emitter use trigger

emits white smoke if block not found

show ABC when assemble and signal station (fixed)

not saving debugOutlineTicks to disk cuz its client variable

not 8x8x8 9x9x9 (fixed)

Signal Pickaxe wont drop if break the gps emitter (fixed)

quick swap gps emitter not working (fixed)

