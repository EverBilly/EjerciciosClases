create procedure InsertarUsuario(OUT nombre varchar(20), OUT apellido varchar(20), OUT telefono varchar(20), in id int)
begin
  insert into usuario values (nombre, apellido, telefono, id);
end;

