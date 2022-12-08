// generated with ast extension for cup
// version 0.8
// 22/7/2022 19:5:44


package rs.ac.bg.etf.pp1.ast;

public class VarDeclVectorForMethod_notEmpty extends VarDeclVectorForMethod {

    private VarDeclVectorForMethod VarDeclVectorForMethod;
    private VariableDeclList VariableDeclList;

    public VarDeclVectorForMethod_notEmpty (VarDeclVectorForMethod VarDeclVectorForMethod, VariableDeclList VariableDeclList) {
        this.VarDeclVectorForMethod=VarDeclVectorForMethod;
        if(VarDeclVectorForMethod!=null) VarDeclVectorForMethod.setParent(this);
        this.VariableDeclList=VariableDeclList;
        if(VariableDeclList!=null) VariableDeclList.setParent(this);
    }

    public VarDeclVectorForMethod getVarDeclVectorForMethod() {
        return VarDeclVectorForMethod;
    }

    public void setVarDeclVectorForMethod(VarDeclVectorForMethod VarDeclVectorForMethod) {
        this.VarDeclVectorForMethod=VarDeclVectorForMethod;
    }

    public VariableDeclList getVariableDeclList() {
        return VariableDeclList;
    }

    public void setVariableDeclList(VariableDeclList VariableDeclList) {
        this.VariableDeclList=VariableDeclList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarDeclVectorForMethod!=null) VarDeclVectorForMethod.accept(visitor);
        if(VariableDeclList!=null) VariableDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclVectorForMethod!=null) VarDeclVectorForMethod.traverseTopDown(visitor);
        if(VariableDeclList!=null) VariableDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclVectorForMethod!=null) VarDeclVectorForMethod.traverseBottomUp(visitor);
        if(VariableDeclList!=null) VariableDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclVectorForMethod_notEmpty(\n");

        if(VarDeclVectorForMethod!=null)
            buffer.append(VarDeclVectorForMethod.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VariableDeclList!=null)
            buffer.append(VariableDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclVectorForMethod_notEmpty]");
        return buffer.toString();
    }
}
