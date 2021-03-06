package com.squiggle.base;

import com.squiggle.output.Output;

/**
 * Class NoCriteria is a Criteria that represents an absent operand in an SQL
 * predicate expression so that one may represent a unary operator (for example,
 * {@link NOT}) using a binary operator derived from a {@link BaseLogicGroup}).
 * 
 * @author <a href="mailto:derek@derekmahar.ca">Derek Mahar</a>
 */
public class NoCriteria extends Criteria {
  /**
   * Writes an empty criteria (single space) to the given output stream.
   * 
   * @see com.squiggle.base.truemesh.squiggle.Criteria#write(com.squiggle.output.truemesh.squiggle.output.Output)
   */
  public void write(Output out) {
    out.print(' ');
  }
}
