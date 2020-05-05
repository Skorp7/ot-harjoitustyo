# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovelluksella on tarkoitus seurata hammaslaboratorion tuotantoa. Seurantakoodin avulla näkee missä työvaiheessa tilaus on menossa tuotantoketjussa ja ketkä työntekijät ovat tehneet mitkä työvaiheet. Sovellus tallentaa myös historian ja luo erilaisia tilastoja tilausmääristä sekä hakee tietyn päivän aikana käsitellyt tilaukset. 

On tärkeää ettei työvaiheille luoda liian tarkkoja määrityksiä ja ne saa nimetä vapaasti, koska yhdellä tilauksella voi olla useita työvaiheita, joita toisiin samanlaisiin tilauksiin ei tarvitse tehdä ehkä lainkaan. Voi myös esiintyä täysin ennalta arvaamattomia työvaiheita, joita ei ole koskaan aiemmin tehty.

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
    - nähdä tilaston uusista tilauksista 30 päivän aikana
    - nähdä tilausten valmistumisaikojen mediaanin
    - nähdä kuka käyttäjistä on tehnyt eniten työvaiheita
    - nähdä käyttäjien määrän
    - luoda käyttäjätunnuksia
    - muokata käyttäjien oikeuksia
    - poistaa käyttäjiä
    - tyhjentää tietokannan
  
  - Sovellusta käyttöönottaessa on olemassa jo yksi tunnus jolla on työnjohtajan oikeudet. Tämän tunnuksen ('admin') voi poistaa kun on luotu toinen tunnus työnjohtajan oikeuksilla.
  
  - Perusversiossa ei ole toteutettu kirjautumisessa salasanatoimintoa vaan kirjautuminen tapahtuu pelkällä tunnuksella.

## Käyttöliittymäluonnos

- Kirjautumisnäkymä
  - ANNA TUNNUS - tekstikenttä
  - viesti jos epäonnistuu
- Tuotantonäkymä
  - LUO UUSI TILAUS
    - (anna koodi) -tekstikenttä
    - viesti onnistumisesta/epäonnistumisesta
    - jos tilaus on jo olemassa, antaa virheilmoituksen ja näyttää napin, joka klikatessa siirtää tiedot työvaiheen merkintäikkunaan ja valinta "sisäänkirjaus" on valittuna 
  - LISÄÄ TYÖVAIHE
    - (anna koodi, työvaihe ja lisätietoja) - tekstikentät 
    - ruutuvalinta: uloskirjaus tai uusi sisäänkirjaus - valinta muuttaa tekstikenttää 'työvaihe'
    - uloskirjaus-valinta antaa valittavaksi käytettävän lähettipalvelun 
    - viesti onnistumisesta/epäonnistumisesta
  - ETSI TILAUS KOODILLA
    - (anna koodi) -tekstikenttä
    - viesti onnistumisesta/epäonnistumisesta
    - näyttää tilauksen seurantahistorian
  - ETSI TILAUKSET PÄIVÄMÄÄRÄLLÄ
    - (anna päivämäärä) -tekstikenttä
    - klikatessa 'tänään'-nappia, tulee päivämääräkenttään järjestelmän paikalliseen aikaan perustuva päivämäärä
    - näyttää päivän aikana käsiteltyjen tilausten edellisen työvaiheen
    - jos jotain tilausta klikkaa pitkään, näkee kyseisen tilauksen kaikki työvaiheet
- Hallintanäkymä (työnjohtajille)
  - KÄYTTÄJÄHALLINTA (lisää/muokkaa/poista käyttäjä)
    - (anna tunnus) -tekstikenttä
    - käyttäjäroolin valinta
    - varoitusikkuna muokkaamisesta ja poistamisesta
    - ilmoitus onnistumesta/epäonnistumisesta
  - TILASTOT
    - näyttää automaattisesti tilastokäppyrän kuukauden uusista tilauksista päiväkohtaisesti
    - näyttää myös käyttäjien määrät käyttäjärooleittain sekä aktiivisimman käyttäjän
  - ASETUKSET
    - tyhjennä tietokanta -nappi
    - varoitus ennen tyhjennystä
    
    
## Jatkokehitysideat harjoitustyöhön

Mahdollisia lisättäviä toiminnallisuuksia:
  - työnantajalle lisätoiminnot:
    - poista tilaus
    - poista työvaihe
    - muuta työntekijän nimi
    - hae tilastot työntekijäkohtaisesti
    - välitä asiakkaalta saatu palaute työntekijälle
  - työntekijälle lisätoiminnot:
    - poistaa mahdollisen virhelyönnin seurauksena tekemänsä viimeisimmän työvaiheen tai lisäämänsä tilauksen sen luomisnäkymässä
  - muut lisätoiminnot:
  	- asiakastiedon liittäminen tilaukseen
    - tilauksen tyypin liittäminen tilaukseen (esim. "purentakisko")
    - tarkemmat tilastot mm. tilausten työstönopeudesta per työntekijä ja työtyyppi
    

## Jatkokehityksen tavoitteet lopulliseen sovellukseen

Harjoitustyönä ei ole tarkoitus tehdä alla mainittuja ominaisuuksia, mutta ne kannattaa ottaa huomioon rakennetta suunnitellessa.
Käyttöliittymä on tarkoitus rakentaa siten, että lopullisessa versiossa on mahdollista tulostaa jokaiselle tilaukselle oma viivakoodi/qr-koodi ja jokaiselle työntekijälle oma työvaihelista, jossa on koodit jokaista työvaihetta kohti. Työvaiheen tekeminen merkataan skannaamalla tilauksen seurantakoodi ja työvaihekoodi peräkkäin. Tällöin sovellus lisää tietokantaan tietyn seurantakoodin alle työvaiheen joka sisältää tiedon työntekijästä ja aikaleiman. Lisäksi on mahdollista merkitä info-kenttään merkintöjä jos johonkin työvaiheeseen liittyy erityistietoja (esim. "purennan määritys hankalaa jäljennösvirheen vuoksi"). Tarkoitus kuitenkin olisi että työntekijä säästyisi klikkailemiselta ja kirjoittamiselta ja pelkkä koodiskannaus riittäisi peruskäyttöön työvaiheiden merkinnässä. Työvaiheiden merkintä tapahtuisi laboratorion työpisteillä pelkän pädin ja viivakoodinlukijan tai vastaavan avulla.
Sisään- ja uloskirjaus tapahtuisi tietokonepäätteellä toimistossa, joten siinä ylimääräinen klikkailu ei ole niin haitaksi.

