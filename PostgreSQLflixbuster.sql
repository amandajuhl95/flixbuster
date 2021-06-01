drop trigger if exists has_account on flixbuster_user;
drop function if exists has_account;
DROP TABLE IF EXISTS Flixbuster_User CASCADE;
DROP TABLE IF EXISTS Payment CASCADE;
DROP TABLE IF EXISTS Account;
drop type if EXISTS Abonnement CASCADE;


create extension if not exists "uuid-ossp";

create type Abonnement as enum ('basis', 'standard', 'premium');

CREATE TABLE Account (
    account_id SERIAL PRIMARY KEY,
    UUID uuid default uuid_generate_v4(),
    firstname varchar(30) NOT null,
    lastname varchar(30) not null,
    email varchar(50) unique not null,
    passwd varchar(20) not null,
    birthday date not null,
    phonenumber varchar(15),
    current_abonnement Abonnement
    );
   
create table Payment (
	payment_id int primary key references Account NOT NULL,
	cardnumber char(16) not null,
	securitycode char(3) not null,
	expirationdate char(4) not null
	);

CREATE TABLE Flixbuster_User (
    user_id SERIAL PRIMARY key,
    account_id int not null,
    CONSTRAINT foreignkey_account
   	FOREIGN KEY(account_id) 
    REFERENCES Account(account_id),
    UUID uuid default uuid_generate_v4(),
    username varchar(30) not null,
    child bool default FALSE not null    
    );
   

CREATE OR REPLACE procedure create_account (account_firstname varchar(30), account_lastname varchar(30), account_email varchar(50), account_passwd varchar(20), account_birthday date, account_phonenumber char(15), account_current_abonnement Abonnement, account_cardnumber char(16), account_securitycode char(3), account_expirationdate char(4))	
as $$
declare 
	id int;
begin 
	INSERT INTO account (firstname, lastname, email, passwd, birthday, phonenumber, current_abonnement) VALUES (account_firstname, account_lastname, account_email, account_passwd, account_birthday, account_phonenumber, account_current_abonnement) RETURNING account_id into id;
	INSERT INTO payment (payment_id, cardnumber, securitycode, expirationdate) SELECT id, account_cardnumber, account_securitycode, account_expirationdate;
	insert into flixbuster_user (account_id, username) select id, account_firstname;
end 
$$ language plpgsql;

call create_account('Kasper', 'Frederiksen', 'kasper_frederiksen@gmail.com', '2980', '1995-10-10', '61271486', 'basis', '2097643167861256', '165', '0123');
call create_account('Jesper', 'Larsen', 'jesper_larsen@gmail.com', '989', '1979-03-05', '54601814', 'standard', '2097543218765876', '912', '0522');
call create_account('Jonathan', 'Klausen', 'jonathan_klausen@gmail.com', '1998', '1965-12-24', '61127052', 'premium', '1278479276317942', '764', '1021');

create function has_account() returns trigger as $$
begin 
	if not exists (select 1 from Account where account_id = new.account_id) then 
	raise exception 'Create account first';
end if;
	return new;
end $$ language plpgsql;

create trigger has_account before insert on flixbuster_user
	for each ROW execute procedure has_account();

insert into flixbuster_user (account_id, username) values ((select account_id from Account where firstname = 'Kasper'), 'Betty');
insert into flixbuster_user (account_id, username, child) values ((select account_id from Account where firstname = 'Jonathan'), 'Storm', TRUE);




    





