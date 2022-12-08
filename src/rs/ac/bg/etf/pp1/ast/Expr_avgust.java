// generated with ast extension for cup
// version 0.8
// 22/7/2022 19:5:44


package rs.ac.bg.etf.pp1.ast;

public class Expr_avgust extends ExprList {

    private ExprList ExprList;
    private TernaryOp TernaryOp;
    private ExprBasic ExprBasic;

    public Expr_avgust (ExprList ExprList, TernaryOp TernaryOp, ExprBasic ExprBasic) {
        this.ExprList=ExprList;
        if(ExprList!=null) ExprList.setParent(this);
        this.TernaryOp=TernaryOp;
        if(TernaryOp!=null) TernaryOp.setParent(this);
        this.ExprBasic=ExprBasic;
        if(ExprBasic!=null) ExprBasic.setParent(this);
    }

    public ExprList getExprList() {
        return ExprList;
    }

    public void setExprList(ExprList ExprList) {
        this.ExprList=ExprList;
    }

    public TernaryOp getTernaryOp() {
        return TernaryOp;
    }

    public void setTernaryOp(TernaryOp TernaryOp) {
        this.TernaryOp=TernaryOp;
    }

    public ExprBasic getExprBasic() {
        return ExprBasic;
    }

    public void setExprBasic(ExprBasic ExprBasic) {
        this.ExprBasic=ExprBasic;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ExprList!=null) ExprList.accept(visitor);
        if(TernaryOp!=null) TernaryOp.accept(visitor);
        if(ExprBasic!=null) ExprBasic.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ExprList!=null) ExprList.traverseTopDown(visitor);
        if(TernaryOp!=null) TernaryOp.traverseTopDown(visitor);
        if(ExprBasic!=null) ExprBasic.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ExprList!=null) ExprList.traverseBottomUp(visitor);
        if(TernaryOp!=null) TernaryOp.traverseBottomUp(visitor);
        if(ExprBasic!=null) ExprBasic.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Expr_avgust(\n");

        if(ExprList!=null)
            buffer.append(ExprList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(TernaryOp!=null)
            buffer.append(TernaryOp.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ExprBasic!=null)
            buffer.append(ExprBasic.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Expr_avgust]");
        return buffer.toString();
    }
}
