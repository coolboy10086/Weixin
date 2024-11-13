package gd.cn.message;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="xml")
public class ReceiveMessage {
@XmlElement(name="ToUserName")
private String ToUserName;
@XmlElement(name="FromUserName")
private String FromUserName;
@XmlElement(name="CreateTime")
private String CreateTime;
@XmlElement(name="MsgType")
private String MsgType;
@XmlElement(name="Content")
private String Content;
@XmlElement(name="MsgId")
private String MsgId;
@XmlElement(name="Event")
private String Event;
@XmlElement(name="EventKey")
private String EventKey;
@XmlElement(name="MenuId")
private String MenuId;
@XmlElement(name="PicUrl")
private String PicUrl;
@XmlElement(name="MediaId")
private String MediaId;
@XmlElement(name="Location_X")
private String Location_X;
@XmlElement(name="Location_Y")
private String Location_Y;
@XmlElement(name="Scale")
private String Scale;
@XmlElement(name="Label")
private String Label;
@XmlElement(name="SendLocationInfo")
private SendLocationInfo SendLocationInfo;
@XmlElement(name="ScanCodeInfo")
private ScanCodeInfo ScanCodeInfo;
@XmlElement(name="SendPicsInfo")
private SendPicsInfo SendPicsInfo;
@XmlElement(name="Latitude")
private String Latitude;
@XmlElement(name="Longitude")
private String Longitude;
@XmlElement(name="Precision")
private String Precision;
public String getToUserName() {
	return ToUserName;
}
public void setToUserName(String toUserName) {
	ToUserName = toUserName;
}
public String getFromUserName() {
	return FromUserName;
}
public void setFromUserName(String fromUserName) {
	FromUserName = fromUserName;
}
public String getCreateTime() {
	return CreateTime;
}
public void setCreateTime(String createTime) {
	CreateTime = createTime;
}
public String getMsgType() {
	return MsgType;
}
public void setMsgType(String msgType) {
	MsgType = msgType;
}
public String getContent() {
	return Content;
}
public void setContent(String content) {
	Content = content;
}
public String getMsgId() {
	return MsgId;
}
public void setMsgId(String msgId) {
	MsgId = msgId;
}
public String getEvent() {
	return Event;
}
public void setEvent(String event) {
	Event = event;
}
public String getEventKey() {
	return EventKey;
}
public void setEventKey(String eventKey) {
	EventKey = eventKey;
}
public String getMenuId() {
	return MenuId;
}
public void setMenuId(String menuId) {
	MenuId = menuId;
}
public String getPicUrl() {
	return PicUrl;
}
public void setPicUrl(String picUrl) {
	PicUrl = picUrl;
}
public String getMediaId() {
	return MediaId;
}
public void setMediaId(String mediaId) {
	MediaId = mediaId;
}
public String getLocation_X() {
	return Location_X;
}
public void setLocation_X(String location_X) {
	Location_X = location_X;
}
public String getLocation_Y() {
	return Location_Y;
}
public void setLocation_Y(String location_Y) {
	Location_Y = location_Y;
}
public String getScale() {
	return Scale;
}
public void setScale(String scale) {
	Scale = scale;
}
public String getLabel() {
	return Label;
}
public void setLabel(String label) {
	Label = label;
}
public SendLocationInfo getSendLocationInfo() {
	return SendLocationInfo;
}
public void setSendLocationInfo(SendLocationInfo sendLocationInfo) {
	SendLocationInfo = sendLocationInfo;
}
public ScanCodeInfo getScanCodeInfo() {
	return ScanCodeInfo;
}
public void setScanCodeInfo(ScanCodeInfo scanCodeInfo) {
	ScanCodeInfo = scanCodeInfo;
}
public SendPicsInfo getSendPicsInfo() {
	return SendPicsInfo;
}
public void setSendPicsInfo(SendPicsInfo sendPicsInfo) {
	SendPicsInfo = sendPicsInfo;
}
public String getLatitude() {
	return Latitude;
}
public void setLatitude(String latitude) {
	Latitude = latitude;
}
public String getLongitude() {
	return Longitude;
}
public void setLongitude(String longitude) {
	Longitude = longitude;
}
public String getPrecision() {
	return Precision;
}
public void setPrecision(String precision) {
	Precision = precision;
}

}