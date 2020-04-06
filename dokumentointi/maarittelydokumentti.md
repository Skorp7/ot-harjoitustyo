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
    - tehdä tilauksen uloskirjauksen (tilaus poistuu tuotantotiloista mutta saattaa tulla takaisin)

  - Työnjohtajat voivat tämän lisäksi:
    - nähdä kuukausikohtaisia tilastoja tilauksista tilastonäkymässä
    - luoda käyttäjätunnuksia ja määrittää tunnusten oikeudet
  
  - Sovellusta käyttöönottaessa on olemassa jo yksi tunnus jolla on työnjohtajan oikeudet.
  
  - Perusversiossa ei ole toteutettu kirjautumisessa salasanatoimintoa vaan kirjautuminen tapahtuu pelkällä tunnuksella.

## Käyttöliittymäluonnos

- Kirjautumisnäkymä
  - ANNA TUNNUS
  - viesti jos epäonnistuu
- Aloitusikkuna
  - TILAUKSEN SISÄÄNKIRJAUS
    - (anna koodi tai luo uusi koodi)
    - viesti onnistumisesta/epäonnistumisesta
    - näyttää tilauksen seurantahistorian jos sitä on
  - MERKITSE TYÖVAIHE
    - (anna koodi ja valitse työvaihe sekä työntekijä)
    - viesti onnistumisesta/epäonnistumisesta
  - TILAUKSEN ULOSKIRJAUS
    - (anna koodi ja valitse toimitustapa)
    - viesti onnistumisesta/epäonnistumisesta
  - HAE TILAUS
    - (anna koodi tai päivämäärä)
    - viesti onnistumisesta/epäonnistumisesta
    - näyttää tilauksen seurantahistorian jos haettiin koodilla
    - näyttää päivän aikana käsitellyt tilaukset (koodit) jos haettiin päivämäärällä
- Työnjohtajaikkuna
  - näyttää automaattisesti tilastokäppyrän kuukauden sisäänkirjatuista työmääristä päiväkohtaisesti
  - LUO TUNNUS
    - (anna tunnus ja valitse käyttäjäryhmä)
    - viesti onnistumisesta/epäonnistumisesta
    
    
## Jatkokehitysideat harjoitustyöhön

Mahdollisia lisättäviä toiminnallisuuksia:
  - työnantajalle lisätoiminnot:
    - poista tunnus
    - poista tilaus
    - poista työvaihe
    - hae tilastot työntekijäkohtaisesti
    - välitä asiakkaalta saatu palaute työntekijälle
  - työntekijälle lisätoiminnot:
    - poistaa mahdollisen virhelyönnin seurauksena tekemänsä viimeisimmän työvaiheen tai lisäämänsä tilauksen sen luomisnäkymässä
  - muut lisätoiminnot:
  	- tilausnumeron automaattinen generointi
  	- asiakastiedon liittäminen tilaukseen
    

## Jatkokehityksen tavoitteet lopulliseen sovellukseen

Harjoitustyönä ei ole tarkoitus tehdä alla mainittuja ominaisuuksia, mutta ne kannattaa ottaa huomioon rakennetta suunnitellessa.
Käyttöliittymä on tarkoitus rakentaa siten, että lopullisessa versiossa on mahdollista tulostaa jokaiselle tilaukselle oma viivakoodi/qr-koodi ja jokaiselle työntekijälle oma työvaihelista, jossa on koodit jokaista työvaihetta kohti. Työvaiheen tekeminen merkataan skannaamalla tilauksen seurantakoodi ja työvaihekoodi peräkkäin. Tällöin sovellus lisää tietokantaan tietyn seurantakoodin alle työvaiheen joka sisältää tiedon työntekijästä ja aikaleiman. Lisäksi on mahdollista merkitä info-kenttään merkintöjä jos johonkin työvaiheeseen liittyy erityistietoja (esim. purennan määritys hankalaa jäljennösvirheen vuoksi). Tarkoitus kuitenkin olisi että työntekijä säästyisi klikkailemiselta ja kirjoittamiselta ja pelkkä viivakoodiskannaus riittäisi peruskäyttöön työvaiheiden merkinnässä. Työvaiheiden merkintä tapahtuisi laboratorion työpisteillä pelkän pädin ja viivakoodinlukijan tai vastaavan avulla.
Sisään- ja uloskirjaus tapahtuisi tietokonepäätteellä toimistossa, joten siinä ylimääräinen klikkailu ei ole niin haitaksi.

