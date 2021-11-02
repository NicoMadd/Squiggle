package com.systech.Squiggle;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.systech.Squiggle.output.Output;

/**
 * @author <a href="joe@truemesh.com">Joe Walnes</a>
 * @author <a href="derek@derekmahar.ca">Derek Mahar</a>
 */
public class MatchCriteria extends Criteria {
  public static final String EQUALS = "=";

  public static final String GREATER = ">";

  public static final String GREATEREQUAL = ">=";

  public static final String LESS = "<";

  public static final String LESSEQUAL = "<=";

  public static final String LIKE = "LIKE";

  public static final String NOTEQUAL = "<>";

  private Column column;

  private String matchType;

  private String value;

  public MatchCriteria(Column column, String matchType, boolean value) {
    this.column = column;
    this.value = String.valueOf(value);
    this.matchType = matchType;
  }

  /**
   * Initializes a MatchCriteria with a given column, comparison operator, and
   * date operand that the criteria will use to make a comparison between the
   * given column and the date.
   * 
   * @param column
   *            the column to use in the date comparison.
   * 
   * @param operator
   *            the comparison operator to use in the date comparison.
   * 
   * @param operand
   *            the date literal to use in the comparison.
   */
  public MatchCriteria(Column column, String operator, Date operand) {
    this(column, operator, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(operand));
  }

  public MatchCriteria(Column column, String matchType, float value) {
    this.column = column;
    this.value = String.valueOf(value);
    this.matchType = matchType;
  }

  public MatchCriteria(Column column, String matchType, int value) {
    this.column = column;
    this.value = String.valueOf(value);
    this.matchType = matchType;
  }

  public MatchCriteria(Column column, String matchType, String value) {
    this.column = column;
    this.value = quote(value);
    this.matchType = matchType;
  }

  public MatchCriteria(Table table, String columnname, String matchType, boolean value) {
    this(table.getColumn(columnname), matchType, value);
  }

  /**
   * Initializes a MatchCriteria with a table, column name is this table,
   * comparison operator, and date operand that the criteria will use to make a
   * comparison between the given table column and the date.
   * 
   * @param table
   *            the table that contains a column having the given name to use in
   *            the date comparison.
   * 
   * @param columnName
   *            the name of the column to use in the date comparison.
   * 
   * @param operator
   *            the comparison operator to use in the date comparison.
   * 
   * @param operand
   *            the date literal to use in the comparison.
   */
  public MatchCriteria(Table table, String columnName, String operator,
    Date operand) {
    this(table.getColumn(columnName), operator, operand);
  }

  public MatchCriteria(Table table, String columnname, String matchType,
    float value) {
    this(table.getColumn(columnname), matchType, value);
  }

  public MatchCriteria(Table table, String columnname, String matchType,
    int value) {
    this(table.getColumn(columnname), matchType, value);
  }

  public MatchCriteria(Table table, String columnname, String matchType,
    String value) {
    this(table.getColumn(columnname), matchType, value);
  }

  public Column getColumn() {
    return column;
  }

  public void write(Output out) {
    out.print(column).print(' ').print(matchType).print(' ').print(value);
  }
}
