# Tuotannonohjaus

Sovelluksella voi seurata hammaslaboratorion tuotantoa. Laboratorion työtahti on nopea ja tilaukset liikkuvat päivän aikana usean eri työntekijän käsissä eri tuotantotiloissa. Sovellus auttaa pitämään tilaukset järjestyksessä ja auttaa työnjohtajaa hahmottamaan kunkin työntekijän työmääriä. Seurantakoodin avulla näkee missä työvaiheessa tilaus on menossa tuotantoketjussa ja ketkä työntekijät ovat tehneet mitkä työvaiheet. Jos jostakin tilauksesta tulee palautetta, se on helppo näin kohdentaa oikealle työntekijälle.


## Dokumentaatio

* [Työaikakirjanpito](https://github.com/Skorp7/ot-harjoitustyo/blob/master/dokumentointi/tyoaikakirjanpito.md)
* [Vaatimusmäärittely](https://github.com/Skorp7/ot-harjoitustyo/blob/master/dokumentointi/maarittelydokumentti.md)
* [Arkkitehtuurikuvaus](https://github.com/Skorp7/ot-harjoitustyo/blob/master/dokumentointi/arkkitehtuuri.md)

## Komentorivitoiminnot
Kommennot siitä kansiosta jossa pom.xml sijaitsee.

### Testaus

Testit suoritetaan komennolla
```
mvn test
```

Testikattavuusraportti saadaan luotua komennolla
```
mvn test jacoco:report
```
Raportin saa nähtäväksi avaamalla selaimella tiedoston target/site/jacoco/index.html

### Käynnistys

Sovelluksen saa käynnistettyä yliopiston Cubbli-linux koneilla
```
mvn compile exec:java -Dexec.mainClass=ui.MainProg
```

### Checkstyle

Checkstyle-tarkistukset saa ajettua komennolla
```
mvn jxr:jxr checkstyle:checkstyle
```
Raportin saa nähtäväksi avaamalla selaimella tiedoston target/site/checkstyle.html
