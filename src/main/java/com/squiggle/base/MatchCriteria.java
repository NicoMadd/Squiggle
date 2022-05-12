package com.squiggle.base;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.squiggle.output.Output;
import com.squiggle.types.values.BigDecimalTypeValue;
import com.squiggle.types.values.BigIntegerTypeValue;
import com.squiggle.types.values.BooleanTypeValue;
import com.squiggle.types.values.FloatTypeValue;
import com.squiggle.types.values.IntegerTypeValue;
import com.squiggle.types.values.LongTypeValue;
import com.squiggle.types.values.StringTypeValue;
import com.squiggle.types.values.TypeValue;

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

  private TypeValue value;

  private static String dateFormat = "yyyy-MM-dd HH:mm:ss.S";

  public MatchCriteria(Column column, String matchType, boolean value) {
    this.column = column;
    this.value = new BooleanTypeValue(value);
    this.matchType = matchType;
  }

  /**
   * Initializes a MatchCriteria with a given column, comparison operator, and
   * date operand that the criteria will use to make a comparison between the
   * given column and the date.
   * 
   * @param column   the column to use in the date comparison.
   * 
   * @param operator the comparison operator to use in the date comparison.
   * 
   * @param operand  the date literal to use in the comparison.
   */
  public MatchCriteria(Column column, String operator, Date operand) {
    this(column, operator, getDateFormat().format(operand));
  }

  public MatchCriteria(Column column, String matchType, Float value) {
    this.column = column;
    this.value = new FloatTypeValue(value);
    this.matchType = matchType;
  }

  public MatchCriteria(Column column, String matchType, Integer value) {
    this.column = column;
    this.value = new IntegerTypeValue(value);
    this.matchType = matchType;
  }

  public MatchCriteria(Column column, String matchType, String value, Boolean quote) {
    this.column = column;
    this.value = quote ? new StringTypeValue(value) : new StringTypeValue(value, false);
    this.matchType = matchType;
  }

  public MatchCriteria(Column column, String matchType, BigDecimal value) {
    this.column = column;
    this.value = new BigDecimalTypeValue(value);
    this.matchType = matchType;
  }

  public MatchCriteria(Column column, String matchType, BigInteger value) {
    this.column = column;
    this.value = new BigIntegerTypeValue(value);
    this.matchType = matchType;
  }

  public MatchCriteria(Column column, String matchType, String value) {
    this.column = column;
    this.value = new StringTypeValue(value);
    this.matchType = matchType;
  }

  public MatchCriteria(Column column, String matchType, Long value) {
    this.column = column;
    this.value = new LongTypeValue(value);
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
   * @param table      the table that contains a column having the given name to
   *                   use in the date comparison.
   * 
   * @param columnName the name of the column to use in the date comparison.
   * 
   * @param operator   the comparison operator to use in the date comparison.
   * 
   * @param operand    the date literal to use in the comparison.
   */
  public MatchCriteria(Table table, String columnName, String operator, Date operand) {
    this(table.getColumn(columnName), operator, operand);
  }

  public MatchCriteria(Table table, String columnname, String matchType, float value) {
    this(table.getColumn(columnname), matchType, value);
  }

  public MatchCriteria(Table table, String columnname, String matchType, int value) {
    this(table.getColumn(columnname), matchType, value);
  }

  public MatchCriteria(Table table, String columnname, String matchType, String value) {
    this(table.getColumn(columnname), matchType, value);
  }

  public Column getColumn() {
    return column;
  }

  public void write(Output out) {
    out.print(column);
    out.space();
    out.print(matchType);
    out.space();
    value.write(out);
  }

  public static SimpleDateFormat getDateFormat() {
    return new SimpleDateFormat(MatchCriteria.dateFormat);
  }
}
