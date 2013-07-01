/*-------------------------------------------------------------------------
    The Friendly Model Checker
    Copyright (C) 2013  Sylvain Hallé

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 -------------------------------------------------------------------------*/
package ca.uqac.info.tfmc;

import java.util.*;
import ca.uqac.info.ltl.*;

/**
 * Representation of a node in a Kripke structure. A node is a valuation
 * for all the variables defined in the Kripke structure, i.e. a mapping
 * from variable names ({@link ca.uqac.info.ltl.Atom}) to values
 * ({@link ca.uqac.info.ltl.Constant}).
 * @author Sylvain
 */
public class KripkeNode
{
  /**
   * Internal map from Atoms to Constants
   */
  protected Map<Atom,Constant> m_contents;
  
  /**
   * Constructs an empty Kripke node.
   */
  public KripkeNode()
  {
    super();
    m_contents = new HashMap<Atom,Constant>();
  }
  
  /**
   * Defines the value associated to a particular variable.
   * @param a The variable
   * @param c The value
   */
  public void put(final Atom a, final Constant c)
  {
    m_contents.put(a, c);
  }
  
  public Set<Atom> getVariables()
  {
    return m_contents.keySet();
  }
  
  public Constant getValue(Atom a)
  {
    return m_contents.get(a);
  }
  
  @Override
  public int hashCode()
  {
    return m_contents.hashCode();
  }
  
  @Override
  public boolean equals(final Object o)
  {
    if (o == null)
      return false;
    if (o instanceof KripkeNode)
      return (equals((KripkeNode) o));
    return false;
  }
  
  public boolean equals(final KripkeNode n)
  {
    if (n == null || n.m_contents == null)
      return false;
    return m_contents.equals(n.m_contents);
  }
  
  @Override
  public String toString()
  {
    return m_contents.toString();
  }
}
