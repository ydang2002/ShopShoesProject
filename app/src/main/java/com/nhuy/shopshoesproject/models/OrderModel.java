package com.nhuy.shopshoesproject.models;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderModel implements Serializable {
        private String id, status, address, street, comments, idUser;
        private String dateOfOrder;
        private double totalPrice;
        private ArrayList<Product> productArrayList;

        public OrderModel(String id, String status, String address, String street, String comments, String dateOfOrder, double totalPrice, ArrayList<Product> productArrayList, String idUser) {
            this.id = id;
            this.status = status;
            this.address = address;
            this.street = street;
            this.comments = comments;
            this.dateOfOrder = dateOfOrder;
            this.totalPrice = totalPrice;
            this.productArrayList = productArrayList;
            this.idUser = idUser;
        }

        public OrderModel() {
            totalPrice = 0;
            productArrayList = new ArrayList<Product>();
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public ArrayList<Product> getProductArrayList() {
            return productArrayList;
        }

        public void setProductArrayList(ArrayList<Product> productArrayList) {
            this.productArrayList = productArrayList;
        }


        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIdUser() {
            return idUser;
        }

        public void setIdUser(String idUser) {
            this.idUser = idUser;
        }

        public ArrayList<Product> getCartProductList() {
            return productArrayList;
        }

        public void setCartProductList(ArrayList<Product> cartMedicineList) {
            this.productArrayList = cartMedicineList;
        }

        public String getDateOfOrder() {
            return dateOfOrder;
        }

        public void setDateOfOrder(String dateOfOrder) {
            this.dateOfOrder = dateOfOrder;
        }

        public double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
        }

        public void addProduct(Product product) {
            totalPrice = totalPrice + product.getPrice()*product.getQuantityInCart();
            productArrayList.add(product);
        }

        public void addTotal(Product product) {
            totalPrice = totalPrice + product.getPrice();
            //CartActivity.totalPriceView.setText(new DecimalFormat("##.##").format(totalPrice));
        }

        public void minusTotal(Product product) {
            totalPrice = totalPrice - product.getPrice();
            //CartActivity.totalPriceView.setText(new DecimalFormat("##.##").format(totalPrice));
        }

        public void removeProduct(Product product) {
            totalPrice = totalPrice - product.getPrice() * product.getQuantityInCart();
            productArrayList.remove(product);
            if(totalPrice>0){
                //CartActivity.totalPriceView.setText(new DecimalFormat("##.##").format(totalPrice));
            }
            else{
                //CartActivity.totalPriceView.setText(new DecimalFormat("##.##").format(0.00));
            }

        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "Order{" +
                    "id='" + id + '\'' +
                    ", status='" + status + '\'' +
                    ", address='" + address + '\'' +
                    ", street='" + street + '\'' +
                    ", comments='" + comments + '\'' +
                    ", idUser='" + idUser + '\'' +
                    ", dateOfOrder='" + dateOfOrder + '\'' +
                    ", totalPrice=" + totalPrice +
                    ", productArrayList=" + productArrayList +
                    '}';
        }
    

}
