package com.squiggle.base;

import com.squiggle.output.Output;

/**
 * Class ParameterMatchCriteria is a Criteria class extension that generates the
 * SQL syntax for a parameter condition in an SQL Where clause.
 * 
 * @author <a href="mailto:derek@derekmahar.ca">Derek Mahar</a>
 */
public class ParameterMatchCriteria extends Criteria {
  // Column to include in the condition.
  private Column column;

  // Comparison operator, one of EQUALS, GREATER, GREATEREQUAL, LESS, LESSEQUAL,
  // or LIKE.
  private String comparisonOperator;

  /**
   * Initializes a ParameterMatchCriter with the table column and comparison
   * operator to use in the condition.
   * 
   * @param column             the Column object that represents the table column
   *                           to use in the SQL query condition.
   * 
   * @param comparisonOperator the comparison operator to use in the SQL query
   *                           condition. May be one of EQUALS, GREATER,
   *                           GREATEREQUAL, LESS, LESSEQUAL, or LIKE.
   */
  public ParameterMatchCriteria(Column column, String comparisonOperator) {
    this.column = column;
    this.comparisonOperator = comparisonOperator;
  }

  /**
   * Returns the Column object that represents the table column that the
   * ParameterMatchCriteria uses in its SQL query condition.
   * 
   * @return the Column object that represents the table column to use in the SQL
   *         query condition.
   */
  public Column getColumn() {
    return column;
  }

  /**
   * Returns the comparison operator that the ParameterMatchCriteria uses in its
   * SQL query condition.
   * 
   * @return the comparison operator that the ParameterMatchCriteria uses in its
   *         query condition.
   */
  public String getComparisonOperator() {
    return comparisonOperator;
  }

  /**
   * Writes a parameterized SQL query predicate to the given output stream.
   * Predicate includes the table column name and comparison operator given at
   * criteria initialization.
   * 
   * @see com.squiggle.base.truemesh.squiggle.Criteria#write(com.squiggle.output.truemesh.squiggle.output.Output)
   */
  public void write(Output out) {
    out.print(column).print(' ').print(comparisonOperator).print(" ?");
  }
}
