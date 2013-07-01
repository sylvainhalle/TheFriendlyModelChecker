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

import java.util.List;
import java.util.Set;

import org.jboss.*;

import ca.uqac.info.ltl.*;

/**
 * Formats a Kripke structure for output to Graphviz.
 * @author Sylvain
 */
public class GraphvizFormatter
{
  public String toGraphviz(KripkeStructure k)
  {
    StringBuilder out = new StringBuilder();
    GraphvizVisitor visitor = new GraphvizVisitor();
    out.append("digraph G {\n");
    out.append("  node [shape=oval];\n");
    Vertex<KripkeNode> v = k.getRootVertex();
    k.depthFirstSearch(v, visitor);
    out.append(visitor.getStringBuilder());
    out.append("}");
    return out.toString();
  }
  
  protected class GraphvizVisitor implements Visitor<KripkeNode>
  {
    
    protected StringBuilder m_sb;

    public GraphvizVisitor()
    {
      super();
      m_sb = new StringBuilder();
    }
    
    public StringBuilder getStringBuilder()
    {
      return m_sb;
    }
    
    public void visit(Graph<KripkeNode> g, Vertex<KripkeNode> v)
    {
      KripkeNode kn = v.getData();
      String node_name = v.getName();
      String formatted_contents = nodeToGraphviz(kn);
      m_sb.append("  ").append(node_name).append(" [label=\"").append(formatted_contents).append("\"];\n");
      List<Edge<KripkeNode>> ln = v.getOutgoingEdges();
      for (Edge<KripkeNode> e : ln)
      {
        m_sb.append("  ").append(e.getFrom().getName()).append(" -> ").append(e.getTo().getName()).append(";\n");
      }
      if (g.isRootVertex(v))
      {
        m_sb.append("  ").append("init_").append(v.getName()).append(" -> ").append(v.getName()).append(";\n");
        m_sb.append("  ").append("init_").append(v.getName()).append(" [label=\"\",shape=none];\n");
      }
    }
    
  }
  
  /**
   * Formats the contents of a Kripke node to be displayed inside
   * a node in a Graphviz graph
   * @param n The node whose contents to format
   * @return A (Graphviz-compatible) string representing the node's
   *   contents
   */
  public static String nodeToGraphviz(KripkeNode n)
  {
    StringBuilder out = new StringBuilder();
    Set<Atom> keys = n.getVariables();
    boolean first = true;
    for (Atom a : keys)
    {
      if (first)
        first = false;
      else
        out.append(",");
      Constant c = n.getValue(a);
      out.append(a).append("=").append(c);
    }
    return out.toString();
  }
}
