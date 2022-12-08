// generated with ast extension for cup
// version 0.8
// 22/7/2022 19:5:44


package rs.ac.bg.etf.pp1.ast;

public class VariableDeclOptions_single extends VariableDeclOptions {

    private VariableSingle VariableSingle;

    public VariableDeclOptions_single (VariableSingle VariableSingle) {
        this.VariableSingle=VariableSingle;
        if(VariableSingle!=null) VariableSingle.setParent(this);
    }

    public VariableSingle getVariableSingle() {
        return VariableSingle;
    }

    public void setVariableSingle(VariableSingle VariableSingle) {
        this.VariableSingle=VariableSingle;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VariableSingle!=null) VariableSingle.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VariableSingle!=null) VariableSingle.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VariableSingle!=null) VariableSingle.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VariableDeclOptions_single(\n");

        if(VariableSingle!=null)
            buffer.append(VariableSingle.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VariableDeclOptions_single]");
        return buffer.toString();
    }
}
