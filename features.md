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

