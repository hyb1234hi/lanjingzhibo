package com.shengma.lanjing.beans;





public class TuiSongBenass {


    /**
     * cid : 8103a4c628a0b98974ec1949-711261d4-5f17-4d2f-a855-5e5a8909b26e
     * platform : all
     * audience : {"tag":["深圳","北京"]}
     * notification : {"android":{"alert":"Hi, JPush!","title":"Send to Android","builder_id":1,"large_icon":"http://www.jiguang.cn/largeIcon.jpg","extras":{"newsid":321}},"ios":{"alert":"Hi, JPush!","sound":"default","badge":"+1","thread-id":"default","extras":{"newsid":321}}}
     * message : {"msg_content":"Hi,JPush","content_type":"text","title":"msg","extras":{"key":"value"}}
     * sms_message : {"temp_id":1250,"temp_para":{"code":"123456"},"delay_time":3600,"active_filter":false}
     * options : {"time_to_live":60,"apns_production":false,"apns_collapse_id":"jiguang_test_201706011100"}
     */

    private String cid;
    private String platform;
    private MessageBean message;

    private String audience;
    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }


    public MessageBean getMessage() {
        return message;
    }

    public void setMessage(MessageBean message) {
        this.message = message;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public static class MessageBean {


        public MessageBean(String msg_content, String content_type) {
            this.msg_content = msg_content;
            this.content_type = content_type;
        }

        /**
         * msg_content : Hi,JPush
         * content_type : text
         * title : msg
         * extras : {"key":"value"}
         */



        private String msg_content;
        private String content_type;
        private String title;
        private ExtrasBeanXX extras;

        public String getMsg_content() {
            return msg_content;
        }

        public void setMsg_content(String msg_content) {
            this.msg_content = msg_content;
        }

        public String getContent_type() {
            return content_type;
        }

        public void setContent_type(String content_type) {
            this.content_type = content_type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public ExtrasBeanXX getExtras() {
            return extras;
        }

        public void setExtras(ExtrasBeanXX extras) {
            this.extras = extras;
        }

        public static class ExtrasBeanXX {
            /**
             * key : value
             */

            private String key;

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }
        }
    }


}
