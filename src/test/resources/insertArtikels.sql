insert into artikelgroepen(naam) values ('test');
insert into artikels(naam,aankoopprijs,verkoopprijs,soort,artikelgroepid,houdbaarheid)
values('testFood',10,12,'F',(select id from artikelgroepen where naam='test'),5);
insert into artikels(naam,aankoopprijs,verkoopprijs,soort,artikelgroepid,garantie)
values('testNonFood',100,120,'NF',(select id from artikelgroepen where naam='test'),24);
insert into kortingen(artikelid,vanafAantal,percentage) values((select id from artikels where naam='testFood'),5,2);