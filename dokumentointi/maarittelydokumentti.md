# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovelluksella on tarkoitus seurata hammaslaboratorion tuotantoa. Seurantakoodin avulla näkee missä työvaiheessa tilaus on menossa tuotantoketjussa ja ketkä työntekijät ovat tehneet mitkä työvaiheet. Sovellus tallentaa myös historian ja luo erilaisia tilastoja tilausmääristä sekä hakee tietyn päivän aikana käsitellyt tilaukset. 

On tärkeää ettei työvaiheille luoda liian tarkkoja määrityksiä ja ne saa nimetä vapaasti, koska yhdellä tilauksella voi olla useita työvaiheita, joita toisiin samanlaisiin tilauksiin ei tarvitse tehdä ehkä lainkaan. Voi myös esiintyä täysin ennalata arvaamattomia työvaiheita, joita ei ole koskaan aiemmin tehty.

## Käyttäjät

Sovelluksessa on kaksi käyttäjäryhmää; *työntekijät* ja *työnjohtajat*. 

## Perusversion toiminnallisuus

- Ennen kirjautumista
  - Käyttäjä voi kirjautua sisään TEHTY
  - Virheellinen tunnus aiheuttaa virheviestin TEHTY

- Kirjautumisen jälkeen

  - Työntekijät voivat:
    - luoda tilauksia TEHTY
    - tehdä niihin työvaihemerkintöjä TEHTY
    - hakea seurantakoodilla tai päivämäärällä tilauksia tietokannasta TEHTY
    - tehdä tilauksen uloskirjauksen (tilaus poistuu tuotantotiloista mutta saattaa tulla takaisin) TEHTY

  - Työnjohtajat voivat tämän lisäksi:
    - nähdä kuukausikohtaisia tilastoja tilauksista tilastonäkymässä TEHTY
    - luoda käyttäjätunnuksia ja määrittää tunnusten oikeudet TEHTY
  
  - Sovellusta käyttöönottaessa on olemassa jo yksi tunnus jolla on työnjohtajan oikeudet. TEHTY
  
  - Perusversiossa ei ole toteutettu kirjautumisessa salasanatoimintoa vaan kirjautuminen tapahtuu pelkällä tunnuksella.

## Käyttöliittymäluonnos

- Kirjautumisnäkymä
  - ANNA TUNNUS - tekstikenttä TEHTY
  - viesti jos epäonnistuu TEHTY
- Aloitusikkuna
  - LUO UUSI TILAUS
    - (anna koodi) -tekstikenttä TEHTY
    - viesti onnistumisesta/epäonnistumisesta TEHTY
    - jos tilaus on jo olemassa, antaa virheilmoituksen ja näyttää napin, joka klikatessa siirtää tiedot työvaiheen merkintäikkunaan ja valinta "sisäänkirjaus" on valittuna TEHTY
  - LISÄÄ TYÖVAIHE
    - (anna koodi, työvaihe ja lisätietoja) - tekstikentät TEHTY
    - ruutuvalinta: uloskirjaus tai uusi sisäänkirjaus - muuttaa tekstikenttää 'työvaihe' TEHTY
    - uloskirjaus-valinta antaa valittavaksi käytettävän lähettipalvelun TEHTY
    - viesti onnistumisesta/epäonnistumisesta TEHTY
  - TILAUKSEN ULOSKIRJAUS (liitetty työvaiheen merkintään)
    - (anna koodi ja valitse toimitustapa) - tekstikentät TEHTY
    - viesti onnistumisesta/epäonnistumisesta TEHTY
  - ETSI TILAUS KOODILLA
    - (anna koodi) TEHTY
    - viesti onnistumisesta/epäonnistumisesta TEHTY
    - näyttää tilauksen seurantahistorian TEHTY
  - ETSI TILAUKSET PÄIVÄMÄÄRÄLLÄ
    - (anna päivämäärä) TEHTY
    - klikatessa 'tänään'-nappia, tulee päivämääräkenttään paikalliseen aikaan perustuva päivämäärä TEHTY
    - näyttää päivän aikana käsiteltyjen tilausten edellisen työvaiheen jos haettiin päivämäärällä TEHTY
    - jos jotain tilausta klikkaa pitkään, näkee kyseisen tilauksen kaikki työvaiheet TEHTY
- Työnjohtajaikkuna
  - näyttää automaattisesti tilastokäppyrän kuukauden uusista tilauksista päiväkohtaisesti TEHTY
  - LUO TUNNUS
    - (anna tunnus ja valitse käyttäjäryhmä) - tekstikentät TEHTY
    - viesti onnistumisesta/epäonnistumisesta TEHTY
    
    
## Jatkokehitysideat harjoitustyöhön

Mahdollisia lisättäviä toiminnallisuuksia:
  - työnantajalle lisätoiminnot:
    - poista tunnus TEHTY
    - poista tilaus
    - poista työvaihe
    - muuta työntekijän käyttöoikeus TEHTY
    - tyhjennä tietokanta TEHTY
    - muuta työntekijän nimi
    - hae tilastot työntekijäkohtaisesti
    - välitä asiakkaalta saatu palaute työntekijälle
  - työntekijälle lisätoiminnot:
    - poistaa mahdollisen virhelyönnin seurauksena tekemänsä viimeisimmän työvaiheen tai lisäämänsä tilauksen sen luomisnäkymässä
  - muut lisätoiminnot:
  	- tilausnumeron automaattinen generointi
  	- asiakastiedon liittäminen tilaukseen
    - tilauksen tyypin liittäminen tilaukseen (esim. "purentakisko")
    - erilaiset tilastot mm. tilausten työstönopeudesta
    

## Jatkokehityksen tavoitteet lopulliseen sovellukseen

Harjoitustyönä ei ole tarkoitus tehdä alla mainittuja ominaisuuksia, mutta ne kannattaa ottaa huomioon rakennetta suunnitellessa.
Käyttöliittymä on tarkoitus rakentaa siten, että lopullisessa versiossa on mahdollista tulostaa jokaiselle tilaukselle oma viivakoodi/qr-koodi ja jokaiselle työntekijälle oma työvaihelista, jossa on koodit jokaista työvaihetta kohti. Työvaiheen tekeminen merkataan skannaamalla tilauksen seurantakoodi ja työvaihekoodi peräkkäin. Tällöin sovellus lisää tietokantaan tietyn seurantakoodin alle työvaiheen joka sisältää tiedon työntekijästä ja aikaleiman. Lisäksi on mahdollista merkitä info-kenttään merkintöjä jos johonkin työvaiheeseen liittyy erityistietoja (esim. "purennan määritys hankalaa jäljennösvirheen vuoksi"). Tarkoitus kuitenkin olisi että työntekijä säästyisi klikkailemiselta ja kirjoittamiselta ja pelkkä koodiskannaus riittäisi peruskäyttöön työvaiheiden merkinnässä. Työvaiheiden merkintä tapahtuisi laboratorion työpisteillä pelkän pädin ja viivakoodinlukijan tai vastaavan avulla.
Sisään- ja uloskirjaus tapahtuisi tietokonepäätteellä toimistossa, joten siinä ylimääräinen klikkailu ei ole niin haitaksi.

