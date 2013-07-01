/*-------------------------------------------------------------------------
    The Friendly Model Checker
    Copyright (C) 2013  Sylvain Hall�

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
import ca.uqac.info.ltl.Operator;

/**
 * Representation of a node marking in the CTL model checking algorithm.
 * A marking is simply a set of CTL formul&aelig; associated to a node in
 * the Kripke structure.
 * @author Sylvain
 */
public class KripkeNodeMarking extends HashMap<KripkeNode, Set<Operator>>
{
  /**
   * Dummy UID
   */
  private static final long serialVersionUID = 1L;

  public void add(final KripkeNode kn, final Operator o)
  {
    Set<Operator> out = null;
    if (containsKey(kn))
    {
      out = get(kn);
    }
    else
    {
      out = new HashSet<Operator>();
    }
    assert out != null;
    out.add(o);
    put(kn, out);
  }
}
