drop table if exists addresses, users, comments, places, services, schedulerDaysOfWeek, schedules;
drop type if exists roleType, serviceType, dayOfWeekType;

create type roleType as ENUM(
    'ADMIN', 'CUSTOMER', 'MANAGER'
);

create type serviceType as ENUM(
    'WI-FI', 'POOL', 'TV', 'BAR', 'SHOWER', 'TRANSFER', 'BREAKFAST', 'FITNESS', 'PARKING',
    'ANIMALS', 'ALL'
);

create type dayOfWeekType as ENUM(
    'MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT', 'SUN'
);

/*----------------------------------------------------------*/
create table addresses
(
	"addressId" serial not null,
	country text not null,
	city text not null,
	street text not null,
	"houseNumber" text not null
);

alter table addresses
	add constraint addresses_pk
		primary key ("addressId");
/*----------------------------------------------------------*/

/*----------------------------------------------------------*/
create table roles
(
	"roleId" serial not null,
	role text not null
);

alter table roles
	add constraint roles_pk
		primary key ("roleId");
/*----------------------------------------------------------*/

/*----------------------------------------------------------*/
create table users
(
	"userId" serial not null,
	username text not null,
	password text not null,
	email text not null,
	"firstName" text not null,
	surname text not null,
	phone text,
	activated boolean default True not null,
	"roleId" int not null
		constraint "FK_users_roles"
			references roles,
	"addressId" int not null
		constraint "FK_users_addresses"
			references addresses
);

create unique index users_username_uindex
	on users (username);

alter table users
	add constraint users_pk
		primary key ("userId");
/*----------------------------------------------------------*/


create table if not exists comments (
    commentId int generated always as identity,
    userId int not null,
    comment varchar not null,
    primary key (commentId),
    constraint fkUser
      foreign key (userId)
      references users(userId)
);

create table if not exists places (
    placeId int generated always as identity,
    addressId int not null,
    userId int not null,
    placeName varchar not null,
    description varchar not null,
    commentId int not null,
    primary key (placeId),
    constraint fkAddress
      foreign key (addressId)
      references addresses(addressId),
    constraint fkUser
      foreign key (userId)
      references users(userId),
    constraint fkComment
      foreign key (commentId)
      references comments(commentId)
);

create table if not exists services (
    serviceId int generated always as identity,
    service serviceType not null,
    placeId int not null,
    primary key (serviceId),
    constraint fkPlaces
      foreign key (placeId)
      references places(placeId)
);

create table if not exists schedulerDaysOfWeek (
    schedulerDaysOfWeekId int generated always as identity,
    dayOfWeek dayOfWeekType not null,
    workStart time not null,
    workStop time not null,
    price money not null,
    primary key (schedulerDaysOfWeekId)
);

create table if not exists schedules (
    scheduleId int generated always as identity,
    schedulerDaysOfWeekId int not null,
    placeId int not null,
    primary key (scheduleId),
    constraint fkPlaces
      foreign key (placeId)
      references places(placeId),
    constraint fkSchedulerDaysOfWeek
      foreign key (schedulerDaysOfWeekId)
      references schedulerDaysOfWeek(schedulerDaysOfWeekId)
);

