package com.github.ming.wechat.msgevent.bean;

import org.eclipse.persistence.oxm.annotations.XmlCDATA;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

/**
 * 微信事件
 *
 * @author ZM
 * @date : 2018-12-31 20:32
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class WeChatEvent {

    /**
     * 开发者微信号
     */
    @XmlElement(name = "ToUserName")
    private String toUserName;

    /**
     * 发送方帐号（一个OpenID）
     */
    @XmlElement(name = "FromUserName")
    private String fromUserName;

    /**
     * 消息创建时间 （整型）
     */
    @XmlElement(name = "CreateTime")
    private Integer createTime;

    /**
     * 消息类型
     */
    @XmlElement(name = "MsgType")
    private String msgType;

    /**
     * 消息id，64位整型
     */
    @XmlElement(name = "MsgId")
    private Long msgId;

    /**
     * 文本消息内容
     */
    @XmlCDATA
    @XmlElement(name = "Content")
    private String content;

    /**
     * 图片链接（由系统生成）
     */
    @XmlElement(name = "PicUrl")
    private String picUrl;

    /**
     * 媒体id，可以调用多媒体文件下载接口拉取数据
     */
    @XmlElement(name = "MediaId")
    private String mediaId;

    /**
     * 语音格式，如amr，speex等
     */
    @XmlElement(name = "Format")
    private String format;

    /**
     * 语音识别结果，UTF8编码
     * 请注意，开通语音识别后，用户每次发送语音给公众号时，微信会在推送的语音消息XML数据包中，增加一个Recognition字段
     * （注：由于客户端缓存，开发者开启或者关闭语音识别功能，对新关注者立刻生效，对已关注用户需要24小时生效。开发者可以
     * 重新关注此帐号进行测试
     */
    @XmlElement(name = "Recognition")
    private String recognition;

    /**
     * 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据
     */
    @XmlElement(name = "ThumbMediaId")
    private String thumbMediaId;

    /**
     * 地理位置 纬度
     */
    @XmlElement(name = "Location_X")
    private BigDecimal locationX;

    /**
     * 地理位置 经度
     */
    @XmlElement(name = "Location_Y")
    private BigDecimal locationY;

    /**
     * 地图缩放大小
     */
    @XmlElement(name = "Scale")
    private Integer scale;

    /**
     * 地理位置信息
     */
    @XmlElement(name = "Label")
    private String label;

    /**
     * 消息标题
     */
    @XmlElement(name = "Title")
    private String title;

    /**
     * 消息描述
     */
    @XmlElement(name = "Description")
    private String description;

    /**
     * 消息链接
     */
    @XmlElement(name = "Url")
    private String url;

    /**
     * 事件类型
     */
    @XmlElement(name = "Event")
    private String event;

    /**
     * 事件KEY值，qrscene_为前缀，后面为二维码的参数值
     */
    @XmlElement(name = "EventKey")
    private String eventKey;

    /**
     * 菜单ID，如果是个性化菜单，则可以通过这个字段，知道是哪个规则的菜单被点击了
     */
    @XmlElement(name = "MenuId")
    private Integer menuId;

    /**
     * 二维码的ticket，可用来换取二维码图片
     */
    @XmlElement(name = "Ticket")
    private String ticket;

    /**
     * 地理位置纬度
     */
    @XmlElement(name = "Latitude")
    private String latitude;

    /**
     * 地理位置经度
     */
    @XmlElement(name = "Longitude")
    private String longitude;

    /**
     * 地理位置精度
     */
    @XmlElement(name = "Precision")
    private String precision;

    /**
     * 扫描信息
     */
    @XmlElement(name = "Precision")
    private String scanCodeInfo;

    /**
     * 扫描类型，一般是qrcode
     */
    @XmlElement(name = "ScanType")
    private String scanType;

    /**
     * 扫描结果，即二维码对应的字符串信息
     */
    @XmlElement(name = "ScanResult")
    private String scanResult;

    /**
     * 发送的图片信息
     */
    @XmlElement(name = "SendPicsInfo")
    private String sendPicsInfo;

    /**
     * 发送的图片数量
     */
    @XmlElement(name = "Count")
    private String count;

    /**
     * 图片列表
     */
    @XmlElement(name = "PicList")
    private String picList;

    /**
     * 图片的MD5值，开发者若需要，可用于验证接收到图片
     */
    @XmlElement(name = "PicMd5Sum")
    private String picMd5Sum;

    /**
     * 发送的位置信息
     */
    @XmlElement(name = "SendLocationInfo")
    private String sendLocationInfo;

    /**
     * 朋友圈POI的名字，可能为空
     */
    @XmlElement(name = "Poiname")
    private String poiname;

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getRecognition() {
        return recognition;
    }

    public void setRecognition(String recognition) {
        this.recognition = recognition;
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }

    public BigDecimal getLocationX() {
        return locationX;
    }

    public void setLocationX(BigDecimal locationX) {
        this.locationX = locationX;
    }

    public BigDecimal getLocationY() {
        return locationY;
    }

    public void setLocationY(BigDecimal locationY) {
        this.locationY = locationY;
    }

    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPrecision() {
        return precision;
    }

    public void setPrecision(String precision) {
        this.precision = precision;
    }

    public String getScanCodeInfo() {
        return scanCodeInfo;
    }

    public void setScanCodeInfo(String scanCodeInfo) {
        this.scanCodeInfo = scanCodeInfo;
    }

    public String getScanType() {
        return scanType;
    }

    public void setScanType(String scanType) {
        this.scanType = scanType;
    }

    public String getScanResult() {
        return scanResult;
    }

    public void setScanResult(String scanResult) {
        this.scanResult = scanResult;
    }

    public String getSendPicsInfo() {
        return sendPicsInfo;
    }

    public void setSendPicsInfo(String sendPicsInfo) {
        this.sendPicsInfo = sendPicsInfo;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getPicList() {
        return picList;
    }

    public void setPicList(String picList) {
        this.picList = picList;
    }

    public String getPicMd5Sum() {
        return picMd5Sum;
    }

    public void setPicMd5Sum(String picMd5Sum) {
        this.picMd5Sum = picMd5Sum;
    }

    public String getSendLocationInfo() {
        return sendLocationInfo;
    }

    public void setSendLocationInfo(String sendLocationInfo) {
        this.sendLocationInfo = sendLocationInfo;
    }

    public String getPoiname() {
        return poiname;
    }

    public void setPoiname(String poiname) {
        this.poiname = poiname;
    }
}
