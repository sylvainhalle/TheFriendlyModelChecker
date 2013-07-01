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

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import ca.uqac.info.ltl.*;

public class KripkeStringParser
{
  public static final String CRLF = System.getProperty("line.separator");
  
  public static KripkeStructure parseFromString(final String s)
  {
    int node_count = 0;
    KripkeStructure ks = new KripkeStructure();
    String[] lines = s.split(CRLF);
    for (String line : lines)
    {
      line = line.trim();
      if (line.isEmpty() || line.startsWith("#"))
        continue;
      if (line.startsWith("VAR"))
      {
        // Parse a variable declaration
        // (currently unnecessary)
      }
      else if (line.startsWith("TRANS"))
      {
        // Parse a transition
        Pattern p = Pattern.compile("\\((.*?)\\) -> \\((.*?)\\)", Pattern.DOTALL);
        Matcher m = p.matcher(line);
        if (!m.find())
          continue; // Silently ignore syntax errors
        KripkeNode kn_from = parseKripkeNode(m.group(1));
        KripkeNode kn_to = parseKripkeNode(m.group(2));
        KripkeVertex kv_from = new KripkeVertex(Integer.toString(node_count), kn_from);
        if (ks.addVertex(kv_from))
          node_count++;
        kv_from = ks.getVertex(kv_from);
        KripkeVertex kv_to = new KripkeVertex(Integer.toString(node_count++), kn_to);
        if (ks.addVertex(kv_to))
          node_count++;
        kv_to = ks.getVertex(kv_to);
        if (!ks.addEdge(kv_from, kv_to, 1))
          System.err.println("Sanity check: could not add edge");
      }
      else if (line.startsWith("INIT"))
      {
        // Parse the definition of an initial state
        Pattern p = Pattern.compile("\\((.*?)\\)", Pattern.DOTALL);
        Matcher m = p.matcher(line);
        if (!m.find())
          continue; // Silently ignore syntax errors
        KripkeNode kn_from = parseKripkeNode(m.group(1));
        KripkeVertex kv_from = new KripkeVertex(Integer.toString(node_count++), kn_from);
        ks.addVertex(kv_from);
        kv_from = ks.getVertex(kv_from);
        ks.setRootVertex(kv_from);
      }
    }
    return ks;
  }
  
  protected static KripkeNode parseKripkeNode(final String s)
  {
    KripkeNode kn = new KripkeNode();
    Pattern p = Pattern.compile("([\\d\\w]*?)=(\\d\\w*?)");
    Matcher m = p.matcher(s);
    while (m.find())
    {
      Atom a = new Atom(m.group(1));
      Constant c = new Constant(m.group(2));
      kn.put(a, c);
    }
    return kn;
  }
}
