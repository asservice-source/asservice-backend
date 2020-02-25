package com.fwg.asservice.sql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionImpl;
import org.springframework.beans.factory.annotation.Autowired;

import com.fwg.asservice.utility.GlobalFunction;
import com.sun.rowset.CachedRowSetImpl;

public class CallableStatementUtil {
	private static Logger log = Logger.getLogger(CallableStatementUtil.class);

	/** The statement this object is wrapping. */
	private final CallableStatement statement;
	private Connection connection = null;
	int value;
	
	@Autowired
	private SessionFactory sessionFactory;

	/** Maps parameter names to arrays of ints which are the parameter indices. */
	private final Map indexMap;
	

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * Creates a NamedParameterStatement. Wraps a call to c.
	 * {@link Connection#prepareStatement(java.lang.String) prepareStatement}.
	 * 
	 * @param connection
	 *            the database connection 
	 * @param query
	 *            the parameterized query
	 * @throws SQLException
	 *             if the statement could not be created
	 */
	public CallableStatementUtil(Session session, String query) throws SQLException {
		
		SessionImpl sessionImpl = (SessionImpl) session;
		connection = sessionImpl.connection();
		
		indexMap = new HashMap();
		String parsedQuery = parse("{" + query + "}", indexMap);
		statement = connection.prepareCall(parsedQuery);
	}


	// for Insert or Update Multiple Statement
	public CallableStatementUtil(Session session, String query, boolean autoCommit) throws SQLException {
		
		close();
		
		SessionImpl sessionImpl = (SessionImpl) session;
		connection = sessionImpl.connection();
		connection.setAutoCommit(autoCommit);
		
		indexMap = new HashMap();
		String parsedQuery = parse("{" + query + "}", indexMap);
		statement = connection.prepareCall(parsedQuery);
	}
	public CallableStatementUtil(Connection connection, String query) throws SQLException {
		indexMap = new HashMap();
		String parsedQuery = parse("{" + query + "}", indexMap);
		statement = connection.prepareCall(parsedQuery);
	}

	
	/**
	 * Parses a query with named parameters. The parameter-index mappings are
	 * put into the map, and the parsed query is returned. DO NOT CALL FROM
	 * CLIENT CODE. This method is non-private so JUnit code can test it.
	 * 
	 * @param query
	 *            query to parse
	 * @param paramMap
	 *            map to hold parameter-index mappings
	 * @return the parsed query
	 */
	static final String parse(String query, Map paramMap) {
		// I was originally using regular expressions, but they didn't work well
		// for ignoring
		// parameter-like strings inside quotes.

		int length = query.length();
		StringBuffer parsedQuery = new StringBuffer(length);
		boolean inSingleQuote = false;
		boolean inDoubleQuote = false;
		int index = 1;

		for (int i = 0; i < length; i++) {
			char chr = query.charAt(i);
			if (inSingleQuote) {
				if (chr == '\'') {
					inSingleQuote = false;
				}
			} else if (inDoubleQuote) {
				if (chr == '"') {
					inDoubleQuote = false;
				}
			} else {
				if (chr == '\'') {
					inSingleQuote = true;
				} else if (chr == '"') {
					inDoubleQuote = true;
				} else if (chr == ':' && i + 1 < length && Character.isJavaIdentifierStart(query.charAt(i + 1))) {
					int j = i + 2;
					while (j < length && Character.isJavaIdentifierPart(query.charAt(j))) {
						j++;
					}
					String name = query.substring(i + 1, j);
					chr = '?'; // replace the parameter with a question mark
					i += name.length(); // skip past the end if the parameter

					List indexList = (List) paramMap.get(name);
					if (indexList == null) {
						indexList = new LinkedList();
						paramMap.put(name, indexList);
					}
					indexList.add(new Integer(index));

					index++;
				}
			}
			parsedQuery.append(chr);
		}

		// replace the lists of Integer objects with arrays of ints
		for (Iterator itr = paramMap.entrySet().iterator(); itr.hasNext();) {
			Map.Entry entry = (Map.Entry) itr.next();
			List list = (List) entry.getValue();
			int[] indexes = new int[list.size()];
			int i = 0;
			for (Iterator itr2 = list.iterator(); itr2.hasNext();) {
				Integer x = (Integer) itr2.next();
				indexes[i++] = x.intValue();
			}
			entry.setValue(indexes);
		}

		return parsedQuery.toString();
	}

	/**
	 * Returns the indexes for a parameter.
	 * 
	 * @param name
	 *            parameter name
	 * @return parameter indexes
	 * @throws IllegalArgumentException
	 *             if the parameter does not exist
	 */
	private int[] getIndexes(String name) {
		int[] indexes = (int[]) indexMap.get(name);
		if (indexes == null) {
			throw new IllegalArgumentException("Parameter not found: " + name);
		}
		return indexes;
	}

	/**
	 * Sets a parameter.
	 * 
	 * @param name
	 *            parameter name
	 * @param value
	 *            parameter value
	 * @throws SQLException
	 *             if an error occurred
	 * @throws IllegalArgumentException
	 *             if the parameter does not exist
	 * @see PreparedStatement#setObject(int, java.lang.Object)
	 */
	public void setObject(String name, Object value) throws SQLException {
		int[] indexes = getIndexes(name);
		for (int i = 0; i < indexes.length; i++) {
			statement.setObject(indexes[i], value);
		}
	}

	/**
	 * Sets a parameter.
	 * 
	 * @param name
	 *            parameter name
	 * @param value
	 *            parameter value
	 * @throws SQLException
	 *             if an error occurred
	 * @throws IllegalArgumentException
	 *             if the parameter does not exist
	 * @see PreparedStatement#setString(int, java.lang.String)
	 */
	public void setString(String name, String value) throws SQLException {
		int[] indexes = getIndexes(name);
		for (int i = 0; i < indexes.length; i++) {
			statement.setString(indexes[i], GlobalFunction.isEmpty(value)?null:value);
		}
	}

	/**
	 * Sets a parameter.
	 * 
	 * @param name
	 *            parameter name
	 * @param value
	 *            parameter value
	 * @throws SQLException
	 *             if an error occurred
	 * @throws IllegalArgumentException
	 *             if the parameter does not exist
	 * @see PreparedStatement#setInt(int, int)
	 */
	
	
	public void setInt(String name, Integer value) throws SQLException {
		int[] indexes = getIndexes(name);
		
		for (int i = 0; i < indexes.length; i++) {
			if(value == null) {
				statement.setNull(indexes[i], 0);
			}else {
				statement.setInt(indexes[i], value.intValue());
			}
			
		}
	}

	/**
	 * Sets a parameter.
	 * 
	 * @param name
	 *            parameter name
	 * @param value
	 *            parameter value
	 * @throws SQLException
	 *             if an error occurred
	 * @throws IllegalArgumentException
	 *             if the parameter does not exist
	 * @see PreparedStatement#setInt(int, booleans)
	 */
	public void setBoolean(String name, boolean value) throws SQLException {
		int[] indexes = getIndexes(name);
		for (int i = 0; i < indexes.length; i++) {
			statement.setBoolean(indexes[i], value);
		}
	}

	/**
	 * Sets a parameter.
	 * 
	 * @param name
	 *            parameter name
	 * @param value
	 *            parameter value
	 * @throws SQLException
	 *             if an error occurred
	 * @throws IllegalArgumentException
	 *             if the parameter does not exist
	 * @see PreparedStatement#setInt(int, int)
	 */
	public void setLong(String name, long value) throws SQLException {
		int[] indexes = getIndexes(name);
		for (int i = 0; i < indexes.length; i++) {
			statement.setLong(indexes[i], value);
		}
	}

	/**
	 * Sets a parameter.
	 * 
	 * @param name
	 *            parameter name
	 * @param value
	 *            parameter value
	 * @throws SQLException
	 *             if an error occurred
	 * @throws IllegalArgumentExceptio
	 *             if the parameter does not exist
	 * @see PreparedStatement#setTimestamp(int, java.sql.Timestamp)
	 */
	public void setTimestamp(String name, Timestamp value) throws SQLException {
		int[] indexes = getIndexes(name);
		for (int i = 0; i < indexes.length; i++) {
			statement.setTimestamp(indexes[i], value);
		}
	}

	/**
	 * Sets a parameter.
	 * 
	 * @param name
	 *            parameter name
	 * @param value
	 *            parameter value
	 * @throws SQLException
	 *             if an error occurred
	 * @throws IllegalArgumentExceptio
	 *             if the parameter does not exist
	 * @see PreparedStatement#setNull(int, null)
	 */
	public void setNull(String name, int value) throws SQLException {
		int[] indexes = getIndexes(name);
		for (int i = 0; i < indexes.length; i++) {
			try {
				statement.setNull(indexes[i], value);
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
				throw sqlException;
			}
		}

	}
	
	public void setDate(String name, Date value) {
		int[] indexes = getIndexes(name);
		for (int i = 0; i < indexes.length; i++) {
			try {
				statement.setDate(indexes[i], value);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void registerOutParameter(String name, int value) {
		int[] indexes = getIndexes(name);
		for (int i = 0; i < indexes.length; i++) {
			try {
				statement.registerOutParameter(indexes[i], value);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Returns the underlying statement.
	 * 
	 * @return the statement
	 */
	public CallableStatement getStatement() {
		return statement;
	}

	/**
	 * Executes the statement.
	 * 
	 * @return true if the first result is a {@link ResultSet}
	 * @throws SQLException
	 *             if an error occurred
	 * @see PreparedStatement#execute()
	 */
	public boolean execute() throws SQLException {
		return statement.execute();
	}

	/**
	 * Executes the statement, which must be a query.
	 * 
	 * @return the query results
	 * @throws SQLException
	 *             if an error occurred
	 * @see PreparedStatement#executeQuery()
	 */
	public ResultSet executeQuery() throws Exception {
		ResultSet resultSet = null;
		CachedRowSetImpl cachedRowSetImpl = null;
		try {
			resultSet = statement.executeQuery();
			cachedRowSetImpl = new CachedRowSetImpl();
			cachedRowSetImpl.populate(resultSet);

			return cachedRowSetImpl;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally {
			if (resultSet != null) {
				resultSet.close();
				log.debug("ResultSet is closed.");
			}
		}

	}

	/**
	 * Executes the statement, which must be an SQL INSERT, UPDATE or DELETE
	 * statement; or an SQL statement that returns nothing, such as a DDL
	 * statement.
	 * 
	 * @return number of rows affected
	 * @throws SQLException
	 *             if an error occurred
	 * @see PreparedStatement#executeUpdate()
	 */
	public int executeUpdate() throws SQLException {
		int row = 0;
		try {
			statement.executeUpdate();

		} catch (Exception exception) {
			throw exception;
		}
		
		return row;
	}

	/**
	 * Closes the statement.
	 * 
	 * @throws SQLException
	 *             if an error occurred
	 * @see Statement#close()
	 */
	public void close() throws SQLException {
		if (statement != null) {
			statement.close();
		}
		if (connection != null) {
			connection.close();
			log.debug("Connection is closed.");
		}
	}
	
	// for Insert or Update Multiple Statement
	public void closeAndCommit(boolean isCommit) throws SQLException {
		if (statement != null) {
			statement.close();
		}
		if (connection != null) {
			if (isCommit) {
				connection.commit();
			} else {
				connection.rollback();
			}
			connection.close();
			log.debug("Connection is closed.");
		}
	}

	/**
	 * Adds the current set of parameters as a batch entry.
	 * 
	 * @throws SQLException
	 *             if something went wrong
	 */
	public void addBatch() throws SQLException {
		statement.addBatch();
	}

	/**
	 * Executes all of the batched statements.
	 * 
	 * See {@link Statement#executeBatch()} for details.
	 * 
	 * @return update counts for each statement
	 * @throws SQLException
	 *             if something went wrong
	 */
	public int[] executeBatch() throws SQLException {
		return statement.executeBatch();
	}

	public int getInt(String name) {
		int[] indexes = getIndexes(name);
		int value = 0;

		try {

			for (int i = 0; i < indexes.length; i++) {
				try {
					value = statement.getInt(indexes[i]);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			throw exception;
			
		}
//		finally {
//			if (statement != null) {
//				try {
//					statement.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//				log.debug("PreparedStatement is closed.");
//			}
//			if (connection != null) {
//				try {
//					connection.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//				log.debug("Connection is closed.");
//			}
//		}

		return value;
	}
	
	public String getString(String name) {
		int[] indexes = getIndexes(name);
		String value = "";

		try {

			for (int i = 0; i < indexes.length; i++) {
				try {
					value = statement.getString(indexes[i]);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			throw exception;
			
		}
		return value;
	}

	public Connection getConnection() {
		return connection;
	}

}
