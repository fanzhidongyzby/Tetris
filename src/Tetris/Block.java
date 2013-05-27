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
public class Block {

    public static int[][][][] arrange=new int [][][][]//各种图形的排列数组arrange[type][rotation][x][y]
    {  
        {//I
            {{0,1,0,0},{0,1,0,0},{0,1,0,0},{0,1,0,0}},//0
            {{0,0,0,0},{1,1,1,1},{0,0,0,0},{0,0,0,0}},//90
            {{0,1,0,0},{0,1,0,0},{0,1,0,0},{0,1,0,0}},//180
            {{0,0,0,0},{1,1,1,1},{0,0,0,0},{0,0,0,0}}//270
        },
        {//S
            {{0,0,0,0},{0,1,1,0},{1,1,0,0},{0,0,0,0}},//0
            {{0,1,0,0},{0,1,1,0},{0,0,1,0},{0,0,0,0}},//90
            {{0,0,0,0},{0,1,1,0},{1,1,0,0},{0,0,0,0}},//180
            {{0,1,0,0},{0,1,1,0},{0,0,1,0},{0,0,0,0}}//270
        },
        {//Z
            {{0,0,0,0},{0,1,1,0},{0,0,1,1},{0,0,0,0}},//0
            {{0,0,1,0},{0,1,1,0},{0,1,0,0},{0,0,0,0}},//90
            {{0,0,0,0},{0,1,1,0},{0,0,1,1},{0,0,0,0}},//180
            {{0,0,1,0},{0,1,1,0},{0,1,0,0},{0,0,0,0}},//270
        },
        {//L
            {{0,1,0,0},{0,1,0,0},{0,1,1,0},{0,0,0,0}},//0
            {{0,0,0,0},{0,1,1,1},{0,1,0,0},{0,0,0,0}},//90
            {{0,0,0,0},{0,1,1,0},{0,0,1,0},{0,0,1,0}},//180
            {{0,0,0,0},{0,0,1,0},{1,1,1,0},{0,0,0,0}}//270
        },
        {//J
            {{0,0,1,0},{0,0,1,0},{0,1,1,0},{0,0,0,0}},//0
            {{0,0,0,0},{0,1,0,0},{0,1,1,1},{0,0,0,0}},//90
            {{0,1,1,0},{0,1,0,0},{0,1,0,0},{0,0,0,0}},//180
            {{0,0,0,0},{1,1,1,0},{0,0,1,0},{0,0,0,0}}//270
        },
        {//O
            {{0,0,0,0},{0,1,1,0},{0,1,1,0},{0,1,1,0}},//0
            {{0,0,0,0},{0,1,1,0},{0,1,1,0},{0,1,1,0}},//90
            {{0,0,0,0},{0,1,1,0},{0,1,1,0},{0,1,1,0}},//180
            {{0,0,0,0},{0,1,1,0},{0,1,1,0},{0,1,1,0}}//270
        },
        {//T
            {{0,0,0,0},{0,1,1,1},{0,0,1,0},{0,0,0,0}},//0
            {{0,0,1,0},{0,1,1,0},{0,0,1,0},{0,0,0,0}},//90
            {{0,0,1,0},{0,1,1,1},{0,0,0,0},{0,0,0,0}},//180
            {{0,0,1,0},{0,0,1,1},{0,0,1,0},{0,0,0,0}}//270
        }
    };
    public  SquareBlock[]sqrBlock=new SquareBlock[]//一个Block的四个SquareBlock对象
    {
        new SquareBlock(),new SquareBlock(),new SquareBlock(),new SquareBlock()
    };
    private int type;
    private int rotate;
    int x;
    int y;
    private Color fillColor;

    public  boolean generateNext;

    public Block(int x,int y,int type,int rotate)
    {
        this.x=x;
        this.y=y;
        this.type=type;
        this.rotate=rotate;
        this.boundColorToType();
        this.arrangeSquareBlock();
        generateNext=false;
    }

