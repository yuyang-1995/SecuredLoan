package com.sec.yn.loan.bean;

import java.util.List;

public class InitBean {


    /**
     * email : Hubungi kami melalui email:service@jrweid.com
     * product_hint : 0
     * product_hint_time : 3
     * product_dialog : 0
     * product_dialog_time : 3
     * cate_list : [{"id":"1","name":"PINJAMAN"},{"id":"2","name":"UANG TUNAI"},{"id":"3","name":"CICILAN"}]
     * hot_search : [{"product_name":"Doctor Rupiah","product_id":"23"},{"product_name":"GoCash","product_id":"22"},{"product_name":"Rupiah cash","product_id":"21"},{"product_name":"easycash","product_id":"24"}]
     * privacy_policy :
     */

    private String email;
    private String product_hint;
    private String product_hint_time;
    private String product_dialog;
    private String product_dialog_time;
    private String privacy_policy;
    private List<CateListBean> cate_list;
    private List<HotSearchBean> hot_search;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProduct_hint() {
        return product_hint;
    }

    public void setProduct_hint(String product_hint) {
        this.product_hint = product_hint;
    }

    public String getProduct_hint_time() {
        return product_hint_time;
    }

    public void setProduct_hint_time(String product_hint_time) {
        this.product_hint_time = product_hint_time;
    }

    public String getProduct_dialog() {
        return product_dialog;
    }

    public void setProduct_dialog(String product_dialog) {
        this.product_dialog = product_dialog;
    }

    public String getProduct_dialog_time() {
        return product_dialog_time;
    }

    public void setProduct_dialog_time(String product_dialog_time) {
        this.product_dialog_time = product_dialog_time;
    }

    public String getPrivacy_policy() {
        return privacy_policy;
    }

    public void setPrivacy_policy(String privacy_policy) {
        this.privacy_policy = privacy_policy;
    }

    public List<CateListBean> getCate_list() {
        return cate_list;
    }

    public void setCate_list(List<CateListBean> cate_list) {
        this.cate_list = cate_list;
    }

    public List<HotSearchBean> getHot_search() {
        return hot_search;
    }

    public void setHot_search(List<HotSearchBean> hot_search) {
        this.hot_search = hot_search;
    }

    public static class CateListBean {
        /**
         * id : 1
         * name : PINJAMAN
         */

        private String id;
        private String name;

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
    }

    public static class HotSearchBean {
        /**
         * product_name : Doctor Rupiah
         * product_id : 23
         */

        private String product_name;
        private String product_id;

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }
    }
}
