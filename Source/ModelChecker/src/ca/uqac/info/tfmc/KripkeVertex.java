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

import org.jboss.Vertex;

public class KripkeVertex extends Vertex<KripkeNode>
{
  public KripkeVertex(KripkeNode data)
  {
    super();
    setData(data);
  }
  
  public KripkeVertex(String name, KripkeNode data)
  {
    super(name, data);
  }
  
  @Override
  public int hashCode()
  {
    return getData().hashCode();
  }
  
  @Override
  public boolean equals(Object o)
  {
    if (o == null)
      return false;
    if (o instanceof KripkeVertex)
      return equals((KripkeVertex) o);
    return false;
  }  
  
  public boolean equals(KripkeVertex v)
  {
    if (v == null)
      return false;
    KripkeNode kn = v.getData();
    if (kn == null)
      return false;
    return kn.equals(this.getData());
  }
  
  @Override
  public String toString()
  {
    KripkeNode kn = getData();
    if (kn == null)
      return "";
    return kn.toString();
  }
}
