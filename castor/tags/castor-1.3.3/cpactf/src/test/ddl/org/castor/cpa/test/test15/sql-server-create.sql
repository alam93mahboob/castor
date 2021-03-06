create table test15_person (
  fname varchar(15)    not null,
  lname varchar(15)    not null,
  bday  datetime       null
)
go

create unique index test15_person_pk on test15_person( fname, lname )
go

create table test15_address (
  fname   varchar(15)  not null,
  lname   varchar(15)  not null,
  id      int          not null,
  street  varchar(30)  null,
  city    varchar(30)  null,
  state   varchar(2)   null,
  zip     varchar(6)   null
)
go

create unique index test15_address_pk on test15_address( id )
go

create table test15_employee (
  fname      varchar(15)    not null,
  lname      varchar(15)    not null,
  start_date datetime       null
)
go

create unique index test15_employee_pk on test15_employee( fname, lname )
go

create table test15_contract (
  fname        varchar(15)  not null,
  lname        varchar(15)  not null,
  policy_no    int          not null,
  contract_no  int          not null,
  c_comment    varchar(90)  null
)
go

create unique index test15_contract_fk on test15_contract( fname, lname )
go

create unique index test15_contract_pk on test15_contract( policy_no, contract_no )
go

create table test15_category_contract (
  policy_no    int      not null,
  contract_no  int      not null,
  cate_id      int      not null
)
go

create table test15_payroll (
  fname       varchar(15)  not null,
  lname       varchar(15)  not null,
  id          int          not null,
  holiday     int          not null,
  hourly_rate int          not null
)
go

create unique index test15_payroll_fk on test15_payroll( fname, lname )
go

create unique index test15_payroll_pk on test15_payroll( id )
go

create table test15_category (
  id    int             not null,
  name  varchar(20)     not null
)
go

create unique index test15_category_pk on test15_category( id )
go

create table test15_only(
  fname varchar(15)    not null,
  lname varchar(15)    not null
)
go

create unique index test15_only_pk on test15_only( fname, lname )
go
