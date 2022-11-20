Dolineanu Miruna - grupa 321CD
Tema 1 POO - GwentStone


Acest fisier contine detalii despre implementarea si 
structurarea codului pentru rezolvarea temei.


 - Pachetul cards

 ~ Contine clasa abstracta Card, care este mostenita de toate
    clasele din pachet, si trei subpachete, organizate in functie
    de tipul de carte: minion, environment si hero.

 ~ Fiecare subpachet contine o clasa specifica tipului de carte
    si subclase pentru fiecare carte (ex: Warden, Firestorm etc.).
 
 ~ Fiecare carte contine si o metoda ce implementeaza abilitatea
    speciala corespunzatoare (acolo unde este cazul).

 - Pachetul components

 ~ Contine o clasa Constants folosita in program pentru a evita
    erorile de checkstyle. Clasa contine constante numerice
    pentru randurile mesei, dimensiunile sale si maximul de 
    viata al eroilor

 ~ Contine o clasa Player care pastreaza informatiile ce tin de
    fiecare jucator, precum pachetul de carti jucat, eroul,
    mana jucatorului etc. 

 ~ Contine o clasa GameTable care contine randurile mesei de
    joc, cat si o matrice care memoreaza ce carti sunt
    inghetate pentru tura respectiva si alta care tine
    cont de cartile care au atacat in tura respectiva.

 ~ Contine o clasa GameInfo, care pastreaza toate informatiile
    necesare pentru executarea jocului: jucatorii, masa de joc,
    jucatorul activ, inputul primit pentru jocul respectiv etc.

 ~ Clasa Statistics: contine informatii despre statistica
    jocurilor jucate si implementeaza metodele pentru afisarea
    lor.

 - Pachetul commands

 ~ Clasa StartGamEngine: contine metode ce implementeaza pregatirea 
    necesara inceperii unui nou joc. Metoda startProgram face 
    instantierile necesare pentru inceput si trece prin fiecare joc 
    in parte. Celelalte metode au ca scop alegerea, crearea si 
    amestecarea pachetului de joc ales de fiecare jucator si crearea 
    instantelor pentru jucatori.

 ~ Clasa RunGame: contine implementarea executarii jocului,
    structurat pe runde, iar implementarea executarii comenzilor
    primite la input a fost realizata in urmatoare clase.

 ~ Clasa TableCommands: contine metodele corespunzatoare
    comenzilor ce au efect direct asupra mesei de joc;
    comenzi precum plasarea unei carti pe masa, executarea
    atacurilor si utilizarea abilitatilor speciale.

 ~ Clasa Debugger: contine implementarea comenzilor din 
    sectiunea de debugging; comenzi precum aflarea
    informatiilor despre un jucator (cartile din pachet sau
    din mana, eroul sau) sau despre starea mesei de joc
    (aflarea unei carti la o pozitie data etc.).

