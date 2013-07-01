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

import org.jboss.*;
import ca.uqac.info.ltl.*;

import java.util.*;

public class CtlMarker
{
  protected KripkeNodeMarking m_marking;
  protected KripkeStructure m_structure;
  
  public CtlMarker(KripkeStructure k)
  {
    super();
    m_structure = k;
    m_marking = new KripkeNodeMarking();
  }
  
  public KripkeNodeMarking getMarking()
  {
    return m_marking;
  }
  
  public void applyMarking(Operator o)
  {
    MarkerVisitor mv = new MarkerVisitor();
    o.accept(mv);
  }
  
  protected void applyMarkingSingle(Operator o)
  {
    m_structure.clearMark();
    List<Vertex<KripkeNode>> nodes_to_visit = m_structure.getVerticies();
    Iterator<Vertex<KripkeNode>> it = nodes_to_visit.iterator();
    while (it.hasNext())
    {
      Vertex<KripkeNode> v = it.next();
      mark(v, o);
    }
  }
  
  protected void mark(Vertex<KripkeNode> n, Operator o)
  {
    if (o instanceof OperatorEquals)
      mark(n, (OperatorEquals) o);
    if (o instanceof OperatorOr)
      mark(n, (OperatorOr) o);
    if (o instanceof OperatorAnd)
      mark(n, (OperatorAnd) o);
    if (o instanceof OperatorImplies)
      mark(n, (OperatorImplies) o);
    if (o instanceof OperatorXor)
      mark(n, (OperatorXor) o);
    if (o instanceof OperatorNot)
      mark(n, (OperatorNot) o);
    if (o instanceof OperatorEX)
      mark(n, (OperatorEX) o);
    if (o instanceof OperatorEU)
      mark(n, (OperatorEU) o);
    if (o instanceof OperatorEG)
      mark(n, (OperatorEG) o);
    if (o instanceof OperatorG)
      mark(n, (OperatorG) o);
    if (o instanceof OperatorX)
      mark(n, (OperatorX) o);
    if (o instanceof OperatorF)
      mark(n, (OperatorF) o);
    if (o instanceof OperatorU)
      mark(n, (OperatorU) o);
  }
  
  protected void mark(Vertex<KripkeNode> n, OperatorEquals o)
  {
    if (n.visited())
      return;
    n.visit();
    KripkeNode kn = n.getData();
    Operator left = o.getLeft();
    Operator right = o.getRight();
    if (left instanceof Constant && right instanceof Constant)
    {
      if (left.equals(right))
        m_marking.add(kn, o);
    }
    else if (left instanceof Atom && right instanceof Constant)
    {
      Atom a_left = (Atom) left;
      Constant c_right = (Constant) right;
      Constant value = kn.getValue(a_left);
      if (c_right.equals(value))
      {
        m_marking.add(kn, o);
      }
    }
    else if (left instanceof XPathAtom && right instanceof Constant)
    {
      // Currently, XPathAtoms are handled like single atoms
      XPathAtom a_left = (XPathAtom) left;
      Atom a = new Atom(a_left.getFlatName());
      Constant c_right = (Constant) right;
      Constant value = kn.getValue(a);
      if (c_right.equals(value))
      {
        m_marking.add(kn, o);
      }
    }
  }
  
  protected void mark(Vertex<KripkeNode> n, OperatorOr o)
  {
    if (n.visited())
      return;
    n.visit();
    KripkeNode kn = n.getData();
    Set<Operator> kn_marking = m_marking.get(kn);
    Operator left = o.getLeft();
    Operator right = o.getRight();
    if (kn_marking.contains(left) || kn_marking.contains(right))
    {
      m_marking.add(kn, o);
    }
  }
  
  protected void mark(Vertex<KripkeNode> n, OperatorAnd o)
  {
    if (n.visited())
      return;
    n.visit();
    KripkeNode kn = n.getData();
    Set<Operator> kn_marking = m_marking.get(kn);
    Operator left = o.getLeft();
    Operator right = o.getRight();
    if (kn_marking.contains(left) && kn_marking.contains(right))
    {
      m_marking.add(kn, o);
    }
  }
  
  protected void mark(Vertex<KripkeNode> n, OperatorImplies o)
  {
    if (n.visited())
      return;
    n.visit();
    KripkeNode kn = n.getData();
    Set<Operator> kn_marking = m_marking.get(kn);
    Operator left = o.getLeft();
    Operator right = o.getRight();
    if (!kn_marking.contains(left) || kn_marking.contains(right))
    {
      m_marking.add(kn, o);
    }
  }
  
