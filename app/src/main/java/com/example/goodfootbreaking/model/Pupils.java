package com.example.goodfootbreaking.model;

import java.io.Serializable;

// Описание класса Pupils
public class Pupils implements Serializable {

    //---------- Personal information -----------
    private String id, name, email;
    private int status;         /* 0 - не указан; 1 - дети до 7 лет; 2 - начинающие; 3 - второгодки
                                   4 - продолжающие; 5 - kidsPro; 6 - преподаватель
                                */
    private int subscription;   /* 5 - 1 месяц; 2 - 3 месяца; 3 - 5 месяцев; 4 - 10 месяцев;
                                   10 - без ограничений
                                */
    private int subscriptionDay;
    private int subscriptionMonth;
    private int subscriptionYear;
    //---------- Personal information -----------

    //---------- Rating -----------
    private double rating, frezze_rating, powermove_rating, ofp_rating, streching_rating;
    private int  battle_rating, battle_cur_position, battle_new_position, current_position, new_position;
    //---------- Rating -----------

    //---------- FREZZE -----------
    public int babyfrezze, chairfrezze, elbowfrezze, headfrezze, headhollowbackfrezze;
    public int hollowbackfrezze, invertfrezze, onehandfrezze, shoulderfrezze, turtlefrezze;
    //---------- FREZZE -----------

    //-------- POWER MOVE -------------
    public int airflare, backspin, cricket, elbowairflare, flare, jackhammer, halo, headspin;
    public int munchmill, ninety_nine, swipes, turtle, ufo, web, windmill, wolf;
    //-------- POWER MOVE -------------

    //----------- OFP -------------
    public int angle, bridge, finger, handstand, horizont, pushups;
    //----------- OFP -------------

    //-------- stretching -------------
    public int butterfly, fold, shoulders, twine;
    //-------- stretching -------------

    // Инициализатор
    public Pupils(){}

    // Инициализатор root
    public Pupils(String name)
    {
        id = "Pupils";
        this.name = name;
        email = name;
        status = 6;
        subscription = 10;
        subscriptionDay = 15;
        subscriptionMonth = 5; //июнь
        subscriptionYear = 2021;

        rating = 100; frezze_rating = 100; powermove_rating = 100; ofp_rating = 100; streching_rating = 100;
        battle_rating = 100;  battle_cur_position = 0; battle_new_position = 0; current_position = 0; new_position = 0;

        babyfrezze = 78; chairfrezze = 12; elbowfrezze = 34; headfrezze = 71; headhollowbackfrezze = 70;
        hollowbackfrezze = 34; invertfrezze = 24; onehandfrezze = 23; shoulderfrezze = 88; turtlefrezze = 72;

        airflare = 70; backspin = 90; cricket = 45; elbowairflare = 45; flare = 67; jackhammer = 12; halo = 67; headspin = 99;
        munchmill = 67; ninety_nine = 34; swipes = 87; turtle = 67; ufo = 34; web = 65; windmill = 87; wolf = 56;

        angle = 46; bridge = 91; finger = 67; handstand = 69; horizont = 45; pushups = 78;
        butterfly = 61; fold = 20; shoulders = 80; twine = 56;

    }

    public String getId() {
          return id;
    }
    public void setId(String id) {
          this.id = id;
    }

    public String getName() {
          return name;
    }
    public void setName(String name) {
          this.name = name;
    }

    public String getEmail() {
          return email;
     }
    public void setEmail(String email) {
        this.email = email;
    }

    public void setStatus(int status) {
          this.status = status;
     }
    public int getStatus() {
           return status;
      }

    public int getSubscription() {
        return subscription;
    }
    public void setSubscription(int subscription) {
        this.subscription = subscription;
    }

    public int getSubscriptionDay() {
        return subscriptionDay;
    }
    public void setSubscriptionDay(int subscriptionDay) {
        this.subscriptionDay = subscriptionDay;
    }

    public int getSubscriptionMonth() {
        return subscriptionMonth;
    }
    public void setSubscriptionMonth(int subscriptionMonth) { this.subscriptionMonth = subscriptionMonth; }

    public int getSubscriptionYear() {
        return subscriptionYear;
    }
    public void setSubscriptionYear(int subscriptionYear) { this.subscriptionYear = subscriptionYear; }

    public double getRating() {
         return rating;
     }
    public void setRating(double rating) {
         this.rating = rating;
     }

    public double getFrezze_rating() {
         return frezze_rating;
     }
    public void setFrezze_rating(double frezze_rating) {
         this.frezze_rating = frezze_rating;
     }

    public double getPowermove_rating() {
         return powermove_rating;
     }
    public void setPowermove_rating(double powermove_rating) {
         this.powermove_rating = powermove_rating;
    }

    public double getOfp_rating() {
         return ofp_rating;
     }
    public void setOfp_rating(double ofp_rating) {
         this.ofp_rating = ofp_rating;
     }

    public double getStreching_rating() {
         return streching_rating;
     }
    public void setStreching_rating(double streching_rating) {
         this.streching_rating = streching_rating;
    }

    public int getBattle_rating() { return battle_rating;}
    public void setBattle_rating(int battle_rating) {
         this.battle_rating = battle_rating;
     }

    public int getBattle_cur_position() {
         return battle_cur_position;
     }
    public void setBattle_cur_position(int battle_cur_position) {
         this.battle_cur_position = battle_cur_position;
    }

    public int getBattle_new_position() {
         return battle_new_position;
     }
    public void setBattle_new_position(int battle_new_position) {
         this.battle_new_position = battle_new_position;
    }

     public int getCurrent_position() {
         return current_position;
     }
     public void setCurrent_position(int current_position) {
         this.current_position = current_position;
     }

     public int getNew_position() {
         return new_position;
     }
     public void setNew_position(int new_position) {
         this.new_position = new_position;
     }
}











