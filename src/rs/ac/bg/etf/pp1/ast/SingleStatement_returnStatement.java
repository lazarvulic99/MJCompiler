// generated with ast extension for cup
// version 0.8
// 22/7/2022 19:5:44


package rs.ac.bg.etf.pp1.ast;

public class SingleStatement_returnStatement extends SingleStatement {

    public SingleStatement_returnStatement () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleStatement_returnStatement(\n");

        buffer.append(tab);
        buffer.append(") [SingleStatement_returnStatement]");
        return buffer.toString();
    }
}
