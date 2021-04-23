package com.example.projekt_bazaDanych;

import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Customer {

        private String UniqueName, NameSurname, Address, Phone, Email;

        public Customer(String UniqueName,String NameSurname,String Address,String Phone,String Email) {  //konstruktor
            this.UniqueName = UniqueName;
            this.NameSurname = NameSurname;
            this.Address = Address;
            this.Phone = Phone;
            this.Email = Email;
        }

    public Customer() {
    }

    public String getUniqueName() {
        return UniqueName;
    }

    public void setUniqueName(String UniqueName) {
        this.UniqueName = UniqueName;
    }

    public String getNameSurname() {
        return NameSurname;
    }

    public void setNameSurname(String NameSurname) {
        this.NameSurname = NameSurname;
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

    //Wypisanie danych klienta w liście klientów
    public String stringCustomer(){

            return "\n"+UniqueName + ":" + "\n"+ NameSurname+ "\n\n" + Address + "\n" + Phone + "\n" + Email + "\n";

    }
}
