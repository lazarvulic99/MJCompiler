// generated with ast extension for cup
// version 0.8
// 22/7/2022 19:5:44


package rs.ac.bg.etf.pp1.ast;

public class Designator_element extends Designator {

    private VariableNameZaNiz VariableNameZaNiz;
    private Expr Expr;

    public Designator_element (VariableNameZaNiz VariableNameZaNiz, Expr Expr) {
        this.VariableNameZaNiz=VariableNameZaNiz;
        if(VariableNameZaNiz!=null) VariableNameZaNiz.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
    }

    public VariableNameZaNiz getVariableNameZaNiz() {
        return VariableNameZaNiz;
    }

    public void setVariableNameZaNiz(VariableNameZaNiz VariableNameZaNiz) {
        this.VariableNameZaNiz=VariableNameZaNiz;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VariableNameZaNiz!=null) VariableNameZaNiz.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VariableNameZaNiz!=null) VariableNameZaNiz.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VariableNameZaNiz!=null) VariableNameZaNiz.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Designator_element(\n");

        if(VariableNameZaNiz!=null)
            buffer.append(VariableNameZaNiz.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Designator_element]");
        return buffer.toString();
    }
}
