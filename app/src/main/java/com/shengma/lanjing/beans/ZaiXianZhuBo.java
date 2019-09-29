package com.shengma.lanjing.beans;

import java.util.List;

public class ZaiXianZhuBo {


    /**
     * code : 2000
     * desc : Success
     * result : [{"id":600046,"headImage":"https://img-1259669722.cos.ap-guangzhou.myqcloud.com/images/86531568517955304.png","nickname":"瑞瞳测试","sex":2,"level":24,"roomId":100046},{"id":600102,"headImage":"http://thirdwx.qlogo.cn/mmopen/vi_32/n2vmqxIOHJ6L9o6rLibkKVkpiaCwHZpA1mib0t6ZrMODztPF7gdfhcf3XPLgyAg47JO0MSY396vw4mOBVSgYEJCKg/132","nickname":"小军","sex":1,"level":1,"roomId":100106}]
     * total : 2
     */

    private int code;
    private String desc;
    private int total;
    private List<ResultBean> result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 600046
         * headImage : https://img-1259669722.cos.ap-guangzhou.myqcloud.com/images/86531568517955304.png
         * nickname : 瑞瞳测试
         * sex : 2
         * level : 24
         * roomId : 100046
         */

        private long id;
        private String headImage;
        private String nickname;
        private int sex;
        private int level;
        private int roomId;
        private String playSafeUrl;

        public String getPlaySafeUrl() {
            return playSafeUrl;
        }

        public void setPlaySafeUrl(String playSafeUrl) {
            this.playSafeUrl = playSafeUrl;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getHeadImage() {
            return headImage;
        }

        public void setHeadImage(String headImage) {
            this.headImage = headImage;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getRoomId() {
            return roomId;
        }

        public void setRoomId(int roomId) {
            this.roomId = roomId;
        }
    }
}
