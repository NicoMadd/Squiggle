package com.squiggle.output;

/**
 * Any object that can output itself as part of the final query should implement
 * this interface.
 *
 * @author <a href="mailto:joe@truemesh.com">Joe Walnes</a>
 */
public interface Outputable {
    public void write(Output out);
}
