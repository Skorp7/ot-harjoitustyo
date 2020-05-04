# Käyttöohje

Lataa tiedosto [tuotannonohjaus.jar](https://github.com/Skorp7/ot-harjoitustyo/releases/tag/viikko7) ja konfigurointitiedosto config.properties.

## Konfigurointi

Ohjelma olettaa käynnistyessään että samasta kansiosta .jar -tiedoston kanssa löytyy tiedosto *config.properties*.

Konfigurointitiedoston sisältö on seuraava:
```
dataFile=jdbc:sqlite:datafile.db
```

Ohjelma luo käynnistyessään tietokantatiedoston *datafile.db* samaan kansioon, johon .jar-tiedosto on ladattu. Jos olet ladannut saman ohjelman eri version aiemmin, kannattaa poistaa vanha versio ja vanha tietokantatiedosto ennen käynnistämistä.

Voit testata ohjelmaa myös jo valmiiksi täydennetyllä tietokannalla. Jos haluat sen testikäyttöön, voit ladata tiedoston *datafileForTutorial.db* ja tallentaa sen samaan kansioon .jar-tiedoston kanssa. Tällöin sinun täytyy myös muuttaa tiedoston *config.properties* sisältö seuraavaksi:
```
dataFile=jdbc:sqlite:datafileForTutorial.db
```


## Ohjelman käynnistäminen

Ohjelman voi käynnistää komentoriviltä menemällä siihen kansioon jossa .jar-tiedosto sijaitsee ja kirjoittamalla komennon:

```
java -jar tuotannonohjaus.jar
```

## Sisäänkirjautuminen

Käynnistyksen jälkeen voi kirjautua sisään tunnuksella 'admin', jolla on kaikki käyttöoikeudet. Kirjoita nimi kenttään ja klikkaa 'Kirjaudu'.

<img src="kuvat/login.png" width=300>

## Käyttäjien lisääminen ja poistaminen

On suositeltavaa että käyt ensimmäisenä luomassa toisen tunnuksen, jolla on kaikki käyttöoikeudet ja poistat tunnuksen 'admin'.

Uusi käyttäjä lisätään menemällä vasemmanpuoleisesta valikosta kohtaan 'Hallinta'. Aukeaa hallinta-näkymä joka aukeaa vain *Työnjohtajan* oikeuksilla.

<img src="kuvat/adduser.png" width=600>

Valitse sitten oikeanpuolimmaisesta valikosta 'Käyttäjänhallinta' ja 'Lisää käyttäjä'. Valitse käyttäjärooliksi *Työnjohtaja*.

<img src="kuvat/adduser_boss.png" width=400>

Klikkaa painiketta 'Lisää käyttäjä'. Ruutuun tulee palaute, onnistuiko käyttäjän lisääminen vai ei.

Jos onnistui, nyt voit halutessasi poistaa tunnuksen 'admin'. 
Se onnistuu menemällä ylävalikosta kohtaan 'Poista käyttäjä'. Kirjoita tekstikenttään 'admin' ja klikkaa "Poista käyttäjä". Vastaa myös varoitusviestiin OK. Ohjelma kirjaa sinut nyt automaattisesti ulos ja voit kirjautua takaisin sisään juuri luomallasi toisella tunnuksella.

<img src="kuvat/rmuser_alert.png" width=500>

Ohjelma pitää huolen siitä, että tietokannassa on aina vähintään yksi käyttäjä, jolla on *Työnjohtajan* oikeudet. Ainoaa jäljellä olevaa *Työnjohtajaa* ei siis voi poistaa tai sen käyttäjäroolia muuttaa.
Ohjelma ei myöskään poista kokonaan sellaisia käyttäjiä, jotka ovat jo luoneet tilauksia tai tehneet työvaihemerkintöjä, ettei tilastoista häviä tietoa. Näiltä käyttäjiltä poistataan vain sisäänkirjautumisoikeus, jonka *Työnjohtajan* oikeuksilla voi antaa myöhemmin takaisin.
