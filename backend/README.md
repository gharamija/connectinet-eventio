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
/filter - putem urla šalje:
 sort ("uzlazno" ili "silazno" - po vremenu)
 lokacija (enum poštujte CAPSLOCK)
 vrijeme (1 dan, 7 dana, 8 dana)
 vrsta(enum poštujte CAPSLOCK)
 zavrseno (0 - prikazuje samo aktivne, 1 - prikazuje samo završene, kad ne pošaljete ništa oboje)
 placeno  (0 - prikaz bez placanja, 1 - prikaz s placanje, kad ne pošaljete ništa oboje)

/izrada
{
  "organizator": {
    "username": "organizator_username",
    "id": "organizator_id",
    "uloga": "2"
  },
  "nazivDogadaja": "Naziv događaja",
  "vrsta": "ENUM",
  "lokacija": "ENUM",
  "opisLokacije": "Opis lokacije",
  "vrijemePocetka": "2023-12-31T18:00:00",    //pripazite na format
  "cijenaUlaznice": "100.00",
  "opis": "Opis događaja",
  "galerija": "http://putanja-do-galerije.com"
}

## /user
/all    - samo admin
vaća sve korisnike

/register
preko foruma kreira novog korisnika

/validate
endpoint koji vraća korisnika.

## /organizator
... nastavit