  protected void mark(Vertex<KripkeNode> n, OperatorXor o)
  {
    if (n.visited())
      return;
    n.visit();
    KripkeNode kn = n.getData();
    Set<Operator> kn_marking = m_marking.get(kn);
    Operator left = o.getLeft();
    Operator right = o.getRight();
    if (kn_marking.contains(left) != kn_marking.contains(right))
    {
      m_marking.add(kn, o);
    }
  }
  
  protected void mark(Vertex<KripkeNode> n, OperatorEG o)
  {
    if (n.visited())
      return;
    n.visit();
    KripkeNode kn = n.getData();
    Set<Operator> kn_marking = m_marking.get(kn);
    Operator inside = o.getOperand();
    if (!kn_marking.contains(inside))
      return;
    Set<Vertex<KripkeNode>> incoming = n.getIncomingVertices();
    for (Vertex<KripkeNode> v : incoming)
    {
      mark(v, o);
    }
  }
  
  protected void mark(Vertex<KripkeNode> n, OperatorNot o)
  {
    if (n.visited())
      return;
    n.visit();
    KripkeNode kn = n.getData();
    Set<Operator> kn_marking = m_marking.get(kn);
    Operator inside = o.getOperand();
    if (!kn_marking.contains(inside))
      m_marking.add(kn, o);
  }
  
  protected void mark(Vertex<KripkeNode> n, OperatorEX o)
  {
    if (n.visited())
      return;
    n.visit();
    KripkeNode kn = n.getData();
    Set<Operator> kn_marking = m_marking.get(kn);
    Operator inside = o.getOperand();
    if (!kn_marking.contains(inside))
      return;
    Set<Vertex<KripkeNode>> incoming = n.getIncomingVertices();
    for (Vertex<KripkeNode> v : incoming)
    {
      KripkeNode v_kn = v.getData();
      m_marking.add(v_kn, o);
    }
  }
  
  protected void mark(Vertex<KripkeNode> n, OperatorG o)
  {
    // No such operator in a CTL marking
    assert false;
  }
  
  protected void mark(Vertex<KripkeNode> n, OperatorX o)
  {
    // No such operator in a CTL marking
    assert false;
  }
  
  protected void mark(Vertex<KripkeNode> n, OperatorF o)
  {
    // No such operator in a CTL marking
    assert false;
  }
  
  protected void mark(Vertex<KripkeNode> n, OperatorU o)
  {
    // No such operator in a CTL marking
    assert false;
  }
  
  protected void mark(Vertex<KripkeNode> n, ForAll o)
  {
    // No such operator in a CTL marking
    assert false;
  }
  
  protected void mark(Vertex<KripkeNode> n, Exists o)
  {
    // No such operator in a CTL marking
    assert false;
  }
  
  protected class MarkerVisitor extends EmptyVisitor
  {

    @Override
    public void visit(OperatorAnd o)
    {
      applyMarkingSingle(o);
    }

    @Override
    public void visit(OperatorOr o)
    {
      applyMarkingSingle(o);
    }

    @Override
    public void visit(OperatorXor o)
    {
      applyMarkingSingle(o);
      
    }

    @Override
    public void visit(OperatorNot o)
    {
      applyMarkingSingle(o);
    }

    @Override
    public void visit(OperatorEX o)
    {
      applyMarkingSingle(o);
    }
    
    @Override
    public void visit(OperatorAX o)
    {
      applyMarkingSingle(o);
    }

    @Override
    public void visit(OperatorEG o)
    {
      applyMarkingSingle(o);
    }
    
    @Override
    public void visit(OperatorAG o)
    {
      applyMarkingSingle(o);
    }

    @Override
    public void visit(OperatorEquals o)
    {
      applyMarkingSingle(o);
    }

    @Override
    public void visit(OperatorGreaterThan o)
    {
      applyMarkingSingle(o);
    }

    @Override
    public void visit(OperatorImplies o)
    {
      applyMarkingSingle(o);
    }

    @Override
    public void visit(OperatorEquiv o)
    {
      applyMarkingSingle(o);
    }

    @Override
    public void visit(OperatorEU o)
    {
      applyMarkingSingle(o);
    }
    
    @Override
    public void visit(OperatorAU o)
    {
      applyMarkingSingle(o);
    }

    @Override
    public void visit(Exists o)
    {
      applyMarkingSingle(o);
    }

    @Override
    public void visit(ForAll o)
    {
      applyMarkingSingle(o);
    }

    @Override
    public void visit(OperatorTrue o)
    {
      applyMarkingSingle(o);
    }

    @Override
    public void visit(OperatorFalse o)
    {
      applyMarkingSingle(o);
    }

    @Override
    public void visit(XPathAtom o)
    {
      applyMarkingSingle(o);
    }
  }
}
