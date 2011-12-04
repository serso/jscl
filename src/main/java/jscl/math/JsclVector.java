package jscl.math;

import jscl.math.function.Conjugate;
import jscl.math.function.Constant;
import jscl.math.function.Frac;
import jscl.mathml.MathML;
import jscl.util.ArrayComparator;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class JsclVector extends Generic {

	protected final Generic elements[];

	protected final int rows;

    public JsclVector(Generic elements[]) {
        this.elements = elements;
        rows = elements.length;
    }

    public Generic[] elements() {
        return elements;
    }

    public JsclVector add(JsclVector vector) {
        JsclVector v=(JsclVector) newInstance();
        for(int i=0;i< rows;i++) v.elements[i]= elements[i].add(vector.elements[i]);
        return v;
    }

    @NotNull
	public Generic add(@NotNull Generic that) {
        if(that instanceof JsclVector) {
            return add((JsclVector) that);
        } else {
            return add(valueOf(that));
        }
    }

    public JsclVector subtract(JsclVector vector) {
        JsclVector v=(JsclVector) newInstance();
        for(int i=0;i< rows;i++) v.elements[i]= elements[i].subtract(vector.elements[i]);
        return v;
    }

    @NotNull
	public Generic subtract(@NotNull Generic that) {
        if(that instanceof JsclVector) {
            return subtract((JsclVector) that);
        } else {
            return subtract(valueOf(that));
        }
    }

    @NotNull
	public Generic multiply(@NotNull Generic that) {
        if(that instanceof JsclVector) {
            return scalarProduct((JsclVector) that);
        } else if(that instanceof Matrix) {
            return ((Matrix) that).transpose().multiply(this);
        } else {
            JsclVector v=(JsclVector) newInstance();
            for(int i=0;i< rows;i++) v.elements[i]= elements[i].multiply(that);
            return v;
        }
    }

    @NotNull
	public Generic divide(@NotNull Generic that) throws ArithmeticException {
        if(that instanceof JsclVector) {
            throw new ArithmeticException("Unable to divide vector by vector!");
        } else if(that instanceof Matrix) {
            return multiply(that.inverse());
        } else {
            JsclVector v=(JsclVector) newInstance();
            for(int i=0;i< rows;i++) {
                try {
                    v.elements[i]= elements[i].divide(that);
                } catch (NotDivisibleException e) {
                    v.elements[i]=new Frac(elements[i], that).evaluate();
                }
            }
            return v;
        }
    }

    public Generic gcd(Generic generic) {
        return null;
    }

    public Generic gcd() {
        return null;
    }

    public Generic negate() {
        JsclVector v=(JsclVector) newInstance();
        for(int i=0;i< rows;i++) v.elements[i]= elements[i].negate();
        return v;
    }

    public int signum() {
        for(int i=0;i< rows;i++) {
            int c= elements[i].signum();
            if(c<0) return -1;
            else if(c>0) return 1;
        }
        return 0;
    }

    public int degree() {
        return 0;
    }

    public Generic antiDerivative(Variable variable) throws NotIntegrableException {
        JsclVector v=(JsclVector) newInstance();
        for(int i=0;i< rows;i++) v.elements[i]= elements[i].antiDerivative(variable);
        return v;
    }

    public Generic derivative(Variable variable) {
        JsclVector v=(JsclVector) newInstance();
        for(int i=0;i< rows;i++) v.elements[i]= elements[i].derivative(variable);
        return v;
    }

    public Generic substitute(Variable variable, Generic generic) {
        JsclVector v=(JsclVector) newInstance();
        for(int i=0;i< rows;i++) v.elements[i]= elements[i].substitute(variable,generic);
        return v;
    }

    public Generic expand() {
        JsclVector v=(JsclVector) newInstance();
        for(int i=0;i< rows;i++) v.elements[i]= elements[i].expand();
        return v;
    }

    public Generic factorize() {
        JsclVector v=(JsclVector) newInstance();
        for(int i=0;i< rows;i++) v.elements[i]= elements[i].factorize();
        return v;
    }

    public Generic elementary() {
        JsclVector v=(JsclVector) newInstance();
        for(int i=0;i< rows;i++) v.elements[i]= elements[i].elementary();
        return v;
    }

    public Generic simplify() {
        JsclVector v=(JsclVector) newInstance();
        for(int i=0;i< rows;i++) v.elements[i]= elements[i].simplify();
        return v;
    }

    public Generic numeric() {
        return new NumericWrapper(this);
    }

    public Generic valueOf(Generic generic) {
        if(generic instanceof JsclVector ||  generic instanceof Matrix) {
            throw new ArithmeticException("Unable to create vector: vector of vectors or vector of matrices are forbidden!");
        } else {
            JsclVector v=(JsclVector)unity(rows).multiply(generic);
            return newInstance(v.elements);
        }
    }

    public Generic[] sumValue() {
        return new Generic[] {this};
    }

    public Generic[] productValue() throws NotProductException {
        return new Generic[] {this};
    }

    public Power powerValue() throws NotPowerException {
        return new Power(this,1);
    }

    public Expression expressionValue() throws NotExpressionException {
        throw new NotExpressionException();
    }

    public JsclInteger integerValue() throws NotIntegerException {
        throw new NotIntegerException();
    }

	@Override
	public boolean isInteger() {
		return false;
	}

	public Variable variableValue() throws NotVariableException {
        throw new NotVariableException();
    }

    public Variable[] variables() {
        return null;
    }

    public boolean isPolynomial(Variable variable) {
        return false;
    }

    public boolean isConstant(Variable variable) {
        return false;
    }

    public Generic magnitude2() {
        return scalarProduct(this);
    }

    public Generic scalarProduct(JsclVector vector) {
        Generic a= JsclInteger.valueOf(0);
        for(int i=0;i< rows;i++) {
            a=a.add(elements[i].multiply(vector.elements[i]));
        }
        return a;
    }

    public JsclVector vectorProduct(JsclVector vector) {
        JsclVector v=(JsclVector) newInstance();
        Generic m[][]={
            {JsclInteger.valueOf(0), elements[2].negate(), elements[1]},
            {elements[2], JsclInteger.valueOf(0), elements[0].negate()},
            {elements[1].negate(), elements[0], JsclInteger.valueOf(0)}
        };
        JsclVector v2=(JsclVector)new Matrix(m).multiply(vector);
        for(int i=0;i< rows;i++) v.elements[i]=i<v2.rows ?v2.elements[i]: JsclInteger.valueOf(0);
        return v;
    }

    public JsclVector complexProduct(JsclVector vector) {
        return product(new Clifford(0,1).operator(),vector);
    }

    public JsclVector quaternionProduct(JsclVector vector) {
        return product(new Clifford(0,2).operator(),vector);
    }

    public JsclVector geometricProduct(JsclVector vector, int algebra[]) {
        return product(new Clifford(algebra==null?new int[] {Clifford.log2e(rows),0}:algebra).operator(),vector);
    }

    JsclVector product(int product[][], JsclVector vector) {
        JsclVector v=(JsclVector) newInstance();
        for(int i=0;i< rows;i++) v.elements[i]= JsclInteger.valueOf(0);
        for(int i=0;i< rows;i++) {
            for(int j=0;j< rows;j++) {
                Generic a= elements[i].multiply(vector.elements[j]);
                int k=Math.abs(product[i][j])-1;
                v.elements[k]=v.elements[k].add(product[i][j]<0?a.negate():a);
            }
        }
        return v;
    }

    public Generic divergence(Variable variable[]) {
        Generic a= JsclInteger.valueOf(0);
        for(int i=0;i< rows;i++) a=a.add(elements[i].derivative(variable[i]));
        return a;
    }

    public JsclVector curl(Variable variable[]) {
        JsclVector v=(JsclVector) newInstance();
        v.elements[0]= elements[2].derivative(variable[1]).subtract(elements[1].derivative(variable[2]));
        v.elements[1]= elements[0].derivative(variable[2]).subtract(elements[2].derivative(variable[0]));
        v.elements[2]= elements[1].derivative(variable[0]).subtract(elements[0].derivative(variable[1]));
        for(int i=3;i< rows;i++) v.elements[i]= elements[i];
        return v;
    }

    public Matrix jacobian(Variable variable[]) {
        Matrix m=new Matrix(new Generic[rows][variable.length]);
        for(int i=0;i< rows;i++) {
            for(int j=0;j<variable.length;j++) {
                m.elements[i][j]= elements[i].derivative(variable[j]);
            }
        }
        return m;
    }

    public Generic del(Variable variable[], int algebra[]) {
        return differential(new Clifford(algebra==null?new int[] {Clifford.log2e(rows),0}:algebra).operator(),variable);
    }

    JsclVector differential(int product[][], Variable variable[]) {
        JsclVector v=(JsclVector) newInstance();
        for(int i=0;i< rows;i++) v.elements[i]= JsclInteger.valueOf(0);
        int l=Clifford.log2e(rows);
        for(int i=1;i<=l;i++) {
            for(int j=0;j< rows;j++) {
                Generic a= elements[j].derivative(variable[i-1]);
                int k=Math.abs(product[i][j])-1;
                v.elements[k]=v.elements[k].add(product[i][j]<0?a.negate():a);
            }
        }
        return v;
    }

    public Generic conjugate() {
        JsclVector v=(JsclVector) newInstance();
        for(int i=0;i< rows;i++) {
            v.elements[i]=new Conjugate(elements[i]).evaluate();
        }
        return v;
    }

    public int compareTo(JsclVector vector) {
        return ArrayComparator.comparator.compare(elements,vector.elements);
    }

    public int compareTo(Generic generic) {
        if(generic instanceof JsclVector) {
            return compareTo((JsclVector)generic);
        } else {
            return compareTo(valueOf(generic));
        }
    }

    public static JsclVector unity(int dimension) {
        JsclVector v=new JsclVector(new Generic[dimension]);
        for(int i=0;i<v.rows;i++) {
            if(i==0) v.elements[i]= JsclInteger.valueOf(1);
            else v.elements[i]= JsclInteger.valueOf(0);
        }
        return v;
    }

    public String toString() {
		StringBuilder buffer = new StringBuilder();
        buffer.append("{");
        for(int i=0;i< rows;i++) {
            buffer.append(elements[i]).append(i< rows -1?", ":"");
        }
        buffer.append("}");
        return buffer.toString();
    }

    public String toJava() {
		final StringBuilder result = new StringBuilder();
        result.append("new Vector(new Numeric[] {");
        for(int i=0;i< rows;i++) {
            result.append(elements[i].toJava()).append(i< rows -1?", ":"");
        }
        result.append("})");
        return result.toString();
    }

    public void toMathML(MathML element, Object data) {
        int exponent=data instanceof Integer? (Integer) data :1;
        if(exponent==1) bodyToMathML(element);
        else {
            MathML e1=element.element("msup");
            bodyToMathML(e1);
            MathML e2=element.element("mn");
            e2.appendChild(element.text(String.valueOf(exponent)));
            e1.appendChild(e2);
            element.appendChild(e1);
        }
    }

	@NotNull
	@Override
	public Set<? extends Constant> getConstants() {
		final Set<Constant> result = new HashSet<Constant>(elements.length);

		for (Generic element : elements) {
			result.addAll(element.getConstants());
		}

		return result;
	}

	protected void bodyToMathML(MathML e0) {
        MathML e1=e0.element("mfenced");
        MathML e2=e0.element("mtable");
        for(int i=0;i< rows;i++) {
            MathML e3=e0.element("mtr");
            MathML e4=e0.element("mtd");
            elements[i].toMathML(e4,null);
            e3.appendChild(e4);
            e2.appendChild(e3);
        }
        e1.appendChild(e2);
        e0.appendChild(e1);
    }

    protected Generic newInstance() {
        return newInstance(new Generic[rows]);
    }

    protected Generic newInstance(Generic element[]) {
        return new JsclVector(element);
    }
}

