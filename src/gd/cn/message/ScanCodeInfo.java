package gd.cn.message;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="ScanCodeInfo")
public class ScanCodeInfo {
@XmlElement(name="ScanType")
private String ScanType;
@XmlElement(name="ScanResult")
private String ScanResult;
public String getScanType() {
	return ScanType;
}
public void setScanType(String scanType) {
	ScanType = scanType;
}
public String getScanResult() {
	return ScanResult;
}
public void setScanResult(String scanResult) {
	ScanResult = scanResult;
}

}
