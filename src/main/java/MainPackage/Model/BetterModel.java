package MainPackage.Model;

import MainPackage.Annotation.Column;
import MainPackage.Annotation.ID;
import MainPackage.Annotation.Table;
import MainPackage.Util.ConnectionHelper;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class BetterModel {

    public static void migrateData(Class clazz) {
        String tableName = clazz.getSimpleName();
        //neu co annotation table thi su dung:
        if (clazz.isAnnotationPresent(Table.class)) {
            Table table = (Table) clazz.getAnnotation(Table.class);
            if (table.name() != null && table.name().length() > 0) {
                tableName = table.name();
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE TABLE");
        stringBuilder.append(" ");
        stringBuilder.append(tableName);
        stringBuilder.append("(");
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields
        ) {
            //check xem truong co danh dau la clumn hay khong
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                stringBuilder.append(column.name());
                stringBuilder.append(" ");
                stringBuilder.append(column.type());
            } else {
                //neu khong thi suy dien ra tu truong
                stringBuilder.append(field.getName());
                stringBuilder.append(" ");
                if (field.getType().getSimpleName().equals("int")) {
                    stringBuilder.append("INT");
                } else if (field.getType().getSimpleName().equals("String")) {
                    stringBuilder.append("VARCHAR(200)");
                }
            }
            if (field.isAnnotationPresent(ID.class)) {
                ID id = field.getAnnotation(ID.class);
                if (id.autoIncrement()) {
                    stringBuilder.append(" AUTO_INCREMENT");
                }
                stringBuilder.append(" PRIMARY KEY");
            }
            stringBuilder.append(",");
        }
        stringBuilder.setLength(stringBuilder.length() - 1);
        stringBuilder.append(")");
        System.out.println(stringBuilder.toString());
        Connection cnn = null;
        try {
            cnn = ConnectionHelper.getConnection();
            Statement stt = cnn.createStatement();
            stt.execute(stringBuilder.toString());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public static void saveData(Object obj) throws IllegalAccessException {
        StringBuilder stringBuilder = new StringBuilder();
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        stringBuilder.append("INSERT INTO ");
        if (clazz.isAnnotationPresent(Table.class)) {
            Table table = (Table) clazz.getAnnotation(Table.class);
            stringBuilder.append(table.name());
            stringBuilder.append(" ");
        } else {
            stringBuilder.append(clazz.getSimpleName());
            stringBuilder.append(" ");
        }
        //StudentData (ID,name,address,age)
        stringBuilder.append("(");
        for (Field field : fields
        ) {
            ID id = field.getAnnotation(ID.class);
            if (field.isAnnotationPresent(Column.class)) {
                if (field.isAnnotationPresent(ID.class) && id.autoIncrement()) {
                    continue;
                } else {
                    Column column = field.getAnnotation(Column.class);
                    stringBuilder.append(column.name());
                    stringBuilder.append(",");
                }
            } else {
                if (field.isAnnotationPresent(ID.class) && id.autoIncrement()) {
                    continue;
                } else {
                    stringBuilder.append(field.getName());
                    stringBuilder.append(",");
                }
            }
        }
        stringBuilder.setLength(stringBuilder.length() - 1);
        stringBuilder.append(")");
        //VALUES (0,'Nguyen Van Quy','Ha Noi',30)
        stringBuilder.append(" VALUES ");
        stringBuilder.append("(");
        for (Field field : fields
        ) {
            ID id = field.getAnnotation(ID.class);
            if (field.isAnnotationPresent(ID.class) && id.autoIncrement()) {
                continue;
            } else {
                field.setAccessible(true);
                if (field.getType().getSimpleName().equalsIgnoreCase("String")) {
                    stringBuilder.append("'");
                    stringBuilder.append(field.get(obj));
                    stringBuilder.append("',");
                } else {
                    stringBuilder.append(field.get(obj));
                    stringBuilder.append(",");
                }
            }
        }
        stringBuilder.setLength(stringBuilder.length() - 1);
        stringBuilder.append(")");
        System.out.println(stringBuilder.toString());
        try {
            Connection cnn = ConnectionHelper.getConnection();
            Statement stt = cnn.createStatement();
            stt.execute(stringBuilder.toString());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
