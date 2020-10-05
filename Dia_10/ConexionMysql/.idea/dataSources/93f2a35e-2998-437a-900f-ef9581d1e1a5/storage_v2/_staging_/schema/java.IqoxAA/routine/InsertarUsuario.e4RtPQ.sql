create procedure InsertarUsuario(out nombre varchar(20), out apellido varchar(20), out telefono varchar(20))
begin
insert into usuario values (nombre, apellido, telefono);
end;

