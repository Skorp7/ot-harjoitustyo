# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovelluksella on tarkoitus seurata hammaslaboratorion tuotantoa. Seurantakoodin avulla näkee missä työvaiheessa tilaus on menossa tuotantoketjussa ja ketkä työntekijät ovat tehneet mitkä työvaiheet. Sovellus tallentaa myös historian ja luo erilaisia tilastoja tilausmääristä.

## Käyttäjät

Sovelluksessa on kaksi käyttäjäryhmää; *työntekijät* ja *työnjohtajat*. 

## Perusversion toiminnallisuus

- Ennen kirjautumista
  - Käyttäjä voi kirjautua sisään
  - Virheellinen tunnus aiheuttaa virheviestin

- Kirjautumisen jälkeen

  - Työntekijät voivat:
    - luoda tilauksia
    - tehdä niihin työvaihemerkintöjä
    - hakea seurantakoodilla tai päivämäärällä tilauksia tietokannasta
    - poistaa mahdollisen virhelyönnin seurauksena tekemänsä viimeisimmän työvaiheen tai lisäämänsä tilauksen sen luomisnäkymässä
    - tehdä tilauksen uloskirjauksen (tilaus poistuu tuotantotiloista mutta saattaa tulla takaisin)

  - Työnjohtajat voivat tämän lisäksi:
    - nähdä kuukausikohtaisia tilastoja tilauksista tilastonäkymässä
    - nähdä työntekijäkohtaisia työmääriä tilastonäkymässä
    - poistaa työntekijöitä, tilauksia tai työvaiheita
    - luoda käyttäjätunnuksia ja määrittää tunnusten oikeudet
  
  - Sovellusta käyttöönottaessa on olemassa jo yksi tunnus jolla on työnjohtajan oikeudet.
  
  - Perusversiossa ei ole toteutettu kirjautumisessa salasanatoimintoa vaan kirjautuminen tapahtuu pelkällä tunnuksella.

## Käyttöliittymäluonnos

- Kirjautumisnäkymä
- Aloitusikkuna
  - TILAUKSEN SISÄÄNKIRAUS
    - (anna koodi tai luo uusi koodi)
  - MERKITSE TYÖVAIHE
    - (anna koodi ja valitse työvaihe sekä työntekijä)
  - TILAUKSEN ULOSKIRJAUS
    - (anna koodi ja valitse toimitustapa)
  - HAE TILAUS
    - (anna koodi tai päivämäärä)
- Tilastoikkuna (vain työnjohtajille)
  - näyttää automaattisesti kuukauden sisäänkirjatut työmäärät päiväkohtaisesti
  - HAE TYÖNTEKIJÄN TUNNUKSELLA
  - LUO TUNNUS
  - POISTA TUNNUS
  - POISTA TILAUS
  - POISTA TYÖVAIHE

## Jatkokehityksen tavoitteet

Käyttöliittymä on tarkoitus rakentaa siten, että lopullisessa versiossa on mahdollista tulostaa jokaiselle tilaukselle oma viivakoodi/qr-koodi ja jokaiselle työntekijälle oma työvaihelista, jossa on koodit jokaista työvaihetta kohti. Työvaiheen tekeminen merkataan skannaamalla tilauksen seurantakoodi ja työvaihekoodi peräkkäin. Tällöin sovellus lisää tietokantaan tietyn seurantakoodin alle työvaiheen joka sisältää tiedon työntekijästä ja aikaleiman. Lisäksi on mahdollista merkitä info-kenttään merkintöjä jos johonkin työvaiheeseen liittyy erityistietoja (esim. purennan määritys hankalaa jäljennösvirheen vuoksi). Tarkoitus kuitenkin olisi että työntekijä säästyisi klikkailemiselta ja kirjoittamiselta ja pelkkä viivakoodiskannaus riittäisi peruskäyttöön.
