
package calc;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the calc package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _EvalResponse_QNAME = new QName("http://calc/", "evalResponse");
    private final static QName _Eval_QNAME = new QName("http://calc/", "eval");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: calc
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Eval }
     * 
     */
    public Eval createEval() {
        return new Eval();
    }

    /**
     * Create an instance of {@link EvalResponse }
     * 
     */
    public EvalResponse createEvalResponse() {
        return new EvalResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EvalResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://calc/", name = "evalResponse")
    public JAXBElement<EvalResponse> createEvalResponse(EvalResponse value) {
        return new JAXBElement<EvalResponse>(_EvalResponse_QNAME, EvalResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Eval }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://calc/", name = "eval")
    public JAXBElement<Eval> createEval(Eval value) {
        return new JAXBElement<Eval>(_Eval_QNAME, Eval.class, null, value);
    }

}
