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

import java.io.IOException;
import java.util.*;

import ca.uqac.info.ltl.Operator;
import ca.uqac.info.util.FileReadWrite;

public class Test
{
  public static void main(String[] args)
  {
    // Load Kripke structure from file
    String filename = "examples/test-1.tfmc", file_contents = "";
    try
    {
      file_contents = FileReadWrite.readFile(filename);
    }
    catch (IOException ioe)
    {
      System.err.println("Error reading file " + filename);
      System.exit(1);
    }
    KripkeStructure ks = KripkeStringParser.parseFromString(file_contents);
    
    // Perform a couple of sanity checks on the structure
    if (ks.getRootVertex() == null)
    {
      System.err.println("ERROR: The Kripke structure has no initial state");
      System.exit(1);      
    }
    
    // Apply CTL marking and show
    Operator o = null;
    try
    {
      o = Operator.parseFromString("EX (/p = 0)");
    }
    catch (Operator.ParseException e)
    {
      System.err.println("ERROR: cannot parse CTL formula");
    }
    CtlMarker cm = new CtlMarker(ks);
    cm.applyMarking(o);
    KripkeNodeMarking marking = cm.getMarking();
    System.out.println(marking);
    
    // Format resulting graph to Graphviz
    GraphvizFormatter gf = new GraphvizFormatter();
    String out = gf.toGraphviz(ks);
    System.out.print(out);
  }
}
