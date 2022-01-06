insert into sd_status (id , bezeichnung) values (10, "in Vorbereitung");
insert into sd_status (id , bezeichnung) values (11, "online");
insert into sd_status (id , bezeichnung) values (12, "eingeladen");
insert into sd_status (id , bezeichnung) values (13, "zugesagt");
insert into sd_status (id , bezeichnung) values (14, "abgesagt");


insert into sd_kanal (id , bezeichnung) values (20, "unklar");
insert into sd_kanal (id , bezeichnung) values (21, "Zeitung");
insert into sd_kanal (id , bezeichnung) values (22, "Jobbörse");
insert into sd_kanal (id , bezeichnung) values (23, "Xing");
insert into sd_kanal (id , bezeichnung) values (24, "LinkedIn");
insert into sd_kanal (id , bezeichnung) values (25, "Flyer");



insert into stellenangebot (id , bezeichnung, beginn, ende, notizen, id_sd_status, id_sd_kanal_access, id_kanal )
values (9001, "Java Entwickler", sysdate()-30 , sysdate()-5, "dringend gesucht", 11, 00);

insert into kanal (id , bezeichnung, stellenangebot) values (3001, 9001, "Zeitung", 21);
insert into kanal (id , bezeichnung, stellenangebot) values (3002, 9001, "Jobbörse", 22);
insert into kanal (id , bezeichnung, stellenangebot) values (3003, 9001, "Xing", 23);
