package epiccatto.catto.utils.render.tweek;

import epiccatto.catto.utils.ChatUtil;

import java.awt.*;

public class ColorTween {
    private Color color1;
    private Color color2;
    private long time;
    private long startTime;


    // Progress is a value between 0 and 1
    private double progress;
    // Current color
    private Color currentColor = new Color(0, 0, 0, 0);

    public ColorTween(int color1, int color2, long time) {
        this.color1 = new Color(color1);
        this.color2 = new Color(color2);
        this.time = time;
        this.startTime = System.currentTimeMillis();
        this.progress = 0;
    }

    public ColorTween(Color color1, Color color2, long time) {
        this.color1 = color1;
        this.color2 = color2;
        this.time = time;
        this.startTime = System.currentTimeMillis();
        this.progress = 0;
    }

    public int getColor(){
        return currentColor.getRGB();
    }

    public Color getCurrentColor(){
        return currentColor;
    }

    public void update(){
        if (progress >= 1){
            progress = 1;
            return;
        }
        // Start slowly increasing progress animation from 0 to 1
        progress = (System.currentTimeMillis() - startTime) / (double) time;

        currentColor = lerpColor(color1, color2, progress);

        ChatUtil.sendChatMessageWOutPrefix("Progress: " + progress + " Color: " + getCurrentColor().getRed() + " " + getCurrentColor().getGreen() + " " + getCurrentColor().getBlue() + " " + getCurrentColor().getAlpha());

    }

    public boolean isDone(){
        return progress >= 1;
    }

    public void reset(){
        progress = 0;
        startTime = System.currentTimeMillis();
    }

    private Color lerpColor(Color color1, Color color2, double progress){
        int red = (int) lerp(color1.getRed(), color2.getRed(), progress);
        int green = (int) lerp(color1.getGreen(), color2.getGreen(), progress);
        int blue = (int) lerp(color1.getBlue(), color2.getBlue(), progress);
        int alpha = (int) lerp(color1.getAlpha(), color2.getAlpha(), progress);

        return new Color(red, green, blue, alpha);
    }

    private float lerp(float a, float b, double progress){
        return (float) (a + progress * (b - a));
    }

    public void setColor1(int color1) {
        this.color1 = new Color(color1, true);
    }

    public void setColor2(int color2) {
        this.color2 = new Color(color2, true);
    }
    public void setColor1(Color color1) {
        this.color1 = color1;
    }

    public void setColor2(Color color2) {
        this.color2 = color2;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
