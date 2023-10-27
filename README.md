# connectinet-eventio

# Razvoj i stvaranje promjena

Repozitorij će imati "master" (glavna) granu i "development" (razvojnu) granu.

  - Glavna grana koristi se za "produkciju" i sadrži najnoviju verziju koda koja je objavljena javnosti.

  - Razvojna grana koristi se za razvoj i testiranje u QA okruženju. Na kraju sprinta tj. za svaku kontrolnu točku, sve promjene na razvojnoj grani trebaju biti testirane i spojene na glavnu granu.

Prilikom pokretanja nove značajke, trebate novu granu stvoriti ili od razvojne grane. Kada završite svoj rad, predložite svoje izmjene na razvojnu granu (zadano ponašanje), a ne na glavnu granu. Sve izmjene na razvojnoj grani bit će spojene na glavnu kad sprint završi i "QA testiranje prođe".

Prilikom stvaranja nove grane, dodijelite joj isti naziv kao i ID Exellice za zadatak, na primjer "TAG-1234". TAG koristimo kao placeholder.

Naslov commit poruke treba odgovarati nazivu ID zadatka iz exellice, na primjer "TAG-1 Inicijalizacija repozitorija".

# Korištenje GIT-a

Osnovne naredbe koje morate koristiti:

	- git clone - ako nemate lokalnu kopiju repozitorija sa ovom naredbom klonirate repozitorij
	- git status 
		- informacija o grani na kojoj se trenutno nalazite, promjene na datotekama
		- koristite uvijek, provjerite na kojoj se grani nalazite...NE RADITI na main ili develop grani
	- git checkout - prelazak na drugu granu
		- npr. prelazak na postojecu granu - git checkout grana
		- stvaranje nove grane i prelazak na nju - git checkout -b grana
	- git add ime-datoteke
		- dodati izmjene za commit
	- git commit -m "commit message"
		- commitati promjene sa odgovarajucom porukom prema uputama (koristiti ID taska)
	- git pull
		- povuci najnovije promjene s udaljenog repozitorija
	- git push
		- poslati nove izmjene na udaljeni repozitorij
		- sa ovim saljete sve izmjene, mozete koristiti git push origin ime-grane-za-poslati


Zaključak uputa za korištenje:
	- ne koristiti grane main i develop, koristite nove grane za feature koje radite. Nakon što ste završili pushajte nove izmjene i napravite PR