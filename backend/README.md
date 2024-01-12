# Upute
## Preduvjeti
- Java 17
- Maven
## Postavljanje PostgreSQL baze
### Instalacija alata
- **PostgreSQL** - https://www.postgresql.org/download/
- **GUI** (pgAdmin ili neki drugi) - https://www.pgadmin.org/download/
- master password postaviti na `postgres` jer ce se ta lozinka koristiti za spajanje
### Izrada baze
- kroz **pgAdmin**, **DBeaver** ili **psql**
- napraviti novu bazu s imenom `eventio-db` i ownerom `postgres`
    ![img.png](img.png)
    ![img_1.png](img_1.png)
- po defaultu bi njegova lozinka trebala biti `postgres` ali za svaki slucaj postaviti je
    ![img_2.png](img_2.png)
    ![img_3.png](img_3.png)
### Pokretanje
- postgreSQL Server (pg_ctl.exe) bi se trebao upaliti na startupu racunala pa nije nužno pokretati pgAdmin da bi se backend spojio na bazu

## Pokretanje aplikacije
- mvn spring-boot:run ili zelena strelica u Eventio razredu

### Mapping
## Admin
- Za brisanje dogadaja koristi DELETE metodu /dogadaj/delete/{id}
- Za uredivanje dogadaja koristi PUT metodu /dogadaj/update/{id}    na isti nacin ko i organizator kad ureduje svoje
- Za brisanje korisnika koristi DELETE metodu /user/delete/{id}
- Za uredivanje korisnika koristi PUT metodu /user/update/{id}    na isti nacin ko i korisnik ureduje sebe
- Za postavljanje nove cijene preplate PUT metodu /organizator/cijena/{iznos}, radi sa stringom
- /user/all    - samo admin  GET
vraća sve korisnike
- /organizator/cijena/{iznos} PUT postavlja cijenu pretplate

## /dogadaj
- /filter - putem urla šalje:   GET vraća List<responseDogadajDTO>
 <br />sort ("uzlazno" ili "silazno" - po vremenu, po interesu) 
        -> primjer: "vrijeme-uzlazno" ili "zainteresiranost-silazno" 
 <br />lokacija (enum poštujte CAPSLOCK)
 <br />vrijeme (24 sata, 7 dana, 30 dana)
 <br />vrsta(enum poštujte CAPSLOCK)
 <br />zavrseno (Ne - prikazuje samo aktivne, Da - prikazuje samo završene, kad ne pošaljete ništa oboje)
 <br />placeno  (besplatno - prikaz bez placanja, placa se - prikaz s placanje, kad ne pošaljete ništa oboje)

- /izrada/?id=INT       POST METODA  (id organizatora) SAMO ORGANIZATOR
<br />Npr. {
  "nazivDogadaja": "Naziv događaja",
  "vrsta": "ENUM",
  "lokacija": "ENUM",
  "opisLokacije": "Opis lokacije",
  "vrijemePocetka": "2023-12-31T18:00:00",
  "cijenaUlaznice": "100.00",
  "opis": "Opis događaja",
}
<br />Slike šaljete idućom POST metodom.
<br />Ukoliko postoje korisnici koji imaju uključene notifikacije za takav dogdađaj, šalju se.
- /slika/{dogadajId} POST
<br />Koristi se za slanje slika (MultipartFile)

- /update/{id}     PUT metoda
Body ko na /izradi
Ako je id na dogadaju i na trenutnom korisniku isti ili ako je korisnik admin

- /organizator/{id}  GET
Vraća listu pojedinacni responseDogadajDTO od pojedinog organizatora

- /user/{id}  GET
Vraća listu pojedinacni responseDogadajDTO na koje je reagiro pojedini user

- /{id}
vraća responseDogadajDTO s idom ou putanje. 

- Izgled responseDogadajDTO
<br />npr.
{
    "organizatorId": 4,
    "username": "org",
    "dogadajId": 4,
    "nazivDogadaja": "Naziv događaja 4",
    "vrsta": "UMJETNOST",
    "lokacija": "SESVETE",
    "opisLokacije": "Opis lokacije",
    "vrijemePocetka": "2023-12-31T18:00:00",
    "cijenaUlaznice": "100.00",
    "opis": "Opis događaja",
    "galerija": "putanja-do-galerije- static",
    "recenzije": [LISTA RecenzijaDTO],
    "trenutna": Zainteresiranost,
    "sigurnoZainteresiranost":Integer,
    "mozdaZainteresiranost": Integer,
    "neDolazeZainteresiranost" : Integer
}

- Izgled RecenzijaDTO <br />npr.
{
"korisnikId": 2,
"dogadajId": 5,
"recenzijaId": 2,
"username": "imekornisika",
"tekst": "Ovo je odličan događaj!",
"ocjena": 5
}

- /recenzija  POST prima recenzijaDTO npr.
{
  "dogadaj_id": 5,
  "kornisnik_id": 2,
  "tekst": "Ovo je odličan događaj!",
  "ocjena": 4
}

- /zainteresiranost   POST stvaranje zainteresiranosti, zahtjeva 
dogadajId INT, korisnikId INT, kategorija Kategorija
izraduje zainteresiranost, 1 korisnik moze za 1 dogadaj imat samo 1 zainteresiranost



## /user

- /register  POST, koristi requestKorisnikDTO, preko foruma kreira novog korisnika

- /   GET
endpoint koji vraća resposeKorisnikDTO. (id, username, email i ulogu)

- /update/{id}  PUT
requestKorisnikDTO
{
  "username": "ime",
  "email": "email@email",
  "password": "",      // ukoliko dolazi prazna ne mijenja se
  "uloga": "POSJETITELJ"
}

## /organizator

- /register
preko foruma kreira novog organizatora

- /update/{id}
OrganizatorDTO 
<br />npr. {
    "username": "Franjo", 
    "email": "organizator@gmail.com", 
    "uloga": "ORGANIZATOR",
    "nazivOrganizacije": "Eventio",
    "adresa": "Eventovska 24",
    "poveznica": "http://putanja-do-eventovaca.com",
}

- /  GET
vraća 
{
    "username": "org",
    "email": "organizator@org",
    "password": null,
    "uloga": "ORGANIZATOR",
    "nazivOrganizacije": "Eventio Corp",
    "adresa": "Adresa BB",
    "poveznica": "http://www.eventio-corp.com",
    "clanarina": false  
}
- /cijena GET vraća cijenu pretplate

## /notification

- /{id} GET vraća listu notifikacija pojedinog korisnika 

- /add/{id} POST  u bodyu prima NotifikacijaDTO

- izgled NotifikacijaDTO npr.
{
        "vrsta": "MAKSIMIR",
        "lokacija": "SPORT"
}

- /{id notifikacije} DELETE radi brisanje željene notifikacije

## /transakcija 
- /paypal/{id}   POST
s 95% uspješnosti vraća 200 ok, ako je ok tada i postavlja zastavicu organizatora clanarina na true

- /banka/{id}   POST 
s 90% uspješnosti vraća 200 ok, ako je ok tada i postavlja zastavicu organizatora clanarina na true