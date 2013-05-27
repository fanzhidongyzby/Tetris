/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Tetris;

import java.awt.Graphics;
import javax.swing.JOptionPane;

/**
 *
 * @author fanzhidong
 */
public class AccBlock {

    public static SquareBlock [][] accArrange=new SquareBlock[10][20];

    public static int fillNum=19;//一次填充后的下次填充的开始行号

    public AccBlock()
    {

    }

    public void fillArray()//填充数组,游戏结束时执行的动画,返回一次填充后的最高的行号
    {
        int fillNums=4;//一次填充的行数
        for(int i=fillNum;i>fillNum-fillNums;i--)
        {
            for(int j=0;j<10;j++)
            {
                if(accArrange[j][i]==null)
                    accArrange[j][i]=new SquareBlock(j,i);
            }
        }
        fillNum-=fillNums;//修改开始行号

    }

    public void clearArray()//清空数组
    {
        for(int i=0;i<10;i++)
            for(int j=0;j<20;j++)
            {
                accArrange[i][j]=null;
            }
        fillNum=19;//还原
    }

    public int getHeight()
    {
        for(int i=0;i<19;i++)
        {
            for(int j=0;j<10;j++)
            {
                if(accArrange[j][i]!=null)
                {
                    return 20-i;
                }
            }
        }
        return 0;
    }
    public   void  invalidateArray(Block block)//刷新累计数组
    {
        for(int i=0;i<4;i++)
        {
            int x=block.sqrBlock[i].getX();
            int y=block.sqrBlock[i].getY();
            if(y>=0&&x>=0&&x<=9)//出现在区域的快
                accArrange[x][y]=new SquareBlock(block.sqrBlock[i]);
        }
    }
    public void removeLine()//消除完整行算法
    {
        int []remove=new int []{-1,-1,-1,-1};
        int k=0;
        for(int i=0;i<20;i++)//行
        {
            boolean haveFullLine=true;
            for(int j=0;j<10;j++)//列
            {
                if(accArrange[j][i]==null)
                {
                    haveFullLine=false;
                    break;//有空缺,跳过
                }
            }
            if(haveFullLine)
            {
                remove[k]=i;//记录能消行的行号
                k++;
            }
        }

        String music = null;
        switch ((int)(Math.random()*4))
        {
            case 0:
                music="./sound/normal.wav";
                break;
            case 1:
                music="./sound/welcome.wav";
                break;
            case 2:
                music="./sound/start.wav";
            case 3:
                music="./sound/getScore.wav";
                break;
        }
        switch (k)
        {
            case 1:
                AppSet.score += 10;
                AppSet.playGameMusic("./sound/1.wav");
                AppSet.playBckMusic(music);//得分切背景音乐
                break;
            case 2:
                AppSet.score += 30;
                AppSet.playGameMusic("./sound/2.wav");
                AppSet.playBckMusic(music);//得分切背景音乐
                break;
            case 3:
                AppSet.score += 60;
                AppSet.playGameMusic("./sound/34.wav");
                AppSet.playBckMusic(music);//得分切背景音乐
                break;
            case 4:
                AppSet.score += 100;
                AppSet.playGameMusic("./sound/34.wav");
                AppSet.playBckMusic(music);//得分切背景音乐
                break;
        }

        Tetris.instance.getLbScore().setText(String.valueOf(AppSet.score));

        
        String s;
        if(AppSet.score>=10000)
        {
            s="独孤求败";
        }
        else if(AppSet.score>=6000)
        {
            s="武林盟主";
        }
        else if(AppSet.score>=3000)
        {
            s="一代豪侠";
        }
        else if(AppSet.score>=1000)
        {
            s="名动一方";
        }
        else
        {
            s="初入江湖";
        }
        Tetris.instance.getLbLevel().setText(s);

        for(int h=0;h<k;h++)//找出所有的行
        {
            //System.out.println(remove[h]);
            for(int i=remove[h]-1;i>=0;i--)//消行上边的所有行(逆序)
            {
                for(int j=0;j<10;j++)//遍历各个单元格
                {
                    //System.out.println("第"+i+"行移动到第"+i+1+"行");
                    if(accArrange[j][i]!=null)
                        accArrange[j][i].setPosition(accArrange[j][i].getX(),accArrange[j][i].getY()+1);
                    accArrange[j][i+1]=accArrange[j][i];//平移
                }
            }
        }
    }
    public void paint(Graphics g)
    {
        for(int i=0;i<10;i++)
            for(int j=0;j<20;j++)
            {
                if(accArrange[i][j]!=null)
                    accArrange[i][j].paint(g);
            }
    }
}
