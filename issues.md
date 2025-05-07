### 2025.4.22

After placing the build helper block and the slot is empty. 
Right clicking with the same slot wont change the mode of the block

Energy and item disappear when reopen the world (1)

### 2025.4.23

Flawless Exchanger needs GUI and a new name

Signal Wire needs collision block and needs a way to detect which multipart it clicked on (2)

### 2025.4.24

Signal Wire break box isnt the same as the model (make the break box bigger than the unconnected one to make it)

### 2025.4.25

Let the one who is updated to modify its capability, not the one who sends the update (1)

### 2025.4.27

The Collision box of the SignalWireBlock is so small that when a wire is connected player will fall between the wires

When placing the ConstSignalBlock, the FACING blockstate is always north. The block should be face to the player when placed.

ConstSignalBlock requires RF/t

Nearby wires not updated when break (4)

### 2025.4.28

Nearby source not applying source to newly configured wire. (1)

Signal to Redstone converter redstone feature not implemented. (2)

### 2025.4.30

Cannot type anything to the editbox (1)

Not checking inputs and sending inputs to the entity (2)

### 2025.5.1

Signal Block needs to update wires which is connected (1)

Higher Signal Values should dominate Low Signal Values in wire networks (2)

### 2025.5.2

Packet should use method in the const signal block to update signal value it outputs. Do not hardcode stuff.

Breaking const signal block with two signal blocks connection to each other with wires doesn't update the wires. (2)

Add signal operation block and switch operation by storing item (3)

### 2025.5.5

Adding input port and output port models. (1)

clicking the sides to switch input and output. (2)

Limiting input and output sides.

Turning off functionality if not the same amount of input and output sides.

### 2025.5.7

Render the model of input and output ports of the operation block (1)

Adding functionalities to the operation block (2)

Shrink the hit box of the operation block

need input 1 and input 2 models (4)

Signal Operation Block output will be pulled to zero when output and one of the input connected to each other and the wire between the two updates.
Requires a new block that provides stable feedback loop.

Const Signal Block and Operation block has a lot of functionalities that are the same

