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

import org.jboss.Graph;
import org.jboss.Vertex;
import java.util.*;
import ca.uqac.info.ltl.*;

/**
 * Representation of a Kripke structure. A Kripke structure is a particular
 * type of oriented graph whose vertices are Kripke nodes.
 * @author Sylvain
 *
 */
public class KripkeStructure extends Graph<KripkeNode>
{
  protected Map<Atom,Set<Constant>> m_domains;
  
  public KripkeStructure()
  {
    super();
    m_domains = new HashMap<Atom,Set<Constant>>();
  }
  
  public void addToDomain(final Atom a, final Constant c)
  {
    Set<Constant> domain = null;
    if (m_domains.containsKey(a))
    {
      domain = m_domains.get(a);
    }
    else
    {
      domain = new HashSet<Constant>();
    }
    assert domain != null;
    domain.add(c);
    m_domains.put(a, domain);
  }
  
  protected KripkeVertex getVertex(KripkeVertex v)
  {
    List<Vertex<KripkeNode>> lv = getVerticies();
    for (Vertex<KripkeNode> ve : lv)
    {
      if (ve.equals(v))
        return (KripkeVertex) ve;
    }
    return null;
  }
}
