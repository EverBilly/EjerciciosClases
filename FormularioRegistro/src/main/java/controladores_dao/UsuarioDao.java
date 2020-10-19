package controladores_dao;

import lombok.extern.log4j.Log4j2;
import modelos.Usuario;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import vistas_modelos.UsuarioV;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@Log4j2
public class UsuarioDao {

    private DataSource dataSource;
    private QueryRunner queryRunner;
    private static UsuarioDao usuarioDao;

    private String SQL_INSERT = "insert into usuario (nombre, apellido, telefono)" +
            "                   values(?,?,?)";
    private String SQL_SELECT = "select * from usuario";
    private String SQL_DELETE = "delete from usuario where id = ?";
    private String SQL_UPDATE = "update usuario set (nombre, apellido, telefono)" +
            "                   values(?,?,?)";

    public UsuarioDao() throws NamingException{
        this.dataSource = UsuarioV.getDataSource();
        this.queryRunner = new QueryRunner(dataSource);
    }

    public static UsuarioDao getInstance() throws NamingException {
        if(usuarioDao == null) {
            usuarioDao = new UsuarioDao();
        }

        return usuarioDao;
    }

//  MOSTRAR USUARIOS
    public List<Usuario> mostrarUsuarios() throws SQLException{
        BeanListHandler<Usuario> resultHandler = new BeanListHandler<>(Usuario.class);

        return queryRunner.query(SQL_SELECT, resultHandler);
    }

//  INSERTAR USUSARIO
    public int insertaUsuario(Usuario usuario) {
        int result = 0;
        Object[] params = {
                usuario.getNombre(), usuario.getApellido(), usuario.getTelefono()
        };

        try {
            result = queryRunner.update(SQL_INSERT, params);
        } catch (SQLException e) {
            log.debug("Error al Insertar" + e.getMessage());
        }
        return result;
    }

//    ACTUALIZAR USUARIO
    public int actualizaUsuario(Usuario usuario) {
        int result = 0;
        Object[] params = {
                usuario.getNombre(), usuario.getApellido(), usuario.getTelefono()
        };

        try {
            result = queryRunner.update(SQL_UPDATE, params);
        } catch (SQLException e) {
            log.debug("Error al Actualizar" + e.getMessage());
        }
        return result;
    }

//    ELIMINAR USUARIO
    public int eliminarUsuario(Usuario usuario) {
        int result = 0;

        Object[] params = { usuario.getId() };

        try {
            result = queryRunner.update(SQL_DELETE, params);
        } catch (SQLException e) {
            log.debug("Error al Eliminar" + e.getMessage());
        }
        return result;
    }
}
//    <?xml version="1.0" encoding="UTF-8"?>
//<!--
//        Licensed to the Apache Software Foundation (ASF) under one or more
//        contributor license agreements.  See the NOTICE file distributed with
//        this work for additional information regarding copyright ownership.
//        The ASF licenses this file to You under the Apache License, Version 2.0
//        (the "License"); you may not use this file except in compliance with
//        the License.  You may obtain a copy of the License at
//
//        http://www.apache.org/licenses/LICENSE-2.0
//
//        Unless required by applicable law or agreed to in writing, software
//        distributed under the License is distributed on an "AS IS" BASIS,
//        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//        See the License for the specific language governing permissions and
//        limitations under the License.
//        -->
//<!-- The contents of this file will be loaded for each web application -->
//<Context>
//
//<!-- Default set of monitored resources. If one of these changes, the    -->
//<!-- web application will be reloaded.                                   -->
//<WatchedResource>WEB-INF/web.xml</WatchedResource>
//<WatchedResource>WEB-INF/tomcat-web.xml</WatchedResource>
//<WatchedResource>${catalina.base}/conf/web.xml</WatchedResource>
//
//<!-- Uncomment this to disable session persistence across Tomcat restarts -->
//<!--
//<Manager pathname="" />
//        -->
//<Resource
//            name="jdbc/java"
//                    auth="Container"
//                    type="javax.sql.DataSource"
//                    username="ever"
//                    password="Ever2020--"
//                    driverClassName="com.mysql.jdbc.Driver"
//                    url="jdbc:mysql://localhost:3306/java"
//                    maxActive="300"
//                    maxIdle="4"
//                    />
//</Context>