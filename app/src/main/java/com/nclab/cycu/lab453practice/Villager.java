package com.nclab.cycu.lab453practice;

import android.widget.ImageView;

public class Villager {
    public static final int DRAWABLE_RESOURCE = R.drawable.villager;

    public static final String JOB_NONE = "閒置村民";
    public static final String JOB_LUMBERJACK = "伐木工";
    public static final String JOB_FARMER = "農夫";
    public static final String JOB_GOLD_MINER = "金礦工";
    public static final String JOB_STONE_MINER = "石礦工";
    private Player mPlayer;
    private ImageView mImageView;
    private VillagerThread mVillagerThread;
    private boolean mIsAlive;
    private int mHp;
    private int mMaxHp;
    private int mAttack;
    private int mAttackRange;
    private int mNearDefence;
    private int mFarDefence;
    private String mJob;

    /**
     * @param player 擁有這個村民的Player
     * @param villageImageView 顯示在畫面上的ImageView
     */
    public Villager(Player player, ImageView villageImageView) {
        mPlayer = player; //把擁有這個村民的player記錄起來，供以後使用
        mImageView = villageImageView;
        mIsAlive = true;
        mHp = 25;
        mMaxHp = 25;
        mAttack = 3;
        mAttackRange = 0;
        mNearDefence = 0;
        mFarDefence = 0;
        mJob = JOB_NONE;

        //創建屬於村民的執行序，並讓它開始。
        mVillagerThread = new VillagerThread();
        mVillagerThread.start();
    }

    public ImageView getImageView() {
        return mImageView;
    }

    public boolean isAlive() {
        return mIsAlive;
    }

    /**
     * 設定村民的工作
     *
     * @param job JOB_NONE, JOB_LUMBERJACK, JOB_FARMER, JOB_GOLD_MINER, JOB_STONE_MINER
     */
    public void setJob(String job) {
        mJob = job;
    }

    public String getJob() {
        return mJob;
    }

    synchronized public void setHp(int hp) {
        mHp = hp;
        if (hp <= 0) {
            mIsAlive = false;
        }
    }

    public int getHp() {
        return mHp;
    }

    public int getNearDefence() {
        return mNearDefence;
    }

    /**
     * 村民的執行序，繼承於Thread
     */
    private class VillagerThread extends Thread {
        @Override
        public void run() {
            while (mIsAlive) {
                //每秒一次
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //根據村民的工作做對應的事情
                switch (mJob) {
                    case JOB_NONE:
                        //Do nothing.
                        break;

                    case JOB_LUMBERJACK:
                        //玩家的木材+1
                        mPlayer.setWood(mPlayer.getWood() + 1);
                        break;

                    case JOB_FARMER:
                        //玩家的食物+1
                        mPlayer.setFood(mPlayer.getFood() + 1);
                        break;

                    case JOB_GOLD_MINER:
                        //玩家的金礦+1
                        mPlayer.setGold(mPlayer.getGold() + 1);
                        break;

                    case JOB_STONE_MINER:
                        //玩家的石頭+1
                        mPlayer.setStone(mPlayer.getStone() + 1);
                        break;
                }
            }
        }
    }
}
