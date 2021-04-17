package com.example.projekt_bazaDanych;

import androidx.appcompat.app.AppCompatActivity;

public class Customer {

        private String stringName, stringSurname, stringAddress, stringPhone, stringEmail;

        public Customer(String stringName,String stringSurname,String stringAddress,String stringPhone,String stringEmail) {  //konstruktor
            this.stringName = stringName;
            this.stringSurname = stringSurname;
            this.stringAddress = stringAddress;
            this.stringPhone = stringPhone;
            this.stringEmail = stringEmail;
        }


    public String getStringName() {
        return stringName;
    }

    public void setStringName(String stringName) {
        this.stringName = stringName;
    }

    public String getStringSurname() {
        return stringSurname;
    }

    public void setStringSurname(String stringSurname) {
        this.stringSurname = stringSurname;
    }

    public String getStringAddress() {
        return stringAddress;
    }

    public void setStringAddress(String stringAddress) {
        this.stringAddress = stringAddress;
    }

    public String getStringPhone() {
        return stringPhone;
    }

    public void setStringPhone(String stringPhone) {
        this.stringPhone = stringPhone;
    }

    public String getStringEmail() {
        return stringEmail;
    }

    public void setStringEmail(String stringEmail) {
        this.stringEmail = stringEmail;
    }
}
