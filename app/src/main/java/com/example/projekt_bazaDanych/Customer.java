package com.example.projekt_bazaDanych;

import androidx.appcompat.app.AppCompatActivity;

public class Customer {

        private String Name, Surname, Address, Phone, Email;

        public Customer(String Name,String Surname,String Address,String Phone,String Email) {  //konstruktor
            this.Name = Name;
            this.Surname = Surname;
            this.Address = Address;
            this.Phone = Phone;
            this.Email = Email;
        }

    public Customer() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String Surname) {
        this.Surname = Surname;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }
}
