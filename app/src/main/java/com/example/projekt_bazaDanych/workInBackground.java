package com.example.projekt_bazaDanych;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ParseException;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

//Klasa rozszerzająca AsyncTask -
//asynchroniczne wykonywanie czasochłonnych zadań; mechanizm, który pozwala na przeniesienie czasochłonnych zadań do nowego wątku.
//podanie parametrów klasy AsyncTask: AsyncTask<String,Void,String>:
//Pierwszym z nich jest typ danych wejściowych, jakie możemy przekazać wątkowi,
//drugim typ danych przedstawiających postęp w działaniu, trzecim wynik zwracany przez zadanie.
//Jeżeli któregoś nie potrzebujemy, wystarczy że ustawimy typ Void.
//Należy pamiętać, że zgodnie z definicją typów generycznych w Javie,
//nie możemy korzystać tutaj z typów prymitywnych (int, boolean, float etc.).

public class workInBackground extends AsyncTask<String,Void,String> {

    //zmienna Context
    Context cont;
    AlertDialog message_window;

    //Konstruktor workInBackground do wyciągnięcia context
    workInBackground(Context c) {
        cont = c;
    }
    //doInBackground(Params… params) – jedyna metoda, która uruchamiana jest w oddzielnym wątku.
    //To tutaj umieszczane są wszystkie czasochłonne czynności.
    //Nie ma dostępu do wątku głównego aplikacji,
    //w związku z czym jedynym sposobem na zaktualizowanie stanu interfejsu
    //jest wywołanie metody publishProgress(Progress… values).
    //Musi być String w params, ponieważ na takich pracuje baza
    @Override
    protected String doInBackground(String... params) {
        //Wyciągnięcie parametru z action
        String action = params[0];

        //Zmienne do bazy i poszczególnych plików php
        String login_php = "http://10.0.2.2:8090/login.php";
        String add_php = "http://10.0.2.2:8090/add_record.php";
        String update_php = "http://10.0.2.2:8090/update_record.php";
        String search_php = "http://10.0.2.2:8090/search_record.php";
        String delete_php = "http://10.0.2.2:8090/delete_record.php";
        String search_delete_php = "http://10.0.2.2:8090/search_delete_record.php";

        //"Silnik":
        //logowanie wyciąga parametry z loginu i hasła
        if (action.equals("login")) {
            try {
                String name = params[1];
                String password = params[2];
                //Objekt URL do String z danym plikiem php w bazie
                URL php_file = new URL(login_php);
                //Klasa połączenia HTTP za pomocą pliku zmiennej i pliku php
                HttpURLConnection php_connection = (HttpURLConnection) php_file.openConnection();
                //przesyłanie parametrów via POST
                php_connection.setRequestMethod("POST");
                //Set the DoOutput flag to true to use the URL connection for output - używanie połączenia adresu URL do wprowadzania danych
                php_connection.setDoOutput(true);
                //Set the DoInput flag to true to use the URL connection for input - używanie połączenia URL do danych wyjściowych
                php_connection.setDoInput(true);
                //Otrzymujemy output stream z php_connection -
                //The Java. io. OutputStream class is the superclass of all classes representing an output stream of bytes
                //Receive information from the stream. OutputStream is used for many things that you write to.
                OutputStream os = php_connection.getOutputStream();
                //Podstawowa metoda która utworzy nam plik, oraz wypisze w nim to co podaliśmy w metodzie write.
                //Wielokrotne uruchomienie tej metody nie spowoduje dodanie do pliku niczego nowego – za każdym razem plik będzie nadpisywany.
                //Pierwszy argument os, a drugi "UTF-8" -> typ strumienia
                BufferedWriter b_writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                //Dane, które chcemy wysłać z URLEncoder.encode z kluczem (z utf-8 jako typem) i wartością z parametrów;
                String data_to_post = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                //zapisujemy dane z data_to_post do BufferedWriter
                b_writer.write(data_to_post);
                //flush the characters to the intended stream;
                //flush(); jest powszechnie używane przez Developerów; niektórzy uważają, że jest zbędne
                //bo, close(); w teorii również wywołuje flush; ale z praktyki nie zawsze, co czasami powoduje błędy
                //ogólnie taki jest wzrór
                b_writer.flush();
                b_writer.close();
                os.close();
                //strumien bajtowy wejścia, aby otrzymać odpowiedź z php
                InputStream is =php_connection.getInputStream();
                //Tym razem definujemy Reader do otrzymania stream'u; tym razem
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                //Deklaracja zmiennej (pustej result i next do następnych linii) do otrzymania wyników z bazy
                String result_from_php = "";
                String next="";
                //Pętla do wyników; czytanie linia po lini
                while ((next = bufferedReader.readLine()) != null) {
                    result_from_php += next;
                }
                //Zamykanie wszystkiego i połączenia
                bufferedReader.close();
                is.close();
                php_connection.disconnect();

                //Wynik działania
                return result_from_php;

                //Wyjątki
                //MalformedURLException:
                //w wypadku niewłaściwego typu adresu URL.
                //handle I/O problems.
            } catch (MalformedURLException e) {
                e.printStackTrace();
//                IOException:
//                Wyjątek ten jest bardzo ogólny i może być wygenerowany w wielu różnych sytuacjach. M.in.:
//                nieudana próba odczytu lub zapisu do pliku związana np. z brakiem odpowiednich praw dostępu
//                próba odczytywania danych z internetu, gdy w międzyczasie utraciliśmy połączenie
//                próba odczytu danych z zamkniętego strumienia
            } catch (IOException e) {
                e.printStackTrace();
                //Wyjątek parseru
            } catch (ParseException e){
                e.printStackTrace();
            } catch (NullPointerException e){
                e.printStackTrace();
            }
         //Wysyłka danych do utworzenia nowego rekordu
        } else if (action.equals("sendData")) {
            try {
                //definicja 5 zmiennych rejestracyjnych do indexow
                String name = params[1];
                String surname = params[2];
                String address = params[3];
                String phone = params[4];
                String email = params[5];
                //Objekt URL do String z danym plikiem php w bazie
                URL php_file = new URL(add_php);
                //Klasa połączenia HTTP za pomocą pliku zmiennej i pliku php
                HttpURLConnection php_connection = (HttpURLConnection) php_file.openConnection();
                //przesyłanie parametrów via POST
                php_connection.setRequestMethod("POST");
                //Set the DoOutput flag to true to use the URL connection for output - używanie połączenia adresu URL do wprowadzania danych
                php_connection.setDoOutput(true);
                //Set the DoInput flag to true to use the URL connection for input - używanie połączenia URL do danych wyjściowych
                php_connection.setDoInput(true);
                //Otrzymujemy output stream z php_connection -
                //The Java. io. OutputStream class is the superclass of all classes representing an output stream of bytes
                //Receive information from the stream. OutputStream is used for many things that you write to.
                OutputStream os = php_connection.getOutputStream();
                //Podstawowa metoda która utworzy nam plik, oraz wypisze w nim to co podaliśmy w metodzie write.
                //Wielokrotne uruchomienie tej metody nie spowoduje dodanie do pliku niczego nowego – za każdym razem plik będzie nadpisywany.
                //Pierwszy argument os, a drugi "UTF-8" -> typ strumienia
                BufferedWriter b_writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                //Dane, które chcemy wysłać z URLEncoder.encode z kluczem (z utf-8 jako typem) i wartością z parametrów;
                String data_to_post = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&"
                        + URLEncoder.encode("surname", "UTF-8") + "=" + URLEncoder.encode(surname, "UTF-8") + "&"
                        + URLEncoder.encode("address", "UTF-8") + "=" + URLEncoder.encode(address, "UTF-8") + "&"
                        + URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8") + "&"
                        + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
                //zapisujemy dane z data_to_post do BufferedWriter
                b_writer.write(data_to_post);
                //flush the characters to the intended stream;
                //flush(); jest powszechnie używane przez Developerów; niektórzy uważają, że jest zbędne
                //bo, close(); w teorii również wywołuje flush; ale z praktyki nie zawsze, co czasami powoduje błędy
                //ogólnie taki jest wzrór
                b_writer.flush();
                b_writer.close();
                os.close();
                //strumien bajtowy wejścia, aby otrzymać odpowiedź z php
                InputStream is =php_connection.getInputStream();
                //Tym razem definujemy Reader do otrzymania stream'u; tym razem
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                //Deklaracja zmiennej (pustej result i next do następnych linii) do otrzymania wyników z bazy
                String result_from_php = "";
                String next;
                //Pętla do wyników; czytanie linia po lini
                while ((next = bufferedReader.readLine()) != null) {
                    result_from_php += next;
                }
                //Zamykanie wszystkiego i połączenia
                bufferedReader.close();
                is.close();
                php_connection.disconnect();

                //Wynik działania
                return result_from_php;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Edycja danych:
        } else if(action.equals("update")) {
            try {
                String id = params[1];
                String name = params[2];
                String surname = params[3];
                String address = params[4];
                String phone = params[5];
                String email = params[6];
                //Objekt URL do String z danym plikiem php w bazie
                URL php_file = new URL(update_php);
                //Klasa połączenia HTTP za pomocą pliku zmiennej i pliku php
                HttpURLConnection php_connection = (HttpURLConnection) php_file.openConnection();
                //przesyłanie parametrów via POST
                php_connection.setRequestMethod("POST");
                //Set the DoOutput flag to true to use the URL connection for output - używanie połączenia adresu URL do wprowadzania danych
                php_connection.setDoOutput(true);
                //Set the DoInput flag to true to use the URL connection for input - używanie połączenia URL do danych wyjściowych
                php_connection.setDoInput(true);
                //Otrzymujemy output stream z php_connection -
                //The Java. io. OutputStream class is the superclass of all classes representing an output stream of bytes
                //Receive information from the stream. OutputStream is used for many things that you write to.
                OutputStream os = php_connection.getOutputStream();
                //Podstawowa metoda która utworzy nam plik, oraz wypisze w nim to co podaliśmy w metodzie write.
                //Wielokrotne uruchomienie tej metody nie spowoduje dodanie do pliku niczego nowego – za każdym razem plik będzie nadpisywany.
                //Pierwszy argument os, a drugi "UTF-8" -> typ strumienia
                BufferedWriter b_writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                //Dane, które chcemy wysłać z URLEncoder.encode z kluczem (z utf-8 jako typem) i wartością z parametrów;
                String data_to_post = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8") + "&"
                        + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&"
                        + URLEncoder.encode("surname", "UTF-8") + "=" + URLEncoder.encode(surname, "UTF-8") + "&"
                        + URLEncoder.encode("address", "UTF-8") + "=" + URLEncoder.encode(address, "UTF-8") + "&"
                        + URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8") + "&"
                        + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
                //zapisujemy dane z data_to_post do BufferedWriter
                b_writer.write(data_to_post);
                //flush the characters to the intended stream;
                //flush(); jest powszechnie używane przez Developerów; niektórzy uważają, że jest zbędne
                //bo, close(); w teorii również wywołuje flush; ale z praktyki nie zawsze, co czasami powoduje błędy
                //ogólnie taki jest wzrór
                b_writer.flush();
                b_writer.close();
                os.close();
                //strumien bajtowy wejścia, aby otrzymać odpowiedź z php
                InputStream is =php_connection.getInputStream();
                //Tym razem definujemy Reader do otrzymania stream'u; tym razem
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                //Deklaracja zmiennej (pustej result i next do następnych linii) do otrzymania wyników z bazy
                String result_from_php = "";
                String next="";
                //Pętla do wyników; czytanie linia po lini
                while ((next = bufferedReader.readLine()) != null) {
                    result_from_php += next;
                }
                //Zamykanie wszystkiego i połączenia
                bufferedReader.close();
                is.close();
                php_connection.disconnect();

                //Wynik działania
                return result_from_php;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Wyszukiwanie w bazie po adresie zamieszkania
        } else if (action.equals("search")) {
            try {

                String adres = params[1];

                //Objekt URL do String z danym plikiem php w bazie
                URL php_file = new URL(search_php);
                //Klasa połączenia HTTP za pomocą pliku zmiennej i pliku php
                HttpURLConnection php_connection = (HttpURLConnection) php_file.openConnection();
                //przesyłanie parametrów via POST
                php_connection.setRequestMethod("POST");
                //Set the DoOutput flag to true to use the URL connection for output - używanie połączenia adresu URL do wprowadzania danych
                php_connection.setDoOutput(true);
                //Set the DoInput flag to true to use the URL connection for input - używanie połączenia URL do danych wyjściowych
                php_connection.setDoInput(true);
                //Otrzymujemy output stream z php_connection -
                //The Java. io. OutputStream class is the superclass of all classes representing an output stream of bytes
                //Receive information from the stream. OutputStream is used for many things that you write to.
                OutputStream os = php_connection.getOutputStream();
                //Podstawowa metoda która utworzy nam plik, oraz wypisze w nim to co podaliśmy w metodzie write.
                //Wielokrotne uruchomienie tej metody nie spowoduje dodanie do pliku niczego nowego – za każdym razem plik będzie nadpisywany.
                //Pierwszy argument os, a drugi "UTF-8" -> typ strumienia
                BufferedWriter b_writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                //Dane, które chcemy wysłać z URLEncoder.encode z kluczem (z utf-8 jako typem) i wartością z parametrów;
                String data_to_post = URLEncoder.encode("adres", "UTF-8") + "=" + URLEncoder.encode(adres, "UTF-8");
                //zapisujemy dane z data_to_post do BufferedWriter
                b_writer.write(data_to_post);
                //flush the characters to the intended stream;
                //flush(); jest powszechnie używane przez Developerów; niektórzy uważają, że jest zbędne
                //bo, close(); w teorii również wywołuje flush; ale z praktyki nie zawsze, co czasami powoduje błędy
                //ogólnie taki jest wzrór
                b_writer.flush();
                b_writer.close();
                os.close();
                //strumien bajtowy wejścia, aby otrzymać odpowiedź z php
                InputStream is =php_connection.getInputStream();
                //Tym razem definujemy Reader do otrzymania stream'u; tym razem
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                //Deklaracja zmiennej (pustej result i next do następnych linii) do otrzymania wyników z bazy
                String result_from_php = "";
                String next="";

                //Pętla do wyników; czytanie linia po lini
                while ((next = bufferedReader.readLine()) != null) {
                    result_from_php += next;
                }
                //Zamykanie wszystkiego i połączenia
                bufferedReader.close();
                is.close();
                php_connection.disconnect();

                //Wynik działania
                return result_from_php;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e){
                e.printStackTrace();
            }
        } else if (action.equals("search_delete")) {
            try {

                String adres = params[1];

                //Objekt URL do String z danym plikiem php w bazie
                URL php_file = new URL(search_delete_php);
                //Klasa połączenia HTTP za pomocą pliku zmiennej i pliku php
                HttpURLConnection php_connection = (HttpURLConnection) php_file.openConnection();
                //przesyłanie parametrów via POST
                php_connection.setRequestMethod("POST");
                //Set the DoOutput flag to true to use the URL connection for output - używanie połączenia adresu URL do wprowadzania danych
                php_connection.setDoOutput(true);
                //Set the DoInput flag to true to use the URL connection for input - używanie połączenia URL do danych wyjściowych
                php_connection.setDoInput(true);
                //Otrzymujemy output stream z php_connection -
                //The Java. io. OutputStream class is the superclass of all classes representing an output stream of bytes
                //Receive information from the stream. OutputStream is used for many things that you write to.
                OutputStream os = php_connection.getOutputStream();
                //Podstawowa metoda która utworzy nam plik, oraz wypisze w nim to co podaliśmy w metodzie write.
                //Wielokrotne uruchomienie tej metody nie spowoduje dodanie do pliku niczego nowego – za każdym razem plik będzie nadpisywany.
                //Pierwszy argument os, a drugi "UTF-8" -> typ strumienia
                BufferedWriter b_writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                //Dane, które chcemy wysłać z URLEncoder.encode z kluczem (z utf-8 jako typem) i wartością z parametrów;
                String data_to_post = URLEncoder.encode("adres", "UTF-8") + "=" + URLEncoder.encode(adres, "UTF-8");
                //zapisujemy dane z data_to_post do BufferedWriter
                b_writer.write(data_to_post);
                //flush the characters to the intended stream;
                //flush(); jest powszechnie używane przez Developerów; niektórzy uważają, że jest zbędne
                //bo, close(); w teorii również wywołuje flush; ale z praktyki nie zawsze, co czasami powoduje błędy
                //ogólnie taki jest wzrór
                b_writer.flush();
                b_writer.close();
                os.close();
                //strumien bajtowy wejścia, aby otrzymać odpowiedź z php
                InputStream is =php_connection.getInputStream();
                //Tym razem definujemy Reader do otrzymania stream'u; tym razem
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                //Deklaracja zmiennej (pustej result i next do następnych linii) do otrzymania wyników z bazy
                String result_from_php = "";
                String next="";
                //Pętla do wyników; czytanie linia po lini
                while ((next = bufferedReader.readLine()) != null) {
                    result_from_php += next;
                }
                //Zamykanie wszystkiego i połączenia
                bufferedReader.close();
                is.close();
                php_connection.disconnect();

                //Wynik działania
                return result_from_php;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (action.equals("delete")) {
            try {

                String id = params[1];
                String adres = params[2];

                //Objekt URL do String z danym plikiem php w bazie
                URL php_file = new URL(delete_php);
                //Klasa połączenia HTTP za pomocą pliku zmiennej i pliku php
                HttpURLConnection php_connection = (HttpURLConnection) php_file.openConnection();
                //przesyłanie parametrów via POST
                php_connection.setRequestMethod("POST");
                //Set the DoOutput flag to true to use the URL connection for output - używanie połączenia adresu URL do wprowadzania danych
                php_connection.setDoOutput(true);
                //Set the DoInput flag to true to use the URL connection for input - używanie połączenia URL do danych wyjściowych
                php_connection.setDoInput(true);
                //Otrzymujemy output stream z php_connection -
                //The Java. io. OutputStream class is the superclass of all classes representing an output stream of bytes
                //Receive information from the stream. OutputStream is used for many things that you write to.
                OutputStream os = php_connection.getOutputStream();
                //Podstawowa metoda która utworzy nam plik, oraz wypisze w nim to co podaliśmy w metodzie write.
                //Wielokrotne uruchomienie tej metody nie spowoduje dodanie do pliku niczego nowego – za każdym razem plik będzie nadpisywany.
                //Pierwszy argument os, a drugi "UTF-8" -> typ strumienia
                BufferedWriter b_writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                //Dane, które chcemy wysłać z URLEncoder.encode z kluczem (z utf-8 jako typem) i wartością z parametrów;
                String data_to_post = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8")+ "&"
                        + URLEncoder.encode("adres", "UTF-8") + "=" + URLEncoder.encode(adres, "UTF-8");
                //zapisujemy dane z data_to_post do BufferedWriter
                b_writer.write(data_to_post);
                //flush the characters to the intended stream;
                //flush(); jest powszechnie używane przez Developerów; niektórzy uważają, że jest zbędne
                //bo, close(); w teorii również wywołuje flush; ale z praktyki nie zawsze, co czasami powoduje błędy
                //ogólnie taki jest wzrór
                b_writer.flush();
                b_writer.close();
                os.close();
                //strumien bajtowy wejścia, aby otrzymać odpowiedź z php
                InputStream is = php_connection.getInputStream();
                //Tym razem definujemy Reader do otrzymania stream'u; tym razem
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                //Deklaracja zmiennej (pustej result i next do następnych linii) do otrzymania wyników z bazy
                String result_from_php = "";
                String next;
                //Pętla do wyników; czytanie linia po lini
                while ((next = bufferedReader.readLine()) != null) {
                    result_from_php += next;
                }
                //Zamykanie wszystkiego i połączenia
                bufferedReader.close();
                is.close();
                php_connection.disconnect();

                //Wynik działania
                return result_from_php;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    //onPreExecute() wywoływana zaraz przed uruchomieniem właściwego zadania.
    // Metoda ta wykonywana jest w wątku głównym aplikacji,
    // dzięki czemu możemy w niej skonfigurować interfejs aplikacji
    // (np. wyświetlić ProgressBar albo zablokować przyciski).
    @Override
    protected void onPreExecute() {
        //utworzenie okna Dialogowego wraz z przyciskiem
        message_window = new AlertDialog.Builder(cont).create();
        message_window.setButton(DialogInterface.BUTTON_POSITIVE, "OK", (DialogInterface.OnClickListener) null);

    }
    //onPostExecute(Result result) – wywoływana w momencie zakończenia pracy doInBackground(…).
    //Argumentem metody jest wynik zwrócony przez doInBackground(…).
    //Część wynikowa. Wartości z Result jako czynnik decydujący o tym co pojawi się na ekranie
    @Override
    protected void onPostExecute(String result_from_php) {

        //Jeśli "succes", to przechodzi z ekranu logowania do Menu poprzez intent -> ważny jest tutaj context, bez niego nie działa
        if (result_from_php.equals("success")) {

            Intent logg = new Intent(cont, Menu.class);
            cont.startActivity(logg);
            ((Activity) cont).finish();

            Toast.makeText(cont, "Logowanie powiodło się", Toast.LENGTH_LONG).show();

            //Gdy result zawiera słowo "Znaleziono" w echo z PHP to pokazuje się wynik wyszukiwania użytkownika do edycji
        } else if (result_from_php.contains("Znaleziono")) {


            message_window.setTitle("Dane użytkownika do edycji:");
            message_window.setMessage(result_from_php);
            message_window.show();

            //"Insert" oznacza pojawienie się informacji o tym, że rekord został skutecznie dodany do bazy
        } else if (result_from_php.contains("nieodwracalne")) {

            message_window.setTitle("Dane użytkownika do usunięcia:");
            message_window.setMessage(result_from_php);
            message_window.show();

            //"Insert" oznacza pojawienie się informacji o tym, że rekord został skutecznie dodany do bazy
        }else if (result_from_php.contains("Insert")) {

            message_window.setTitle("Status:");
            message_window.setMessage("Dodano rekord do bazy.");
            message_window.show();

            //"Update" oznacza skuteczną aktualizacje rekordu
        } else if (result_from_php.equals("Update")) {

            message_window.setTitle("Status:");
            message_window.setMessage("Zakutalizowano.");
            message_window.show();
          //W innym wypadku pojawia się komunikuat błędu.
        } else if (result_from_php.equals("Delete")) {

            message_window.setTitle("Status:");
            message_window.setMessage("Usunięto użytkownika");
            message_window.show();
            //W innym wypadku pojawia się komunikuat błędu.
        }else {

            message_window.setTitle("Hmm");
            message_window.setMessage("Spróbuj jeszcze raz.");
            message_window.show();

        }
    }
}






