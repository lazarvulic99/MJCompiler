// generated with ast extension for cup
// version 0.8
// 22/7/2022 19:5:44


package rs.ac.bg.etf.pp1.ast;

public class MethodDeclList implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private MethodName MethodName;
    private VarDeclVectorForMethod VarDeclVectorForMethod;
    private StatementVector StatementVector;

    public MethodDeclList (MethodName MethodName, VarDeclVectorForMethod VarDeclVectorForMethod, StatementVector StatementVector) {
        this.MethodName=MethodName;
        if(MethodName!=null) MethodName.setParent(this);
        this.VarDeclVectorForMethod=VarDeclVectorForMethod;
        if(VarDeclVectorForMethod!=null) VarDeclVectorForMethod.setParent(this);
        this.StatementVector=StatementVector;
        if(StatementVector!=null) StatementVector.setParent(this);
    }

    public MethodName getMethodName() {
        return MethodName;
    }

    public void setMethodName(MethodName MethodName) {
        this.MethodName=MethodName;
    }

    public VarDeclVectorForMethod getVarDeclVectorForMethod() {
        return VarDeclVectorForMethod;
    }

    public void setVarDeclVectorForMethod(VarDeclVectorForMethod VarDeclVectorForMethod) {
        this.VarDeclVectorForMethod=VarDeclVectorForMethod;
    }

    public StatementVector getStatementVector() {
        return StatementVector;
    }

    public void setStatementVector(StatementVector StatementVector) {
        this.StatementVector=StatementVector;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodName!=null) MethodName.accept(visitor);
        if(VarDeclVectorForMethod!=null) VarDeclVectorForMethod.accept(visitor);
        if(StatementVector!=null) StatementVector.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodName!=null) MethodName.traverseTopDown(visitor);
        if(VarDeclVectorForMethod!=null) VarDeclVectorForMethod.traverseTopDown(visitor);
        if(StatementVector!=null) StatementVector.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodName!=null) MethodName.traverseBottomUp(visitor);
        if(VarDeclVectorForMethod!=null) VarDeclVectorForMethod.traverseBottomUp(visitor);
        if(StatementVector!=null) StatementVector.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodDeclList(\n");

        if(MethodName!=null)
            buffer.append(MethodName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclVectorForMethod!=null)
            buffer.append(VarDeclVectorForMethod.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementVector!=null)
            buffer.append(StatementVector.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodDeclList]");
        return buffer.toString();
    }
}
