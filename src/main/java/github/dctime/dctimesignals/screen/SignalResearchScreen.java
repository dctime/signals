package github.dctime.dctimesignals.screen;

import github.dctime.dctimesignals.menu.SignalResearchMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.joml.Quaternionf;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class SignalResearchScreen extends AbstractContainerScreen<SignalResearchMenu> {
    public SignalResearchScreen(SignalResearchMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        inputSignalQueues = List.of(new ArrayDeque<>(), new ArrayDeque<>(), new ArrayDeque<>());
        outputSignalQueues = List.of(new ArrayDeque<>(), new ArrayDeque<>(), new ArrayDeque<>());
    }

    List<Queue<Integer>> inputSignalQueues;
    List<Queue<Integer>> outputSignalQueues;
    private final int maxQueueSize = 100;


    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {

        int startX = (this.width - this.imageWidth)/2;
        int startY = (this.height - this.imageHeight)/2;
        int graphWidth = (int)(imageWidth*(0.8));
        int graphHeight = (int)(imageHeight*(0.2));
        int gapHeight = (int)(imageHeight*(0.1));

        renderSignalGraph(guiGraphics, inputSignalQueues, startX-graphWidth/2, startX+graphWidth/2, startY+gapHeight, graphHeight, gapHeight);
        renderSignalGraph(guiGraphics, outputSignalQueues, startX+imageWidth-graphWidth/2, startX+imageWidth+graphWidth/2, startY+gapHeight, graphHeight, gapHeight);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int startX = (this.width - this.imageWidth)/2;
        int startY = (this.height - this.imageHeight)/2;
        int graphWidth = (int)(imageWidth*(0.9));
        int graphHeight = (int)(imageHeight*(0.2));
        int gapHeight = (int)(imageHeight*(0.1));

        guiGraphics.drawString(this.font, this.title, -graphWidth/2, 0, 4210752, false);
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
    }

    private void renderSignalGraph(GuiGraphics guiGraphics, List<Queue<Integer>> listOfQueues, int x1, int x2, int y1, int graphHeight, int spaceBetweenGraph) {
        for (int queueIndex = 0; queueIndex < listOfQueues.size(); queueIndex++) {
            int minY = y1+(spaceBetweenGraph+graphHeight)*queueIndex;
            int maxY = y1+graphHeight+(spaceBetweenGraph+graphHeight)*queueIndex;
            int minX = x1;
            int maxX = x2;
            int graphWidth = maxX - minX;
            guiGraphics.fill(minX, minY, maxX, maxY, 0xFF000000);
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

            // draw center line
            int zeroY = (int)(maxY - (double)(0 - valueMin) / (valueMax - valueMin) * graphHeight);
            drawLine(guiGraphics, minX, zeroY, maxX, zeroY, 0x99FF0000, 1.0);
        }
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
