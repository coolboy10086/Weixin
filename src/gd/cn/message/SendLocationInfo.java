package gd.cn.message;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="SendLocationInfo")
public class SendLocationInfo {
@XmlElement(name="Location_X")
private double Location_X;
@XmlElement(name="Location_Y")
private double Location_Y;
@XmlElement(name="Scale")
private int Scale;
@XmlElement(name="Label")
private String Label;
@XmlElement(name="Poiname")
private String Poiname;
public double getLocation_X() {
	return Location_X;
}
public void setLocation_X(double location_X) {
	Location_X = location_X;
}
public double getLocation_Y() {
	return Location_Y;
}
public void setLocation_Y(double location_Y) {
	Location_Y = location_Y;
}
public int getScale() {
	return Scale;
}
public void setScale(int scale) {
	Scale = scale;
}
public String getLabel() {
	return Label;
}
public void setLabel(String label) {
	Label = label;
}
public String getPoiname() {
	return Poiname;
}
public void setPoiname(String poiname) {
	Poiname = poiname;
}

}
