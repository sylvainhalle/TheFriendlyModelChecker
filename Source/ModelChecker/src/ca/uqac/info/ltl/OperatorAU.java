package ca.uqac.info.ltl;

public class OperatorAU extends BinaryOperator
{
    public static final String SYMBOL = "AU";
    
    public OperatorAU()
    {
        super();
        m_symbol = OperatorAU.SYMBOL;
        m_commutes = false;
    }

    public OperatorAU(Operator left, Operator right)
    {
        super(left, right);
        m_symbol = OperatorAU.SYMBOL;
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
