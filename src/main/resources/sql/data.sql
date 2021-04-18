INSERT INTO T_MOVIES (id, name, genre) VALUES (1, 'Godfather', 'Drama');
INSERT INTO T_MOVIES (id, name, genre) VALUES (2, 'Hangover', 'Comedy');
INSERT INTO T_MOVIES (id, name, genre) VALUES (3, 'Harry Potter', 'Fantasy');
INSERT INTO T_MOVIES (id, name, genre) VALUES (4, 'Turbo', 'Anime');

--Players
insert into Team (id,name) values(1,'Barcelona');

insert into Player (id, team_id, name, num, position) values(1, 1, 'Lionel Messi', 10, 'Forward');
insert into Player (id, team_id, name, num, position) values(2, 1, 'Andreas Inniesta', 8, 'Midfielder');
insert into Player (id, team_id, name, num, position) values(3, 1, 'Pique', 3, 'Defender');

-- 
insert into Team (id,name) values(1,'Barcelona');
insert into Team (id,name) values(2,'Barcelona2');

INSERT INTO player (id, name, num, position, team_id) values (1, 'Pique1', 11, 'Defender', 1);
INSERT INTO player (id, name, num, position, team_id) values (2, 'Pique2', 12, 'Defender', 1);
INSERT INTO player (id, name, num, position, team_id) values (3, 'Pique3', 13, 'Defender', 2);
INSERT INTO player (id, name, num, position, team_id) values (4, 'Pique4', 14, 'Defender', 2);

SELECT * FROM TEAM;
SELECT * FROM PLAYER;

--To Fetch players based on team name (INNER JOIN)
SELECT p.id, p.name, p.position, t.name
FROM player p
INNER JOIN team t  ON p.team_id=t.id
WHERE t.name='Barcelona';

SELECT p.id, p.name, p.position, t.name
FROM player p, team t  
WHERE p.team_id=t.id 
AND t.name='Barcelona';

--TO launch H2 Console in browser
--http://localhost:8080/h2/login.do
