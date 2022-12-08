// generated with ast extension for cup
// version 0.8
// 22/7/2022 19:5:44


package rs.ac.bg.etf.pp1.ast;

public class VariableSingle_ok extends VariableSingle {

    private VariableDecl VariableDecl;

    public VariableSingle_ok (VariableDecl VariableDecl) {
        this.VariableDecl=VariableDecl;
        if(VariableDecl!=null) VariableDecl.setParent(this);
    }

    public VariableDecl getVariableDecl() {
        return VariableDecl;
    }

    public void setVariableDecl(VariableDecl VariableDecl) {
        this.VariableDecl=VariableDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VariableDecl!=null) VariableDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VariableDecl!=null) VariableDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VariableDecl!=null) VariableDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VariableSingle_ok(\n");

        if(VariableDecl!=null)
            buffer.append(VariableDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VariableSingle_ok]");
        return buffer.toString();
    }
}
