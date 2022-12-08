// generated with ast extension for cup
// version 0.8
// 22/7/2022 19:5:44


package rs.ac.bg.etf.pp1.ast;

public class FactorZamena_const extends FactorZamena {

    private FactorConst FactorConst;

    public FactorZamena_const (FactorConst FactorConst) {
        this.FactorConst=FactorConst;
        if(FactorConst!=null) FactorConst.setParent(this);
    }

    public FactorConst getFactorConst() {
        return FactorConst;
    }

    public void setFactorConst(FactorConst FactorConst) {
        this.FactorConst=FactorConst;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FactorConst!=null) FactorConst.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FactorConst!=null) FactorConst.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FactorConst!=null) FactorConst.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FactorZamena_const(\n");

        if(FactorConst!=null)
            buffer.append(FactorConst.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FactorZamena_const]");
        return buffer.toString();
    }
}
