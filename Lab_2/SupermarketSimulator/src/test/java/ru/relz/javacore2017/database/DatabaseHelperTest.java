package ru.relz.javacore2017.database;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseHelperTest {
	private static final String classNameFieldName = "className";
	private static final String databasePathFieldName = "databasePath";
	private static final String[] fieldNames = {
			classNameFieldName,
			databasePathFieldName
	};
	private static Object classNameFieldValidValue;
	private static Field classNameField;
	private static Object databasePathFieldValidValue;
	private static Field databasePathField;

	@Test
	void fieldsExists() {
		for (String fieldName : fieldNames) {
			try {
				DatabaseHelper.class.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				fail("DatabaseHelper does not have field \"" + fieldName + "\"");
			}
		}
	}

	@Test
	void throwsClassNotFoundExceptionIfDatabaseClassNameDoesNotFound() throws IllegalAccessException {
		classNameField = setFieldAccessible(classNameFieldName);
		classNameFieldValidValue = classNameField.get(DatabaseHelper.class);
		setFieldValue(classNameField, "");

		assertThrows(ClassNotFoundException.class, DatabaseHelper::createConnection);
	}

	@Test
	void throwsSQLExceptionIfDatabasePathDoesNotFound() throws IllegalAccessException {
		databasePathField = setFieldAccessible(databasePathFieldName);
		databasePathFieldValidValue = databasePathField.get(DatabaseHelper.class);
		setFieldValue(databasePathField, "");

		assertThrows(ClassNotFoundException.class, DatabaseHelper::createConnection);
	}

	@Test
	void loadsDatabaseClass() {
		try {
			DatabaseHelper.createConnection();
			assertNotEquals(DatabaseHelper.connection, null);
			DatabaseHelper.closeConnection();
		} catch (ClassNotFoundException e) {
			fail("Database class loading failed. Wrong class name or library not installed");
		} catch (SQLException ignored) { }
	}

	@Test
	void connectsToDatabase() {
		try {
			DatabaseHelper.createConnection();
			assertNotEquals(DatabaseHelper.connection, null);
			DatabaseHelper.closeConnection();
		} catch (ClassNotFoundException ignored) {
		} catch (SQLException e) {
			fail("Database path not found");
		}
	}

	private static Field setFieldAccessible(String fieldName) {
		Field field = null;
		try {
			field = DatabaseHelper.class.getDeclaredField(fieldName);
			field.setAccessible(true);
		} catch (NoSuchFieldException e) {
			fail("DatabaseHelper does not have field \"" + fieldName + "\"");
		}
		return field;
	}

	private static void setFieldValue(Field field, Object value) {
		try {
			field.set(null, value);
		} catch (IllegalAccessException e) {
			fail("Access denied to set private field value");
		}
	}

	@AfterAll
	static void afterAll() {
		setFieldValue(classNameField, classNameFieldValidValue);
		setFieldValue(databasePathField, databasePathFieldValidValue);
	}
}
