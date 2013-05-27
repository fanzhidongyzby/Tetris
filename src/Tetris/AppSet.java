/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Tetris;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.audio.AudioPlayer;

/**
 *
 * @author fanzhidong
 */
public class AppSet  {

    public static int defaultDropV=1;//默认下落速度
    public static boolean isChangeType=false;//是否支持变型
    public static int score=0;
    public static int hightestScore=1000;


    private static InputStream inputStream;//音乐文件流

    public  static AudioPlayer player=AudioPlayer.player;//音乐控制线程


    public AppSet()
    {
        
    }

    public static void playBckMusic(String bckMusic)
    {        
        if(player.isAlive())//有音乐线程
        {
            player.stop(inputStream);//停止旧音乐线程
        }
        try {
            inputStream = new FileInputStream(new File(bckMusic));//初始化文件流
        } catch (FileNotFoundException ex) {

        }
        player.start(inputStream);
    }

    public static void playGameMusic(String gameMusic)
    {
        try {
            player.start(new FileInputStream(new File(gameMusic)));
        } catch (FileNotFoundException ex) {

        }
    }
      

}
