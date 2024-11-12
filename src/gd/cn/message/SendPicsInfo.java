package gd.cn.message;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlAccessorType(XmlAccessType.PROPERTY) 
@XmlRootElement(name="SendPicsInfo")
public class SendPicsInfo {
@XmlElement(name="Count")
private int Count;
@XmlElement(name="PicList")
private List<PicList> PicList;
public int getCount() {
	return Count;
}
public void setCount(int count) {
	Count = count;
}
public List<PicList> getPicList() {
	return PicList;
}
public void setPicList(List<PicList> picList) {
	PicList = picList;
}

}
