package com.nhattuan.growrichs.Contants;

import com.nhattuan.growrichs.model.Song;

import java.util.ArrayList;
import java.util.List;

public class ListSong {
   public static List<Song> getListSong(){
       String[] mName = {"Beautiful.mp3"
               ,"Crazy chicken.mp3"
               ,"Despacito.mp3"
               ,"Love.mp3"
               ,"Morning.mp3"
               ,"Nicy.mp3"
               ,"Rehman.mp3"
               ,"Romance.mp3"
               ,"Tender.mp3"
               ,"Whistle.mp3"
       };
       String[] mTitle = {"Beautiful"
               ,"Crazy chicken"
               ,"Despacito"
               ,"Love"
               ,"Morning"
               ,"Nicy"
               ,"Rehman"
               ,"Romance"
               ,"Tender"
               ,"Whistle"
       };
       List<Song> list = new ArrayList<>();
       for (int i = 0; i < mName.length; i++){
        Song song = new Song(i,mName[i],mTitle[i]);
        list.add(song);
       }
       return list;
   }

    public static List<Song> getSongSecret(){
        String[] mName = {"tssben.mp3"

        };
        String[] mTitle = {"The Strangest Secret by Earl Nightingale"
        };
        List<Song> list = new ArrayList<>();
        for (int i = 0; i < mName.length; i++){
            Song song = new Song(i,mName[i],mTitle[i]);
            list.add(song);
        }
        return list;
    }


}
