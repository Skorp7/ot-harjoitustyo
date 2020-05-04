# Tuotannonohjaus

Sovelluksella voi seurata hammaslaboratorion tuotantoa. Laboratorion työtahti on nopea ja tilaukset liikkuvat päivän aikana usean eri työntekijän käsissä eri tuotantotiloissa. Sovellus auttaa pitämään tilaukset järjestyksessä ja auttaa työnjohtajaa hahmottamaan kunkin työntekijän työmääriä. Seurantakoodin avulla näkee missä työvaiheessa tilaus on menossa tuotantoketjussa ja ketkä työntekijät ovat tehneet mitkä työvaiheet. Jos jostakin tilauksesta tulee palautetta, se on helppo näin kohdentaa oikealle työntekijälle.


## Dokumentaatio

* [Käyttöohje](https://github.com/Skorp7/ot-harjoitustyo/blob/master/dokumentointi/kayttoohje.md)
* [Työaikakirjanpito](https://github.com/Skorp7/ot-harjoitustyo/blob/master/dokumentointi/tyoaikakirjanpito.md)
* [Vaatimusmäärittely](https://github.com/Skorp7/ot-harjoitustyo/blob/master/dokumentointi/maarittelydokumentti.md)
* [Arkkitehtuurikuvaus](https://github.com/Skorp7/ot-harjoitustyo/blob/master/dokumentointi/arkkitehtuuri.md)
* [Testausdokumentti](https://github.com/Skorp7/ot-harjoitustyo/blob/master/dokumentointi/testaus.md)

## Releaset

* [Viikko 5](https://github.com/Skorp7/ot-harjoitustyo/releases/tag/viikko5)
* [Viikko 6](https://github.com/Skorp7/ot-harjoitustyo/releases/tag/viikko6)

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
mvn compile exec:java -Dexec.mainClass=dicip.MainProg
```
Tai voit luoda .jar-tiedoston komennolla
```
mvn package
```
ja käynnistää juuri luodun .jar-tiedoston kansiosta target/ komennolla
```
java -jar tuotannonohjaus-1.0-SNAPSHOT.jar
```

### Checkstyle

Checkstyle-tarkistukset saa ajettua komennolla
```
mvn jxr:jxr checkstyle:checkstyle
```
Raportin saa nähtäväksi avaamalla selaimella tiedoston target/site/checkstyle.html

### JavaDoc

JavaDoc:in saa generoitua komennolla
```
mvn javadoc:javadoc
```
JavaDocin saa nähtäväksi avaamalla selaimella tiedoston target/site/apidocs/index.html