    public void setPosition(int x,int y)
    {
        this.x=x;
        this.y=y;
        this.arrangeSquareBlock();
    }
    public void  moveLeft()//有个覆盖的大问题没有解决！！！摸不着头脑！！！
    {
        this.x--;
        this.arrangeSquareBlock();
        if(this.isCollision())
        {
            this.x++;
            this.arrangeSquareBlock();
        }
    }
    public void moveRight()
    {
        this.x++;
        this.arrangeSquareBlock();
        if(this.isCollision())
        {
            this.x--;
            this.arrangeSquareBlock();
        }
    }
    public void drop()
    {
        this.y+=AppSet.defaultDropV;
        this.arrangeSquareBlock();
        if (this.isDeadCollision())//发生碰撞
        {
            this.y-=AppSet.defaultDropV;
            this.arrangeSquareBlock();//恢复位置
            return;//停止下落
        }
    }
    public void accelerate()
    {        
        this.y+=AppSet.defaultDropV;
        this.arrangeSquareBlock();
        if (this.isDeadCollision())//发生碰撞
        {
            this.y-=AppSet.defaultDropV;
            this.arrangeSquareBlock();//恢复位置
            return;//停止下落
        }
    }
    public void rotate()//与最初的设计息息需要相关....需要谨慎修改
    {
        this.rotate=(this.rotate+1)%4;
        this.arrangeSquareBlock();
        if(this.isCollision()||this.isDeadCollision())
        {
            this.rotate=Math.abs(this.rotate-1)%4;
            this.arrangeSquareBlock();
        }
    }
    public void paint(Graphics g)
    {
        for(int i=0;i<4;i++)
        {
            sqrBlock[i].setFillColor(fillColor);
            sqrBlock[i].paint(g);
        }
    }
    
    public boolean isCollision()
    {
        for(int i=0;i<4;i++)
        {
            if(sqrBlock[i].getX()<=-1||sqrBlock[i].getX()>=10)//出界
                return true;
            else //触及其他积累的块
            {
                for (int k = 0; k < 10; k++) {
                    for (int j = 0; j < 20; j++) {
                        if (AccBlock.accArrange[k][j] != null) {
                            if(sqrBlock[i].getY()==AccBlock.accArrange[k][j].getY()
                                    &&sqrBlock[i].getX()==AccBlock.accArrange[k][j].getX())//占用已有的块
                            {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean isDeadCollision()
    {
        for(int i=0;i<4;i++)
        {
            if(sqrBlock[i].getY()>=20)//落地,可能加速时越过边界进入死循环！！！
                return generateNext=true;
            else //落到其他积累的块
            {
                for (int k = 0; k < 10; k++) {
                    for (int j = 0; j < 20; j++) {
                        if (AccBlock.accArrange[k][j] != null) {
                            if(sqrBlock[i].getY()==AccBlock.accArrange[k][j].getY()
                                    &&sqrBlock[i].getX()==AccBlock.accArrange[k][j].getX())//占用已有的块
                            {
                                return generateNext=true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }


    private void boundColorToType()
    {
        switch(type)
        {
            case 0:this.fillColor=Color.red;
                break;
            case 1:this.fillColor=Color.WHITE;
                break;
            case 2:this.fillColor=Color.BLUE;
                break;
            case 3:this.fillColor=Color.ORANGE;
                break;
            case 4:this.fillColor=Color.GREEN;
                break;
            case 5:this.fillColor=Color.black;
                break;
            case 6:this.fillColor=Color.yellow;
                break;
        }
    }

    private void arrangeSquareBlock()//排列小块
    {
        int k=0;//记录第几块
        for(int i=0;i<4;i++)
        {
            for(int j=0;j<4;j++)
            {
                if(arrange[type][rotate][i][j]==1)//有块
                {
                    sqrBlock[k].setPosition(x+i, y+j);
                    k++;
                    if(k==4)
                        return;//所有块都已定位
                }
            }
        }
    }
    /**
     * @param type the type to set
     */
    public int getType() {
        return type;
    }

    /**
     * @param rotate the rotate to set
     */
    public int getRotate() {
        return rotate;
    }
}
