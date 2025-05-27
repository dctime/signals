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