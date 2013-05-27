/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Tetris;

import java.awt.Color;

import java.awt.Graphics;

/**
 *
 * @author fanzhidong
 */
public class SquareBlock {

    public static Color color=Color.CYAN;//边框线条颜色
    public static int unitSize=30;//规格一块的大小

    private Color fillColor;
    private int x;
    private int y;

    public SquareBlock()
    {
        this(0,0);
    }
    public SquareBlock(int x,int y)
    {
        this.x=x;
        this.y=y;
        this.fillColor=Color.gray;
    }

    public SquareBlock(SquareBlock another)
    {
        this.x=another.x;
        this.y=another.y;
        this.fillColor=another.fillColor;
    }

    public void setPosition(int x,int y)
    {
        this.x=x;
        this.y=y;
    }

    public void paint(Graphics g)
    {
        g.setColor(SquareBlock.color);
        int x0=getX()*unitSize;
        int y0=getY()*unitSize;
        g.drawRect(x0, y0, unitSize, unitSize);
        g.setColor(fillColor);
        g.fillRect(x0+1, y0+1, unitSize-2, unitSize-2);
    }

    /**
     * @param fillColor the fillColor to set
     */
    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

}
