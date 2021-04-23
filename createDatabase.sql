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

create table if not exists addresses(
    addressId int generated always as identity,
    country varchar not null,
    city varchar not null,
    street varchar not null,
    houseNumber varchar not null,
    primary key(addressId)
);

create table if not exists users (
  userId int generated always as identity,
  username varchar not null unique,
  password varchar not null,
  email varchar not null unique,
  firstName varchar not null,
  surname varchar not null,
  phone varchar,
  activated boolean not null default TRUE,
  addressId int not null,
  userRole roleType not null,
  primary key (userId),
  constraint fkAddress
    foreign key (addressId)
    references addresses(addressId)
);

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

