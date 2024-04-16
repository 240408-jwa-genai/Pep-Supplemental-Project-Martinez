-- Use this script to set up your Planetarium database

-- needed for referential integrity enforcement
PRAGMA foreign_keys = 1;

create table users(
	id integer primary key,
	username text unique,
	password text
);

create table planets(
	id integer primary key,
	name text,
	ownerId int references users(id)
);

create table moons(
	id integer primary key,
	name text,
	myPlanetId int references planets(id)
);

INSERT INTO users (username, password) values ();
DELETE FROM planets;
INSERT INTO planets (id,name, ownerId) values (1, "Mars",1);
