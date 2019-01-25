package com.rumah.kredit.loan.model;

import java.util.List;

public class IndexModel {


    private String max_page;
    private List<AdsBean> ads;
    private List<TagBean> tag;
    private List<ProductBean> product;

    public String getMax_page() {
        return max_page;
    }

    public void setMax_page(String max_page) {
        this.max_page = max_page;
    }

    public List<AdsBean> getAds() {
        return ads;
    }

    public void setAds(List<AdsBean> ads) {
        this.ads = ads;
    }

    public List<TagBean> getTag() {
        return tag;
    }

    public void setTag(List<TagBean> tag) {
        this.tag = tag;
    }

    public List<ProductBean> getProduct() {
        return product;
    }

    public void setProduct(List<ProductBean> product) {
        this.product = product;
    }

    public static class AdsBean {

        private String id;
        private String img;
        private String link;
        private String media_type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getMedia_type() {
            return media_type;
        }

        public void setMedia_type(String media_type) {
            this.media_type = media_type;
        }
    }

    public static class TagBean {
        /**
         * id : 4
         * name : Jumlah besar
         * img : http://inter.jiehuahua.com/uploads/vstore/20180323/1521791292_6848.png
         * sort : 0
         * status : 1
         */

        private String id;
        private String name;
        private String img;
        private String sort;
        private String status;

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

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public static class ProductBean {

        private String id;
        private String name;
        private String logo;
        private String first_plan;
        private String lulus_score;
        private String kecepatan_score;
        private String penagihan_score;
        private String rate_interest;
        private String loan_amount_max;
        private String pass_num;
        private String declare;
        private String score;

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

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getFirst_plan() {
            return first_plan;
        }

        public void setFirst_plan(String first_plan) {
            this.first_plan = first_plan;
        }

        public String getLulus_score() {
            return lulus_score;
        }

        public void setLulus_score(String lulus_score) {
            this.lulus_score = lulus_score;
        }

        public String getKecepatan_score() {
            return kecepatan_score;
        }

        public void setKecepatan_score(String kecepatan_score) {
            this.kecepatan_score = kecepatan_score;
        }

        public String getPenagihan_score() {
            return penagihan_score;
        }

        public void setPenagihan_score(String penagihan_score) {
            this.penagihan_score = penagihan_score;
        }

        public String getRate_interest() {
            return rate_interest;
        }

        public void setRate_interest(String rate_interest) {
            this.rate_interest = rate_interest;
        }

        public String getLoan_amount_max() {
            return loan_amount_max;
        }

        public void setLoan_amount_max(String loan_amount_max) {
            this.loan_amount_max = loan_amount_max;
        }

        public String getPass_num() {
            return pass_num;
        }

        public void setPass_num(String pass_num) {
            this.pass_num = pass_num;
        }

        public String getDeclare() {
            return declare;
        }

        public void setDeclare(String declare) {
            this.declare = declare;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }
    }
}
