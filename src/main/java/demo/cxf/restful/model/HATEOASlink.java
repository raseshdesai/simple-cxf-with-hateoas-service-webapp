package demo.cxf.restful.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created with IntelliJ IDEA.
 * User: SKAVA
 * Date: 6/17/14
 * Time: 9:28 AM
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name="link", namespace="http://www.w3.org/2005/Atom")
public class HATEOASlink {
    public  String rel;
    public  String href;
    public  String type;
    public HATEOASlink() {}
    public HATEOASlink(String rel, String href, String type) {
        this.rel = rel;
        this.href = href;
        this.type = type;
    }
    public HATEOASlink(String rel, String href) {
        this(rel,href,"application/xml");
    }

}
