package com.shengma.lanjing.beans;


import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;



@Entity
public class XiaZaiLiWuBean {

        @Id(assignable = true)
        private Long id;
        private String giftName;
        private String giftMoney;
        private String giftUrl;
        private String specialUrl;
        private int num;
        private int type;
        private boolean isD;
        private boolean isJY;
        private boolean isA;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean isA() {
        return isA;
    }

    public void setA(boolean a) {
        isA = a;
    }

    public boolean isJY() {
        return isJY;
    }

    public void setJY(boolean JY) {
        isJY = JY;
    }

    public boolean isD() {
            return isD;
        }

        public void setD(boolean d) {
            isD = d;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getGiftName() {
            return giftName;
        }

        public void setGiftName(String giftName) {
            this.giftName = giftName;
        }

        public String getGiftMoney() {
            return giftMoney;
        }

        public void setGiftMoney(String giftMoney) {
            this.giftMoney = giftMoney;
        }

        public String getGiftUrl() {
            return giftUrl;
        }

        public void setGiftUrl(String giftUrl) {
            this.giftUrl = giftUrl;
        }

        public String getSpecialUrl() {
            return specialUrl;
        }

        public void setSpecialUrl(String specialUrl) {
            this.specialUrl = specialUrl;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

}
