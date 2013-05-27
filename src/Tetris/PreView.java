/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Tetris;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author fanzhidong
 */
public class PreView extends Canvas {

    PreView()
    {
        this.addMouseListener(new MouseAdapter()
        {
            public void mouseReleased(MouseEvent e)
            {
                Tetris.instance.getBtnNewGame().requestFocus();
            }
        });
    }
    @Override
    public void paint(Graphics g)
    {
        new Block(0,0,MainBack.nextBlock.getType(),MainBack.nextBlock.getRotate()).paint(g);
    }
    @Override
    public void update(Graphics g)
    {
        Image buffer = createImage(getWidth(), getHeight());
        Graphics GraImage = buffer.getGraphics();
        paint(GraImage);
        GraImage.dispose();
        g.drawImage(buffer,0,0,null);
    }

}