class Clifford {
    int p,n;
    int operator[][];

    Clifford(int algebra[]) {
        this(algebra[0],algebra[1]);
    }

    Clifford(int p, int q) {
        this.p=p;
        n=p+q;
        int m=1<<n;
        operator=new int[m][m];
        for(int i=0;i<m;i++) {
            for(int j=0;j<m;j++) {
                int a=combination(i,n);
                int b=combination(j,n);
                int c=a^b;
                int l=location(c,n);
                boolean s=sign(a,b);
                int k=l+1;
                operator[i][j]=s?-k:k;
            }
        }
    }

    boolean sign(int a, int b) {
        boolean s=false;
        for(int i=0;i<n;i++) {
            if((b&(1<<i))>0) {
                for(int j=i;j<n;j++) {
                    if((a&(1<<j))>0 && (j>i || i>=p)) s=!s;
                }
            }
        }
        return s;
    }

    static int combination(int l, int n) {
        if(n<=2) return l;
        int b[]=new int[1];
        int l1=decimation(l,n,b);
        int c=combination(l1,n-1);
        return (c<<1)+b[0];
    }

    static int location(int c, int n) {
        if(n<=2) return c;
        int c1=c>>1;
        int b=c&1;
        int l1=location(c1,n-1);
        return dilatation(l1,n,new int[] {b});
    }

    static int decimation(int l, int n, int b[]) {
        int p=grade(l,n-1,1);
        int p1=(p+1)>>1;
        b[0]=p&1;
        return l-sum(p1,n-1);
    }

    static int dilatation(int l, int n, int b[]) {
        int p1=grade(l,n-1);
        return l+sum(p1+b[0],n-1);
    }

    static int grade(int l, int n) {
        return grade(l,n,0);
    }

    static int grade(int l, int n, int d) {
        int s=0, p=0;
        while(true) {
            s+=binomial(n,p>>d);
            if(s<=l) p++;
            else break;
        }
        return p;
    }

    static int sum(int p, int n) {
        int q=0, s=0;
        while(q<p) s+=binomial(n,q++);
        return s;
    }

    static int binomial(int n, int p) {
        int a=1, b=1;
        for(int i=n-p+1;i<=n;i++) a*=i;
        for(int i=2;i<=p;i++) b*=i;
        return a/b;
    }

    static int log2e(int n) {
        int i;
        for(i=0;n>1;n>>=1) i++;
        return i;
    }

    int[][] operator() {
        return operator;
    }
}
