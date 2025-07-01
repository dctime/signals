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