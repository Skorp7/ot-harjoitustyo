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

  - Työntekijät voivat luoda tilauksia ja tehdä niihin työvaihemerkintöjä sekä hakea seurantakoodilla tai päivämäärällä tilauksia tietokannasta. Työntekijät voivat myös poistaa mahdollisen virhelyönnin seurauksena tekemänsä viimeisimmän työvaiheen tai lisäämänsä tilauksen sen luomisnäkymässä.

  - Työnjohtajalla on työntekijöiden toiminnallisuuksien lisäksi mahdollisuus nähdä kuukausikohtaisia tilastoja tilauksista sekä työntekijäkohtaisia työmääriä. Työnjohtajat voivat poistaa työntekijöitä, tilauksia tai työvaiheita. Työnjohtajat voivat luoda käyttäjätunnuksia ja määrittää tunnuksien oikeudet.
  
  - Sovellusta käyttöönottaessa on olemassa jo yksi tunnus jolla on työnjohtajan oikeudet.

## Käyttöliittymäluonnos




## Jatkokehityksen tavoitteet

Käyttöliittymä on tarkoitus rakentaa siten, että lopullisessa versiossa on mahdollista tulostaa jokaiselle tilaukselle oma viivakoodi/qr-koodi ja jokaiselle työntekijälle oma työvaihelista, jossa on koodit jokaista työvaihetta kohti. Työvaiheen tekeminen merkataan skannaamalla tilauksen seurantakoodi ja työvaihekoodi peräkkäin. Tällöin sovellus lisää tietokantaan tietyn seurantakoodin alle työvaiheen joka sisältää tiedon työntekijästä ja aikaleiman. Lisäksi on mahdollista merkitä info-kenttään merkintöjä jos johonkin työvaiheeseen liittyy erityistietoja (esim. purennan määritys hankalaa jäljennösvirheen vuoksi). Tarkoitus kuitenkin olisi että työntekijä säästyisi klikkailemiselta ja kirjoittamiselta ja pelkkä viivakoodiskannaus riittäisi peruskäyttöön.
