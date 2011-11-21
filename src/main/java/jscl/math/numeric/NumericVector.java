package jscl.math.numeric;

import jscl.util.ArrayComparator;
import org.jetbrains.annotations.NotNull;

public class NumericVector extends Numeric {
    protected final Numeric element[];
    protected final int n;

    public NumericVector(Numeric element[]) {
        this.element=element;
        n=element.length;
    }

    public Numeric[] elements() {
        return element;
    }

    public NumericVector add(NumericVector vector) {
        NumericVector v= newinstance();
        for(int i=0;i<n;i++) v.element[i]=element[i].add(vector.element[i]);
        return v;
    }

    @NotNull
	public Numeric add(@NotNull Numeric that) {
        if(that instanceof NumericVector) {
            return add((NumericVector) that);
        } else {
            return add(valueOf(that));
        }
    }

    public NumericVector subtract(NumericVector vector) {
        NumericVector v= newinstance();
		for (int i = 0; i < n; i++) {
			v.element[i] = element[i].subtract(vector.element[i]);
		}
        return v;
    }

    @NotNull
	public Numeric subtract(@NotNull Numeric that) {
        if(that instanceof NumericVector) {
            return subtract((NumericVector) that);
        } else {
            return subtract(valueOf(that));
        }
    }

    @NotNull
	public Numeric multiply(@NotNull Numeric that) {
        if(that instanceof NumericVector) {
            return scalarProduct((NumericVector) that);
        } else if(that instanceof NumericMatrix) {
            return ((NumericMatrix) that).transpose().multiply(this);
        } else {
            NumericVector v= newinstance();
            for(int i=0;i<n;i++) v.element[i]=element[i].multiply(that);
            return v;
        }
    }

    @NotNull
	public Numeric divide(@NotNull Numeric that) throws ArithmeticException {
        if(that instanceof NumericVector) {
            throw new ArithmeticException();
        } else if(that instanceof NumericMatrix) {
            return multiply(that.inverse());
        } else {
            NumericVector v= newinstance();
            for(int i=0;i<n;i++) {
                v.element[i]=element[i].divide(that);
            }
            return v;
        }
    }

    @NotNull
	public Numeric negate() {
        NumericVector v= newinstance();
        for(int i=0;i<n;i++) v.element[i]=element[i].negate();
        return v;
    }

    public int signum() {
		for (int i = 0; i < n; i++) {
			int c = element[i].signum();
			if (c < 0) {
				return -1;
			} else if (c > 0) {
				return 1;
			}
		}
		return 0;
	}

	public Numeric valueOf(Numeric numeric) {
        if(numeric instanceof NumericVector ||  numeric instanceof NumericMatrix) {
            throw new ArithmeticException();
        } else {
            NumericVector v=(NumericVector)unity(n).multiply(numeric);
            return newinstance(v.element);
        }
    }

    public Numeric magnitude2() {
        return scalarProduct(this);
    }

    public Numeric scalarProduct(NumericVector vector) {
        Numeric a= JsclDouble.ZERO;
        for(int i=0;i<n;i++) {
            a=a.add(element[i].multiply(vector.element[i]));
        }
        return a;
    }

    @NotNull
	public Numeric ln() {
        throw new ArithmeticException();
    }

	@NotNull
	@Override
	public Numeric lg() {
		throw new ArithmeticException();
	}

	@NotNull
	public Numeric exp() {
        throw new ArithmeticException();
    }

    public Numeric conjugate() {
        NumericVector v= newinstance();
        for(int i=0;i<n;i++) v.element[i]=element[i].conjugate();
        return v;
    }

    public int compareTo(NumericVector vector) {
        return ArrayComparator.comparator.compare(element,vector.element);
    }

    public int compareTo(Numeric numeric) {
        if(numeric instanceof NumericVector) {
            return compareTo((NumericVector)numeric);
        } else {
            return compareTo(valueOf(numeric));
        }
    }

    public static NumericVector unity(int dimension) {
        NumericVector v=new NumericVector(new Numeric[dimension]);
        for(int i=0;i<v.n;i++) {
            if(i==0) v.element[i]= JsclDouble.ONE;
            else v.element[i]= JsclDouble.ZERO;
        }
        return v;
    }

    public String toString() {
        StringBuffer buffer=new StringBuffer();
        buffer.append("{");
        for(int i=0;i<n;i++) {
            buffer.append(element[i]).append(i<n-1?", ":"");
        }
        buffer.append("}");
        return buffer.toString();
    }

    protected NumericVector newinstance() {
        return newinstance(new Numeric[n]);
    }

    protected NumericVector newinstance(Numeric element[]) {
        return new NumericVector(element);
    }
}
