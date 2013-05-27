/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Tetris;

import java.awt.Canvas;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.Timer;



/**
 *
 * @author fanzhidong
 */
public class MainBack extends Canvas implements ActionListener,KeyListener{

    private Block curBlock;
    public static Block nextBlock;
    private AccBlock curAccBlock;
    private Timer timer;
    private boolean gameIsOver;
    private boolean fillIsOver;
    private boolean gameIsBegin;
    private boolean passIt;

    public MainBack()
    {

        timer=new Timer(500,this);        
        this.curAccBlock=new AccBlock();
        generateRadomBlock();//产生下一个块        
        gameIsOver=false;
        fillIsOver=false;
        gameIsBegin=false;
        passIt=false;
        this.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseReleased(MouseEvent e)
            {
                Tetris.instance.getBtnNewGame().requestFocus();
            }
        });
    }
    public void newGame()//开始新游戏
    {
        this.curAccBlock.clearArray();
        AppSet.score=0;
        gameIsOver=false;
        fillIsOver=false;
        gameIsBegin=true;
        passIt=false;

        
        curBlock=nextBlock;
        generateRadomBlock();//产生下一个块
        Tetris.instance.preView.repaint();//重绘小窗口

        getTimer().start();
    }

    public boolean passGame()
    {
        //判断是否通关
        if (AppSet.score >10000)//通关
        {
            this.getTimer().stop();
            AppSet.playBckMusic("./sound/pass.wav");
            String s = JOptionPane.showInputDialog(null, "恭喜你已经通关！请留下你的大名！");
            if (s == null) {
                Tetris.instance.getLbPlayer().setText("无名高人");
            } else {
                Tetris.instance.getLbPlayer().setText(s);
            }
            AppSet.hightestScore = AppSet.score;//更新最高分
            Tetris.instance.getLbHighScore().setText(String.valueOf(AppSet.score));

            gameIsOver=true;
            return true;
        }
        return false;
    }

    public void actionPerformed(ActionEvent e)
    {
        if(!AppSet.player.isAlive())//背景音乐停止
        {
            AppSet.playBckMusic("./sound/normal.wav");
        }
        if(e.getSource()==getTimer())
        {
            if(!gameIsOver)//游戏还没结束
            {
                curBlock.drop();
                if (curBlock.generateNext)
                {
                    AppSet.playGameMusic("./sound/collision.wav");//碰撞声音
                    Tetris.instance.preView.repaint();//重绘小窗口
                    curAccBlock.invalidateArray(curBlock);//刷新数组
                    curAccBlock.removeLine();//消行
                    passIt=this.passGame();

                    //nextBlock=generateRadomBlock();
                    curBlock=nextBlock;
                    generateRadomBlock();//取下一块
                    //nextBlock=generateRadomBlock();//再产生下一个方块

                    if (curBlock.isDeadCollision())//游戏结束！！！
                    {
                        gameIsOver=true;
                        AppSet.playBckMusic("./sound/fail.wav");//碰撞声音
                    }
                }                
            }
            else//游戏结束
            {
                if (!fillIsOver)
                {
                    curAccBlock.fillArray();
                    if (AccBlock.fillNum == -1)
                    {
                        fillIsOver=true;
                    }
                }
                else//填充完毕
                {
                    getTimer().stop();//停止动画                    
                }              
                
            }
            this.repaint();
        }
    }

    @Override
    public void paint(Graphics g)
    {
        if(this.gameIsBegin&&!this.gameIsOver||this.passIt)
            curBlock.paint(g);
        curAccBlock.paint(g);
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

    private void generateRadomBlock()
    {
        int type = (int) (Math.random() * 7);//随机产生类型
        int rotate = (int) (Math.random() * 4);//随即产生旋转
        nextBlock= new Block(3,0,type,rotate);
    }

    //键盘事件,由窗口监听
    public void keyPressed(KeyEvent e) {
        if(this.gameIsOver||!this.gameIsBegin)
            return;
        if (getTimer().isRunning())
        {
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                curBlock.rotate();
                AppSet.playGameMusic("./sound/rotate.wav");
                this.repaint();
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                curBlock.moveLeft();
                AppSet.playGameMusic("./sound/change.wav");
                this.repaint();
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                curBlock.moveRight();
                AppSet.playGameMusic("./sound/change.wav");
                this.repaint();
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                curBlock.accelerate();
                AppSet.playGameMusic("./sound/change.wav");
                this.repaint();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (this.getTimer().isRunning()) {
                this.getTimer().stop();
            } else {
                this.getTimer().start();
            }
        }
    }

    /**
     * @return the timer
     */
    public Timer getTimer() {
        return timer;
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

}
