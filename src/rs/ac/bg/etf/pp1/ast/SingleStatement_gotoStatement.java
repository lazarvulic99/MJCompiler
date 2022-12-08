// generated with ast extension for cup
// version 0.8
// 22/7/2022 19:5:44


package rs.ac.bg.etf.pp1.ast;

public class SingleStatement_gotoStatement extends SingleStatement {

    private LabelIzaGoTo LabelIzaGoTo;

    public SingleStatement_gotoStatement (LabelIzaGoTo LabelIzaGoTo) {
        this.LabelIzaGoTo=LabelIzaGoTo;
        if(LabelIzaGoTo!=null) LabelIzaGoTo.setParent(this);
    }

    public LabelIzaGoTo getLabelIzaGoTo() {
        return LabelIzaGoTo;
    }

    public void setLabelIzaGoTo(LabelIzaGoTo LabelIzaGoTo) {
        this.LabelIzaGoTo=LabelIzaGoTo;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(LabelIzaGoTo!=null) LabelIzaGoTo.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(LabelIzaGoTo!=null) LabelIzaGoTo.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(LabelIzaGoTo!=null) LabelIzaGoTo.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleStatement_gotoStatement(\n");

        if(LabelIzaGoTo!=null)
            buffer.append(LabelIzaGoTo.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleStatement_gotoStatement]");
        return buffer.toString();
    }
}
