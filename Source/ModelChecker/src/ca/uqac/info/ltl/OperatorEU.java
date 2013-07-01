package ca.uqac.info.ltl;

public class OperatorEU extends BinaryOperator
{
    public static final String SYMBOL = "U";
    
    public OperatorEU()
    {
        super();
        m_symbol = OperatorEU.SYMBOL;
        m_commutes = false;
    }

    public OperatorEU(Operator left, Operator right)
    {
        super(left, right);
        m_symbol = OperatorU.SYMBOL;
        m_commutes = false;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == null)
            return false;
        if (o.getClass() != this.getClass())
            return false;
        return super.equals((BinaryOperator) o);
    }

    @Override
    public void accept(OperatorVisitor v)
    {
      super.accept(v);
      v.visit(this);
    }
}
