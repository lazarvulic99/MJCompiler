// generated with ast extension for cup
// version 0.8
// 22/7/2022 19:5:44


package rs.ac.bg.etf.pp1.ast;

public class VarDeclVectorForMethod_empty extends VarDeclVectorForMethod {

    public VarDeclVectorForMethod_empty () {
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
        buffer.append("VarDeclVectorForMethod_empty(\n");

        buffer.append(tab);
        buffer.append(") [VarDeclVectorForMethod_empty]");
        return buffer.toString();
    }
}
