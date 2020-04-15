# Arkkitehtuurikuvaus

## Rakenne

Ohjelman rakenne koostuu kolmesta kerroksesta. Pakkaus ui, sisältää käyttöliittymän joka on toteutettu JavaFX:llä,
domain-pakkauksessa on sovelluslogiikka ja database käsittelee tietokantaa.

Pakkauskaavio:

<img src="kuvat/pakkaukset.png" height="300" title="Pakkauskaavio"> 


## Sovelluslogiikka

Keskeistä dataa tässä sovelluksessa edustavat oliot User, Order ja WorkPhase, jotka kuvaavat käyttäjiä, tilauksia ja näihin molempiin liittyviä työvaiheita.

Luokkien keskinäiset suhteet nähdään luokkakaaviosta:

<img src="kuvat/luokka.JPG" height="300" title="Luokkakaavio">

Käyttöliittymä (ui) saa sovelluslogiikan käyttöönsä domain-pakkauksen Service-luokasta. Service käsittelee tietokantaa ja saa tietokannan kautta käsiteltäväkseen olioita User, Order ja WorkPhase.
Tietokantaluokka toteuttaa rajapinnan Data ja tässä sovelluksessa on luotu kaksi tietokantaluokkaa, DataSql ja DataMap, joista jälkimmäinen on vain testausta varten.

Pakkaus/luokkakaaviosta näkee luokkien ja pakkausten suhteet toisiinsa.

<img src="kuvat/luokkapakkaus.png" width="400" title="Luokka/pakkauskaavio">

### Päätoiminnallisuudet

Näytetään muutama toiminnalisuus sekvenssikaaviona.

#### Käyttäjän kirjautuminen

Kun käyttäjä kirjoittaa tekstikenttään olemassaolevan admin-statuksella varustetun käyttäjätunnuksen ja klikkaa painiketta "loginBtn", kontrolli etenee ohjelmassa seuraavasti:

<img src="kuvat/sekvenssi_logIn.JPG" width="max" title="Sekvenssikaavio - logIn"> 

Käyttöliittymän (App) tapahtumakäsittelijä reagoi painikkeen painamiseen kutsumalla sovelluslogiikkaluokan *Service* metodia *login*, jolle annetaan parametriksi teksikenttään syötetty teksti, eli käyttäjätunnuksen nimi. *Service* kutsuu rajapinnan *Data* metodia *getUser* saadakseen tietää onko kyseinen käyttäjä olemassa annetun nimen perusteella. Jos käyttäjä löytyy, *getUser* palauttaa olion *User*, joka sisältää kyseisen käyttäjätunnuksen nimen ja käyttäjästatuksen (tässä tapauksessa status on 'admin', jota merkintään numerolla 1). Käyttäjän löydyttyä *Service* palauttaa käyttöliittymälle (App) arvon *true*.

Seuraavaksi *App* pyytää *Service*ltä kirjautuneen käyttäjän (olion User) metodilla getLoggedInUser selvittääkesen tämän käyttäjästatuksen metodilla *getStatus*, jolle annetaan parametriksi olio *User*. Saatuaan tietoon että käyttäjän status on '1', *App* ottaa adminBtn-napin takaisin käyttöön (lähtökohtaisesti nappi on poissa käytöstä), ja asettaa näkymäksi workScenen, eli sovelluksen varsinaisen käyttönäkymän. Tässä yhteydessä *App* myös klikkaa itse nappia *orderBtn* saadakseen tilaustenhallinta-kentän workScenen valituksi ja nähtäväksi kentäksi.
