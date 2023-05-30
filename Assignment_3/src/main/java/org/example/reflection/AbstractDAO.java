package org.example.reflection;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


import org.example.database.ConnectionFactory;


/**
 * @Author: Lucacel Malina
 *  Tehnica Reflection în Java se referă la capacitatea unui program de a
 *  inspecta și manipula dinamic informații despre clase, obiecte, metode și câmpuri în
 *  timpul execuției. Aceasta oferă posibilitatea de a accesa și modifica structura și
 *  comportamentul unei clase în mod dinamic, fără a cunoaște detaliile sale la momentul
 *  compilării.
 * @Source https://www.oracle.com/technical-resources/articles/java/javareflection.html
 */
public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }

    /**
     * Metoda genereaza Query-ul pentru selectarea tuturor campurilor tabelei
     * @param field Campul utilizat in clauza WHERE pentru generarea query-ului
     * @return Query-ul pentru SELECT
     */
    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }

    /**
     * Metoda genereaza Query-ul pentru selectarea stocului unui produs
     * @param field Campul utilizat in clauza WHERE pentru generarea query-ului
     * @return Query-ul pentru selectarea stocului (folosit strict pentru obeictul "Produs")
     */

    private String createSelectSTOCQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" stoc ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }

    /**
     * Metoda genereaza Query-ul pentru operatia DELETE
     * @param field Campul utilizat in clauza WHERE pentru generarea query-ului
     * @return Query-ul pentru stergere
     */
    private String createDeleteQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }

    /**
     * Metoda genereaza Query-ul pentru operatia INSERT
     * @param t obiectul pe care se va realiza Query-ul
     * @return Query-ul pentru operatia INSERT(Add)
     * @throws IllegalAccessException  excepție verificată în Java care este aruncată atunci când se încearcă să se acceseze un membru (metodă, câmp, constructor) al unei clase prin intermediul tehnicii Reflection, dar accesul este ilegal sau interzis.
     */
    private String createInsertQuery(T t) throws IllegalAccessException {
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();

        for (Field field : t.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value = field.get(t);
            if (value != null) {
                columns.append(field.getName()).append(", ");
                values.append("'").append(escapeValue(value.toString())).append("', ");
            }
        }
        columns.deleteCharAt(columns.length() - 2); // șterge ultima virgulă și spațiu
        values.deleteCharAt(values.length() - 2); // șterge ultima virgulă și spațiu

        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(t.getClass().getSimpleName());
        sb.append(" (").append(columns).append(")");
        sb.append(" VALUES (").append(values).append(")");
        return sb.toString();
    }

    private String escapeValue(String value) {
        //TO DO: metoda de escapare a valorilor
        return value;
    }

    /**
     * Metoda genereaza Query-ul pentru operatia UPDATE
     * @param t obiectul pe care se va realiza Query-ul
     * @param id id ul specific obiectului
     * @return Query-ul pentru operatie UPDATE(Edit)
     */
    private String createUpdateQuery(T t, Integer id) {

        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(t.getClass().getSimpleName());
        sb.append(" SET ");
        Field[] fields = t.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            try {
                sb.append(fields[i].getName()).append("='").append(fields[i].get(t)).append("'");
                if (i < fields.length - 1) {
                    sb.append(",");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        //System.out.println("Sunt in createUpdateQuery");
        sb.append(" WHERE id=").append(id);
        return sb.toString();
    }

    /**
     * Metoda returneaza o lista cu toate componentele tabelului de tip obiect T
     * @return lista cu obiecte de tip T din tabelul corespunzator obiectului
     */

    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM " + type.getSimpleName();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            return createObjects(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Metoda cauta obiectul cu id ul "id" in tabela corespunzatoare
     * @param id parametrul dupa care se face cautarea
     * @return obiectul daca este gasit, daca nu este gasit returneaza null
     */

    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Metoda returneaza stoc ul disponibil pentru obiectul cu id-ul transmis (folosit strict pentru tabela "Produs")
     * @param id parametrul dupa care se face cautarea
     * @return stocul sau 0 daca nu a fost gasit
     */
    public int findStocById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectSTOCQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt("stoc");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return 0;
    }

    /**
     * Metoda genereaza o lista de obiecte de tip T
     * @param resultSet rezultatul obtinut
     * @return lista de obiecte
     */
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T)ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Metoda insereaza un obiect de tipul T cu id-ul "id"
     * @param t obiectul asupra caruia se face inserarea
     * @param id id ul inserarii
     * @return obiectul actualizat
     */

    public T insert(T t, Integer id) {
        // TODO:
        Connection connection = null;
        PreparedStatement statement = null;
        String query = null;
        try {
            query = createInsertQuery(t);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.executeUpdate();
        } catch (SQLException e) {
           e.printStackTrace();
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return t;
    }

    /**
     * Metoda face update pe un obiect in functie de rezultatul generat de "createUpdateQuery", insereaza obiectul de tip T, cu id-ul "id"
     * @param t obiectul asupra caruia se face update
     * @param id id ul obiectului asupra caruia se face update
     * @return obiectul actualizat
     */
    public T update(T t, int id) {
        // TODO:
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createUpdateQuery(t, id);

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return t;
    }

    /**
     * Metoda face stergerea unui obiect in functie de rezultatul generat de "createDeleteQuery", sterge obiectul cu id-ul "id"
     * @param id id ul obiectului asupra caruia se face update
     * @return obiectul actualizat
     */
    public void deleteObject(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createDeleteQuery("id");
        System.out.println(query);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

    }

    /**
     * Metoda genereaza un header de tabel, folosita pentru afisarea tabelara a datelor specifice fiecarui obiect
     * @return returneaza header-ul tabelului
     */
    public String[] getHeader() {
        Field[] fields = type.getDeclaredFields();
        String[] header = new String[fields.length];
        int index = 0;
        for (Field field : fields) {
            header[index++] = field.getName();
        }
        return header;
    }
}
