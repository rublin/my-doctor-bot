create table patient
(
	id bigserial not null,
	first_name varchar not null,
	last_name varchar not null,
	date_of_birth timestamp
);

create unique index patient_id_uindex
	on patient (id);

alter table patient
	add constraint patient_pk
		primary key (id);

