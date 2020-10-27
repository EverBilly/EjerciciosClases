package controladores_dao;

import cn.hutool.core.text.csv.CsvUtil;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.*;
import jxl.write.Number;
import lombok.extern.log4j.Log4j2;
import modelos.Usuario;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import vistas_modelos.UsuarioV;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Locale;

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


    ////////////////////////////////////////////////////////////////////
    private String db = "cons_rr_ss";
    private String user = "cons_rs_des";
    private String password = "cons_rs_des";
    private String url = "jdbc:mysql://172.16.200.28:3306/"+db;


    private Connection conn = null;
    /** ruta y archivo de destino */
//    File file = new File("e:\\temporal\\output.xls");

    /**
     * Constructor de clase
     */
    public Connection Conexion()
    {
        this.url = "jdbc:mysql://172.16.200.28:3306/"+this.db;
        try{
            //obtenemos el driver de para mysql
            Class.forName("com.mysql.jdbc.Driver");
            //obtenemos la conexión
            conn = DriverManager.getConnection( this.url, this.user , this.password );
            if ( conn!=null ){
                System.out.println("Conexión a la base de datos "+this.db+"...... Listo ");
            }
        }catch(SQLException e){
            System.err.println( e.getMessage() );
        }catch(ClassNotFoundException e){
            System.err.println( e.getMessage() );
        }
        return conn;
    }

