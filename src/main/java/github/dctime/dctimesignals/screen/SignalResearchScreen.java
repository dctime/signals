package github.dctime.dctimesignals.screen;

import github.dctime.dctimesignals.block.SignalResearchStationBlockEntity;
import github.dctime.dctimesignals.menu.SignalResearchMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.joml.Quaternionf;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class SignalResearchScreen extends AbstractContainerScreen<SignalResearchMenu> {
    public SignalResearchScreen(SignalResearchMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        inputSignalQueues = List.of(new ArrayDeque<>(), new ArrayDeque<>(), new ArrayDeque<>());
        outputSignalQueues = List.of(new ArrayDeque<>(), new ArrayDeque<>(), new ArrayDeque<>());
        requiredInputSignalQueues = List.of(new ArrayDeque<>(), new ArrayDeque<>(), new ArrayDeque<>());
    }

    List<Queue<Integer>> inputSignalQueues;
    List<Queue<Integer>> outputSignalQueues;
    List<Queue<Integer>> requiredInputSignalQueues;
    private final int maxQueueSize = 100;


    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {

        int startX = (this.width - this.imageWidth)/2;
        int startY = (this.height - this.imageHeight)/2;
        int graphWidth = (int)(imageWidth*(0.8));
        int graphHeight = (int)(imageHeight*(0.2));
        int gapHeight = (int)(imageHeight*(0.1));

        // invalid output ports
        if (menu.getFlagsData().get(SignalResearchStationBlockEntity.DATA_FLAGS_MULTIBLOCK_INVALID_INDEX) > 0) {
            int textWidth = this.font.width("Multiblock invalid! Try reassembling the multiblock.");
            guiGraphics.fill(startX+imageWidth/2-textWidth/2, startY+imageHeight/2, startX+imageWidth/2+textWidth/2, startY+imageHeight/2, 0xFF000000);
            guiGraphics.drawString(this.font, "Multiblock invalid! Try reassembling the multiblock.", startX+imageWidth/2-textWidth/2, startY+imageHeight/2, 0xFFFF0000);
            return;
        }

        renderSignalGraph(guiGraphics, inputSignalQueues, requiredInputSignalQueues, startX-graphWidth/2, startX+graphWidth/2, startY+gapHeight, graphHeight, gapHeight, "Signal Read");
        renderSignalGraph(guiGraphics, outputSignalQueues, startX+imageWidth-graphWidth/2, startX+imageWidth+graphWidth/2, startY+gapHeight, graphHeight, gapHeight, "Signal Export");
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
//        int startX = (this.width - this.imageWidth)/2;
//        int startY = (this.height - this.imageHeight)/2;
//        int graphWidth = (int)(imageWidth*(0.9));
//        int graphHeight = (int)(imageHeight*(0.2));
//        int gapHeight = (int)(imageHeight*(0.1));
//
//        guiGraphics.drawString(this.font, this.title, -graphWidth/2, 0, 4210752, false);
    }

    @Override
    protected void containerTick() {
        // get time stamp
        if (Minecraft.getInstance().level == null) return;
        int time = Math.toIntExact(Minecraft.getInstance().level.getGameTime() % 1);
        if (time == 0) {
            updateSignalQueues();
        }
    }

    private void updateSignalQueues() {
        for (int inputSignalIndex = 0; inputSignalIndex < this.getMenu().getInputSignalData().getCount(); inputSignalIndex++) {
            int value = this.getMenu().getInputSignalData().get(inputSignalIndex);
            Queue<Integer> targetQueue = inputSignalQueues.get(inputSignalIndex);
            if (targetQueue.size() >= maxQueueSize) {
                targetQueue.poll();
            }
            targetQueue.offer(value);
        }

        for (int outputSignalIndex = 0; outputSignalIndex < this.getMenu().getOutputSignalData().getCount(); outputSignalIndex++) {
            int value = this.getMenu().getOutputSignalData().get(outputSignalIndex);
            Queue<Integer> targetQueue = outputSignalQueues.get(outputSignalIndex);
            if (targetQueue.size() >= maxQueueSize) {
                targetQueue.poll();
            }
            targetQueue.offer(value);
        }

        for (int requiredInputQueue = 0; requiredInputQueue < this.getMenu().getRequiredInputSignalData().getCount(); requiredInputQueue++) {
            int value = this.getMenu().getRequiredInputSignalData().get(requiredInputQueue);
            Queue<Integer> targetQueue = requiredInputSignalQueues.get(requiredInputQueue);
            if (targetQueue.size() >= maxQueueSize) {
                targetQueue.poll();
            }
            targetQueue.offer(value);
        }
    }

    private void renderSignalGraph(GuiGraphics guiGraphics, List<Queue<Integer>> listOfQueues, List<Queue<Integer>> secondaryListOfQueues, int x1, int x2, int y1, int graphHeight, int spaceBetweenGraph, String graphName) {
        for (int queueIndex = 0; queueIndex < listOfQueues.size(); queueIndex++) {
            int minY = y1+(spaceBetweenGraph+graphHeight)*queueIndex;
            int maxY = y1+graphHeight+(spaceBetweenGraph+graphHeight)*queueIndex;
            int minX = x1;
            int maxX = x2;
            int graphWidth = maxX - minX;
            // background
            guiGraphics.fill(minX, minY, maxX, maxY, 0xFF000000);

            String callSign;
            switch (queueIndex) {
                case 0 -> callSign = "A";
                case 1 -> callSign = "B";
                case 2 -> callSign = "C";
                default -> callSign = "Unknown";
            }

            guiGraphics.drawString(this.font, callSign, minX-spaceBetweenGraph-this.font.width(callSign), (minY+maxY)/2-this.font.lineHeight/2, 0xFFFFFFFF);
            Queue<Integer> targetQueue = listOfQueues.get(queueIndex);
            Queue<Integer> secondaryTargetQueue = secondaryListOfQueues.get(queueIndex);


            int targetQueueSize = targetQueue.size();
            if (targetQueueSize != secondaryTargetQueue.size()) {
                System.out.println("Warning: Target queue size does not match secondary target queue size for graph: " + graphName);
                return;
            }

            double widthBetweenValues = (double)graphWidth / (targetQueueSize-1);

            int valueMin = Math.min(getMinFromQueue(targetQueue), getMinFromQueue(secondaryTargetQueue));
            int valueMax = Math.max(getMaxFromQueue(targetQueue), getMaxFromQueue(secondaryTargetQueue));

            if (Math.abs(valueMin) > Math.abs(valueMax)) {
                valueMax = Math.abs(valueMin);
                valueMin = -Math.abs(valueMin);
            } else if (Math.abs(valueMax) > Math.abs(valueMin)) {
                valueMin = -Math.abs(valueMax);
                valueMax = Math.abs(valueMax);
            } else if (Math.abs(valueMax) == Math.abs(valueMin)) {
                valueMin = -Math.abs(valueMax);
                valueMax = Math.abs(valueMax);
            }

            if (valueMax == 0) {
                valueMin = -1;
                valueMax = 1;
            }
            // draw center line
            int zeroY = (int)(maxY - (double)(0 - valueMin) / (valueMax - valueMin) * graphHeight);
            drawLine(guiGraphics, minX, zeroY, maxX, zeroY, 0x99FF0000, 1.0);

            int lastX = -1;
            int lastY = -1;
            int valueIndex = 0;

            for (Integer value : secondaryTargetQueue) {
                if (lastX == -1 || lastY == -1) {
                    lastX = minX;
                    lastY = (int)(maxY - (double)(value - valueMin) / (valueMax - valueMin) * graphHeight);
//                    String text = ""+value;
//                    guiGraphics.drawString(this.font, text, lastX - this.font.width(text)-1, lastY - 5, 0xFFFFFF);
                } else {
                    int x = (int)(minX + valueIndex * widthBetweenValues);
                    int y = (int)(maxY - (double)(value - valueMin) / (valueMax - valueMin) * graphHeight);
                    drawLine(guiGraphics, lastX, lastY, x, y, 0x9900FF00, 1.0);
                    lastX = x;
                    lastY = y;
                }

                valueIndex++;
            }

            String text = ""+((ArrayDeque<Integer>)secondaryTargetQueue).peekLast();
            guiGraphics.drawString(this.font, text, lastX+1+this.font.width(""+((ArrayDeque<Integer>)targetQueue).peekLast()+1), lastY - 5, 0x9900FF00);

            lastX = -1;
            lastY = -1;
            valueIndex = 0;

            for (Integer value : targetQueue) {
                if (lastX == -1 || lastY == -1) {
                    lastX = minX;
                    lastY = (int)(maxY - (double)(value - valueMin) / (valueMax - valueMin) * graphHeight);
//                    String text = ""+value;
//                    guiGraphics.drawString(this.font, text, lastX - this.font.width(text)-1, lastY - 5, 0xFFFFFF);
                } else {
                    int x = (int)(minX + valueIndex * widthBetweenValues);
                    int y = (int)(maxY - (double)(value - valueMin) / (valueMax - valueMin) * graphHeight);
                    drawLine(guiGraphics, lastX, lastY, x, y, 0xFFFFFFFF, 1.0);
                    lastX = x;
                    lastY = y;
                }

                valueIndex++;
            }

            text = ""+((ArrayDeque<Integer>)targetQueue).peekLast();
            guiGraphics.drawString(this.font, text, lastX+1, lastY - 5, 0xFFFFFF);
        }

        int endX = x1;
        int endY = y1+graphHeight+(spaceBetweenGraph+graphHeight)*(listOfQueues.size()-1)+ spaceBetweenGraph/2;
        guiGraphics.drawString(this.font, graphName, endX, endY, 0xFFFFFF);
    }

    private void renderSignalGraph(GuiGraphics guiGraphics, List<Queue<Integer>> listOfQueues, int x1, int x2, int y1, int graphHeight, int spaceBetweenGraph, String graphName) {
        for (int queueIndex = 0; queueIndex < listOfQueues.size(); queueIndex++) {
            int minY = y1+(spaceBetweenGraph+graphHeight)*queueIndex;
            int maxY = y1+graphHeight+(spaceBetweenGraph+graphHeight)*queueIndex;
            int minX = x1;
            int maxX = x2;
            int graphWidth = maxX - minX;
            // background
            guiGraphics.fill(minX, minY, maxX, maxY, 0xFF000000);

            String callSign;
            switch (queueIndex) {
                case 0 -> callSign = "A";
                case 1 -> callSign = "B";
                case 2 -> callSign = "C";
                default -> callSign = "Unknown";
            }

            guiGraphics.drawString(this.font, callSign, (int)(maxX+spaceBetweenGraph*1.5), (minY+maxY)/2-this.font.lineHeight/2, 0xFFFFFFFF);

            Queue<Integer> targetQueue = listOfQueues.get(queueIndex);

            int valueIndex = 0;
            int targetQueueSize = targetQueue.size();


            double widthBetweenValues = (double)graphWidth / (targetQueueSize-1);
            int lastX = -1;
            int lastY = -1;
            int valueMin = getMinFromQueue(targetQueue);
            int valueMax = getMaxFromQueue(targetQueue);


            if (Math.abs(valueMin) > Math.abs(valueMax)) {
                valueMax = Math.abs(valueMin);
                valueMin = -Math.abs(valueMin);
            } else if (Math.abs(valueMax) > Math.abs(valueMin)) {
                valueMin = -Math.abs(valueMax);
                valueMax = Math.abs(valueMax);
            } else if (Math.abs(valueMax) == Math.abs(valueMin)) {
                valueMin = -Math.abs(valueMax);
                valueMax = Math.abs(valueMax);
            }

            if (valueMax == 0) {
                valueMin = -1;
                valueMax = 1;
            }
            // draw center line
            int zeroY = (int)(maxY - (double)(0 - valueMin) / (valueMax - valueMin) * graphHeight);
            drawLine(guiGraphics, minX, zeroY, maxX, zeroY, 0x99FF0000, 1.0);

            for (Integer value : targetQueue) {
                if (lastX == -1 || lastY == -1) {
                    lastX = minX;
                    lastY = (int)(maxY - (double)(value - valueMin) / (valueMax - valueMin) * graphHeight);
//                    String text = ""+value;
//                    guiGraphics.drawString(this.font, text, lastX - this.font.width(text)-1, lastY - 5, 0xFFFFFF);
                } else {
                    int x = (int)(minX + valueIndex * widthBetweenValues);
                    int y = (int)(maxY - (double)(value - valueMin) / (valueMax - valueMin) * graphHeight);
                    drawLine(guiGraphics, lastX, lastY, x, y, 0xFFFFFFFF, 1.0);
                    lastX = x;
                    lastY = y;
                }

                valueIndex++;
            }

            String text = ""+((ArrayDeque<Integer>)targetQueue).peekLast();
            guiGraphics.drawString(this.font, text, lastX+1, lastY - 5, 0xFFFFFF);
        }

        int endX = x1;
        int endY = y1+graphHeight+(spaceBetweenGraph+graphHeight)*(listOfQueues.size()-1)+ spaceBetweenGraph/2;
        guiGraphics.drawString(this.font, graphName, endX, endY, 0xFFFFFF);
    }

    private int getMaxFromQueue(Queue<Integer> queue) {
        return queue.stream().max(Integer::compareTo).orElse(0);
    }

    private int getMinFromQueue(Queue<Integer> queue) {
        return queue.stream().min(Integer::compareTo).orElse(0);
    }

    private void drawLine(GuiGraphics guiGraphics, int x1, int y1, int x2, int y2, int color, double width) {
        guiGraphics.pose().pushPose();
        double length = Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));
        double angle = Math.atan((double)(y2-y1)/(x2-x1));
        guiGraphics.pose().rotateAround(new Quaternionf().rotateZ((float)angle), x1, y1, 0);
        guiGraphics.fill(x1, (int)(y1-width/2), (int)(x1+length), (int)(y1+width/2), color);
        guiGraphics.pose().popPose();
    }
}
