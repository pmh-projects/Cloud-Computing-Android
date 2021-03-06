# <p align="center">Projekt zaliczeniowy na przedmiot "Cloud Computing w aplikacjach mobilnych dla platformy Android"</p>
Informatyka i ekonometria, specj. Aplikacje informatyczne w biznesie
<br>
Grupa N22-31
<br>
Paweł Mach, Marcin Edel
<br><br>

## Baza danych w Firebase
* [Logowanie](#logowanie)
* [Menu](#menu)
* [Dodaj](#dodaj)
* [Edycja](#edycja)
* [Usun](#usun)
* [Klienci](#klienci)
* [Zakładanie konta](#zakładanie-konta)
* [Wylogowanie i zamykanie](#Wylogowanie-i-zamykanie)

<br>
<p align="center">
Aplikacja umożliwiająca dodawanie, edycję i usuwanie klientów, a także wyświetlanie zawartości bazy.
 <br>
Tworzona i testowana na NEXUS 4 API 27.
</p>


## Logowanie

<p align="center">W wypadku wprowadzenia niepoprawnych danych nie można przejść do następnego okna.
<img src="https://github.com/pmh-projects/projektAndroid_bazaDanych/blob/master/screenshots/screenshot%20(23).PNG">
</p>

## Menu
<p align="center">Po zalogowaniu pojawia się okienko z wyborem 6 opcji.</p>

<p align="center"><img src="https://github.com/pmh-projects/projektAndroid_bazaDanych/blob/master/screenshots/screenshot%20(22).PNG">
</p>

## Dodaj
<p align="center">
Umożliwia dodanie nowego klienta z jego unikalną nazwą, która ma na celu ułatwienie wyszukiwania w bazie i ułatwienie pracy z opcjami edycji, a także rozpoznanie klienta
poprzez użytkownika w oknie "Lista klientów".</p>

<p align="center">
<img src="https://github.com/pmh-projects/projektAndroid_bazaDanych/blob/master/screenshots/screenshot%20(21).PNG">
<img src="https://github.com/pmh-projects/projektAndroid_bazaDanych/blob/master/screenshots/screenshot%20(20).PNG">
<img src="https://github.com/pmh-projects/projektAndroid_bazaDanych/blob/master/screenshots/screenshot%20(19).PNG">
</p>

<p align="center">
Po dodaniu użytkownika automatycznie pojawia się nowy wpis w bazie klientów:</p>
<p align="center">
<img src="https://github.com/pmh-projects/projektAndroid_bazaDanych/blob/master/screenshots/screenshot%20(16).PNG">
</p>

<p align="center">
W wypadku niewypełnienia jednego z pól pojawia się informacja o konieczności jego uzupełnienia.
Do walidacji danych zastosowany został Saripaar Validator.
</p>

<p align="center">
<img src="https://github.com/pmh-projects/projektAndroid_bazaDanych/blob/master/screenshots/screenshot%20(18).PNG">
</p>

## Edycja
<p align="center">
Prawidłowe wpisanie unikalnej szukanej nazwy spowoduje wypełnienie pól do edycji lub usunięcia klienta.
</p>
<p align="center">
<img src="https://github.com/pmh-projects/projektAndroid_bazaDanych/blob/master/screenshots/screenshot(26).PNG">
<img src=https://github.com/pmh-projects/projektAndroid_bazaDanych/blob/master/screenshots/screenshot2.PNG>
<img src="https://github.com/pmh-projects/projektAndroid_bazaDanych/blob/master/screenshots/screenshot%20(13).PNG">
</p>

<p align="center">
Po wprowadzeniu danych i kliknięciu edytuj pojawi się okno dialogowe z pytaniem o potwierdzenie decyzji.
Zastosowany został AlerDialog.Builder z deklaracją przycisków "Tak" i "Cofnij". Zatwierdzenie decyzji powoduje natychmiastowe zmiany w bazie danych.
Wybór "Cofnij" wywoła dialog.cancel(), co spowoduje zniknięcie okna.
</p>
<p align="center">
<img src="https://github.com/pmh-projects/projektAndroid_bazaDanych/blob/master/screenshots/screenshot%20(12).PNG">
 <img src="https://github.com/pmh-projects/projektAndroid_bazaDanych/blob/master/screenshots/screenshot3.PNG">
</p>
<p align="center">
W wypadku nie wypełnienia pola pojawi się informacja o konieczności jego uzupełnienia. Również w tej klasie zastosowana Saripaar Validator.
</p>

<p align="center">
<img src="https://github.com/pmh-projects/projektAndroid_bazaDanych/blob/master/screenshots/screenshot%20(9).PNG">
</p>

<p align="center">
Błędne wpisanie danych:
</p>
<p align="center">
<img src="https://github.com/pmh-projects/projektAndroid_bazaDanych/blob/master/screenshots/screenshot%20(15).PNG">
</p>

## Usun
<p align="center">
Zastosowano ten sam mechanizm akceptacji, co przy edycji. Tym razem wybór "Tak" spowoduje usunięcie danych.
</p>
<p align="center">
<img src="https://github.com/pmh-projects/projektAndroid_bazaDanych/blob/master/screenshots/screenshot.PNG">
</p>
<p align="center">
Akceptacja spowoduje usunięcie wpisu z bazy danych.
</p>
<p align="center">
<img src="https://github.com/pmh-projects/projektAndroid_bazaDanych/blob/master/screenshots/screenshot%20(11).PNG">
<img src="https://github.com/pmh-projects/projektAndroid_bazaDanych/blob/master/screenshots/screenshot%20(10).PNG">
</p>
<p align="center">
Wprowadzenie niepoprawnej unikalnej nazwy sprawi, że nie zostanie zwrócony żaden wynik z bazy. Spowoduje to wyświetlenie stosownego komunikatu na ekranie.
</p>
<p align="center">
<img src="https://github.com/pmh-projects/projektAndroid_bazaDanych/blob/master/screenshots/screenshot%20(1).PNG">
</p>

## Klienci
<p align="center">
Wybór ocji menu "Klienci" powoduje wyświetlenie wszystkich wpisów z bazy.
Do wyświetlenia zawartości bazy wykorzystano ListView, ArrayList<String>, ArrayAdapter<String>, ChildEventListener oraz metodę stringCustomer(), która
zwraca wartości z bazy poprzez return.
</p>
<p align="center">
Powrót do menu odbywa się poprzez wybór z menu opcji cofnij. 
Do utworzenia Menu wykorzystano onCreateOptionsMenu, onOptionsItemSelected, MenuInflater oraz utworzone activity (item) z zadeklarowanych przyciskiem. 
</p>
<p align="center">
<img src="https://github.com/pmh-projects/projektAndroid_bazaDanych/blob/master/screenshots/screenshot(25).PNG">
</p>
<p align="center">
<img src="https://github.com/pmh-projects/projektAndroid_bazaDanych/blob/master/screenshots/screenshot%20(7).PNG">
</p>
<p align="center"><b>
 
## Zakładanie konta
</b></p>
<p align="center">
Istnieje możliwość dodania nowego konta użytkownika aplikacji. 
W odpowiadającej za tę czynność klasie zastosowano FirebaseAuth i FirebaseAuth.AuthStateListener.
Dodawanie odbywa sie poprzez createUserWithEmailAndPassword i setOnClickListener przycisku "Załóż nowe konto".
Utworzenie nowe konta automatycznie zostanie odnotowane w bazie Firebase w zakładce authentication.
</p>
<p align="center">
<img src="https://github.com/pmh-projects/projektAndroid_bazaDanych/blob/master/screenshots/screenshot%20(6).PNG">
<img src="https://github.com/pmh-projects/projektAndroid_bazaDanych/blob/master/screenshots/screenshot%20(5).PNG">
<img src="https://github.com/pmh-projects/projektAndroid_bazaDanych/blob/master/screenshots/screenshot%20(4).PNG">
</p>

## Wylogowanie zamykanie
<p align="center">
Opcja WYLOGUJ powoduje wylogowanie się z bazy i powrót do okienka z logowanie.
ZAMKNIJ sprawia, że aplikacja zostanie zamknięta.
</p>
<p align="center">
<img src="https://github.com/pmh-projects/projektAndroid_bazaDanych/blob/master/screenshots/screenshot%20(33).PNG">
</p>

<p align="center">Wykorzystane zewnętrzne zasoby:
  <ul>
    <li>Globe rotate matte loop (Royalty-Free License),</li>
    <li>Earth Map (Creative Commons Zero - CC0),</li>
    <li>Saripaar Validator (licencja Apache 2.0).</li>
 </ul>
</p>
<p align="left">Licencja aplikacji:</p>
<img src=https://github.com/pmh-projects/projektAndroid_bazaDanych/blob/master/screenshots/gplv3-88x31.png>
<p align="left">Dodatkowe informacje:</p>
<p align="left">Na aplikacje składa się 7 klas odpowiedzialnych za jej funkcjonalności. Do tego utworzono 7 plików zawierających layout dla każdej z klas oraz osobne menu dla okienka z pełną zawartością bazy danych. Osobny komentarz w kodzie.
<br>
</p>

