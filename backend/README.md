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
## /dogadaj
/filter - putem urla šalje:   GET vraća List<responseDogadajDTO>
 sort ("uzlazno" ili "silazno" - po vremenu, po interesu) 
        -> primjer: "vrijeme-uzlazno" ili "zainteresiranost-silazno" 
 lokacija (enum poštujte CAPSLOCK)
 vrijeme (24 sata, 7 dana, 30 dana)
 vrsta(enum poštujte CAPSLOCK)
 zavrseno (Ne - prikazuje samo aktivne, Da - prikazuje samo završene, kad ne pošaljete ništa oboje)
 placeno  (besplatno - prikaz bez placanja, placa se - prikaz s placanje, kad ne pošaljete ništa oboje)

/izrada/?id=INT       POST METODA  (id organizatora) SAMO ORGANIZATOR
{
  "nazivDogadaja": "Naziv događaja",
  "vrsta": "ENUM",
  "lokacija": "ENUM",
  "opisLokacije": "Opis lokacije",
  "vrijemePocetka": "2023-12-31T18:00:00",    //pripazite na format
  "cijenaUlaznice": "100.00",
  "opis": "Opis događaja",
  "galerija": "http://putanja-do-galerije.com"
}

/update/{id}     PUT metoda
Body ko na /izradi
Ako je id na dogadaju i na trenutnom korisniku isti ili ako je korisnik admin
--> dogadaj updatean 

/organizator/{id}  GET
Vraća listu pojedinacni responseDogadajDTO od pojedinog organizatora

/user/{id}  GET
Vraća listu pojedinacni responseDogadajDTO na koje je reagiro pojedini user

/{id}
vraća responseDogadajDTO s idom ou putanje. 

Izgled responseDogadajDTO

npr.
{
    "organizator_id": 4,
    "username": "org",
    "dogadaj_id": 4,
    "nazivDogadaja": "Naziv događaja 4",
    "vrsta": "UMJETNOST",
    "lokacija": "SESVETE",
    "opisLokacije": "Opis lokacije",
    "vrijemePocetka": "2023-12-31T18:00:00",
    "cijenaUlaznice": "100.00",
    "opis": "Opis događaja",
    "galerija": "http://putanja-do-galerije.com",
    "recenzije": [LISTA recenzijaResponseDTO],
    "zainteresiranosti": [LISTA zainteresiranostResponseDTO]
}

Izgled recenzijaResponseDTO u izradi

Izgled zainteresiranostResponseDTO  u izradi 

/recenzija  POST prima recenzijaDTO npr.
{
  "dogadaj_id": 5,
  "kornisnik_id": 2,
  "tekst": "Ovo je odličan događaj!",
  "ocjena": 4
}



## /user
/all    - samo admin  GET
vraća sve korisnike

/register  POST
preko foruma kreira novog korisnika

/   GEZ
endpoint koji vraća resposeKorisnikDTO. (id, username, email i ulogu)

/update/{id}  PUT
Body
{
  "username": "ime",
  "email": "email@email",
  "password": "",      // ukoliko dolazi prazna ne mijenja se
  "uloga": "POSJETITELJ"
}

## /organizator

/register
preko foruma kreira novog organizatora

/update/{id}
{
    "username": "Franjo", 
    "email": "franjoRazarac@gmail.com", 
    "uloga": "ORGANIZATOR",
    "nazivOrganizacije": "Eventio",
    "adresa": "Eventovska 24",
    "poveznica": "http://putanja-do-eventovaca.com",
}

/  GET
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

## /notification

/{id} GET vraća listu notifikacija pojedinog korisnika 

/izrada/{id} POST  u bodyu prima NotifikacijaDTO

izgled NotifikacijaDTO npr.
{
        "vrsta": "MAKSIMIR",
        "lokacija": "SPORT"
}

/{id notifikacije} POST radi brisanje željene notifikacije

## /transakcija 
/paypal/{id}   POST
s 95% uspješnosti vraća 200 ok, ako je ok tada i postavlja zastavicu organizatora clanarina na true

/banka/{id}   POST 
s 90% uspješnosti vraća 200 ok, ako je ok tada i postavlja zastavicu organizatora clanarina na true