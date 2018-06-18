package com.nhattuan.growrichs.Contants;

import com.nhattuan.growrichs.model.ObjectLogan;

import java.util.ArrayList;
import java.util.List;

public class ListLogan {
    private static final String TAG = "ListLogan";

    public static List<ObjectLogan> getList() {

        String Morningquote = "Don’t make change too complicated , just begin\n" +
                "Never forget how wildly capable you are\n" +
                "Don't limit your challenges , challenge your limits\n" +
                "Show up even when no one applauds you for it.\n" +
                "The harder you work, the luckier you get\n" +
                "Pour your heart and soul into everything you do\n" +
                "Make it happen. Shock everyone\n" +
                "Do not wait around for someone else's approval. All you need is your own \n" +
                "Think big, start small, begin now\n" +
                "Do what you have to do until you can do what you want to do\n" +
                "Push harder than yesterday if you want a difference tomorrow\n" +
                "I can and I will . Watch me\n" +
                "If it doesn't challenge you, it won't change you\n" +
                "You will never change your life until you change something you do daily. The secret of your success is found in your daily routine. \n" +
                "We all have problems. The way we solve them is what makes us different\n" +
                "Stop overthinking things and just do\n" +
                "If you can dream it. you can do it\n" +
                "I am in charge of how I feel and today I'm choosing happiness.\n" +
                "If you aren't willing to work for it. Don't complain about not having it.\n" +
                "Make today magical\n" +
                "Do more of what makes you happy\n" +
                "Stop waiting for perfect moment. The perfect moment is now.\n" +
                "Believe in yourself you will be unstoppable.\n" +
                "Life is so much simpler when you stop explaining yourself to people and just do what works for you.\n" +
                "You can only get better through actions\n" +
                "Notice everything but keep your mouth shut\n" +
                "Stand for what is right even if that means standing alone.\n" +
                "Too many of us are not living our dreams because we are living our fears.\n" +
                "Focus on the essentials and get rid of everything else\n" +
                "Like is tough, my darling but so are you.\n" +
                "Don’t make change too complicated , just begin\n" +
                "Never forget how wildly capable you are";
        String Eveningquote = "If you want something you never had, you need to do something you’ve never done\n" +
                "You are not how other people see you\n" +
                "You are good enough, smart enough, beautiful enough and strong enough. Believe it and never let insecurity run your life.\n" +
                "The less you response to negative people, the more peaceful your life will become.\n" +
                "You can handle whatever life throws at you\n" +
                "Stop being afraid of what could go wrong and start being excited about what could go right\n" +
                "Success seems to be connected with action. Successful people keep moving. They make mistakes but don't quit\n" +
                "You can\n" +
                "Believe in yourself and where you're going\n" +
                "Stop doubting yourself, work hard and make it happen\n" +
                "Wake up with determination. Go to bed with satisfaction\n" +
                "Train your mind to see the good in everything\n" +
                "Things get worse before it gets better...Just keep going\n" +
                "Give youself some credits because you're doing great\n" +
                "Failure is not the opposite of success. It is part of success\n" +
                "Stop being distracted by the things that have nothing to do with your goals\n" +
                "Sometimes I feel like giving up then I remember I have a lot motherfuckers to prove wrong\n" +
                "Losers stop when they fail. Winners fail until they succeed.\n" +
                "Believe you can , you're half way there\n" +
                "Sometimes miracles are just good people with kind hearts\n" +
                "In the end we only regret the chances we didn’t take.\n" +
                "Sunsets are proof that endings can be beautiful\n" +
                "Remember that stress doesn't come from what's going on in your life . It comes from your thoughts about what's going on in your life.\n" +
                "Thank for the pain. It makes me raised my game\n" +
                "Holding on is believing that there's a past. Letting go is knowing there's a future.\n" +
                "A corward gets scared and quits. A hero gets scared, but still goes on. \n" +
                "One of hapiness moments in life is when you find the courage to let go of what you can't change\n" +
                "Stars can't shine without darkness.\n" +
                "A diamond is a chunk of coal that did well under pressure\n" +
                "Ask yourself if what you're doing today is getting you closer to where you want to be tomorrow.\n" +
                "If you want something you never had, you need to do something you’ve never done\n" +
                "You are not how other people see you";

        String[] morning = convertStringToArray(Morningquote);
        String[] evening = convertStringToArray(Eveningquote);
        List<ObjectLogan> list = new ArrayList<>();
        for (int i = 0; i < morning.length; i++) {
            //Log.d(TAG, "getList: "+i+" "+morning[i]);
            list.add(new ObjectLogan(i, morning[i], evening[i]));
        }

        return list;
    }

    public static String strSeparator = "\n";

    public static String convertArrayToString(String[] array) {
        String str = "";
        for (int i = 0; i < array.length; i++) {
            str = str + array[i];
            // Do not append comma at the end of last element
            if (i < array.length - 1) {
                str = str + strSeparator;
            }
        }
        return str;
    }

    public static String[] convertStringToArray(String str) {
        String[] arr = str.split(strSeparator);
        return arr;
    }
}