//    public void ejecutar(PreparedStatement consulta, int numero) {
//
//
//        if(numero > 0) {
//            try {
//                consulta.setString(1, "Juan");
//                consulta.setString(2, "Herrera");
//                consulta.setString(3, "44662233");
//                consulta.addBatch();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            ejecutar(consulta, numero - 1);
//        }
//    }


    public void EjercicioPreparedStatement() {
        long inicio = System.currentTimeMillis();
        Connection connection;
        PreparedStatement preparedStatement = null;
        String url = "jdbc:mysql://localhost/java?serverTimezone=GMT";
        String user = "ever";
        String pass = "Ever2020--";

        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection(url, user, pass);
            String sql = "insert into usuario (nombre, apellido, telefono) values(?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
//            ejecutar(preparedStatement, 1000);

            for (int i = 0; i < 2; i++) {
                preparedStatement.setString(1, "Juan");
                preparedStatement.setString(2, "Herrera");
                preparedStatement.setString(3, "44662233");
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        long fin = System.currentTimeMillis();
        System.out.println("PreparedStatement: " + (fin - inicio));
    }
    ////////////////////////////////////////////////////////////////////

    //EXPORTAR A EXCEL

    File file = new File("D:\\output.xls");

    public void Exportar(Usuario usuario) {

        long inicio = System.currentTimeMillis();
        int row = 0;
        WritableFont writableFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.NO_BOLD);
        WritableCellFormat writableCellFormat= new WritableCellFormat(writableFont);

        //Interfaz para una hoja de calculo
        WritableSheet writableSheet = null;
        WritableWorkbook writableWorkbook = null;

        //Establece la configuración regional para generar la hoja de cálculo
        WorkbookSettings workbookSettings = new WorkbookSettings();
        workbookSettings.setLocale(new Locale("en", "EN"));

        try {
            writableWorkbook = Workbook.createWorkbook(file, workbookSettings);
            //Hoja con nombre de la tabla
            writableWorkbook.createSheet("data", 0);
            writableSheet = writableWorkbook.getSheet(0);
            log.debug("Creando hoja de Excel");
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }

        //CONSULTA SQL
//        String sql = "select nombre, apellido, telefono, id from usuario";
        String sql = "SELECT -- CV.ID_CONVERSACION,\n" +
                "   \t(SELECT NOMBRE_USUARIO FROM USUARIOS WHERE ID_USUARIO = CV.ID_USUARIO_INICIO) ABRE, \n" +
                "   \tCV.ID_USUARIO, \n" +
                "   \t(SELECT NOMBRE_USUARIO FROM USUARIOS WHERE ID_USUARIO = CV.ID_USUARIO) CIERRA, \n" +
                "   \tCV.NOMBRE_RED_SOCIAL DESTINO, \n" +
                "   \tCV.NOMBRE_BOT, \n" +
                "   \tCV.ID_RRSS_EXTERNO ORIGEN, \n" +
                "   \tDATE(CV.FECHA_ATENCION) FECHA_INICIO, \n" +
                "   \tCONCAT('',TIME(CV.FECHA_ATENCION)) HORA_INICIO, \n" +
                "   \tDATE(CV.FECHA_FINALIZACION) FECHA_FINAL, \n" +
                "   \tCONCAT('',TIME(CV.FECHA_FINALIZACION)) HORA_FINAL, \n" +
                "   \tMC.DURACION, \n" +
                "   \tMC.TIEMPO_COLA COLA, \n" +
                "       CONCAT(MC.FECHA_PRIMER_MENSAJE_CLIENTE,' ', MC.HORA_PRIMER_MENSAJE_CLIENTE ) PRIMER_RESPUESTA, \n" +
                "   \tMC.TME_CLIENTE TIEMPO_PRIMERA, \n" +
                "   \tMC.TME, \n" +
                "   \tTG.GESTION CATEGORIA, \n" +
                "   \tTR.RESOLUCION TIPO_RESOLUCION, \n" +
                "   \tR.RESOLUCION, \n" +
                "   \tR.OPCION_BOT OPCION_MENU_BOT \n" +
                "   FROM \n" +
                "   \tCONVERSACIONES_REPORTE AS CV \n" +
                "   \tLEFT OUTER JOIN TIPOS_GESTION TG ON TG.ID_TIPO_GESTION = CV.ID_GESTION \n" +
                "   \tINNER JOIN RESOLUCIONES R  ON R.ID_CONVERSACION = CV.ID_CONVERSACION \n" +
                "   \tINNER JOIN TIPOS_RESOLUCIONES TR ON TR.ID_TIPO_RESOLUCION = R.TIPO_RESOLUCION  \n" +
                "   \tLEFT OUTER JOIN METRICAS_CONVERSACION MC  ON MC.ID_CONVERSACION = CV.ID_CONVERSACION \n" +
                " WHERE CV.FECHA_FINALIZACION IS NOT NULL AND \n" +
                "\t\tCV.ESTADO = 3  \n" +
                "\t\tAND   cast(CV.FECHA_ATENCION AS DATE) between '2020-08-01' and '2020-08-30'\n" +
                "   AND CV.ID_EMPRESA = 17 \n" +
                "\t ORDER BY CV.ID_CONVERSACION DESC;";
        try {
            PreparedStatement preparedStatement = Conexion().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            log.info("Obteniendo Resultados");
            while (resultSet.next()) {
                Label ABRE = new Label(0, row, resultSet.getString("ABRE"), writableCellFormat);
                Number ID_USUARIO = new Number(1, row, resultSet.getInt("ID_USUARIO"), writableCellFormat);
                Label CIERRA = new Label(2, row, resultSet.getString("CIERRA"), writableCellFormat);
                Label DESTINO = new Label(3, row, resultSet.getString("DESTINO"), writableCellFormat);
                Label NOMBRE_BOT = new Label(4, row, resultSet.getString("NOMBRE_BOT"), writableCellFormat);
                Label ORIGEN = new Label(5, row, resultSet.getString("ORIGEN"), writableCellFormat);
                Label FECHA_INICIO = new Label(6, row, resultSet.getString("FECHA_INICIO"), writableCellFormat);
                Label HORA_INICIO = new Label(7, row, resultSet.getString("HORA_INICIO"), writableCellFormat);
                Label FECHA_FINAL = new Label(8, row, resultSet.getString("FECHA_FINAL"), writableCellFormat);
                Label HORA_FINAL = new Label(9, row, resultSet.getString("HORA_FINAL"), writableCellFormat);
                Label DURACION = new Label(10, row, resultSet.getString("DURACION"), writableCellFormat);
                Label COLA = new Label(11, row, resultSet.getString("COLA"), writableCellFormat);
                Label PRIMER_RESPUESTA = new Label(12, row, resultSet.getString("PRIMER_RESPUESTA"), writableCellFormat);
                Label TIEMPO_PRIMERA = new Label(13, row, resultSet.getString("TIEMPO_PRIMERA"), writableCellFormat);
                Label TME = new Label(14, row, resultSet.getString("TME"), writableCellFormat);
                Label CATEGORIA = new Label(15, row, resultSet.getString("CATEGORIA"), writableCellFormat);
                Label TIPO_RESOLUCION = new Label(16, row, resultSet.getString("TIPO_RESOLUCION"), writableCellFormat);
                Label RESOLUCION = new Label(17, row, resultSet.getString("RESOLUCION"), writableCellFormat);
                Label OPCION_MENU_BOT = new Label(18, row, resultSet.getString("OPCION_MENU_BOT"), writableCellFormat);

                row++;
                try {
                    writableSheet.addCell(ABRE);
                    writableSheet.addCell(ID_USUARIO);
                    writableSheet.addCell(CIERRA);
                    writableSheet.addCell(DESTINO);
                    writableSheet.addCell(NOMBRE_BOT);
                    writableSheet.addCell(ORIGEN);
                    writableSheet.addCell(FECHA_INICIO);
                    writableSheet.addCell(HORA_INICIO);
                    writableSheet.addCell(FECHA_FINAL);
                    writableSheet.addCell(HORA_FINAL);
                    writableSheet.addCell(DURACION);
                    writableSheet.addCell(COLA);
                    writableSheet.addCell(PRIMER_RESPUESTA);
                    writableSheet.addCell(TIEMPO_PRIMERA);
                    writableSheet.addCell(TME);
                    writableSheet.addCell(CATEGORIA);
                    writableSheet.addCell(TIPO_RESOLUCION);
                    writableSheet.addCell(RESOLUCION);
                    writableSheet.addCell(OPCION_MENU_BOT);
                }
                catch (WriteException ex) {
                    log.info(ex.getMessage());
                }
            }
        }
        catch( SQLException e ){
            System.err.println( e.getMessage() );
        }

        //Escribir archivo en el disco
        try {
            writableWorkbook.write();
            writableWorkbook.close();
            log.info("Escribiendo en Disco");
        }
        catch (IOException e) {
            log.info(e.getMessage());
        }
        catch (WriteException ex) {
            log.info(ex.getMessage());
        }
        log.info("Finalizado");
        long fin = System.currentTimeMillis();
        System.out.println("PreparedStatement: " + (fin - inicio));
    }

    //////////////////////////////////////////////////////////////////////
    public void ExportaPOI(Usuario usuario) {
        long inicio = System.currentTimeMillis();
        Connection connection;
        PreparedStatement preparedStatement = null;
//        String url = "jdbc:mysql://localhost/aliens?serverTimezone=GMT";
//        String user = "ever";
//        String pass = "Ever2020--";
        String db = "cons_rr_ss";
        String user = "cons_rs_des";
        String pass = "cons_rs_des";
        String url = "jdbc:mysql://172.16.200.28:3306/"+db;


        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, pass);

            Statement statement = connection.createStatement();

            FileOutputStream fileOut = new FileOutputStream("D:\\Nuevo.xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet worksheet = workbook.createSheet("Hoja1");

            Row row = worksheet.createRow((short)0);

            row.createCell(0).setCellValue("name");
            row.createCell(1).setCellValue("points");
            row.createCell(2).setCellValue("id");
            row.createCell(18).setCellValue("ule");

            Row row1;
            ResultSet resultSet = statement.executeQuery("SELECT -- CV.ID_CONVERSACION,\n" +
                    "   \t(SELECT NOMBRE_USUARIO FROM USUARIOS WHERE ID_USUARIO = CV.ID_USUARIO_INICIO) ABRE, \n" +
                    "   \tCV.ID_USUARIO, \n" +
                    "   \t(SELECT NOMBRE_USUARIO FROM USUARIOS WHERE ID_USUARIO = CV.ID_USUARIO) CIERRA, \n" +
                    "   \tCV.NOMBRE_RED_SOCIAL DESTINO, \n" +
                    "   \tCV.NOMBRE_BOT, \n" +
                    "   \tCV.ID_RRSS_EXTERNO ORIGEN, \n" +
                    "   \tDATE(CV.FECHA_ATENCION) FECHA_INICIO, \n" +
                    "   \tCONCAT('',TIME(CV.FECHA_ATENCION)) HORA_INICIO, \n" +
                    "   \tDATE(CV.FECHA_FINALIZACION) FECHA_FINAL, \n" +
                    "   \tCONCAT('',TIME(CV.FECHA_FINALIZACION)) HORA_FINAL, \n" +
                    "   \tMC.DURACION, \n" +
                    "   \tMC.TIEMPO_COLA COLA, \n" +
                    "       CONCAT(MC.FECHA_PRIMER_MENSAJE_CLIENTE,' ', MC.HORA_PRIMER_MENSAJE_CLIENTE ) PRIMER_RESPUESTA, \n" +
                    "   \tMC.TME_CLIENTE TIEMPO_PRIMERA, \n" +
                    "   \tMC.TME, \n" +
                    "   \tTG.GESTION CATEGORIA, \n" +
                    "   \tTR.RESOLUCION TIPO_RESOLUCION, \n" +
                    "   \tR.RESOLUCION, \n" +
                    "   \tR.OPCION_BOT OPCION_MENU_BOT \n" +
                    "   FROM \n" +
                    "   \tCONVERSACIONES_REPORTE AS CV \n" +
                    "   \tLEFT OUTER JOIN TIPOS_GESTION TG ON TG.ID_TIPO_GESTION = CV.ID_GESTION \n" +
                    "   \tINNER JOIN RESOLUCIONES R  ON R.ID_CONVERSACION = CV.ID_CONVERSACION \n" +
                    "   \tINNER JOIN TIPOS_RESOLUCIONES TR ON TR.ID_TIPO_RESOLUCION = R.TIPO_RESOLUCION  \n" +
                    "   \tLEFT OUTER JOIN METRICAS_CONVERSACION MC  ON MC.ID_CONVERSACION = CV.ID_CONVERSACION \n" +
                    " WHERE CV.FECHA_FINALIZACION IS NOT NULL AND \n" +
                    "\t\tCV.ESTADO = 3  \n" +
                    "\t\tAND   cast(CV.FECHA_ATENCION AS DATE) between '2020-08-01' and '2020-08-30'\n" +
                    "   AND CV.ID_EMPRESA = 17 \n" +
                    "\t ORDER BY CV.ID_CONVERSACION DESC;");

            while (resultSet.next()) {
                int a = resultSet.getRow();
                row1 = worksheet.createRow((short)a);
                for(int i = 0; i <= 18; i++) {
                    row1.createCell(i).setCellValue(resultSet.getString(i+1));
                }
            }
            workbook.write(fileOut);
            fileOut.flush();
            fileOut.close();
            resultSet.close();
            statement.close();
            connection.close();
            System.out.println("Exportado");

        }catch(SQLException ex){
            System.out.println(ex);
        }catch(IOException ioe){
            System.out.println(ioe);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        log.info("Finalizado");
        long fin = System.currentTimeMillis();
        System.out.println("PreparedStatement: " + (fin - inicio) + " Inicio: "+inicio + " Fin: "+fin );

    }

    /////////////////////////////////////////////////////////////////////


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