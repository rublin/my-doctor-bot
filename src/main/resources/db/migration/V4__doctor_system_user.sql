delete from doctor;
alter table doctor drop column first_name, drop column last_name, drop column date_of_birth;
alter table doctor add column system_user_id bigint not null unique, add column year_start_experience int ;


alter table doctor
	add constraint doctor_system_user_id_fk
		foreign key (system_user_id) references system_user (id)
			on delete cascade;
