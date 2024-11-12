package gd.cn.message;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlAccessorType(XmlAccessType.PROPERTY) 
@XmlRootElement(name="PicList")
public class PicList {
@XmlElement(name="PicMd5Sum")
private String PicMd5Sum;

public String getPicMd5Sum() {
	return PicMd5Sum;
}

public void setPicMd5Sum(String picMd5Sum) {
	PicMd5Sum = picMd5Sum;
}

}
